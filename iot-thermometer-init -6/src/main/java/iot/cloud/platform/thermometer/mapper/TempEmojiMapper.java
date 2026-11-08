package iot.cloud.platform.thermometer.mapper;

import iot.cloud.platform.thermometer.entity.TempEmoji;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TempEmojiMapper {
  // 返回类型改成 TempEmoji，和 XML 里的 resultType 一致
  TempEmoji getFaceByTemp(Integer temp);
}