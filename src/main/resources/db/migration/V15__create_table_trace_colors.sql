DROP TABLE IF EXISTS `trace_color`;
CREATE TABLE `trace_color` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `main_color` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bfr0gbrbper1su0y9ig2asy7c` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `trace_color_seq`;
CREATE TABLE `trace_color_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `trace_color_seq` VALUES (1);