package iot.cloud.platform.cloud.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class DeviceVo {
  private String iotId;

  @Size(min = 1, max = 32,message = "设备名长度限制32个字符")
  @NotBlank (message = "设备名不能为空")
  private String devName;

  @Size(min = 1, max = 32,message = "设备类型长度限制32个字符")
  @NotBlank (message = "设备类型不能为空")
  private String devType;

  @Size(min = 0, max = 256,message = "设备描述长度限制32个字符")
  private String description;

  @Size(min = 1, max = 16,message = "设备状态限制32个字符")
  @NotBlank (message = "设备状态不能为空")
  private String status;

  public String getIotId() {
    return iotId;
  }

  public void setIotId(String iotId) {
    this.iotId = iotId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

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
