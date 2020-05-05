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
INSERT INTO `troubleshooting` VALUES (1, 'trouble1','Replacement or cleaning of parts of the LC has been done','LC Maintenance','TS:0000034','ACTION',NULL),(2,'trouble2','Replacement or cleaning of parts of the MS has been done','MS maintenance','TS:0000035','ACTION',NULL),(3,'trouble3','Others','Others (action)','Others','ACTION',NULL);
INSERT INTO `troubleshooting` VALUES (36,'trouble36','The signal in MS1 is different than expected','MS1 problems','TS:0000066','PROBLEM',NULL), (42,'trouble42','The signal in MS2 is different than expected','MS2 problems','TS:0000067','PROBLEM',NULL),(52,'trouble52','The ion source is not working properly','Source Problems','TS:0000076','PROBLEM',NULL), (54,'trouble54','Others','Others (problem)','Other problem','PROBLEM',NULL), (57,'trouble57','An issue is detected in the chromatography','Chromatographic problems','TS:0000069','PROBLEM',NULL);
INSERT INTO `troubleshooting` VALUES (4,'trouble4','The chromatographic column has been changed','Column changed','TS:0000028','ACTION',1),(5,'trouble5','The chromatographic pre-column has been changed','Pre-column changed','TS:0000029','ACTION',1),(6,'trouble6','The rotor-seal has been changed','Rotor-seal changed','TS:0000032','ACTION',1),(7,'trouble7','The piston seal has been changed','Piston seal changed','TS:0000033','ACTION',1),(8,'trouble8','The sample loop has been changed','Sample loop changed','TS:0000045','ACTION',1),(9,'trouble9','The autosampler needle has been changed','Sample needle changed','TS:0000046','ACTION',1),(10,'trouble10','The capillar that goes from Valve S to the column assembly s inlet has been changed','Column-out changed','TS:0000047','ACTION',1),(11,'trouble11','The capillar that goes from the column assemblys inlet to valve W has been changed','Waste-in changed','TS:0000048','ACTION',1),(12,'trouble12','\"The chromatographic path of the LC system has been cleaned\"','Chromatographic path cleaned','TS:0000049','ACTION',1),(13,'trouble13','The chromatographic column has been cleaned','Column cleaned','TS:0000050','ACTION',12),(14,'trouble14','The injection loop has been cleaned','Loop cleaned','TS:0000051','ACTION',12),(15,'trouble15','The chromatographic solvents have been replaced, refilled or emptied','Solvents mainteinance','TS:0000052','ACTION',1),(16,'trouble16','Solvent S has been filled','Solvent S Filled','TS:0000053','ACTION',15),(17,'trouble17','Solvent S has been changed','Solvent S Changed','TS:0000054','ACTION',15),(18,'trouble18','Solvent A has been filled','Solvent A Filled','TS:0000055','ACTION',15),(19,'trouble19','Solvent A has been Changed','Solvent A Changed','TS:0000056','ACTION',15),(20,'trouble20','Solvent B has been Filled','Solvent B Filled','TS:0000057','ACTION',15),(21,'trouble21','Solvent B has been changed','Solvent B Changed','TS:0000058','ACTION',15),(22,'trouble22','Solvent W has been emptied','Solvent W (empty)','TS:0000059','ACTION',15),(23,'trouble23','\"The vial with the QC sample has been replaced with a new one\"','QC aliquote changed','TS:0000037','ACTION',1),(24,'trouble24','The vial with the QC 01 sample has been replaced with a new one','QC01 aliquote changed','TS:0000061','ACTION',23),(25,'trouble25','The vial with the QC02 sample has been replaced with a new one','QC02 aliquote changed','TS:0000062','ACTION',23),(26,'trouble26','\"The vial with the QC sample has been replaced with a new one\"','QC03 aliquote changed','TS:0000063','ACTION',23),(27,'trouble27','The mass spectrometer has been cleaned','MS cleaned','TS:0000030','ACTION',2),(28,'trouble28','The ion source has been cleaned','Ion source cleaning','TS:0000043','ACTION',27),(29,'trouble29','The full ion path of the instrument has been cleaned','MS full cleaning','TS:0000044','ACTION',27),(30,'trouble30','The mass spectrometer has been calibrated','MS calibration ','TS:0000038','ACTION',2),(31,'trouble31','The accuracy of the mass measurements has been checked','Check mass','TS:0000064','ACTION',30),(32,'trouble32','The MS instrument has been fully calibrated','Full calibration','TS:0000065','ACTION',30),(33,'trouble33','The ion transfer tube has been changed','Ion transfer tube changed','TS:0000031','ACTION',2),(34,'trouble34','Service engineers have come to check the instrument','Service visit','TS:0000039','ACTION',3),(35,'trouble35','The shape, intensity or retention times of the chromatographic profile do not correspond with the expected','Bad chromatography','TS:0000020','PROBLEM',57),(37,'trouble37','The intensity of the signal in MS1 is lower than expected','Decrease of MS1 signal','TS:0000003','PROBLEM',36),(38,'trouble38','The intensity of the signal in MS1 is sometimes lower than expected and sometimes inside the thresholds of acceptance.','MS1 signal fluctuation','TS:0000011','PROBLEM',36),(39,'trouble39','The intensity of the signal in MS1 for complex samples is lower than expected only in the central part of the chromatogram','Loss of MS1 signal in the central part of the chromatogram','TS:0000014','PROBLEM',36),(40,'trouble40','There is no signal in MS1','No signal in MS1','TS:0000019','PROBLEM',36),(41,'trouble41','The mass accuracy of the measured peptides is out of specifications','Uncalibrated masses','TS:0000022','PROBLEM',36),(43,'trouble43','The intensity of the signal in MS2 is lower than expected','Decrease of MS2 signal','TS:0000040','PROBLEM',42),(44,'trouble44','\"The pressure of the chromatographic system exceeds the maximum set value\"','Overpressure in the chromatographic system','TS:0000041','PROBLEM',57),(45,'trouble45','Sample injection process get stacked in the loading sample step','Sample injection step does not finish','TS:0000016','PROBLEM',57),(46,'trouble46','Contamination which causes sample peaks to re-appear in later runs','Carry over','TS:0000070','PROBLEM',57),(47,'trouble47','Part of the chromatographic part ic clogged','Clogged paths','TS:0000071','PROBLEM',57),(48,'trouble48','Chromatographic column is clogged','Column clogged','TS:0000072','PROBLEM',47),(49,'trouble49','The column-out tube is clogged','Column-out clogged','TS:0000073','PROBLEM',47),(50,'trouble50','The waste-in tube is clogged','Waste-in clogged','TS:0000074','PROBLEM',47),(51,'trouble51','The injection loop is clogged','Loop clogged','TS:0000075','PROBLEM',47),(53,'trouble53','The sample is not properly sprayed in the ion source','Spray issues','TS:0000042','PROBLEM',52),(55,'trouble55','The problem has been solved and the instrument is ready to use','Problem solved','TS:0000027','PROBLEM',54),(56,'trouble56','The acquisition computer cannot connect with the instrument','Communication error','TS:0000023','PROBLEM',54);
