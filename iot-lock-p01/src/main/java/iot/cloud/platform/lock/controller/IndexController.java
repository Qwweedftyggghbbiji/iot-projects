package iot.cloud.platform.lock.controller;

import stu225.entity.ConfigEntity;
import iot.cloud.platform.lock.mapper.ConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  // 1. 注入 ConfigMapper，用于调用数据库查询方法
  @Autowired
  private ConfigMapper configMapper;

  /**
   * 首页
   * @param model
   * @return
   */
  @RequestMapping("/")
  public String index(Model model){
    // 2. 调用 Mapper 的方法，根据键名（例如 "deviceId"）从数据库查询配置
    // 注意：这里的 "deviceId" 是你数据库 config 表中 k 列的具体值，根据实际情况调整
    ConfigEntity config = configMapper.getConfigByK("deviceId");


    if (config != null) {
      model.addAttribute("deviceId", config.getV()); // 假设 ConfigEntity 中存放值的字段名为 v
    } else {
      model.addAttribute("deviceId", "DefaultLock"); // 数据库没查到时的兜底默认值
    }

    return "index";
  }
}


