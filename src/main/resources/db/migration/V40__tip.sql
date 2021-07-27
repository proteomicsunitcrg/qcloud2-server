DROP TABLE IF EXISTS `tip`;
CREATE TABLE `tip` (
  `id` bigint NOT NULL,
  `display` bit(1) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `show_at` datetime DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `published_twitter` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `tip_increment`;
CREATE TABLE `tip_increment` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
