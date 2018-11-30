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
-- Table structure for table `action_seq`
--

DROP TABLE IF EXISTS `action_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `non_conformity_action`
--

DROP TABLE IF EXISTS `non_conformity_action`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_action` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_annotation`
--

DROP TABLE IF EXISTS `non_conformity_annotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_annotation` (
  `id` bigint(20) NOT NULL,
  `date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_annotation_actions`
--

DROP TABLE IF EXISTS `non_conformity_annotation_actions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_annotation_actions` (
  `annotation_id` bigint(20) NOT NULL,
  `actions_id` bigint(20) NOT NULL,
  KEY `FKarlujo1p448m6avkbej034psd` (`actions_id`),
  KEY `FK7leq2wrs6lkvi37pshitax2hu` (`annotation_id`),
  CONSTRAINT `FK7leq2wrs6lkvi37pshitax2hu` FOREIGN KEY (`annotation_id`) REFERENCES `non_conformity_annotation` (`id`),
  CONSTRAINT `FKarlujo1p448m6avkbej034psd` FOREIGN KEY (`actions_id`) REFERENCES `non_conformity_action` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_annotation_problems`
--

DROP TABLE IF EXISTS `non_conformity_annotation_problems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_annotation_problems` (
  `annotation_id` bigint(20) NOT NULL,
  `problems_id` bigint(20) NOT NULL,
  KEY `FKt4t6c6d2bn23qlk1sj4y063br` (`problems_id`),
  KEY `FKk83em6rcxh61fsy8my7i9j99o` (`annotation_id`),
  CONSTRAINT `FKk83em6rcxh61fsy8my7i9j99o` FOREIGN KEY (`annotation_id`) REFERENCES `non_conformity_annotation` (`id`),
  CONSTRAINT `FKt4t6c6d2bn23qlk1sj4y063br` FOREIGN KEY (`problems_id`) REFERENCES `non_conformity_problem` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_annotation_reasons`
--

DROP TABLE IF EXISTS `non_conformity_annotation_reasons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_annotation_reasons` (
  `annotation_id` bigint(20) NOT NULL,
  `reasons_id` bigint(20) NOT NULL,
  KEY `FK841p2o644ag2qgj20506hety8` (`reasons_id`),
  KEY `FK9vbjuudooc94li1ffoapqbe51` (`annotation_id`),
  CONSTRAINT `FK841p2o644ag2qgj20506hety8` FOREIGN KEY (`reasons_id`) REFERENCES `non_conformity_reason` (`id`),
  CONSTRAINT `FK9vbjuudooc94li1ffoapqbe51` FOREIGN KEY (`annotation_id`) REFERENCES `non_conformity_annotation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_problem`
--

DROP TABLE IF EXISTS `non_conformity_problem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_problem` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `non_conformity_reason`
--

DROP TABLE IF EXISTS `non_conformity_reason`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `non_conformity_reason` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `problem_seq`
--

DROP TABLE IF EXISTS `problem_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `problem_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `reason_seq`
--

DROP TABLE IF EXISTS `reason_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `reason_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-11-29 10:28:23
