#include "task_app_mqtt.h"
#include "app_config.h"
#include "driver_rgb_led.h"
#include "bsp.h"

#include "mqtt_client.h"
#include "string.h"
#include "esp_timer.h"

#define TAG "MQTT"

static esp_mqtt_client_handle_t s_mqtt_client = NULL;
static int s_mqtt_connected = 0;

static void mqtt_event_handler(void *handler_args, esp_event_base_t base,
                               int32_t event_id, void *event_data)
{
    esp_mqtt_event_handle_t event = event_data;
    esp_mqtt_client_handle_t client = event->client;

    switch (event_id) {
        case MQTT_EVENT_CONNECTED:
            ESP_LOGI(TAG, "✅ MQTT 已连接");
            s_mqtt_connected = 1;
            esp_mqtt_client_subscribe(client, MQTT_TOPIC_LAMP, MQTT_QOS);
            ESP_LOGI(TAG, "📡 已订阅命令主题: %s", MQTT_TOPIC_LAMP);
            break;

        case MQTT_EVENT_DISCONNECTED:
            ESP_LOGW(TAG, "❌ MQTT 断开连接");
            s_mqtt_connected = 0;
            break;

        case MQTT_EVENT_SUBSCRIBED:
            ESP_LOGI(TAG, "订阅成功, msg_id=%d", event->msg_id);
            break;

        case MQTT_EVENT_UNSUBSCRIBED:
            ESP_LOGI(TAG, "取消订阅, msg_id=%d", event->msg_id);
            break;

        case MQTT_EVENT_PUBLISHED:
            ESP_LOGI(TAG, "发布成功, msg_id=%d", event->msg_id);
            break;

        case MQTT_EVENT_DATA:
            ESP_LOGI(TAG, "收到消息: topic=%.*s, payload=%.*s",
                     event->topic_len, event->topic,
                     event->data_len, event->data);

            if (strncmp(event->topic, MQTT_TOPIC_LAMP, event->topic_len) == 0) {
                if (event->data_len >= 1) {
                    if (event->data[0] == '1') {
                        ESP_LOGI(TAG, "💡 MQTT命令: 开灯");
                        g_manual_mode = 1;
                        rgb_led_set_color(RGB_COLOR_WHITE);
                    } else if (event->data[0] == '0') {
                        ESP_LOGI(TAG, "🔌 MQTT命令: 关灯");
                        g_manual_mode = 2;
                        rgb_led_off();
                    }
                }
            }
            break;

        case MQTT_EVENT_ERROR:
            ESP_LOGE(TAG, "MQTT 错误: %d", event->error_handle->error_type);
            break;

        default:
            break;
    }
}

void mqtt_publish_sensor_data(sensor_data_t *data)
{
    if (!s_mqtt_connected || !data) return;

    char json[512];
    int len = snprintf(json, sizeof(json),
        "{\"device_id\":\"%s\",\"temperature\":%.1f,\"humidity\":%.1f,"
        "\"light_adc\":%d,\"light_digital\":%d,"
        "\"gas_adc\":%d,\"gas_digital\":%d,\"gas_level\":%d,"
        "\"radar\":%d,\"manual\":%d,\"sos\":%d}",
        MQTT_CLIENT_ID, data->temperature, data->humidity,
        data->light_adc, data->light_digital,
        data->gas_adc, data->gas_digital, data->gas_level,
        data->radar_presence, g_manual_mode, g_sos_active);

    int ret = esp_mqtt_client_publish(s_mqtt_client, MQTT_TOPIC, json, len, MQTT_QOS, 0);
    if (ret < 0) {
        ESP_LOGW(TAG, "发布失败: %d", ret);
    }
}

void mqtt_publish_event(const char *event, int value)
{
    if (!s_mqtt_connected) return;

    char json[128];
    int len = snprintf(json, sizeof(json),
        "{\"device_id\":\"%s\",\"event\":\"%s\",\"value\":%d}",
        MQTT_CLIENT_ID, event, value);

    esp_mqtt_client_publish(s_mqtt_client, MQTT_TOPIC, json, len, MQTT_QOS, 0);
}

void mqtt_publish_alarm(const char *alarm_type, float temp, float humidity)
{
    if (!s_mqtt_connected) return;

    char json[256];
    int len = snprintf(json, sizeof(json),
        "{\"device_id\":\"%s\",\"alarm_type\":\"%s\",\"temperature\":%.1f,\"humidity\":%.1f,\"timestamp\":%ld}",
        MQTT_CLIENT_ID, alarm_type, temp, humidity, (long)(esp_timer_get_time() / 1000));

    int ret = esp_mqtt_client_publish(s_mqtt_client, MQTT_TOPIC_ALARM, json, len, MQTT_QOS, 0);
    if (ret >= 0) {
        ESP_LOGI(TAG, "🚨 告警已推送: %s (温度=%.1f°C, 湿度=%.1f%%)", alarm_type, temp, humidity);
    } else {
        ESP_LOGW(TAG, "告警推送失败: %d", ret);
    }
}

void mqtt_publish_sos(int active)
{
    if (!s_mqtt_connected) return;

    char json[128];
    int len = snprintf(json, sizeof(json),
        "{\"device_id\":\"%s\",\"sos\":%d,\"event\":\"%s\",\"timestamp\":%ld}",
        MQTT_CLIENT_ID, active, active ? "sos_triggered" : "sos_cleared", (long)(esp_timer_get_time() / 1000));

    int ret = esp_mqtt_client_publish(s_mqtt_client, MQTT_TOPIC_SOS, json, len, MQTT_QOS, 0);
    if (ret >= 0) {
        ESP_LOGI(TAG, "🚨 SOS消息已推送: %s", active ? "触发" : "解除");
    } else {
        ESP_LOGW(TAG, "SOS推送失败: %d", ret);
    }
}

void mqtt_publish_people(int detected)
{
    if (!s_mqtt_connected) return;

    char json[128];
    int len = snprintf(json, sizeof(json),
        "{\"device_id\":\"%s\",\"people\":%d,\"event\":\"%s\",\"timestamp\":%ld}",
        MQTT_CLIENT_ID, detected, detected ? "person_detected" : "no_person", (long)(esp_timer_get_time() / 1000));

    int ret = esp_mqtt_client_publish(s_mqtt_client, MQTT_TOPIC_PEOPLE, json, len, MQTT_QOS, 0);
    if (ret >= 0) {
        ESP_LOGI(TAG, "👤 人体感应消息已推送: %s", detected ? "有人" : "无人");
    } else {
        ESP_LOGW(TAG, "人体感应推送失败: %d", ret);
    }
}

void mqtt_task(void *arg)
{
    char uri[64];
    snprintf(uri, sizeof(uri), "mqtt://%s:%d", MQTT_BROKER_ADDR, MQTT_BROKER_PORT);

    esp_mqtt_client_config_t mqtt_cfg = {
        .broker.address.uri = uri,
        .credentials.client_id = MQTT_CLIENT_ID,
        .session.keepalive = MQTT_KEEPALIVE,
    };

    s_mqtt_client = esp_mqtt_client_init(&mqtt_cfg);
    esp_mqtt_client_register_event(s_mqtt_client, ESP_EVENT_ANY_ID, mqtt_event_handler, NULL);
    esp_mqtt_client_start(s_mqtt_client);

    ESP_LOGI(TAG, "MQTT 任务启动, 连接: %s:%d", MQTT_BROKER_ADDR, MQTT_BROKER_PORT);

    while (1) {
        if (!s_mqtt_connected) {
            vTaskDelay(pdMS_TO_TICKS(2000));
        } else {
            vTaskDelay(pdMS_TO_TICKS(1000));
        }
    }

    esp_mqtt_client_stop(s_mqtt_client);
    esp_mqtt_client_destroy(s_mqtt_client);
    vTaskDelete(NULL);
}