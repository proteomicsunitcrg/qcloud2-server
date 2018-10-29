

DROP TABLE IF EXISTS `chart_params`;

update `user` set `user_default_view_id` = null;
delete from `user_default_view`;
delete from `view_display`;
delete from `view`;

DROP TABLE IF EXISTS `view_display`;

DROP TABLE IF EXISTS `chart`;
CREATE TABLE `chart` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `is_normalized` tinyint(1) DEFAULT '0',
  `is_threshold` tinyint(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `cv_id` bigint(20) NOT NULL,
  `sample_type_id` bigint(20) NOT NULL,
  `param_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jr8waxdp8eul46fw1hdtmq8vb` (`api_key`),
  KEY `FKnmcqvc5ht31b55xrk5jk1sbjo` (`cv_id`),
  KEY `FKdieh4ukwpaig66w1g3qakboci` (`sample_type_id`),
  KEY `FKdaeh45kwpgig66w1g3qakbpar` (`param_id`),
  CONSTRAINT `FKdieh4ukwpaig66w1g3qakboci` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKnmcqvc5ht31b55xrk5jk1sbjo` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`),
  CONSTRAINT `FKdaeh45kwpgig66w1g3qakbpar` FOREIGN KEY (`param_id`) REFERENCES `param` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `chart_params` (
  `chart_id` bigint(20) NOT NULL,
  `context_source_id` bigint(20) NOT NULL,
  PRIMARY KEY (`chart_id`,`context_source_id`),
  KEY `FK45wq42vv53fkf7vf08ixv6rof` (`context_source_id`),
  CONSTRAINT `FK45wq42vv53fkf7vf08ixv6rof` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`),
  CONSTRAINT `FK8l6bpqlr3f7ngyr3d5pm4pqk9` FOREIGN KEY (`chart_id`) REFERENCES `chart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `view_display` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `col` int(11) NOT NULL,
  `row` int(11) NOT NULL,
  `chart_id` bigint(20) DEFAULT NULL,
  `view_id` bigint(20) NOT NULL,
  `lab_system_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqllm025dtc51xf38qdr5je52q` (`chart_id`),
  KEY `FKb1gu01sld2cm281oa88pjq9ia` (`view_id`),
  KEY `FK4b1psw98dbkbkvwfsvuoy4rem` (`lab_system_id`),
  CONSTRAINT `FK4b1psw98dbkbkvwfsvuoy4rem` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKb1gu01sld2cm281oa88pjq9ia` FOREIGN KEY (`view_id`) REFERENCES `view` (`id`),
  CONSTRAINT `FKqllm025dtc51xf38qdr5je52q` FOREIGN KEY (`chart_id`) REFERENCES `chart` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
