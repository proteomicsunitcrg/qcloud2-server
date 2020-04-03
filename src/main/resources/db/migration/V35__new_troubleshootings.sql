--
-- Table structure for table `troubleshooting`
--

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