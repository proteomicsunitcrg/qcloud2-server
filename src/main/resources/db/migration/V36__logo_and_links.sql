CREATE TABLE `logo` (
  `id` bigint NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `alt` varchar(255) DEFAULT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6qifu55ej2a695vmj57rypy8h` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logo`
--

LOCK TABLES `logo` WRITE;
/*!40000 ALTER TABLE `logo` DISABLE KEYS */;
INSERT INTO `logo` VALUES (1,_binary '☺','May the force with you','apiKey1','Star Wars','https://i.imgur.com/9hXWCat.png'),(2,_binary '\0','Happy Sant jordi!','apiKey2','Sant Jordi','https://i.imgur.com/ueoSLUU.png'),(3,_binary '\0','Happy christmas','apiKey3',
'Christmas','https://i.imgur.com/L0gu7hj.png');
/*!40000 ALTER TABLE `logo` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `logo_increment` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `logo_increment` VALUES (4);






CREATE TABLE `link` (
  `id` bigint NOT NULL,
  `api_key` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_1voekpuq97u80rjorwd65qk42` (`api_key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES `link` WRITE;
/*!40000 ALTER TABLE `link` DISABLE KEYS */;
INSERT INTO `link` VALUES (1,'apiKey1','Download','https://www.dropbox.com/s/kotvlfuu5zn8gnz/Q
Crawler0192.exe'),(2,'apiKey2','Manual','https://www.dropbox.com/s/cwlf08ofx7u1z2n/Qcrawler_fo
r%20Qcloud_2.1.pdf');
/*!40000 ALTER TABLE `link` ENABLE KEYS */;
UNLOCK TABLES;

CREATE TABLE `link_increment` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `link_increment` VALUES (3);