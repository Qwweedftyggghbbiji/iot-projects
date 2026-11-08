package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

  @Autowired
  private ConfigService configService;

  /**
   * 首页
   * @param model
   * @return
   */
  @RequestMapping("/")
  public String index(Model model){
    return "index";
  }
}
