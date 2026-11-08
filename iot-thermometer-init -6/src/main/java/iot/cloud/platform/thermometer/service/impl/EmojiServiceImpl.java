package iot.cloud.platform.thermometer.service.impl;

import iot.cloud.platform.thermometer.mapper.EmojiMapper;
import iot.cloud.platform.thermometer.service.EmojiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmojiServiceImpl implements EmojiService {

  @Autowired
  private EmojiMapper emojiMapper;
  @Override
  public boolean updateEmojiFace(String name, String face) {
    return emojiMapper.updateEmojiFace(name,face);
  }
}
