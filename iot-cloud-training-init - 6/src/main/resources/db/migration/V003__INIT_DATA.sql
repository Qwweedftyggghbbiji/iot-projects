INSERT INTO `user` (`user_id`, `pwd`, `user_name`, `user_secret`) VALUES ('zhangsan', '123456', '张三', '123456');
INSERT INTO `device` (`iot_id`, `dev_name`, `user_id`, `dev_type`, `status`, `dev_secret`, `description`,`create_time`) VALUES ('d123', 'Lock01', 'zhangsan', 'lock', 'enabled', 'jgiofjgdfiogj', '智能锁1号',now());
