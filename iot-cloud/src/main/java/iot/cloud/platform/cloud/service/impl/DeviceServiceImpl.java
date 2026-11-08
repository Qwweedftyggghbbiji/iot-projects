package iot.cloud.platform.cloud.service.impl;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.mapper.DeviceMapper;
import iot.cloud.platform.cloud.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private DeviceMapper deviceMapper;

  @Override
  public List<DeviceEntity> getDeviceListByUserId(String userId) {
    return deviceMapper.getDeviceByUserId(userId);
  }
}
