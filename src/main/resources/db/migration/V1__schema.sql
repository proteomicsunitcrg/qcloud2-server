-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: qcloud2
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.16.04.1

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
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `authority_seq`
--

DROP TABLE IF EXISTS `authority_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `authority_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `main` tinyint(1) DEFAULT '0',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_bfd0gb7bper1su0y9ig2asy7c` (`api_key`),
  UNIQUE KEY `UK_46ccwnsi9409t36lurvtyljak` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_seq`
--

DROP TABLE IF EXISTS `category_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart`
--

DROP TABLE IF EXISTS `chart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `is_normalized` tinyint(1) DEFAULT '0',
  `is_threshold` tinyint(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `cv_id` bigint(20) NOT NULL,
  `sample_type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_jr8waxdp8eul46fw1hdtmq8vb` (`api_key`),
  KEY `FKnmcqvc5ht31b55xrk5jk1sbjo` (`cv_id`),
  KEY `FKdieh4ukwpaig66w1g3qakboci` (`sample_type_id`),
  CONSTRAINT `FKdieh4ukwpaig66w1g3qakboci` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKnmcqvc5ht31b55xrk5jk1sbjo` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart_params`
--

DROP TABLE IF EXISTS `chart_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart_params` (
  `chart_id` bigint(20) NOT NULL,
  `context_source_id` bigint(20) NOT NULL,
  `param_id` bigint(20) NOT NULL,
  PRIMARY KEY (`chart_id`,`context_source_id`,`param_id`),
  KEY `FK45wq42vv53fkf7vf08ixv6rof` (`context_source_id`),
  KEY `FKt6ild58v7yjkxa1ow6ga0mtvi` (`param_id`),
  CONSTRAINT `FK45wq42vv53fkf7vf08ixv6rof` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`),
  CONSTRAINT `FK8l6bpqlr3f7ngyr3d5pm4pqk9` FOREIGN KEY (`chart_id`) REFERENCES `chart` (`id`),
  CONSTRAINT `FKt6ild58v7yjkxa1ow6ga0mtvi` FOREIGN KEY (`param_id`) REFERENCES `param` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chart_seq`
--

DROP TABLE IF EXISTS `chart_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chart_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `context_source`
--

DROP TABLE IF EXISTS `context_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `context_source` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `abbreviated` varchar(255) DEFAULT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `quality_control_controlled_vocabulary` varchar(255) DEFAULT NULL,
  `charge` int(11) DEFAULT NULL,
  `mz` float DEFAULT NULL,
  `sequence` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_dtxla0m8f4ymo757ygmdf24pq` (`api_key`),
  UNIQUE KEY `UK_lu3qjf87d31ppvkbwbyfqy6ng` (`name`),
  UNIQUE KEY `UK_5tspe3pfsgpsvn4l1ka4j2xe` (`quality_control_controlled_vocabulary`),
  UNIQUE KEY `UK_hlm5nsym1ckq68e2ea9od25lp` (`sequence`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cv`
--

DROP TABLE IF EXISTS `cv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv` (
  `id` bigint(20) NOT NULL,
  `definition` longtext,
  `enabled` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `cv_id` varchar(255) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_tqa9at57xpjbfmf4cr7kt4kb5` (`name`),
  KEY `FKd0275o3rpi7ce6yrmwyn11t1j` (`category_id`),
  CONSTRAINT `FKd0275o3rpi7ce6yrmwyn11t1j` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cv_seq`
--

DROP TABLE IF EXISTS `cv_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cv_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data`
--

DROP TABLE IF EXISTS `data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data` (
  `context_source_id` bigint(20) NOT NULL,
  `file_id` bigint(20) NOT NULL,
  `param_id` bigint(20) NOT NULL,
  `calculated_value` float DEFAULT NULL,
  `non_conformity_status` varchar(255) DEFAULT 'OK',
  `value` float DEFAULT NULL,
  PRIMARY KEY (`context_source_id`,`file_id`,`param_id`),
  KEY `FKg3e02o0b669arg7nkyrs60lh7` (`file_id`),
  KEY `FKoq9uqfyub8f4dw5o7lff9qla1` (`param_id`),
  CONSTRAINT `FK8xdxnnj4xh98i813oys7xdurh` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`),
  CONSTRAINT `FKg3e02o0b669arg7nkyrs60lh7` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FKoq9uqfyub8f4dw5o7lff9qla1` FOREIGN KEY (`param_id`) REFERENCES `param` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `data_source`
--

DROP TABLE IF EXISTS `data_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `data_source` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  `name` varchar(50) DEFAULT NULL,
  `cv_id` bigint(20) DEFAULT NULL,
  `node_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_stpbo9mu9qpc36jk1yebu1xv6` (`api_key`),
  KEY `FK8u4u8dh9kin523t3fle0ibm99` (`cv_id`),
  KEY `FKec97t1h6uqif2cfyork23m689` (`node_id`),
  CONSTRAINT `FK8u4u8dh9kin523t3fle0ibm99` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`),
  CONSTRAINT `FKec97t1h6uqif2cfyork23m689` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file`
--

DROP TABLE IF EXISTS `file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file` (
  `id` bigint(20) NOT NULL,
  `checksum` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `filename` varchar(255) DEFAULT NULL,
  `guide_set_id` bigint(20) DEFAULT NULL,
  `labsystem_id` bigint(20) DEFAULT NULL,
  `sample_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_s6qjegp2eohyfcc8nehhrrk17` (`checksum`),
  KEY `FKqd7cq8w4djtbh8686la31n1g6` (`guide_set_id`),
  KEY `FK72asntujuf2j0wamkcs1m1j7w` (`labsystem_id`),
  KEY `FKat613sr4dlgdlfy3rktgv2t7s` (`sample_type_id`),
  CONSTRAINT `FK72asntujuf2j0wamkcs1m1j7w` FOREIGN KEY (`labsystem_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKat613sr4dlgdlfy3rktgv2t7s` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKqd7cq8w4djtbh8686la31n1g6` FOREIGN KEY (`guide_set_id`) REFERENCES `guide_set` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `file_seq`
--

DROP TABLE IF EXISTS `file_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `file_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guide_set`
--

DROP TABLE IF EXISTS `guide_set`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guide_set` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '0',
  `start_date` datetime DEFAULT NULL,
  `files` int(11) DEFAULT NULL,
  `sample_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ea8e5icv3469cmimqepyfx3cx` (`api_key`),
  KEY `FKdk49nrvbdygcqolk42idt2gij` (`sample_type_id`),
  CONSTRAINT `FKdk49nrvbdygcqolk42idt2gij` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `guide_set_seq`
--

DROP TABLE IF EXISTS `guide_set_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `guide_set_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labsystem`
--

DROP TABLE IF EXISTS `labsystem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labsystem` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_lo1qglw1ko8kk94xb87cl76jt` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labsystem_data_sources`
--

DROP TABLE IF EXISTS `labsystem_data_sources`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labsystem_data_sources` (
  `lab_system_id` bigint(20) NOT NULL,
  `data_sources_id` bigint(20) NOT NULL,
  KEY `FKs4f7d072hopd4ldi5j38a7p86` (`data_sources_id`),
  KEY `FKmm18jl5vyr9mi1ufw1uvcoa6i` (`lab_system_id`),
  CONSTRAINT `FKmm18jl5vyr9mi1ufw1uvcoa6i` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKs4f7d072hopd4ldi5j38a7p86` FOREIGN KEY (`data_sources_id`) REFERENCES `data_source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labsystem_guide_sets`
--

DROP TABLE IF EXISTS `labsystem_guide_sets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labsystem_guide_sets` (
  `lab_system_id` bigint(20) NOT NULL,
  `guide_sets_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_980nqoj0kphpw8vdis8blop9q` (`guide_sets_id`),
  KEY `FKju8japci5tvqbpus6eb6gqm79` (`lab_system_id`),
  CONSTRAINT `FKju8japci5tvqbpus6eb6gqm79` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKrquk8u0xt4w5505uk3k1523b` FOREIGN KEY (`guide_sets_id`) REFERENCES `guide_set` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `labsystem_seq`
--

DROP TABLE IF EXISTS `labsystem_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `labsystem_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `node`
--

DROP TABLE IF EXISTS `node`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_obvbgg44871hprrdukj0ev7is` (`api_key`),
  UNIQUE KEY `UK_fwigxdmj6bsrpcmhcgpmlsirh` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `node_seq`
--

DROP TABLE IF EXISTS `node_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `param`
--

DROP TABLE IF EXISTS `param`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param` (
  `id` bigint(20) NOT NULL,
  `is_for` varchar(255) DEFAULT NULL,
  `is_zero_no_data` tinyint(1) DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `processor` int(11) DEFAULT NULL,
  `qccv` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pwh3bu6ckkxevw19avdaowla3` (`qccv`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `param_seq`
--

DROP TABLE IF EXISTS `param_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `param_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample_category_seq`
--

DROP TABLE IF EXISTS `sample_category_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_category_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample_composition`
--

DROP TABLE IF EXISTS `sample_composition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_composition` (
  `peptide_id` bigint(20) NOT NULL,
  `sample_type_id` bigint(20) NOT NULL,
  `concentration` float DEFAULT NULL,
  PRIMARY KEY (`peptide_id`,`sample_type_id`),
  KEY `FKav1cuc85ucg96ydf9x7r7p7ek` (`sample_type_id`),
  CONSTRAINT `FKav1cuc85ucg96ydf9x7r7p7ek` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKb0jw4p5lib5td36wr2811lsow` FOREIGN KEY (`peptide_id`) REFERENCES `context_source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample_seq`
--

DROP TABLE IF EXISTS `sample_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample_type`
--

DROP TABLE IF EXISTS `sample_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_type` (
  `id` bigint(20) NOT NULL,
  `is_main_sample_type` bit(1) DEFAULT NULL,
  `name` varchar(50) NOT NULL,
  `qccv` varchar(50) DEFAULT NULL,
  `sample_type_category` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_f45tc3hc7yabewttdy2cdyafn` (`name`),
  UNIQUE KEY `UK_hp1kdymh06b64j8j2g8yltbln` (`qccv`),
  KEY `FKog28c232kuhrdpr4p27d1ljml` (`sample_type_category`),
  CONSTRAINT `FKog28c232kuhrdpr4p27d1ljml` FOREIGN KEY (`sample_type_category`) REFERENCES `sample_type_category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sample_type_category`
--

DROP TABLE IF EXISTS `sample_type_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sample_type_category` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(50) NOT NULL,
  `complexity` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_n3pp6ym8sliwea6r8dulgrs8b` (`api_key`),
  UNIQUE KEY `UK_57su9twcfm41exlvhv2rp9ehc` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `threshold`
--

DROP TABLE IF EXISTS `threshold`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threshold` (
  `dtype` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `is_enabled` tinyint(1) DEFAULT '1',
  `is_monitored` tinyint(1) DEFAULT '1',
  `name` varchar(255) DEFAULT NULL,
  `steps` int(11) DEFAULT NULL,
  `cv_id` bigint(20) NOT NULL,
  `lab_system_id` bigint(20) DEFAULT NULL,
  `param_id` bigint(20) NOT NULL,
  `sample_type_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9hxspk3fg6bhh4y1yyuu36ov9` (`api_key`),
  KEY `FK5wj84i4qlovm1vfctjtulqxsk` (`cv_id`),
  KEY `FK6u2jdq904i6g7vw64semwewqv` (`lab_system_id`),
  KEY `FKmp0khk9w2rhtce4qh73xvu1rc` (`param_id`),
  KEY `FKae33knn5nm6k7u9vr5vgpuoj3` (`sample_type_id`),
  CONSTRAINT `FK5wj84i4qlovm1vfctjtulqxsk` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`),
  CONSTRAINT `FK6u2jdq904i6g7vw64semwewqv` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKae33knn5nm6k7u9vr5vgpuoj3` FOREIGN KEY (`sample_type_id`) REFERENCES `sample_type` (`id`),
  CONSTRAINT `FKmp0khk9w2rhtce4qh73xvu1rc` FOREIGN KEY (`param_id`) REFERENCES `param` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `threshold_non_conformity`
--

DROP TABLE IF EXISTS `threshold_non_conformity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threshold_non_conformity` (
  `id` bigint(20) NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `context_source_id` bigint(20) NOT NULL,
  `file_id` bigint(20) NOT NULL,
  `guide_set_id` bigint(20) DEFAULT NULL,
  `threshold_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6i1eryp0udi4bar6694hud7vt` (`context_source_id`),
  KEY `FKh041ownqhtu11th845hoxy22c` (`file_id`),
  KEY `FKgc55edyr77onqy23iv242mwvt` (`guide_set_id`),
  KEY `FKr4vbi2dex2spcga6dujx1qbiu` (`threshold_id`),
  CONSTRAINT `FK6i1eryp0udi4bar6694hud7vt` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`),
  CONSTRAINT `FKgc55edyr77onqy23iv242mwvt` FOREIGN KEY (`guide_set_id`) REFERENCES `guide_set` (`id`),
  CONSTRAINT `FKh041ownqhtu11th845hoxy22c` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`),
  CONSTRAINT `FKr4vbi2dex2spcga6dujx1qbiu` FOREIGN KEY (`threshold_id`) REFERENCES `threshold` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `threshold_non_conformity_seq`
--

DROP TABLE IF EXISTS `threshold_non_conformity_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threshold_non_conformity_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `threshold_params`
--

DROP TABLE IF EXISTS `threshold_params`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threshold_params` (
  `context_source_id` bigint(20) NOT NULL,
  `threshold_id` bigint(20) NOT NULL,
  `initial_value` float DEFAULT NULL,
  `is_enabled` tinyint(1) DEFAULT '1',
  `step_value` float DEFAULT NULL,
  PRIMARY KEY (`context_source_id`,`threshold_id`),
  KEY `FK8g1pxjaqjdhdc96r5ok5eyd9k` (`threshold_id`),
  CONSTRAINT `FK8g1pxjaqjdhdc96r5ok5eyd9k` FOREIGN KEY (`threshold_id`) REFERENCES `threshold` (`id`),
  CONSTRAINT `FKs85ab4k83uteyqmd8xf4ucb0n` FOREIGN KEY (`context_source_id`) REFERENCES `context_source` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `threshold_seq`
--

DROP TABLE IF EXISTS `threshold_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `threshold_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `email` varchar(50) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastpasswordresetdate` datetime(6) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `node_id` bigint(20) DEFAULT NULL,
  `user_default_view_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_2lxq4yoyabuji9s2a1i0c8938` (`api_key`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FKm70yy06pfcfshd41vhwieh8ox` (`node_id`),
  KEY `FKqv8gamm7m095x354qe5jxye7l` (`user_default_view_id`),
  CONSTRAINT `FKm70yy06pfcfshd41vhwieh8ox` FOREIGN KEY (`node_id`) REFERENCES `node` (`id`),
  CONSTRAINT `FKqv8gamm7m095x354qe5jxye7l` FOREIGN KEY (`user_default_view_id`) REFERENCES `user_default_view` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  KEY `FKgvxjs381k6f48d5d2yi11uh89` (`authority_id`),
  KEY `FKpqlsjpkybgos9w2svcri7j8xy` (`user_id`),
  CONSTRAINT `FKgvxjs381k6f48d5d2yi11uh89` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FKpqlsjpkybgos9w2svcri7j8xy` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_default_view`
--

DROP TABLE IF EXISTS `user_default_view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_default_view` (
  `id` bigint(20) NOT NULL,
  `view_type` varchar(255) DEFAULT NULL,
  `lab_system_id` bigint(20) DEFAULT NULL,
  `view_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi8w0rh5f29kq9kb1htf723wei` (`lab_system_id`),
  KEY `FKlj9v6xr3jrarcay5dr6c6f08h` (`view_id`),
  CONSTRAINT `FKi8w0rh5f29kq9kb1htf723wei` FOREIGN KEY (`lab_system_id`) REFERENCES `labsystem` (`id`),
  CONSTRAINT `FKlj9v6xr3jrarcay5dr6c6f08h` FOREIGN KEY (`view_id`) REFERENCES `view` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_default_view_seq`
--

DROP TABLE IF EXISTS `user_default_view_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_default_view_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `view`
--

DROP TABLE IF EXISTS `view`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view` (
  `id` bigint(20) NOT NULL,
  `api_key` binary(16) NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `cv_id` bigint(20) DEFAULT NULL,
  `sample_type_category_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_fwuf3luwk67b5kyha20hqgomg` (`api_key`),
  UNIQUE KEY `UKekwsuuds2k5src0fdqet2kndw` (`cv_id`,`sample_type_category_id`),
  KEY `FK4hbv1t4auglyo42k3jjngerbu` (`sample_type_category_id`),
  KEY `FK37w6bab99jhjeja56i1te4htp` (`user_id`),
  CONSTRAINT `FK37w6bab99jhjeja56i1te4htp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK4hbv1t4auglyo42k3jjngerbu` FOREIGN KEY (`sample_type_category_id`) REFERENCES `sample_type_category` (`id`),
  CONSTRAINT `FKl3chbriqqrag0do15dwkh45nj` FOREIGN KEY (`cv_id`) REFERENCES `cv` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `view_display`
--

DROP TABLE IF EXISTS `view_display`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
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
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `view_display_seq`
--

DROP TABLE IF EXISTS `view_display_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view_display_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `view_seq`
--

DROP TABLE IF EXISTS `view_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `view_seq` (
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

-- Dump completed on 2018-09-27 10:32:27
