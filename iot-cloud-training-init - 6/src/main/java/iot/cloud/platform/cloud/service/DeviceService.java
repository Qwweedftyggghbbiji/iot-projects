package iot.cloud.platform.cloud.service;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.vo.DeviceVo;
import iot.cloud.platform.cloud.vo.RegisterDeviceVo;

import java.util.List;

public interface DeviceService {
  /**
   * 获取用户设备所有信息
   * @param userId 用户ID
   * @return
   */
  List<DeviceEntity> getDeviceByUserId(String userId);

  /**
   * 获取用户公开设备列表信息
   * @param userId 用户ID
   * @return
   */
  List<DeviceVo> getDevicePubInfoByUserId(String userId);

  /**
   * 注册设备
   * @param dev 设备信息
   * @param userId 用户ID
   * @return
   */
  DeviceEntity registerDevice(RegisterDeviceVo dev,String userId);

  /**
   * 获取设备
   * @param userId 用户ID
   * @param iotId 设备物联网平台ID
   * @return
   */
  DeviceEntity getDevice(String userId,String iotId);

  /**
   * 创建设备
   * @param dev 设备信息
   * @param userId 用户ID
   * @return
   */
  DeviceEntity createDevice(DeviceVo dev,String userId);

  /**
   * 更新设备信息
   * @param dev 设备信息
   * @param userId 用户ID
   * @return
   */
  boolean updateDevice(DeviceVo dev,String userId);

  /**
   * 验证设备密钥
   * @param iotId 设备物联网平台ID
   * @param secret 密钥
   * @return
   */
  boolean validSecret(String iotId,String secret);
}
