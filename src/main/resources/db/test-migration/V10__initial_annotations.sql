-- MySQL dump 10.13  Distrib 5.7.24, for Linux (x86_64)
--
-- Host: localhost    Database: qcloud2
-- ------------------------------------------------------
-- Server version	5.7.24-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `annotation`
--

DROP TABLE IF EXISTS `annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `lab_system_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_rsuvoe92l9tyqeii9i2bisdtx` (`api_key`),
  KEY `FK3aon3k7pmcfihdijuj19b7t24` (`lab_system_id`),
  CONSTRAINT `FK3aon3k7pmcfihdijuj19b7t24` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation`
--

LOCK TABLES `annotation` WRITE;
/*!40000 ALTER TABLE `annotation` DISABLE KEYS */;
/*!40000 ALTER TABLE `annotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation_actions`
--

DROP TABLE IF EXISTS `annotation_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_actions` (
  `annotation_id` bigint(20) NOT NULL,
  `actions_id` bigint(20) NOT NULL,
  KEY `FK881jss0tknn3p8l6vfqp0ry07` (`actions_id`),
  KEY `FKk7duf1q79ynn721b384d8jg93` (`annotation_id`),
  CONSTRAINT `FK881jss0tknn3p8l6vfqp0ry07` FOREIGN KEY (`actions_id`) REFERENCES `troubleshooting` (`id`),
  CONSTRAINT `FKk7duf1q79ynn721b384d8jg93` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation_actions`
--

LOCK TABLES `annotation_actions` WRITE;
/*!40000 ALTER TABLE `annotation_actions` DISABLE KEYS */;
/*!40000 ALTER TABLE `annotation_actions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation_causes`
--

DROP TABLE IF EXISTS `annotation_causes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_causes` (
  `annotation_id` bigint(20) NOT NULL,
  `causes_id` bigint(20) NOT NULL,
  KEY `FK8xxa04bokfh812wrqbxc874lc` (`causes_id`),
  KEY `FKbpxnqudig12rmhuyhv2l81d0c` (`annotation_id`),
  CONSTRAINT `FK8xxa04bokfh812wrqbxc874lc` FOREIGN KEY (`causes_id`) REFERENCES `troubleshooting` (`id`),
  CONSTRAINT `FKbpxnqudig12rmhuyhv2l81d0c` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation_causes`
--

LOCK TABLES `annotation_causes` WRITE;
/*!40000 ALTER TABLE `annotation_causes` DISABLE KEYS */;
/*!40000 ALTER TABLE `annotation_causes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation_problems`
--

DROP TABLE IF EXISTS `annotation_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_problems` (
  `annotation_id` bigint(20) NOT NULL,
  `problems_id` bigint(20) NOT NULL,
  KEY `FKkhn6b1qc9sgjlyjo5olr6t91h` (`problems_id`),
  KEY `FK5newarcoq6mnsb63xa1onmoi9` (`annotation_id`),
  CONSTRAINT `FK5newarcoq6mnsb63xa1onmoi9` FOREIGN KEY (`annotation_id`) REFERENCES `annotation` (`id`),
  CONSTRAINT `FKkhn6b1qc9sgjlyjo5olr6t91h` FOREIGN KEY (`problems_id`) REFERENCES `troubleshooting` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation_problems`
--

LOCK TABLES `annotation_problems` WRITE;
/*!40000 ALTER TABLE `annotation_problems` DISABLE KEYS */;
/*!40000 ALTER TABLE `annotation_problems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `annotation_seq`
--

DROP TABLE IF EXISTS `annotation_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `annotation_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `annotation_seq`
--

LOCK TABLES `annotation_seq` WRITE;
/*!40000 ALTER TABLE `annotation_seq` DISABLE KEYS */;
INSERT INTO `annotation_seq` VALUES (1);
/*!40000 ALTER TABLE `annotation_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `troubleshooting`
--

DROP TABLE IF EXISTS `troubleshooting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `troubleshooting` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `qccv` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pyb0rgsetb3b6gsiqwag2jc98` (`api_key`),
  UNIQUE KEY `UK_qs1k7yaboj8q9q72iuylnpf40` (`qccv`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `troubleshooting`
--

LOCK TABLES `troubleshooting` WRITE;
/*!40000 ALTER TABLE `troubleshooting` DISABLE KEYS */;
INSERT INTO `troubleshooting` VALUES ('problem',1,_binary '\æ\Ú\á¬\à\ZK<vÂ¥X\Ùþ',NULL,'Decrease of MS1 signal','TS:0000003'),('problem',2,_binary 'ý\nÐ\ZLÖ¶~\Ù\Åx\Â\×!',NULL,'MS1 signal fluctuation','TS:0000011'),('action',3,_binary 'U\é\à\à|@\î@Éš ¢\Ã',NULL,'Column changed','TS:0000028'),('action',4,_binary 'ûn\n/¢E~ª|Ú\Õ\æ',NULL,'Pre-column changed','TS:0000029');
/*!40000 ALTER TABLE `troubleshooting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `troubleshooting_seq`
--

DROP TABLE IF EXISTS `troubleshooting_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `troubleshooting_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `troubleshooting_seq`
--

LOCK TABLES `troubleshooting_seq` WRITE;
/*!40000 ALTER TABLE `troubleshooting_seq` DISABLE KEYS */;
INSERT INTO `troubleshooting_seq` VALUES (5);
/*!40000 ALTER TABLE `troubleshooting_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-14 12:36:58
