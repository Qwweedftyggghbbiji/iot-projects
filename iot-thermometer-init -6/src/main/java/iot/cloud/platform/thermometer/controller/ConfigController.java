package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.config.Const;
import iot.cloud.platform.thermometer.service.CloudTokenService;
import iot.cloud.platform.thermometer.service.ConfigService;
import iot.cloud.platform.thermometer.service.HttpService;
import iot.cloud.platform.thermometer.vo.DeviceRegisterVo;
import iot.cloud.platform.thermometer.vo.ResMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ConfigController {

  private HttpService httpService = HttpService.RETROFIT.create(HttpService.class);

  @Autowired
  private ConfigService configService;

  @Autowired
  private CloudTokenService cloudTokenService;

  /**
   * 配置页面
   * @param model
   * @return
   */
  @RequestMapping("/config")
  public String configIndex(Model model) {
    model.addAttribute(Const.CONFIG_K_IOTID, configService.getV(Const.CONFIG_K_IOTID));
    model.addAttribute(Const.CONFIG_K_USERSECRET, configService.getV(Const.CONFIG_K_USERSECRET));
    model.addAttribute(Const.CONFIG_K_USERID, configService.getV(Const.CONFIG_K_USERID));
    model.addAttribute(Const.CONFIG_K_TOKEN, configService.getV(Const.CONFIG_K_TOKEN));
    model.addAttribute(Const.CONFIG_K_DEVSECRET, configService.getV(Const.CONFIG_K_DEVSECRET));
    model.addAttribute(Const.CONFIG_K_DEVNAME, configService.getV(Const.CONFIG_K_DEVNAME));
    model.addAttribute(Const.CONFIG_K_DEVTYPE, configService.getV(Const.CONFIG_K_DEVTYPE));
    model.addAttribute(Const.CONFIG_K_DESCRIPTION, configService.getV(Const.CONFIG_K_DESCRIPTION));
    model.addAttribute(Const.CONFIG_K_DEVTOPICPOST, configService.getV(Const.CONFIG_K_DEVTOPICPOST));
    model.addAttribute(Const.CONFIG_K_DEVTOPICPOSTREPLY, configService.getV(Const.CONFIG_K_DEVTOPICPOSTREPLY));
    model.addAttribute(Const.CONFIG_K_DEVTOPICRECEIVE, configService.getV(Const.CONFIG_K_DEVTOPICRECEIVE));
    model.addAttribute(Const.CONFIG_K_DEVTOPICRECEIVEREPLY, configService.getV(Const.CONFIG_K_DEVTOPICRECEIVEREPLY));
    return "config";
  }

  /**
   * 保存配置
   * @param map
   * @return
   */
  @RequestMapping("/saveConfig")
  @ResponseBody
  public ResMsg saveConfig(@RequestBody Map<String, String> map) {
    ResMsg msg = new ResMsg();
    if (configService.saveConfigs(map)) {
      msg.setErrcode("0");
      msg.setErrmsg("配置保存成功");
    } else {
      msg.setErrcode("9001");
      msg.setErrmsg("配置保存失败");
    }
    return msg;
  }

  /**
   * 获取用户令牌（GET接口，用于前端自动拉取）
   * @return
   */
  @GetMapping("/getUserToken")
  @ResponseBody
  public ResMsg getUserToken() {
    ResMsg result = new ResMsg();
    try {
      String token = cloudTokenService.getCloudToken();
      result.setErrcode("0");
      result.setErrmsg("获取令牌成功");
      Map<String, String> data = new HashMap<>();
      data.put("token", token);
      result.setData(data);
    } catch (Exception e) {
      result.setErrcode("8001");
      result.setErrmsg("获取令牌失败：" + e.getMessage());
      result.setData(null);
    }
    return result;
  }

  /**
   * 调用物联网云平台 API 获取用户令牌
   * @param map
   * @return
   */
  @RequestMapping("/getToken")
  @ResponseBody
  public ResMsg getToken(@RequestBody Map<String, String> map) {
    ResMsg result = new ResMsg();
    result.setErrcode("8004");
    result.setErrmsg("用户ID，用户密钥不能为空");
    //从请求参数中读取 userId 和 userSecret
    String userId = map.get(Const.CONFIG_K_USERID);
    String userSecret = map.get(Const.CONFIG_K_USERSECRET);
    if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userSecret)) {
      try {
        // 调用物联网云平台获取用户令牌的API
        Call<ResMsg> call = httpService.getUserToken(userId, userSecret);
        Response<ResMsg> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
          ResMsg resMsg = response.body();
          if ("0".equals(resMsg.getErrcode())) {
            // 从响应中提取token
            Map<String, Object> dataMap = (Map<String, Object>) resMsg.getData();
            String token = (String) dataMap.get("token");
            // 保存token到config表
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put(Const.CONFIG_K_TOKEN, token);
            tokenMap.put(Const.CONFIG_K_USERID, userId);
            tokenMap.put(Const.CONFIG_K_USERSECRET, userSecret);
            configService.saveConfigs(tokenMap);
            result.setErrcode("0");
            result.setErrmsg("令牌获取并保存成功");
            // 返回token给前端
            Map<String, String> returnData = new HashMap<>();
            returnData.put("token", token);
            result.setData(returnData);
          } else {
            result.setErrcode(resMsg.getErrcode());
            result.setErrmsg(resMsg.getErrmsg());
          }
        } else {
          result.setErrcode("8002");
          result.setErrmsg("获取令牌失败：接口响应异常");
        }
      } catch (IOException e) {
        result.setErrcode("8001");
        result.setErrmsg("无法连接云平台服务器");
      }
    }
    return result;
  }

  /**
   * 调用物联网云平台 API 注册设备，并保存物联网云平台设备IotID和设备密钥
   * @param map
   * @return
   */
  @RequestMapping("/registerDevice")
  @ResponseBody
  public ResMsg registerDevice(@RequestBody Map<String, String> map) {
    ResMsg result = new ResMsg();
    result.setErrcode("8003");
    result.setErrmsg("设备名称，类型，描述，用户令牌不能为空");
    String devName = map.get(Const.CONFIG_K_DEVNAME);
    String devType = map.get(Const.CONFIG_K_DEVTYPE);
    String description = map.get(Const.CONFIG_K_DESCRIPTION);
    String token = map.get(Const.CONFIG_K_TOKEN);
    if (StringUtils.isNotBlank(token)
            && StringUtils.isNotBlank(devName)
            && StringUtils.isNotBlank(devType)
            && StringUtils.isNotBlank(description)
    ) {
      DeviceRegisterVo vo=new DeviceRegisterVo();
      vo.setDescription(description);
      vo.setDevName(devName);
      vo.setDevType(devType);
      try {
        // 调用物联网云平台注册设备 API
        Call<ResMsg> call = httpService.registerDevice(token, vo);
        Response<ResMsg> response = call.execute();
        if (response.isSuccessful() && response.body() != null) {
          ResMsg resMsg = response.body();
          if ("0".equals(resMsg.getErrcode())) {
            // 从响应中提取 iotId 和 devSecret
            Map<String, Object> dataMap = (Map<String, Object>) resMsg.getData();
            String iotId = (String) dataMap.get(Const.CONFIG_K_IOTID);
            String devSecret = (String) dataMap.get(Const.CONFIG_K_DEVSECRET);

            // 保存到配置表
            Map<String, String> deviceMap = new HashMap<>();
            deviceMap.put(Const.CONFIG_K_IOTID, iotId);
            deviceMap.put(Const.CONFIG_K_DEVSECRET, devSecret);
            deviceMap.put(Const.CONFIG_K_DEVNAME, devName);
            deviceMap.put(Const.CONFIG_K_DEVTYPE, devType);
            deviceMap.put(Const.CONFIG_K_DESCRIPTION, description);
            configService.saveConfigs(deviceMap);

            result.setErrcode("0");
            result.setErrmsg("设备注册成功");
            result.setData(dataMap);
          } else {
            result.setErrcode(resMsg.getErrcode());
            result.setErrmsg(resMsg.getErrmsg());
          }
        } else {
          result.setErrcode("8002");
          result.setErrmsg("注册设备失败：接口响应异常");
        }
      } catch (IOException e) {
        result.setErrcode("8001");
        result.setErrmsg("无法连接云平台服务器");
      }
    }
    return result;
  }
}
