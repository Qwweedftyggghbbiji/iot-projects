package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
  UserEntity getUserById(@Param("userId") String userId);
  UserEntity getUserByIdOrName(@Param("idOrName") String idOrName);
}
