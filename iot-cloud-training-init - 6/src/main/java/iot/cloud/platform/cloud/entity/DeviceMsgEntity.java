package iot.cloud.platform.cloud.entity;

import java.util.Date;

public class DeviceMsgEntity {
  private long id;
  private String iotId;
  private String tag;
  private String msg;
  private Date createTime;
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

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

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

}
