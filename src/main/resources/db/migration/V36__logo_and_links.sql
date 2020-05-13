

DROP TABLE IF EXISTS `link`;

CREATE TABLE `link` (
  `id` bigint NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1voekpuq97u80rjorwd65qk42` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `logo`;

CREATE TABLE `logo` (
  `id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `alt` varchar(255) DEFAULT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6qifu55ej2a695vmj57rypy8h` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `link_increment` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE `logo_increment` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `logo_increment` VALUES (1);

INSERT INTO `link_increment` VALUES (1);

INSERT INTO `link` VALUES (1,_binary 'apiKey1\0\0\0\0\0\0\0\0\0','Download','https://www.dropbox.com/s/kotvlfuu5zn8gnz/QCrawler0192.exe'),(2,_binary 'apiKey2\0\0\0\0\0\0\0\0\0','Manual','https://www.dropbox.com/s/cwlf08ofx7u1z2n/Qcrawler_for%20Qcloud_2.1.pdf');

INSERT INTO `logo` VALUES (142,_binary '\0','May the force with u',_binary 'Äó░\▄%ÄMë♦\ÊM\r.☻┴','Star Wars','https://i.imgur.com/9hXWCat.png'),(145,_binary '\0','Happy Sant jordi!',_binary '|Uè~/ GÄî*z½\Þ.ö$','Sant Jordi','https://i.imgur.com/ueoSLUU.png'),(147,_binary '\0','Happy christmas',_binary '♦f\Ô\╩Ð©C´Öè«%S\È\¶,','Xmas LUL','https://i.imgur.com/L0gu7hj.png');