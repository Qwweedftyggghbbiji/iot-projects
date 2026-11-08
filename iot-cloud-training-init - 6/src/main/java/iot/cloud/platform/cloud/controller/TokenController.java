package iot.cloud.platform.cloud.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import iot.cloud.platform.cloud.entity.TokenEntity;
import iot.cloud.platform.cloud.service.TokenService;
import iot.cloud.platform.cloud.service.UserService;
import iot.cloud.platform.cloud.vo.ResMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api("平台访问令牌接口")
public class TokenController {

  @Autowired
  private TokenService tokenService;

  @Autowired
  private UserService userService;

  /**
   * 获取用户访问令牌（开放API）
   * @param userId 用户ID
   * @param secret 用户密钥
   * @return
   */
  @GetMapping("/token")
  @ResponseBody
  @ApiOperation("获取用户访问令牌")
  @ApiImplicitParams ({
    @ApiImplicitParam(name = "user_id",value = "用户ID",required = true,paramType = "query",dataType = "string"),
    @ApiImplicitParam(name = "secret",value = "用户密钥",required = true,paramType = "query",dataType = "string")
  })
  public ResMsg token(@RequestParam("user_id") String userId,@RequestParam("secret") String secret){
    ResMsg msg=new ResMsg();
    if(userId!=null && secret!=null){
      if(userService.verifySecret(userId,secret)){
        TokenEntity token=tokenService.getTokenByUserId(userId);
        if(token!=null) {
          if (token.expired()) {
            //如果令牌过期，重新生成一个
            token=tokenService.generateToken(userId);
          }
          msg.setErrcode("0");
          msg.setErrmsg("获取令牌成功");
          msg.setData(token);
        }else{
          token=tokenService.generateToken(userId);
          msg.setErrcode("0");
          msg.setErrmsg("获取令牌成功");
          msg.setData(token);
        }
      }else{
        msg.setErrcode("1003");
        msg.setErrmsg("用户不存在或secret不匹配");
      }
    }else{
      msg.setErrcode("1004");
      msg.setErrmsg("用户 ID 和 secret 不能为空");
    }
    return msg;
  }
}
