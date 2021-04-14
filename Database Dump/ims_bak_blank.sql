-- MySQL dump 10.17  Distrib 10.3.25-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: inventory_management
-- ------------------------------------------------------
-- Server version	10.3.25-MariaDB-0ubuntu1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `active_invoice`
--

DROP TABLE IF EXISTS `active_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `active_invoice` (
  `order_id` int(11) unsigned zerofill NOT NULL,
  `date_processed` date NOT NULL,
  `total_charge` decimal(10,2) NOT NULL,
  `total_recieved` decimal(10,2) GENERATED ALWAYS AS (`total_charge` - `outstanding_balance`) VIRTUAL,
  `outstanding_balance` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `active_invoice_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `active_invoice`
--

LOCK TABLES `active_invoice` WRITE;
/*!40000 ALTER TABLE `active_invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `active_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `budget`
--

DROP TABLE IF EXISTS `budget`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `budget` (
  `date_start` date NOT NULL,
  `date_end` date NOT NULL,
  `outgoing` double unsigned NOT NULL,
  `income` double unsigned NOT NULL,
  `net` double GENERATED ALWAYS AS (`income` - `outgoing`) VIRTUAL,
  `employee_no` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`date_start`,`date_end`),
  KEY `employee_no` (`employee_no`),
  CONSTRAINT `budget_ibfk_1` FOREIGN KEY (`employee_no`) REFERENCES `employees` (`employee_no`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `budget`
--

LOCK TABLES `budget` WRITE;
/*!40000 ALTER TABLE `budget` DISABLE KEYS */;
/*!40000 ALTER TABLE `budget` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `current_stock`
--

DROP TABLE IF EXISTS `current_stock`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `current_stock` (
  `product_id` int(11) unsigned zerofill NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `current_stock_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `current_stock`
--

LOCK TABLES `current_stock` WRITE;
/*!40000 ALTER TABLE `current_stock` DISABLE KEYS */;
/*!40000 ALTER TABLE `current_stock` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `destination_products`
--

DROP TABLE IF EXISTS `destination_products`;
/*!50001 DROP VIEW IF EXISTS `destination_products`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `destination_products` (
  `date_placed` tinyint NOT NULL,
  `product_id` tinyint NOT NULL,
  `customer_add` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_no` int(11) unsigned zerofill NOT NULL,
  `user_id` int(11) unsigned zerofill NOT NULL,
  `employee_fn` varchar(255) NOT NULL,
  `employee_ln` varchar(255) NOT NULL,
  `pay_hour` double NOT NULL DEFAULT 10,
  `position` varchar(99) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date DEFAULT NULL,
  PRIMARY KEY (`employee_no`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `employees_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incoming_goods`
--

DROP TABLE IF EXISTS `incoming_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incoming_goods` (
  `incoming_id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned zerofill NOT NULL,
  `date_in` date NOT NULL,
  `track_no` varchar(35) DEFAULT NULL,
  `quantity` int(11) NOT NULL,
  `employee_no` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`incoming_id`),
  KEY `employee_no` (`employee_no`),
  KEY `incoming_goods_ibfk_1` (`product_id`),
  CONSTRAINT `incoming_goods_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON UPDATE CASCADE,
  CONSTRAINT `incoming_goods_ibfk_2` FOREIGN KEY (`employee_no`) REFERENCES `employees` (`employee_no`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incoming_goods`
--

LOCK TABLES `incoming_goods` WRITE;
/*!40000 ALTER TABLE `incoming_goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `incoming_goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inventory_management`.`incoming_goods_AFTER_INSERT` AFTER INSERT ON `incoming_goods` FOR EACH ROW
BEGIN
	UPDATE `inventory_management`.`current_stock`
		SET current_stock.quantity = current_stock.quantity + NEW.quantity
        WHERE current_stock.product_id = NEW.product_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `invoice_history`
--

DROP TABLE IF EXISTS `invoice_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice_history` (
  `order_id` int(11) unsigned zerofill NOT NULL,
  `date_processed` date NOT NULL,
  `total_charge` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `invoice_history_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice_history`
--

LOCK TABLES `invoice_history` WRITE;
/*!40000 ALTER TABLE `invoice_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_items` (
  `order_id` int(11) unsigned zerofill NOT NULL,
  `product_id` int(11) unsigned zerofill NOT NULL,
  KEY `product_id` (`product_id`),
  KEY `order_items_ibfk_1` (`order_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `order_status`
--

DROP TABLE IF EXISTS `order_status`;
/*!50001 DROP VIEW IF EXISTS `order_status`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `order_status` (
  `order_date` tinyint NOT NULL,
  `invoice_date` tinyint NOT NULL,
  `outstanding_balance` tinyint NOT NULL,
  `shipping_status` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` int(11) unsigned zerofill NOT NULL,
  `customer_fn` varchar(255) NOT NULL,
  `customer_ln` varchar(255) NOT NULL,
  `customer_add` varchar(255) NOT NULL,
  `date_placed` date NOT NULL,
  `employee_no` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `orders_ibfk_1` (`employee_no`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`employee_no`) REFERENCES `employees` (`employee_no`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outgoing_goods`
--

DROP TABLE IF EXISTS `outgoing_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `outgoing_goods` (
  `outgoing_id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `product_id` int(11) unsigned zerofill NOT NULL,
  `date_go` date NOT NULL,
  `quantity` int(11) unsigned NOT NULL,
  `employee_no` int(11) unsigned zerofill DEFAULT NULL,
  PRIMARY KEY (`outgoing_id`),
  KEY `outgoing_goods_ibfk_2` (`employee_no`),
  KEY `outgoing_goods_ibfk_1` (`product_id`),
  CONSTRAINT `outgoing_goods_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON UPDATE CASCADE,
  CONSTRAINT `outgoing_goods_ibfk_2` FOREIGN KEY (`employee_no`) REFERENCES `employees` (`employee_no`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outgoing_goods`
--

LOCK TABLES `outgoing_goods` WRITE;
/*!40000 ALTER TABLE `outgoing_goods` DISABLE KEYS */;
/*!40000 ALTER TABLE `outgoing_goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inventory_management`.`outgoing_goods_AFTER_INSERT` AFTER INSERT ON `outgoing_goods` FOR EACH ROW
BEGIN
	UPDATE `inventory_management`.`current_stock`
		SET current_stock.quantity = current_stock.quantity - NEW.quantity
        WHERE current_stock.product_id = NEW.product_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `product_id` int(11) unsigned zerofill NOT NULL,
  `name` varchar(255) NOT NULL,
  `msrp` decimal(10,2) unsigned NOT NULL,
  `price` decimal(10,2) unsigned NOT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inventory_management`.`products_AFTER_INSERT` AFTER INSERT ON `products` FOR EACH ROW
BEGIN
	INSERT INTO current_stock(product_id) VALUES (NEW.product_id);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `inventory_management`.`products_BEFORE_DELETE` BEFORE DELETE ON `products` FOR EACH ROW
BEGIN
	DELETE FROM current_stock WHERE product_id = OLD.product_id;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tracking`
--

DROP TABLE IF EXISTS `tracking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tracking` (
  `order_id` int(11) unsigned zerofill NOT NULL,
  `shipping_status` varchar(255) NOT NULL,
  `tracking_id` varchar(35) DEFAULT NULL,
  `carrier` varchar(99) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  CONSTRAINT `tracking_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tracking`
--

LOCK TABLES `tracking` WRITE;
/*!40000 ALTER TABLE `tracking` DISABLE KEYS */;
/*!40000 ALTER TABLE `tracking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT,
  `username` varchar(99) NOT NULL,
  `password` varchar(255) NOT NULL,
  `fname` varchar(255) NOT NULL,
  `lname` varchar(255) NOT NULL,
  `role` varchar(99) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `destination_products`
--

/*!50001 DROP TABLE IF EXISTS `destination_products`*/;
/*!50001 DROP VIEW IF EXISTS `destination_products`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`casiano`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `destination_products` AS select `orders`.`date_placed` AS `date_placed`,`order_items`.`product_id` AS `product_id`,`orders`.`customer_add` AS `customer_add` from (`orders` left join `order_items` on(`orders`.`order_id` = `order_items`.`order_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `order_status`
--

/*!50001 DROP TABLE IF EXISTS `order_status`*/;
/*!50001 DROP VIEW IF EXISTS `order_status`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`casiano`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `order_status` AS select `orders`.`date_placed` AS `order_date`,`active_invoice`.`date_processed` AS `invoice_date`,`active_invoice`.`outstanding_balance` AS `outstanding_balance`,`tracking`.`shipping_status` AS `shipping_status` from ((`active_invoice` join `orders` on(`active_invoice`.`order_id` = `orders`.`order_id`)) join `tracking` on(`orders`.`order_id` = `tracking`.`order_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-14 10:16:18
