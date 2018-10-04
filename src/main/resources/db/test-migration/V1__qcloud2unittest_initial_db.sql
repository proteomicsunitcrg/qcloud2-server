-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: qcloud2test
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
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ROLE_USER'),(2,'ROLE_MANAGER'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `authority_seq`
--

LOCK TABLES `authority_seq` WRITE;
/*!40000 ALTER TABLE `authority_seq` DISABLE KEYS */;
INSERT INTO `authority_seq` VALUES (1);
/*!40000 ALTER TABLE `authority_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,_binary '!YùHZùDfùùùG?\0',1,'Mass spectrometer'),(2,_binary '@%ù_ù(Hùù}}Xw!',0,'Liquid chromatographer');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `category_seq`
--

LOCK TABLES `category_seq` WRITE;
/*!40000 ALTER TABLE `category_seq` DISABLE KEYS */;
INSERT INTO `category_seq` VALUES (3);
/*!40000 ALTER TABLE `category_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `chart`
--

LOCK TABLES `chart` WRITE;
/*!40000 ALTER TABLE `chart` DISABLE KEYS */;
INSERT INTO `chart` VALUES (1,_binary '\"bùJùùI?ùY`ùùw\0',0,0,'Peptide area',75,1),(2,_binary 'FAùùnOWùùJùMùù\0',0,1,'Mass accuracy',75,1),(3,_binary ' ùù*BùjùùJùùV\0',0,0,'Miit',75,1),(4,_binary 'mùLùùJùùùùùù',0,0,'Median IT MS2',48,1),(5,_binary '{ùùkùABù6R(ù`ù',0,1,'Totals',75,1),(6,_binary 'mùVù#NùùùùHùd',0,0,'Mass accuracy',75,4);
/*!40000 ALTER TABLE `chart` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `chart_params`
--

LOCK TABLES `chart_params` WRITE;
/*!40000 ALTER TABLE `chart_params` DISABLE KEYS */;
INSERT INTO `chart_params` VALUES (1,1,1),(2,1,2),(1,2,1),(2,2,2),(1,3,1),(2,3,2),(1,4,1),(2,4,2),(1,5,1),(2,5,2),(1,6,1),(2,6,2),(1,7,1),(2,7,2),(1,8,1),(2,8,2),(1,9,1),(2,9,2),(1,10,1),(2,10,2),(3,11,3),(3,12,3),(4,12,3),(5,13,4),(5,14,4),(5,15,4),(5,16,4),(6,17,2),(6,18,2),(6,19,2),(6,20,2),(6,21,2),(6,22,2),(6,23,2),(6,24,2),(6,25,2),(6,26,2),(6,27,2),(6,28,2),(6,29,2),(6,30,2),(6,31,2);
/*!40000 ALTER TABLE `chart_params` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `chart_seq`
--

LOCK TABLES `chart_seq` WRITE;
/*!40000 ALTER TABLE `chart_seq` DISABLE KEYS */;
INSERT INTO `chart_seq` VALUES (7);
/*!40000 ALTER TABLE `chart_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `context_source`
--

LOCK TABLES `context_source` WRITE;
/*!40000 ALTER TABLE `context_source` DISABLE KEYS */;
INSERT INTO `context_source` VALUES ('peptide',1,'LVN',_binary 'ù:?	/JjùùùLùZr\0','LVNELTEFAK',NULL,2,582.319,'LVNELTEFAK'),('peptide',2,'HLV',_binary 'ù?ù*ùEùùùù_ùJùù\0','HLVDEPQNLIK',NULL,2,653.362,'HLVDEPQNLIK'),('peptide',3,'VPQ',_binary 'ùù]3ùùLù^|ùrdùù','VPQVSTPTLVEVSR',NULL,2,756.425,'VPQVSTPTLVEVSR'),('peptide',4,'EAC',_binary 'ù2ù`ùNùù4ù3\0','EAC(Carbamidomethyl)FAVEGPK',NULL,2,554.261,'EAC(Carbamidomethyl)FAVEGPK'),('peptide',5,'EYE',_binary 'ùKùù0F?ùùùuù\0\0','EYEATLEEC(Carbamidomethyl)C(Carbamidomethyl)AK',NULL,2,751.811,'EYEATLEEC(Carbamidomethyl)C(Carbamidomethyl)AK'),('peptide',6,'ECC',_binary '?4vùHMùùùùW?kù\0\0','EC(Carbamidomethyl)C(Carbamidomethyl)HGDLLEC(Carbamidomethyl)ADDR',NULL,3,583.892,'EC(Carbamidomethyl)C(Carbamidomethyl)HGDLLEC(Carbamidomethyl)ADDR'),('peptide',7,'SLH',_binary '\'ùùùù-Jùùù\'X:vùù','SLHTLFGDELC(Carbamidomethyl)K',NULL,2,710.35,'SLHTLFGDELC(Carbamidomethyl)K'),('peptide',8,'TCV',_binary 'y+ùùù%@aùùùaùù,\0','TC(Carbamidomethyl)VADESHAGC(Carbamidomethyl)EK',NULL,3,488.534,'TC(Carbamidomethyl)VADESHAGC(Carbamidomethyl)EK'),('peptide',9,'YIC',_binary 'ù`ù!ùMù>ùQ=3ùU','YIC(Carbamidomethyl)DNQDTISSK',NULL,2,722.325,'YIC(Carbamidomethyl)DNQDTISSK'),('peptide',10,'NEC',_binary 'ùùùùH2ù_ù^ù)\\?','NEC(Carbamidomethyl)FLSHK',NULL,2,517.74,'NEC(Carbamidomethyl)FLSHK'),('instrument_sample',11,'miit ms1',_binary 'ùùùùùDùùhùùrQù','Median IT MS1','QC:1000927',NULL,NULL,NULL),('instrument_sample',12,'Median IT MS2',_binary 'ù\'#	ù@\0ùùùwùYùù','Median IT MS2','QC:1000928',NULL,NULL,NULL),('instrument_sample',13,'# proteins',_binary 'ù[6QùF6ùjù/ùO5','Total number of identified proteins','QC:0000032',NULL,NULL,NULL),('instrument_sample',14,'# peptides',_binary '	xù/[ùC3ùùù;ùùV','Total number of uniquely identified peptides','QC:0000031',NULL,NULL,NULL),('instrument_sample',15,'MS2 spectral count',_binary 'x8ùùFvùnelùù?\0\0','MS2 Spectral count','QC:0000007',NULL,NULL,NULL),('instrument_sample',16,'# psm',_binary 'ùùù&#G?#B ùù?\0\0','Total number of PSM','QC:0000029',NULL,NULL,NULL),('peptide',17,'YAE',_binary '*ù=6CE?ùO@gcGùù','YAEAVTR',NULL,2,405.211,'YAEAVTR'),('peptide',18,'TPA',_binary '?06s\"Oùù?)ù	ùE\0\0','TPAQFDADELR',NULL,2,631.804,'TPAQFDADELR'),('peptide',19,'STL',_binary '?,ùùFùùOùù?\0\0','STLTDSLVC(Carbamidomethyl)K',NULL,2,562.287,'STLTDSLVC(Carbamidomethyl)K'),('peptide',20,'SLA',_binary '9Zùù@%Iùùùc$xù','SLADELALVDVLEDK',NULL,2,815.433,'SLADELALVDVLEDK'),('peptide',21,'NPD',_binary 'IùùùG\\ùùùù=ùùù','NPDDITNEEYGEFYK',NULL,2,917.394,'NPDDITNEEYGEFYK'),('peptide',22,'LGD',_binary '4HE98A\ZùùFJùù','LGDLYEEEMR',NULL,2,627.787,'LGDLYEEEMR'),('peptide',23,'LAV',_binary '!ùaùùJNùù:ùù?ù','LAVDEEENADNNTK',NULL,2,781.352,'LAVDEEENADNNTK'),('peptide',24,'FEE',_binary 'ùù\nùDùJùùùù?ùùùù','FEELNMDLFR',NULL,2,657.313,'FEELNMDLFR'),('peptide',25,'EAA',_binary 'ùR~ù\rùLYùùùùù[ù','EAALSTALSEK',NULL,2,560.298,'EAALSTALSEK'),('peptide',26,'DDV',_binary 'c)ùJ?ùùùkA\Zù\0','DDVAQTDLLQIDPNFGSK',NULL,2,988.484,'DDVAQTDLLQIDPNFGSK'),('peptide',27,'RFP',_binary 'ù4ùKvJNùùù?ùù\0','RFPGYDSESK',NULL,2,593.28,'RFPGYDSESK'),('peptide',28,'EVS',_binary 'ùMùùùE?{sùvùù\'\0','EVSTYIK',NULL,2,420.229,'EVSTYIK'),('peptide',29,'EAT',_binary 'ùrù21\"D6ù;ùT\"p]','EATTEFSVDAR',NULL,2,613.288,'EATTEFSVDAR'),('peptide',30,'FAF',_binary 'ù1EùùOWùùùeZùùù\0','FAFQAEVNR',NULL,2,541.275,'FAFQAEVNR'),('peptide',31,'EQF',_binary 'ù\'Uù8Jùùùùù]ùù\0','EQFLDGDGWTSR',NULL,2,705.818,'EQFLDGDGWTSR'),('peptide',32,'YVY',_binary '9mWY3&Lùùù*6ha0','YV(Heavy)YV(Heavy)ADV(Heavy)A(Heavy)A(Heavy)K(Heavy)',NULL,2,566.83,'YV(Heavy)YV(Heavy)ADV(Heavy)A(Heavy)A(Heavy)K(Heavy)'),('peptide',33,'YVY',_binary 'ùWù$`Jùù!ù`Cù','YV(Heavy)YV(Heavy)ADV(Heavy)AAK(Heavy)',NULL,2,562.823,'YV(Heavy)YV(Heavy)ADV(Heavy)AAK(Heavy)'),('peptide',34,'YVY',_binary 'ùPùGù\\A?ù1ùùùù\0','YVYV(Heavy)ADV(Heavy)AAK(Heavy)',NULL,2,559.816,'YVYV(Heavy)ADV(Heavy)AAK(Heavy)'),('peptide',35,'YVY',_binary 'g8ùùjK?\0\'nùùù\0','YVYVADV(Heavy)AAK(Heavy)',NULL,2,556.809,'YVYVADV(Heavy)AAK(Heavy)'),('peptide',36,'YVY',_binary 'ùù<v?F?ùù?r\0\0\0\0\0','YVYVADVAAK(Heavy)',NULL,2,553.802,'YVYVADVAAK(Heavy)'),('peptide',37,'LLS',_binary 'ùS\\ù,IGdùù	ùùùwK','L(Heavy)L(Heavy)SL(Heavy)GAGEF(Heavy)K(Heavy)',NULL,2,537.344,'L(Heavy)L(Heavy)SL(Heavy)GAGEF(Heavy)K(Heavy)'),('peptide',38,'LLS',_binary 'ùùùZFIùùùpùùùù','L(Heavy)L(Heavy)SL(Heavy)GAGEFK(Heavy)',NULL,2,532.331,'L(Heavy)L(Heavy)SL(Heavy)GAGEFK(Heavy)'),('peptide',39,'LLS',_binary 'zSTùMùA0ù/Tùt?ù','L(Heavy)L(Heavy)SLGAGEFK(Heavy)',NULL,2,528.822,'L(Heavy)L(Heavy)SLGAGEFK(Heavy)'),('peptide',40,'LLS',_binary 'ùùgùx@ùùùùùù4\0','L(Heavy)LSLGAGEFK(Heavy)',NULL,2,525.313,'L(Heavy)LSLGAGEFK(Heavy)'),('peptide',41,'LLS',_binary 'ùùù@YH)ùHNùùùù','LLSLGAGEFK(Heavy)',NULL,2,521.805,'LLSLGAGEFK(Heavy)'),('peptide',42,'LGF',_binary 'ùùP-yM+ùùùùù?ù\0','L(Heavy)GF(Heavy)TDL(Heavy)F(Heavy)SK(Heavy)',NULL,2,535.328,'L(Heavy)GF(Heavy)TDL(Heavy)F(Heavy)SK(Heavy)'),('peptide',43,'LGF',_binary '[KùDBùùù?QUù\0','L(Heavy)GFTDL(Heavy)F(Heavy)SK(Heavy)',NULL,2,530.315,'L(Heavy)GFTDL(Heavy)F(Heavy)SK(Heavy)'),('peptide',44,'LGF',_binary 'ù!ùùùùHùù?Sù4ùù','L(Heavy)GFTDL(Heavy)FSK(Heavy)',NULL,2,525.301,'L(Heavy)GFTDL(Heavy)FSK(Heavy)'),('peptide',45,'LGF',_binary 'ùbùùùOùQù?ù7\0','L(Heavy)GFTDLFSK(Heavy)',NULL,2,521.792,'L(Heavy)GFTDLFSK(Heavy)'),('peptide',46,'LGF',_binary 'ùùvùùCùù#ùù(','LGFTDLFSK(Heavy)',NULL,2,518.284,'LGFTDLFSK(Heavy)'),('peptide',47,'VTS',_binary 'ù?ùùYM?ùtùùùùù\0','V(Heavy)T(Heavy)S(Heavy)GST(Heavy)ST(Heavy)SR(Heavy)',NULL,2,509.274,'V(Heavy)T(Heavy)S(Heavy)GST(Heavy)ST(Heavy)SR(Heavy)'),('peptide',48,'VTS',_binary 'Zf5DùùùUùùG','V(Heavy)T(Heavy)SGSTST(Heavy)SR(Heavy)',NULL,2,504.765,'V(Heavy)T(Heavy)SGSTST(Heavy)SR(Heavy)'),('peptide',49,'VTS',_binary 'ù??N1ùùù;Wfùù\0\0','V(Heavy)T(Heavy)SGSTSTSR(Heavy)',NULL,2,502.26,'V(Heavy)T(Heavy)SGSTSTSR(Heavy)'),('peptide',50,'VTS',_binary 'ùBùl>ùI?ùbùùGù\0','V(Heavy)TSGSTSTSR(Heavy)',NULL,2,499.755,'V(Heavy)TSGSTSTSR(Heavy)'),('peptide',51,'VTS',_binary '4soùDBùùùù?ùNù\0','VTSGSTSTSR(Heavy)',NULL,2,496.748,'VTSGSTSTSR(Heavy)'),('peptide',52,'VVG',_binary 'ùù?TH(ùg%?ùN\0\0\0','V(Heavy)V(Heavy)GGL(Heavy)V(Heavy)ALR(Heavy)',NULL,2,459.823,'V(Heavy)V(Heavy)GGL(Heavy)V(Heavy)ALR(Heavy)'),('peptide',53,'VVG',_binary 'jDN*@=ùùùùWùù','V(Heavy)V(Heavy)GGLV(Heavy)ALR(Heavy)',NULL,2,456.315,'V(Heavy)V(Heavy)GGLV(Heavy)ALR(Heavy)'),('peptide',54,'VVG',_binary 'ù0+9ù*AùùEù[pù?\0','V(Heavy)V(Heavy)GGLVALR(Heavy)',NULL,2,453.308,'V(Heavy)V(Heavy)GGLVALR(Heavy)'),('peptide',55,'VVG',_binary 'ùù?HùNùù\'ù?ù\0ù\0\0','V(Heavy)VGGLVALR(Heavy)',NULL,2,450.301,'V(Heavy)VGGLVALR(Heavy)'),('peptide',56,'VVG',_binary 'ùù3ùùùNùdgbm}}\0','VVGGLVALR(Heavy)',NULL,2,447.294,'VVGGLVALR(Heavy)'),('peptide',57,'LAS',_binary 'ùùEùù>G?ùùù9Dù\0','L(Heavy)A(Heavy)SV(Heavy)SV(Heavy)S(Heavy)R(Heavy)',NULL,2,428.274,'L(Heavy)A(Heavy)SV(Heavy)SV(Heavy)S(Heavy)R(Heavy)'),('peptide',58,'LAS',_binary '7ùù>Bù.,ù\nS\0','L(Heavy)ASV(Heavy)SV(Heavy)SR(Heavy)',NULL,2,424.267,'L(Heavy)ASV(Heavy)SV(Heavy)SR(Heavy)'),('peptide',59,'LAS',_binary 'ù(KùùùHùùùùùGDg','LASV(Heavy)SV(Heavy)SR(Heavy)',NULL,2,420.758,'LASV(Heavy)SV(Heavy)SR(Heavy)'),('peptide',60,'LAS',_binary 'ùù3f\nwF@ùùJ?%\0\0','LASVSV(Heavy)SR(Heavy)',NULL,2,417.751,'LASVSV(Heavy)SR(Heavy)'),('peptide',61,'LAS',_binary 'k+ùùJ{DùùMùùù','LASVSVSR(Heavy)',NULL,2,414.744,'LASVSVSR(Heavy)');
/*!40000 ALTER TABLE `context_source` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `cv`
--

LOCK TABLES `cv` WRITE;
/*!40000 ALTER TABLE `cv` DISABLE KEYS */;
INSERT INTO `cv` VALUES (1,'Thermo Scientific second generation Velos. [PSI:MS]',_binary '\0','Velos Plus','MS:1001909',1),(2,'Thermo Scientific ISQ single quadrupole MS with the ExtractraBrite source. [PSI:MS]',_binary '\0','ISQ','MS:1001908',1),(3,'Thermo Scientific Exactive MS. [PSI:MS]',_binary '\0','Exactive','MS:1000649',1),(4,'Thermo Scientific GC IsoLink Isotope Ratio MS. [PSI:MS]',_binary '\0','GC IsoLink','MS:1000648',1),(5,'Thermo Scientific MALDI LTQ Orbitrap MS. [PSI:MS]',_binary '\0','MALDI LTQ Orbitrap','MS:1000643',1),(6,'Thermo Scientific MALDI LTQ XL MS. [PSI:MS]',_binary '\0','MALDI LTQ XL','MS:1000642',1),(7,'Thermo Scientific DSQ II GC-MS. [PSI:MS]',_binary '\0','DSQ II','MS:1000641',1),(8,'Thermo Scientific DFS HR GC-MS. [PSI:MS]',_binary '\0','DFS','MS:1000640',1),(9,'Thermo Scientific Element GD Glow Discharge MS. [PSI:MS]',_binary '\0','Element GD','MS:1000647',1),(10,'Thermo Scientific Element 2 HR-ICP-MS. [PSI:MS]',_binary '\0','Element 2','MS:1000646',1),(11,'Thermo Scientific Element XR HR-ICP-MS. [PSI:MS]',_binary '\0','Element XR','MS:1000645',1),(12,'Thermo Scientific TSQ Quantum Access MS. [PSI:MS]',_binary '\0','TSQ Quantum Access','MS:1000644',1),(13,'Finnigan LXQ MS. [PSI:MS]',_binary '\0','LXQ','MS:1000450',1),(14,'Finnigan LTQ Orbitrap Velos MS. [PSI:MS]',_binary '\0','LTQ Orbitrap Velos','MS:1001742',1),(15,'Thermo Scientific Q Exactive Plus. [PSI:PI]',_binary '\0','Q Exactive Plus','MS:1002634',1),(16,'Thermo Scientific TSQ Quantiva MS. [PSI:PI]',_binary '\0','TSQ Quantiva','MS:1002418',1),(17,'Thermo Scientific TSQ Endura MS. [PSI:PI]',_binary '\0','TSQ Endura','MS:1002419',1),(18,'OBSOLETE SCIEX or Applied Biosystems|MDS SCIEX QTRAP 4000. [PSI:MS]',_binary '\0','4000 QTRAP','MS:1000870',1),(19,'Thermo Scientific Orbitrap Fusion. [PSI:PI]',_binary '\0','Orbitrap Fusion','MS:1002416',1),(20,'Thermo Scientific Orbitrap Fusion with ETD. [PSI:PI]',_binary '\0','Orbitrap Fusion ETD','MS:1002417',1),(21,'SCIEX Triple Quad 6500+. [PSI:MS]',_binary '\0','Triple Quad 6500+','MS:1002595',1),(22,'Thermo Scientific LTQ XL MS with ETD. [PSI:MS]',_binary '\0','LTQ XL ETD','MS:1000638',1),(23,'Thermo Scientific LTQ Orbitrap XL MS with ETD. [PSI:MS]',_binary '\0','LTQ Orbitrap XL ETD','MS:1000639',1),(24,'Thermo Scientific ITQ 900 GC-MS. [PSI:MS]',_binary '\0','ITQ 900','MS:1000636',1),(25,'Thermo Scientific ITQ 1100 GC-MS. [PSI:MS]',_binary '\0','ITQ 1100','MS:1000637',1),(26,'Thermo Scientific ITQ 700 GC-MS. [PSI:MS]',_binary '\0','ITQ 700','MS:1000635',1),(27,'Finnigan LTQ Orbitrap MS. [PSI:MS]',_binary '\0','LTQ Orbitrap','MS:1000449',1),(28,'Finnigan LTQ FT MS. [PSI:MS]',_binary '\0','LTQ FT','MS:1000448',1),(29,'Finnigan LTQ MS. [PSI:MS]',_binary '\0','LTQ','MS:1000447',1),(30,'SCIEX Triple Quad 4500. [PSI:MS]',_binary '\0','Triple Quad 4500','MS:1002592',1),(32,'TSQ Vantage. [PSI:MS]',_binary '\0','TSQ Vantage','MS:1001510',1),(33,'Applied Biosystems/MDS SCIEX Proteomics Solution 1 MS. [PSI:MS]',_binary '\0','proteomics solution 1','MS:1000186',1),(34,'Applied Biosystems/MDS SCIEX Q TRAP MS. [PSI:MS]',_binary '\0','Q TRAP','MS:1000187',1),(35,'Accela PDA. [PSI:MS]',_binary '\0','Accela PDA','MS:1000623',1),(36,'Surveyor PDA. [PSI:MS]',_binary '\0','Surveyor PDA','MS:1000622',1),(37,'Thermo Scientific TSQ Quantum Ultra AM. [PSI:MS]',_binary '\0','TSQ Quantum Ultra AM','MS:1000743',1),(38,'SCIEX X500R QTOF, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',_binary '\0','X500R QTOF','MS:1002674',1),(39,'Applied Biosystems/MDS SCIEX SymBiot XVI MS. [PSI:MS]',_binary '\0','SymBiot XVI','MS:1000195',1),(40,'Applied Biosystems/MDS SCIEX SymBiot I MS. [PSI:MS]',_binary '\0','SymBiot I','MS:1000194',1),(41,'SCIEX 2000 QTRAP. [PSI:MS]',_binary '\0','2000 QTRAP','MS:1002577',1),(42,'Applied Biosystems/MDS SCIEX QSTAR MS. [PSI:MS]',_binary '\0','QSTAR','MS:1000190',1),(43,'SCIEX 3500 QTRAP. [PSI:MS]',_binary '\0','3500 QTRAP','MS:1002579',1),(44,'SCIEX 2500 QTRAP. [PSI:MS]',_binary '\0','2500 QTRAP','MS:1002578',1),(45,'Thermo Scientific TSQ Quantum Ultra. [PSI:MS]',_binary '\0','TSQ Quantum Ultra','MS:1000751',1),(46,'Thermo Fisher Scientific LTQ Orbitrap Classic. [PSI:MS]',_binary '\0','LTQ Orbitrap Classic','MS:1002835',1),(47,'LTQ FT Ultra. [PSI:MS]',_binary '\0','LTQ FT Ultra','MS:1000557',1),(48,'LTQ Orbitrap XL. [PSI:MS]',_binary '','LTQ Orbitrap XL','MS:1000556',1),(49,'LTQ Orbitrap Discovery. [PSI:MS]',_binary '\0','LTQ Orbitrap Discovery','MS:1000555',1),(50,'SCIEX 5800 TOF-TOF Analyzer. [PSI:MS]',_binary '\0','5800 TOF/TOF','MS:1001482',1),(51,'Applied Biosystems/MDS SCIEX API 300 MS. [PSI:MS]',_binary '\0','API 300','MS:1002588',1),(52,'Applied Biosystems/MDS SCIEX API 350 MS. [PSI:MS]',_binary '\0','API 350','MS:1002589',1),(53,'Applied Biosystems/MDS SCIEX API 100LC MS. [PSI:MS]',_binary '\0','API 100LC','MS:1002586',1),(54,'Applied Biosystems/MDS SCIEX API 165 MS. [PSI:MS]',_binary '\0','API 165','MS:1002587',1),(55,'SCIEX TripleTOF 5600+ time-of-flight mass spectrometer. [PSI:MS]',_binary '\0','TripleTOF 5600+','MS:1002584',1),(56,'Applied Biosystems/MDS SCIEX API 100 MS. [PSI:MS]',_binary '\0','API 100','MS:1002585',1),(57,'SCIEX QTRAP 6500+. [PSI:MS]',_binary '\0','QTRAP 6500+','MS:1002582',1),(58,'SCIEX TripleTOF 4600 time-of-flight mass spectrometer. [PSI:MS]',_binary '\0','TripleTOF 4600','MS:1002583',1),(59,'SCIEX QTRAP 4500. [PSI:MS]',_binary '\0','QTRAP 4500','MS:1002580',1),(60,'SCIEX QTRAP 6500. [PSI:MS]',_binary '\0','QTRAP 6500','MS:1002581',1),(61,'Thermo Scientific Q Exactive. [PSI:PI]',_binary '\0','Q Exactive HF','MS:1002523',1),(62,'Applied Biosystems|MDS SCIEX QTRAP 5500. [PSI:MS]',_binary '\0','QTRAP 5500','MS:1000931',1),(63,'Thermo Scientific TSQ 8000 Evo MS. [PSI:PI]',_binary '\0','TSQ 8000 Evo','MS:1002525',1),(64,'Thermo Scientific Exactive Plus MS. [PSI:PI]',_binary '\0','Exactive Plus','MS:1002526',1),(65,'SCIEX TripleTOF 5600, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',_binary '\0','TripleTOF 5600','MS:1000932',1),(66,'SCIEX Triple Quad 6500. [PSI:MS]',_binary '\0','Triple Quad 6500','MS:1002594',1),(67,'Applied Biosystems/MDS SCIEX API 150EX Prep MS. [PSI:MS]',_binary '\0','API 150EX Prep','MS:1000144',1),(68,'Applied Biosystems/MDS SCIEX API 2000 MS. [PSI:MS]',_binary '\0','API 2000','MS:1000145',1),(69,'Applied Biosystems/MDS SCIEX API 3000 MS. [PSI:MS]',_binary '\0','API 3000','MS:1000146',1),(70,'Applied Biosystems/MDS SCIEX API 4000 MS. [PSI:MS]',_binary '\0','API 4000','MS:1000147',1),(71,'SCIEX Triple Quad 3500. [PSI:MS]',_binary '\0','Triple Quad 3500','MS:1002591',1),(72,'Applied Biosystems/MDS SCIEX API 365 MS. [PSI:MS]',_binary '\0','API 365','MS:1002590',1),(73,'SCIEX Triple Quad 5500. [PSI:MS]',_binary '\0','Triple Quad 5500','MS:1002593',1),(74,'Applied Biosystems/MDS SCIEX API 150EX MS. [PSI:MS]',_binary '\0','API 150EX','MS:1000143',1),(75,'Thermo Scientific Orbitrap Fusion Lumos mass spectrometer with Tribrid architecture consisting of quadrupole mass filter, linear ion trap and Orbitrap mass analyzers. [PSI:PI]',_binary '','Orbitrap Fusion Lumos','MS:1002732',1),(76,'SCIEX TripleTOF 6600, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',_binary '\0','TripleTOF 6600','MS:1002533',1),(77,'Thermo Scientific LTQ Velos MS with ETD. [PSI:MS]',_binary '\0','LTQ Velos ETD','MS:1000856',1),(78,'Thermo Scientific LTQ XL MS. [PSI:MS]',_binary '\0','LTQ XL','MS:1000854',1),(79,'Thermo Scientific LTQ Velos MS. [PSI:MS]',_binary '','LTQ Velos','MS:1000855',1),(80,'Thermo Scientific second generation Velos and Orbitrap. [PSI:MS]',_binary '\0','LTQ Orbitrap Elite','MS:1001910',1),(81,'Thermo Scientific Q Exactive. [PSI:MS]',_binary '\0','Q Exactive','MS:1001911',1),(82,'SCIEX or Applied Biosystems|MDS SCIEX API 5000 MS. [PSI:MS]',_binary '\0','API 5000','MS:1000654',1),(83,'SCIEX or Applied Biosystems|MDS SCIEX QSTAR Elite. [PSI:MS]',_binary '\0','QSTAR Elite','MS:1000655',1),(84,'Applied Biosystems|MDS SCIEX QSTAR Pulsar. [PSI:MS]',_binary '\0','QSTAR Pulsar','MS:1000656',1),(85,'Applied Biosystems|MDS SCIEX QSTAR XL. [PSI:MS]',_binary '\0','QSTAR XL','MS:1000657',1),(86,'SCIEX or Applied Biosystems|MDS SCIEX QTRAP 3200. [PSI:MS]',_binary '\0','3200 QTRAP','MS:1000651',1),(87,'SCIEX or Applied Biosystems|MDS SCIEX 4800 Plus MALDI TOF-TOF Analyzer. [PSI:MS]',_binary '\0','4800 Plus MALDI TOF/TOF','MS:1000652',1),(88,'SCIEX or Applied Biosystems|MDS SCIEX API 3200 MS. [PSI:MS]',_binary '\0','API 3200','MS:1000653',1),(89,'Thermo Scientific TSQ 9000 Triple Quadrupole MS. [PSI:PI]',_binary '\0','TSQ 9000','MS:1002876',1),(90,'Thermo Scientific Q Exactive HF-X Hybrid Quadrupole Orbitrap MS. [PSI:PI]',_binary '\0','Q Exactive HF-X','MS:1002877',1),(91,'Thermo Scientific TSQ Altis Triple Quadrupole MS. [PSI:PI]',_binary '\0','TSQ Altis','MS:1002874',1),(92,'Thermo Scientific TSQ Quantis Triple Quadrupole MS. [PSI:PI]',_binary '\0','TSQ Quantis','MS:1002875',1),(93,'Liquid chromatographer for mass spectrometry',_binary '','Easy nLC 1000','LC:0000001',2),(94,'Liquid chromatographer for mass spectromety',_binary '','Easy nLc 110','LC:0000002',2);
/*!40000 ALTER TABLE `cv` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `cv_seq`
--

LOCK TABLES `cv_seq` WRITE;
/*!40000 ALTER TABLE `cv_seq` DISABLE KEYS */;
INSERT INTO `cv_seq` VALUES (95);
/*!40000 ALTER TABLE `cv_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `data`
--

LOCK TABLES `data` WRITE;
/*!40000 ALTER TABLE `data` DISABLE KEYS */;
INSERT INTO `data` VALUES (1,1,1,28.4775,'OK',373746000),(1,1,2,0.593912,'OK',0.593912),(1,2,1,28.5961,'OK',405784000),(1,2,2,0.650505,'OK',0.650505),(1,3,1,28.4965,'OK',378704000),(1,3,2,0.503106,'OK',0.503106),(1,4,1,28.3387,'OK',339472000),(1,4,2,0.545011,'OK',0.545011),(1,5,1,28.2429,'OK',317651000),(1,5,2,0.614492,'OK',0.614492),(1,6,1,28.2505,'OK',319336000),(1,6,2,0.573797,'OK',0.573797),(1,7,1,28.5793,'OK',401084000),(1,7,2,0.563874,'OK',0.563874),(1,8,1,28.2456,'OK',318258000),(1,8,2,0.610982,'OK',0.610982),(1,10,1,29.5331,'OK',776871000),(1,10,2,0.484979,'OK',0.484979),(1,11,1,29.5076,'OK',763268000),(1,11,2,0.4233,'OK',0.4233),(1,12,1,29.3484,'OK',683535000),(1,12,2,0.146636,'OK',0.146636),(1,13,1,29.5929,'OK',809733000),(1,13,2,0.39487,'OK',0.39487),(1,14,1,29.665,'OK',851234000),(1,14,2,0.114227,'OK',0.114227),(1,15,1,29.3888,'OK',702916000),(1,15,2,0.244849,'OK',0.244849),(1,16,1,29.172,'OK',604846000),(1,16,2,0.400485,'OK',0.400485),(1,17,1,29.1265,'OK',586052000),(1,17,2,0.358169,'OK',0.358169),(1,18,1,29.0055,'OK',538932000),(1,18,2,0.247765,'OK',0.247765),(1,19,1,28.3386,'DANGER',339455000),(1,19,2,0.493567,'OK',0.493567),(1,20,1,26.3323,'DANGER',84492300),(1,20,2,1.69175,'OK',1.69175),(1,21,1,24.1837,'DANGER',19055700),(1,21,2,2.41,'DANGER',2.41),(1,22,1,25.3465,'OK',42662300),(1,22,2,0.38869,'OK',0.38869),(1,23,1,25.1543,'OK',37341500),(1,23,2,0.560293,'OK',0.560293),(1,24,1,26.0231,'OK',68190200),(1,24,2,0.592798,'OK',0.592798),(1,25,1,27.1468,'OK',148589000),(1,25,2,0.471737,'OK',0.471737),(1,26,1,27.6035,'OK',203932000),(1,26,2,0.472753,'OK',0.472753),(1,27,1,28.0621,'OK',280235000),(1,27,2,0.487573,'OK',0.487573),(1,28,1,28.8942,'OK',498923000),(1,28,2,0.31501,'OK',0.31501),(1,29,1,28.7935,'OK',465277000),(1,29,2,0.400505,'OK',0.400505),(1,30,1,29.432,'OK',724274000),(1,30,2,0.303769,'OK',0.303769),(1,31,1,29.8839,'OK',990694000),(1,31,2,0.276873,'OK',0.276873),(1,32,1,29.5241,'OK',772046000),(1,32,2,0.377772,'OK',0.377772),(1,33,1,29.504,'OK',761381000),(1,33,2,0.406418,'OK',0.406418),(1,34,1,30.4505,'OK',1467260000),(1,34,2,0.258808,'OK',0.258808),(1,35,1,30.1309,'OK',1175700000),(1,35,2,0.280922,'OK',0.280922),(1,36,1,30.263,'OK',1288480000),(1,36,2,0.282618,'OK',0.282618),(1,37,1,30.374,'OK',1391500000),(1,37,2,0.340758,'OK',0.340758),(1,38,1,30.2302,'OK',1259510000),(1,38,2,0.364206,'OK',0.364206),(1,39,1,30.1183,'OK',1165490000),(1,39,2,0.2713,'OK',0.2713),(1,40,1,30.1807,'OK',1216980000),(1,40,2,0.295995,'OK',0.295995),(1,41,1,29.722,'OK',885526000),(1,41,2,0.438367,'OK',0.438367),(1,42,1,30.0151,'OK',1085020000),(1,42,2,0.27591,'OK',0.27591),(1,43,1,29.8377,'OK',959499000),(1,43,2,0.372247,'OK',0.372247),(1,44,1,29.9318,'OK',1024180000),(1,44,2,0.308612,'OK',0.308612),(1,45,1,29.6888,'OK',865380000),(1,45,2,0.376892,'OK',0.376892),(1,46,1,29.6215,'OK',825973000),(1,46,2,0.338432,'OK',0.338432),(1,47,1,29.7928,'OK',930068000),(1,47,2,0.43796,'OK',0.43796),(1,48,1,29.7217,'OK',885343000),(1,48,2,0.37928,'OK',0.37928),(1,49,1,29.6285,'OK',830007000),(1,49,2,0.413442,'OK',0.413442),(1,50,1,29.6195,'OK',824826000),(1,50,2,0.37143,'OK',0.37143),(1,51,1,30.4031,'OK',1419840000),(1,51,2,0.386791,'OK',0.386791),(1,52,1,29.5743,'OK',799381000),(1,52,2,0.486709,'OK',0.486709),(1,53,1,29.4123,'OK',714455000),(1,53,2,0.505327,'OK',0.505327),(1,55,1,27.0702,'OK',140909000),(1,55,2,0.130295,'OK',0.130295),(1,55,9,NULL,'OK',754.193),(1,56,1,26.6959,'OK',108710000),(1,56,2,0.585188,'OK',0.585188),(1,56,9,NULL,'OK',751.845),(1,57,1,27.1673,'OK',150720000),(1,57,2,-0.105411,'OK',-0.105411),(1,57,9,NULL,'OK',756.481),(1,58,1,27.7643,'OK',227979000),(1,58,2,0.13208,'OK',0.13208),(1,58,9,NULL,'OK',753.204),(1,59,1,27.5425,'OK',195483000),(1,59,2,0.0340545,'OK',0.0340545),(1,59,9,NULL,'OK',756.482),(1,60,1,21.2554,'OK',2503300),(1,60,2,-0.821952,'OK',-0.821952),(1,60,9,NULL,'OK',796.523),(1,61,1,28.0316,'OK',274372000),(1,61,2,0.058941,'OK',0.058941),(1,61,9,NULL,'OK',749.059),(1,62,1,27.5177,'OK',192149000),(1,62,2,-0.0289019,'OK',-0.0289019),(1,62,9,NULL,'OK',752.183),(1,63,1,27.8589,'OK',243431000),(1,63,2,0.283416,'OK',0.283416),(1,63,9,NULL,'OK',752.557),(1,64,1,27.4674,'OK',185572000),(1,64,2,0.441082,'OK',0.441082),(1,64,9,NULL,'OK',752.159),(1,65,1,23.2493,'OK',9970850),(1,65,2,-0.679955,'OK',-0.679955),(1,65,9,0.1,'OK',764.491),(1,66,1,27.4239,'OK',180055000),(1,66,2,0.146616,'OK',0.146616),(1,66,9,-0.1,'OK',753.728),(1,67,1,28.2771,'OK',325277000),(1,67,2,0.398297,'OK',0.398297),(1,67,9,0,'OK',757.506),(1,68,1,27.1201,'OK',145874000),(1,68,2,0.0733716,'OK',0.0733716),(1,68,9,-0.1,'OK',750.496),(1,69,1,27.1652,'OK',150503000),(1,69,2,-0.053094,'OK',-0.053094),(1,69,9,-0.1,'OK',751.781),(1,70,1,27.3727,'OK',173785000),(1,70,2,0.240081,'OK',0.240081),(1,70,9,0,'OK',756.17),(1,71,1,27.1853,'OK',152616000),(1,71,2,0.188798,'OK',0.188798),(1,71,9,0,'OK',751.973),(1,72,1,27.9674,'OK',262442000),(1,72,2,0.142524,'OK',0.142524),(1,72,9,0,'OK',756.276),(1,73,1,27.8309,'OK',238747000),(1,73,2,0.217455,'OK',0.217455),(1,73,9,-0.1,'OK',750.009),(1,74,1,27.3063,'OK',165963000),(1,74,2,0.189903,'OK',0.189903),(1,74,9,0,'OK',752.901),(1,75,1,28.0718,'OK',282134000),(1,75,2,0.342766,'OK',0.342766),(1,75,9,0,'OK',753.618),(1,76,1,28.2646,'OK',322467000),(1,76,2,0.15105,'OK',0.15105),(1,76,9,-0.1,'OK',749.12),(1,77,1,27.4932,'OK',188924000),(1,77,2,0.280642,'OK',0.280642),(1,77,9,-0.1,'OK',749.593),(1,78,1,27.6442,'OK',209764000),(1,78,2,0.276611,'OK',0.276611),(1,78,9,0,'OK',751.718),(1,79,1,28.4962,'OK',378619000),(1,79,2,0.0685519,'OK',0.0685519),(1,79,9,0,'OK',750.303),(1,80,1,27.5478,'OK',196203000),(1,80,2,0.179412,'OK',0.179412),(1,80,9,0,'OK',752.503),(1,81,1,25.78,'OK',57615700),(1,81,2,-0.200723,'OK',-0.200723),(1,81,9,0,'OK',751.505),(1,82,1,28.5343,'OK',388754000),(1,82,2,0.0513655,'OK',0.0513655),(1,82,9,0.6,'OK',788.77),(1,83,1,28.2229,'OK',313283000),(1,83,2,0.0236453,'OK',0.0236453),(1,83,9,0.1,'OK',759.189),(1,84,1,28.5542,'OK',394157000),(1,84,2,0.304952,'OK',0.304952),(1,84,9,0,'OK',757.692),(1,85,1,NULL,'OK',0),(1,85,2,0,'OK',0),(1,85,9,NULL,'OK',0),(1,86,1,28.319,'OK',334874000),(1,86,2,0.195183,'OK',0.195183),(1,86,9,-0.1,'OK',750.498),(1,87,1,28.5613,'OK',396097000),(1,87,2,0.0496274,'OK',0.0496274),(1,87,9,-0.1,'OK',751.388),(1,88,1,27.1627,'OK',150242000),(1,88,2,-0.167617,'OK',-0.167617),(1,88,9,0,'OK',758.078),(1,89,1,27.1117,'OK',145026000),(1,89,2,-0.293807,'OK',-0.293807),(1,89,9,0.3,'OK',774.432),(1,90,1,21.5308,'OK',3029790),(1,90,2,-0.588745,'OK',-0.588745),(1,90,9,0.1,'OK',765.626),(1,91,1,27.5014,'OK',189994000),(1,91,2,-0.124626,'OK',-0.124626),(1,91,9,-0.1,'OK',752.935),(1,92,1,27.8852,'OK',247899000),(1,92,2,-0.0278902,'OK',-0.0278902),(1,92,9,-0.2,'OK',749.284),(1,93,1,28.122,'OK',292117000),(1,93,2,0.0109536,'OK',0.0109536),(1,93,9,-0.2,'OK',749.818),(1,94,1,28.1531,'OK',298488000),(1,94,2,-0.0871274,'OK',-0.0871274),(1,94,9,-0.1,'OK',753.469),(1,95,1,28.148,'OK',297436000),(1,95,2,-0.0673202,'OK',-0.0673202),(1,95,9,0,'OK',755.561),(1,96,1,28.5226,'OK',385625000),(1,96,2,0.0444744,'OK',0.0444744),(1,96,9,0,'OK',755.594),(1,97,1,28.4078,'OK',356130000),(1,97,2,0.0288358,'OK',0.0288358),(1,97,9,0,'OK',756.784),(1,98,1,NULL,'OK',0),(1,98,2,0,'OK',0),(1,98,9,NULL,'OK',0),(1,99,1,28.4003,'OK',354282000),(1,99,2,0.0815895,'OK',0.0815895),(1,99,9,0,'OK',757.018),(1,100,1,28.5652,'OK',397187000),(1,100,2,-0.457233,'OK',-0.457233),(1,100,9,0.4,'OK',779.944),(1,101,1,28.2577,'OK',320932000),(1,101,2,-0.24964,'OK',-0.24964),(1,101,9,0.2,'OK',768.263),(1,102,1,28.8286,'OK',476718000),(1,102,2,0.0506281,'OK',0.0506281),(1,102,9,0,'OK',755.569),(1,103,1,29.035,'OK',550067000),(1,103,2,0.0504387,'OK',0.0504387),(1,103,9,-0.1,'OK',752.202),(1,104,1,28.7515,'OK',451922000),(1,104,2,0.133681,'OK',0.133681),(1,104,9,-0.1,'OK',753.117),(1,105,1,28.8725,'OK',491465000),(1,105,2,0.0534397,'OK',0.0534397),(1,105,9,0,'OK',756.823),(1,106,1,28.7975,'OK',466580000),(1,106,2,0.172013,'OK',0.172013),(1,106,9,0,'OK',757.961),(1,107,1,28.8963,'OK',499625000),(1,107,2,-0.399712,'OK',-0.399712),(1,107,9,-0.1,'OK',754.008),(1,108,1,29.0442,'OK',553557000),(1,108,2,0.178177,'OK',0.178177),(1,108,9,-0.1,'OK',753.672),(1,109,1,28.9033,'OK',502053000),(1,109,2,-0.0175473,'OK',-0.0175473),(1,109,9,-0.1,'OK',752.969),(1,110,1,28.9881,'OK',532450000),(1,110,2,0.0249904,'OK',0.0249904),(1,110,9,-0.1,'OK',755.21),(1,111,1,28.8502,'OK',483932000),(1,111,2,-0.118993,'OK',-0.118993),(1,111,9,0,'OK',756.131),(1,112,1,28.901,'OK',501266000),(1,112,2,0.119469,'OK',0.119469),(1,112,9,0,'OK',756.372),(1,113,1,23.2442,'OK',9935720),(1,113,2,0.677633,'OK',0.677633),(1,113,9,0.1,'OK',762.834),(1,114,1,28.9425,'OK',515911000),(1,114,2,-0.179164,'OK',-0.179164),(1,114,9,0,'OK',755.913),(1,115,1,28.9431,'OK',516098000),(1,115,2,0.241838,'OK',0.241838),(1,115,9,0.1,'OK',760.253),(1,116,1,27.7158,'OK',220434000),(1,116,2,0.185071,'OK',0.185071),(1,116,9,0.1,'OK',760.259),(1,117,1,28.6441,'OK',419504000),(1,117,2,0.125338,'OK',0.125338),(1,117,9,0.1,'OK',759.784),(1,118,1,28.3949,'OK',352943000),(1,118,2,0.158149,'OK',0.158149),(1,118,9,0,'OK',754.892),(1,119,1,28.4503,'OK',366772000),(1,119,2,0.122794,'OK',0.122794),(1,119,9,0,'OK',758.889),(1,120,1,28.616,'OK',411423000),(1,120,2,0.238335,'OK',0.238335),(1,120,9,0,'OK',758.43),(1,121,1,27.9994,'OK',268329000),(1,121,2,0.0635332,'OK',0.0635332),(1,121,9,-0.1,'OK',755.114),(1,122,1,28.6311,'OK',415740000),(1,122,2,0.146138,'OK',0.146138),(1,122,9,0.1,'OK',761.304),(1,123,1,NULL,'OK',0),(1,123,2,0,'OK',0),(1,123,9,NULL,'OK',0),(1,124,1,28.6909,'OK',433340000),(1,124,2,0.233926,'OK',0.233926),(1,124,9,0,'OK',756.454),(1,125,1,28.1573,'OK',299350000),(1,125,2,0.382076,'OK',0.382076),(1,125,9,-0.1,'OK',754.621),(1,126,1,28.3636,'OK',345376000),(1,126,2,0.114224,'OK',0.114224),(1,126,9,0,'OK',755.394),(1,127,1,28.3685,'OK',346564000),(1,127,2,0.533743,'OK',0.533743),(1,127,9,0,'OK',758.048),(1,128,1,28.1814,'OK',304403000),(1,128,2,0.361965,'OK',0.361965),(1,128,9,-0.1,'OK',751.449),(1,129,1,28.3701,'OK',346941000),(1,129,2,0.630044,'OK',0.630044),(1,129,9,0,'OK',755.878),(1,130,1,27.8728,'OK',245776000),(1,130,2,0.333261,'OK',0.333261),(1,130,9,-0.1,'OK',750.445),(1,131,1,28.0244,'OK',273022000),(1,131,2,0.567598,'OK',0.567598),(1,131,9,0,'OK',756.435),(1,132,1,28.0662,'OK',281048000),(1,132,2,0.49997,'OK',0.49997),(1,132,9,0,'OK',754.371),(1,133,1,28.1189,'OK',291503000),(1,133,2,0.410738,'OK',0.410738),(1,133,9,0,'OK',755.807),(1,134,1,28.0928,'OK',286265000),(1,134,2,0.430004,'OK',0.430004),(1,134,9,-0.1,'OK',749.945),(1,135,1,NULL,'OK',0),(1,135,2,0,'OK',0),(1,135,9,NULL,'OK',0),(1,136,1,NULL,'OK',0),(1,136,2,0,'OK',0),(1,136,9,NULL,'OK',0),(1,137,1,27.628,'OK',207425000),(1,137,2,0.468053,'OK',0.468053),(1,137,9,0.1,'OK',762.789),(1,138,1,27.786,'OK',231435000),(1,138,2,0.511931,'OK',0.511931),(1,138,9,0.1,'OK',762.06),(1,139,1,28.0452,'OK',276974000),(1,139,2,0.491369,'OK',0.491369),(1,139,9,0.1,'OK',758.842),(1,140,1,28.0443,'OK',276806000),(1,140,2,0.435328,'OK',0.435328),(1,140,9,0,'OK',756.558),(1,141,1,27.9695,'OK',262825000),(1,141,2,0.499472,'OK',0.499472),(1,141,9,0,'OK',755.682),(1,142,1,27.8592,'OK',243472000),(1,142,2,0.595592,'OK',0.595592),(1,142,9,-0.1,'OK',750.748),(1,143,1,27.6011,'OK',203596000),(1,143,2,0.216252,'OK',0.216252),(1,143,9,-0.1,'OK',751.19),(1,144,1,27.8959,'OK',249741000),(1,144,2,0.549639,'OK',0.549639),(1,144,9,0,'OK',755.799),(1,145,1,26.9046,'OK',125627000),(1,145,2,0.231135,'OK',0.231135),(1,145,9,0.1,'OK',759.752),(1,146,1,27.1575,'OK',149700000),(1,146,2,0.409465,'OK',0.409465),(1,146,9,0,'OK',757.012),(1,147,1,23.5526,'OK',12303800),(1,147,2,1.12461,'OK',1.12461),(1,147,9,0.1,'OK',761.043),(1,148,9,0,'OK',754.802),(1,149,1,26.0185,'OK',67974900),(1,149,2,-0.129822,'OK',-0.129822),(1,149,9,0.3,'OK',776.183),(1,150,1,26.0205,'OK',68069300),(1,150,2,-0.0751749,'OK',-0.0751749),(1,150,9,0.3,'OK',776.861),(1,151,1,25.9072,'OK',62926300),(1,151,2,0.0257277,'OK',0.0257277),(1,151,9,0.4,'OK',781.199),(1,152,1,25.539,'OK',48752500),(1,152,2,-0.0661861,'OK',-0.0661861),(1,152,9,0.2,'OK',774.808),(1,153,1,25.6711,'OK',53429900),(1,153,2,-0.0984671,'OK',-0.0984671),(1,153,9,0.3,'OK',783.363),(1,154,1,25.801,'OK',58463600),(1,154,2,-0.0524554,'OK',-0.0524554),(1,154,9,0.2,'OK',777.385),(1,155,1,25.5113,'OK',47824700),(1,155,2,-0.183033,'OK',-0.183033),(1,155,9,0.2,'OK',782.07),(1,156,1,25.9439,'OK',64551400),(1,156,2,-0.244698,'OK',-0.244698),(1,156,9,0.1,'OK',780.017),(1,157,1,26.07,'OK',70444800),(1,157,2,-0.212919,'OK',-0.212919),(1,157,9,0.1,'OK',780.546),(1,158,1,25.5044,'OK',47598700),(1,158,2,-0.150749,'OK',-0.150749),(1,158,9,0,'OK',776.838),(1,159,1,25.6511,'OK',52693800),(1,159,2,-0.045268,'OK',-0.045268),(1,159,9,0.1,'OK',783.98),(1,160,1,25.6352,'OK',52115600),(1,160,2,-0.258369,'OK',-0.258369),(1,160,9,0,'OK',777.226),(1,161,1,25.4157,'OK',44761200),(1,161,2,-0.115823,'OK',-0.115823),(1,161,9,-0.1,'OK',775.866),(1,162,1,25.1808,'OK',38035600),(1,162,2,0.139126,'OK',0.139126),(1,162,9,0,'OK',779.762),(1,163,1,25.5889,'OK',50470700),(1,163,2,-0.128307,'OK',-0.128307),(1,163,9,0.1,'OK',783.042),(1,164,1,25.1667,'OK',37665000),(1,164,2,-0.000466672,'OK',-0.000466672),(1,164,9,0,'OK',781.113),(1,165,1,25.2548,'OK',40035200),(1,165,2,0.0795108,'OK',0.0795108),(1,165,9,0,'OK',782.097),(1,166,1,25.1848,'OK',38140600),(1,166,2,0.139933,'OK',0.139933),(1,166,9,0.1,'OK',784.222),(1,167,1,25.1637,'OK',37586400),(1,167,2,0.15453,'OK',0.15453),(1,167,9,0,'OK',779.645),(1,168,1,24.9699,'OK',32861700),(1,168,2,-0.0796029,'OK',-0.0796029),(1,168,9,-0.1,'OK',776.946),(1,169,1,25.1351,'OK',36849100),(1,169,2,0.0058163,'OK',0.0058163),(1,169,9,-0.1,'OK',775.624),(1,170,1,25.5011,'OK',47488200),(1,170,2,0.0141355,'OK',0.0141355),(1,170,9,-0.1,'OK',775.858),(1,171,1,25.1222,'OK',36520900),(1,171,2,0.0177268,'OK',0.0177268),(1,171,9,-0.1,'OK',776.037),(1,172,1,25.2459,'OK',39790700),(1,172,2,-0.0600287,'OK',-0.0600287),(1,172,9,-0.1,'OK',775.776),(1,173,1,24.2087,'OK',19388900),(1,173,2,-0.249182,'OK',-0.249182),(1,173,9,-0.1,'OK',772.857),(1,174,1,24.763,'OK',28471500),(1,174,2,-0.0765198,'OK',-0.0765198),(1,174,9,-0.1,'OK',769.782),(1,175,1,24.6519,'OK',26361400),(1,175,2,-0.105217,'OK',-0.105217),(1,175,9,0,'OK',777.421),(1,176,1,24.6791,'OK',26863300),(1,176,2,-0.167989,'OK',-0.167989),(1,176,9,-0.1,'OK',772.38),(1,177,1,24.6771,'OK',26824700),(1,177,2,0.0883976,'OK',0.0883976),(1,177,9,0,'OK',772.519),(1,178,1,24.8201,'OK',29620400),(1,178,2,-0.0211245,'OK',-0.0211245),(1,178,9,-0.1,'OK',768.465),(1,179,1,24.4598,'OK',23074900),(1,179,2,-0.0683517,'OK',-0.0683517),(1,179,9,0,'OK',770.761),(2,1,1,28.3467,'OK',341364000),(2,1,2,0.748025,'OK',0.748025),(2,2,1,28.2398,'OK',316986000),(2,2,2,0.755051,'OK',0.755051),(2,3,1,28.2686,'OK',323361000),(2,3,2,0.719641,'OK',0.719641),(2,4,1,28.1946,'OK',307190000),(2,4,2,0.787985,'OK',0.787985),(2,5,1,27.9129,'OK',252706000),(2,5,2,0.624751,'OK',0.624751),(2,6,1,27.882,'OK',247359000),(2,6,2,0.761714,'OK',0.761714),(2,7,1,28.0958,'OK',286872000),(2,7,2,0.669634,'OK',0.669634),(2,8,1,27.9461,'OK',258588000),(2,8,2,0.813443,'OK',0.813443),(2,10,1,28.9653,'OK',524126000),(2,10,2,0.480876,'OK',0.480876),(2,11,1,28.8573,'OK',486304000),(2,11,2,0.497769,'OK',0.497769),(2,12,1,29.0315,'OK',548725000),(2,12,2,0.530283,'OK',0.530283),(2,13,1,29.0904,'OK',571593000),(2,13,2,0.289553,'OK',0.289553),(2,14,1,28.9404,'OK',515138000),(2,14,2,0.513318,'OK',0.513318),(2,15,1,28.9099,'OK',504381000),(2,15,2,0.52628,'OK',0.52628),(2,16,1,28.6783,'OK',429556000),(2,16,2,0.492523,'OK',0.492523),(2,17,1,28.7263,'OK',444098000),(2,17,2,0.301112,'OK',0.301112),(2,18,1,28.4234,'OK',359996000),(2,18,2,0.522924,'OK',0.522924),(2,19,1,27.703,'WARNING',218492000),(2,19,2,0.557678,'OK',0.557678),(2,20,1,26.0429,'DANGER',69133100),(2,20,2,2.50854,'DANGER',2.50854),(2,21,1,25.8632,'WARNING',61038600),(2,21,2,2.95141,'DANGER',2.95141),(2,22,1,27.8825,'OK',247446000),(2,22,2,0.586264,'OK',0.586264),(2,23,1,27.7721,'OK',229216000),(2,23,2,0.602485,'OK',0.602485),(2,24,1,28.6233,'OK',413503000),(2,24,2,0.673074,'OK',0.673074),(2,25,1,29.6477,'OK',841086000),(2,25,2,0.391938,'OK',0.391938),(2,26,1,29.9831,'OK',1061250000),(2,26,2,0.483574,'OK',0.483574),(2,27,1,30.0642,'OK',1122620000),(2,27,2,0.446478,'OK',0.446478),(2,28,1,30.1564,'OK',1196650000),(2,28,2,0.435774,'OK',0.435774),(2,29,1,30.2958,'OK',1318100000),(2,29,2,0.455723,'OK',0.455723),(2,30,1,30.7988,'OK',1867950000),(2,30,2,0.312561,'OK',0.312561),(2,31,1,30.5576,'OK',1580330000),(2,31,2,0.423346,'OK',0.423346),(2,32,1,30.4071,'OK',1423770000),(2,32,2,0.49801,'OK',0.49801),(2,33,1,30.2881,'OK',1311090000),(2,33,2,0.482877,'OK',0.482877),(2,34,1,30.946,'OK',2068520000),(2,34,2,0.508771,'OK',0.508771),(2,35,1,30.2357,'OK',1264280000),(2,35,2,0.483125,'OK',0.483125),(2,36,1,30.1956,'OK',1229610000),(2,36,2,0.413416,'OK',0.413416),(2,37,1,30.1066,'OK',1156060000),(2,37,2,0.476218,'OK',0.476218),(2,38,1,30.082,'OK',1136510000),(2,38,2,0.440494,'OK',0.440494),(2,39,1,29.7625,'WARNING',910755000),(2,39,2,0.54043,'OK',0.54043),(2,40,1,29.8258,'OK',951610000),(2,40,2,0.320771,'OK',0.320771),(2,41,1,29.2782,'WARNING',651072000),(2,41,2,0.481261,'OK',0.481261),(2,42,1,29.5389,'OK',780005000),(2,42,2,0.503277,'OK',0.503277),(2,43,1,29.377,'OK',697185000),(2,43,2,0.56147,'OK',0.56147),(2,44,1,29.3493,'OK',683921000),(2,44,2,0.424688,'OK',0.424688),(2,45,1,29.3868,'OK',701952000),(2,45,2,0.435719,'OK',0.435719),(2,46,1,29.1057,'OK',577670000),(2,46,2,0.512631,'OK',0.512631),(2,47,1,29.3525,'OK',685472000),(2,47,2,0.610166,'OK',0.610166),(2,48,1,29.255,'OK',640650000),(2,48,2,0.532483,'OK',0.532483),(2,49,1,29.2041,'OK',618473000),(2,49,2,0.614775,'OK',0.614775),(2,50,1,29.1366,'OK',590183000),(2,50,2,0.702699,'OK',0.702699),(2,51,1,29.9056,'OK',1005720000),(2,51,2,0.588321,'OK',0.588321),(2,52,1,29.0669,'OK',562368000),(2,52,2,0.512474,'OK',0.512474),(2,53,1,28.9026,'OK',501831000),(2,53,2,0.596886,'OK',0.596886),(2,55,1,28.3126,'OK',333382000),(2,55,2,0.742298,'OK',0.742298),(2,55,9,NULL,'OK',655.655),(2,56,1,28.1791,'OK',303926000),(2,56,2,0.967351,'OK',0.967351),(2,56,9,NULL,'OK',655.791),(2,57,1,27.9642,'OK',261862000),(2,57,2,0.948137,'OK',0.948137),(2,57,9,NULL,'OK',658.674),(2,58,1,27.6747,'OK',214241000),(2,58,2,0.650885,'OK',0.650885),(2,58,9,NULL,'OK',658.397),(2,59,1,28.1305,'OK',293849000),(2,59,2,0.576504,'OK',0.576504),(2,59,9,NULL,'OK',654.52),(2,60,1,28.2783,'OK',325543000),(2,60,2,0.755962,'OK',0.755962),(2,60,9,NULL,'OK',656.855),(2,61,1,28.4818,'OK',374876000),(2,61,2,0.781912,'OK',0.781912),(2,61,9,NULL,'OK',652.876),(2,62,1,28.0511,'OK',278107000),(2,62,2,0.717412,'OK',0.717412),(2,62,9,NULL,'OK',653.219),(2,63,1,27.6913,'OK',216724000),(2,63,2,0.7738,'OK',0.7738),(2,63,9,NULL,'OK',650.107),(2,64,1,28.1084,'OK',289383000),(2,64,2,1.00425,'OK',1.00425),(2,64,9,NULL,'OK',652.091),(2,65,1,28.1862,'OK',305421000),(2,65,2,1.24261,'OK',1.24261),(2,65,9,-0.1,'OK',650.207),(2,66,1,27.222,'OK',156542000),(2,66,2,0.487131,'OK',0.487131),(2,66,9,0,'OK',655.355),(2,67,1,27.7051,'OK',218802000),(2,67,2,1.0442,'OK',1.0442),(2,67,9,0,'OK',655.253),(2,68,1,27.7487,'OK',225530000),(2,68,2,0.725049,'OK',0.725049),(2,68,9,-0.1,'OK',649.765),(2,69,1,NULL,'OK',0),(2,69,2,0,'OK',0),(2,69,9,NULL,'OK',0),(2,70,1,27.4673,'OK',185558000),(2,70,2,0.883808,'OK',0.883808),(2,70,9,0,'OK',652.298),(2,71,1,26.4404,'OK',91068500),(2,71,2,0.625612,'OK',0.625612),(2,71,9,0,'OK',652.317),(2,72,1,27.1938,'OK',153512000),(2,72,2,0.811664,'OK',0.811664),(2,72,9,0,'OK',652.102),(2,73,1,27.9045,'OK',251243000),(2,73,2,0.613944,'OK',0.613944),(2,73,9,0,'OK',650.679),(2,74,1,NULL,'OK',0),(2,74,2,0,'OK',0),(2,74,9,NULL,'OK',0),(2,75,1,NULL,'OK',0),(2,75,2,0,'OK',0),(2,75,9,NULL,'OK',0),(2,76,1,27.4334,'OK',181247000),(2,76,2,0.914675,'OK',0.914675),(2,76,9,-0.1,'OK',647.965),(2,77,1,27.3408,'OK',169984000),(2,77,2,0.809822,'OK',0.809822),(2,77,9,-0.1,'OK',647.151),(2,78,1,26.896,'OK',124880000),(2,78,2,0.817713,'OK',0.817713),(2,78,9,0,'OK',653.07),(2,79,1,27.5733,'OK',199708000),(2,79,2,0.975459,'OK',0.975459),(2,79,9,0,'OK',651.534),(2,80,1,27.4675,'OK',185585000),(2,80,2,1.14854,'OK',1.14854),(2,80,9,0,'OK',652.148),(2,81,1,27.2849,'OK',163525000),(2,81,2,0.762502,'OK',0.762502),(2,81,9,0,'OK',651.778),(2,82,1,28.229,'OK',314618000),(2,82,2,0.848436,'OK',0.848436),(2,82,9,0.6,'OK',687.294),(2,83,1,28.1134,'OK',290387000),(2,83,2,1.08193,'OK',1.08193),(2,83,9,0.1,'OK',661.661),(2,84,1,28.2381,'OK',316603000),(2,84,2,1.00125,'OK',1.00125),(2,84,9,0,'OK',653.135),(2,85,1,NULL,'OK',0),(2,85,2,0,'OK',0),(2,85,9,NULL,'OK',0),(2,86,1,28.229,'OK',314612000),(2,86,2,0.846987,'OK',0.846987),(2,86,9,0,'OK',653.395),(2,87,1,28.0827,'OK',284267000),(2,87,2,0.768552,'OK',0.768552),(2,87,9,-0.1,'OK',651.933),(2,88,1,28.3245,'OK',336150000),(2,88,2,0.706596,'OK',0.706596),(2,88,9,0.1,'OK',659.449),(2,89,1,28.4649,'OK',370504000),(2,89,2,0.77335,'OK',0.77335),(2,89,9,0.3,'OK',676.549),(2,90,1,23.5762,'OK',12506500),(2,90,2,1.00054,'OK',1.00054),(2,90,9,3,'OK',839.24),(2,91,1,28.4196,'OK',359043000),(2,91,2,0.785413,'OK',0.785413),(2,91,9,-0.5,'OK',650.572),(2,92,1,28.8209,'OK',474209000),(2,92,2,1.05462,'OK',1.05462),(2,92,9,-0.5,'OK',649.061),(2,93,1,28.9693,'OK',525576000),(2,93,2,0.793456,'OK',0.793456),(2,93,9,-0.5,'OK',647.496),(2,94,1,28.8413,'OK',480938000),(2,94,2,0.810793,'OK',0.810793),(2,94,9,-0.3,'OK',654.12),(2,95,1,28.987,'OK',532057000),(2,95,2,0.85776,'OK',0.85776),(2,95,9,-0.3,'OK',653.294),(2,96,1,29.197,'OK',615439000),(2,96,2,1.02301,'OK',1.02301),(2,96,9,-0.4,'OK',652.087),(2,97,1,29.0765,'OK',566110000),(2,97,2,0.766049,'OK',0.766049),(2,97,9,-0.4,'OK',651.526),(2,98,1,NULL,'OK',0),(2,98,2,0,'OK',0),(2,98,9,NULL,'OK',0),(2,99,1,29.0236,'OK',545726000),(2,99,2,0.652406,'OK',0.652406),(2,99,9,-0.4,'OK',652.138),(2,100,1,28.9294,'OK',511238000),(2,100,2,0.420708,'OK',0.420708),(2,100,9,0.2,'OK',681.79),(2,101,1,28.972,'OK',526535000),(2,101,2,0.666806,'OK',0.666806),(2,101,9,-0.2,'OK',664.105),(2,102,1,29.1499,'OK',595670000),(2,102,2,0.503599,'OK',0.503599),(2,102,9,0,'OK',653.814),(2,103,1,29.1199,'OK',583388000),(2,103,2,0.819983,'OK',0.819983),(2,103,9,-0.1,'OK',648.789),(2,104,1,28.996,'OK',535397000),(2,104,2,0.792036,'OK',0.792036),(2,104,9,-0.1,'OK',649.747),(2,105,1,28.9277,'OK',510630000),(2,105,2,0.405297,'OK',0.405297),(2,105,9,0,'OK',653.727),(2,106,1,28.892,'OK',498148000),(2,106,2,0.590415,'OK',0.590415),(2,106,9,0,'OK',655.729),(2,107,1,28.9388,'OK',514587000),(2,107,2,0.487846,'OK',0.487846),(2,107,9,-0.1,'OK',652.289),(2,108,1,29.0554,'OK',557889000),(2,108,2,0.357892,'OK',0.357892),(2,108,9,-0.1,'OK',652.844),(2,109,1,29.0276,'OK',547227000),(2,109,2,0.272498,'OK',0.272498),(2,109,9,0,'OK',654.74),(2,110,1,29.0076,'OK',539690000),(2,110,2,0.567025,'OK',0.567025),(2,110,9,0,'OK',655.589),(2,111,1,28.7942,'OK',465495000),(2,111,2,0.412554,'OK',0.412554),(2,111,9,0,'OK',653.529),(2,112,1,28.7426,'OK',449158000),(2,112,2,0.613671,'OK',0.613671),(2,112,9,0,'OK',653.355),(2,113,1,28.9434,'OK',516228000),(2,113,2,0.403741,'OK',0.403741),(2,113,9,-0.1,'OK',649.684),(2,114,1,28.9033,'OK',502077000),(2,114,2,0.602248,'OK',0.602248),(2,114,9,0,'OK',652.059),(2,115,1,28.8452,'OK',482245000),(2,115,2,0.769661,'OK',0.769661),(2,115,9,0.1,'OK',657.595),(2,116,1,27.525,'OK',193137000),(2,116,2,0.361802,'OK',0.361802),(2,116,9,0.1,'OK',658.966),(2,117,1,28.5563,'OK',394734000),(2,117,2,0.57742,'OK',0.57742),(2,117,9,0,'OK',657.05),(2,118,1,28.5473,'OK',392289000),(2,118,2,0.453686,'OK',0.453686),(2,118,9,-0.1,'OK',651.505),(2,119,1,28.4416,'OK',364565000),(2,119,2,0.493516,'OK',0.493516),(2,119,9,0.1,'OK',659.305),(2,120,1,28.5164,'OK',383971000),(2,120,2,0.324438,'OK',0.324438),(2,120,9,0,'OK',657.075),(2,121,1,27.9065,'OK',251595000),(2,121,2,0.409788,'OK',0.409788),(2,121,9,-0.1,'OK',650.629),(2,122,1,28.4082,'OK',356230000),(2,122,2,0.444674,'OK',0.444674),(2,122,9,0,'OK',654.708),(2,123,1,28.5537,'OK',394018000),(2,123,2,0.538547,'OK',0.538547),(2,123,9,-0.1,'OK',651.084),(2,124,1,28.4899,'OK',376975000),(2,124,2,0.483622,'OK',0.483622),(2,124,9,0,'OK',652.948),(2,125,1,27.9563,'OK',260422000),(2,125,2,0.30876,'OK',0.30876),(2,125,9,-0.1,'OK',649.828),(2,126,1,28.1025,'OK',288194000),(2,126,2,0.479223,'OK',0.479223),(2,126,9,0,'OK',653.833),(2,127,1,28.1735,'OK',302745000),(2,127,2,0.44009,'OK',0.44009),(2,127,9,0,'OK',655.899),(2,128,1,28.285,'OK',327054000),(2,128,2,0.365402,'OK',0.365402),(2,128,9,-0.1,'OK',648.556),(2,129,1,28.1941,'OK',307087000),(2,129,2,0.425354,'OK',0.425354),(2,129,9,0,'OK',652.028),(2,130,1,27.9857,'OK',265786000),(2,130,2,0.513041,'OK',0.513041),(2,130,9,-0.1,'OK',646.528),(2,131,1,27.9124,'OK',252614000),(2,131,2,0.351961,'OK',0.351961),(2,131,9,0,'OK',651.29),(2,132,1,27.9455,'OK',258478000),(2,132,2,0.284816,'OK',0.284816),(2,132,9,0,'OK',651.271),(2,133,1,27.9011,'OK',250644000),(2,133,2,0.284787,'OK',0.284787),(2,133,9,0,'OK',652.979),(2,134,1,27.654,'OK',211193000),(2,134,2,0.27083,'OK',0.27083),(2,134,9,-0.1,'OK',648.142),(2,135,1,27.5906,'OK',202112000),(2,135,2,0.257534,'OK',0.257534),(2,135,9,1.3,'OK',730.27),(2,136,1,NULL,'OK',0),(2,136,2,0,'OK',0),(2,136,9,NULL,'OK',0),(2,137,1,27.396,'OK',176612000),(2,137,2,0.203238,'OK',0.203238),(2,137,9,0,'OK',658.758),(2,138,1,27.4966,'OK',189368000),(2,138,2,0.3408,'OK',0.3408),(2,138,9,0,'OK',660.128),(2,139,1,27.6847,'OK',215740000),(2,139,2,0.205883,'OK',0.205883),(2,139,9,-0.1,'OK',656.558),(2,140,1,27.6863,'OK',215984000),(2,140,2,0.182726,'OK',0.182726),(2,140,9,-0.2,'OK',650.879),(2,141,1,27.4772,'OK',186836000),(2,141,2,0.277069,'OK',0.277069),(2,141,9,-0.2,'OK',649.992),(2,142,1,27.4725,'OK',186232000),(2,142,2,0.171779,'OK',0.171779),(2,142,9,-0.2,'OK',646.841),(2,143,1,24.0981,'OK',17957400),(2,143,2,0.521503,'OK',0.521503),(2,143,9,-0.2,'OK',650.391),(2,144,1,27.4177,'OK',179284000),(2,144,2,0.247043,'OK',0.247043),(2,144,9,-0.2,'OK',649.657),(2,145,1,28.373,'OK',347639000),(2,145,2,0.345799,'OK',0.345799),(2,145,9,-0.1,'OK',654.328),(2,146,1,28.3261,'OK',336517000),(2,146,2,0.237966,'OK',0.237966),(2,146,9,-0.1,'OK',654.133),(2,147,1,NULL,'OK',0),(2,147,2,0,'OK',0),(2,147,9,NULL,'OK',0),(2,148,9,0,'OK',653.767),(2,149,1,25.8557,'OK',60722000),(2,149,2,0.0264911,'OK',0.0264911),(2,149,9,0.1,'OK',656.494),(2,150,1,25.9579,'OK',65179000),(2,150,2,-0.101207,'OK',-0.101207),(2,150,9,0.1,'OK',659.425),(2,151,1,25.8488,'OK',60431300),(2,151,2,-0.112188,'OK',-0.112188),(2,151,9,0.2,'OK',663.967),(2,152,1,25.6203,'OK',51579500),(2,152,2,-0.266764,'OK',-0.266764),(2,152,9,0.2,'OK',664.436),(2,153,1,25.5337,'OK',48573800),(2,153,2,-0.204582,'OK',-0.204582),(2,153,9,0.2,'OK',667.239),(2,154,1,25.5868,'OK',50395200),(2,154,2,-0.119968,'OK',-0.119968),(2,154,9,0.1,'OK',663.141),(2,155,1,25.4437,'OK',45635700),(2,155,2,-0.234957,'OK',-0.234957),(2,155,9,0.2,'OK',672.537),(2,156,1,25.7401,'OK',56044800),(2,156,2,-0.078257,'OK',-0.078257),(2,156,9,0.1,'OK',666.366),(2,157,1,25.9679,'OK',65630200),(2,157,2,-0.349842,'OK',-0.349842),(2,157,9,0,'OK',662.769),(2,158,1,25.3298,'OK',42172400),(2,158,2,-0.207952,'OK',-0.207952),(2,158,9,-0.1,'OK',659.217),(2,159,1,25.3515,'OK',42810600),(2,159,2,-0.17806,'OK',-0.17806),(2,159,9,0,'OK',663.102),(2,160,1,NULL,'OK',0),(2,160,2,0,'OK',0),(2,160,9,NULL,'OK',0),(2,161,1,25.4459,'OK',45705900),(2,161,2,-0.221864,'OK',-0.221864),(2,161,9,-0.1,'OK',660.393),(2,162,1,24.8747,'OK',30763600),(2,162,2,-0.230198,'OK',-0.230198),(2,162,9,0,'OK',663.007),(2,163,1,25.4481,'OK',45777400),(2,163,2,-0.198583,'OK',-0.198583),(2,163,9,0,'OK',664.044),(2,164,1,24.883,'OK',30941400),(2,164,2,-0.170242,'OK',-0.170242),(2,164,9,0,'OK',664.091),(2,165,1,24.8494,'OK',30228300),(2,165,2,-0.282587,'OK',-0.282587),(2,165,9,0,'OK',663.297),(2,166,1,24.7838,'OK',28885100),(2,166,2,-0.270847,'OK',-0.270847),(2,166,9,0,'OK',664.671),(2,167,1,24.8253,'OK',29728500),(2,167,2,-0.201416,'OK',-0.201416),(2,167,9,0,'OK',663.643),(2,168,1,24.6848,'OK',26968900),(2,168,2,-0.220617,'OK',-0.220617),(2,168,9,0,'OK',662.514),(2,169,1,25.2578,'OK',40120800),(2,169,2,-0.426883,'OK',-0.426883),(2,169,9,0,'OK',660.569),(2,170,1,25.1158,'OK',36358700),(2,170,2,-0.225856,'OK',-0.225856),(2,170,9,0,'OK',662.639),(2,171,1,24.6342,'OK',26039000),(2,171,2,-0.208866,'OK',-0.208866),(2,171,9,0,'OK',662.512),(2,172,1,24.8642,'OK',30540000),(2,172,2,-0.33106,'OK',-0.33106),(2,172,9,0,'OK',660.886),(2,173,1,23.7572,'OK',14178500),(2,173,2,-0.434976,'OK',-0.434976),(2,173,9,0,'OK',660.19),(2,174,1,24.3175,'OK',20907300),(2,174,2,-0.229144,'OK',-0.229144),(2,174,9,-0.1,'OK',654.685),(2,175,1,24.1282,'OK',18335700),(2,175,2,-0.343087,'OK',-0.343087),(2,175,9,0,'OK',664.457),(2,176,1,24.2939,'OK',20568000),(2,176,2,-0.48086,'OK',-0.48086),(2,176,9,-0.2,'OK',652.502),(2,177,1,24.2382,'OK',19788400),(2,177,2,-0.439043,'OK',-0.439043),(2,177,9,-0.1,'OK',653.297),(2,178,1,24.4345,'OK',22673900),(2,178,2,-0.473168,'OK',-0.473168),(2,178,9,-0.1,'OK',652.599),(2,179,1,24.0093,'OK',16886200),(2,179,2,-0.41056,'OK',-0.41056),(2,179,9,0,'OK',660.198),(3,1,1,26.3901,'OK',87947200),(3,1,2,0.0526776,'OK',0.0526776),(3,2,1,26.4313,'OK',90492800),(3,2,2,0.0303134,'OK',0.0303134),(3,3,1,26.3506,'OK',85569900),(3,3,2,-0.0150769,'OK',-0.0150769),(3,4,1,26.3152,'OK',83498100),(3,4,2,-0.0757155,'OK',-0.0757155),(3,5,1,26.2042,'OK',77314100),(3,5,2,-0.0193673,'OK',-0.0193673),(3,6,1,26.3053,'OK',82923900),(3,6,2,-0.00239398,'OK',-0.00239398),(3,7,1,26.5998,'OK',101706000),(3,7,2,0.0671488,'OK',0.0671488),(3,8,1,26.3512,'OK',85603000),(3,8,2,0.108507,'OK',0.108507),(3,10,1,27.4003,'OK',177140000),(3,10,2,-0.211725,'OK',-0.211725),(3,11,1,27.3221,'OK',167795000),(3,11,2,-0.132597,'OK',-0.132597),(3,12,1,27.5401,'OK',195159000),(3,12,2,0.0189555,'OK',0.0189555),(3,13,1,27.5451,'OK',195835000),(3,13,2,-0.0285441,'OK',-0.0285441),(3,14,1,27.539,'OK',195011000),(3,14,2,-0.021843,'OK',-0.021843),(3,15,1,27.366,'OK',172978000),(3,15,2,-0.0124099,'OK',-0.0124099),(3,16,1,27.1939,'OK',153526000),(3,16,2,0.0568925,'OK',0.0568925),(3,17,1,27.1666,'OK',150652000),(3,17,2,0.00854348,'OK',0.00854348),(3,18,1,26.9894,'OK',133232000),(3,18,2,-0.0185368,'OK',-0.0185368),(3,19,1,26.3848,'DANGER',87619800),(3,19,2,0.0381888,'OK',0.0381888),(3,20,1,24.7329,'DANGER',27884200),(3,20,2,0.924272,'OK',0.924272),(3,21,1,20.4852,'DANGER',1467730),(3,21,2,0.0549542,'OK',0.0549542),(3,22,1,21.8549,'WARNING',3792930),(3,22,2,-0.147844,'OK',-0.147844),(3,23,1,20.8067,'OK',1834170),(3,23,2,-0.117745,'OK',-0.117745),(3,24,1,20.9885,'OK',2080500),(3,24,2,-0.106325,'OK',-0.106325),(3,25,1,21.9839,'OK',4147640),(3,25,2,-0.17264,'OK',-0.17264),(3,26,1,21.8501,'OK',3780510),(3,26,2,-0.013084,'OK',-0.013084),(3,27,1,22.0183,'OK',4247990),(3,27,2,-0.233567,'OK',-0.233567),(3,28,1,22.5678,'OK',6216930),(3,28,2,-0.0948352,'OK',-0.0948352),(3,29,1,22.355,'OK',5364640),(3,29,2,-0.153679,'OK',-0.153679),(3,30,1,22.9625,'OK',8173630),(3,30,2,-0.0433069,'OK',-0.0433069),(3,31,1,23.0153,'OK',8478040),(3,31,2,-0.100388,'OK',-0.100388),(3,32,1,22.8047,'OK',7326550),(3,32,2,-0.0762224,'OK',-0.0762224),(3,33,1,22.6175,'OK',6434760),(3,33,2,-0.0737336,'OK',-0.0737336),(3,34,1,23.8153,'OK',14760700),(3,34,2,-0.101291,'OK',-0.101291),(3,35,1,24.1765,'OK',18960300),(3,35,2,-0.0938386,'OK',-0.0938386),(3,36,1,24.5976,'OK',25387800),(3,36,2,-0.0526389,'OK',-0.0526389),(3,37,1,25.0626,'OK',35041500),(3,37,2,-0.0962921,'OK',-0.0962921),(3,38,1,25.3727,'OK',43445400),(3,38,2,-0.0702196,'OK',-0.0702196),(3,39,1,25.3748,'OK',43507200),(3,39,2,-0.0915357,'OK',-0.0915357),(3,40,1,25.7063,'OK',54746800),(3,40,2,-0.0538813,'OK',-0.0538813),(3,41,1,25.5485,'OK',49076400),(3,41,2,-0.0385478,'OK',-0.0385478),(3,42,1,26.0033,'OK',67263700),(3,42,2,-0.0471149,'OK',-0.0471149),(3,43,1,26.1385,'OK',73872400),(3,43,2,-0.0195525,'OK',-0.0195525),(3,44,1,26.4555,'OK',92021100),(3,44,2,0.0962675,'OK',0.0962675),(3,45,1,26.4311,'OK',90480800),(3,45,2,0.0712633,'OK',0.0712633),(3,46,1,26.4413,'OK',91125100),(3,46,2,0.16414,'OK',0.16414),(3,47,1,26.9047,'OK',125641000),(3,47,2,0.0169014,'OK',0.0169014),(3,48,1,27.0662,'OK',140518000),(3,48,2,0.114778,'OK',0.114778),(3,49,1,27.0234,'OK',136409000),(3,49,2,0.111529,'OK',0.111529),(3,50,1,27.3035,'OK',165639000),(3,50,2,0.173149,'OK',0.173149),(3,51,1,27.9912,'OK',266801000),(3,51,2,-0.0993596,'OK',-0.0993596),(3,52,1,27.3642,'OK',172755000),(3,52,2,0.0584471,'OK',0.0584471),(3,53,1,27.2327,'OK',157715000),(3,53,2,0.176252,'OK',0.176252),(3,55,1,24.1892,'OK',19128600),(3,55,2,-0.491443,'OK',-0.491443),(3,55,9,NULL,'OK',724.794),(3,56,1,24.4916,'OK',23588300),(3,56,2,-0.540297,'OK',-0.540297),(3,56,9,NULL,'OK',724.068),(3,57,1,24.1821,'OK',19034400),(3,57,2,-0.514063,'OK',-0.514063),(3,57,9,NULL,'OK',725.398),(3,58,1,24.6099,'OK',25603900),(3,58,2,-0.36438,'OK',-0.36438),(3,58,9,NULL,'OK',726.596),(3,59,1,24.3998,'OK',22134100),(3,59,2,-0.628464,'OK',-0.628464),(3,59,9,NULL,'OK',724.572),(3,60,1,24.7913,'OK',29034800),(3,60,2,-0.408715,'OK',-0.408715),(3,60,9,NULL,'OK',724.131),(3,61,1,25.4406,'OK',45537900),(3,61,2,-0.399706,'OK',-0.399706),(3,61,9,NULL,'OK',718.75),(3,62,1,24.8359,'OK',29947600),(3,62,2,-0.502837,'OK',-0.502837),(3,62,9,NULL,'OK',721.508),(3,63,1,25.2308,'OK',39375400),(3,63,2,-0.438125,'OK',-0.438125),(3,63,9,NULL,'OK',722.987),(3,64,1,26.3526,'OK',85688700),(3,64,2,-0.37161,'OK',-0.37161),(3,64,9,NULL,'OK',721.295),(3,65,1,26.1592,'OK',74940700),(3,65,2,0.00897373,'OK',0.00897373),(3,65,9,0,'OK',722.088),(3,66,1,26.1713,'OK',75567000),(3,66,2,-0.16526,'OK',-0.16526),(3,66,9,0,'OK',725.25),(3,67,1,25.733,'OK',55771300),(3,67,2,-0.0380827,'OK',-0.0380827),(3,67,9,0,'OK',724.561),(3,68,1,25.5667,'OK',49698800),(3,68,2,-0.243168,'OK',-0.243168),(3,68,9,-0.1,'OK',719.328),(3,69,1,25.1255,'OK',36603800),(3,69,2,-0.0385581,'OK',-0.0385581),(3,69,9,0,'OK',722.619),(3,70,1,25.6976,'OK',54417600),(3,70,2,-0.41895,'OK',-0.41895),(3,70,9,0,'OK',721.6),(3,71,1,25.3711,'OK',43398500),(3,71,2,-0.108328,'OK',-0.108328),(3,71,9,0,'OK',721.818),(3,72,1,26.0365,'OK',68829200),(3,72,2,-0.329601,'OK',-0.329601),(3,72,9,0.1,'OK',728.317),(3,73,1,19.5801,'OK',783791),(3,73,2,1.3343,'OK',1.3343),(3,73,9,0.3,'OK',740.21),(3,74,1,26.1426,'OK',74078800),(3,74,2,-0.226819,'OK',-0.226819),(3,74,9,-0.1,'OK',721.681),(3,75,1,26.006,'OK',67390800),(3,75,2,-0.208767,'OK',-0.208767),(3,75,9,0,'OK',724.986),(3,76,1,21.9615,'OK',4083820),(3,76,2,-0.9618,'OK',-0.9618),(3,76,9,0.4,'OK',746.327),(3,77,1,26.7677,'OK',114254000),(3,77,2,0.0116198,'OK',0.0116198),(3,77,9,-0.2,'OK',716.583),(3,78,1,26.0349,'OK',68751600),(3,78,2,-0.262992,'OK',-0.262992),(3,78,9,-0.1,'OK',719.629),(3,79,1,26.6689,'OK',106693000),(3,79,2,-0.210291,'OK',-0.210291),(3,79,9,-0.1,'OK',717.766),(3,80,1,26.4748,'OK',93262200),(3,80,2,-0.222327,'OK',-0.222327),(3,80,9,-0.1,'OK',718.244),(3,81,1,26.704,'OK',109323000),(3,81,2,-0.124644,'OK',-0.124644),(3,81,9,-0.1,'OK',718.575),(3,82,1,27.3017,'OK',165440000),(3,82,2,-0.310589,'OK',-0.310589),(3,82,9,0.5,'OK',755.674),(3,83,1,27.1486,'OK',148783000),(3,83,2,-0.1586,'OK',-0.1586),(3,83,9,0,'OK',728.92),(3,84,1,27.5732,'OK',199698000),(3,84,2,-0.0468095,'OK',-0.0468095),(3,84,9,0,'OK',729.626),(3,85,1,NULL,'OK',0),(3,85,2,0,'OK',0),(3,85,9,NULL,'OK',0),(3,86,1,27.3285,'OK',168541000),(3,86,2,-0.110249,'OK',-0.110249),(3,86,9,-0.1,'OK',719.181),(3,87,1,27.4854,'OK',187905000),(3,87,2,-0.238899,'OK',-0.238899),(3,87,9,-0.1,'OK',720.603),(3,88,1,24.7088,'OK',27421100),(3,88,2,-0.52031,'OK',-0.52031),(3,88,9,0,'OK',725.887),(3,89,1,24.727,'OK',27768900),(3,89,2,-0.428366,'OK',-0.428366),(3,89,9,0.3,'OK',743.35),(3,90,1,24.6806,'OK',26890300),(3,90,2,-0.490489,'OK',-0.490489),(3,90,9,-0.1,'OK',722.887),(3,91,1,24.8187,'OK',29591600),(3,91,2,-0.584532,'OK',-0.584532),(3,91,9,-0.1,'OK',721.936),(3,92,1,25.1848,'OK',38138800),(3,92,2,-0.430388,'OK',-0.430388),(3,92,9,-0.2,'OK',718.034),(3,93,1,25.1808,'OK',38033800),(3,93,2,-0.456499,'OK',-0.456499),(3,93,9,-0.1,'OK',719.687),(3,94,1,25.2913,'OK',41061000),(3,94,2,-0.46083,'OK',-0.46083),(3,94,9,-0.1,'OK',721.513),(3,95,1,25.1255,'OK',36603300),(3,95,2,-0.425312,'OK',-0.425312),(3,95,9,0,'OK',726.438),(3,96,1,25.5179,'OK',48044800),(3,96,2,-0.413722,'OK',-0.413722),(3,96,9,0,'OK',724.133),(3,97,1,25.381,'OK',43694700),(3,97,2,-0.463597,'OK',-0.463597),(3,97,9,0,'OK',726.655),(3,98,1,NULL,'OK',0),(3,98,2,0,'OK',0),(3,98,9,NULL,'OK',0),(3,99,1,25.6521,'OK',52729500),(3,99,2,-0.460612,'OK',-0.460612),(3,99,9,0.1,'OK',728.14),(3,100,1,25.3314,'OK',42220500),(3,100,2,-0.461976,'OK',-0.461976),(3,100,9,0.4,'OK',746.078),(3,101,1,25.3059,'OK',41478800),(3,101,2,-0.385391,'OK',-0.385391),(3,101,9,0.2,'OK',737.827),(3,102,1,25.7169,'OK',55151200),(3,102,2,-0.307516,'OK',-0.307516),(3,102,9,-0.1,'OK',723.716),(3,103,1,26.1314,'OK',73508400),(3,103,2,-0.280761,'OK',-0.280761),(3,103,9,-0.1,'OK',721.517),(3,104,1,26.084,'OK',71131100),(3,104,2,-0.447778,'OK',-0.447778),(3,104,9,-0.1,'OK',722.345),(3,105,1,26.1852,'OK',76301700),(3,105,2,-0.335627,'OK',-0.335627),(3,105,9,0,'OK',725.538),(3,106,1,26.052,'OK',69570600),(3,106,2,-0.24767,'OK',-0.24767),(3,106,9,0,'OK',727.478),(3,107,1,26.2081,'OK',77519300),(3,107,2,-0.538642,'OK',-0.538642),(3,107,9,-0.1,'OK',722.875),(3,108,1,26.3467,'OK',85337400),(3,108,2,-0.373187,'OK',-0.373187),(3,108,9,-0.1,'OK',722.097),(3,109,1,26.418,'OK',89659500),(3,109,2,-0.593697,'OK',-0.593697),(3,109,9,-0.1,'OK',721.653),(3,110,1,26.3363,'OK',84723000),(3,110,2,-0.295494,'OK',-0.295494),(3,110,9,-0.1,'OK',724.061),(3,111,1,26.4299,'OK',90404900),(3,111,2,-0.424001,'OK',-0.424001),(3,111,9,0,'OK',724.582),(3,112,1,26.5158,'OK',95952100),(3,112,2,-0.364751,'OK',-0.364751),(3,112,9,0,'OK',722.887),(3,113,1,26.4758,'OK',93330600),(3,113,2,-0.443171,'OK',-0.443171),(3,113,9,0,'OK',721.89),(3,114,1,26.406,'OK',88921300),(3,114,2,-0.338293,'OK',-0.338293),(3,114,9,0,'OK',724.953),(3,115,1,26.4927,'OK',94426000),(3,115,2,-0.0496443,'OK',-0.0496443),(3,115,9,0.1,'OK',729.215),(3,116,1,24.9831,'OK',33163500),(3,116,2,-0.356588,'OK',-0.356588),(3,116,9,0.1,'OK',728.894),(3,117,1,26.3577,'OK',85992900),(3,117,2,-0.176992,'OK',-0.176992),(3,117,9,0.1,'OK',728.072),(3,118,1,26.4198,'OK',89773400),(3,118,2,0.102275,'OK',0.102275),(3,118,9,0,'OK',726.412),(3,119,1,26.2145,'OK',77867500),(3,119,2,-0.191607,'OK',-0.191607),(3,119,9,0,'OK',727.08),(3,120,1,26.3473,'OK',85376400),(3,120,2,-0.16012,'OK',-0.16012),(3,120,9,0,'OK',726.902),(3,121,1,25.6627,'OK',53117100),(3,121,2,-0.157577,'OK',-0.157577),(3,121,9,0,'OK',725.887),(3,122,1,26.3623,'OK',86265100),(3,122,2,-0.201715,'OK',-0.201715),(3,122,9,0.1,'OK',731.559),(3,123,1,NULL,'OK',0),(3,123,2,0,'OK',0),(3,123,9,NULL,'OK',0),(3,124,1,26.4798,'OK',93588700),(3,124,2,-0.191726,'OK',-0.191726),(3,124,9,0,'OK',726.105),(3,125,1,25.8373,'OK',59952300),(3,125,2,-0.100711,'OK',-0.100711),(3,125,9,0,'OK',727.116),(3,126,1,26.5035,'OK',95137500),(3,126,2,-0.0243872,'OK',-0.0243872),(3,126,9,-0.1,'OK',724.293),(3,127,1,26.4576,'OK',92158000),(3,127,2,0.295525,'OK',0.295525),(3,127,9,0,'OK',727.979),(3,128,1,26.3483,'OK',85435800),(3,128,2,0.265462,'OK',0.265462),(3,128,9,-0.1,'OK',721.302),(3,129,1,26.4969,'OK',94704200),(3,129,2,0.225763,'OK',0.225763),(3,129,9,0,'OK',723.69),(3,130,1,26.1934,'OK',76736100),(3,130,2,0.143193,'OK',0.143193),(3,130,9,-0.1,'OK',719.676),(3,131,1,26.4692,'OK',92899200),(3,131,2,0.0961693,'OK',0.0961693),(3,131,9,0,'OK',725.654),(3,132,1,26.3511,'OK',85601700),(3,132,2,0.348047,'OK',0.348047),(3,132,9,0,'OK',723.702),(3,133,1,26.3393,'OK',84904100),(3,133,2,0.211692,'OK',0.211692),(3,133,9,0,'OK',724.539),(3,134,1,26.2337,'OK',78910200),(3,134,2,0.202417,'OK',0.202417),(3,134,9,-0.1,'OK',718.86),(3,135,1,26.2054,'OK',77374600),(3,135,2,0.19967,'OK',0.19967),(3,135,9,1.2,'OK',798.227),(3,136,1,NULL,'OK',0),(3,136,2,0,'OK',0),(3,136,9,NULL,'OK',0),(3,137,1,26.0104,'OK',67595600),(3,137,2,0.101647,'OK',0.101647),(3,137,9,0,'OK',732.351),(3,138,1,NULL,'OK',0),(3,138,2,0,'OK',0),(3,138,9,NULL,'OK',0),(3,139,1,26.393,'OK',88123100),(3,139,2,0.0898507,'OK',0.0898507),(3,139,9,0,'OK',729.344),(3,140,1,26.5916,'OK',101129000),(3,140,2,0.254004,'OK',0.254004),(3,140,9,-0.1,'OK',728.589),(3,141,1,26.32,'OK',83772900),(3,141,2,0.191143,'OK',0.191143),(3,141,9,-0.1,'OK',726.021),(3,142,1,26.2893,'OK',82011200),(3,142,2,0.280598,'OK',0.280598),(3,142,9,-0.2,'OK',719.259),(3,143,1,NULL,'OK',0),(3,143,2,0,'OK',0),(3,143,9,NULL,'OK',0),(3,144,1,26.648,'OK',105160000),(3,144,2,0.213181,'OK',0.213181),(3,144,9,-0.1,'OK',726.771),(3,145,1,24.7602,'OK',28416500),(3,145,2,0.0994886,'OK',0.0994886),(3,145,9,0,'OK',730.286),(3,146,1,24.8988,'OK',31280700),(3,146,2,0.183903,'OK',0.183903),(3,146,9,-0.1,'OK',725.725),(3,147,1,22.7546,'OK',7076440),(3,147,2,0.0759991,'OK',0.0759991),(3,147,9,-0.1,'OK',729.044),(3,148,9,-0.2,'OK',725.133),(3,149,1,24.7393,'OK',28007100),(3,149,2,0.0743274,'OK',0.0743274),(3,149,9,0.2,'OK',740.959),(3,150,1,24.761,'OK',28432300),(3,150,2,0.225953,'OK',0.225953),(3,150,9,0.3,'OK',743.313),(3,151,1,24.7543,'OK',28300300),(3,151,2,0.211597,'OK',0.211597),(3,151,9,0.3,'OK',746.583),(3,152,1,24.3752,'OK',21759800),(3,152,2,0.224132,'OK',0.224132),(3,152,9,0.1,'OK',739.754),(3,153,1,24.5671,'OK',24855700),(3,153,2,0.126732,'OK',0.126732),(3,153,9,0.2,'OK',747.316),(3,154,1,24.611,'OK',25624000),(3,154,2,0.150893,'OK',0.150893),(3,154,9,0.1,'OK',743.98),(3,155,1,24.2142,'OK',19462200),(3,155,2,0.00274371,'OK',0.00274371),(3,155,9,0.1,'OK',745.315),(3,156,1,24.8131,'OK',29478100),(3,156,2,0.121794,'OK',0.121794),(3,156,9,0.1,'OK',746.367),(3,157,1,24.7436,'OK',28091200),(3,157,2,0.260224,'OK',0.260224),(3,157,9,0.1,'OK',745.659),(3,158,1,24.4179,'OK',22413700),(3,158,2,0.158017,'OK',0.158017),(3,158,9,0,'OK',743.146),(3,159,1,24.4623,'OK',23114000),(3,159,2,0.100039,'OK',0.100039),(3,159,9,0.1,'OK',749.974),(3,160,1,24.3948,'OK',22058500),(3,160,2,0.00277,'OK',0.00277),(3,160,9,-0.1,'OK',740.927),(3,161,1,24.3138,'OK',20854300),(3,161,2,0.19097,'OK',0.19097),(3,161,9,-0.1,'OK',741.711),(3,162,1,24.3271,'OK',21046500),(3,162,2,0.240628,'OK',0.240628),(3,162,9,0,'OK',746.168),(3,163,1,24.5363,'OK',24331200),(3,163,2,0.0404297,'OK',0.0404297),(3,163,9,0.1,'OK',748.177),(3,164,1,24.0553,'OK',17432900),(3,164,2,0.280334,'OK',0.280334),(3,164,9,0,'OK',745.438),(3,165,1,24.1148,'OK',18166600),(3,165,2,0.197791,'OK',0.197791),(3,165,9,0,'OK',747.3),(3,166,1,24.2069,'OK',19363800),(3,166,2,0.357433,'OK',0.357433),(3,166,9,0.1,'OK',749.065),(3,167,1,24.2686,'OK',20211000),(3,167,2,0.166859,'OK',0.166859),(3,167,9,0,'OK',744.725),(3,168,1,24.0904,'OK',17862100),(3,168,2,0.234972,'OK',0.234972),(3,168,9,0,'OK',743.519),(3,169,1,24.1766,'OK',18962200),(3,169,2,0.29111,'OK',0.29111),(3,169,9,-0.1,'OK',741.45),(3,170,1,24.4796,'OK',23393400),(3,170,2,0.26397,'OK',0.26397),(3,170,9,0,'OK',743.418),(3,171,1,24.1937,'OK',19188600),(3,171,2,0.267227,'OK',0.267227),(3,171,9,-0.1,'OK',741.746),(3,172,1,24.3172,'OK',20903300),(3,172,2,0.298964,'OK',0.298964),(3,172,9,0,'OK',742.185),(3,173,1,23.5387,'OK',12185900),(3,173,2,0.120519,'OK',0.120519),(3,173,9,-0.1,'OK',739.263),(3,174,1,24.0462,'OK',17323600),(3,174,2,0.163123,'OK',0.163123),(3,174,9,-0.1,'OK',736.254),(3,175,1,23.975,'OK',16488700),(3,175,2,0.193884,'OK',0.193884),(3,175,9,0,'OK',742.818),(3,176,1,23.9364,'OK',16053800),(3,176,2,0.171712,'OK',0.171712),(3,176,9,-0.1,'OK',737.525),(3,177,1,23.91,'OK',15762300),(3,177,2,0.177832,'OK',0.177832),(3,177,9,-0.1,'OK',736.609),(3,178,1,24.2361,'OK',19760300),(3,178,2,0.296714,'OK',0.296714),(3,178,9,-0.1,'OK',734.159),(3,179,1,23.8899,'OK',15545000),(3,179,2,0.305184,'OK',0.305184),(3,179,9,-0.1,'OK',734.652),(4,1,1,27.2259,'OK',156969000),(4,1,2,0.650223,'OK',0.650223),(4,2,1,27.185,'OK',152578000),(4,2,2,0.599369,'OK',0.599369),(4,3,1,27.2071,'OK',154939000),(4,3,2,0.658891,'OK',0.658891),(4,4,1,27.1583,'OK',149784000),(4,4,2,0.606913,'OK',0.606913),(4,5,1,27.0258,'OK',136639000),(4,5,2,0.591378,'OK',0.591378),(4,6,1,27.1145,'OK',145304000),(4,6,2,0.686631,'OK',0.686631),(4,7,1,27.3652,'OK',172879000),(4,7,2,0.634178,'OK',0.634178),(4,8,1,27.1936,'OK',153488000),(4,8,2,0.680411,'OK',0.680411),(4,10,1,28.4657,'OK',370711000),(4,10,2,0.684458,'OK',0.684458),(4,11,1,28.489,'OK',376753000),(4,11,2,0.664262,'OK',0.664262),(4,12,1,28.5894,'OK',403880000),(4,12,2,0.628612,'OK',0.628612),(4,13,1,28.5929,'OK',404874000),(4,13,2,0.453334,'OK',0.453334),(4,14,1,28.6304,'OK',415542000),(4,14,2,0.59305,'OK',0.59305),(4,15,1,28.4282,'OK',361186000),(4,15,2,0.609125,'OK',0.609125),(4,16,1,28.2682,'OK',323289000),(4,16,2,0.623971,'OK',0.623971),(4,17,1,28.3187,'OK',334796000),(4,17,2,0.643682,'OK',0.643682),(4,18,1,28.135,'OK',294765000),(4,18,2,0.710743,'OK',0.710743),(4,19,1,27.3744,'WARNING',173982000),(4,19,2,0.734497,'OK',0.734497),(4,20,1,26.2594,'DANGER',80326400),(4,20,2,2.5363,'DANGER',2.5363),(4,21,1,26.5696,'WARNING',99599100),(4,21,2,2.44583,'DANGER',2.44583),(4,22,1,27.5812,'OK',200800000),(4,22,2,0.791984,'OK',0.791984),(4,23,1,27.0069,'OK',134865000),(4,23,2,0.761923,'OK',0.761923),(4,24,1,27.5603,'OK',197909000),(4,24,2,0.669872,'OK',0.669872),(4,25,1,27.9079,'OK',251838000),(4,25,2,0.671991,'OK',0.671991),(4,26,1,28.1751,'OK',303073000),(4,26,2,0.596677,'OK',0.596677),(4,27,1,27.9495,'OK',259196000),(4,27,2,0.702169,'OK',0.702169),(4,28,1,28.1051,'OK',288719000),(4,28,2,0.757199,'OK',0.757199),(4,29,1,28.0499,'OK',277888000),(4,29,2,0.724656,'OK',0.724656),(4,30,1,28.5999,'OK',406839000),(4,30,2,0.641891,'OK',0.641891),(4,31,1,28.2302,'OK',314869000),(4,31,2,0.595412,'OK',0.595412),(4,32,1,28.1262,'OK',292984000),(4,32,2,0.734663,'OK',0.734663),(4,33,1,27.9726,'OK',263383000),(4,33,2,0.635245,'OK',0.635245),(4,34,1,28.8159,'OK',472542000),(4,34,2,0.617556,'OK',0.617556),(4,35,1,28.1192,'OK',291554000),(4,35,2,0.727699,'OK',0.727699),(4,36,1,28.0633,'OK',280473000),(4,36,2,0.568348,'OK',0.568348),(4,37,1,28.0795,'OK',283651000),(4,37,2,0.577451,'OK',0.577451),(4,38,1,28.099,'OK',287498000),(4,38,2,0.681453,'OK',0.681453),(4,39,1,27.8902,'OK',248759000),(4,39,2,0.76904,'OK',0.76904),(4,40,1,27.9381,'OK',257167000),(4,40,2,0.741538,'OK',0.741538),(4,41,1,27.5386,'WARNING',194961000),(4,41,2,0.670756,'OK',0.670756),(4,42,1,27.7635,'OK',227842000),(4,42,2,0.652277,'OK',0.652277),(4,43,1,27.6424,'OK',209501000),(4,43,2,0.660944,'OK',0.660944),(4,44,1,27.8017,'OK',233970000),(4,44,2,0.719524,'OK',0.719524),(4,45,1,27.7526,'OK',226132000),(4,45,2,0.798952,'OK',0.798952),(4,46,1,27.6008,'OK',203554000),(4,46,2,0.612325,'OK',0.612325),(4,47,1,27.7784,'OK',230216000),(4,47,2,0.768389,'OK',0.768389),(4,48,1,27.829,'OK',238426000),(4,48,2,0.752159,'OK',0.752159),(4,49,1,27.687,'OK',216079000),(4,49,2,0.758935,'OK',0.758935),(4,50,1,27.78,'OK',230466000),(4,50,2,0.578293,'OK',0.578293),(4,51,1,28.7866,'OK',463057000),(4,51,2,0.583768,'OK',0.583768),(4,52,1,27.7414,'OK',224389000),(4,52,2,0.717744,'OK',0.717744),(4,53,1,27.6868,'OK',216053000),(4,53,2,0.747969,'OK',0.747969),(4,55,1,26.2165,'OK',77975300),(4,55,2,0.230688,'OK',0.230688),(4,55,9,NULL,'OK',641.039),(4,56,1,26.3999,'OK',88543800),(4,56,2,0.0144977,'OK',0.0144977),(4,56,9,NULL,'OK',640.485),(4,57,1,25.8287,'OK',59595000),(4,57,2,-0.0772768,'OK',-0.0772768),(4,57,9,NULL,'OK',642.295),(4,58,1,25.9021,'OK',62706300),(4,58,2,0.153543,'OK',0.153543),(4,58,9,NULL,'OK',641.517),(4,59,1,26.1896,'OK',76532400),(4,59,2,-0.0714049,'OK',-0.0714049),(4,59,9,NULL,'OK',640.356),(4,60,1,25.9621,'OK',65367900),(4,60,2,0.0539984,'OK',0.0539984),(4,60,9,NULL,'OK',640.96),(4,61,1,26.298,'OK',82506600),(4,61,2,0.108577,'OK',0.108577),(4,61,9,NULL,'OK',636.795),(4,62,1,26.4928,'OK',94434400),(4,62,2,0.137336,'OK',0.137336),(4,62,9,NULL,'OK',638.026),(4,63,1,26.4245,'OK',90069300),(4,63,2,-0.164814,'OK',-0.164814),(4,63,9,NULL,'OK',636.404),(4,64,1,26.4118,'OK',89277200),(4,64,2,-0.142092,'OK',-0.142092),(4,64,9,NULL,'OK',635.87),(4,65,1,25.7788,'OK',57570900),(4,65,2,-0.0694904,'OK',-0.0694904),(4,65,9,0,'OK',636.401),(4,66,1,25.6433,'OK',52407900),(4,66,2,0.00708066,'OK',0.00708066),(4,66,9,0,'OK',639.978),(4,67,1,25.9404,'OK',64394500),(4,67,2,-0.00111778,'OK',-0.00111778),(4,67,9,0,'OK',638.862),(4,68,1,25.8618,'OK',60976600),(4,68,2,-0.00773223,'OK',-0.00773223),(4,68,9,0,'OK',638.461),(4,69,1,24.8758,'OK',30786700),(4,69,2,0.0198662,'OK',0.0198662),(4,69,9,0,'OK',635.305),(4,70,1,NULL,'OK',0),(4,70,2,0,'OK',0),(4,70,9,NULL,'OK',0),(4,71,1,24.7016,'OK',27285600),(4,71,2,-0.191154,'OK',-0.191154),(4,71,9,0,'OK',637.382),(4,72,1,25.8821,'OK',61842800),(4,72,2,-0.257866,'OK',-0.257866),(4,72,9,0,'OK',635.187),(4,73,1,24.2826,'OK',20408100),(4,73,2,-0.767321,'OK',-0.767321),(4,73,9,0,'OK',635.733),(4,74,1,25.3692,'OK',43341100),(4,74,2,-0.405117,'OK',-0.405117),(4,74,9,0,'OK',635.509),(4,75,1,NULL,'OK',0),(4,75,2,0,'OK',0),(4,75,9,NULL,'OK',0),(4,76,1,25.7185,'OK',55213400),(4,76,2,-0.260151,'OK',-0.260151),(4,76,9,-0.1,'OK',632.765),(4,77,1,26.0947,'OK',71660100),(4,77,2,-0.0882594,'OK',-0.0882594),(4,77,9,-0.1,'OK',633.196),(4,78,1,26.0589,'OK',69907300),(4,78,2,-0.239937,'OK',-0.239937),(4,78,9,0,'OK',637.427),(4,79,1,NULL,'OK',0),(4,79,2,0,'OK',0),(4,79,9,NULL,'OK',0),(4,80,1,25.6877,'OK',54047700),(4,80,2,-0.174749,'OK',-0.174749),(4,80,9,0,'OK',636.517),(4,81,1,25.58,'OK',50158400),(4,81,2,-0.158516,'OK',-0.158516),(4,81,9,0,'OK',636.33),(4,82,1,26.7795,'OK',115196000),(4,82,2,-0.0446409,'OK',-0.0446409),(4,82,9,0.6,'OK',670.266),(4,83,1,26.8106,'OK',117701000),(4,83,2,0.0130375,'OK',0.0130375),(4,83,9,0.1,'OK',644.246),(4,84,1,26.8055,'OK',117286000),(4,84,2,0.0881669,'OK',0.0881669),(4,84,9,0,'OK',640.511),(4,85,1,NULL,'OK',0),(4,85,2,0,'OK',0),(4,85,9,NULL,'OK',0),(4,86,1,26.9201,'OK',126990000),(4,86,2,0.0893127,'OK',0.0893127),(4,86,9,-0.1,'OK',636.538),(4,87,1,26.8121,'OK',117827000),(4,87,2,-0.00976028,'OK',-0.00976028),(4,87,9,-0.1,'OK',634.349),(4,88,1,27.0919,'OK',143049000),(4,88,2,-0.000123801,'OK',-0.000123801),(4,88,9,0.1,'OK',643.468),(4,89,1,27.314,'OK',166857000),(4,89,2,0.163102,'OK',0.163102),(4,89,9,0.3,'OK',660.309),(4,90,1,NULL,'OK',0),(4,90,2,0,'OK',0),(4,90,9,NULL,'OK',0),(4,91,1,26.9167,'OK',126684000),(4,91,2,0.0569399,'OK',0.0569399),(4,91,9,-0.1,'OK',635.795),(4,92,1,27.1968,'OK',153832000),(4,92,2,0.050163,'OK',0.050163),(4,92,9,-0.2,'OK',633.038),(4,93,1,27.2189,'OK',156206000),(4,93,2,0.0427716,'OK',0.0427716),(4,93,9,-0.2,'OK',634.112),(4,94,1,27.3109,'OK',166497000),(4,94,2,0.12851,'OK',0.12851),(4,94,9,-0.1,'OK',638.89),(4,95,1,27.2998,'OK',165216000),(4,95,2,0.230112,'OK',0.230112),(4,95,9,0,'OK',640.526),(4,96,1,27.5439,'OK',195677000),(4,96,2,0.0973888,'OK',0.0973888),(4,96,9,0,'OK',637.106),(4,97,1,27.4459,'OK',182821000),(4,97,2,0.0809938,'OK',0.0809938),(4,97,9,0,'OK',638.164),(4,98,1,NULL,'OK',0),(4,98,2,0,'OK',0),(4,98,9,NULL,'OK',0),(4,99,1,27.345,'OK',170475000),(4,99,2,0.154808,'OK',0.154808),(4,99,9,0,'OK',638.441),(4,100,1,27.1451,'OK',148424000),(4,100,2,0.00328115,'OK',0.00328115),(4,100,9,0.3,'OK',659.06),(4,101,1,27.116,'OK',145452000),(4,101,2,0.0379121,'OK',0.0379121),(4,101,9,0.1,'OK',647.61),(4,102,1,27.2805,'OK',163027000),(4,102,2,0.245009,'OK',0.245009),(4,102,9,0,'OK',640.409),(4,103,1,27.5423,'OK',195457000),(4,103,2,0.284437,'OK',0.284437),(4,103,9,-0.1,'OK',636.241),(4,104,1,27.3826,'OK',174981000),(4,104,2,0.109529,'OK',0.109529),(4,104,9,-0.1,'OK',636.617),(4,105,1,27.4094,'OK',178258000),(4,105,2,0.185555,'OK',0.185555),(4,105,9,0,'OK',641.347),(4,106,1,27.2735,'OK',162236000),(4,106,2,0.253591,'OK',0.253591),(4,106,9,0,'OK',642.808),(4,107,1,27.336,'OK',169416000),(4,107,2,0.100315,'OK',0.100315),(4,107,9,0,'OK',639.726),(4,108,1,27.4942,'OK',189056000),(4,108,2,0.0994366,'OK',0.0994366),(4,108,9,-0.1,'OK',638.989),(4,109,1,27.4687,'OK',185742000),(4,109,2,0.10124,'OK',0.10124),(4,109,9,0,'OK',639.712),(4,110,1,27.3807,'OK',174743000),(4,110,2,0.128624,'OK',0.128624),(4,110,9,0,'OK',640.757),(4,111,1,27.279,'OK',162852000),(4,111,2,0.0370209,'OK',0.0370209),(4,111,9,0,'OK',638.612),(4,112,1,27.4034,'OK',177522000),(4,112,2,0.124262,'OK',0.124262),(4,112,9,0,'OK',640.96),(4,113,1,27.3965,'OK',176666000),(4,113,2,0.0789657,'OK',0.0789657),(4,113,9,0,'OK',637.688),(4,114,1,27.47,'OK',185906000),(4,114,2,0.182203,'OK',0.182203),(4,114,9,0,'OK',638.992),(4,115,1,27.452,'OK',183599000),(4,115,2,0.164272,'OK',0.164272),(4,115,9,0.1,'OK',643.58),(4,116,1,26.2113,'OK',77692000),(4,116,2,0.061381,'OK',0.061381),(4,116,9,0.1,'OK',645.391),(4,117,1,27.2146,'OK',155742000),(4,117,2,0.294388,'OK',0.294388),(4,117,9,0,'OK',642.666),(4,118,1,27.1952,'OK',153662000),(4,118,2,0.21214,'OK',0.21214),(4,118,9,0,'OK',639.261),(4,119,1,27.1938,'OK',153518000),(4,119,2,0.172484,'OK',0.172484),(4,119,9,0.1,'OK',645.048),(4,120,1,27.1956,'OK',153710000),(4,120,2,0.227554,'OK',0.227554),(4,120,9,0,'OK',642.431),(4,121,1,26.5745,'OK',99934300),(4,121,2,0.0813838,'OK',0.0813838),(4,121,9,0,'OK',638.797),(4,122,1,27.1409,'OK',147984000),(4,122,2,0.163012,'OK',0.163012),(4,122,9,0,'OK',642.597),(4,123,1,27.229,'OK',157308000),(4,123,2,0.0747854,'OK',0.0747854),(4,123,9,-0.1,'OK',638.549),(4,124,1,27.218,'OK',156110000),(4,124,2,0.208339,'OK',0.208339),(4,124,9,0,'OK',640.11),(4,125,1,26.7582,'OK',113509000),(4,125,2,0.073957,'OK',0.073957),(4,125,9,-0.1,'OK',638.531),(4,126,1,27.107,'OK',144553000),(4,126,2,0.321113,'OK',0.321113),(4,126,9,0,'OK',640.961),(4,127,1,27.1424,'OK',148138000),(4,127,2,0.381242,'OK',0.381242),(4,127,9,0,'OK',642.794),(4,128,1,27.1923,'OK',153353000),(4,128,2,0.401283,'OK',0.401283),(4,128,9,-0.1,'OK',635.951),(4,129,1,27.208,'OK',155032000),(4,129,2,0.345592,'OK',0.345592),(4,129,9,0,'OK',639.225),(4,130,1,27.0305,'OK',137082000),(4,130,2,0.32483,'OK',0.32483),(4,130,9,-0.1,'OK',634.784),(4,131,1,27.194,'OK',153539000),(4,131,2,0.330364,'OK',0.330364),(4,131,9,0,'OK',638.417),(4,132,1,27.126,'OK',146464000),(4,132,2,0.392737,'OK',0.392737),(4,132,9,0,'OK',639.286),(4,133,1,27.0919,'OK',143046000),(4,133,2,0.346249,'OK',0.346249),(4,133,9,0,'OK',639.804),(4,134,1,26.9745,'OK',131870000),(4,134,2,0.267653,'OK',0.267653),(4,134,9,-0.1,'OK',633.025),(4,135,1,26.684,'OK',107819000),(4,135,2,0.0415903,'OK',0.0415903),(4,135,9,1.2,'OK',713.174),(4,136,1,NULL,'OK',0),(4,136,2,0,'OK',0),(4,136,9,NULL,'OK',0),(4,137,1,26.5209,'OK',96293200),(4,137,2,0.109648,'OK',0.109648),(4,137,9,0,'OK',645.05),(4,138,1,NULL,'OK',0),(4,138,2,0,'OK',0),(4,138,9,NULL,'OK',0),(4,139,1,27.0361,'OK',137616000),(4,139,2,0.284922,'OK',0.284922),(4,139,9,-0.1,'OK',641.941),(4,140,1,27.015,'OK',135618000),(4,140,2,0.262724,'OK',0.262724),(4,140,9,-0.1,'OK',639.067),(4,141,1,26.8209,'OK',118545000),(4,141,2,0.236335,'OK',0.236335),(4,141,9,-0.1,'OK',637.608),(4,142,1,26.8328,'OK',119529000),(4,142,2,0.261705,'OK',0.261705),(4,142,9,-0.2,'OK',634.974),(4,143,1,NULL,'OK',0),(4,143,2,0,'OK',0),(4,143,9,NULL,'OK',0),(4,144,1,27.0006,'OK',134278000),(4,144,2,0.322828,'OK',0.322828),(4,144,9,-0.2,'OK',636.209),(4,145,1,27.0951,'OK',143361000),(4,145,2,0.220536,'OK',0.220536),(4,145,9,-0.1,'OK',641.059),(4,146,1,27.2025,'OK',154445000),(4,146,2,0.226919,'OK',0.226919),(4,146,9,-0.1,'OK',639.886),(4,147,1,25.2359,'OK',39515500),(4,147,2,0.030008,'OK',0.030008),(4,147,9,-0.1,'OK',640.216),(4,148,9,-0.1,'OK',638.875),(4,149,1,24.7664,'OK',28538100),(4,149,2,-0.122319,'OK',-0.122319),(4,149,9,0.1,'OK',643.729),(4,150,1,24.9884,'OK',33285000),(4,150,2,-0.16038,'OK',-0.16038),(4,150,9,0.1,'OK',645.139),(4,151,1,24.798,'OK',29170200),(4,151,2,-0.177208,'OK',-0.177208),(4,151,9,0.1,'OK',648.172),(4,152,1,24.8171,'OK',29559300),(4,152,2,-0.171696,'OK',-0.171696),(4,152,9,0.1,'OK',649.22),(4,153,1,24.6318,'OK',25996300),(4,153,2,-0.165435,'OK',-0.165435),(4,153,9,0.2,'OK',653.015),(4,154,1,24.6987,'OK',27229200),(4,154,2,-0.225901,'OK',-0.225901),(4,154,9,0,'OK',645.604),(4,155,1,24.8567,'OK',30381200),(4,155,2,-0.110051,'OK',-0.110051),(4,155,9,0.3,'OK',662.003),(4,156,1,24.7776,'OK',28761000),(4,156,2,-0.128897,'OK',-0.128897),(4,156,9,0.1,'OK',651.345),(4,157,1,25.1319,'OK',36765800),(4,157,2,-0.140097,'OK',-0.140097),(4,157,9,0,'OK',647.555),(4,158,1,24.6174,'OK',25737300),(4,158,2,-0.137561,'OK',-0.137561),(4,158,9,0,'OK',647.106),(4,159,1,24.538,'OK',24360500),(4,159,2,-0.119099,'OK',-0.119099),(4,159,9,0,'OK',648.583),(4,160,1,24.5695,'OK',24898200),(4,160,2,-0.150239,'OK',-0.150239),(4,160,9,0.1,'OK',655.164),(4,161,1,25.0863,'OK',35621900),(4,161,2,-0.0209462,'OK',-0.0209462),(4,161,9,-0.1,'OK',644.095),(4,162,1,24.1913,'OK',19156100),(4,162,2,-0.221748,'OK',-0.221748),(4,162,9,-0.1,'OK',644.922),(4,163,1,24.896,'OK',31220800),(4,163,2,-0.140208,'OK',-0.140208),(4,163,9,0,'OK',648.388),(4,164,1,24.4561,'OK',23015400),(4,164,2,-0.00347267,'OK',-0.00347267),(4,164,9,0,'OK',650.511),(4,165,1,24.5611,'OK',24752800),(4,165,2,-0.0592818,'OK',-0.0592818),(4,165,9,0,'OK',649.516),(4,166,1,24.4546,'OK',22991000),(4,166,2,-0.00874258,'OK',-0.00874258),(4,166,9,0,'OK',651.043),(4,167,1,24.5335,'OK',24283200),(4,167,2,-0.0623592,'OK',-0.0623592),(4,167,9,0,'OK',646.534),(4,168,1,24.3577,'OK',21498200),(4,168,2,-0.113782,'OK',-0.113782),(4,168,9,-0.1,'OK',645.104),(4,169,1,24.9411,'OK',32212700),(4,169,2,0.0221657,'OK',0.0221657),(4,169,9,0,'OK',647.378),(4,170,1,24.9249,'OK',31853000),(4,170,2,-0.0581935,'OK',-0.0581935),(4,170,9,0,'OK',647.235),(4,171,1,24.4278,'OK',22569000),(4,171,2,-0.110225,'OK',-0.110225),(4,171,9,0,'OK',645.104),(4,172,1,24.7338,'OK',27900800),(4,172,2,-0.123685,'OK',-0.123685),(4,172,9,-0.1,'OK',644.564),(4,173,1,23.7124,'OK',13744800),(4,173,2,-0.423265,'OK',-0.423265),(4,173,9,-0.1,'OK',643.044),(4,174,1,24.1726,'OK',18909400),(4,174,2,-0.223899,'OK',-0.223899),(4,174,9,-0.1,'OK',639.698),(4,175,1,24.1019,'OK',18004900),(4,175,2,-0.084928,'OK',-0.084928),(4,175,9,0,'OK',647.311),(4,176,1,24.4524,'OK',22956300),(4,176,2,-0.160981,'OK',-0.160981),(4,176,9,-0.1,'OK',639.317),(4,177,1,24.5158,'OK',23987700),(4,177,2,-0.0522123,'OK',-0.0522123),(4,177,9,0,'OK',641.7),(4,178,1,24.5021,'OK',23761700),(4,178,2,-0.165118,'OK',-0.165118),(4,178,9,0,'OK',642.488),(4,179,1,24.1679,'OK',18848300),(4,179,2,-0.0203773,'OK',-0.0203773),(4,179,9,0.1,'OK',650.58),(5,1,1,22.7594,'OK',7099980),(5,1,2,-0.227844,'OK',-0.227844),(5,2,1,22.6071,'OK',6388960),(5,2,2,-0.354912,'OK',-0.354912),(5,3,1,22.5282,'OK',6048830),(5,3,2,-0.160956,'OK',-0.160956),(5,4,1,22.5491,'OK',6136900),(5,4,2,-0.189869,'OK',-0.189869),(5,5,1,22.2607,'OK',5025070),(5,5,2,-0.332802,'OK',-0.332802),(5,6,1,22.1842,'OK',4765350),(5,6,2,-0.137539,'OK',-0.137539),(5,7,1,22.4491,'OK',5725880),(5,7,2,-0.356827,'OK',-0.356827),(5,8,1,22.3384,'OK',5303180),(5,8,2,-0.209797,'OK',-0.209797),(5,10,1,23.3639,'OK',10795500),(5,10,2,0.0131085,'OK',0.0131085),(5,11,1,23.4742,'OK',11653100),(5,11,2,0.113171,'OK',0.113171),(5,12,1,23.3719,'OK',10855400),(5,12,2,0.0899572,'OK',0.0899572),(5,13,1,23.4293,'OK',11296000),(5,13,2,-0.171432,'OK',-0.171432),(5,14,1,23.6407,'OK',13078600),(5,14,2,-0.237072,'OK',-0.237072),(5,15,1,23.1923,'OK',9584560),(5,15,2,-0.232736,'OK',-0.232736),(5,16,1,23.0621,'OK',8757850),(5,16,2,-0.140899,'OK',-0.140899),(5,17,1,22.9388,'OK',8040000),(5,17,2,-0.0603792,'OK',-0.0603792),(5,18,1,22.799,'OK',7297720),(5,18,2,-0.0309359,'OK',-0.0309359),(5,19,1,22.277,'WARNING',5082040),(5,19,2,-0.128916,'OK',-0.128916),(5,20,1,NULL,'OK',0),(5,20,2,0,'OK',0),(5,21,1,NULL,'OK',0),(5,21,2,0,'OK',0),(5,22,1,22.0564,'WARNING',4361520),(5,22,2,-0.166943,'OK',-0.166943),(5,23,1,21.7529,'WARNING',3533980),(5,23,2,-0.204486,'OK',-0.204486),(5,24,1,22.473,'OK',5821520),(5,24,2,-0.206791,'OK',-0.206791),(5,25,1,23.2714,'OK',10124600),(5,25,2,-0.267606,'OK',-0.267606),(5,26,1,23.6482,'OK',13147000),(5,26,2,-0.159382,'OK',-0.159382),(5,27,1,23.6987,'OK',13614600),(5,27,2,-0.185718,'OK',-0.185718),(5,28,1,24.0157,'OK',16960800),(5,28,2,-0.177378,'OK',-0.177378),(5,29,1,24.1129,'OK',18142700),(5,29,2,-0.216782,'OK',-0.216782),(5,30,1,24.6011,'OK',25448300),(5,30,2,-0.175567,'OK',-0.175567),(5,31,1,24.3178,'OK',20911500),(5,31,2,-0.104006,'OK',-0.104006),(5,32,1,24.323,'OK',20987500),(5,32,2,-0.19036,'OK',-0.19036),(5,33,1,24.0952,'OK',17922200),(5,33,2,-0.115101,'OK',-0.115101),(5,34,1,24.9413,'OK',32216300),(5,34,2,-0.0977811,'OK',-0.0977811),(5,35,1,24.2521,'OK',19980600),(5,35,2,-0.298675,'OK',-0.298675),(5,36,1,24.2038,'OK',19322200),(5,36,2,-0.058567,'OK',-0.058567),(5,37,1,24.2646,'OK',20153900),(5,37,2,-0.0805218,'OK',-0.0805218),(5,38,1,24.1562,'OK',18695800),(5,38,2,-0.127352,'OK',-0.127352),(5,39,1,23.921,'OK',15882800),(5,39,2,-0.116121,'OK',-0.116121),(5,40,1,23.9019,'OK',15674100),(5,40,2,-0.248131,'OK',-0.248131),(5,41,1,23.5612,'WARNING',12377300),(5,41,2,-0.141581,'OK',-0.141581),(5,42,1,23.7343,'OK',13955600),(5,42,2,-0.226445,'OK',-0.226445),(5,43,1,23.699,'OK',13617700),(5,43,2,-0.150274,'OK',-0.150274),(5,44,1,23.6573,'OK',13230100),(5,44,2,-0.316412,'OK',-0.316412),(5,45,1,23.508,'OK',11929600),(5,45,2,-0.285694,'OK',-0.285694),(5,46,1,23.3543,'OK',10724000),(5,46,2,-0.00894411,'OK',-0.00894411),(5,47,1,23.5134,'OK',11974100),(5,47,2,0.0491151,'OK',0.0491151),(5,48,1,23.6301,'OK',12982700),(5,48,2,-0.161404,'OK',-0.161404),(5,49,1,23.3086,'OK',10389400),(5,49,2,-0.373305,'OK',-0.373305),(5,50,1,23.4269,'OK',11276800),(5,50,2,-0.20592,'OK',-0.20592),(5,51,1,24.2273,'OK',19640500),(5,51,2,-0.00279776,'OK',-0.00279776),(5,52,1,23.4129,'OK',11168300),(5,52,2,-0.0661987,'OK',-0.0661987),(5,53,1,23.2812,'OK',10193600),(5,53,2,-0.161369,'OK',-0.161369),(5,55,1,21.8897,'OK',3885530),(5,55,2,-0.39301,'OK',-0.39301),(5,55,9,NULL,'OK',621.364),(5,56,1,22.3463,'OK',5332240),(5,56,2,-0.272037,'OK',-0.272037),(5,56,9,NULL,'OK',621.442),(5,57,1,22.2503,'OK',4988810),(5,57,2,-0.479177,'OK',-0.479177),(5,57,9,NULL,'OK',623.509),(5,58,1,22.3782,'OK',5451480),(5,58,2,-0.0523335,'OK',-0.0523335),(5,58,9,NULL,'OK',623.356),(5,59,1,22.4996,'OK',5929850),(5,59,2,-0.492481,'OK',-0.492481),(5,59,9,NULL,'OK',621.279),(5,60,1,22.6646,'OK',6648310),(5,60,2,-0.273269,'OK',-0.273269),(5,60,9,NULL,'OK',619.201),(5,61,1,22.8689,'OK',7659860),(5,61,2,-0.351731,'OK',-0.351731),(5,61,9,NULL,'OK',616.499),(5,62,1,22.5404,'OK',6100180),(5,62,2,-0.142973,'OK',-0.142973),(5,62,9,NULL,'OK',618.286),(5,63,1,22.7153,'OK',6886250),(5,63,2,-0.34465,'OK',-0.34465),(5,63,9,NULL,'OK',618.805),(5,64,1,22.3985,'OK',5528480),(5,64,2,-0.446736,'OK',-0.446736),(5,64,9,NULL,'OK',615.305),(5,65,1,21.5436,'OK',3056800),(5,65,2,-0.341448,'OK',-0.341448),(5,65,9,-0.1,'OK',616.635),(5,66,1,22.1346,'OK',4604560),(5,66,2,-0.193348,'OK',-0.193348),(5,66,9,0,'OK',621.875),(5,67,1,22.453,'OK',5741610),(5,67,2,-0.21694,'OK',-0.21694),(5,67,9,0,'OK',620.501),(5,68,1,22.4016,'OK',5540620),(5,68,2,-0.0996339,'OK',-0.0996339),(5,68,9,0,'OK',618.414),(5,69,1,NULL,'OK',0),(5,69,2,0,'OK',0),(5,69,9,NULL,'OK',0),(5,70,1,21.8212,'OK',3705290),(5,70,2,-0.546207,'OK',-0.546207),(5,70,9,0,'OK',619.308),(5,71,1,NULL,'OK',0),(5,71,2,0,'OK',0),(5,71,9,NULL,'OK',0),(5,72,1,21.3809,'OK',2730900),(5,72,2,-0.508521,'OK',-0.508521),(5,72,9,0,'OK',619.431),(5,73,1,NULL,'OK',0),(5,73,2,0,'OK',0),(5,73,9,NULL,'OK',0),(5,74,1,22.1454,'OK',4638900),(5,74,2,-0.238928,'OK',-0.238928),(5,74,9,-0.1,'OK',615.032),(5,75,1,22.1499,'OK',4653510),(5,75,2,-0.226724,'OK',-0.226724),(5,75,9,0,'OK',621.356),(5,76,1,NULL,'OK',0),(5,76,2,0,'OK',0),(5,76,9,NULL,'OK',0),(5,77,1,21.9215,'OK',3972170),(5,77,2,-0.157562,'OK',-0.157562),(5,77,9,-0.1,'OK',614.218),(5,78,1,NULL,'OK',0),(5,78,2,0,'OK',0),(5,78,9,NULL,'OK',0),(5,79,1,21.4235,'OK',2812680),(5,79,2,-0.418416,'OK',-0.418416),(5,79,9,0,'OK',616.527),(5,80,1,NULL,'OK',0),(5,80,2,0,'OK',0),(5,80,9,NULL,'OK',0),(5,81,1,NULL,'OK',0),(5,81,2,0,'OK',0),(5,81,9,NULL,'OK',0),(5,82,1,22.3398,'OK',5308270),(5,82,2,-0.393476,'OK',-0.393476),(5,82,9,0.5,'OK',649.291),(5,83,1,22.3632,'OK',5394870),(5,83,2,-0.386338,'OK',-0.386338),(5,83,9,0,'OK',623.356),(5,84,1,22.6397,'OK',6534970),(5,84,2,-0.614065,'OK',-0.614065),(5,84,9,0,'OK',623.038),(5,85,1,NULL,'OK',0),(5,85,2,0,'OK',0),(5,85,9,NULL,'OK',0),(5,86,1,22.4951,'OK',5911370),(5,86,2,-0.466243,'OK',-0.466243),(5,86,9,-0.1,'OK',614.757),(5,87,1,22.4201,'OK',5611920),(5,87,2,-0.392632,'OK',-0.392632),(5,87,9,-0.1,'OK',613.106),(5,88,1,22.1299,'OK',4589340),(5,88,2,-0.364468,'OK',-0.364468),(5,88,9,0,'OK',622.324),(5,89,1,22.6131,'OK',6415280),(5,89,2,0.088673,'OK',0.088673),(5,89,9,0.3,'OK',639.271),(5,90,1,22.0386,'OK',4308120),(5,90,2,-0.443422,'OK',-0.443422),(5,90,9,0,'OK',621.305),(5,91,1,22.3612,'OK',5387720),(5,91,2,-0.150906,'OK',-0.150906),(5,91,9,-0.1,'OK',616.585),(5,92,1,22.8904,'OK',7774860),(5,92,2,-0.482964,'OK',-0.482964),(5,92,9,-0.2,'OK',611.735),(5,93,1,23.0037,'OK',8410210),(5,93,2,-0.190971,'OK',-0.190971),(5,93,9,-0.1,'OK',615.788),(5,94,1,22.9504,'OK',8104880),(5,94,2,-0.490277,'OK',-0.490277),(5,94,9,0,'OK',619.171),(5,95,1,23.1889,'OK',9562220),(5,95,2,-0.0486872,'OK',-0.0486872),(5,95,9,0,'OK',621.921),(5,96,1,23.6055,'OK',12763700),(5,96,2,-0.299226,'OK',-0.299226),(5,96,9,0,'OK',619.126),(5,97,1,NULL,'OK',0),(5,97,2,0,'OK',0),(5,97,9,NULL,'OK',0),(5,98,1,23.4355,'OK',11344400),(5,98,2,-0.248301,'OK',-0.248301),(5,98,9,0.1,'OK',623.285),(5,99,1,23.4076,'OK',11127400),(5,99,2,-0.0257303,'OK',-0.0257303),(5,99,9,0,'OK',620.267),(5,100,1,23.3512,'OK',10700700),(5,100,2,0.105031,'OK',0.105031),(5,100,9,0.3,'OK',634.831),(5,101,1,23.2664,'OK',10089500),(5,101,2,-0.0960553,'OK',-0.0960553),(5,101,9,0.1,'OK',627.999),(5,102,1,23.5358,'OK',12161500),(5,102,2,-0.202565,'OK',-0.202565),(5,102,9,0,'OK',620.963),(5,103,1,23.6028,'OK',12739400),(5,103,2,-0.238666,'OK',-0.238666),(5,103,9,-0.1,'OK',617.151),(5,104,1,23.4228,'OK',11245100),(5,104,2,-0.268996,'OK',-0.268996),(5,104,9,-0.1,'OK',617.598),(5,105,1,23.4373,'OK',11358700),(5,105,2,-0.228811,'OK',-0.228811),(5,105,9,0,'OK',622.29),(5,106,1,23.2642,'OK',10074500),(5,106,2,0.0757706,'OK',0.0757706),(5,106,9,0,'OK',623.497),(5,107,1,23.2386,'OK',9897140),(5,107,2,-0.473732,'OK',-0.473732),(5,107,9,0,'OK',621.146),(5,108,1,23.4305,'OK',11305600),(5,108,2,-0.288776,'OK',-0.288776),(5,108,9,0,'OK',619.936),(5,109,1,23.3298,'OK',10543000),(5,109,2,-0.325241,'OK',-0.325241),(5,109,9,-0.1,'OK',618.868),(5,110,1,23.2916,'OK',10267300),(5,110,2,-0.501336,'OK',-0.501336),(5,110,9,0,'OK',620.454),(5,111,1,23.1853,'OK',9538340),(5,111,2,-0.425811,'OK',-0.425811),(5,111,9,0,'OK',618.006),(5,112,1,23.2658,'OK',10085900),(5,112,2,-0.380828,'OK',-0.380828),(5,112,9,0,'OK',622.534),(5,113,1,23.326,'OK',10515200),(5,113,2,-0.262969,'OK',-0.262969),(5,113,9,0,'OK',618.815),(5,114,1,23.2493,'OK',9970760),(5,114,2,-0.375132,'OK',-0.375132),(5,114,9,0,'OK',620.099),(5,115,1,23.228,'OK',9824650),(5,115,2,0.145949,'OK',0.145949),(5,115,9,0.1,'OK',623.615),(5,116,1,21.7547,'OK',3538500),(5,116,2,0.0605262,'OK',0.0605262),(5,116,9,0.1,'OK',624.835),(5,117,1,22.892,'OK',7783730),(5,117,2,0.199786,'OK',0.199786),(5,117,9,0,'OK',622.871),(5,118,1,23.0191,'OK',8500610),(5,118,2,0.201884,'OK',0.201884),(5,118,9,0,'OK',620.928),(5,119,1,22.8772,'OK',7704120),(5,119,2,0.341909,'OK',0.341909),(5,119,9,0.1,'OK',625.164),(5,120,1,22.7894,'OK',7249400),(5,120,2,-0.0943963,'OK',-0.0943963),(5,120,9,0,'OK',622.171),(5,121,1,22.0261,'OK',4270840),(5,121,2,0.320659,'OK',0.320659),(5,121,9,0,'OK',621.167),(5,122,1,22.599,'OK',6353060),(5,122,2,-0.000677381,'OK',-0.000677381),(5,122,9,0,'OK',624.322),(5,123,1,22.8095,'OK',7350900),(5,123,2,0.00879118,'OK',0.00879118),(5,123,9,-0.1,'OK',618.965),(5,124,1,NULL,'OK',0),(5,124,2,0,'OK',0),(5,124,9,NULL,'OK',0),(5,125,1,NULL,'OK',0),(5,125,2,0,'OK',0),(5,125,9,NULL,'OK',0),(5,126,1,22.7245,'OK',6930520),(5,126,2,0.175308,'OK',0.175308),(5,126,9,0,'OK',621.523),(5,127,1,22.4606,'OK',5771650),(5,127,2,0.274031,'OK',0.274031),(5,127,9,0,'OK',624.199),(5,128,1,22.5532,'OK',6154320),(5,128,2,0.0977442,'OK',0.0977442),(5,128,9,-0.1,'OK',617.492),(5,129,1,22.4854,'OK',5871860),(5,129,2,0.261917,'OK',0.261917),(5,129,9,0,'OK',620.563),(5,130,1,22.3643,'OK',5399030),(5,130,2,0.130195,'OK',0.130195),(5,130,9,-0.1,'OK',616.294),(5,131,1,NULL,'OK',0),(5,131,2,0,'OK',0),(5,131,9,NULL,'OK',0),(5,132,1,22.2559,'OK',5008330),(5,132,2,0.213127,'OK',0.213127),(5,132,9,0,'OK',621.187),(5,133,1,22.176,'OK',4738410),(5,133,2,0.0797173,'OK',0.0797173),(5,133,9,0,'OK',620.501),(5,134,1,22.1222,'OK',4564970),(5,134,2,0.23973,'OK',0.23973),(5,134,9,-0.1,'OK',612.827),(5,135,1,NULL,'OK',0),(5,135,2,0,'OK',0),(5,135,9,NULL,'OK',0),(5,136,1,NULL,'OK',0),(5,136,2,0,'OK',0),(5,136,9,NULL,'OK',0),(5,137,1,21.5,'OK',2965790),(5,137,2,0.560556,'OK',0.560556),(5,137,9,0.1,'OK',625.476),(5,138,1,NULL,'OK',0),(5,138,2,0,'OK',0),(5,138,9,NULL,'OK',0),(5,139,1,NULL,'OK',0),(5,139,2,0,'OK',0),(5,139,9,NULL,'OK',0),(5,140,1,22.0289,'OK',4279190),(5,140,2,0.237039,'OK',0.237039),(5,140,9,0,'OK',621.02),(5,141,1,21.6094,'OK',3199540),(5,141,2,0.546191,'OK',0.546191),(5,141,9,0,'OK',618.465),(5,142,1,21.7779,'OK',3595890),(5,142,2,0.317094,'OK',0.317094),(5,142,9,-0.1,'OK',616.112),(5,143,1,21.533,'OK',3034410),(5,143,2,0.133073,'OK',0.133073),(5,143,9,-0.1,'OK',615.786),(5,144,1,21.8565,'OK',3797240),(5,144,2,0.141639,'OK',0.141639),(5,144,9,0,'OK',616.974),(5,145,1,22.3277,'OK',5263800),(5,145,2,-0.0285785,'OK',-0.0285785),(5,145,9,0.1,'OK',622.693),(5,146,1,22.0192,'OK',4250450),(5,146,2,0.751318,'OK',0.751318),(5,146,9,0,'OK',620.082),(5,147,1,NULL,'OK',0),(5,147,2,0,'OK',0),(5,147,9,NULL,'OK',0),(5,148,9,0,'OK',617.328),(5,149,1,NULL,'OK',0),(5,149,2,0,'OK',0),(5,149,9,NULL,'OK',0),(5,150,1,NULL,'OK',0),(5,150,2,0,'OK',0),(5,150,9,NULL,'OK',0),(5,151,1,NULL,'OK',0),(5,151,2,0,'OK',0),(5,151,9,NULL,'OK',0),(5,152,1,NULL,'OK',0),(5,152,2,0,'OK',0),(5,152,9,NULL,'OK',0),(5,153,1,NULL,'OK',0),(5,153,2,0,'OK',0),(5,153,9,NULL,'OK',0),(5,154,1,NULL,'OK',0),(5,154,2,0,'OK',0),(5,154,9,NULL,'OK',0),(5,155,1,NULL,'OK',0),(5,155,2,0,'OK',0),(5,155,9,NULL,'OK',0),(5,156,1,NULL,'OK',0),(5,156,2,0,'OK',0),(5,156,9,NULL,'OK',0),(5,157,1,NULL,'OK',0),(5,157,2,0,'OK',0),(5,157,9,NULL,'OK',0),(5,158,1,NULL,'OK',0),(5,158,2,0,'OK',0),(5,158,9,NULL,'OK',0),(5,159,1,NULL,'OK',0),(5,159,2,0,'OK',0),(5,159,9,NULL,'OK',0),(5,160,1,NULL,'OK',0),(5,160,2,0,'OK',0),(5,160,9,NULL,'OK',0),(5,161,1,NULL,'OK',0),(5,161,2,0,'OK',0),(5,161,9,NULL,'OK',0),(5,162,1,NULL,'OK',0),(5,162,2,0,'OK',0),(5,162,9,NULL,'OK',0),(5,163,1,NULL,'OK',0),(5,163,2,0,'OK',0),(5,163,9,NULL,'OK',0),(5,164,1,NULL,'OK',0),(5,164,2,0,'OK',0),(5,164,9,NULL,'OK',0),(5,165,1,NULL,'OK',0),(5,165,2,0,'OK',0),(5,165,9,NULL,'OK',0),(5,166,1,NULL,'OK',0),(5,166,2,0,'OK',0),(5,166,9,NULL,'OK',0),(5,167,1,NULL,'OK',0),(5,167,2,0,'OK',0),(5,167,9,NULL,'OK',0),(5,168,1,NULL,'OK',0),(5,168,2,0,'OK',0),(5,168,9,NULL,'OK',0),(5,169,1,NULL,'OK',0),(5,169,2,0,'OK',0),(5,169,9,NULL,'OK',0),(5,170,1,NULL,'OK',0),(5,170,2,0,'OK',0),(5,170,9,NULL,'OK',0),(5,171,1,NULL,'OK',0),(5,171,2,0,'OK',0),(5,171,9,NULL,'OK',0),(5,172,1,NULL,'OK',0),(5,172,2,0,'OK',0),(5,172,9,NULL,'OK',0),(5,173,1,NULL,'OK',0),(5,173,2,0,'OK',0),(5,173,9,NULL,'OK',0),(5,174,1,NULL,'OK',0),(5,174,2,0,'OK',0),(5,174,9,NULL,'OK',0),(5,175,1,NULL,'OK',0),(5,175,2,0,'OK',0),(5,175,9,NULL,'OK',0),(5,176,1,NULL,'OK',0),(5,176,2,0,'OK',0),(5,176,9,NULL,'OK',0),(5,177,1,NULL,'OK',0),(5,177,2,0,'OK',0),(5,177,9,NULL,'OK',0),(5,178,1,NULL,'OK',0),(5,178,2,0,'OK',0),(5,178,9,NULL,'OK',0),(5,179,1,NULL,'OK',0),(5,179,2,0,'OK',0),(5,179,9,NULL,'OK',0),(6,1,1,24.9449,'OK',32297900),(6,1,2,0.482992,'OK',0.482992),(6,2,1,24.8461,'OK',30159800),(6,2,2,0.512597,'OK',0.512597),(6,3,1,24.7555,'OK',28324500),(6,3,2,0.461635,'OK',0.461635),(6,4,1,24.7713,'OK',28635800),(6,4,2,0.454815,'OK',0.454815),(6,5,1,24.5,'OK',23726800),(6,5,2,0.384363,'OK',0.384363),(6,6,1,24.57,'OK',24905900),(6,6,2,0.348108,'OK',0.348108),(6,7,1,24.6887,'OK',27041900),(6,7,2,0.484629,'OK',0.484629),(6,8,1,24.4447,'OK',22835100),(6,8,2,0.352918,'OK',0.352918),(6,10,1,25.3935,'OK',44075600),(6,10,2,0.388227,'OK',0.388227),(6,11,1,25.2471,'OK',39823600),(6,11,2,0.379517,'OK',0.379517),(6,12,1,25.3918,'OK',44025200),(6,12,2,0.448517,'OK',0.448517),(6,13,1,25.3843,'OK',43795600),(6,13,2,0.442691,'OK',0.442691),(6,14,1,25.3661,'OK',43248600),(6,14,2,0.491578,'OK',0.491578),(6,15,1,25.0744,'OK',35330300),(6,15,2,0.522741,'OK',0.522741),(6,16,1,24.9565,'OK',32558300),(6,16,2,0.3844,'OK',0.3844),(6,17,1,24.8489,'OK',30218200),(6,17,2,0.39932,'OK',0.39932),(6,18,1,24.7073,'OK',27393200),(6,18,2,0.353536,'OK',0.353536),(6,19,1,24.0156,'DANGER',16959500),(6,19,2,0.436166,'OK',0.436166),(6,20,1,22.1263,'DANGER',4578100),(6,20,2,1.06525,'OK',1.06525),(6,21,1,22.1297,'WARNING',4588810),(6,21,2,0.899215,'OK',0.899215),(6,22,1,24.5594,'OK',24724000),(6,22,2,0.934847,'OK',0.934847),(6,23,1,24.6076,'OK',25564500),(6,23,2,0.789767,'OK',0.789767),(6,24,1,25.4023,'OK',44345300),(6,24,2,0.824781,'OK',0.824781),(6,25,1,26.4602,'OK',92324600),(6,25,2,0.686763,'OK',0.686763),(6,26,1,26.9442,'OK',129124000),(6,26,2,0.920396,'OK',0.920396),(6,27,1,27.1851,'OK',152596000),(6,27,2,0.829446,'OK',0.829446),(6,28,1,27.4922,'OK',188790000),(6,28,2,0.779784,'OK',0.779784),(6,29,1,27.6268,'OK',207253000),(6,29,2,0.637682,'OK',0.637682),(6,30,1,28.1863,'OK',305437000),(6,30,2,0.701082,'OK',0.701082),(6,31,1,27.9023,'OK',250851000),(6,31,2,0.81637,'OK',0.81637),(6,32,1,27.9376,'OK',257068000),(6,32,2,0.697731,'OK',0.697731),(6,33,1,27.8719,'OK',245630000),(6,33,2,0.961499,'OK',0.961499),(6,34,1,28.6649,'OK',425587000),(6,34,2,0.655839,'OK',0.655839),(6,35,1,27.9451,'OK',258407000),(6,35,2,0.738908,'OK',0.738908),(6,36,1,27.7949,'OK',232854000),(6,36,2,0.618334,'OK',0.618334),(6,37,1,27.7965,'OK',233114000),(6,37,2,0.772524,'OK',0.772524),(6,38,1,27.7308,'OK',222739000),(6,38,2,0.891437,'OK',0.891437),(6,39,1,27.4016,'OK',177304000),(6,39,2,0.857321,'OK',0.857321),(6,40,1,27.2935,'OK',164504000),(6,40,2,0.904268,'OK',0.904268),(6,41,1,26.8478,'WARNING',120782000),(6,41,2,0.950958,'OK',0.950958),(6,42,1,26.9121,'OK',126283000),(6,42,2,0.866119,'OK',0.866119),(6,43,1,26.8275,'OK',119092000),(6,43,2,1.01328,'OK',1.01328),(6,44,1,26.7616,'OK',113775000),(6,44,2,0.811528,'OK',0.811528),(6,45,1,26.6064,'OK',102167000),(6,45,2,0.850427,'OK',0.850427),(6,46,1,26.4068,'OK',88969900),(6,46,2,0.990634,'OK',0.990634),(6,47,1,26.513,'OK',95765600),(6,47,2,0.964443,'OK',0.964443),(6,48,1,26.4731,'OK',93152200),(6,48,2,1.11022,'OK',1.11022),(6,49,1,26.209,'OK',77571600),(6,49,2,0.818347,'OK',0.818347),(6,50,1,26.2918,'OK',82152100),(6,50,2,0.97782,'OK',0.97782),(6,51,1,27.0734,'OK',141221000),(6,51,2,0.890364,'OK',0.890364),(6,52,1,26.1426,'OK',74078800),(6,52,2,0.95605,'OK',0.95605),(6,53,1,25.9983,'OK',67028600),(6,53,2,1.15574,'OK',1.15574),(6,55,1,24.0184,'OK',16992500),(6,55,2,-0.285373,'OK',-0.285373),(6,55,9,NULL,'OK',625.546),(6,56,1,25.6093,'OK',51187000),(6,56,2,-0.204947,'OK',-0.204947),(6,56,9,NULL,'OK',624.84),(6,57,1,25.3937,'OK',44081100),(6,57,2,-0.016593,'OK',-0.016593),(6,57,9,NULL,'OK',627.463),(6,58,1,25.2076,'OK',38748100),(6,58,2,-0.20565,'OK',-0.20565),(6,58,9,NULL,'OK',626.98),(6,59,1,25.6764,'OK',53624400),(6,59,2,-0.198031,'OK',-0.198031),(6,59,9,NULL,'OK',625.57),(6,60,1,25.9349,'OK',64148500),(6,60,2,-0.0260572,'OK',-0.0260572),(6,60,9,NULL,'OK',623.383),(6,61,1,26.008,'OK',67480000),(6,61,2,-0.101086,'OK',-0.101086),(6,61,9,NULL,'OK',620.709),(6,62,1,25.8402,'OK',60073900),(6,62,2,-0.210185,'OK',-0.210185),(6,62,9,NULL,'OK',621.99),(6,63,1,25.7357,'OK',55873500),(6,63,2,-0.0559401,'OK',-0.0559401),(6,63,9,NULL,'OK',621.776),(6,64,1,25.6839,'OK',53903600),(6,64,2,-0.274373,'OK',-0.274373),(6,64,9,NULL,'OK',620.237),(6,65,1,24.9525,'OK',32467400),(6,65,2,0.145744,'OK',0.145744),(6,65,9,-0.1,'OK',619.451),(6,66,1,25.2119,'OK',38863100),(6,66,2,-0.236587,'OK',-0.236587),(6,66,9,0,'OK',626.167),(6,67,1,25.4471,'OK',45744100),(6,67,2,0.0172157,'OK',0.0172157),(6,67,9,0,'OK',624.641),(6,68,1,25.2006,'OK',38560000),(6,68,2,-0.0348888,'OK',-0.0348888),(6,68,9,0,'OK',622.042),(6,69,1,NULL,'OK',0),(6,69,2,0,'OK',0),(6,69,9,NULL,'OK',0),(6,70,1,23.2029,'OK',9655480),(6,70,2,-0.294646,'OK',-0.294646),(6,70,9,0,'OK',622.836),(6,71,1,23.6032,'OK',12743100),(6,71,2,-0.0783986,'OK',-0.0783986),(6,71,9,0,'OK',620.519),(6,72,1,24.9874,'OK',33261800),(6,72,2,0.281093,'OK',0.281093),(6,72,9,0,'OK',621.843),(6,73,1,NULL,'OK',0),(6,73,2,0,'OK',0),(6,73,9,NULL,'OK',0),(6,74,1,25.2442,'OK',39743700),(6,74,2,-0.0330129,'OK',-0.0330129),(6,74,9,-0.1,'OK',619.115),(6,75,1,24.7182,'OK',27601500),(6,75,2,0.113497,'OK',0.113497),(6,75,9,0.1,'OK',625.19),(6,76,1,24.8309,'OK',29843300),(6,76,2,-0.104618,'OK',-0.104618),(6,76,9,-0.1,'OK',617.943),(6,77,1,24.4529,'OK',22964500),(6,77,2,-0.0538467,'OK',-0.0538467),(6,77,9,-0.1,'OK',618.164),(6,78,1,23.3682,'OK',10827500),(6,78,2,-0.468352,'OK',-0.468352),(6,78,9,0,'OK',621.272),(6,79,1,NULL,'OK',0),(6,79,2,0,'OK',0),(6,79,9,NULL,'OK',0),(6,80,1,NULL,'OK',0),(6,80,2,0,'OK',0),(6,80,9,NULL,'OK',0),(6,81,1,NULL,'OK',0),(6,81,2,0,'OK',0),(6,81,9,NULL,'OK',0),(6,82,1,25.4534,'OK',45944800),(6,82,2,-0.576276,'OK',-0.576276),(6,82,9,0.6,'OK',654.72),(6,83,1,25.4423,'OK',45592900),(6,83,2,-0.436163,'OK',-0.436163),(6,83,9,0.1,'OK',628.192),(6,84,1,25.0215,'OK',34059200),(6,84,2,-0.415063,'OK',-0.415063),(6,84,9,0,'OK',626.461),(6,85,1,NULL,'OK',0),(6,85,2,0,'OK',0),(6,85,9,NULL,'OK',0),(6,86,1,25.5025,'OK',47537000),(6,86,2,-0.36478,'OK',-0.36478),(6,86,9,-0.1,'OK',619.689),(6,87,1,24.8993,'OK',31291200),(6,87,2,-0.516989,'OK',-0.516989),(6,87,9,-0.1,'OK',617.897),(6,88,1,25.4998,'OK',47446500),(6,88,2,-0.652651,'OK',-0.652651),(6,88,9,0,'OK',627.496),(6,89,1,25.7712,'OK',57268700),(6,89,2,-0.576566,'OK',-0.576566),(6,89,9,0.3,'OK',643.881),(6,90,1,25.4921,'OK',47193700),(6,90,2,-0.395766,'OK',-0.395766),(6,90,9,0,'OK',625.501),(6,91,1,25.8353,'OK',59869500),(6,91,2,-0.273413,'OK',-0.273413),(6,91,9,-0.1,'OK',620.963),(6,92,1,25.9818,'OK',66266500),(6,92,2,-0.336206,'OK',-0.336206),(6,92,9,-0.2,'OK',616.528),(6,93,1,26.102,'OK',72023800),(6,93,2,-0.318796,'OK',-0.318796),(6,93,9,-0.1,'OK',619.248),(6,94,1,26.4773,'OK',93423500),(6,94,2,-0.41486,'OK',-0.41486),(6,94,9,0,'OK',622.855),(6,95,1,26.5872,'OK',100821000),(6,95,2,-0.291481,'OK',-0.291481),(6,95,9,0,'OK',626.084),(6,96,1,27.004,'OK',134589000),(6,96,2,-0.0960124,'OK',-0.0960124),(6,96,9,0,'OK',623.272),(6,97,1,27.0324,'OK',137270000),(6,97,2,0.0227075,'OK',0.0227075),(6,97,9,0,'OK',624.464),(6,98,1,NULL,'OK',0),(6,98,2,0,'OK',0),(6,98,9,NULL,'OK',0),(6,99,1,26.9209,'OK',127058000),(6,99,2,-0.120365,'OK',-0.120365),(6,99,9,0,'OK',624.029),(6,100,1,26.8374,'OK',119915000),(6,100,2,-0.25067,'OK',-0.25067),(6,100,9,0.4,'OK',644.71),(6,101,1,26.9769,'OK',132088000),(6,101,2,-0.187853,'OK',-0.187853),(6,101,9,0.1,'OK',633.166),(6,102,1,26.9344,'OK',128250000),(6,102,2,-0.0476267,'OK',-0.0476267),(6,102,9,0,'OK',625.938),(6,103,1,27.0039,'OK',134584000),(6,103,2,-0.0668508,'OK',-0.0668508),(6,103,9,-0.1,'OK',621.254),(6,104,1,26.7324,'OK',111495000),(6,104,2,-0.239401,'OK',-0.239401),(6,104,9,-0.1,'OK',621.193),(6,105,1,26.693,'OK',108494000),(6,105,2,-0.272691,'OK',-0.272691),(6,105,9,0,'OK',625.85),(6,106,1,26.5564,'OK',98693200),(6,106,2,-0.259882,'OK',-0.259882),(6,106,9,0,'OK',627.479),(6,107,1,26.6327,'OK',104047000),(6,107,2,-0.500872,'OK',-0.500872),(6,107,9,0,'OK',624.602),(6,108,1,26.9005,'OK',125277000),(6,108,2,-0.104498,'OK',-0.104498),(6,108,9,-0.1,'OK',623.779),(6,109,1,26.7549,'OK',113247000),(6,109,2,-0.156573,'OK',-0.156573),(6,109,9,-0.1,'OK',622.871),(6,110,1,26.588,'OK',100873000),(6,110,2,-0.289815,'OK',-0.289815),(6,110,9,0,'OK',624.395),(6,111,1,26.5583,'OK',98816700),(6,111,2,-0.192065,'OK',-0.192065),(6,111,9,-0.1,'OK',622.01),(6,112,1,26.5577,'OK',98776400),(6,112,2,-0.409923,'OK',-0.409923),(6,112,9,0,'OK',625.957),(6,113,1,26.4576,'OK',92159500),(6,113,2,-0.0725186,'OK',-0.0725186),(6,113,9,0,'OK',622.126),(6,114,1,26.3954,'OK',88268200),(6,114,2,-0.144492,'OK',-0.144492),(6,114,9,0,'OK',623.498),(6,115,1,26.4003,'OK',88567100),(6,115,2,-0.247836,'OK',-0.247836),(6,115,9,0.1,'OK',627.834),(6,116,1,25.0808,'OK',35487200),(6,116,2,-0.51899,'OK',-0.51899),(6,116,9,0.1,'OK',629.612),(6,117,1,26.0681,'OK',70351100),(6,117,2,-0.223944,'OK',-0.223944),(6,117,9,0,'OK',626.76),(6,118,1,25.9961,'OK',66927400),(6,118,2,-0.0867745,'OK',-0.0867745),(6,118,9,0,'OK',624.412),(6,119,1,25.8918,'OK',62257900),(6,119,2,-0.409702,'OK',-0.409702),(6,119,9,0.1,'OK',629.084),(6,120,1,25.8568,'OK',60766300),(6,120,2,-0.475528,'OK',-0.475528),(6,120,9,0,'OK',626.505),(6,121,1,25.2614,'OK',40220100),(6,121,2,-0.410369,'OK',-0.410369),(6,121,9,0,'OK',624.749),(6,122,1,25.7831,'OK',57743300),(6,122,2,-0.332539,'OK',-0.332539),(6,122,9,0,'OK',628.128),(6,123,1,25.7109,'OK',54923400),(6,123,2,-0.26609,'OK',-0.26609),(6,123,9,-0.1,'OK',622.701),(6,124,1,25.8987,'OK',62557300),(6,124,2,-0.0832929,'OK',-0.0832929),(6,124,9,0,'OK',624.934),(6,125,1,25.2011,'OK',38573600),(6,125,2,-0.342238,'OK',-0.342238),(6,125,9,0,'OK',623.982),(6,126,1,25.58,'OK',50157100),(6,126,2,-0.427818,'OK',-0.427818),(6,126,9,0,'OK',625.827),(6,127,1,25.5895,'OK',50488600),(6,127,2,-0.0850557,'OK',-0.0850557),(6,127,9,0,'OK',627.666),(6,128,1,25.4399,'OK',45518100),(6,128,2,-0.426779,'OK',-0.426779),(6,128,9,-0.1,'OK',621.239),(6,129,1,25.3866,'OK',43865200),(6,129,2,-0.264462,'OK',-0.264462),(6,129,9,0,'OK',624.208),(6,130,1,24.999,'OK',33530900),(6,130,2,-0.194172,'OK',-0.194172),(6,130,9,-0.1,'OK',619.822),(6,131,1,24.9363,'OK',32105200),(6,131,2,-0.29137,'OK',-0.29137),(6,131,9,0,'OK',623.279),(6,132,1,24.7791,'OK',28789900),(6,132,2,-0.232909,'OK',-0.232909),(6,132,9,0,'OK',624.773),(6,133,1,25.0661,'OK',35127600),(6,133,2,-0.221826,'OK',-0.221826),(6,133,9,0,'OK',623.941),(6,134,1,24.5864,'OK',25190400),(6,134,2,-0.132354,'OK',-0.132354),(6,134,9,-0.1,'OK',616.698),(6,135,1,NULL,'OK',0),(6,135,2,0,'OK',0),(6,135,9,NULL,'OK',0),(6,136,1,NULL,'OK',0),(6,136,2,0,'OK',0),(6,136,9,NULL,'OK',0),(6,137,1,24.1753,'OK',18944400),(6,137,2,-0.32806,'OK',-0.32806),(6,137,9,0.1,'OK',629.211),(6,138,1,NULL,'OK',0),(6,138,2,0,'OK',0),(6,138,9,NULL,'OK',0),(6,139,1,24.5071,'OK',23843400),(6,139,2,-0.261472,'OK',-0.261472),(6,139,9,0,'OK',626.358),(6,140,1,24.4866,'OK',23506900),(6,140,2,-0.265638,'OK',-0.265638),(6,140,9,0,'OK',624.105),(6,141,1,24.2633,'OK',20135900),(6,141,2,-0.195215,'OK',-0.195215),(6,141,9,0,'OK',622.059),(6,142,1,24.0327,'OK',17161800),(6,142,2,-0.172209,'OK',-0.172209),(6,142,9,-0.1,'OK',619.635),(6,143,1,23.6993,'OK',13621000),(6,143,2,-0.543359,'OK',-0.543359),(6,143,9,-0.1,'OK',619.16),(6,144,1,23.802,'OK',14625500),(6,144,2,-0.338222,'OK',-0.338222),(6,144,9,0,'OK',620.562),(6,145,1,25.1341,'OK',36822800),(6,145,2,-0.1527,'OK',-0.1527),(6,145,9,0.1,'OK',625.917),(6,146,1,25.2794,'OK',40723900),(6,146,2,-0.229703,'OK',-0.229703),(6,146,9,0,'OK',623.81),(6,147,1,24.8979,'OK',31262300),(6,147,2,0.0239887,'OK',0.0239887),(6,147,9,0.1,'OK',625.935),(6,148,9,0,'OK',621.681),(6,149,1,NULL,'OK',0),(6,149,2,0,'OK',0),(6,149,9,NULL,'OK',0),(6,150,1,22.2951,'OK',5146240),(6,150,2,-0.752561,'OK',-0.752561),(6,150,9,0.1,'OK',627.839),(6,151,1,22.0779,'OK',4426870),(6,151,2,-0.996124,'OK',-0.996124),(6,151,9,0.1,'OK',628.282),(6,152,1,22.0292,'OK',4280000),(6,152,2,-0.812119,'OK',-0.812119),(6,152,9,0.1,'OK',629.876),(6,153,1,21.859,'OK',3803820),(6,153,2,-0.659481,'OK',-0.659481),(6,153,9,0.2,'OK',634.021),(6,154,1,22.0098,'OK',4222880),(6,154,2,-0.748324,'OK',-0.748324),(6,154,9,0,'OK',624.705),(6,155,1,21.9471,'OK',4043420),(6,155,2,-0.761501,'OK',-0.761501),(6,155,9,0.3,'OK',644.789),(6,156,1,22.0612,'OK',4376040),(6,156,2,-0.702387,'OK',-0.702387),(6,156,9,0.1,'OK',631.92),(6,157,1,21.8107,'OK',3678630),(6,157,2,-0.83968,'OK',-0.83968),(6,157,9,0,'OK',628.55),(6,158,1,NULL,'OK',0),(6,158,2,0,'OK',0),(6,158,9,NULL,'OK',0),(6,159,1,21.5133,'OK',2993230),(6,159,2,-0.611578,'OK',-0.611578),(6,159,9,0,'OK',629.932),(6,160,1,21.3408,'OK',2655950),(6,160,2,-0.941367,'OK',-0.941367),(6,160,9,0.1,'OK',637.945),(6,161,1,21.7316,'OK',3482290),(6,161,2,-0.743895,'OK',-0.743895),(6,161,9,-0.1,'OK',624.855),(6,162,1,21.1533,'OK',2332200),(6,162,2,-0.671292,'OK',-0.671292),(6,162,9,-0.1,'OK',625.283),(6,163,1,NULL,'OK',0),(6,163,2,0,'OK',0),(6,163,9,NULL,'OK',0),(6,164,1,NULL,'OK',0),(6,164,2,0,'OK',0),(6,164,9,NULL,'OK',0),(6,165,1,20.9099,'OK',1970240),(6,165,2,-0.71954,'OK',-0.71954),(6,165,9,0,'OK',629.956),(6,166,1,NULL,'OK',0),(6,166,2,0,'OK',0),(6,166,9,NULL,'OK',0),(6,167,1,20.9253,'OK',1991270),(6,167,2,-0.736034,'OK',-0.736034),(6,167,9,-0.1,'OK',625.511),(6,168,1,20.4781,'OK',1460580),(6,168,2,-0.607956,'OK',-0.607956),(6,168,9,-0.1,'OK',624.641),(6,169,1,20.8968,'OK',1952440),(6,169,2,-0.674518,'OK',-0.674518),(6,169,9,0,'OK',630.282),(6,170,1,20.7559,'OK',1770750),(6,170,2,-0.687651,'OK',-0.687651),(6,170,9,0,'OK',628.632),(6,171,1,20.7463,'OK',1759000),(6,171,2,-0.856991,'OK',-0.856991),(6,171,9,-0.1,'OK',624.814),(6,172,1,20.5256,'OK',1509440),(6,172,2,-0.464408,'OK',-0.464408),(6,172,9,0,'OK',625.657),(6,173,1,NULL,'OK',0),(6,173,2,0,'OK',0),(6,173,9,NULL,'OK',0),(6,174,1,NULL,'OK',0),(6,174,2,0,'OK',0),(6,174,9,NULL,'OK',0),(6,175,1,NULL,'OK',0),(6,175,2,0,'OK',0),(6,175,9,NULL,'OK',0),(6,176,1,NULL,'OK',0),(6,176,2,0,'OK',0),(6,176,9,NULL,'OK',0),(6,177,1,NULL,'OK',0),(6,177,2,0,'OK',0),(6,177,9,NULL,'OK',0),(6,178,1,NULL,'OK',0),(6,178,2,0,'OK',0),(6,178,9,NULL,'OK',0),(6,179,1,NULL,'OK',0),(6,179,2,0,'OK',0),(6,179,9,NULL,'OK',0),(7,1,1,23.6373,'OK',13048000),(7,1,2,-0.127383,'OK',-0.127383),(7,2,1,23.5784,'OK',12525400),(7,2,2,-0.234753,'OK',-0.234753),(7,3,1,23.7587,'OK',14193200),(7,3,2,-0.116604,'OK',-0.116604),(7,4,1,23.3965,'OK',11041700),(7,4,2,-0.134124,'OK',-0.134124),(7,5,1,23.4081,'OK',11131500),(7,5,2,-0.146124,'OK',-0.146124),(7,6,1,23.163,'OK',9392180),(7,6,2,-0.0640411,'OK',-0.0640411),(7,7,1,23.4671,'OK',11596000),(7,7,2,-0.029123,'OK',-0.029123),(7,8,1,23.2139,'OK',9729470),(7,8,2,-0.132367,'OK',-0.132367),(7,10,1,25.6478,'OK',52572000),(7,10,2,-0.0706155,'OK',-0.0706155),(7,11,1,25.4124,'OK',44656700),(7,11,2,-0.0322075,'OK',-0.0322075),(7,12,1,25.6074,'OK',51121200),(7,12,2,-0.0578016,'OK',-0.0578016),(7,13,1,25.6216,'OK',51625300),(7,13,2,-0.0631268,'OK',-0.0631268),(7,14,1,25.6063,'OK',51081700),(7,14,2,-0.0704998,'OK',-0.0704998),(7,15,1,25.3158,'OK',41765100),(7,15,2,-0.0198016,'OK',-0.0198016),(7,16,1,25.1802,'OK',38018400),(7,16,2,0.00542452,'OK',0.00542452),(7,17,1,25.2274,'OK',39282800),(7,17,2,-0.0934057,'OK',-0.0934057),(7,18,1,25.0259,'OK',34161100),(7,18,2,-0.0712973,'OK',-0.0712973),(7,19,1,24.2009,'OK',19283900),(7,19,2,-0.00646879,'OK',-0.00646879),(7,20,1,22.9417,'DANGER',8056330),(7,20,2,0.438023,'OK',0.438023),(7,21,1,20.8519,'DANGER',1892540),(7,21,2,0.209091,'OK',0.209091),(7,22,1,22.4755,'OK',5831840),(7,22,2,0.0506561,'OK',0.0506561),(7,23,1,21.6175,'OK',3217590),(7,23,2,-0.058593,'OK',-0.058593),(7,24,1,22.5502,'OK',6141770),(7,24,2,0.0380126,'OK',0.0380126),(7,25,1,23.8767,'OK',15402600),(7,25,2,0.0500754,'OK',0.0500754),(7,26,1,24.4064,'OK',22236800),(7,26,2,-0.0193481,'OK',-0.0193481),(7,27,1,25.3341,'OK',42297900),(7,27,2,-0.0918707,'OK',-0.0918707),(7,28,1,25.8718,'OK',61401800),(7,28,2,-0.129435,'OK',-0.129435),(7,29,1,25.1048,'OK',36081900),(7,29,2,-0.089302,'OK',-0.089302),(7,30,1,26.6155,'OK',102815000),(7,30,2,-0.191835,'OK',-0.191835),(7,31,1,26.5782,'OK',100192000),(7,31,2,-0.155224,'OK',-0.155224),(7,32,1,26.2385,'OK',79172000),(7,32,2,-0.233135,'OK',-0.233135),(7,33,1,26.0076,'OK',67462000),(7,33,2,-0.0233602,'OK',-0.0233602),(7,34,1,27.2506,'OK',159674000),(7,34,2,-0.160315,'OK',-0.160315),(7,35,1,26.6783,'OK',107388000),(7,35,2,-0.105454,'OK',-0.105454),(7,36,1,26.8895,'OK',124322000),(7,36,2,-0.110755,'OK',-0.110755),(7,37,1,26.8612,'OK',121906000),(7,37,2,-0.215076,'OK',-0.215076),(7,38,1,26.8967,'OK',124941000),(7,38,2,-0.192003,'OK',-0.192003),(7,39,1,26.7259,'OK',110997000),(7,39,2,-0.219275,'OK',-0.219275),(7,40,1,26.7713,'OK',114542000),(7,40,2,-0.211813,'OK',-0.211813),(7,41,1,26.2519,'OK',79910900),(7,41,2,-0.286094,'OK',-0.286094),(7,42,1,26.3087,'OK',83119100),(7,42,2,-0.0855336,'OK',-0.0855336),(7,43,1,26.3171,'OK',83606100),(7,43,2,-0.284125,'OK',-0.284125),(7,44,1,26.2189,'OK',78105300),(7,44,2,-0.147199,'OK',-0.147199),(7,45,1,25.9586,'WARNING',65209700),(7,45,2,-0.137093,'OK',-0.137093),(7,46,1,25.966,'OK',65543600),(7,46,2,-0.157469,'OK',-0.157469),(7,47,1,26.1059,'OK',72221500),(7,47,2,-0.0577965,'OK',-0.0577965),(7,48,1,25.9459,'OK',64637800),(7,48,2,-0.108854,'OK',-0.108854),(7,49,1,25.8672,'OK',61206600),(7,49,2,-0.0923938,'OK',-0.0923938),(7,50,1,25.7545,'OK',56609400),(7,50,2,-0.194585,'OK',-0.194585),(7,51,1,27.1887,'OK',152971000),(7,51,2,-0.130557,'OK',-0.130557),(7,52,1,25.722,'OK',55346300),(7,52,2,-0.183904,'OK',-0.183904),(7,53,1,25.4016,'OK',44323300),(7,53,2,-0.174222,'OK',-0.174222),(7,55,1,24.2837,'OK',20422600),(7,55,2,-0.592907,'OK',-0.592907),(7,55,9,NULL,'OK',742.611),(7,56,1,25.6189,'OK',51530200),(7,56,2,-0.749435,'OK',-0.749435),(7,56,9,NULL,'OK',741.786),(7,57,1,24.5505,'OK',24572500),(7,57,2,-0.400122,'OK',-0.400122),(7,57,9,NULL,'OK',744.139),(7,58,1,25.5972,'OK',50760000),(7,58,2,-0.825094,'OK',-0.825094),(7,58,9,NULL,'OK',744.675),(7,59,1,24.3899,'OK',21983800),(7,59,2,-0.609793,'OK',-0.609793),(7,59,9,NULL,'OK',744.308),(7,60,1,24.8517,'OK',30277500),(7,60,2,-0.643173,'OK',-0.643173),(7,60,9,NULL,'OK',743.365),(7,61,1,25.5275,'OK',48367800),(7,61,2,-0.725296,'OK',-0.725296),(7,61,9,NULL,'OK',738.224),(7,62,1,25.6499,'OK',52649500),(7,62,2,-0.810011,'OK',-0.810011),(7,62,9,NULL,'OK',740.49),(7,63,1,25.5135,'OK',47898400),(7,63,2,-0.571129,'OK',-0.571129),(7,63,9,NULL,'OK',738.883),(7,64,1,26.2066,'OK',77440000),(7,64,2,-0.812258,'OK',-0.812258),(7,64,9,NULL,'OK',741.04),(7,65,1,24.8031,'OK',29273900),(7,65,2,-0.930904,'OK',-0.930904),(7,65,9,0,'OK',740.955),(7,66,1,25.6132,'OK',51325900),(7,66,2,-0.927545,'OK',-0.927545),(7,66,9,0,'OK',742.973),(7,67,1,20.034,'OK',1073560),(7,67,2,-0.448011,'OK',-0.448011),(7,67,9,0.2,'OK',756.826),(7,68,1,24.9289,'OK',31941000),(7,68,2,-0.635162,'OK',-0.635162),(7,68,9,-0.1,'OK',736.482),(7,69,1,24.8206,'OK',29631100),(7,69,2,-0.739462,'OK',-0.739462),(7,69,9,0,'OK',740.937),(7,70,1,24.5901,'OK',25255300),(7,70,2,-0.757315,'OK',-0.757315),(7,70,9,0,'OK',744.144),(7,71,1,24.4074,'OK',22252000),(7,71,2,-0.633439,'OK',-0.633439),(7,71,9,0,'OK',740.018),(7,72,1,25.2494,'OK',39885600),(7,72,2,-1.08756,'OK',-1.08756),(7,72,9,0,'OK',743.96),(7,73,1,24.9418,'OK',32227800),(7,73,2,-0.780481,'OK',-0.780481),(7,73,9,-0.1,'OK',739.502),(7,74,1,25.5334,'OK',48563500),(7,74,2,-0.883462,'OK',-0.883462),(7,74,9,0,'OK',740.914),(7,75,1,25.3864,'OK',43859600),(7,75,2,-0.860172,'OK',-0.860172),(7,75,9,0,'OK',743.625),(7,76,1,25.1791,'OK',37989500),(7,76,2,-0.675124,'OK',-0.675124),(7,76,9,-0.1,'OK',736.464),(7,77,1,24.5229,'OK',24106400),(7,77,2,-0.800311,'OK',-0.800311),(7,77,9,-0.1,'OK',738.174),(7,78,1,25.298,'OK',41252100),(7,78,2,-0.750322,'OK',-0.750322),(7,78,9,0,'OK',739.074),(7,79,1,24.7854,'OK',28916000),(7,79,2,-0.934938,'OK',-0.934938),(7,79,9,0,'OK',740.163),(7,80,1,24.3184,'OK',20920600),(7,80,2,-0.757178,'OK',-0.757178),(7,80,9,0,'OK',742.617),(7,81,1,24.6294,'OK',25952700),(7,81,2,-0.786875,'OK',-0.786875),(7,81,9,0,'OK',742.054),(7,82,1,25.8504,'OK',60496800),(7,82,2,-0.724514,'OK',-0.724514),(7,82,9,0.6,'OK',779.076),(7,83,1,26.0194,'OK',68015700),(7,83,2,-0.912096,'OK',-0.912096),(7,83,9,0.1,'OK',749.02),(7,84,1,25.8833,'OK',61894400),(7,84,2,-0.651488,'OK',-0.651488),(7,84,9,0.1,'OK',748.359),(7,85,1,NULL,'OK',0),(7,85,2,0,'OK',0),(7,85,9,NULL,'OK',0),(7,86,1,25.6803,'OK',53771500),(7,86,2,-1.01791,'OK',-1.01791),(7,86,9,-0.1,'OK',740.348),(7,87,1,25.3729,'OK',43451300),(7,87,2,-0.959064,'OK',-0.959064),(7,87,9,-0.1,'OK',740.31),(7,88,1,24.6606,'OK',26521100),(7,88,2,-0.677648,'OK',-0.677648),(7,88,9,0,'OK',746.962),(7,89,1,24.7612,'OK',28435500),(7,89,2,-0.728937,'OK',-0.728937),(7,89,9,0.3,'OK',763.675),(7,90,1,NULL,'OK',0),(7,90,2,0,'OK',0),(7,90,9,NULL,'OK',0),(7,91,1,24.7731,'OK',28671600),(7,91,2,-0.639486,'OK',-0.639486),(7,91,9,-0.1,'OK',742.006),(7,92,1,25.2138,'OK',38915500),(7,92,2,-0.618527,'OK',-0.618527),(7,92,9,-0.2,'OK',737.754),(7,93,1,25.3211,'OK',41919400),(7,93,2,-0.812841,'OK',-0.812841),(7,93,9,-0.2,'OK',738.167),(7,94,1,25.4554,'OK',46007600),(7,94,2,-0.56257,'OK',-0.56257),(7,94,9,-0.1,'OK',742.908),(7,95,1,25.4152,'OK',44742800),(7,95,2,-0.768205,'OK',-0.768205),(7,95,9,0,'OK',744.791),(7,96,1,26.0584,'OK',69881400),(7,96,2,-0.750271,'OK',-0.750271),(7,96,9,0,'OK',744.031),(7,97,1,25.7096,'OK',54874300),(7,97,2,-0.680716,'OK',-0.680716),(7,97,9,0,'OK',746.329),(7,98,1,NULL,'OK',0),(7,98,2,0,'OK',0),(7,98,9,NULL,'OK',0),(7,99,1,26.0451,'OK',69237700),(7,99,2,-0.615275,'OK',-0.615275),(7,99,9,0,'OK',745.853),(7,100,1,26.1934,'OK',76737800),(7,100,2,-0.775677,'OK',-0.775677),(7,100,9,0.5,'OK',775.74),(7,101,1,25.9079,'OK',62958800),(7,101,2,-0.706697,'OK',-0.706697),(7,101,9,0.2,'OK',760.13),(7,102,1,26.3825,'OK',87484500),(7,102,2,-0.58872,'OK',-0.58872),(7,102,9,-0.1,'OK',743.999),(7,103,1,26.6549,'OK',105660000),(7,103,2,-0.989528,'OK',-0.989528),(7,103,9,-0.1,'OK',739.014),(7,104,1,26.2086,'OK',77547700),(7,104,2,-0.538693,'OK',-0.538693),(7,104,9,-0.1,'OK',741.005),(7,105,1,26.4958,'OK',94631100),(7,105,2,-0.676445,'OK',-0.676445),(7,105,9,-0.1,'OK',744.74),(7,106,1,26.2,'OK',77089500),(7,106,2,-0.290903,'OK',-0.290903),(7,106,9,0,'OK',746.81),(7,107,1,26.4356,'OK',90763200),(7,107,2,-0.569746,'OK',-0.569746),(7,107,9,-0.1,'OK',741.966),(7,108,1,26.4555,'OK',92024500),(7,108,2,-0.555794,'OK',-0.555794),(7,108,9,-0.1,'OK',741.133),(7,109,1,26.5322,'OK',97046300),(7,109,2,-0.500428,'OK',-0.500428),(7,109,9,-0.1,'OK',740.285),(7,110,1,26.3782,'OK',87220300),(7,110,2,-0.383808,'OK',-0.383808),(7,110,9,-0.1,'OK',742.639),(7,111,1,26.2143,'OK',77857900),(7,111,2,-0.680898,'OK',-0.680898),(7,111,9,0,'OK',744.023),(7,112,1,26.1107,'OK',72462200),(7,112,2,-0.840982,'OK',-0.840982),(7,112,9,0,'OK',743.494),(7,113,1,26.3144,'OK',83450100),(7,113,2,-0.500374,'OK',-0.500374),(7,113,9,-0.1,'OK',738.935),(7,114,1,26.2856,'OK',81799200),(7,114,2,-0.536414,'OK',-0.536414),(7,114,9,0,'OK',742.72),(7,115,1,26.1922,'OK',76673600),(7,115,2,-0.37948,'OK',-0.37948),(7,115,9,0.1,'OK',747.424),(7,116,1,24.6863,'OK',26996700),(7,116,2,-0.354894,'OK',-0.354894),(7,116,9,0.1,'OK',748.416),(7,117,1,25.9864,'OK',66480000),(7,117,2,-0.59829,'OK',-0.59829),(7,117,9,0.1,'OK',747.025),(7,118,1,27.0594,'OK',139855000),(7,118,2,-0.281153,'OK',-0.281153),(7,118,9,0,'OK',744.784),(7,119,1,25.6957,'OK',54348200),(7,119,2,-0.428379,'OK',-0.428379),(7,119,9,0,'OK',746.68),(7,120,1,25.7216,'OK',55332600),(7,120,2,-0.543585,'OK',-0.543585),(7,120,9,0,'OK',745.48),(7,121,1,25.4582,'OK',46097900),(7,121,2,-0.403631,'OK',-0.403631),(7,121,9,0,'OK',741.954),(7,122,1,25.4514,'OK',45881900),(7,122,2,-0.490282,'OK',-0.490282),(7,122,9,0.1,'OK',748.659),(7,123,1,NULL,'OK',0),(7,123,2,0,'OK',0),(7,123,9,NULL,'OK',0),(7,124,1,25.6088,'OK',51171600),(7,124,2,-0.341223,'OK',-0.341223),(7,124,9,0,'OK',743.434),(7,125,1,24.8984,'OK',31272400),(7,125,2,-0.473138,'OK',-0.473138),(7,125,9,-0.1,'OK',742.594),(7,126,1,25.5322,'OK',48523800),(7,126,2,-0.28785,'OK',-0.28785),(7,126,9,0,'OK',742.961),(7,127,1,25.5053,'OK',47627900),(7,127,2,-0.262185,'OK',-0.262185),(7,127,9,0,'OK',745.992),(7,128,1,25.4694,'OK',46456600),(7,128,2,-0.334454,'OK',-0.334454),(7,128,9,-0.1,'OK',739.944),(7,129,1,25.3582,'OK',43012200),(7,129,2,-0.408337,'OK',-0.408337),(7,129,9,0,'OK',743.012),(7,130,1,24.9595,'OK',32625400),(7,130,2,-0.25705,'OK',-0.25705),(7,130,9,-0.1,'OK',737.351),(7,131,1,24.7678,'OK',28566900),(7,131,2,-0.107546,'OK',-0.107546),(7,131,9,0,'OK',744.62),(7,132,1,24.9226,'OK',31800900),(7,132,2,-0.365939,'OK',-0.365939),(7,132,9,0,'OK',742.519),(7,133,1,24.7628,'OK',28467200),(7,133,2,-0.26693,'OK',-0.26693),(7,133,9,0,'OK',743.754),(7,134,1,24.9535,'OK',32491100),(7,134,2,-0.21886,'OK',-0.21886),(7,134,9,-0.1,'OK',735.353),(7,135,1,NULL,'OK',0),(7,135,2,0,'OK',0),(7,135,9,NULL,'OK',0),(7,136,1,NULL,'OK',0),(7,136,2,0,'OK',0),(7,136,9,NULL,'OK',0),(7,137,1,24.5301,'OK',24227300),(7,137,2,-0.00897993,'OK',-0.00897993),(7,137,9,0.1,'OK',750.388),(7,138,1,24.7386,'OK',27994200),(7,138,2,-0.1945,'OK',-0.1945),(7,138,9,0.1,'OK',748.949),(7,139,1,24.98,'OK',33093000),(7,139,2,-0.0570011,'OK',-0.0570011),(7,139,9,0.1,'OK',746.233),(7,140,1,25.1318,'OK',36765200),(7,140,2,-0.249575,'OK',-0.249575),(7,140,9,0,'OK',744.321),(7,141,1,24.7411,'OK',28042800),(7,141,2,-0.0766636,'OK',-0.0766636),(7,141,9,0,'OK',743.587),(7,142,1,24.4995,'OK',23718200),(7,142,2,-0.0928064,'OK',-0.0928064),(7,142,9,-0.1,'OK',739.032),(7,143,1,24.6038,'OK',25496000),(7,143,2,-0.329663,'OK',-0.329663),(7,143,9,-0.1,'OK',738.878),(7,144,1,24.5934,'OK',25314200),(7,144,2,-0.0909829,'OK',-0.0909829),(7,144,9,0,'OK',743.18),(7,145,1,24.642,'OK',26180800),(7,145,2,-0.118356,'OK',-0.118356),(7,145,9,0.1,'OK',746.929),(7,146,1,24.4512,'OK',22936800),(7,146,2,-0.120118,'OK',-0.120118),(7,146,9,0,'OK',744.839),(7,147,1,21.8181,'OK',3697530),(7,147,2,-0.28454,'OK',-0.28454),(7,147,9,0.1,'OK',748.455),(7,148,9,-0.1,'OK',741.259),(7,149,1,21.9921,'OK',4171460),(7,149,2,-0.146093,'OK',-0.146093),(7,149,9,0.3,'OK',762.244),(7,150,1,21.9333,'OK',4004860),(7,150,2,-0.172777,'OK',-0.172777),(7,150,9,0.3,'OK',762.929),(7,151,1,22.0114,'OK',4227670),(7,151,2,-0.0967793,'OK',-0.0967793),(7,151,9,0.3,'OK',766.71),(7,152,1,21.8458,'OK',3769200),(7,152,2,-0.173718,'OK',-0.173718),(7,152,9,0.2,'OK',760.887),(7,153,1,21.6017,'OK',3182430),(7,153,2,0.0456705,'OK',0.0456705),(7,153,9,0.3,'OK',769.556),(7,154,1,21.9819,'OK',4142110),(7,154,2,-0.141625,'OK',-0.141625),(7,154,9,0.1,'OK',761.682),(7,155,1,21.4831,'OK',2931380),(7,155,2,0.234791,'OK',0.234791),(7,155,9,0.2,'OK',767.168),(7,156,1,22.0534,'OK',4352340),(7,156,2,0.0968326,'OK',0.0968326),(7,156,9,0.1,'OK',766.649),(7,157,1,22.6305,'OK',6493260),(7,157,2,-0.0386119,'OK',-0.0386119),(7,157,9,0.1,'OK',765.368),(7,158,1,21.4942,'OK',2954000),(7,158,2,-0.10703,'OK',-0.10703),(7,158,9,0,'OK',763.135),(7,159,1,21.5697,'OK',3112550),(7,159,2,0.113784,'OK',0.113784),(7,159,9,0.1,'OK',769.819),(7,160,1,21.4684,'OK',2901530),(7,160,2,-0.079954,'OK',-0.079954),(7,160,9,-0.1,'OK',762.33),(7,161,1,21.3645,'OK',2699950),(7,161,2,-0.0769363,'OK',-0.0769363),(7,161,9,-0.1,'OK',761.655),(7,162,1,21.3137,'OK',2606440),(7,162,2,-0.172717,'OK',-0.172717),(7,162,9,0,'OK',765.994),(7,163,1,21.9547,'OK',4064650),(7,163,2,0.233394,'OK',0.233394),(7,163,9,0.1,'OK',768.563),(7,164,1,21.1531,'OK',2331880),(7,164,2,-0.23816,'OK',-0.23816),(7,164,9,0,'OK',766.013),(7,165,1,21.1633,'OK',2348500),(7,165,2,-0.141938,'OK',-0.141938),(7,165,9,0,'OK',767.846),(7,166,1,20.977,'OK',2063930),(7,166,2,0.181491,'OK',0.181491),(7,166,9,0.1,'OK',771.025),(7,167,1,21.1712,'OK',2361330),(7,167,2,-0.111511,'OK',-0.111511),(7,167,9,0,'OK',765.129),(7,168,1,20.8386,'OK',1875220),(7,168,2,-0.10715,'OK',-0.10715),(7,168,9,-0.1,'OK',763.127),(7,169,1,20.8545,'OK',1896010),(7,169,2,-0.112148,'OK',-0.112148),(7,169,9,-0.1,'OK',761.761),(7,170,1,21.818,'OK',3697190),(7,170,2,0.0424677,'OK',0.0424677),(7,170,9,-0.1,'OK',761.476),(7,171,1,21.0788,'OK',2214940),(7,171,2,-0.0927451,'OK',-0.0927451),(7,171,9,0,'OK',762.535),(7,172,1,21.5332,'OK',3034850),(7,172,2,0.0866073,'OK',0.0866073),(7,172,9,-0.1,'OK',761.42),(7,173,1,NULL,'OK',0),(7,173,2,0,'OK',0),(7,173,9,NULL,'OK',0),(7,174,1,20.4257,'OK',1408510),(7,174,2,-0.210218,'OK',-0.210218),(7,174,9,-0.1,'OK',755.967),(7,175,1,20.6294,'OK',1622090),(7,175,2,0.0803043,'OK',0.0803043),(7,175,9,0,'OK',763.165),(7,176,1,20.4095,'OK',1392720),(7,176,2,-0.0504794,'OK',-0.0504794),(7,176,9,-0.1,'OK',758.843),(7,177,1,20.2803,'OK',1273440),(7,177,2,0.0731782,'OK',0.0731782),(7,177,9,-0.1,'OK',758.474),(7,178,1,20.5028,'OK',1485840),(7,178,2,0.0560079,'OK',0.0560079),(7,178,9,-0.1,'OK',755.095),(7,179,1,19.8927,'OK',973436),(7,179,2,-0.118014,'OK',-0.118014),(7,179,9,0,'OK',757.697),(8,1,1,23.1867,'OK',9547310),(8,1,2,-0.216284,'OK',-0.216284),(8,2,1,23.2063,'OK',9678070),(8,2,2,-0.0755937,'OK',-0.0755937),(8,3,1,23.0918,'OK',8939950),(8,3,2,-0.167936,'OK',-0.167936),(8,4,1,23.0432,'OK',8643460),(8,4,2,-0.242366,'OK',-0.242366),(8,5,1,22.8941,'OK',7794800),(8,5,2,-0.0284475,'OK',-0.0284475),(8,6,1,22.9849,'OK',8301490),(8,6,2,-0.149151,'OK',-0.149151),(8,7,1,23.1208,'OK',9121400),(8,7,2,-0.177378,'OK',-0.177378),(8,8,1,22.7817,'OK',7210560),(8,8,2,-0.113489,'OK',-0.113489),(8,10,1,23.6638,'OK',13289700),(8,10,2,-0.00875457,'OK',-0.00875457),(8,11,1,22.5633,'OK',6197520),(8,11,2,0.163246,'OK',0.163246),(8,12,1,23.5084,'OK',11932600),(8,12,2,0.0466377,'OK',0.0466377),(8,13,1,23.5915,'OK',12640000),(8,13,2,0.0491653,'OK',0.0491653),(8,14,1,23.4922,'OK',11799700),(8,14,2,0.0559486,'OK',0.0559486),(8,15,1,23.2941,'OK',10285400),(8,15,2,0.0236998,'OK',0.0236998),(8,16,1,22.915,'OK',7908920),(8,16,2,0.0109206,'OK',0.0109206),(8,17,1,22.9796,'OK',8271050),(8,17,2,-0.0370344,'OK',-0.0370344),(8,18,1,22.8621,'OK',7623930),(8,18,2,-0.0168929,'OK',-0.0168929),(8,19,1,21.9876,'WARNING',4158520),(8,19,2,0.0660079,'OK',0.0660079),(8,20,1,18.9901,'DANGER',520710),(8,20,2,-0.538705,'OK',-0.538705),(8,21,1,22.5722,'OK',6236100),(8,21,2,0.567523,'OK',0.567523),(8,22,1,23.9149,'OK',15816400),(8,22,2,-0.0914841,'OK',-0.0914841),(8,23,1,23.3203,'OK',10474200),(8,23,2,0.0627865,'OK',0.0627865),(8,24,1,23.7664,'OK',14269600),(8,24,2,0.155077,'OK',0.155077),(8,25,1,24.1867,'OK',19095700),(8,25,2,0.0675581,'OK',0.0675581),(8,26,1,24.4102,'OK',22295000),(8,26,2,0.251683,'OK',0.251683),(8,27,1,24.1073,'OK',18072600),(8,27,2,0.0518254,'OK',0.0518254),(8,28,1,24.1132,'OK',18147200),(8,28,2,0.239224,'OK',0.239224),(8,29,1,24.1722,'OK',18904200),(8,29,2,0.0594355,'OK',0.0594355),(8,30,1,24.5039,'OK',23790400),(8,30,2,0.0967932,'OK',0.0967932),(8,31,1,24.2517,'OK',19975100),(8,31,2,0.0906368,'OK',0.0906368),(8,32,1,24.1249,'OK',18294600),(8,32,2,0.066366,'OK',0.066366),(8,33,1,24.1068,'OK',18066100),(8,33,2,0.06881,'OK',0.06881),(8,34,1,24.1677,'OK',18845500),(8,34,2,0.0348554,'OK',0.0348554),(8,35,1,24.126,'OK',18309000),(8,35,2,0.0342023,'OK',0.0342023),(8,36,1,24.0746,'OK',17667300),(8,36,2,0.0549603,'OK',0.0549603),(8,37,1,23.9861,'OK',16616800),(8,37,2,0.00274138,'OK',0.00274138),(8,38,1,23.9811,'OK',16559200),(8,38,2,0.042348,'OK',0.042348),(8,39,1,23.9134,'OK',15799800),(8,39,2,0.057957,'OK',0.057957),(8,40,1,23.9255,'OK',15933300),(8,40,2,-0.0192164,'OK',-0.0192164),(8,41,1,22.9138,'DANGER',7901860),(8,41,2,-0.0312499,'OK',-0.0312499),(8,42,1,23.7185,'OK',13803400),(8,42,2,-0.0412562,'OK',-0.0412562),(8,43,1,23.5991,'OK',12706800),(8,43,2,-0.0679109,'OK',-0.0679109),(8,44,1,23.6875,'OK',13509400),(8,44,2,0.0123981,'OK',0.0123981),(8,45,1,23.5958,'OK',12678000),(8,45,2,-0.098041,'OK',-0.098041),(8,46,1,23.5452,'OK',12240900),(8,46,2,-0.0232576,'OK',-0.0232576),(8,47,1,23.4781,'OK',11684800),(8,47,2,-0.0351671,'OK',-0.0351671),(8,48,1,23.5063,'OK',11914800),(8,48,2,-0.0710848,'OK',-0.0710848),(8,49,1,23.4984,'OK',11850400),(8,49,2,-0.0791249,'OK',-0.0791249),(8,50,1,23.4699,'OK',11618400),(8,50,2,-0.0636253,'OK',-0.0636253),(8,51,1,23.9383,'OK',16075100),(8,51,2,-0.0154545,'OK',-0.0154545),(8,52,1,23.3095,'WARNING',10395500),(8,52,2,-0.0901037,'OK',-0.0901037),(8,53,1,23.309,'OK',10392100),(8,53,2,-0.115691,'OK',-0.115691),(8,55,1,NULL,'OK',0),(8,55,2,0,'OK',0),(8,55,9,NULL,'OK',0),(8,56,1,NULL,'OK',0),(8,56,2,0,'OK',0),(8,56,9,NULL,'OK',0),(8,57,1,NULL,'OK',0),(8,57,2,0,'OK',0),(8,57,9,NULL,'OK',0),(8,58,1,NULL,'OK',0),(8,58,2,0,'OK',0),(8,58,9,NULL,'OK',0),(8,59,1,NULL,'OK',0),(8,59,2,0,'OK',0),(8,59,9,NULL,'OK',0),(8,60,1,NULL,'OK',0),(8,60,2,0,'OK',0),(8,60,9,NULL,'OK',0),(8,61,1,NULL,'OK',0),(8,61,2,0,'OK',0),(8,61,9,NULL,'OK',0),(8,62,1,NULL,'OK',0),(8,62,2,0,'OK',0),(8,62,9,NULL,'OK',0),(8,63,1,NULL,'OK',0),(8,63,2,0,'OK',0),(8,63,9,NULL,'OK',0),(8,64,1,NULL,'OK',0),(8,64,2,0,'OK',0),(8,64,9,NULL,'OK',0),(8,65,1,NULL,'OK',0),(8,65,2,0,'OK',0),(8,65,9,NULL,'OK',0),(8,66,1,NULL,'OK',0),(8,66,2,0,'OK',0),(8,66,9,NULL,'OK',0),(8,67,1,NULL,'OK',0),(8,67,2,0,'OK',0),(8,67,9,NULL,'OK',0),(8,68,1,NULL,'OK',0),(8,68,2,0,'OK',0),(8,68,9,NULL,'OK',0),(8,69,1,NULL,'OK',0),(8,69,2,0,'OK',0),(8,69,9,NULL,'OK',0),(8,70,1,NULL,'OK',0),(8,70,2,0,'OK',0),(8,70,9,NULL,'OK',0),(8,71,1,NULL,'OK',0),(8,71,2,0,'OK',0),(8,71,9,NULL,'OK',0),(8,72,1,NULL,'OK',0),(8,72,2,0,'OK',0),(8,72,9,NULL,'OK',0),(8,73,1,NULL,'OK',0),(8,73,2,0,'OK',0),(8,73,9,NULL,'OK',0),(8,74,1,NULL,'OK',0),(8,74,2,0,'OK',0),(8,74,9,NULL,'OK',0),(8,75,1,NULL,'OK',0),(8,75,2,0,'OK',0),(8,75,9,NULL,'OK',0),(8,76,1,NULL,'OK',0),(8,76,2,0,'OK',0),(8,76,9,NULL,'OK',0),(8,77,1,NULL,'OK',0),(8,77,2,0,'OK',0),(8,77,9,NULL,'OK',0),(8,78,1,NULL,'OK',0),(8,78,2,0,'OK',0),(8,78,9,NULL,'OK',0),(8,79,1,NULL,'OK',0),(8,79,2,0,'OK',0),(8,79,9,NULL,'OK',0),(8,80,1,NULL,'OK',0),(8,80,2,0,'OK',0),(8,80,9,NULL,'OK',0),(8,81,1,NULL,'OK',0),(8,81,2,0,'OK',0),(8,81,9,NULL,'OK',0),(8,82,1,NULL,'OK',0),(8,82,2,0,'OK',0),(8,82,9,NULL,'OK',0),(8,83,1,NULL,'OK',0),(8,83,2,0,'OK',0),(8,83,9,NULL,'OK',0),(8,84,1,NULL,'OK',0),(8,84,2,0,'OK',0),(8,84,9,NULL,'OK',0),(8,85,1,NULL,'OK',0),(8,85,2,0,'OK',0),(8,85,9,NULL,'OK',0),(8,86,1,NULL,'OK',0),(8,86,2,0,'OK',0),(8,86,9,NULL,'OK',0),(8,87,1,NULL,'OK',0),(8,87,2,0,'OK',0),(8,87,9,NULL,'OK',0),(8,88,1,NULL,'OK',0),(8,88,2,0,'OK',0),(8,88,9,NULL,'OK',0),(8,89,1,NULL,'OK',0),(8,89,2,0,'OK',0),(8,89,9,NULL,'OK',0),(8,90,1,NULL,'OK',0),(8,90,2,0,'OK',0),(8,90,9,NULL,'OK',0),(8,91,1,NULL,'OK',0),(8,91,2,0,'OK',0),(8,91,9,NULL,'OK',0),(8,92,1,NULL,'OK',0),(8,92,2,0,'OK',0),(8,92,9,NULL,'OK',0),(8,93,1,NULL,'OK',0),(8,93,2,0,'OK',0),(8,93,9,NULL,'OK',0),(8,94,1,NULL,'OK',0),(8,94,2,0,'OK',0),(8,94,9,NULL,'OK',0),(8,95,1,NULL,'OK',0),(8,95,2,0,'OK',0),(8,95,9,NULL,'OK',0),(8,96,1,NULL,'OK',0),(8,96,2,0,'OK',0),(8,96,9,NULL,'OK',0),(8,97,1,NULL,'OK',0),(8,97,2,0,'OK',0),(8,97,9,NULL,'OK',0),(8,98,1,NULL,'OK',0),(8,98,2,0,'OK',0),(8,98,9,NULL,'OK',0),(8,99,1,NULL,'OK',0),(8,99,2,0,'OK',0),(8,99,9,NULL,'OK',0),(8,100,1,NULL,'OK',0),(8,100,2,0,'OK',0),(8,100,9,NULL,'OK',0),(8,101,1,NULL,'OK',0),(8,101,2,0,'OK',0),(8,101,9,NULL,'OK',0),(8,102,1,NULL,'OK',0),(8,102,2,0,'OK',0),(8,102,9,NULL,'OK',0),(8,103,1,NULL,'OK',0),(8,103,2,0,'OK',0),(8,103,9,NULL,'OK',0),(8,104,1,NULL,'OK',0),(8,104,2,0,'OK',0),(8,104,9,NULL,'OK',0),(8,105,1,NULL,'OK',0),(8,105,2,0,'OK',0),(8,105,9,NULL,'OK',0),(8,106,1,NULL,'OK',0),(8,106,2,0,'OK',0),(8,106,9,NULL,'OK',0),(8,107,1,NULL,'OK',0),(8,107,2,0,'OK',0),(8,107,9,NULL,'OK',0),(8,108,1,NULL,'OK',0),(8,108,2,0,'OK',0),(8,108,9,NULL,'OK',0),(8,109,1,NULL,'OK',0),(8,109,2,0,'OK',0),(8,109,9,NULL,'OK',0),(8,110,1,NULL,'OK',0),(8,110,2,0,'OK',0),(8,110,9,NULL,'OK',0),(8,111,1,NULL,'OK',0),(8,111,2,0,'OK',0),(8,111,9,NULL,'OK',0),(8,112,1,NULL,'OK',0),(8,112,2,0,'OK',0),(8,112,9,NULL,'OK',0),(8,113,1,NULL,'OK',0),(8,113,2,0,'OK',0),(8,113,9,NULL,'OK',0),(8,114,1,NULL,'OK',0),(8,114,2,0,'OK',0),(8,114,9,NULL,'OK',0),(8,115,1,NULL,'OK',0),(8,115,2,0,'OK',0),(8,115,9,NULL,'OK',0),(8,116,1,NULL,'OK',0),(8,116,2,0,'OK',0),(8,116,9,NULL,'OK',0),(8,117,1,NULL,'OK',0),(8,117,2,0,'OK',0),(8,117,9,NULL,'OK',0),(8,118,1,NULL,'OK',0),(8,118,2,0,'OK',0),(8,118,9,NULL,'OK',0),(8,119,1,NULL,'OK',0),(8,119,2,0,'OK',0),(8,119,9,NULL,'OK',0),(8,120,1,NULL,'OK',0),(8,120,2,0,'OK',0),(8,120,9,NULL,'OK',0),(8,121,1,NULL,'OK',0),(8,121,2,0,'OK',0),(8,121,9,NULL,'OK',0),(8,122,1,NULL,'OK',0),(8,122,2,0,'OK',0),(8,122,9,NULL,'OK',0),(8,123,1,NULL,'OK',0),(8,123,2,0,'OK',0),(8,123,9,NULL,'OK',0),(8,124,1,NULL,'OK',0),(8,124,2,0,'OK',0),(8,124,9,NULL,'OK',0),(8,125,1,NULL,'OK',0),(8,125,2,0,'OK',0),(8,125,9,NULL,'OK',0),(8,126,1,NULL,'OK',0),(8,126,2,0,'OK',0),(8,126,9,NULL,'OK',0),(8,127,1,NULL,'OK',0),(8,127,2,0,'OK',0),(8,127,9,NULL,'OK',0),(8,128,1,NULL,'OK',0),(8,128,2,0,'OK',0),(8,128,9,NULL,'OK',0),(8,129,1,NULL,'OK',0),(8,129,2,0,'OK',0),(8,129,9,NULL,'OK',0),(8,130,1,NULL,'OK',0),(8,130,2,0,'OK',0),(8,130,9,NULL,'OK',0),(8,131,1,NULL,'OK',0),(8,131,2,0,'OK',0),(8,131,9,NULL,'OK',0),(8,132,1,NULL,'OK',0),(8,132,2,0,'OK',0),(8,132,9,NULL,'OK',0),(8,133,1,NULL,'OK',0),(8,133,2,0,'OK',0),(8,133,9,NULL,'OK',0),(8,134,1,NULL,'OK',0),(8,134,2,0,'OK',0),(8,134,9,NULL,'OK',0),(8,135,1,NULL,'OK',0),(8,135,2,0,'OK',0),(8,135,9,NULL,'OK',0),(8,136,1,NULL,'OK',0),(8,136,2,0,'OK',0),(8,136,9,NULL,'OK',0),(8,137,1,NULL,'OK',0),(8,137,2,0,'OK',0),(8,137,9,NULL,'OK',0),(8,138,1,NULL,'OK',0),(8,138,2,0,'OK',0),(8,138,9,NULL,'OK',0),(8,139,1,NULL,'OK',0),(8,139,2,0,'OK',0),(8,139,9,NULL,'OK',0),(8,140,1,NULL,'OK',0),(8,140,2,0,'OK',0),(8,140,9,NULL,'OK',0),(8,141,1,NULL,'OK',0),(8,141,2,0,'OK',0),(8,141,9,NULL,'OK',0),(8,142,1,NULL,'OK',0),(8,142,2,0,'OK',0),(8,142,9,NULL,'OK',0),(8,143,1,NULL,'OK',0),(8,143,2,0,'OK',0),(8,143,9,NULL,'OK',0),(8,144,1,NULL,'OK',0),(8,144,2,0,'OK',0),(8,144,9,NULL,'OK',0),(8,145,1,NULL,'OK',0),(8,145,2,0,'OK',0),(8,145,9,NULL,'OK',0),(8,146,1,NULL,'OK',0),(8,146,2,0,'OK',0),(8,146,9,NULL,'OK',0),(8,147,1,NULL,'OK',0),(8,147,2,0,'OK',0),(8,147,9,NULL,'OK',0),(8,148,9,NULL,'OK',0),(8,149,1,NULL,'OK',0),(8,149,2,0,'OK',0),(8,149,9,NULL,'OK',0),(8,150,1,NULL,'OK',0),(8,150,2,0,'OK',0),(8,150,9,NULL,'OK',0),(8,151,1,NULL,'OK',0),(8,151,2,0,'OK',0),(8,151,9,NULL,'OK',0),(8,152,1,NULL,'OK',0),(8,152,2,0,'OK',0),(8,152,9,NULL,'OK',0),(8,153,1,NULL,'OK',0),(8,153,2,0,'OK',0),(8,153,9,NULL,'OK',0),(8,154,1,NULL,'OK',0),(8,154,2,0,'OK',0),(8,154,9,NULL,'OK',0),(8,155,1,NULL,'OK',0),(8,155,2,0,'OK',0),(8,155,9,NULL,'OK',0),(8,156,1,NULL,'OK',0),(8,156,2,0,'OK',0),(8,156,9,NULL,'OK',0),(8,157,1,NULL,'OK',0),(8,157,2,0,'OK',0),(8,157,9,NULL,'OK',0),(8,158,1,NULL,'OK',0),(8,158,2,0,'OK',0),(8,158,9,NULL,'OK',0),(8,159,1,NULL,'OK',0),(8,159,2,0,'OK',0),(8,159,9,NULL,'OK',0),(8,160,1,NULL,'OK',0),(8,160,2,0,'OK',0),(8,160,9,NULL,'OK',0),(8,161,1,NULL,'OK',0),(8,161,2,0,'OK',0),(8,161,9,NULL,'OK',0),(8,162,1,NULL,'OK',0),(8,162,2,0,'OK',0),(8,162,9,NULL,'OK',0),(8,163,1,NULL,'OK',0),(8,163,2,0,'OK',0),(8,163,9,NULL,'OK',0),(8,164,1,NULL,'OK',0),(8,164,2,0,'OK',0),(8,164,9,NULL,'OK',0),(8,165,1,NULL,'OK',0),(8,165,2,0,'OK',0),(8,165,9,NULL,'OK',0),(8,166,1,NULL,'OK',0),(8,166,2,0,'OK',0),(8,166,9,NULL,'OK',0),(8,167,1,NULL,'OK',0),(8,167,2,0,'OK',0),(8,167,9,NULL,'OK',0),(8,168,1,NULL,'OK',0),(8,168,2,0,'OK',0),(8,168,9,NULL,'OK',0),(8,169,1,NULL,'OK',0),(8,169,2,0,'OK',0),(8,169,9,NULL,'OK',0),(8,170,1,NULL,'OK',0),(8,170,2,0,'OK',0),(8,170,9,NULL,'OK',0),(8,171,1,NULL,'OK',0),(8,171,2,0,'OK',0),(8,171,9,NULL,'OK',0),(8,172,1,NULL,'OK',0),(8,172,2,0,'OK',0),(8,172,9,NULL,'OK',0),(8,173,1,NULL,'OK',0),(8,173,2,0,'OK',0),(8,173,9,NULL,'OK',0),(8,174,1,NULL,'OK',0),(8,174,2,0,'OK',0),(8,174,9,NULL,'OK',0),(8,175,1,NULL,'OK',0),(8,175,2,0,'OK',0),(8,175,9,NULL,'OK',0),(8,176,1,NULL,'OK',0),(8,176,2,0,'OK',0),(8,176,9,NULL,'OK',0),(8,177,1,NULL,'OK',0),(8,177,2,0,'OK',0),(8,177,9,NULL,'OK',0),(8,178,1,NULL,'OK',0),(8,178,2,0,'OK',0),(8,178,9,NULL,'OK',0),(8,179,1,NULL,'OK',0),(8,179,2,0,'OK',0),(8,179,9,NULL,'OK',0),(9,1,1,27.8489,'OK',241749000),(9,1,2,0.411114,'OK',0.411114),(9,2,1,27.8106,'OK',235404000),(9,2,2,0.371325,'OK',0.371325),(9,3,1,27.7318,'OK',222904000),(9,3,2,0.294829,'OK',0.294829),(9,4,1,27.7115,'OK',219778000),(9,4,2,0.432078,'OK',0.432078),(9,5,1,27.5113,'OK',191310000),(9,5,2,0.49094,'OK',0.49094),(9,6,1,27.5257,'OK',193229000),(9,6,2,0.414061,'OK',0.414061),(9,7,1,27.7726,'OK',229292000),(9,7,2,0.293268,'OK',0.293268),(9,8,1,27.5344,'OK',194388000),(9,8,2,0.473454,'OK',0.473454),(9,10,1,28.7173,'OK',441325000),(9,10,2,0.244074,'OK',0.244074),(9,11,1,28.6531,'OK',422124000),(9,11,2,0.221488,'OK',0.221488),(9,12,1,28.7819,'OK',461538000),(9,12,2,0.20639,'OK',0.20639),(9,13,1,28.8078,'OK',469899000),(9,13,2,0.226952,'OK',0.226952),(9,14,1,28.7364,'OK',447231000),(9,14,2,0.241068,'OK',0.241068),(9,15,1,28.5256,'OK',386421000),(9,15,2,0.253893,'OK',0.253893),(9,16,1,28.5121,'OK',382821000),(9,16,2,0.234966,'OK',0.234966),(9,17,1,28.3723,'OK',347455000),(9,17,2,0.247856,'OK',0.247856),(9,18,1,28.1996,'OK',308274000),(9,18,2,0.234891,'OK',0.234891),(9,19,1,27.552,'DANGER',196773000),(9,19,2,0.242143,'OK',0.242143),(9,20,1,25.5536,'DANGER',49249400),(9,20,2,1.27993,'OK',1.27993),(9,21,1,26.239,'OK',79199100),(9,21,2,1.70143,'OK',1.70143),(9,22,1,28.6795,'OK',429916000),(9,22,2,0.369402,'OK',0.369402),(9,23,1,28.1035,'OK',288409000),(9,23,2,0.314179,'OK',0.314179),(9,24,1,28.6111,'OK',410013000),(9,24,2,0.269864,'OK',0.269864),(9,25,1,28.8708,'OK',490869000),(9,25,2,0.343163,'OK',0.343163),(9,26,1,29.1461,'OK',594081000),(9,26,2,0.217361,'OK',0.217361),(9,27,1,29.0693,'OK',563287000),(9,27,2,0.361244,'OK',0.361244),(9,28,1,29.0208,'OK',544673000),(9,28,2,0.250215,'OK',0.250215),(9,29,1,29.1033,'OK',576708000),(9,29,2,0.352943,'OK',0.352943),(9,30,1,29.551,'OK',786571000),(9,30,2,0.294459,'OK',0.294459),(9,31,1,29.1125,'OK',580402000),(9,31,2,0.433683,'OK',0.433683),(9,32,1,29.0585,'OK',559104000),(9,32,2,0.21819,'OK',0.21819),(9,33,1,28.9319,'OK',512129000),(9,33,2,0.3032,'OK',0.3032),(9,34,1,29.6641,'OK',850690000),(9,34,2,0.311332,'OK',0.311332),(9,35,1,28.8961,'OK',499581000),(9,35,2,0.332699,'OK',0.332699),(9,36,1,28.8823,'OK',494819000),(9,36,2,0.387675,'OK',0.387675),(9,37,1,28.8579,'OK',486511000),(9,37,2,0.338341,'OK',0.338341),(9,38,1,28.8205,'OK',474070000),(9,38,2,0.309812,'OK',0.309812),(9,39,1,28.6702,'OK',427170000),(9,39,2,0.353967,'OK',0.353967),(9,40,1,28.7747,'OK',459236000),(9,40,2,0.287234,'OK',0.287234),(9,41,1,28.2951,'WARNING',329361000),(9,41,2,0.318356,'OK',0.318356),(9,42,1,28.6016,'OK',407325000),(9,42,2,0.35376,'OK',0.35376),(9,43,1,28.5403,'OK',390389000),(9,43,2,0.254655,'OK',0.254655),(9,44,1,28.5386,'OK',389916000),(9,44,2,0.344514,'OK',0.344514),(9,45,1,28.4198,'OK',359092000),(9,45,2,0.368297,'OK',0.368297),(9,46,1,28.377,'OK',348597000),(9,46,2,0.407845,'OK',0.407845),(9,47,1,28.5517,'OK',393468000),(9,47,2,0.334044,'OK',0.334044),(9,48,1,28.388,'OK',351275000),(9,48,2,0.421184,'OK',0.421184),(9,49,1,28.3713,'OK',347231000),(9,49,2,0.390463,'OK',0.390463),(9,50,1,28.3985,'OK',353839000),(9,50,2,0.37444,'OK',0.37444),(9,51,1,29.0871,'OK',570294000),(9,51,2,0.356821,'OK',0.356821),(9,52,1,28.1729,'OK',302606000),(9,52,2,0.392355,'OK',0.392355),(9,53,1,28.1739,'OK',302823000),(9,53,2,0.395317,'OK',0.395317),(9,55,1,27.2434,'OK',158882000),(9,55,2,-0.00837025,'OK',-0.00837025),(9,55,9,NULL,'OK',583.546),(9,56,1,27.3611,'OK',172387000),(9,56,2,0.051788,'OK',0.051788),(9,56,9,NULL,'OK',589.228),(9,57,1,27.2927,'OK',164407000),(9,57,2,0.126671,'OK',0.126671),(9,57,9,NULL,'OK',588.339),(9,58,1,27.3002,'OK',165269000),(9,58,2,0.183597,'OK',0.183597),(9,58,9,NULL,'OK',588.911),(9,59,1,26.9007,'OK',125292000),(9,59,2,-0.0590253,'OK',-0.0590253),(9,59,9,NULL,'OK',587.413),(9,60,1,27.2833,'OK',163339000),(9,60,2,0.203396,'OK',0.203396),(9,60,9,NULL,'OK',582.275),(9,61,1,27.2266,'OK',157041000),(9,61,2,0.0664323,'OK',0.0664323),(9,61,9,NULL,'OK',581.971),(9,62,1,27.2377,'OK',158253000),(9,62,2,0.310738,'OK',0.310738),(9,62,9,NULL,'OK',582.853),(9,63,1,27.3648,'OK',172832000),(9,63,2,0.186989,'OK',0.186989),(9,63,9,NULL,'OK',585.239),(9,64,1,27.2113,'OK',155388000),(9,64,2,0.0531462,'OK',0.0531462),(9,64,9,NULL,'OK',582.118),(9,65,1,26.8066,'OK',117376000),(9,65,2,0.188811,'OK',0.188811),(9,65,9,-0.1,'OK',580.828),(9,66,1,26.9973,'OK',133966000),(9,66,2,-0.187215,'OK',-0.187215),(9,66,9,0,'OK',586.529),(9,67,1,27.7747,'OK',229621000),(9,67,2,0.553343,'OK',0.553343),(9,67,9,-0.1,'OK',581.525),(9,68,1,27.0876,'OK',142624000),(9,68,2,0.2766,'OK',0.2766),(9,68,9,-0.1,'OK',579.492),(9,69,1,26.3307,'OK',84398700),(9,69,2,0.152801,'OK',0.152801),(9,69,9,0,'OK',580.602),(9,70,1,26.5363,'OK',97322000),(9,70,2,0.273372,'OK',0.273372),(9,70,9,0,'OK',582.569),(9,71,1,26.7288,'OK',111215000),(9,71,2,0.058056,'OK',0.058056),(9,71,9,0,'OK',581.204),(9,72,1,24.8256,'OK',29734200),(9,72,2,0.0773193,'OK',0.0773193),(9,72,9,0,'OK',585.057),(9,73,1,NULL,'OK',0),(9,73,2,0,'OK',0),(9,73,9,NULL,'OK',0),(9,74,1,NULL,'OK',0),(9,74,2,0,'OK',0),(9,74,9,NULL,'OK',0),(9,75,1,22.746,'OK',7034620),(9,75,2,-0.356522,'OK',-0.356522),(9,75,9,0.2,'OK',595.793),(9,76,1,26.8567,'OK',121525000),(9,76,2,0.0679642,'OK',0.0679642),(9,76,9,-0.1,'OK',578.322),(9,77,1,27.0833,'OK',142195000),(9,77,2,0.0957373,'OK',0.0957373),(9,77,9,-0.1,'OK',574.745),(9,78,1,NULL,'OK',0),(9,78,2,0,'OK',0),(9,78,9,NULL,'OK',0),(9,79,1,26.9526,'OK',129882000),(9,79,2,0.111445,'OK',0.111445),(9,79,9,0,'OK',580.256),(9,80,1,26.5739,'OK',99896700),(9,80,2,-0.0031273,'OK',-0.0031273),(9,80,9,0,'OK',582.068),(9,81,1,26.6693,'OK',106722000),(9,81,2,0.0403178,'OK',0.0403178),(9,81,9,0,'OK',581.612),(9,82,1,27.5687,'OK',199074000),(9,82,2,0.0661233,'OK',0.0661233),(9,82,9,0.4,'OK',607.828),(9,83,1,27.5095,'OK',191073000),(9,83,2,-0.015461,'OK',-0.015461),(9,83,9,0,'OK',586.408),(9,84,1,27.7147,'OK',220265000),(9,84,2,0.141262,'OK',0.141262),(9,84,9,0,'OK',588.187),(9,85,1,27.4815,'OK',187391000),(9,85,2,-0.0164731,'OK',-0.0164731),(9,85,9,-0.1,'OK',581.214),(9,86,1,27.4166,'OK',179149000),(9,86,2,0.0919908,'OK',0.0919908),(9,86,9,-0.1,'OK',578.131),(9,87,1,27.5915,'OK',202236000),(9,87,2,-0.0233705,'OK',-0.0233705),(9,87,9,-0.1,'OK',577.055),(9,88,1,27.977,'OK',264183000),(9,88,2,-0.110622,'OK',-0.110622),(9,88,9,0,'OK',584.938),(9,89,1,28.1796,'OK',304014000),(9,89,2,0.111514,'OK',0.111514),(9,89,9,0.3,'OK',599.338),(9,90,1,27.8433,'OK',240814000),(9,90,2,0.037008,'OK',0.037008),(9,90,9,0,'OK',587.5),(9,91,1,27.8093,'OK',235190000),(9,91,2,0.0726956,'OK',0.0726956),(9,91,9,-0.1,'OK',579.442),(9,92,1,28.091,'OK',285921000),(9,92,2,0.206014,'OK',0.206014),(9,92,9,-0.2,'OK',577.223),(9,93,1,28.1823,'OK',304593000),(9,93,2,0.0525728,'OK',0.0525728),(9,93,9,-0.1,'OK',579.927),(9,94,1,28.1322,'OK',294188000),(9,94,2,0.0233899,'OK',0.0233899),(9,94,9,0,'OK',582.179),(9,95,1,28.1088,'OK',289467000),(9,95,2,0.141698,'OK',0.141698),(9,95,9,0.1,'OK',585.91),(9,96,1,28.24,'OK',317021000),(9,96,2,0.117763,'OK',0.117763),(9,96,9,0,'OK',585.338),(9,97,1,28.262,'OK',321894000),(9,97,2,0.109952,'OK',0.109952),(9,97,9,0,'OK',586.458),(9,98,1,28.3593,'OK',344340000),(9,98,2,0.295757,'OK',0.295757),(9,98,9,0,'OK',586.415),(9,99,1,28.3164,'OK',334264000),(9,99,2,0.127585,'OK',0.127585),(9,99,9,0,'OK',584.849),(9,100,1,28.1264,'OK',293021000),(9,100,2,-0.10447,'OK',-0.10447),(9,100,9,0.3,'OK',599.246),(9,101,1,27.9849,'OK',265641000),(9,101,2,-0.0767697,'OK',-0.0767697),(9,101,9,0.1,'OK',592.952),(9,102,1,28.2858,'OK',327253000),(9,102,2,0.116021,'OK',0.116021),(9,102,9,0,'OK',584.523),(9,103,1,28.3389,'OK',339518000),(9,103,2,-0.0139414,'OK',-0.0139414),(9,103,9,-0.1,'OK',580.849),(9,104,1,28.104,'OK',288503000),(9,104,2,0.070357,'OK',0.070357),(9,104,9,-0.1,'OK',582.335),(9,105,1,28.2346,'OK',315844000),(9,105,2,0.381783,'OK',0.381783),(9,105,9,0,'OK',587.084),(9,106,1,28.0536,'OK',278597000),(9,106,2,0.261217,'OK',0.261217),(9,106,9,0,'OK',587.598),(9,107,1,28.1767,'OK',303401000),(9,107,2,-0.115003,'OK',-0.115003),(9,107,9,-0.1,'OK',583.995),(9,108,1,28.2735,'OK',324476000),(9,108,2,-0.142427,'OK',-0.142427),(9,108,9,-0.1,'OK',583.069),(9,109,1,28.2103,'OK',310565000),(9,109,2,0.0619892,'OK',0.0619892),(9,109,9,-0.1,'OK',582.929),(9,110,1,28.1792,'OK',303941000),(9,110,2,0.137757,'OK',0.137757),(9,110,9,0,'OK',583.895),(9,111,1,28.1006,'OK',287827000),(9,111,2,-0.102104,'OK',-0.102104),(9,111,9,-0.1,'OK',580.886),(9,112,1,28.1253,'OK',292790000),(9,112,2,-0.108774,'OK',-0.108774),(9,112,9,0,'OK',585.602),(9,113,1,28.1266,'OK',293059000),(9,113,2,-0.0382874,'OK',-0.0382874),(9,113,9,0,'OK',581.803),(9,114,1,28.1198,'OK',291669000),(9,114,2,0.0382,'OK',0.0382),(9,114,9,0,'OK',585.13),(9,115,1,28.0612,'OK',280076000),(9,115,2,0.228892,'OK',0.228892),(9,115,9,0,'OK',586.009),(9,116,1,26.8633,'OK',122084000),(9,116,2,-0.0703605,'OK',-0.0703605),(9,116,9,0,'OK',586.537),(9,117,1,27.7363,'OK',223598000),(9,117,2,0.174037,'OK',0.174037),(9,117,9,0.1,'OK',589.262),(9,118,1,27.8264,'OK',238008000),(9,118,2,0.0810591,'OK',0.0810591),(9,118,9,0,'OK',585.363),(9,119,1,27.7804,'OK',230538000),(9,119,2,0.169171,'OK',0.169171),(9,119,9,0.1,'OK',587.795),(9,120,1,27.757,'OK',226832000),(9,120,2,0.165859,'OK',0.165859),(9,120,9,0,'OK',585.04),(9,121,1,27.2992,'OK',165147000),(9,121,2,0.0519218,'OK',0.0519218),(9,121,9,0,'OK',585.58),(9,122,1,27.6745,'OK',214222000),(9,122,2,0.238828,'OK',0.238828),(9,122,9,0,'OK',587.126),(9,123,1,NULL,'OK',0),(9,123,2,0,'OK',0),(9,123,9,NULL,'OK',0),(9,124,1,27.7846,'OK',231198000),(9,124,2,0.237195,'OK',0.237195),(9,124,9,0,'OK',584.871),(9,125,1,27.3398,'OK',169858000),(9,125,2,0.112255,'OK',0.112255),(9,125,9,0,'OK',584.178),(9,126,1,27.6552,'OK',211366000),(9,126,2,0.182982,'OK',0.182982),(9,126,9,0,'OK',583.851),(9,127,1,27.5588,'OK',197713000),(9,127,2,0.38831,'OK',0.38831),(9,127,9,0,'OK',587.765),(9,128,1,27.5629,'OK',198277000),(9,128,2,0.359967,'OK',0.359967),(9,128,9,-0.1,'OK',580.855),(9,129,1,27.4906,'OK',188586000),(9,129,2,0.418495,'OK',0.418495),(9,129,9,0,'OK',583.683),(9,130,1,27.3664,'OK',173026000),(9,130,2,0.386262,'OK',0.386262),(9,130,9,-0.1,'OK',578.897),(9,131,1,27.4676,'OK',185602000),(9,131,2,0.527558,'OK',0.527558),(9,131,9,0,'OK',583.595),(9,132,1,27.4203,'OK',179612000),(9,132,2,0.587619,'OK',0.587619),(9,132,9,0,'OK',583.264),(9,133,1,27.3106,'OK',166462000),(9,133,2,0.516549,'OK',0.516549),(9,133,9,0,'OK',583.019),(9,134,1,27.2711,'OK',161959000),(9,134,2,0.395261,'OK',0.395261),(9,134,9,-0.1,'OK',576.931),(9,135,1,NULL,'OK',0),(9,135,2,0,'OK',0),(9,135,9,NULL,'OK',0),(9,136,1,NULL,'OK',0),(9,136,2,0,'OK',0),(9,136,9,NULL,'OK',0),(9,137,1,26.7928,'OK',116265000),(9,137,2,0.252949,'OK',0.252949),(9,137,9,0.1,'OK',587.942),(9,138,1,26.3615,'OK',86218500),(9,138,2,0.165166,'OK',0.165166),(9,138,9,0.1,'OK',588.052),(9,139,1,27.1803,'OK',152081000),(9,139,2,0.466684,'OK',0.466684),(9,139,9,0.1,'OK',588.445),(9,140,1,27.1995,'OK',154124000),(9,140,2,0.370918,'OK',0.370918),(9,140,9,0,'OK',585.33),(9,141,1,NULL,'OK',0),(9,141,2,0,'OK',0),(9,141,9,NULL,'OK',0),(9,142,1,26.9174,'OK',126753000),(9,142,2,0.269121,'OK',0.269121),(9,142,9,-0.1,'OK',579.243),(9,143,1,26.7641,'OK',113973000),(9,143,2,0.407374,'OK',0.407374),(9,143,9,-0.1,'OK',578.209),(9,144,1,26.9965,'OK',133892000),(9,144,2,0.319958,'OK',0.319958),(9,144,9,0,'OK',580.926),(9,145,1,28.0968,'OK',287062000),(9,145,2,0.59209,'OK',0.59209),(9,145,9,0,'OK',585.337),(9,146,1,28.089,'OK',285511000),(9,146,2,0.569991,'OK',0.569991),(9,146,9,0,'OK',582.052),(9,147,1,27.2172,'OK',156029000),(9,147,2,1.04749,'OK',1.04749),(9,147,9,0,'OK',583.936),(9,148,9,-0.1,'OK',580.335),(9,149,1,25.249,'OK',39875100),(9,149,2,0.296182,'OK',0.296182),(9,149,9,0.2,'OK',594.036),(9,150,1,25.2707,'OK',40480400),(9,150,2,0.379849,'OK',0.379849),(9,150,9,0.3,'OK',600.601),(9,151,1,25.4504,'OK',45850600),(9,151,2,0.47922,'OK',0.47922),(9,151,9,0.1,'OK',592.727),(9,152,1,25.1859,'OK',38168000),(9,152,2,0.45027,'OK',0.45027),(9,152,9,0,'OK',588.07),(9,153,1,25.0872,'OK',35643900),(9,153,2,0.286396,'OK',0.286396),(9,153,9,0.1,'OK',592.837),(9,154,1,25.2206,'OK',39097800),(9,154,2,0.297743,'OK',0.297743),(9,154,9,0,'OK',588.39),(9,155,1,25.2879,'OK',40964900),(9,155,2,-0.119803,'OK',-0.119803),(9,155,9,-0.1,'OK',583.59),(9,156,1,25.1527,'OK',37300000),(9,156,2,0.312698,'OK',0.312698),(9,156,9,0.1,'OK',593.133),(9,157,1,25.2663,'OK',40355500),(9,157,2,0.158939,'OK',0.158939),(9,157,9,0.2,'OK',601.714),(9,158,1,25.0323,'OK',34314000),(9,158,2,0.0173324,'OK',0.0173324),(9,158,9,0.1,'OK',598.196),(9,159,1,24.8472,'OK',30183300),(9,159,2,0.431299,'OK',0.431299),(9,159,9,0.2,'OK',602.339),(9,160,1,24.7853,'OK',28914800),(9,160,2,0.22211,'OK',0.22211),(9,160,9,-0.2,'OK',581.852),(9,161,1,25.1097,'OK',36204200),(9,161,2,0.376381,'OK',0.376381),(9,161,9,-0.1,'OK',585.197),(9,162,1,24.4513,'OK',22939100),(9,162,2,0.225708,'OK',0.225708),(9,162,9,-0.1,'OK',583.355),(9,163,1,24.8325,'OK',29875600),(9,163,2,0.19309,'OK',0.19309),(9,163,9,0.2,'OK',601.435),(9,164,1,24.7183,'OK',27603500),(9,164,2,0.333316,'OK',0.333316),(9,164,9,0.1,'OK',599.223),(9,165,1,24.6714,'OK',26719000),(9,165,2,0.516537,'OK',0.516537),(9,165,9,0.1,'OK',601.013),(9,166,1,24.4456,'OK',22847800),(9,166,2,0.407908,'OK',0.407908),(9,166,9,0.2,'OK',604.167),(9,167,1,24.6686,'OK',26668600),(9,167,2,0.442639,'OK',0.442639),(9,167,9,0,'OK',597.522),(9,168,1,24.3676,'OK',21646300),(9,168,2,0.553223,'OK',0.553223),(9,168,9,-0.2,'OK',585.641),(9,169,1,24.5246,'OK',24135200),(9,169,2,0.298405,'OK',0.298405),(9,169,9,0,'OK',593.365),(9,170,1,24.6417,'OK',26175800),(9,170,2,0.241644,'OK',0.241644),(9,170,9,0,'OK',595.007),(9,171,1,24.5847,'OK',25161200),(9,171,2,0.242963,'OK',0.242963),(9,171,9,-0.2,'OK',583.8),(9,172,1,24.2854,'OK',20447400),(9,172,2,0.0326969,'OK',0.0326969),(9,172,9,-0.1,'OK',586.414),(9,173,1,23.7085,'OK',13708200),(9,173,2,0.10537,'OK',0.10537),(9,173,9,-0.2,'OK',581.855),(9,174,1,24.2023,'OK',19302600),(9,174,2,0.287331,'OK',0.287331),(9,174,9,-0.1,'OK',587.811),(9,175,1,24.0681,'OK',17587900),(9,175,2,0.157176,'OK',0.157176),(9,175,9,-0.1,'OK',584.51),(9,176,1,24.1609,'OK',18757100),(9,176,2,0.334285,'OK',0.334285),(9,176,9,0.1,'OK',597.349),(9,177,1,24.1457,'OK',18559800),(9,177,2,0.0993453,'OK',0.0993453),(9,177,9,0.1,'OK',593.379),(9,178,1,24.3123,'OK',20831700),(9,178,2,0.258803,'OK',0.258803),(9,178,9,0,'OK',589.468),(9,179,1,23.9469,'OK',16171300),(9,179,2,0.212061,'OK',0.212061),(9,179,9,-0.2,'OK',577.863),(10,1,1,27.6137,'OK',205381000),(10,1,2,0.753463,'OK',0.753463),(10,2,1,27.6393,'OK',209053000),(10,2,2,0.713871,'OK',0.713871),(10,3,1,27.6168,'OK',205822000),(10,3,2,0.773757,'OK',0.773757),(10,4,1,27.7447,'OK',224896000),(10,4,2,0.819079,'OK',0.819079),(10,5,1,27.6669,'OK',213089000),(10,5,2,0.804797,'OK',0.804797),(10,6,1,27.6536,'OK',211132000),(10,6,2,0.734627,'OK',0.734627),(10,7,1,27.6358,'OK',208554000),(10,7,2,0.726034,'OK',0.726034),(10,8,1,27.6782,'OK',214764000),(10,8,2,0.833695,'OK',0.833695),(10,10,1,28.8171,'OK',472945000),(10,10,2,0.409295,'OK',0.409295),(10,11,1,28.7586,'OK',454140000),(10,11,2,0.449166,'OK',0.449166),(10,12,1,28.8468,'OK',482768000),(10,12,2,0.445294,'OK',0.445294),(10,13,1,28.9994,'OK',536656000),(10,13,2,0.449014,'OK',0.449014),(10,14,1,28.9837,'OK',530834000),(10,14,2,0.507225,'OK',0.507225),(10,15,1,28.8663,'OK',489346000),(10,15,2,0.490865,'OK',0.490865),(10,16,1,28.9403,'OK',515113000),(10,16,2,0.546601,'OK',0.546601),(10,17,1,28.7798,'OK',460871000),(10,17,2,0.61805,'OK',0.61805),(10,18,1,28.633,'OK',416276000),(10,18,2,0.593198,'OK',0.593198),(10,19,1,27.8597,'WARNING',243552000),(10,19,2,0.599245,'OK',0.599245),(10,20,1,26.8568,'DANGER',121532000),(10,20,2,1.78719,'OK',1.78719),(10,21,1,27.3616,'OK',172448000),(10,21,2,1.67241,'OK',1.67241),(10,22,1,28.3031,'OK',331197000),(10,22,2,0.763891,'OK',0.763891),(10,23,1,27.9985,'OK',268157000),(10,23,2,0.72856,'OK',0.72856),(10,24,1,28.3724,'OK',347479000),(10,24,2,0.778264,'OK',0.778264),(10,25,1,28.4247,'OK',360310000),(10,25,2,0.750594,'OK',0.750594),(10,26,1,28.6431,'OK',419214000),(10,26,2,0.759518,'OK',0.759518),(10,27,1,28.3736,'OK',347782000),(10,27,2,0.728189,'OK',0.728189),(10,28,1,28.2328,'OK',315449000),(10,28,2,0.629441,'OK',0.629441),(10,29,1,28.3651,'OK',345745000),(10,29,2,0.686322,'OK',0.686322),(10,30,1,28.6963,'OK',434945000),(10,30,2,0.72198,'OK',0.72198),(10,31,1,28.5747,'OK',399793000),(10,31,2,0.604897,'OK',0.604897),(10,32,1,28.6247,'OK',413891000),(10,32,2,0.660456,'OK',0.660456),(10,33,1,28.5154,'OK',383704000),(10,33,2,0.674638,'OK',0.674638),(10,34,1,28.9316,'OK',512014000),(10,34,2,0.665252,'OK',0.665252),(10,35,1,28.4973,'OK',378915000),(10,35,2,0.717343,'OK',0.717343),(10,36,1,28.4582,'OK',368795000),(10,36,2,0.707902,'OK',0.707902),(10,37,1,28.319,'OK',334863000),(10,37,2,0.68279,'OK',0.68279),(10,38,1,28.4376,'OK',363554000),(10,38,2,0.730543,'OK',0.730543),(10,39,1,28.4013,'OK',354533000),(10,39,2,0.664973,'OK',0.664973),(10,40,1,28.443,'OK',364915000),(10,40,2,0.824523,'OK',0.824523),(10,41,1,28.1082,'WARNING',289339000),(10,41,2,0.702372,'OK',0.702372),(10,42,1,28.2861,'OK',327322000),(10,42,2,0.775746,'OK',0.775746),(10,43,1,28.2168,'OK',311960000),(10,43,2,0.737388,'OK',0.737388),(10,44,1,28.4017,'OK',354615000),(10,44,2,0.813345,'OK',0.813345),(10,45,1,28.2326,'OK',315401000),(10,45,2,0.762139,'OK',0.762139),(10,46,1,28.1528,'OK',298427000),(10,46,2,0.739227,'OK',0.739227),(10,47,1,28.2952,'OK',329383000),(10,47,2,0.779445,'OK',0.779445),(10,48,1,28.2183,'OK',312277000),(10,48,2,0.769387,'OK',0.769387),(10,49,1,28.2137,'OK',311299000),(10,49,2,0.858143,'OK',0.858143),(10,50,1,28.1662,'OK',301215000),(10,50,2,0.73219,'OK',0.73219),(10,51,1,28.8255,'OK',475700000),(10,51,2,0.790129,'OK',0.790129),(10,52,1,28.3703,'OK',346987000),(10,52,2,0.792003,'OK',0.792003),(10,53,1,28.2869,'OK',327497000),(10,53,2,0.71258,'OK',0.71258),(10,55,1,25.4492,'OK',45811500),(10,55,2,-0.284549,'OK',-0.284549),(10,55,9,NULL,'OK',576.984),(10,56,1,26.4806,'OK',93641400),(10,56,2,-0.255506,'OK',-0.255506),(10,56,9,NULL,'OK',581.923),(10,57,1,26.5201,'OK',96239400),(10,57,2,0.00874498,'OK',0.00874498),(10,57,9,NULL,'OK',581.205),(10,58,1,26.0987,'OK',71861200),(10,58,2,-0.25548,'OK',-0.25548),(10,58,9,NULL,'OK',581.709),(10,59,1,26.577,'OK',100109000),(10,59,2,-0.284157,'OK',-0.284157),(10,59,9,NULL,'OK',580.401),(10,60,1,26.8975,'OK',125012000),(10,60,2,-0.0257226,'OK',-0.0257226),(10,60,9,NULL,'OK',575.629),(10,61,1,26.9481,'OK',129471000),(10,61,2,-0.012617,'OK',-0.012617),(10,61,9,NULL,'OK',575.179),(10,62,1,26.8916,'OK',124506000),(10,62,2,-0.043599,'OK',-0.043599),(10,62,9,NULL,'OK',575.787),(10,63,1,26.274,'OK',81143800),(10,63,2,-0.0270709,'OK',-0.0270709),(10,63,9,NULL,'OK',576.836),(10,64,1,27.0225,'OK',136328000),(10,64,2,0.170828,'OK',0.170828),(10,64,9,NULL,'OK',575.195),(10,65,1,25.5253,'OK',48292400),(10,65,2,0.0418356,'OK',0.0418356),(10,65,9,-0.1,'OK',573.42),(10,66,1,20.9431,'OK',2016120),(10,66,2,-0.725898,'OK',-0.725898),(10,66,9,0.3,'OK',593.618),(10,67,1,24.4005,'OK',22145700),(10,67,2,0.513918,'OK',0.513918),(10,67,9,-0.1,'OK',575.559),(10,68,1,NULL,'OK',0),(10,68,2,0,'OK',0),(10,68,9,NULL,'OK',0),(10,69,1,NULL,'OK',0),(10,69,2,0,'OK',0),(10,69,9,NULL,'OK',0),(10,70,1,NULL,'OK',0),(10,70,2,0,'OK',0),(10,70,9,NULL,'OK',0),(10,71,1,26.1757,'OK',75798500),(10,71,2,-0.0230694,'OK',-0.0230694),(10,71,9,-0.1,'OK',575.1),(10,72,1,25.4578,'OK',46083900),(10,72,2,0.199809,'OK',0.199809),(10,72,9,0,'OK',579.038),(10,73,1,NULL,'OK',0),(10,73,2,0,'OK',0),(10,73,9,NULL,'OK',0),(10,74,1,NULL,'OK',0),(10,74,2,0,'OK',0),(10,74,9,NULL,'OK',0),(10,75,1,NULL,'OK',0),(10,75,2,0,'OK',0),(10,75,9,NULL,'OK',0),(10,76,1,25.5616,'OK',49524200),(10,76,2,-0.000830353,'OK',-0.000830353),(10,76,9,-0.1,'OK',571.293),(10,77,1,26.4029,'OK',88731000),(10,77,2,0.102805,'OK',0.102805),(10,77,9,-0.1,'OK',569.913),(10,78,1,NULL,'OK',0),(10,78,2,0,'OK',0),(10,78,9,NULL,'OK',0),(10,79,1,NULL,'OK',0),(10,79,2,0,'OK',0),(10,79,9,NULL,'OK',0),(10,80,1,25.7356,'OK',55870400),(10,80,2,-0.146355,'OK',-0.146355),(10,80,9,0,'OK',575.14),(10,81,1,26.1575,'OK',74851000),(10,81,2,-0.1372,'OK',-0.1372),(10,81,9,0,'OK',576.359),(10,82,1,27.1169,'OK',145546000),(10,82,2,0.141762,'OK',0.141762),(10,82,9,0.4,'OK',599.889),(10,83,1,27.0599,'OK',139904000),(10,83,2,-0.168078,'OK',-0.168078),(10,83,9,0,'OK',580.388),(10,84,1,26.9032,'OK',125506000),(10,84,2,0.140844,'OK',0.140844),(10,84,9,0,'OK',581.229),(10,85,1,27.3951,'OK',176505000),(10,85,2,0.129218,'OK',0.129218),(10,85,9,0,'OK',575.612),(10,86,1,27.2404,'OK',158559000),(10,86,2,-0.0316676,'OK',-0.0316676),(10,86,9,-0.1,'OK',572.133),(10,87,1,27.2284,'OK',157243000),(10,87,2,0.162194,'OK',0.162194),(10,87,9,-0.1,'OK',571.474),(10,88,1,28.1214,'OK',292010000),(10,88,2,0.100383,'OK',0.100383),(10,88,9,0,'OK',579.065),(10,89,1,28.2422,'OK',317494000),(10,89,2,-0.181738,'OK',-0.181738),(10,89,9,0.2,'OK',591.087),(10,90,1,27.2641,'OK',161177000),(10,90,2,-0.0321501,'OK',-0.0321501),(10,90,9,0,'OK',580.724),(10,91,1,27.1911,'OK',153224000),(10,91,2,-0.19651,'OK',-0.19651),(10,91,9,-0.1,'OK',573.693),(10,92,1,27.4803,'OK',187234000),(10,92,2,-0.0352796,'OK',-0.0352796),(10,92,9,-0.2,'OK',571.52),(10,93,1,27.4618,'OK',184856000),(10,93,2,-0.0115495,'OK',-0.0115495),(10,93,9,-0.1,'OK',572.75),(10,94,1,27.4871,'OK',188120000),(10,94,2,-0.0492154,'OK',-0.0492154),(10,94,9,0,'OK',576.61),(10,95,1,27.4873,'OK',188145000),(10,95,2,-0.0815229,'OK',-0.0815229),(10,95,9,0,'OK',579.128),(10,96,1,27.5733,'OK',199706000),(10,96,2,-0.280926,'OK',-0.280926),(10,96,9,0,'OK',578.596),(10,97,1,27.7668,'OK',228374000),(10,97,2,0.0406356,'OK',0.0406356),(10,97,9,0,'OK',578.919),(10,98,1,27.5817,'OK',200872000),(10,98,2,-0.289845,'OK',-0.289845),(10,98,9,0,'OK',579.881),(10,99,1,27.8097,'OK',235270000),(10,99,2,-0.155235,'OK',-0.155235),(10,99,9,0,'OK',578.108),(10,100,1,27.5813,'OK',200814000),(10,100,2,-0.166539,'OK',-0.166539),(10,100,9,0.4,'OK',600.534),(10,101,1,27.4132,'OK',178733000),(10,101,2,-0.247247,'OK',-0.247247),(10,101,9,0.1,'OK',587.964),(10,102,1,27.7496,'OK',225670000),(10,102,2,-0.12789,'OK',-0.12789),(10,102,9,0,'OK',578.516),(10,103,1,27.8726,'OK',245751000),(10,103,2,-0.229584,'OK',-0.229584),(10,103,9,-0.1,'OK',574.015),(10,104,1,27.6403,'OK',209201000),(10,104,2,0.127136,'OK',0.127136),(10,104,9,-0.1,'OK',575.326),(10,105,1,27.8265,'OK',238015000),(10,105,2,-0.144403,'OK',-0.144403),(10,105,9,0,'OK',579.959),(10,106,1,27.6169,'OK',205830000),(10,106,2,-0.0607538,'OK',-0.0607538),(10,106,9,0,'OK',580.706),(10,107,1,27.8099,'OK',235298000),(10,107,2,-0.276887,'OK',-0.276887),(10,107,9,-0.1,'OK',576.99),(10,108,1,27.9569,'OK',260535000),(10,108,2,-0.243772,'OK',-0.243772),(10,108,9,-0.1,'OK',575.767),(10,109,1,27.8885,'OK',248464000),(10,109,2,-0.194557,'OK',-0.194557),(10,109,9,-0.1,'OK',576.833),(10,110,1,27.9198,'OK',253914000),(10,110,2,0.0415189,'OK',0.0415189),(10,110,9,0,'OK',577.684),(10,111,1,27.8175,'OK',236543000),(10,111,2,-0.240396,'OK',-0.240396),(10,111,9,-0.1,'OK',574.46),(10,112,1,27.6957,'OK',217390000),(10,112,2,-0.343746,'OK',-0.343746),(10,112,9,0,'OK',578.404),(10,113,1,27.7998,'OK',233652000),(10,113,2,-0.566965,'OK',-0.566965),(10,113,9,-0.1,'OK',573.978),(10,114,1,27.7676,'OK',228492000),(10,114,2,-0.117596,'OK',-0.117596),(10,114,9,0,'OK',577.378),(10,115,1,NULL,'OK',0),(10,115,2,0,'OK',0),(10,115,9,NULL,'OK',0),(10,116,1,26.4204,'OK',89813800),(10,116,2,-0.258497,'OK',-0.258497),(10,116,9,0,'OK',579.577),(10,117,1,27.6142,'OK',205452000),(10,117,2,-0.201074,'OK',-0.201074),(10,117,9,0.1,'OK',581.475),(10,118,1,27.6865,'OK',216003000),(10,118,2,-0.176455,'OK',-0.176455),(10,118,9,0,'OK',577.825),(10,119,1,27.527,'OK',193400000),(10,119,2,-0.193998,'OK',-0.193998),(10,119,9,0.1,'OK',580.872),(10,120,1,27.6766,'OK',214534000),(10,120,2,-0.117112,'OK',-0.117112),(10,120,9,0,'OK',578.83),(10,121,1,27.2206,'OK',156393000),(10,121,2,-0.286339,'OK',-0.286339),(10,121,9,0,'OK',578.389),(10,122,1,27.57,'OK',199244000),(10,122,2,-0.32864,'OK',-0.32864),(10,122,9,0,'OK',579.663),(10,123,1,NULL,'OK',0),(10,123,2,0,'OK',0),(10,123,9,NULL,'OK',0),(10,124,1,27.6731,'OK',214007000),(10,124,2,-0.292786,'OK',-0.292786),(10,124,9,0,'OK',576.814),(10,125,1,27.1658,'OK',150560000),(10,125,2,-0.288796,'OK',-0.288796),(10,125,9,0,'OK',576.819),(10,126,1,27.6077,'OK',204525000),(10,126,2,0.0432594,'OK',0.0432594),(10,126,9,0,'OK',576.782),(10,127,1,27.5312,'OK',193964000),(10,127,2,-0.286306,'OK',-0.286306),(10,127,9,0,'OK',580.525),(10,128,1,27.6137,'OK',205376000),(10,128,2,-0.150404,'OK',-0.150404),(10,128,9,-0.1,'OK',573.502),(10,129,1,27.7301,'OK',222634000),(10,129,2,-0.222582,'OK',-0.222582),(10,129,9,0,'OK',576.987),(10,130,1,27.5594,'OK',197784000),(10,130,2,-0.0196432,'OK',-0.0196432),(10,130,9,-0.1,'OK',572.12),(10,131,1,27.5927,'OK',202412000),(10,131,2,-0.326296,'OK',-0.326296),(10,131,9,0,'OK',576.91),(10,132,1,27.4894,'OK',188424000),(10,132,2,-0.24123,'OK',-0.24123),(10,132,9,0,'OK',576.439),(10,133,1,27.3597,'OK',172228000),(10,133,2,-0.193483,'OK',-0.193483),(10,133,9,0,'OK',575.451),(10,134,1,27.5758,'OK',200056000),(10,134,2,-0.295343,'OK',-0.295343),(10,134,9,-0.1,'OK',570.413),(10,135,1,NULL,'OK',0),(10,135,2,0,'OK',0),(10,135,9,NULL,'OK',0),(10,136,1,NULL,'OK',0),(10,136,2,0,'OK',0),(10,136,9,NULL,'OK',0),(10,137,1,27.2268,'OK',157064000),(10,137,2,-0.212072,'OK',-0.212072),(10,137,9,0.1,'OK',581.027),(10,138,1,NULL,'OK',0),(10,138,2,0,'OK',0),(10,138,9,NULL,'OK',0),(10,139,1,27.5918,'OK',202285000),(10,139,2,-0.300245,'OK',-0.300245),(10,139,9,0.1,'OK',579.678),(10,140,1,28.4902,'OK',377044000),(10,140,2,-0.0796427,'OK',-0.0796427),(10,140,9,0,'OK',576.294),(10,141,1,NULL,'OK',0),(10,141,2,0,'OK',0),(10,141,9,NULL,'OK',0),(10,142,1,27.3427,'OK',170211000),(10,142,2,-0.289336,'OK',-0.289336),(10,142,9,-0.1,'OK',572.008),(10,143,1,27.1647,'OK',150448000),(10,143,2,-0.298326,'OK',-0.298326),(10,143,9,-0.1,'OK',571.825),(10,144,1,27.6585,'OK',211852000),(10,144,2,-0.165966,'OK',-0.165966),(10,144,9,0,'OK',573.659),(10,145,1,27.8905,'OK',248823000),(10,145,2,-0.515547,'OK',-0.515547),(10,145,9,0,'OK',577.814),(10,146,1,27.9468,'OK',258710000),(10,146,2,-0.356696,'OK',-0.356696),(10,146,9,0,'OK',575.128),(10,147,1,27.2262,'OK',157003000),(10,147,2,-0.0991574,'OK',-0.0991574),(10,147,9,0,'OK',577.641),(10,148,9,0,'OK',572.688),(10,149,1,25.3966,'OK',44171800),(10,149,2,-0.117801,'OK',-0.117801),(10,149,9,0.1,'OK',579.963),(10,150,1,25.4731,'OK',46576100),(10,150,2,-0.378482,'OK',-0.378482),(10,150,9,0.2,'OK',588.153),(10,151,1,NULL,'OK',0),(10,151,2,0,'OK',0),(10,151,9,NULL,'OK',0),(10,152,1,25.5847,'OK',50324200),(10,152,2,-0.351623,'OK',-0.351623),(10,152,9,0,'OK',578.869),(10,153,1,25.2998,'OK',41305000),(10,153,2,-0.340131,'OK',-0.340131),(10,153,9,0.1,'OK',582.992),(10,154,1,25.8056,'OK',58648300),(10,154,2,-0.361243,'OK',-0.361243),(10,154,9,0.1,'OK',582.163),(10,155,1,26.3204,'OK',83798900),(10,155,2,-0.345332,'OK',-0.345332),(10,155,9,-0.1,'OK',575.864),(10,156,1,25.6291,'OK',51893700),(10,156,2,-0.239621,'OK',-0.239621),(10,156,9,0.1,'OK',583.952),(10,157,1,26.2339,'OK',78920500),(10,157,2,-0.606468,'OK',-0.606468),(10,157,9,0.3,'OK',595.445),(10,158,1,26.2327,'OK',78856600),(10,158,2,-0.541013,'OK',-0.541013),(10,158,9,0.1,'OK',585.589),(10,159,1,25.4456,'OK',45696600),(10,159,2,-0.292473,'OK',-0.292473),(10,159,9,0.2,'OK',591.99),(10,160,1,25.3901,'OK',43971700),(10,160,2,-0.428122,'OK',-0.428122),(10,160,9,-0.2,'OK',571.201),(10,161,1,26.0579,'OK',69856000),(10,161,2,-0.286813,'OK',-0.286813),(10,161,9,-0.1,'OK',577.891),(10,162,1,25.1806,'OK',38028400),(10,162,2,-0.339434,'OK',-0.339434),(10,162,9,-0.1,'OK',576.601),(10,163,1,25.8375,'OK',59958900),(10,163,2,-0.272848,'OK',-0.272848),(10,163,9,0.1,'OK',589.34),(10,164,1,25.9114,'OK',63110100),(10,164,2,-0.472467,'OK',-0.472467),(10,164,9,0,'OK',585.659),(10,165,1,25.6758,'OK',53602100),(10,165,2,-0.486019,'OK',-0.486019),(10,165,9,0.2,'OK',595.794),(10,166,1,25.4467,'OK',45730900),(10,166,2,-0.265712,'OK',-0.265712),(10,166,9,0.1,'OK',588.582),(10,167,1,25.4624,'OK',46232100),(10,167,2,-0.373338,'OK',-0.373338),(10,167,9,0.1,'OK',592.753),(10,168,1,25.2378,'OK',39566800),(10,168,2,-0.343887,'OK',-0.343887),(10,168,9,-0.1,'OK',578.85),(10,169,1,25.9271,'OK',63801400),(10,169,2,-0.412876,'OK',-0.412876),(10,169,9,-0.1,'OK',581.072),(10,170,1,26.0387,'OK',68933900),(10,170,2,-0.262744,'OK',-0.262744),(10,170,9,0.1,'OK',587.918),(10,171,1,25.9068,'OK',62912100),(10,171,2,-0.440294,'OK',-0.440294),(10,171,9,-0.2,'OK',576.318),(10,172,1,25.6756,'OK',53596500),(10,172,2,-0.313355,'OK',-0.313355),(10,172,9,-0.1,'OK',578.069),(10,173,1,24.1947,'OK',19201500),(10,173,2,-0.471261,'OK',-0.471261),(10,173,9,-0.2,'OK',572.914),(10,174,1,25.0353,'OK',34386200),(10,174,2,-0.479501,'OK',-0.479501),(10,174,9,0,'OK',581.696),(10,175,1,25.706,'OK',54737000),(10,175,2,-0.343446,'OK',-0.343446),(10,175,9,-0.1,'OK',576.831),(10,176,1,25.4505,'OK',45851700),(10,176,2,-0.448999,'OK',-0.448999),(10,176,9,0.1,'OK',584.604),(10,177,1,25.7529,'OK',56545600),(10,177,2,-0.434803,'OK',-0.434803),(10,177,9,0,'OK',580.598),(10,178,1,25.4238,'OK',45011700),(10,178,2,-0.367386,'OK',-0.367386),(10,178,9,0,'OK',578.002),(10,179,1,25.0245,'OK',34128100),(10,179,2,-0.412534,'OK',-0.412534),(10,179,9,-0.2,'OK',567.57),(11,1,3,5.23113,'OK',5.23113),(11,2,3,5.35641,'OK',5.35641),(11,3,3,5.27901,'OK',5.27901),(11,4,3,5.17416,'OK',5.17416),(11,5,3,5.24558,'OK',5.24558),(11,6,3,5.32608,'OK',5.32608),(11,7,3,5.41099,'OK',5.41099),(11,8,3,5.21798,'OK',5.21798),(11,10,3,5.27939,'OK',5.27939),(11,11,3,4.92267,'OK',4.92267),(11,12,3,4.99602,'OK',4.99602),(11,13,3,5.27893,'OK',5.27893),(11,14,3,5.54427,'OK',5.54427),(11,15,3,5.72327,'OK',5.72327),(11,16,3,5.68246,'OK',5.68246),(11,17,3,5.97426,'OK',5.97426),(11,18,3,7.13823,'OK',7.13823),(11,19,3,11.1,'OK',11.1),(11,20,3,50,'OK',50),(11,21,3,50,'OK',50),(11,22,3,14.0049,'OK',14.0049),(11,23,3,10.7456,'OK',10.7456),(11,24,3,6.91851,'OK',6.91851),(11,25,3,4.82606,'OK',4.82606),(11,26,3,4.8298,'OK',4.8298),(11,27,3,4.64933,'OK',4.64933),(11,28,3,4.82545,'OK',4.82545),(11,29,3,4.77214,'OK',4.77214),(11,30,3,4.97558,'OK',4.97558),(11,31,3,4.88224,'OK',4.88224),(11,32,3,4.62066,'OK',4.62066),(11,33,3,4.54777,'OK',4.54777),(11,34,3,4.66408,'OK',4.66408),(11,35,3,4.66483,'OK',4.66483),(11,36,3,4.74579,'OK',4.74579),(11,37,3,4.69393,'OK',4.69393),(11,38,3,4.243,'OK',4.243),(11,39,3,4.82208,'OK',4.82208),(11,40,3,5.52807,'OK',5.52807),(11,41,3,5.7801,'OK',5.7801),(11,42,3,6.13898,'OK',6.13898),(11,43,3,6.15608,'OK',6.15608),(11,44,3,6.36563,'OK',6.36563),(11,45,3,6.10118,'OK',6.10118),(11,46,3,6.34254,'OK',6.34254),(11,47,3,6.47736,'OK',6.47736),(11,48,3,6.38384,'OK',6.38384),(11,49,3,6.5098,'OK',6.5098),(11,50,3,6.42102,'OK',6.42102),(11,51,3,6.20089,'OK',6.20089),(11,52,3,6.36206,'OK',6.36206),(11,53,3,6.56255,'OK',6.56255),(11,55,3,53.5444,'OK',53.5444),(11,56,3,58.0813,'OK',58.0813),(11,57,3,58.0717,'OK',58.0717),(11,58,3,60.8005,'OK',60.8005),(11,59,3,64.7004,'OK',64.7004),(11,60,3,61.1689,'OK',61.1689),(11,61,3,58.4629,'OK',58.4629),(11,62,3,62.577,'OK',62.577),(11,63,3,60.5248,'OK',60.5248),(11,64,3,60.4417,'OK',60.4417),(11,65,3,69.5529,'OK',69.5529),(11,66,3,58.715,'OK',58.715),(11,67,3,59.3122,'OK',59.3122),(11,68,3,58.4975,'OK',58.4975),(11,69,3,81.2738,'OK',81.2738),(11,70,3,71.0166,'OK',71.0166),(11,71,3,61.1701,'OK',61.1701),(11,72,3,64.3706,'OK',64.3706),(11,73,3,39.4125,'OK',39.4125),(11,74,3,39.6584,'OK',39.6584),(11,75,3,41.1977,'OK',41.1977),(11,76,3,42.0271,'OK',42.0271),(11,77,3,47.4823,'OK',47.4823),(11,78,3,48.9554,'OK',48.9554),(11,79,3,46.7966,'OK',46.7966),(11,80,3,48.0325,'OK',48.0325),(11,81,3,46.174,'OK',46.174),(11,82,3,31.1541,'OK',31.1541),(11,83,3,38.2752,'OK',38.2752),(11,84,3,38.368,'OK',38.368),(11,85,3,41.2827,'OK',41.2827),(11,86,3,41.89,'OK',41.89),(11,87,3,36.3563,'OK',36.3563),(11,88,3,30.909,'OK',30.909),(11,89,3,33.2468,'OK',33.2468),(11,90,3,35.74,'OK',35.74),(11,91,3,35.9447,'OK',35.9447),(11,92,3,33.5019,'OK',33.5019),(11,93,3,32.3477,'OK',32.3477),(11,94,3,20.4428,'OK',20.4428),(11,95,3,27.3138,'OK',27.3138),(11,96,3,27.2631,'OK',27.2631),(11,97,3,28.5258,'OK',28.5258),(11,98,3,28.497,'OK',28.497),(11,99,3,29.119,'OK',29.119),(11,100,3,17.6098,'OK',17.6098),(11,101,3,24.9529,'OK',24.9529),(11,102,3,32.3748,'OK',32.3748),(11,103,3,30.9603,'OK',30.9603),(11,104,3,31.5838,'OK',31.5838),(11,105,3,30.1921,'OK',30.1921),(11,106,3,31.6043,'OK',31.6043),(11,107,3,29.429,'OK',29.429),(11,108,3,28.249,'OK',28.249),(11,109,3,29.5889,'OK',29.5889),(11,110,3,29.9828,'OK',29.9828),(11,111,3,29.9273,'OK',29.9273),(11,112,3,29.1014,'OK',29.1014),(11,113,3,29.8439,'OK',29.8439),(11,114,3,25.7305,'OK',25.7305),(11,115,3,26.6784,'OK',26.6784),(11,116,3,30.4177,'OK',30.4177),(11,117,3,30.6947,'OK',30.6947),(11,118,3,31.4034,'OK',31.4034),(11,119,3,30.7167,'OK',30.7167),(11,120,3,31.7863,'OK',31.7863),(11,121,3,31.8334,'OK',31.8334),(11,122,3,28.6722,'OK',28.6722),(11,123,3,30.2036,'OK',30.2036),(11,124,3,29.2382,'OK',29.2382),(11,125,3,30.926,'OK',30.926),(11,126,3,33.486,'OK',33.486),(11,127,3,36.0919,'OK',36.0919),(11,128,3,36.4161,'OK',36.4161),(11,129,3,35.4263,'OK',35.4263),(11,130,3,38.0801,'OK',38.0801),(11,131,3,36.9105,'OK',36.9105),(11,132,3,36.3085,'OK',36.3085),(11,133,3,35.7597,'OK',35.7597),(11,134,3,35.6712,'OK',35.6712),(11,135,3,26.9654,'OK',26.9654),(11,136,3,28.5387,'OK',28.5387),(11,137,3,30.9951,'OK',30.9951),(11,138,3,30.6415,'OK',30.6415),(11,139,3,28.1051,'OK',28.1051),(11,140,3,28.0594,'OK',28.0594),(11,141,3,30.8601,'OK',30.8601),(11,142,3,35.6485,'OK',35.6485),(11,143,3,35.7819,'OK',35.7819),(11,144,3,33.4424,'OK',33.4424),(11,145,3,22.1754,'OK',22.1754),(11,146,3,20.8338,'OK',20.8338),(11,147,3,24.166,'OK',24.166),(11,149,3,58.2152,'OK',58.2152),(11,150,3,59.0787,'OK',59.0787),(11,151,3,56.3206,'OK',56.3206),(11,152,3,71.4307,'OK',71.4307),(11,153,3,61.0546,'OK',61.0546),(11,154,3,64.2667,'OK',64.2667),(11,155,3,59.2671,'OK',59.2671),(11,156,3,66.7605,'OK',66.7605),(11,157,3,53.7126,'OK',53.7126),(11,158,3,61.5977,'OK',61.5977),(11,159,3,61.2755,'OK',61.2755),(11,160,3,64.9997,'OK',64.9997),(11,161,3,81.6474,'OK',81.6474),(11,162,3,117.745,'OK',117.745),(11,163,3,71.3171,'OK',71.3171),(11,164,3,74.2961,'OK',74.2961),(11,165,3,77.4273,'OK',77.4273),(11,166,3,78.5894,'OK',78.5894),(11,167,3,84.8874,'OK',84.8874),(11,168,3,82.812,'OK',82.812),(11,169,3,75.4389,'OK',75.4389),(11,170,3,76.3362,'OK',76.3362),(11,171,3,85.9435,'OK',85.9435),(11,172,3,79.3526,'OK',79.3526),(11,173,3,80.2627,'OK',80.2627),(11,174,3,105.312,'OK',105.312),(11,175,3,106.896,'OK',106.896),(11,176,3,90.1796,'OK',90.1796),(11,177,3,82.9191,'OK',82.9191),(11,178,3,93.5949,'OK',93.5949),(11,179,3,98.6976,'OK',98.6976);
/*!40000 ALTER TABLE `data` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `data_source`
--

LOCK TABLES `data_source` WRITE;
/*!40000 ALTER TABLE `data_source` DISABLE KEYS */;
INSERT INTO `data_source` VALUES (2,_binary 'oùkùùùMùùxI%Re',1,'Lumos',75,1),(3,_binary 'ùù8Bo E}ùù[;ùù',1,'Proxeon 3',93,1),(4,_binary 'ùùùyH<ùùù5ù',1,'XL',48,1),(5,_binary 'd.Yh`hHeùù5ùù0ù',1,'Velos',79,1),(6,_binary ':?FLùùuxoùP\0\0',1,'Proxeon 2',93,1),(7,_binary '?\0ùùùGùùCR?ùKù\0\0',1,'Proxeon 1',94,1);
/*!40000 ALTER TABLE `data_source` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
INSERT INTO `file` VALUES (1,'3ba969cd67a8014bdd58fa54b6807fc8','2018-09-09 00:39:00','180907_Q_QC1F_01_07-201809090039.qcml',1,1,1),(2,'4eb3e16ddf042f3e720858b61f9d8602','2018-09-09 05:20:00','180907_Q_QC1F_01_08-201809090520.qcml',1,1,1),(3,'247a5f87d16db49fb4958bc8c2641e7a','2018-09-09 09:55:00','180907_Q_QC1F_01_09-201809090955.qcml',1,1,1),(4,'bab4cf261eb39f6daff5668ab15840c9','2018-09-09 14:29:00','180907_Q_QC1F_01_10-201809091429.qcml',1,1,1),(5,'c7d65bc4fba6d47337ec37b493401815','2018-09-09 19:06:00','180907_Q_QC1F_01_11-201809091906.qcml',1,1,1),(6,'a5d302fb76973c224ce1e8eb7f58d9cc','2018-09-09 23:45:00','180907_Q_QC1F_01_12-201809092345.qcml',1,1,1),(7,'f3d4d85c5dc224d2d79169f3ef908402','2018-09-10 04:23:00','180907_Q_QC1F_01_14-201809100423.qcml',1,1,1),(8,'7c8cc1904c100e65c1ddf93b8b2b0930','2018-09-10 09:00:00','180907_Q_QC1F_01_15-201809100900.qcml',1,1,1),(9,'9497607a1502483b68fc0f2741a6b3a5','2018-09-10 15:24:00','180910_Q_QC1F_01_01-201809101524.qcml',1,1,1),(10,'ac021fc663c8e38d5d3bf2dde123c545','2018-09-10 18:45:00','180910_Q_QC1F_01_02-201809101845.qcml',1,1,1),(11,'bfcbf3795f6dbd35589156c7943e73a0','2018-09-10 19:41:00','180910_Q_QC1F_01_03-201809101941.qcml',1,1,1),(12,'3c9ba4be4ab0e532da7b3afa1bd3ba56','2018-09-11 00:52:00','180910_Q_QC1F_01_04-201809110052.qcml',1,1,1),(13,'1798895479c2f6c71b32aaf6df1d6cab','2018-09-11 04:13:00','180910_Q_QC1F_01_05-201809110413.qcml',1,1,1),(14,'5ae40212115225da9429de920ae5b4fb','2018-09-11 05:08:00','180910_Q_QC1F_01_06-201809110508.qcml',1,1,1),(15,'bfad4f9cd5766de895f78a78bd8b76d0','2018-09-11 09:23:00','180911_Q_QC1F_01_01-201809110923.qcml',1,1,1),(16,'92cfbc309d3e85c98286c128e7238f95','2018-09-11 13:38:00','180911_Q_QC1F_01_02-201809111338.qcml',1,1,1),(17,'fc1fb3767a47e02619978ccad814e383','2018-09-11 17:52:00','180911_Q_QC1F_01_03-201809111752.qcml',1,1,1),(18,'b2b05f8d87e73309d8e3ee02a7275779','2018-09-11 22:07:00','180911_Q_QC1F_01_04-201809112207.qcml',1,1,1),(19,'fbae414c2d6babd9ab3faa61030e72cb','2018-09-12 02:21:00','180911_Q_QC1F_01_05-201809120221.qcml',1,1,1),(20,'31a6e90cf3d3fc25192f9f93a8a60d3c','2018-09-12 06:36:00','180911_Q_QC1F_01_06-201809120636.qcml',1,1,1),(21,'e269464ee073e70b7ebb8693ce7df461','2018-09-12 10:51:00','180911_Q_QC1F_01_07-201809121051.qcml',1,1,1),(22,'aca9b1811474d0bc836482f57245d62c','2018-09-12 14:11:00','180911_Q_QC1F_01_07b-201809121411.qcml',1,1,1),(23,'fffc8aab2b1546871ff4a107b251eb9f','2018-09-12 15:43:00','180912_Q_QC1F_01_01-201809121543.qcml',1,1,1),(24,'35b4d16df6a4395cd98fc04f4acb125a','2018-09-12 16:37:00','180912_Q_QC1F_01_02-201809121637.qcml',1,1,1),(25,'e78481553f2ecee19c4e82ec7cf2cb3d','2018-09-12 19:57:00','180912_Q_QC1F_01_03-201809121957.qcml',1,1,1),(26,'b2d93fdecb5a087981b931e5482e43c1','2018-09-12 20:52:00','180912_Q_QC1F_01_04-201809122052.qcml',1,1,1),(27,'03642b077460e19cfe540167b91b28fb','2018-09-13 00:12:00','180912_Q_QC1F_01_05-201809130012.qcml',1,1,1),(28,'772e5d1a3665e188a24a5490c0aa8dca','2018-09-13 02:57:00','180912_Q_QC1F_01_06-201809130257.qcml',1,1,1),(29,'43a0bc76547631363ee68902bcbad3b0','2018-09-13 03:52:00','180912_Q_QC1F_01_07-201809130352.qcml',1,1,1),(30,'4042922bd97f26c20ed180bfe8369170','2018-09-13 07:12:00','180912_Q_QC1F_01_08-201809130712.qcml',1,1,1),(31,'42eb835386d51c328508502a3b548396','2018-09-13 08:06:00','180912_Q_QC1F_01_09-201809130806.qcml',1,1,1),(32,'f957d77a557e3642c45b68956c4103e2','2018-09-13 09:01:00','180912_Q_QC1F_01_10-201809130901.qcml',1,1,1),(33,'2467e7cbe0ce052909a42ac96e1293cd','2018-09-13 09:56:00','180912_Q_QC1F_01_11-201809130956.qcml',1,1,1),(34,'b1e43b9b8d8fcfb398833d0cdcea4e7e','2018-09-13 14:18:00','180913_Q_QC1F_01_01-201809131418.qcml',1,1,1),(35,'1cca68ec2242c15ae14f752e136e9b3d','2018-09-13 18:33:00','180913_Q_QC1F_01_02-201809131833.qcml',1,1,1),(36,'d87e036cf6f5b3300e0b048f3cac5dc0','2018-09-13 22:48:00','180913_Q_QC1F_01_03-201809132248.qcml',1,1,1),(37,'29d4968d22afd90715de48ae5905979f','2018-09-14 03:03:00','180913_Q_QC1F_01_04-201809140303.qcml',1,1,1),(38,'ba2b16984fa68361f367ff6c780f4b6a','2018-09-14 07:18:00','180913_Q_QC1F_01_05-201809140718.qcml',1,1,1),(39,'41a320a32d97bd8e5a36527debc47c13','2018-09-14 11:33:00','180913_Q_QC1F_01_06-201809141133.qcml',1,1,1),(40,'4c87504d75f8991540e56a6ba2f8ba2b','2018-09-14 15:48:00','180913_Q_QC1F_01_07-201809141548.qcml',1,1,1),(41,'b40c38a85c84ec2dc10fe02045a9918e','2018-09-14 20:03:00','180913_Q_QC1F_01_08-201809142003.qcml',1,1,1),(42,'ff01ca0e4ea53dd1bc5d24b0c16e90ba','2018-09-15 00:18:00','180913_Q_QC1F_01_09-201809150018.qcml',1,1,1),(43,'224d6d09e2ccc46496e3594b163dd27b','2018-09-15 04:33:00','180913_Q_QC1F_01_10-201809150433.qcml',1,1,1),(44,'7722919b45afd45148a94f40c20d5119','2018-09-15 08:48:00','180913_Q_QC1F_01_11-201809150848.qcml',1,1,1),(45,'3f3b988e3e8323480098730f4baab77f','2018-09-15 13:03:00','180913_Q_QC1F_01_12-201809151303.qcml',1,1,1),(46,'7115facded5f84ec6dda7d54271973bb','2018-09-15 16:23:00','180913_Q_QC1F_01_12b-201809151623.qcml',1,1,1),(47,'9d3e782877e625eabb13fd60d808823c','2018-09-15 20:38:00','180913_Q_QC1F_01_13-201809152038.qcml',1,1,1),(48,'672c26a6facebcfa1239d707434cd89c','2018-09-16 00:53:00','180913_Q_QC1F_01_14-201809160053.qcml',1,1,1),(49,'3199f12ea4bb2f6d54f0400e47e82f05','2018-09-16 05:08:00','180913_Q_QC1F_01_15-201809160508.qcml',1,1,1),(50,'b2482627b98844e57cb5d018fa0ea3ad','2018-09-16 09:23:00','180913_Q_QC1F_01_16-201809160923.qcml',1,1,1),(51,'bcc07636a90343e38049e1c5eec1ebaa','2018-09-16 13:38:00','180913_Q_QC1F_01_17-201809161338.qcml',1,1,1),(52,'7009fad84ba522c83309510941535b74','2018-09-16 17:54:00','180913_Q_QC1F_01_18-201809161754.qcml',1,1,1),(53,'79bcb331ed15bcf22576851fea23bebc','2018-09-16 22:08:00','180913_Q_QC1F_01_19-201809162208.qcml',1,1,1),(54,'f8a25a37b4df8f0f714c916a3278ea39','2018-09-17 02:22:00','180913_Q_QC1F_01_20-201809170222.qcml',1,1,1),(55,'4f895ea8c080124389dde1a17bb84817','2018-01-09 10:20:00','171223_Q_QC1X_01_16-201801091020.qcml',1,2,1),(56,'6934a0bc8bbbcc0943eda53e0fea3025','2018-01-09 10:54:00','180108_Q_QC1X_01_01-201801091054.qcml',1,2,1),(57,'f80738799a099104e96d411c17b9b2ab','2018-01-09 11:27:00','180108_Q_QC1X_01_02-201801091127.qcml',1,2,1),(58,'f357c8fc10f51c2bb57b4033f236724c','2018-01-09 12:00:00','180108_Q_QC1X_01_03-201801091200.qcml',1,2,1),(59,'784fe5b841068060ee003e375add209c','2018-01-09 15:15:00','180108_Q_QC1X_01_04-201801091515.qcml',1,2,1),(60,'a5d3e6c4e93ec08da163b4afbdcaaab8','2018-01-09 15:49:00','180108_Q_QC1X_01_04b-201801091549.qcml',1,2,1),(61,'a0a67291bc5e23518f0020bd8c0e148e','2018-01-09 16:26:00','180108_Q_QC1X_01_05-201801091626.qcml',1,2,1),(62,'a99b7d9d6123728382d1cc60694319d5','2018-01-09 16:59:00','180108_Q_QC1X_01_06-201801091659.qcml',1,2,1),(63,'f85b9cadf24d2b738597faac749d9db9','2018-01-10 05:45:00','171223_Q_QC1X_01_01-201801100545.qcml',1,2,1),(64,'a5579a6762fb301b8ba43e16d55a42d8','2018-01-10 18:32:00','171223_Q_QC1X_01_02-201801101832.qcml',1,2,1),(65,'ba9e463ba05e7f1f4044fa45a8112438','2018-01-11 07:18:00','171223_Q_QC1X_01_03-201801110718.qcml',1,2,1),(66,'6d35ddb8e057f4ca8073ee0bbe816ba5','2018-01-11 08:53:00','171223_Q_QC1X_01_03b-201801110853.qcml',1,2,1),(67,'bdb562e196f1ac59b2e276901f1030bc','2018-01-11 09:27:00','171223_Q_QC1X_01_03c-201801110927.qcml',1,2,1),(68,'1d65e1d31bb7e91dcec7d37bb4c22525','2018-01-11 10:00:00','171223_Q_QC1X_01_03d-201801111000.qcml',1,2,1),(69,'c56a1c289d69f9455d7f470f34768f81','2018-01-11 21:46:00','171223_Q_QC1X_01_04-201801112146.qcml',1,2,1),(70,'5242607cbc86bca68d10c7c213b77ad2','2018-01-12 10:32:00','171223_Q_QC1X_01_05-201801121032.qcml',1,2,1),(71,'35174b8bf4ab73b065b8dce21a1b0988','2018-01-12 23:19:00','171223_Q_QC1X_01_06-201801122319.qcml',1,2,1),(72,'2d560b37bf99137474bc627b8729ed41','2018-01-13 12:05:00','171223_Q_QC1X_01_07-201801131205.qcml',1,2,1),(73,'0b1f0f053791a3b60e33462fb85be69a','2018-01-14 00:52:00','171223_Q_QC1X_01_08-201801140052.qcml',1,2,1),(74,'292ba349c4d853566e419350abdf270e','2018-01-14 01:26:00','180112_Q_QC1X_01_01-201801140126.qcml',1,2,1),(75,'c58322d8ec4a9b516c484b4a0c47f32d','2018-01-14 01:59:00','180112_Q_QC1X_01_02-201801140159.qcml',1,2,1),(76,'95bb6ad94891c0d12f98f46b5dc2f360','2018-01-14 05:03:00','180112_Q_QC1X_01_03-201801140503.qcml',1,2,1),(77,'3da264cce7cf32d211f3d48b9e251bd3','2018-01-14 10:40:00','180112_Q_QC1X_01_04-201801141040.qcml',1,2,1),(78,'192031ee33cda8a659c317946830ff1d','2018-01-14 16:16:00','180112_Q_QC1X_01_05-201801141616.qcml',1,2,1),(79,'583045ac53ab19580eb127116efe3f62','2018-01-14 21:52:00','180112_Q_QC1X_01_06-201801142152.qcml',1,2,1),(80,'69e5c7dedeb544e1c4d84e409c09c7e6','2018-01-15 03:28:00','180112_Q_QC1X_01_07-201801150328.qcml',1,2,1),(81,'33815764493f47f431536562e909a171','2018-01-15 09:04:00','180112_Q_QC1X_01_08-201801150904.qcml',1,2,1),(82,'03b62014c39aeb11285f182be0df6c65','2018-01-15 11:45:00','180115_Q_QC1X_01_01-201801151145.qcml',1,2,1),(83,'0a19e416690d99214e19684bb5291527','2018-01-15 12:19:00','180115_Q_QC1X_01_02-201801151219.qcml',1,2,1),(84,'c32dec3cfe390bb540b60c5170d12482','2018-01-15 12:53:00','180115_Q_QC1X_01_03-201801151253.qcml',1,2,1),(85,'46e4fa1c346b3b9eb8b054e6f0f230a7','2018-01-15 15:57:00','180115_Q_QC1X_01_04-201801151557.qcml',1,2,1),(86,'599781f035bb8231ebeaa1a7098583c7','2018-01-15 18:57:00','180115_Q_QC1X_01_05-201801151857.qcml',1,2,1),(87,'9b530a82a20c18d21270b3d5b06e00f3','2018-01-16 06:10:00','180115_Q_QC1X_01_07-201801160610.qcml',1,2,1),(88,'2d0eddc7c45db7d531cdca81fe345b63','2018-01-16 14:37:00','180115_Q_QC1X_01_09-201801161437.qcml',1,2,1),(89,'f3dad2c4f0655ea326f1cc355c20a13b','2018-01-16 14:03:00','180115_Q_QC1X_01_08-201801161403.qcml',1,2,1),(90,'4172fbac522956dc4fcd81890831c079','2018-01-16 17:42:00','180116_Q_QC1X_01_01-201801161742.qcml',1,2,1),(91,'dbaff863e5f5166a2b19c03738e13d03','2018-01-16 20:42:00','180116_Q_QC1X_01_02-201801162042.qcml',1,2,1),(92,'a0a8ec56df2711048d91c4be442adec9','2018-01-17 02:19:00','180116_Q_QC1X_01_03-201801170219.qcml',1,2,1),(93,'835eda3947a4a42515e6f57f4bd52b31','2018-01-17 05:24:00','180116_Q_QC1X_01_04-201801170524.qcml',1,2,1),(94,'defdc9e0df1f9b8705d8dad80dd5996e','2018-01-17 08:29:00','180116_Q_QC1X_01_05-201801170829.qcml',1,2,1),(95,'9c6e0e4c1b7503303cb72ef77b7d9c03','2018-01-17 09:03:00','180116_Q_QC1X_01_06-201801170903.qcml',1,2,1),(96,'36254d0f878b703da82d7f5595b4ac07','2018-01-17 11:42:00','180117_Q_QC1X_01_01-201801171142.qcml',1,2,1),(97,'09d155d380e318160c4121679bec5b68','2018-01-17 12:16:00','180117_Q_QC1X_01_02-201801171216.qcml',1,2,1),(98,'f6b3132c9e9084d9f8c768de8dc72ad3','2018-01-17 12:49:00','180117_Q_QC1X_01_03-201801171249.qcml',1,2,1),(99,'3b75abec8afc0ea3ae25c9321fd5fe2c','2018-01-17 13:23:00','180117_Q_QC1X_01_04-201801171323.qcml',1,2,1),(100,'720438eaa2374e7b8086a0b4ceb44050','2018-01-17 16:01:00','180117_Q_QC1X_01_04_20180117053533-201801171601.qcml',1,2,1),(101,'be87c6db2e8324790bcf6c0a1f739231','2018-01-17 16:35:00','180117_Q_QC1X_01_05-201801171635.qcml',1,2,1),(102,'0449dae7da00b33dd80655ed1cb9c983','2018-01-17 22:12:00','180117_Q_QC1X_01_06-201801172212.qcml',1,2,1),(103,'c8364c8873f885075770aac13a272bca','2018-01-18 03:48:00','180117_Q_QC1X_01_07-201801180348.qcml',1,2,1),(104,'63e34fccf3f6a07a8a06c0db7c67da8b','2018-01-18 06:53:00','180117_Q_QC1X_01_08-201801180653.qcml',1,2,1),(105,'d76850e377018ba605765716f632125c','2018-01-18 12:30:00','180117_Q_QC1X_01_09-201801181230.qcml',1,2,1),(106,'da34dbfc49beef065640b1720105288e','2018-01-18 13:04:00','180117_Q_QC1X_01_10-201801181304.qcml',1,2,1),(107,'7095f6eca3881d9db29ae98e667ea312','2018-01-18 15:09:00','180118_Q_QC1X_01_01-201801181509.qcml',1,2,1),(108,'b157ed1889f94d6f6cb71ae571082894','2018-01-18 17:48:00','180118_Q_QC1X_01_02-201801181748.qcml',1,2,1),(109,'622574f454b23f034c7d16209ff2699e','2018-01-18 20:27:00','180118_Q_QC1X_01_03-201801182027.qcml',1,2,1),(110,'75e00cfe694ea1c2f1aab5710059b8ab','2018-01-18 21:34:00','180118_Q_QC1X_01_04-201801182134.qcml',1,2,1),(111,'db9d9d700277f2589cd4a2ee57176c29','2018-01-19 00:13:00','180118_Q_QC1X_01_05-201801190013.qcml',1,2,1),(112,'6849de323f9f45cf291b83c049b3f954','2018-01-19 02:51:00','180118_Q_QC1X_01_06-201801190251.qcml',1,2,1),(113,'8f766e6bb4d24fe32a8ae2672da88a58','2018-01-19 05:29:00','180118_Q_QC1X_01_07-201801190529.qcml',1,2,1),(114,'b654aa5f674e41db5687f023f5ff898e','2018-01-19 08:08:00','180118_Q_QC1X_01_08-201801190808.qcml',1,2,1),(115,'6ab26c26e77437c806c6b6f86a25ade5','2018-01-19 09:15:00','180118_Q_QC1X_01_09-201801190915.qcml',1,2,1),(116,'28605343120b7bb68f572f9ae4a5b491','2018-01-19 11:54:00','180118_Q_QC1X_01_10-201801191154.qcml',1,2,1),(117,'e1983b4506ba27ec9915ac9cc2efd612','2018-01-19 14:32:00','180118_Q_QC1X_01_11-201801191432.qcml',1,2,1),(118,'42d8027e856229344f3f155442bc57ea','2018-01-19 17:11:00','180118_Q_QC1X_01_12-201801191711.qcml',1,2,1),(119,'1039ed8c7355e1f6411fcc972665bf02','2018-01-19 17:45:00','180118_Q_QC1X_01_14-201801191745.qcml',1,2,1),(120,'00accd3ea217aed9d0e2145e93786dcd','2018-01-19 20:23:00','180118_Q_QC1X_01_15-201801192023.qcml',1,2,1),(121,'6d4108ddb5768650688f06db99dd07b1','2018-01-19 23:02:00','180118_Q_QC1X_01_16-201801192302.qcml',1,2,1),(122,'8b8da31934ac81bde3084ea773709ea4','2018-01-20 01:40:00','180118_Q_QC1X_01_17-201801200140.qcml',1,2,1),(123,'b7c949e05e702538b6fe9d96dfe53bd6','2018-01-20 04:19:00','180118_Q_QC1X_01_18-201801200419.qcml',1,2,1),(124,'99ded8fb4a109845e97f7cd978836708','2018-01-20 06:57:00','180118_Q_QC1X_01_19-201801200657.qcml',1,2,1),(125,'a7069ec52784724e81fed0a338752852','2018-01-20 07:31:00','180118_Q_QC1X_01_20-201801200731.qcml',1,2,1),(126,'0ab4bb6107e5c6dd12e0fbee5f6a3c70','2018-01-20 10:35:00','180119_Q_QC1X_01_01-201801201035.qcml',1,2,1),(127,'e099e83ab51cd4ac351878b481a6bb24','2018-01-20 13:36:00','180119_Q_QC1X_01_02-201801201336.qcml',1,2,1),(128,'1b2ed406e403a77f05360d439789c351','2018-01-20 19:12:00','180119_Q_QC1X_01_04-201801201912.qcml',1,2,1),(129,'516ae5dcf3b484c105c68dc4d4c781d5','2018-01-21 00:49:00','180119_Q_QC1X_01_05-201801210049.qcml',1,2,1),(130,'a6dd9a76f662ce630c705b760d8c3b30','2018-01-21 06:26:00','180119_Q_QC1X_01_06-201801210626.qcml',1,2,1),(131,'187b2d0b3739964c3b5cecd997f68376','2018-01-21 12:03:00','180119_Q_QC1X_01_07-201801211203.qcml',1,2,1),(132,'5e2221f1785d48e2a767cb51f53ba859','2018-01-21 17:40:00','180119_Q_QC1X_01_08-201801211740.qcml',1,2,1),(133,'4058d393212bf378a27bd79d3e4e03e0','2018-01-21 20:45:00','180119_Q_QC1X_01_09-201801212045.qcml',1,2,1),(134,'ba02bb7bab154734c77a2d44fa672a7f','2018-01-21 23:45:00','180119_Q_QC1X_01_10-201801212345.qcml',1,2,1),(135,'3a02a0dbddc7315c027a054d06615eec','2018-01-22 11:05:00','180122_Q_QC1X_01_01-201801221105.qcml',1,2,1),(136,'5a003481657456b7da6a7ba838fb4ab8','2018-01-22 11:39:00','180122_Q_QC1X_01_02-201801221139.qcml',1,2,1),(137,'ccc7ef46863a856731c5d552500d67df','2018-01-22 12:12:00','180122_Q_QC1X_01_03-201801221212.qcml',1,2,1),(138,'9e16faf2705a7353340ed3df2270d5bb','2018-01-22 12:45:00','180122_Q_QC1X_01_04-201801221245.qcml',1,2,1),(139,'1f2269732155b873b3032c1771fb7fdc','2018-01-22 13:19:00','180122_Q_QC1X_01_05-201801221319.qcml',1,2,1),(140,'c37041977e5cba8bce667ed5826c996e','2018-01-22 14:15:00','180122_Q_QC1X_01_06-201801221415.qcml',1,2,1),(141,'18783bb61352a362b1ef5c7153f69537','2018-01-22 17:19:00','180122_Q_QC1X_01_07-201801221719.qcml',1,2,1),(142,'b3a7a12216341a688e8b73b94001cf25','2018-01-22 20:19:00','180122_Q_QC1X_01_08-201801222019.qcml',1,2,1),(143,'03d29978fd439ff2021668645b9ab1a0','2018-01-23 01:55:00','180122_Q_QC1X_01_09-201801230155.qcml',1,2,1),(144,'c0fb367b5090d02e46ff60ee4ec4785f','2018-01-23 07:31:00','180122_Q_QC1X_01_10-201801230731.qcml',1,2,1),(145,'eb0273a755c938b0d4f236e35c9749f6','2018-01-23 13:42:00','180122_Q_QC1X_01_12-201801231342.qcml',1,2,1),(146,'f4f8a670a3646a3ee54d9e08f0803862','2018-01-23 13:08:00','180122_Q_QC1X_01_11-201801231308.qcml',1,2,1),(147,'26f284bebbadd7347e28d54225491738','2018-01-23 16:20:00','180123_Q_QC1X_01_01-201801231620.qcml',1,2,1),(148,'a221b9e7c0e1eeb021b0108e2faf7d2d','2018-01-23 18:58:00','180123_Q_QC1X_01_02-201801231858.qcml',1,2,1),(149,'9843993dc24b15cc4acfd995d2740135','2018-08-09 02:08:00','180809_Q_QC1X_01_13-201808090208.qcml',1,2,1),(150,'3ea557bda89e6941d83c70f8be889bfe','2018-08-09 04:59:00','180809_Q_QC1X_01_14-201808090459.qcml',1,2,1),(151,'2bab9ea3a02ae85a029a3747d3314549','2018-08-09 07:51:00','180809_Q_QC1X_01_15-201808090751.qcml',1,2,1),(152,'1b471078d2c281f4bc615961bb627e5a','2018-08-09 10:44:00','180809_Q_QC1X_01_16-201808091044.qcml',1,2,1),(153,'b62d85deeb00fb96d7488f5380983535','2018-08-09 13:37:00','180809_Q_QC1X_01_17-201808091337.qcml',1,2,1),(154,'1cf12d4f1a7e95583d11d190a263842e','2018-08-09 16:30:00','180809_Q_QC1X_01_18-201808091630.qcml',1,2,1),(155,'f8ae1d13b2e9d9f3b1e57f4e9ff7cdc8','2018-08-09 19:23:00','180809_Q_QC1X_01_19-201808091923.qcml',1,2,1),(156,'a68a0b14b36dd56b9cb27949b2bf9d08','2018-08-09 22:17:00','180809_Q_QC1X_01_20-201808092217.qcml',1,2,1),(157,'804fc70c100398990a383ba920445d8b','2018-08-10 01:11:00','180809_Q_QC1X_01_21-201808100111.qcml',1,2,1),(158,'8383e2e3fd0eca37d409f313893fa983','2018-08-10 04:05:00','180809_Q_QC1X_01_22-201808100405.qcml',1,2,1),(159,'3fa62f146651d25d840e408cc8fac09a','2018-08-10 07:00:00','180809_Q_QC1X_01_23-201808100700.qcml',1,2,1),(160,'e0ab5dbe23398840cdb31bd56b088485','2018-08-10 09:54:00','180809_Q_QC1X_01_24-201808100954.qcml',1,2,1),(161,'c6eba867f2fa5dabbabb9b8f2ac0a37d','2018-08-10 13:10:00','180810_Q_QC1X_01_01-201808101310.qcml',1,2,1),(162,'9feec0a4e2f99ffec22d860e042c3752','2018-08-10 16:14:00','180810_Q_QC1X_01_02-201808101614.qcml',1,2,1),(163,'86ef1cfeb5c9e017324600631fb7a780','2018-08-10 19:09:00','180810_Q_QC1X_01_01_20180810094905-201808101909.qcml',1,2,1),(164,'5dd9991e087ce28388ddaf11e4e3e809','2018-08-10 22:04:00','180810_Q_QC1X_01_02_20180811124406-201808102204.qcml',1,2,1),(165,'0c9a0379a9da084185082556085fb5e3','2018-08-10 23:22:00','180810_Q_QC1X_01_03-201808102322.qcml',1,2,1),(166,'0a6572240c6fe5ccbbbb958685235aae','2018-08-11 02:17:00','180810_Q_QC1X_01_04-201808110217.qcml',1,2,1),(167,'9dea68c888282ce8d934ad2e59db877f','2018-08-11 05:14:00','180810_Q_QC1X_01_05-201808110514.qcml',1,2,1),(168,'2d7c2d31bef9ab1c4aacb688517caa6b','2018-08-11 08:08:00','180810_Q_QC1X_01_06-201808110808.qcml',1,2,1),(169,'4123ccf43bed1ec536aec7da0a43c598','2018-08-11 11:02:00','180810_Q_QC1X_01_07-201808111102.qcml',1,2,1),(170,'6b8e0aa1bcd77a888dee25be690a78a1','2018-08-11 13:57:00','180810_Q_QC1X_01_08-201808111357.qcml',1,2,1),(171,'3598f22aff07a9e2104ba9a82763e0b3','2018-08-11 16:50:00','180810_Q_QC1X_01_09-201808111650.qcml',1,2,1),(172,'f8bd9f58c9c7691b9e4bb8b5c5802883','2018-08-11 19:45:00','180810_Q_QC1X_01_10-201808111945.qcml',1,2,1),(173,'b68acd67d7d1d09ddbb44e27f102cc38','2018-08-11 23:00:00','180810_Q_QC1X_01_11-201808112300.qcml',1,2,1),(174,'f93d3554495594cdb689f4521fd14257','2018-08-12 02:05:00','180810_Q_QC1X_01_12-201808120205.qcml',1,2,1),(175,'ccc53439316020fa8ed2cb8dc747af0b','2018-08-12 02:43:00','180810_Q_QC1X_01_13-201808120243.qcml',1,2,1),(176,'75c26d5f402d465bb2d7c4192fbd5fc3','2018-08-12 08:34:00','180810_Q_QC1X_01_14-201808120834.qcml',1,2,1),(177,'d246f4e55200b7e3bb91273e13ef51d9','2018-08-12 14:25:00','180810_Q_QC1X_01_15-201808121425.qcml',1,2,1),(178,'b1351ac0546cdb1c1cb696aa1808d103','2018-08-12 20:16:00','180810_Q_QC1X_01_16-201808122016.qcml',1,2,1),(179,'8740943c45c91ea7568578d87653430b','2018-08-13 02:07:00','180810_Q_QC1X_01_17-201808130207.qcml',1,2,1),(180,'0aeb6f7b3639a93575511902908a003b','2018-08-13 07:58:00','180810_Q_QC1X_01_18-201808130758.qcml',1,2,1);
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `file_seq`
--

LOCK TABLES `file_seq` WRITE;
/*!40000 ALTER TABLE `file_seq` DISABLE KEYS */;
INSERT INTO `file_seq` VALUES (181);
/*!40000 ALTER TABLE `file_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `guide_set`
--

LOCK TABLES `guide_set` WRITE;
/*!40000 ALTER TABLE `guide_set` DISABLE KEYS */;
INSERT INTO `guide_set` VALUES ('automatic',1,_binary 'ùùù[ùBùùùS{|]z','2018-08-13 02:07:00',1,'2018-08-11 13:57:00',10,1),('manual',2,_binary 'ù-zn[ùF?ùNùùù\0','2018-09-10 23:59:00',1,'2018-09-03 00:00:00',NULL,1);
/*!40000 ALTER TABLE `guide_set` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `guide_set_seq`
--

LOCK TABLES `guide_set_seq` WRITE;
/*!40000 ALTER TABLE `guide_set_seq` DISABLE KEYS */;
INSERT INTO `guide_set_seq` VALUES (3);
/*!40000 ALTER TABLE `guide_set_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `labsystem`
--

LOCK TABLES `labsystem` WRITE;
/*!40000 ALTER TABLE `labsystem` DISABLE KEYS */;
INSERT INTO `labsystem` VALUES (1,_binary 'ùùùùùO\Zù\'ù/v5ù','Lumos'),(2,_binary 'ùCùù:Gùù*%eùrù','XL'),(3,_binary '%?ù( ?Feùùùù?\0\0','Velos');
/*!40000 ALTER TABLE `labsystem` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `labsystem_data_sources`
--

LOCK TABLES `labsystem_data_sources` WRITE;
/*!40000 ALTER TABLE `labsystem_data_sources` DISABLE KEYS */;
INSERT INTO `labsystem_data_sources` VALUES (1,2),(1,3),(2,4),(2,7),(3,5),(3,6);
/*!40000 ALTER TABLE `labsystem_data_sources` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `labsystem_guide_sets`
--

LOCK TABLES `labsystem_guide_sets` WRITE;
/*!40000 ALTER TABLE `labsystem_guide_sets` DISABLE KEYS */;
INSERT INTO `labsystem_guide_sets` VALUES (1,2);
/*!40000 ALTER TABLE `labsystem_guide_sets` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `labsystem_seq`
--

LOCK TABLES `labsystem_seq` WRITE;
/*!40000 ALTER TABLE `labsystem_seq` DISABLE KEYS */;
INSERT INTO `labsystem_seq` VALUES (4);
/*!40000 ALTER TABLE `labsystem_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
  `country` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_obvbgg44871hprrdukj0ev7is` (`api_key`),
  UNIQUE KEY `UK_fwigxdmj6bsrpcmhcgpmlsirh` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node`
--

LOCK TABLES `node` WRITE;
/*!40000 ALTER TABLE `node` DISABLE KEYS */;
INSERT INTO `node` VALUES (1,_binary '$ùùjsùK?:?9Xù\0\0\0','CRG',NULL),(8,_binary 'ù5ù{ùlJùùfùùù^','EMBL',NULL),(9,_binary 'c\n9ùBùù$ùRù','EMbiel','Germany');
/*!40000 ALTER TABLE `node` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `node_seq`
--

LOCK TABLES `node_seq` WRITE;
/*!40000 ALTER TABLE `node_seq` DISABLE KEYS */;
INSERT INTO `node_seq` VALUES (10),(10);
/*!40000 ALTER TABLE `node_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `param`
--

LOCK TABLES `param` WRITE;
/*!40000 ALTER TABLE `param` DISABLE KEYS */;
INSERT INTO `param` VALUES (1,'Peptide',1,'Peak area',1,'QC:1001844'),(2,'Peptide',0,'Mass accuracy',3,'QC:1000014'),(3,'InstrumentSample',0,'Median IT',3,'QC:9000002'),(4,'InstrumentSample',0,'Total numbers',3,'QC:9000001'),(9,'Peptide',1,'Retention time',0,'QC:1000894');
/*!40000 ALTER TABLE `param` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `param_seq`
--

LOCK TABLES `param_seq` WRITE;
/*!40000 ALTER TABLE `param_seq` DISABLE KEYS */;
INSERT INTO `param_seq` VALUES (10);
/*!40000 ALTER TABLE `param_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset`
--

DROP TABLE IF EXISTS `password_reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password_reset` (
  `id` bigint(20) NOT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `number_of_requests` int(11) DEFAULT NULL,
  `token` binary(16) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8bojqfhoasv6keiidk8xs4ux1` (`token`),
  KEY `FK3rcc5avyc21uriav34cjrqc91` (`user_id`),
  CONSTRAINT `FK3rcc5avyc21uriav34cjrqc91` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset`
--

LOCK TABLES `password_reset` WRITE;
/*!40000 ALTER TABLE `password_reset` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset_seq`
--

DROP TABLE IF EXISTS `password_reset_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password_reset_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset_seq`
--

LOCK TABLES `password_reset_seq` WRITE;
/*!40000 ALTER TABLE `password_reset_seq` DISABLE KEYS */;
INSERT INTO `password_reset_seq` VALUES (10);
/*!40000 ALTER TABLE `password_reset_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sample_category_seq`
--

LOCK TABLES `sample_category_seq` WRITE;
/*!40000 ALTER TABLE `sample_category_seq` DISABLE KEYS */;
INSERT INTO `sample_category_seq` VALUES (4);
/*!40000 ALTER TABLE `sample_category_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sample_composition`
--

LOCK TABLES `sample_composition` WRITE;
/*!40000 ALTER TABLE `sample_composition` DISABLE KEYS */;
INSERT INTO `sample_composition` VALUES (1,1,NULL),(2,1,NULL),(3,1,NULL),(4,1,NULL),(5,1,NULL),(6,1,NULL),(7,1,NULL),(8,1,NULL),(9,1,NULL),(10,1,NULL),(17,4,NULL),(18,4,NULL),(19,4,NULL),(20,4,NULL),(21,4,NULL),(22,4,NULL),(23,4,NULL),(24,4,NULL),(25,4,NULL),(26,4,NULL),(27,4,NULL),(28,4,NULL),(29,4,NULL),(30,4,NULL),(31,4,NULL),(32,5,100),(33,5,10),(34,5,1),(35,5,0.1),(36,5,0.01),(37,5,100),(38,5,10),(39,5,1),(40,5,0.1),(41,5,0.01),(42,5,100),(43,5,10),(44,5,1),(45,5,0.1),(46,5,0.01),(47,5,100),(48,5,10),(49,5,1),(50,5,0.1),(51,5,0.01),(52,5,100),(53,5,10),(54,5,1),(55,5,0.1),(56,5,0.01),(57,5,100),(58,5,10),(59,5,1),(60,5,0.1),(61,5,0.01);
/*!40000 ALTER TABLE `sample_composition` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sample_seq`
--

LOCK TABLES `sample_seq` WRITE;
/*!40000 ALTER TABLE `sample_seq` DISABLE KEYS */;
INSERT INTO `sample_seq` VALUES (6);
/*!40000 ALTER TABLE `sample_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sample_type`
--

LOCK TABLES `sample_type` WRITE;
/*!40000 ALTER TABLE `sample_type` DISABLE KEYS */;
INSERT INTO `sample_type` VALUES (1,_binary '','bsa','QC:0000005',1),(4,_binary '','hela','QC:0000006',2),(5,_binary '','qc4l','QC:0000009',3);
/*!40000 ALTER TABLE `sample_type` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `sample_type_category`
--

LOCK TABLES `sample_type_category` WRITE;
/*!40000 ALTER TABLE `sample_type_category` DISABLE KEYS */;
INSERT INTO `sample_type_category` VALUES (1,_binary 'ùùù+ù\rK9ù\'ù?ù\0\0','QC1',0),(2,_binary 'UùùTMMùù]ù	#ùù','QC2',1),(3,_binary 'ùV;W?J?2ù/ùHù\0\0\0','QC3',2);
/*!40000 ALTER TABLE `sample_type_category` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `threshold`
--

LOCK TABLES `threshold` WRITE;
/*!40000 ALTER TABLE `threshold` DISABLE KEYS */;
INSERT INTO `threshold` VALUES ('hard_limit',1,_binary '=ùùùùùG?8aaùù\0',1,1,NULL,1,75,NULL,2,1),('sigmalog2',2,_binary 'Tù\0eùvH?4ùjù\Zù\0',1,1,NULL,3,75,NULL,1,1),('sigma',3,_binary 'uCVù6M%ùiùù?K\0',1,1,NULL,3,75,NULL,3,1),('hard_limit',4,_binary 'ùù>ùWAùùùui/vù\Z',0,1,NULL,1,75,1,2,1),('sigmalog2',5,_binary 'ù^ùynùHFù$ù*ù!ù',1,1,NULL,3,75,1,1,1),('sigma',6,_binary 'ùùAx\rHùùKùùù;ùX',1,1,NULL,3,75,1,3,1),('hard_limit',7,_binary 'ùRù# Coùù.@Rùù',1,1,NULL,1,48,NULL,2,1),('hard_limit',8,_binary 'ùùnùOUC&ùùù}Tùg',0,1,NULL,1,75,1,2,1),('hard_limit',9,_binary '?J{^JSùùu?ùù\0\0\0',1,1,NULL,1,75,1,2,1),('hard_limit',10,_binary 'WBΩÖ˛ıJZÇÙ\∆\Z<\¬p§',1,1,NULL,1,48,2,2,1);
/*!40000 ALTER TABLE `threshold` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `threshold_non_conformity`
--

LOCK TABLES `threshold_non_conformity` WRITE;
/*!40000 ALTER TABLE `threshold_non_conformity` DISABLE KEYS */;
INSERT INTO `threshold_non_conformity` VALUES (1,'DANGER',1,19,NULL,5),(4,'DANGER',9,19,NULL,5),(6,'DANGER',3,19,NULL,5),(7,'DANGER',6,19,NULL,5),(10,'DANGER',10,20,NULL,5),(11,'DANGER',1,20,NULL,5),(12,'DANGER',4,20,NULL,5),(13,'DANGER',2,20,NULL,5),(14,'DANGER',9,20,NULL,5),(15,'DANGER',3,20,NULL,5),(16,'DANGER',7,20,NULL,5),(17,'DANGER',6,20,NULL,5),(18,'DANGER',8,20,NULL,5),(19,'DANGER',4,20,NULL,4),(20,'DANGER',2,20,NULL,4),(23,'DANGER',1,21,NULL,5),(25,'DANGER',7,21,NULL,5),(26,'DANGER',3,21,NULL,5),(27,'DANGER',4,21,NULL,4),(28,'DANGER',2,21,NULL,4),(29,'DANGER',1,21,NULL,4),(40,'DANGER',8,41,NULL,5),(43,'WARNING',8,54,NULL,5);
/*!40000 ALTER TABLE `threshold_non_conformity` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `threshold_non_conformity_seq`
--

LOCK TABLES `threshold_non_conformity_seq` WRITE;
/*!40000 ALTER TABLE `threshold_non_conformity_seq` DISABLE KEYS */;
INSERT INTO `threshold_non_conformity_seq` VALUES (44);
/*!40000 ALTER TABLE `threshold_non_conformity_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `threshold_params`
--

LOCK TABLES `threshold_params` WRITE;
/*!40000 ALTER TABLE `threshold_params` DISABLE KEYS */;
INSERT INTO `threshold_params` VALUES (1,1,0,1,2),(1,2,0,1,0),(1,4,0,1,2),(1,5,28.4034,1,0.151413),(1,7,0,1,2),(1,8,0,1,5),(1,9,1,1,5),(1,10,0,1,2),(2,1,0,1,2),(2,2,0,1,0),(2,4,0,1,2),(2,5,28.1108,1,0.178521),(2,7,0,1,2),(2,8,0,1,5),(2,9,1,1,5),(2,10,0,1,2),(3,1,0,1,2),(3,2,0,1,0),(3,4,0,1,2),(3,5,26.3685,1,0.114869),(3,7,0,1,2),(3,8,0,1,5),(3,9,1,1,5),(3,10,0,1,2),(4,1,0,1,2),(4,2,0,1,0),(4,4,0,1,2),(4,5,27.1844,1,0.0969015),(4,7,0,1,2),(4,8,0,1,5),(4,9,1,1,5),(4,10,0,1,2),(5,1,0,1,2),(5,2,0,1,0),(5,4,0,1,2),(5,5,22.4595,1,0.190705),(5,7,0,1,2),(5,8,0,1,5),(5,9,1,1,5),(5,10,0,1,2),(6,1,0,1,2),(6,2,0,1,0),(6,4,0,1,2),(6,5,24.6902,0,0.173586),(6,7,0,1,2),(6,8,0,1,5),(6,9,1,1,5),(6,10,0,1,2),(7,1,0,1,2),(7,2,0,1,0),(7,4,0,1,2),(7,5,23.4529,1,0.203465),(7,7,0,1,2),(7,8,0,1,5),(7,9,1,1,5),(7,10,0,1,2),(8,1,0,1,2),(8,2,0,1,0),(8,4,0,1,2),(8,5,23.0387,1,0.146107),(8,7,0,1,2),(8,8,0,1,5),(8,9,1,1,5),(8,10,0,1,2),(9,1,0,1,2),(9,2,0,1,0),(9,4,0,1,2),(9,5,27.6809,1,0.136951),(9,7,0,1,2),(9,8,0,1,5),(9,9,1,1,5),(9,10,0,1,2),(10,1,0,1,2),(10,2,0,1,0),(10,4,0,1,2),(10,5,27.6561,1,0.0422371),(10,7,0,1,2),(10,8,0,1,5),(10,9,1,1,5),(10,10,0,1,2),(11,3,0,1,0),(11,6,0,1,0),(12,3,0,1,0),(12,6,0,0,0);
/*!40000 ALTER TABLE `threshold_params` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `threshold_seq`
--

LOCK TABLES `threshold_seq` WRITE;
/*!40000 ALTER TABLE `threshold_seq` DISABLE KEYS */;
INSERT INTO `threshold_seq` VALUES (11);
/*!40000 ALTER TABLE `threshold_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary 'LùGùùE?ùùùù$ù\0\0','daniel.mancera@crg.eu',_binary '','Daniel','2018-09-27 10:14:00.366000','Mancera','$2a$10$wSusaymX0lMIdQ16faI93u1t5RtvjmoreLpiw1vfyuZ/YxeKf7YkW','daniel.mancera@crg.eu',1,4),(7,_binary 'ù2E3`Kzù*ùù6Yù@','dmance@edpac.org',_binary '','Danie','2018-10-02 13:10:36.595000','Daniel','$2a$10$L1rXTuxLidUXH9T65raJRuePIDT7Hqcx3NbgQKuizJqvdX6jHQDVi','dmance@edpac.org',8,NULL),(8,_binary 'ùùù5@|ù?ù}ùù\0\0','mister@mister.om',_binary '','se?orrr','2018-10-03 12:04:13.326000','s?orrr','$2a$10$oZCzS0EVHOcfVQiK3Dn0ou0adbXD.roaSNwvoohyf5AACkijiO2Pe','mister@mister.om',9,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (1,1),(1,2),(1,3),(7,1),(7,2),(8,1),(8,2);
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `user_default_view`
--

LOCK TABLES `user_default_view` WRITE;
/*!40000 ALTER TABLE `user_default_view` DISABLE KEYS */;
INSERT INTO `user_default_view` VALUES (4,'INSTRUMENT',1,NULL);
/*!40000 ALTER TABLE `user_default_view` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `user_default_view_seq`
--

LOCK TABLES `user_default_view_seq` WRITE;
/*!40000 ALTER TABLE `user_default_view_seq` DISABLE KEYS */;
INSERT INTO `user_default_view_seq` VALUES (5);
/*!40000 ALTER TABLE `user_default_view_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (9);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
INSERT INTO `view` VALUES (1,_binary '_Q?KùOùùWùO7^',1,'QC1',75,1,NULL),(2,_binary 'ùl>?ùC7ùù!ùùùS6\0',1,'QC2',75,2,NULL);
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `view_display`
--

LOCK TABLES `view_display` WRITE;
/*!40000 ALTER TABLE `view_display` DISABLE KEYS */;
INSERT INTO `view_display` VALUES ('default_view',11,0,0,1,1,NULL),('default_view',12,0,1,2,1,NULL),('default_view',13,1,1,3,1,NULL),('default_view',14,0,2,5,1,NULL),('default_view',15,0,0,6,2,NULL);
/*!40000 ALTER TABLE `view_display` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Dumping data for table `view_display_seq`
--

LOCK TABLES `view_display_seq` WRITE;
/*!40000 ALTER TABLE `view_display_seq` DISABLE KEYS */;
INSERT INTO `view_display_seq` VALUES (16);
/*!40000 ALTER TABLE `view_display_seq` ENABLE KEYS */;
UNLOCK TABLES;

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

--
-- Dumping data for table `view_seq`
--

LOCK TABLES `view_seq` WRITE;
/*!40000 ALTER TABLE `view_seq` DISABLE KEYS */;
INSERT INTO `view_seq` VALUES (3);
/*!40000 ALTER TABLE `view_seq` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-10-04 11:25:33
