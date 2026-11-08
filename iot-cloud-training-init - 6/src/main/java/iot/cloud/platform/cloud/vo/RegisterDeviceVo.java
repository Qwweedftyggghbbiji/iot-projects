package iot.cloud.platform.cloud.vo;

public class RegisterDeviceVo {
  private String devName;
  private String devType;
  private String description;

  public String getDevName() {
    return devName;
  }

  public void setDevName(String devName) {
    this.devName = devName;
  }

  public String getDevType() {
    return devType;
  }

  public void setDevType(String devType) {
    this.devType = devType;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
