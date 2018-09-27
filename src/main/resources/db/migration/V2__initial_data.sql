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
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ROLE_USER'),(2,'ROLE_MANAGER'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `authority_seq`
--

LOCK TABLES `authority_seq` WRITE;
/*!40000 ALTER TABLE `authority_seq` DISABLE KEYS */;
INSERT INTO `authority_seq` VALUES (1);
/*!40000 ALTER TABLE `authority_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,_binary '!Y≥HZ\‚Df∫\œ\‘GÃ¨',1,'Mass spectrometer'),(2,_binary '@%ç_\Ã(H∫¨}}Xw!',0,'Liquid chromatographer');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `category_seq`
--

LOCK TABLES `category_seq` WRITE;
/*!40000 ALTER TABLE `category_seq` DISABLE KEYS */;
INSERT INTO `category_seq` VALUES (3);
/*!40000 ALTER TABLE `category_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chart`
--

LOCK TABLES `chart` WRITE;
/*!40000 ALTER TABLE `chart` DISABLE KEYS */;
/*!40000 ALTER TABLE `chart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chart_params`
--

LOCK TABLES `chart_params` WRITE;
/*!40000 ALTER TABLE `chart_params` DISABLE KEYS */;
/*!40000 ALTER TABLE `chart_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `chart_seq`
--

LOCK TABLES `chart_seq` WRITE;
/*!40000 ALTER TABLE `chart_seq` DISABLE KEYS */;
INSERT INTO `chart_seq` VALUES (1);
/*!40000 ALTER TABLE `chart_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `context_source`
--

LOCK TABLES `context_source` WRITE;
/*!40000 ALTER TABLE `context_source` DISABLE KEYS */;
INSERT INTO `context_source` VALUES ('peptide',1,'LVN',_binary '\Á:‹é	/Jjû\€\‘L\‡Zr','LVNELTEFAK',NULL,2,582.319,'LVNELTEFAK'),('peptide',2,'HLV',_binary 'é–∞Ø*êE∏àä\‰_\‹J\√Ú','HLVDEPQNLIK',NULL,2,653.362,'HLVDEPQNLIK'),('peptide',3,'VPQ',_binary '•∏]3πàLö^|\ﬁrd¨ˆ','VPQVSTPTLVEVSR',NULL,2,756.425,'VPQVSTPTLVEVSR'),('peptide',4,'EAC',_binary '\Á2Ç`\“N\Ïö•4ê3','EAC(Carbamidomethyl)FAVEGPK',NULL,2,554.261,'EAC(Carbamidomethyl)FAVEGPK'),('peptide',5,'EYE',_binary 'Ò¢K´®0FŸô\Ì\·êuñ','EYEATLEEC(Carbamidomethyl)C(Carbamidomethyl)AK',NULL,2,751.811,'EYEATLEEC(Carbamidomethyl)C(Carbamidomethyl)AK'),('peptide',6,'ECC',_binary '…π4v\«HMÜäÜ\‘W–™k\À','EC(Carbamidomethyl)C(Carbamidomethyl)HGDLLEC(Carbamidomethyl)ADDR',NULL,3,583.892,'EC(Carbamidomethyl)C(Carbamidomethyl)HGDLLEC(Carbamidomethyl)ADDR'),('peptide',7,'SLH',_binary '\'¸≠∑\ -J¥ã§\'X:v˘ü','SLHTLFGDELC(Carbamidomethyl)K',NULL,2,710.35,'SLHTLFGDELC(Carbamidomethyl)K'),('peptide',8,'TCV',_binary 'y+≥Æ±%@a©í\ÏÆa˛\Ï,','TC(Carbamidomethyl)VADESHAGC(Carbamidomethyl)EK',NULL,3,488.534,'TC(Carbamidomethyl)VADESHAGC(Carbamidomethyl)EK'),('peptide',9,'YIC',_binary 'û`\—!\ÕMñ>íQ=3ìU','YIC(Carbamidomethyl)DNQDTISSK',NULL,2,722.325,'YIC(Carbamidomethyl)DNQDTISSK'),('peptide',10,'NEC',_binary 'ï¨üµH2ñ_˜^Ñ)\\?','NEC(Carbamidomethyl)FLSHK',NULL,2,517.74,'NEC(Carbamidomethyl)FLSHK'),('instrument_sample',11,'miit ms1',_binary '˛\¬\«Ò\≈DÉÅh\◊\¬rQÅ','Median IT MS1','QC:1000927',NULL,NULL,NULL),('instrument_sample',12,'Median IT MS2',_binary '¨\'#	\Â@\0õ¢ˇwY\”\Õ','Median IT MS2','QC:1000928',NULL,NULL,NULL),('instrument_sample',13,'# proteins',_binary 'ò[6Q£F6°j\Â/\‡O5','Total number of identified proteins','QC:0000032',NULL,NULL,NULL),('instrument_sample',14,'# peptides',_binary '	x\ƒ/[\ÕC3°ª\’;\ﬂV','Total number of uniquely identified peptides','QC:0000031',NULL,NULL,NULL),('instrument_sample',15,'MS2 spectral count',_binary 'x8\Ã\Ï°Fv≠nelòÑﬁå','MS2 Spectral count','QC:0000007',NULL,NULL,NULL),('instrument_sample',16,'# psm',_binary 'äß\Ã&#GÃÇ#B óå≈Ä','Total number of PSM','QC:0000029',NULL,NULL,NULL);
/*!40000 ALTER TABLE `context_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cv`
--

LOCK TABLES `cv` WRITE;
/*!40000 ALTER TABLE `cv` DISABLE KEYS */;
INSERT INTO `cv` VALUES (1,'Thermo Scientific second generation Velos. [PSI:MS]',0,'Velos Plus','MS:1001909',1),(2,'Thermo Scientific ISQ single quadrupole MS with the ExtractraBrite source. [PSI:MS]',0,'ISQ','MS:1001908',1),(3,'Thermo Scientific Exactive MS. [PSI:MS]',0,'Exactive','MS:1000649',1),(4,'Thermo Scientific GC IsoLink Isotope Ratio MS. [PSI:MS]',0,'GC IsoLink','MS:1000648',1),(5,'Thermo Scientific MALDI LTQ Orbitrap MS. [PSI:MS]',0,'MALDI LTQ Orbitrap','MS:1000643',1),(6,'Thermo Scientific MALDI LTQ XL MS. [PSI:MS]',0,'MALDI LTQ XL','MS:1000642',1),(7,'Thermo Scientific DSQ II GC-MS. [PSI:MS]',0,'DSQ II','MS:1000641',1),(8,'Thermo Scientific DFS HR GC-MS. [PSI:MS]',0,'DFS','MS:1000640',1),(9,'Thermo Scientific Element GD Glow Discharge MS. [PSI:MS]',0,'Element GD','MS:1000647',1),(10,'Thermo Scientific Element 2 HR-ICP-MS. [PSI:MS]',0,'Element 2','MS:1000646',1),(11,'Thermo Scientific Element XR HR-ICP-MS. [PSI:MS]',0,'Element XR','MS:1000645',1),(12,'Thermo Scientific TSQ Quantum Access MS. [PSI:MS]',0,'TSQ Quantum Access','MS:1000644',1),(13,'Finnigan LXQ MS. [PSI:MS]',0,'LXQ','MS:1000450',1),(14,'Finnigan LTQ Orbitrap Velos MS. [PSI:MS]',0,'LTQ Orbitrap Velos','MS:1001742',1),(15,'Thermo Scientific Q Exactive Plus. [PSI:PI]',0,'Q Exactive Plus','MS:1002634',1),(16,'Thermo Scientific TSQ Quantiva MS. [PSI:PI]',0,'TSQ Quantiva','MS:1002418',1),(17,'Thermo Scientific TSQ Endura MS. [PSI:PI]',0,'TSQ Endura','MS:1002419',1),(18,'OBSOLETE SCIEX or Applied Biosystems|MDS SCIEX QTRAP 4000. [PSI:MS]',0,'4000 QTRAP','MS:1000870',1),(19,'Thermo Scientific Orbitrap Fusion. [PSI:PI]',0,'Orbitrap Fusion','MS:1002416',1),(20,'Thermo Scientific Orbitrap Fusion with ETD. [PSI:PI]',0,'Orbitrap Fusion ETD','MS:1002417',1),(21,'SCIEX Triple Quad 6500+. [PSI:MS]',0,'Triple Quad 6500+','MS:1002595',1),(22,'Thermo Scientific LTQ XL MS with ETD. [PSI:MS]',0,'LTQ XL ETD','MS:1000638',1),(23,'Thermo Scientific LTQ Orbitrap XL MS with ETD. [PSI:MS]',0,'LTQ Orbitrap XL ETD','MS:1000639',1),(24,'Thermo Scientific ITQ 900 GC-MS. [PSI:MS]',0,'ITQ 900','MS:1000636',1),(25,'Thermo Scientific ITQ 1100 GC-MS. [PSI:MS]',0,'ITQ 1100','MS:1000637',1),(26,'Thermo Scientific ITQ 700 GC-MS. [PSI:MS]',0,'ITQ 700','MS:1000635',1),(27,'Finnigan LTQ Orbitrap MS. [PSI:MS]',0,'LTQ Orbitrap','MS:1000449',1),(28,'Finnigan LTQ FT MS. [PSI:MS]',0,'LTQ FT','MS:1000448',1),(29,'Finnigan LTQ MS. [PSI:MS]',0,'LTQ','MS:1000447',1),(30,'SCIEX Triple Quad 4500. [PSI:MS]',0,'Triple Quad 4500','MS:1002592',1),(32,'TSQ Vantage. [PSI:MS]',0,'TSQ Vantage','MS:1001510',1),(33,'Applied Biosystems/MDS SCIEX Proteomics Solution 1 MS. [PSI:MS]',0,'proteomics solution 1','MS:1000186',1),(34,'Applied Biosystems/MDS SCIEX Q TRAP MS. [PSI:MS]',0,'Q TRAP','MS:1000187',1),(35,'Accela PDA. [PSI:MS]',0,'Accela PDA','MS:1000623',1),(36,'Surveyor PDA. [PSI:MS]',0,'Surveyor PDA','MS:1000622',1),(37,'Thermo Scientific TSQ Quantum Ultra AM. [PSI:MS]',0,'TSQ Quantum Ultra AM','MS:1000743',1),(38,'SCIEX X500R QTOF, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',0,'X500R QTOF','MS:1002674',1),(39,'Applied Biosystems/MDS SCIEX SymBiot XVI MS. [PSI:MS]',0,'SymBiot XVI','MS:1000195',1),(40,'Applied Biosystems/MDS SCIEX SymBiot I MS. [PSI:MS]',0,'SymBiot I','MS:1000194',1),(41,'SCIEX 2000 QTRAP. [PSI:MS]',0,'2000 QTRAP','MS:1002577',1),(42,'Applied Biosystems/MDS SCIEX QSTAR MS. [PSI:MS]',0,'QSTAR','MS:1000190',1),(43,'SCIEX 3500 QTRAP. [PSI:MS]',0,'3500 QTRAP','MS:1002579',1),(44,'SCIEX 2500 QTRAP. [PSI:MS]',0,'2500 QTRAP','MS:1002578',1),(45,'Thermo Scientific TSQ Quantum Ultra. [PSI:MS]',0,'TSQ Quantum Ultra','MS:1000751',1),(46,'Thermo Fisher Scientific LTQ Orbitrap Classic. [PSI:MS]',0,'LTQ Orbitrap Classic','MS:1002835',1),(47,'LTQ FT Ultra. [PSI:MS]',0,'LTQ FT Ultra','MS:1000557',1),(48,'LTQ Orbitrap XL. [PSI:MS]',0,'LTQ Orbitrap XL','MS:1000556',1),(49,'LTQ Orbitrap Discovery. [PSI:MS]',0,'LTQ Orbitrap Discovery','MS:1000555',1),(50,'SCIEX 5800 TOF-TOF Analyzer. [PSI:MS]',0,'5800 TOF/TOF','MS:1001482',1),(51,'Applied Biosystems/MDS SCIEX API 300 MS. [PSI:MS]',0,'API 300','MS:1002588',1),(52,'Applied Biosystems/MDS SCIEX API 350 MS. [PSI:MS]',0,'API 350','MS:1002589',1),(53,'Applied Biosystems/MDS SCIEX API 100LC MS. [PSI:MS]',0,'API 100LC','MS:1002586',1),(54,'Applied Biosystems/MDS SCIEX API 165 MS. [PSI:MS]',0,'API 165','MS:1002587',1),(55,'SCIEX TripleTOF 5600+ time-of-flight mass spectrometer. [PSI:MS]',0,'TripleTOF 5600+','MS:1002584',1),(56,'Applied Biosystems/MDS SCIEX API 100 MS. [PSI:MS]',0,'API 100','MS:1002585',1),(57,'SCIEX QTRAP 6500+. [PSI:MS]',0,'QTRAP 6500+','MS:1002582',1),(58,'SCIEX TripleTOF 4600 time-of-flight mass spectrometer. [PSI:MS]',0,'TripleTOF 4600','MS:1002583',1),(59,'SCIEX QTRAP 4500. [PSI:MS]',0,'QTRAP 4500','MS:1002580',1),(60,'SCIEX QTRAP 6500. [PSI:MS]',0,'QTRAP 6500','MS:1002581',1),(61,'Thermo Scientific Q Exactive. [PSI:PI]',0,'Q Exactive HF','MS:1002523',1),(62,'Applied Biosystems|MDS SCIEX QTRAP 5500. [PSI:MS]',0,'QTRAP 5500','MS:1000931',1),(63,'Thermo Scientific TSQ 8000 Evo MS. [PSI:PI]',0,'TSQ 8000 Evo','MS:1002525',1),(64,'Thermo Scientific Exactive Plus MS. [PSI:PI]',0,'Exactive Plus','MS:1002526',1),(65,'SCIEX TripleTOF 5600, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',0,'TripleTOF 5600','MS:1000932',1),(66,'SCIEX Triple Quad 6500. [PSI:MS]',0,'Triple Quad 6500','MS:1002594',1),(67,'Applied Biosystems/MDS SCIEX API 150EX Prep MS. [PSI:MS]',0,'API 150EX Prep','MS:1000144',1),(68,'Applied Biosystems/MDS SCIEX API 2000 MS. [PSI:MS]',0,'API 2000','MS:1000145',1),(69,'Applied Biosystems/MDS SCIEX API 3000 MS. [PSI:MS]',0,'API 3000','MS:1000146',1),(70,'Applied Biosystems/MDS SCIEX API 4000 MS. [PSI:MS]',0,'API 4000','MS:1000147',1),(71,'SCIEX Triple Quad 3500. [PSI:MS]',0,'Triple Quad 3500','MS:1002591',1),(72,'Applied Biosystems/MDS SCIEX API 365 MS. [PSI:MS]',0,'API 365','MS:1002590',1),(73,'SCIEX Triple Quad 5500. [PSI:MS]',0,'Triple Quad 5500','MS:1002593',1),(74,'Applied Biosystems/MDS SCIEX API 150EX MS. [PSI:MS]',0,'API 150EX','MS:1000143',1),(75,'Thermo Scientific Orbitrap Fusion Lumos mass spectrometer with Tribrid architecture consisting of quadrupole mass filter, linear ion trap and Orbitrap mass analyzers. [PSI:PI]',1,'Orbitrap Fusion Lumos','MS:1002732',1),(76,'SCIEX TripleTOF 6600, a quadrupole - quadrupole - time-of-flight mass spectrometer. [PSI:MS]',0,'TripleTOF 6600','MS:1002533',1),(77,'Thermo Scientific LTQ Velos MS with ETD. [PSI:MS]',0,'LTQ Velos ETD','MS:1000856',1),(78,'Thermo Scientific LTQ XL MS. [PSI:MS]',0,'LTQ XL','MS:1000854',1),(79,'Thermo Scientific LTQ Velos MS. [PSI:MS]',0,'LTQ Velos','MS:1000855',1),(80,'Thermo Scientific second generation Velos and Orbitrap. [PSI:MS]',0,'LTQ Orbitrap Elite','MS:1001910',1),(81,'Thermo Scientific Q Exactive. [PSI:MS]',0,'Q Exactive','MS:1001911',1),(82,'SCIEX or Applied Biosystems|MDS SCIEX API 5000 MS. [PSI:MS]',0,'API 5000','MS:1000654',1),(83,'SCIEX or Applied Biosystems|MDS SCIEX QSTAR Elite. [PSI:MS]',0,'QSTAR Elite','MS:1000655',1),(84,'Applied Biosystems|MDS SCIEX QSTAR Pulsar. [PSI:MS]',0,'QSTAR Pulsar','MS:1000656',1),(85,'Applied Biosystems|MDS SCIEX QSTAR XL. [PSI:MS]',0,'QSTAR XL','MS:1000657',1),(86,'SCIEX or Applied Biosystems|MDS SCIEX QTRAP 3200. [PSI:MS]',0,'3200 QTRAP','MS:1000651',1),(87,'SCIEX or Applied Biosystems|MDS SCIEX 4800 Plus MALDI TOF-TOF Analyzer. [PSI:MS]',0,'4800 Plus MALDI TOF/TOF','MS:1000652',1),(88,'SCIEX or Applied Biosystems|MDS SCIEX API 3200 MS. [PSI:MS]',0,'API 3200','MS:1000653',1),(89,'Thermo Scientific TSQ 9000 Triple Quadrupole MS. [PSI:PI]',0,'TSQ 9000','MS:1002876',1),(90,'Thermo Scientific Q Exactive HF-X Hybrid Quadrupole Orbitrap MS. [PSI:PI]',0,'Q Exactive HF-X','MS:1002877',1),(91,'Thermo Scientific TSQ Altis Triple Quadrupole MS. [PSI:PI]',0,'TSQ Altis','MS:1002874',1),(92,'Thermo Scientific TSQ Quantis Triple Quadrupole MS. [PSI:PI]',0,'TSQ Quantis','MS:1002875',1),(93,'Liquid chromatographer for mass spectrometry',1,'Easy nLC 1000','LC:0000001',2);
/*!40000 ALTER TABLE `cv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cv_seq`
--

LOCK TABLES `cv_seq` WRITE;
/*!40000 ALTER TABLE `cv_seq` DISABLE KEYS */;
INSERT INTO `cv_seq` VALUES (94);
/*!40000 ALTER TABLE `cv_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `data`
--

LOCK TABLES `data` WRITE;
/*!40000 ALTER TABLE `data` DISABLE KEYS */;
/*!40000 ALTER TABLE `data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `data_source`
--

LOCK TABLES `data_source` WRITE;
/*!40000 ALTER TABLE `data_source` DISABLE KEYS */;
INSERT INTO `data_source` VALUES (2,_binary 'oãk∞≠\—Mï\·xI%Re',1,'Lumos',75,1),(3,_binary '\È\Ë8Bo E}£º[;\ﬂ\’',1,'Proxeon 3',93,1);
/*!40000 ALTER TABLE `data_source` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `file`
--

LOCK TABLES `file` WRITE;
/*!40000 ALTER TABLE `file` DISABLE KEYS */;
/*!40000 ALTER TABLE `file` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `file_seq`
--

LOCK TABLES `file_seq` WRITE;
/*!40000 ALTER TABLE `file_seq` DISABLE KEYS */;
INSERT INTO `file_seq` VALUES (1);
/*!40000 ALTER TABLE `file_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `guide_set`
--

LOCK TABLES `guide_set` WRITE;
/*!40000 ALTER TABLE `guide_set` DISABLE KEYS */;
INSERT INTO `guide_set` VALUES ('automatic',1,_binary '•˝û[\‚BÜ§©S{|]z',NULL,1,NULL,10,NULL);
/*!40000 ALTER TABLE `guide_set` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `guide_set_seq`
--

LOCK TABLES `guide_set_seq` WRITE;
/*!40000 ALTER TABLE `guide_set_seq` DISABLE KEYS */;
INSERT INTO `guide_set_seq` VALUES (2);
/*!40000 ALTER TABLE `guide_set_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `labsystem`
--

LOCK TABLES `labsystem` WRITE;
/*!40000 ALTER TABLE `labsystem` DISABLE KEYS */;
INSERT INTO `labsystem` VALUES (1,_binary 'º\—\È˜ÙO\Zê\'\‚/v5˙','Lumos');
/*!40000 ALTER TABLE `labsystem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `labsystem_data_sources`
--

LOCK TABLES `labsystem_data_sources` WRITE;
/*!40000 ALTER TABLE `labsystem_data_sources` DISABLE KEYS */;
INSERT INTO `labsystem_data_sources` VALUES (1,2),(1,3);
/*!40000 ALTER TABLE `labsystem_data_sources` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `labsystem_guide_sets`
--

LOCK TABLES `labsystem_guide_sets` WRITE;
/*!40000 ALTER TABLE `labsystem_guide_sets` DISABLE KEYS */;
/*!40000 ALTER TABLE `labsystem_guide_sets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `labsystem_seq`
--

LOCK TABLES `labsystem_seq` WRITE;
/*!40000 ALTER TABLE `labsystem_seq` DISABLE KEYS */;
INSERT INTO `labsystem_seq` VALUES (2);
/*!40000 ALTER TABLE `labsystem_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `node`
--

LOCK TABLES `node` WRITE;
/*!40000 ALTER TABLE `node` DISABLE KEYS */;
INSERT INTO `node` VALUES (1,_binary '$òïjs\„KŸ£:Ïáé9XÙ','CRG');
/*!40000 ALTER TABLE `node` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `node_seq`
--

LOCK TABLES `node_seq` WRITE;
/*!40000 ALTER TABLE `node_seq` DISABLE KEYS */;
INSERT INTO `node_seq` VALUES (4),(4);
/*!40000 ALTER TABLE `node_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `param`
--

LOCK TABLES `param` WRITE;
/*!40000 ALTER TABLE `param` DISABLE KEYS */;
INSERT INTO `param` VALUES (1,'Peptide',1,'Peak area',1,'QC:1001844'),(2,'Peptide',0,'Mass accuracy',3,'QC:1000014'),(3,'InstrumentSample',0,'Median IT',3,'QC:9000002'),(4,'InstrumentSample',0,'Total numbers',3,'QC:9000001');
/*!40000 ALTER TABLE `param` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `param_seq`
--

LOCK TABLES `param_seq` WRITE;
/*!40000 ALTER TABLE `param_seq` DISABLE KEYS */;
INSERT INTO `param_seq` VALUES (5);
/*!40000 ALTER TABLE `param_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sample_category_seq`
--

LOCK TABLES `sample_category_seq` WRITE;
/*!40000 ALTER TABLE `sample_category_seq` DISABLE KEYS */;
INSERT INTO `sample_category_seq` VALUES (4);
/*!40000 ALTER TABLE `sample_category_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sample_composition`
--

LOCK TABLES `sample_composition` WRITE;
/*!40000 ALTER TABLE `sample_composition` DISABLE KEYS */;
INSERT INTO `sample_composition` VALUES (1,1,NULL),(2,1,NULL),(3,1,NULL),(4,1,NULL),(5,1,NULL),(6,1,NULL),(7,1,NULL),(8,1,NULL),(9,1,NULL),(10,1,NULL);
/*!40000 ALTER TABLE `sample_composition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sample_seq`
--

LOCK TABLES `sample_seq` WRITE;
/*!40000 ALTER TABLE `sample_seq` DISABLE KEYS */;
INSERT INTO `sample_seq` VALUES (2);
/*!40000 ALTER TABLE `sample_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sample_type`
--

LOCK TABLES `sample_type` WRITE;
/*!40000 ALTER TABLE `sample_type` DISABLE KEYS */;
INSERT INTO `sample_type` VALUES (1,1,'bsa','QC:0000005',1);
/*!40000 ALTER TABLE `sample_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `sample_type_category`
--

LOCK TABLES `sample_type_category` WRITE;
/*!40000 ALTER TABLE `sample_type_category` DISABLE KEYS */;
INSERT INTO `sample_type_category` VALUES (1,_binary '®Å\«+\≈\rK9ê\'∞Ó£π','QC1',0),(2,_binary 'U≤\ŒTMMÑ\–]\’	#æ£','QC2',1),(3,_binary '≠V;W«°JÈ™ö2˛/ßH®','QC3',2);
/*!40000 ALTER TABLE `sample_type_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold`
--

LOCK TABLES `threshold` WRITE;
/*!40000 ALTER TABLE `threshold` DISABLE KEYS */;
INSERT INTO `threshold` VALUES ('hard_limit',1,_binary '=\È\÷\√˜\◊G ∞8aaΩú',1,1,NULL,1,75,NULL,2,1),('sigmalog2',2,_binary 'Tâ\0eõvH€à4˚jÚ\ZØ',1,1,NULL,3,75,NULL,1,1),('sigma',3,_binary 'uCV¯6M%Åi\·\«—íK',1,1,NULL,3,75,NULL,3,1);
/*!40000 ALTER TABLE `threshold` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold_non_conformity`
--

LOCK TABLES `threshold_non_conformity` WRITE;
/*!40000 ALTER TABLE `threshold_non_conformity` DISABLE KEYS */;
/*!40000 ALTER TABLE `threshold_non_conformity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold_non_conformity_seq`
--

LOCK TABLES `threshold_non_conformity_seq` WRITE;
/*!40000 ALTER TABLE `threshold_non_conformity_seq` DISABLE KEYS */;
INSERT INTO `threshold_non_conformity_seq` VALUES (1);
/*!40000 ALTER TABLE `threshold_non_conformity_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold_params`
--

LOCK TABLES `threshold_params` WRITE;
/*!40000 ALTER TABLE `threshold_params` DISABLE KEYS */;
INSERT INTO `threshold_params` VALUES (1,1,0,1,2),(1,2,0,1,0),(2,1,0,1,2),(2,2,0,1,0),(3,1,0,1,2),(3,2,0,1,0),(4,1,0,1,2),(4,2,0,1,0),(5,1,0,1,2),(5,2,0,1,0),(6,1,0,1,2),(6,2,0,1,0),(7,1,0,1,2),(7,2,0,1,0),(8,1,0,1,2),(8,2,0,1,0),(9,1,0,1,2),(9,2,0,1,0),(10,1,0,1,2),(10,2,0,1,0),(11,3,0,1,0),(12,3,0,1,0);
/*!40000 ALTER TABLE `threshold_params` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `threshold_seq`
--

LOCK TABLES `threshold_seq` WRITE;
/*!40000 ALTER TABLE `threshold_seq` DISABLE KEYS */;
INSERT INTO `threshold_seq` VALUES (4);
/*!40000 ALTER TABLE `threshold_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary 'L\ÂóG˚ÙE»≥ÜΩ£§$\Ÿ','daniel.mancera@crg.eu',1,'Daniel','2018-09-27 10:14:00.366000','Mancera','$2a$10$f4.VFIdQR7fjjU2/YnIEO.667F1gmF2cwM2Un03b/asXP.pSErA.a','daniel.mancera@crg.eu',1,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (1,1),(1,2),(1,3);
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_default_view`
--

LOCK TABLES `user_default_view` WRITE;
/*!40000 ALTER TABLE `user_default_view` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_default_view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_default_view_seq`
--

LOCK TABLES `user_default_view_seq` WRITE;
/*!40000 ALTER TABLE `user_default_view_seq` DISABLE KEYS */;
INSERT INTO `user_default_view_seq` VALUES (1);
/*!40000 ALTER TABLE `user_default_view_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user_seq`
--

LOCK TABLES `user_seq` WRITE;
/*!40000 ALTER TABLE `user_seq` DISABLE KEYS */;
INSERT INTO `user_seq` VALUES (2);
/*!40000 ALTER TABLE `user_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `view`
--

LOCK TABLES `view` WRITE;
/*!40000 ALTER TABLE `view` DISABLE KEYS */;
/*!40000 ALTER TABLE `view` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `view_display`
--

LOCK TABLES `view_display` WRITE;
/*!40000 ALTER TABLE `view_display` DISABLE KEYS */;
/*!40000 ALTER TABLE `view_display` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `view_display_seq`
--

LOCK TABLES `view_display_seq` WRITE;
/*!40000 ALTER TABLE `view_display_seq` DISABLE KEYS */;
INSERT INTO `view_display_seq` VALUES (1);
/*!40000 ALTER TABLE `view_display_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `view_seq`
--

LOCK TABLES `view_seq` WRITE;
/*!40000 ALTER TABLE `view_seq` DISABLE KEYS */;
INSERT INTO `view_seq` VALUES (1);
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

-- Dump completed on 2018-09-27 10:32:42
