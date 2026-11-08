package iot.cloud.platform.cloud.vo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Map;

public class DeviceMsgVo {
  @Size(min = 1, max = 32,message = "消息标签限制32个字符")
  @NotBlank(message = "设备状态不能为空")
  private String iotId;
  @Size(min = 1, max = 256,message = "消息标签限制256个字符")
  @NotBlank (message = "消息标签不能为空")
  private String tag;

  @NotNull(message = "设备状态不能为空")
  private Map<String,Object> msg;

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

  public Map<String, Object> getMsg() {
    return msg;
  }

  public void setMsg(Map<String, Object> msg) {
    this.msg = msg;
  }
}
