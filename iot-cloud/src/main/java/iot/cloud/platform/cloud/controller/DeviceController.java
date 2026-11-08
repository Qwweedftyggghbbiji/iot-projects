package iot.cloud.platform.cloud.controller;

import iot.cloud.platform.cloud.constants.Const;
import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DeviceController {

  @Autowired
  public HttpSession session;

  @Autowired
  private DeviceService deviceService;

  @RequestMapping("/device_list")
  public String getDeviceList(ModelMap mp){
    String userId=(String)session.getAttribute(Const.SESSION_KEY_USERID);
    List<DeviceEntity> devList=deviceService.getDeviceListByUserId(userId);//TODO:修改这里的代码从数据库读取用户设备。
    mp.addAttribute("devList",devList);
    return "device_list";
  }

}
