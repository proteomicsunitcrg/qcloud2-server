-- MySQL dump 10.13  Distrib 8.0.16, for Linux (x86_64)
--
-- Host: localhost    Database: qcloud2
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `community_line`
--
DROP TABLE IF EXISTS `community_line`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;

CREATE TABLE `community_line` (
  `community_line_id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` float DEFAULT NULL,
  `context_source_id` bigint(20) DEFAULT NULL,
  `cv_id` bigint(20) DEFAULT NULL,
  `param_id` bigint(20) DEFAULT NULL,
  `sample_type_id` bigint(20) DEFAULT NULL,
  `trace_color_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`community_line_id`),
  UNIQUE KEY `UK_cmrrjiph27689yiwf449lyvkd` (`api_key`),
  KEY `FKc2komso8oypfio0vjm4cp1etf` (`context_source_id`),
  KEY `FK7o8easn36cogam5uojtr9qwam` (`cv_id`),
  KEY `FKsg5b7k0nfe7b6qv3tud50dlk2` (`param_id`),
  KEY `FKe24xj7pvprfvpen62oicmu9as` (`sample_type_id`),
  KEY `FKe76hywvcftd45kjhsosnc8e37` (`trace_color_id`),
  CONSTRAINT `FK7o8easn36cogam5uojtr9qwam` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`),
  CONSTRAINT `FKc2komso8oypfio0vjm4cp1etf` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`),
  CONSTRAINT `FKe24xj7pvprfvpen62oicmu9as` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKe76hywvcftd45kjhsosnc8e37` FOREIGN KEY (`trace_color_id`) REFERENCES `trace_color` (`id`),
  CONSTRAINT `FKsg5b7k0nfe7b6qv3tud50dlk2` FOREIGN KEY (`param_id`) REFERENCES `param` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `community_line_node`
--

DROP TABLE IF EXISTS `community_line_node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `community_line_node` (
  `community_line_node_id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `community_line_id` bigint(20) DEFAULT NULL,
  `id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`community_line_node_id`),
  KEY `FKm25yaq3lrqwfoiv26wdrj1tm5` (`community_line_id`),
  KEY `FKkmag0crgcngx09nfradqg2ryg` (`id`),
  CONSTRAINT `FKkmag0crgcngx09nfradqg2ryg` FOREIGN KEY (`id`) REFERENCES `node` (`id`),
  CONSTRAINT `FKm25yaq3lrqwfoiv26wdrj1tm5` FOREIGN KEY (`community_line_id`) REFERENCES `community_line` (`community_line_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `community_line_seq`
--

DROP TABLE IF EXISTS `community_line_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `community_line_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


