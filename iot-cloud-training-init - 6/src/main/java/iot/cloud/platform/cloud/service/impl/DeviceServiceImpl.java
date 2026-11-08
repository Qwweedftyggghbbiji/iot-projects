package iot.cloud.platform.cloud.service.impl;

import iot.cloud.platform.cloud.entity.DeviceEntity;
import iot.cloud.platform.cloud.mapper.DeviceMapper;
import iot.cloud.platform.cloud.service.DeviceService;
import iot.cloud.platform.cloud.vo.DeviceVo;
import iot.cloud.platform.cloud.vo.RegisterDeviceVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

  @Autowired
  private DeviceMapper deviceMapper;

  @Override
  public List<DeviceEntity> getDeviceByUserId(String userId) {
    return deviceMapper.getDeviceByUserId(userId);
  }

  @Override
  public List<DeviceVo> getDevicePubInfoByUserId(String userId) {
    List<DeviceEntity> list = getDeviceByUserId(userId);
    List<DeviceVo> result = new ArrayList<>();
    if (list != null && list.size() > 0) {
      DeviceVo dv = null;
      for (DeviceEntity de : list) {
        dv = new DeviceVo();
        dv.setIotId(de.getIotId());
        dv.setDevName(de.getDevName());
        dv.setDevType(de.getDevType());
        dv.setStatus(de.getStatus());
        dv.setDescription(de.getDescription());
        result.add(dv);
      }
    }
    return result;
  }

  @Override
  public DeviceEntity registerDevice(RegisterDeviceVo dev, String userId) {
    if (dev == null || StringUtils.isBlank(userId)) {
      return null;
    }
    // RegisterDeviceVo 没有 status 属性，只复制存在的字段
    DeviceVo dvo = new DeviceVo();
    dvo.setDevName(dev.getDevName());
    dvo.setDevType(dev.getDevType());
    dvo.setDescription(dev.getDescription());
    dvo.setStatus(DeviceEntity.STATUS_ENABLED); // 使用常量 "enabled"
    // 调用createDevice完成注册
    return createDevice(dvo, userId);
  }

  // 补全 getDevice 方法实现
  @Override
  public DeviceEntity getDevice(String userId, String iotId) {
    return deviceMapper.getDevice(userId, iotId);
  }

  @Override
  public DeviceEntity createDevice(DeviceVo dev, String userId) {
    // 1. 参数校验
    if (dev == null || StringUtils.isBlank(userId) || StringUtils.isBlank(dev.getDevName())) {
      return null;
    }

    // 2. 不用IDUtils，直接用RandomStringUtils生成16位iotId（和文档要求一致）
    String iotId = RandomStringUtils.randomAlphanumeric(16);
    // 3. 生成16位随机设备密钥
    String devSecret = RandomStringUtils.randomAlphanumeric(16);

    // 4. 组装实体对象
    DeviceEntity deviceEntity = new DeviceEntity();
    deviceEntity.setUserId(userId);
    deviceEntity.setIotId(iotId);
    deviceEntity.setDevName(dev.getDevName());
    deviceEntity.setDevType(dev.getDevType());
    deviceEntity.setStatus(StringUtils.isBlank(dev.getStatus()) ? DeviceEntity.STATUS_ENABLED : dev.getStatus());
    deviceEntity.setDescription(dev.getDescription());
    deviceEntity.setDevSecret(devSecret);
    deviceEntity.setCreateTime(new Date());

    // 5. 插入数据库（mapper返回boolean）
    boolean isCreated = deviceMapper.createDevice(deviceEntity);
    if (isCreated) {
      // 6. 插入成功，返回刚创建的设备信息
      return deviceEntity; // 直接返回已组装好（包含 iotId, devSecret 等）的实体
    } else {
      return null;
    }
  }

  @Override
  public boolean updateDevice(DeviceVo dev, String userId) {
    DeviceEntity de = new DeviceEntity();
    de.setDevName(dev.getDevName());
    de.setIotId(dev.getIotId());
    de.setDevType(dev.getDevType());
    de.setStatus(dev.getStatus());
    de.setDescription(dev.getDescription());
    de.setUserId(userId);
    return deviceMapper.updateDevice(de);
  }

  // 补全 validSecret 方法实现（对齐接口要求）
  @Override
  public boolean validSecret(String iotId, String secret) {
    DeviceEntity dev = deviceMapper.getDeviceByIotId(iotId);
    return dev != null && StringUtils.equals(secret, dev.getDevSecret());
  }
}