package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.entity.EmojiEntity;
import iot.cloud.platform.thermometer.service.EmojiService;
import iot.cloud.platform.thermometer.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class EmojiController {

  @Autowired
  private EmojiService emojiService;

  /**
   * 更新表情内容
   * @param name 表情名称
   * @param face 表情内容
   * @return 操作结果
   */
  public ResMsg updateEmojiFace(@Valid EmojiEntity emoji){
    ResMsg result=new ResMsg();
    if(emojiService.updateEmojiFace(emoji.getName(),emoji.getFace())){
      result.setErrmsg("表情更新成功");
      result.setErrcode("0");
    }else{
      result.setErrmsg("表情更新失败");
      result.setErrcode("8191");
    }
    return result;
  }

  /**
   * HTTP 接口：更新表情内容
   */
  @PostMapping("/updateEmojiFace")
  @ResponseBody
  public Map<String, Object> updateEmojiFaceHttp(String name, String face) {
    Map<String, Object> result = new HashMap<>();
    try {
      boolean success = emojiService.updateEmojiFace(name, face);
      if (success) {
        result.put("errcode", "0");
        result.put("errmsg", "表情更新成功");
        result.put("data", null);
      } else {
        result.put("errcode", "3001");
        result.put("errmsg", "表情更新失败");
        result.put("data", null);
      }
    } catch (Exception e) {
      result.put("errcode", "3002");
      result.put("errmsg", "表情更新异常：" + e.getMessage());
      result.put("data", null);
    }
    return result;
  }
}
