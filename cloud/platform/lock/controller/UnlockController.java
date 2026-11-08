package iot.cloud.platform.lock.controller;

import iot.cloud.platform.lock.vo.ResMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class UnlockController {

  /**
   * 解锁
   * @param secret
   * @return
   */
  @RequestMapping("/unlock")
  @ResponseBody
  public ResMsg unlock(@RequestBody Map<String,String> secret){
    ResMsg result=new ResMsg();
    String pwd=secret.get("pwd");
    if("123456".equals(pwd)){//硬编码的密码
      result.setErrcode("0");
      result.setErrmsg("解锁成功");
    }else{
      result.setErrcode("1");
      result.setErrmsg("密码错误");
    }
    return result;
  }
}
