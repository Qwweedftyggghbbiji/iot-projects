package iot.cloud.platform.thermometer.service.impl;

import iot.cloud.platform.thermometer.entity.TempEmoji;
import iot.cloud.platform.thermometer.mapper.TempEmojiMapper;
import iot.cloud.platform.thermometer.service.TempEmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TempEmojiServiceImpl implements TempEmojiService {

  @Autowired
  private TempEmojiMapper tempEmojiMapper;

  @Override
  public String getFaceByTemp(Integer temp) {
    // 用 TempEmoji 对象接收 Mapper 返回的结果
    TempEmoji tempEmoji = tempEmojiMapper.getFaceByTemp(temp);
    if (tempEmoji != null) {
      // 从对象里取出表情名称返回
      return tempEmoji.getEmojiName();
    }
    return "(⊙﹏⊙)"; // 默认表情
  }
}