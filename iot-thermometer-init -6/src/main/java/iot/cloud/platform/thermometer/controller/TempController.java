package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.config.Const;
import iot.cloud.platform.thermometer.service.ConfigService;
import iot.cloud.platform.thermometer.service.HttpService;
import iot.cloud.platform.thermometer.service.TempEmojiService;
import iot.cloud.platform.thermometer.vo.DeviceMsgVo;
import iot.cloud.platform.thermometer.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class TempController {

  @Autowired
  private TempEmojiService tempEmojiService;

  @Autowired
  private ConfigService configService;

  @RequestMapping("/showTempEmoji")
  @ResponseBody
  public Map<String, Object> showTempEmoji(Integer temp) {
    Map<String, Object> result = new HashMap<>();
    try {
      String emoji = tempEmojiService.getFaceByTemp(temp);

      Map<String, Object> data = new HashMap<>();
      data.put("temp", temp);
      data.put("emoji", emoji);

      result.put("errcode", "0");
      result.put("errmsg", "更新温度成功");
      result.put("data", data);
    } catch (Exception e) {
      result.put("errcode", "1");
      result.put("errmsg", "更新温度失败：" + e.getMessage());
    }
    return result;
  }

  /**
   * 发送温度消息到云平台（支持 GET 和 POST，兼容所有功能）
   * @param temp
   * @return
   */
  @RequestMapping("/sendDeviceMsg")
  @ResponseBody
  public Map<String, Object> sendDeviceMsg(Integer temp) {
    Map<String, Object> result = new HashMap<>();
    HttpService httpService = HttpService.getInstance();

    try {
      // 1. 从配置表获取iotId和devSecret
      String iotId = configService.getConfigValue("iot_id");
      String devSecret = configService.getConfigValue("dev_secret");

      if (iotId == null || devSecret == null) {
        result.put("errcode", "8002");
        result.put("errmsg", "请先注册设备");
        return result;
      }

      // 2. 构造消息内容
      Map<String, Object> msg = new HashMap<>();
      msg.put("temp", temp);
      msg.put("time", System.currentTimeMillis());

      // 3. 构造完整的设备消息
      Map<String, Object> deviceMsg = new HashMap<>();
      deviceMsg.put("iotId", iotId);
      deviceMsg.put("msg", msg);
      deviceMsg.put("tag", "temp");

      // 4. 调用云平台API发送消息
      Call<ResMsg> call = httpService.sendDeviceMsg(iotId, devSecret, deviceMsg);
      Response<ResMsg> response = call.execute();
      ResMsg resMsg = response.body();

      if ("0".equals(resMsg.getErrcode())) {
        result.put("errcode", "0");
        result.put("errmsg", "发送成功");
        // 为前端补全 data 字段
        Map<String, Object> data = new HashMap<>();
        data.put("temp", temp);
        data.put("emoji", tempEmojiService.getFaceByTemp(temp));
        result.put("data", data);
      } else {
        result.put("errcode", resMsg.getErrcode());
        result.put("errmsg", resMsg.getErrmsg());
      }
    } catch (IOException e) {
      result.put("errcode", "8001");
      result.put("errmsg", "无法连接云平台服务器");
    }
    return result;
  }
}