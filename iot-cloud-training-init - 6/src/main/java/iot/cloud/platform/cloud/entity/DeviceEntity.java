package iot.cloud.platform.cloud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class DeviceEntity {
  public static final String STATUS_ENABLED="enabled";
  public static final String STATUS_DISABLED="disabled";
  @JsonIgnore
  private long id;
  private String iotId;
  private String devName;
  private String userId;
  private String devType;
  private String status;
  private String devSecret;
  private String description;
  private Date createTime;

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

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

  public String getDevName() {
    return devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getDevType() {
    return devType;
  }

  public void setDevType(String devType) {
    this.devType = devType;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getDevSecret() {
    return devSecret;
  }

  public void setDevSecret(String devSecret) {
    this.devSecret = devSecret;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
