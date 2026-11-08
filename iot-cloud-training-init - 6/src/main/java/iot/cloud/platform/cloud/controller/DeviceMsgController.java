package iot.cloud.platform.cloud.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.entity.DeviceMsgEntity;
import iot.cloud.platform.cloud.entity.TokenEntity;
import iot.cloud.platform.cloud.service.DeviceMsgService;
import iot.cloud.platform.cloud.service.DeviceService;
import iot.cloud.platform.cloud.service.TokenService;
import iot.cloud.platform.cloud.vo.DeviceMsgVo;
import iot.cloud.platform.cloud.vo.DeviceVo;
import iot.cloud.platform.cloud.vo.RegisterDeviceVo;
import iot.cloud.platform.cloud.vo.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class DeviceMsgController {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private DeviceMsgService deviceMsgService;

  @Autowired
  private DeviceService deviceService;

  /**
   * 获取设备存储的消息（开放API)
   * @param token 用户令牌
   * @param iotId 设备物联网云平台 IoT ID
   * @param tag 消息类型
   * @return
   */
  @GetMapping("/devicemsg_get")
  @ResponseBody
  @ApiOperation("获取设备存储的消息")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "token",value = "用户Token",required = true,paramType = "query",dataType = "string"),
          @ApiImplicitParam(name = "iotId",value = "设备物联网平台ID",required = true,paramType = "query",dataType = "string"),
          @ApiImplicitParam(name = "tag",value = "消息标签",required = true,paramType = "query",dataType = "string"),
  })
  public Object getDeviceMsgByTag(@RequestParam String token,
                                  @RequestParam String iotId,
                                  @RequestParam String tag){
    ResMsg resMsg=new ResMsg();
    TokenEntity te=tokenService.getToken(token);
    String userId=te.getUserId();
    if(userId!=null) {
      List<DeviceMsgEntity> devMsgList = deviceMsgService.getByTag(iotId,tag);
      resMsg.setErrcode("0");
      resMsg.setErrmsg("Success");
      resMsg.setData(devMsgList);
    }else{
      resMsg.setErrcode("5001");
      resMsg.setErrmsg("令牌无效");
    }
    return resMsg;
  }

  /**
   * 增加设备消息(开放API)
   * @param iotId 设备物联网云平台 IoT ID
   * @param secret 设备密钥
   * @param msg 消息
   * @return
   */
  @PostMapping("/devicemsg_add")
  @ResponseBody
  @ApiOperation("增加设备消息")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "secret",value = "设备密钥",required = true,paramType = "query",dataType = "string"),
          @ApiImplicitParam(name = "iotId",value = "设备物联网平台ID",required = true,paramType = "query",dataType = "string"),
  })
  public Object addDeviceMsg(@RequestParam String iotId,@RequestParam String secret,@Valid @RequestBody @ApiParam(name = "msg",value = "设备消息",required = true) DeviceMsgVo msg){
    ResMsg resMsg=new ResMsg();
    boolean saved = deviceMsgService.save(msg);
    if (saved) {
      resMsg.setErrcode("0");
      resMsg.setErrmsg("发送成功");
      resMsg.setData(null);
    } else {
      resMsg.setErrcode("1001");
      resMsg.setErrmsg("发送失败");
      resMsg.setData(null);
    }
    return resMsg;
  }
}
