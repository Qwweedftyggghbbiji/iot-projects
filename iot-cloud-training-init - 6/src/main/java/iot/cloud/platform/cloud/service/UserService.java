package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.UserEntity;

public interface UserService {
  /**
   * 获取用户
   * @param userId 用户ID
   * @return
   */
  UserEntity getUserById(String userId);

  /**
   * 获取用户信息
   * @param idOrName 用户ID或用户姓名
   * @return
   */
  UserEntity getUserByIdOrName(String idOrName);

  /**
   * 校验用户ID和密钥，匹配成功返回true，否则false
   * @param userId
   * @param secret
   * @return
   */
  boolean verifySecret(String userId,String secret);
}
