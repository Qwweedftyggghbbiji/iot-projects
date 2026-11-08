package iot.cloud.platform.thermometer.vo;

import java.util.Map;

public class DeviceMsgVo {
  private String iotId;
  private String tag;

  private Object msg;

  public String getIotId() {
    return iotId;
  }

  public void setIotId(String iotId) {
    this.iotId = iotId;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Object getMsg() {
    return msg;
  }

  public void setMsg(Object msg) {
    this.msg = msg;
  }
}
