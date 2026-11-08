CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '行编号，数字自增',
  `user_id` VARCHAR(32) NOT NULL COMMENT '用户ID',
  `pwd` VARCHAR(32) NOT NULL COMMENT '用户密码',
  `user_name` VARCHAR(64) NOT NULL COMMENT '用户姓名',
  `user_secret` VARCHAR(128) NOT NULL COMMENT '用户密钥',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '用户头像路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_user_id` (`user_id`) -- 建议给用户ID加唯一索引
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';