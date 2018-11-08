LOCK TABLES `trace_color` WRITE;
/*!40000 ALTER TABLE `trace_color` DISABLE KEYS */;
INSERT INTO `trace_color` VALUES (1,_binary '!ñ\"´v~D¡ŒþbkOM¬Ž','rgb(51, 102, 204)'),(2,_binary 'M,÷\ÈA+@Kº\0S™+\éTX','rgb(220, 57, 18)'),(3,_binary 'Š\ß\ÞS7G­­¥²\n@·N','rgb(255, 153, 0)'),(4,_binary '2”CÑ¥F;ˆü`\n\â8¿\Ü','rgb(16, 150, 24)'),(5,_binary 'Œkø„ú¥B>¾\ç\á¬\ïq\è','rgb(153, 0, 153)'),(6,_binary ':¯WO=I[¤¦)V&¿•','rgb(0, 153, 198)'),(7,_binary 'R¯òrKIX»6>¹Y\ä','rgb(221, 68, 119)'),(8,_binary '˜\×ö\ÆJh›7\Äð','rgb(102, 170, 0)'),(9,_binary 'Ù±s©\Z\ZE ˜¶À\×	Kñ\Ý','rgb(184, 46, 46)'),(10,_binary 'À€M\ÜHD‘Š“=$5Ž\Ö','rgb(49, 99, 149)'),(11,_binary '2œtJeE\ï¬iÀ¤1','rgb(153, 68, 153)'),(12,_binary 'h˜1†AØ†y\Ï6„z—g','rgb(37, 171, 154)'),(13,_binary 'ü‡ˆ¿¡jEÙ¶ò>¡ƒ','rgb(170, 170, 17)'),(14,_binary 'ž ±=ÿL¿¤?<Hû‰$','rgb(102, 51, 204)'),(15,_binary '\Åij§g5@É¿¥Œ\Å\í=\Â','rgb(230, 115, 0)');
/*!40000 ALTER TABLE `trace_color` ENABLE KEYS */;
UNLOCK TABLES;

update trace_color_seq set next_val = 16;
