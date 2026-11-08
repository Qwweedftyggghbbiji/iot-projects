package iot.cloud.platform.thermometer.service;

public interface EmojiService {
  /**
   * 更新表情符号
   * @param name 表情名称
   * @param face 表情符号
   * @return
   */
  boolean updateEmojiFace(String name,String face);
}
