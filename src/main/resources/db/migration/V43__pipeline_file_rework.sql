--
-- Rework `pipeline_file`: it was created years ago to track the full
-- received/processing/processed/error lifecycle of a file, but was never
-- wired to any code. Table is empty in production, so it's safe to redefine
-- rather than ALTER.
--

DROP TABLE IF EXISTS `pipeline_file`;
DROP TABLE IF EXISTS `pipeline_file_seq`;

CREATE TABLE `pipeline_file` (
  `id` bigint(20) NOT NULL,
  `checksum` varchar(255) NOT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `labsystem_id` bigint(20) DEFAULT NULL,
  `sample_type_id` bigint(20) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `received_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `sample` varchar(255) DEFAULT NULL,
  `qc_code` varchar(50) DEFAULT NULL,
  `acquisition_date` datetime DEFAULT NULL,
  `instrument_uuid` varchar(64) DEFAULT NULL,
  `database_name` varchar(255) DEFAULT NULL,
  `size_mb` double DEFAULT NULL,
  `error_reason` longtext,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pipeline_file_checksum` (`checksum`),
  KEY `FK_pipeline_file_labsystem` (`labsystem_id`),
  KEY `FK_pipeline_file_sample_type` (`sample_type_id`),
  CONSTRAINT `FK_pipeline_file_labsystem` FOREIGN KEY (`labsystem_id`) REFERENCES `labsystem` (`ID`),
  CONSTRAINT `FK_pipeline_file_sample_type` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `pipeline_file_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `pipeline_file_seq` VALUES (1);
