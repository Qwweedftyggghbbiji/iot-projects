CREATE TABLE `pwd` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '行编号',
  `typ` VARCHAR(10) NOT NULL COMMENT '密码类型：fixed-固定，temp-临时',
  `pwd` VARCHAR(18) NOT NULL COMMENT '密码，纯数字6-18位',
  `status` VARCHAR(10) NOT NULL COMMENT '状态：enabled-启用，disabled-禁用',
  `upt_time` DATETIME NOT NULL COMMENT '更新时间',
  `expired_time` DATETIME DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='智能锁密码表';