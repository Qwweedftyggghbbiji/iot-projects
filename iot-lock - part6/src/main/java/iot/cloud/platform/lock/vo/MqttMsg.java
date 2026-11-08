package iot.cloud.platform.lock.vo;

import lombok.Data;

@Data
public class MqttMsg<T>{
    protected String eventId;
    protected String eventName;
    protected long eventTime;
    protected T data;
}