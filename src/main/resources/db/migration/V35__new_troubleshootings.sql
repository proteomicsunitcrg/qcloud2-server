--
-- Table structure for table `troubleshooting`
--

drop table `annotation_actions`;
drop table `annotation_causes`;
drop table `annotation_problems`;


DROP TABLE IF EXISTS `troubleshooting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `troubleshooting` (
  `id` bigint NOT NULL,
  `api_key` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `qccv` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `parent_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pyb0rgsetb3b6gsiqwag2jc98` (`api_key`),
  UNIQUE KEY `UK_qs1k7yaboj8q9q72iuylnpf40` (`qccv`),
  KEY `FKk9233gmhdhncrd8d0kc4c3aqi` (`parent_id`),
  CONSTRAINT `FKk9233gmhdhncrd8d0kc4c3aqi` FOREIGN KEY (`parent_id`) REFERENCES `troubleshooting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annotation` (
  `id` bigint NOT NULL,
  `annotation_date` datetime(6) DEFAULT NULL,
  `api_key` binary(16) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lab_system_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rsuvoe92l9tyqeii9i2bisdtx` (`api_key`),
  KEY `FK3aon3k7pmcfihdijuj19b7t24` (`lab_system_id`),
  KEY `FK13ofl438fghaxt3lpqwl2sc8j` (`user_id`),
  CONSTRAINT `FK13ofl438fghaxt3lpqwl2sc8j` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK3aon3k7pmcfihdijuj19b7t24` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `annotation_troubleshootings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `annotation_troubleshootings` (
  `annotation_id` bigint NOT NULL,
  `troubleshootings_id` bigint NOT NULL,
  KEY `FKd06lxn86slo1yocscvh0iafab` (`troubleshootings_id`),
  KEY `FKqaetgce0wnwmxq1mbdljsodrv` (`annotation_id`),
  CONSTRAINT `FKd06lxn86slo1yocscvh0iafab` FOREIGN KEY (`troubleshootings_id`) REFERENCES `troubleshooting` (`id`),
  CONSTRAINT `FKqaetgce0wnwmxq1mbdljsodrv` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;