package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.vo.DeviceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
  List<DeviceEntity> getDeviceByUserId(@Param("userId") String userId);

  DeviceEntity getDevice(@Param("userId")String userId,@Param("iotId")String iotId);

  boolean createDevice(DeviceEntity dev);
  boolean updateDevice(DeviceEntity dev);
}
