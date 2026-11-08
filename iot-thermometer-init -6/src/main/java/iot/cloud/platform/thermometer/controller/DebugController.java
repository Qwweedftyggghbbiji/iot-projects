package iot.cloud.platform.thermometer.controller;

import iot.cloud.platform.thermometer.config.Const;
import iot.cloud.platform.thermometer.entity.ConfigEntity;
import iot.cloud.platform.thermometer.mapper.ConfigMapper;
import iot.cloud.platform.thermometer.service.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/debug")
public class DebugController {

  @Autowired
  private ConfigMapper configMapper;

  @Autowired
  private ConfigService configService;

  /**
   * 查看 config 表的所有内容
   */
  @GetMapping("/configs")
  public Map<String, Object> getAllConfigs() {
    log.info("查询 config 表所有配置数据");
    
    // 定义我们关心的键名
    String[] keys = {
        Const.CONFIG_K_IOTID,
        Const.CONFIG_K_DEVSECRET,
        Const.CONFIG_K_TOKEN,
        Const.CONFIG_K_USERID,
        Const.CONFIG_K_USERSECRET,
        Const.CONFIG_K_DEVNAME,
        Const.CONFIG_K_DEVTYPE,
        Const.CONFIG_K_DESCRIPTION
    };

    Map<String, Object> result = new HashMap<>();
    List<Map<String, String>> list = new ArrayList<>();

    for (String key : keys) {
      Map<String, String> item = new HashMap<>();
      item.put("k", key);
      
      ConfigEntity entity = configMapper.getConfigByK(key);
      if (entity != null) {
        item.put("v", entity.getV());
        item.put("remark", entity.getRemark());
        log.info("{} -> {}", key, entity.getV());
      } else {
        item.put("v", "NULL (未找到)");
        item.put("remark", "");
      }
      list.add(item);
    }

    result.put("configs", list);
    
    // 打印使用 HeidiSQL 查询的 SQL 语句
    String querySql = "SELECT id, k, v, upt_time, remark FROM config ORDER BY id;";
    log.info("\n========== 在 HeidiSQL 中执行此 SQL ==========\n{}\n", querySql);
    
    return result;
  }
}
