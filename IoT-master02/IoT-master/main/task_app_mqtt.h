#ifndef TASK_APP_MQTT_H
#define TASK_APP_MQTT_H

#include "app_config.h"

#ifdef __cplusplus
extern "C" {
#endif

void mqtt_publish_sensor_data(sensor_data_t *data);
void mqtt_publish_event(const char *event, int value);
void mqtt_publish_alarm(const char *alarm_type, float temp, float humidity);
void mqtt_publish_sos(int active);
void mqtt_publish_people(int detected);
void mqtt_task(void *arg);

#ifdef __cplusplus
}
#endif

#endif /* TASK_APP_MQTT_H */