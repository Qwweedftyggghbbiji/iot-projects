package iot.cloud.platform.thermometer.vo;

import lombok.Data;

@Data
public class MqttReceiveMsg<T>{
  protected String eventId;
  protected String eventName;
  protected long eventTime;
  protected T data;  // 接收消息使用 data 字段
}
