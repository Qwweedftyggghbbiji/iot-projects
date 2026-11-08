package iot.cloud.platform.thermometer.entity;

import java.util.Date;

public class ConfigEntity {
  private String k;
  private String v;
  private Date uptTime;

  private String remark;

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getK() {
    return k;
  }

  public void setK(String k) {
    this.k = k;
  }

  public String getV() {
    return v;
  }

  public void setV(String v) {
    this.v = v;
  }

  public Date getUptTime() {
    return uptTime;
  }

  public void setUptTime(Date uptTime) {
    this.uptTime = uptTime;
  }
}
