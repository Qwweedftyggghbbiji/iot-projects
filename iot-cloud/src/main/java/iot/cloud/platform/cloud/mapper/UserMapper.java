package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
  /**
   * 根据用户ID获取用户信息
   * @param userId
   * @return
   */
  UserEntity getUserById(@Param("userId") String userId);

  /**
   * 根据用户ID或用户名获取用户信息
   * @param idOrName
   * @return
   */
  UserEntity getUserByIdOrName(@Param("idOrName") String idOrName);
}
