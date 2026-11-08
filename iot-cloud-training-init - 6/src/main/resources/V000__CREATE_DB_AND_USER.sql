CREATE DATABASE iotcloudtr DEFAULT character set UTF8mb4 collate utf8mb4_bin;
CREATE user 'iotcloudtr'@'localhost' IDENTIFIED BY 'xwnbHyT6KM@t';
CREATE user 'iotcloudtr'@'%' IDENTIFIED BY 'xwnbHyT6KM@t';
GRANT ALL ON iotcloudtr.* TO 'iotcloudtr'@'localhost';
GRANT ALL ON iotcloudtr.* TO 'iotcloudtr'@'%';