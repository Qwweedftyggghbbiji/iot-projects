package iot.cloud.platform.cloud.controller;

import iot.cloud.platform.cloud.constants.Const;
import iot.cloud.platform.cloud.entity.UserEntity;
import iot.cloud.platform.cloud.service.UserService;
import iot.cloud.platform.cloud.vo.LoginVo;
import iot.cloud.platform.cloud.vo.RedirectVo;
import iot.cloud.platform.cloud.vo.ResMsg;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

  @Autowired
  private UserService userService;

  @Autowired
  public HttpSession session;

  /**
   * 首页
   * @param mv
   * @return
   */
  @RequestMapping(value = "/")
  public ModelAndView index(ModelAndView mv) {
    return new ModelAndView("redirect:/login", mv.getModel());
  }

  /**
   * 访问登录页面
   * @param mv
   * @return
   */
  @RequestMapping(value = "/login")
  public String login(ModelAndView mv) {
    return "index";
  }

  /**
   * 登录
   * @param vo
   * @return
   */
  @RequestMapping(value = "/signin")
  @ResponseBody
  public ResMsg signIn(@RequestBody LoginVo vo) {
    ResMsg resMsg = new ResMsg();
    if (StringUtils.isNotBlank(vo.getUsername()) && StringUtils.isNotBlank(vo.getPassword())) {
      UserEntity dbuser = userService.getUserByIdOrName(vo.getUsername());
      if (dbuser != null) {
        //加密明文
        String pwdEnc=vo.getPassword();
        if (pwdEnc.equals(dbuser.getPwd())) {
          resMsg.setErrcode("0");
          resMsg.setErrmsg("登录成功");
          resMsg.setData(new RedirectVo("/device_list?random="+ RandomStringUtils.randomAlphanumeric(10)));
          session.setAttribute(Const.SESSION_KEY_USER, dbuser);
          session.setAttribute(Const.SESSION_KEY_USERID, dbuser.getUserId());
        } else {
          resMsg.setErrcode("2001");
          resMsg.setErrmsg("用户名或密码不正确");
        }
      } else {
        resMsg.setErrcode("2003");
        resMsg.setErrmsg("用户不存在");
      }
    } else {
      resMsg.setErrcode("2002");
      resMsg.setErrmsg("用户名密码均不能为空");
    }
    return resMsg;
  }

  /**
   * 登出
   * @param map
   * @return
   */
  @RequestMapping(value = "/logout")
  public String logout(ModelMap map) {
    session.removeAttribute(Const.SESSION_KEY_USER);
    session.removeAttribute(Const.SESSION_KEY_USERID);
    map.put("msg", "成功退出，点击跳转链接跳转到首页");
    map.put("redirect","/");
    map.put("title","用户退出");
    return "msg";
  }
}
