package iot.cloud.platform.thermometer.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class EmojiEntity {
  private long id;
  @Size(min = 1, max = 256,message = "表情名称限制256个字符")
  @NotBlank (message = "表情名称不能为空")
  private String name;
  @Size(min = 1, max = 100,message = "表情字符限制100个字符")
  @NotBlank(message = "表情字符不能为空")
  private String face;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFace() {
    return face;
  }

  public void setFace(String face) {
    this.face = face;
  }
}
