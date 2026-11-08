package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.UserEntity;

public interface UserService {
  UserEntity getUserById(String userId);
  UserEntity getUserByIdOrName(String idOrName);
  boolean verifySecret(String userId,String secret);
}
