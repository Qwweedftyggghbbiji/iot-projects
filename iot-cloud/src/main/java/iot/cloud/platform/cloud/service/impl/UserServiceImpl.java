package iot.cloud.platform.cloud.service.impl;

import iot.cloud.platform.cloud.entity.UserEntity;
import iot.cloud.platform.cloud.mapper.UserMapper;
import iot.cloud.platform.cloud.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserEntity getUserById(String userId) {
    return userMapper.getUserById(userId);
  }

  @Override
  public UserEntity getUserByIdOrName(String idOrName) {
    return userMapper.getUserByIdOrName(idOrName);
  }

  @Override
  public boolean verifySecret(String userId, String secret) {
    boolean result=false;
    UserEntity user=getUserById(userId);
    if(user!=null){
      result= StringUtils.equals(user.getUserSecret(),secret);
    }
    return result;
  }
}
