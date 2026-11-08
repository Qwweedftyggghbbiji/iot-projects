package iot.cloud.platform.thermometer.vo;

import lombok.Data;

@Data
public class MqttMsg<T>{
  protected String eventId;
  protected String eventName;
  protected long eventTime;
  protected T resMsg;  // 改名为 resMsg 以符合实验要求
}
