package iot.cloud.platform.thermometer.service.impl;

import iot.cloud.platform.thermometer.config.Const;
import iot.cloud.platform.thermometer.entity.ConfigEntity;
import iot.cloud.platform.thermometer.mapper.ConfigMapper;
import iot.cloud.platform.thermometer.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {

  @Autowired
  private ConfigMapper configMapper;

  @Override
  public String getV(String k) {
    ConfigEntity config=configMapper.getConfigByK(k);
    if(config!=null){
      return config.getV();
    }else{
      return null;
    }
  }

  @Override
  public boolean saveConfigs(Map<String,String> map){
    return configMapper.saveConfigs(map)>0;
  }

  @Override
  public String getConfigValue(String key) {
    // 兼容实验步骤的命名，同时支持实验步骤的 key 和当前项目的 key
    if ("iot_id".equals(key)) {
      return getV(Const.CONFIG_K_IOTID);
    } else if ("dev_secret".equals(key)) {
      return getV(Const.CONFIG_K_DEVSECRET);
    }
    return getV(key);
  }

  @Override
  public void saveConfig(String key, String value) {
    Map<String, String> map = new HashMap<>();
    map.put(key, value);
    saveConfigs(map);
  }
}
