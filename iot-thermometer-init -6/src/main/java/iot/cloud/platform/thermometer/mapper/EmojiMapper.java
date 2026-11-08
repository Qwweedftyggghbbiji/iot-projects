package iot.cloud.platform.thermometer.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmojiMapper {
  boolean updateEmojiFace(String name, String face);
}
