package iot.cloud.platform.thermometer.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQTT消息发布者
 */
@Component
@Slf4j
public class MqttProvider {

    @Autowired
    private MqttClient mqttClient;

    /**
     * 发布MQTT消息
     * @param topic 主题
     * @param payload 消息内容
     */
    public void publish(String topic, String payload) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            mqttClient.publish(topic, message);
            log.info("MQTT消息发布成功，主题：{}，内容：{}", topic, payload);
        } catch (Exception e) {
            log.error("MQTT消息发布失败：{}", e.getMessage(), e);
        }
    }

    /**
     * 发布MQTT消息（带QoS级别）
     * @param topic 主题
     * @param payload 消息内容
     * @param qos 服务质量级别（0, 1, 2）
     */
    public void publish(String topic, String payload, int qos) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(qos);
            mqttClient.publish(topic, message);
            log.info("MQTT消息发布成功，主题：{}，QoS：{}，内容：{}", topic, qos, payload);
        } catch (Exception e) {
            log.error("MQTT消息发布失败：{}", e.getMessage(), e);
        }
    }
}
