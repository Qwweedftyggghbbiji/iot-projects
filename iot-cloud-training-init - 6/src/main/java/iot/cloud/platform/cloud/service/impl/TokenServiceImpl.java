package iot.cloud.platform.cloud.service.impl;

import iot.cloud.platform.cloud.entity.TokenEntity;
import iot.cloud.platform.cloud.mapper.TokenMapper;
import iot.cloud.platform.cloud.mapper.UserMapper;
import iot.cloud.platform.cloud.service.TokenService;
import iot.cloud.platform.cloud.utils.IDUtils;
import iot.cloud.platform.cloud.utils.MsgDigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

  private long tokenValidPeriod=1000*60*60*8;

  @Autowired
  private TokenMapper tokenMapper;

  @Autowired
  private UserMapper userService;

  @Override
  public boolean isValidToken(String token) {
    TokenEntity te=getToken(token);
    return te!=null && !te.expired();
  }

  @Override
  public TokenEntity getTokenByUserId(String userId) {
    return tokenMapper.getTokenByUserId(userId);
  }

  @Override
  public TokenEntity generateToken(String userId) {
    TokenEntity token=new TokenEntity();
    long expiredTs=new Date().getTime()+tokenValidPeriod;
    Date expiredTime=new Date(expiredTs);
    token.setUserId(userId);
    token.setExpiredTs(expiredTs);
    token.setExpiredTime(expiredTime);
    token.setToken(IDUtils.genUniqueId());
    if(getTokenByUserId(userId)==null) {
      tokenMapper.saveToken(token);
    }else{
      tokenMapper.updateToken(token);
    }
    return token;
  }

  @Override
  public TokenEntity getToken(String token) {
    return tokenMapper.getToken(token);
  }


}
