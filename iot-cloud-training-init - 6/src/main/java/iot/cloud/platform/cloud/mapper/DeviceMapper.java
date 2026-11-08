package iot.cloud.platform.cloud.mapper;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.vo.DeviceVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeviceMapper {
  /**
   * 获取用户设备信息
   * @param userId 用户ID
   * @return
   */
  List<DeviceEntity> getDeviceByUserId(@Param("userId") String userId);

  /**
   * 获取单台设备信息
   * @param userId 用户ID
   * @param iotId 物联网云平台IoT ID
   * @return
   */
  DeviceEntity getDevice(@Param("userId")String userId,@Param("iotId")String iotId);

  /**
   * 获取单台设备信息
   * @param iotId 物联网云平台IoT ID
   * @return
   */
  DeviceEntity getDeviceByIotId(@Param("iotId")String iotId);

  /**
   * 创建设备
   * @param dev 设备信息
   * @return
   */
  boolean createDevice(DeviceEntity dev);

  /**
   * 更新设备信息
   * @param dev 设备信息
   * @return
   */
  boolean updateDevice(DeviceEntity dev);
}
