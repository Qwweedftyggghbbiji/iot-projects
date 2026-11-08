INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('iotId', 'Lock01', now(), '设备物联网平台ID');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devSecret', '', now(), '设备密钥');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('token', '', now(), '用户令牌');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('userId', '', now(), '用户ID');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('userSecret', '', now(), '用户密钥');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devName', '客厅智能锁', now(), '设备名称');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devType', 'lock', now(), '设备类型');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('description', '我的客厅智能锁', now(), '设备描述');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicPost', '', now(), '设备主动推送主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicPostReply', '', now(), '设备主动推送回复主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicReceive', '', now(), '设备被动接收主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicReceiveReply', '', now(), '设备被动接收回复主题');

INSERT into `pwd`(`pwd`,`typ`,`upt_time`,`status`) values('123456','fixed',now(),'enabled');
