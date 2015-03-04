CREATE TABLE `log`.`log` (
  `jvm` VARCHAR(45) NULL,
  `day` INT(2) NULL,
  `month` INT(2) NULL,
  `year` INT(4) NULL,
  `root` VARCHAR(45) NULL,
  `filename` VARCHAR(999) NULL,
  `path` VARCHAR(1999) NULL,
  `status` INT(3) NULL,
  `size` BIGINT(10) NULL,
  `id` BIGINT(10) NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(3) NULL,
  PRIMARY KEY (id));