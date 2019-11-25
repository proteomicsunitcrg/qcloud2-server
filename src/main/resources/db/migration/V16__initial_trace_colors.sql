-- LOCK TABLES `trace_color` WRITE;
/*!40000 ALTER TABLE `trace_color` DISABLE KEYS */;
INSERT INTO `trace_color` VALUES (1,_binary 'color1','rgb(51, 102, 204)'),(2,_binary 'color2','rgb(220, 57, 18)'),(3,_binary 'color3','rgb(255, 153, 0)'),(4,_binary 'color4','rgb(16, 150, 24)'),(5,_binary 'color5','rgb(153, 0, 153)'),(6,_binary 'color6','rgb(0, 153, 198)'),(7,_binary 'color7','rgb(221, 68, 119)'),(8,_binary 'color8','rgb(102, 170, 0)'),(9,_binary 'color9','rgb(184, 46, 46)'),(10,_binary 'color10','rgb(49, 99, 149)'),(11,_binary 'color11','rgb(153, 68, 153)'),(12,_binary 'color12','rgb(37, 171, 154)'),(13,_binary 'color13','rgb(170, 170, 17)'),(14,_binary 'color14','rgb(102, 51, 204)'),(15,_binary 'color15','rgb(230, 115, 0)');
/*!40000 ALTER TABLE `trace_color` ENABLE KEYS */;
-- UNLOCK TABLES;

update trace_color_seq set next_val = 16;
