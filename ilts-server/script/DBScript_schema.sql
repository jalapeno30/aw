
-- Host: localhost    Database: lottery
-- ------------------------------------------------------
-- Server version	5.6.16

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


-- Table structure for table `draw_statuses`

DROP TABLE IF EXISTS draw_statuses ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draw_statuses` (
  `draw_statuses_id` tinyint(1) NOT NULL ,
  `status_name` varchar(15) NOT NULL,
  PRIMARY KEY (`draw_statuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `users`
DROP TABLE IF EXISTS users ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `password` varchar(25) NOT NULL COLLATE latin1_general_cs,
  `first_name` varchar(25) NOT NULL,
  `last_name` varchar(25) NOT NULL,
  `birth_date` varchar(30) DEFAULT NULL,
  `gender` varchar(1) DEFAULT NULL,
  `email_id` varchar(50) DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `account_number` int(8) DEFAULT NULL,
  `address_1` varchar(50) DEFAULT NULL,
  `address_2` varchar(50) DEFAULT NULL,
  `city` varchar(15) DEFAULT NULL,
  `state` varchar(2) DEFAULT NULL,
  `zipcode` int(8) DEFAULT NULL,
  `country` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `account_number` (`account_number`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `games`
DROP TABLE IF EXISTS games ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `game_id` int(3) NOT NULL AUTO_INCREMENT,
  `game_name` varchar(50) NOT NULL,
  `game_numbers` int(2) NOT NULL,
  `game_cost` decimal(10,2) NOT NULL,
  `game_logo` varchar(100) NOT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `draws`
DROP TABLE IF EXISTS draws ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draws` (
  `draw_id` int(4) NOT NULL AUTO_INCREMENT,
  `game_id` int(3) NOT NULL,
  `draw_jackpot` decimal(10,2) NOT NULL,
  `draw_date` date NOT NULL,
  PRIMARY KEY (`draw_id`),
  KEY `game_id` (`game_id`),
  CONSTRAINT `FK_draw_game_id` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=200 DEFAULT CHARSET=latin1;  
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `draw_status`
DROP TABLE IF EXISTS draw_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draw_status` (
  `draw_status_id` int(4) NOT NULL AUTO_INCREMENT,
  `draw_id` int(4) NOT NULL,
  `status_id` tinyint(1) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`draw_status_id`),
  KEY `draw_id` (`draw_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `FK_draw_status_detail_id` FOREIGN KEY (`status_id`) REFERENCES `draw_statuses` (`draw_statuses_id`),
  CONSTRAINT `FK_draw_status_draw_id` FOREIGN KEY (`draw_id`) REFERENCES `draws` (`draw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `tickets`
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE IF EXISTS tickets ;
CREATE TABLE `tickets` (
  `ticket_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticket_serialnumber` varchar(255) NOT NULL,
  `ticket_timestamp` datetime NOT NULL,
  `ticket_cost` decimal(10,2) NOT NULL,
  `ticket_Tax` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `bets`
DROP TABLE IF EXISTS bets ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bets` (
  `bet_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `draw_id` int(4) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `bet_cost` decimal(10,2) NOT NULL,
  `bet_tax` decimal(10,2) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`bet_id`),
  KEY `draw_id` (`draw_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FK_bet_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `bets_ibfk_1` FOREIGN KEY (`draw_id`) REFERENCES `draws` (`draw_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `systems`
DROP TABLE IF EXISTS systems ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systems` (
  `system_name` varchar(255) NOT NULL DEFAULT '',
  `system_number` int(11) DEFAULT NULL,
  PRIMARY KEY (`system_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `bet_selections`
DROP TABLE IF EXISTS bet_selections ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet_selections` (
  `bet_selection_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bet_id` bigint(20) NOT NULL,
  `bet_selection_bonus` int(10) NOT NULL,
  `bet_selection_system` varchar(255) NOT NULL,
  PRIMARY KEY (`bet_selection_id`),
  KEY `bet_id` (`bet_id`),
  KEY `bet_selection_system` (`bet_selection_system`),
  CONSTRAINT `FK_bet_selection_bet_id` FOREIGN KEY (`bet_id`) REFERENCES `bets` (`bet_id`),
  CONSTRAINT `FK_bet_selection_system` FOREIGN KEY (`bet_selection_system`) REFERENCES `systems` (`system_name`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `bet_selection_numbers`
DROP TABLE IF EXISTS bet_selection_numbers ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet_selection_numbers` (
  `bet_selection_number_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bet_selection_id` bigint(20) NOT NULL,
  `bet_selection_number` int(10) NOT NULL,
  PRIMARY KEY (`bet_selection_number_id`),
  KEY `bet_selection_id` (`bet_selection_id`),
  CONSTRAINT `FK_bet_selection_numbers_bet_selection` FOREIGN KEY (`bet_selection_id`) REFERENCES `bet_selections` (`bet_selection_id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `orders`
DROP TABLE IF EXISTS orders ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `order_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `game_id` int(3) DEFAULT NULL,
  `draw_id` int(4) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  `system_name` varchar(255) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`order_id`),
  KEY `orders_gameId_ibfk_1` (`game_id`),
  KEY `orders_drawId_ibfk_1` (`draw_id`),
  KEY `FK_orders_user_id` (`user_id`),
  CONSTRAINT `FK_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_drawId_ibfk_1` FOREIGN KEY (`draw_id`) REFERENCES `draws` (`draw_id`),
  CONSTRAINT `orders_gameId_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1300 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `orders_numbers`
DROP TABLE IF EXISTS orders_numbers ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_numbers` (
  `orders_number_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `numbers` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orders_number_id`),
  KEY `FK_orders_order_id` (`order_id`),
  CONSTRAINT `FK_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1500 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `purchases`
DROP TABLE IF EXISTS purchases ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `purchases_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`purchases_id`),
  KEY `FK_purchase_orders_order_id` (`order_id`),
  KEY `FK_purchases_user_id` (`user_id`),
  CONSTRAINT `FK_purchase_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_purchases_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1700 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `roles`
DROP TABLE IF EXISTS roles ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` tinyint(1) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(32) NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `user_roles`
DROP TABLE IF EXISTS user_roles ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` tinyint(1) NOT NULL,
  PRIMARY KEY (`user_role_id`),
  CONSTRAINT `user_roles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_roles_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `user_statuses`
DROP TABLE IF EXISTS user_statuses ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_statuses` (
  `user_statuses_id` int(2) NOT NULL,
  `user_statuses_name` varchar(12) NOT NULL,
  PRIMARY KEY (`user_statuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `user_status`
DROP TABLE IF EXISTS user_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `user_status_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `user_statuses_id` int(2) NOT NULL,
  PRIMARY KEY (`user_status_id`),
  KEY `user_status_ibfk_1` (`user_id`),
  KEY `user_status_ibfk_2` (`user_statuses_id`),
  CONSTRAINT `user_status_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_status_ibfk_2` FOREIGN KEY (`user_statuses_id`) REFERENCES `user_statuses` (`user_statuses_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000000 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `permissions`
DROP TABLE IF EXISTS permissions ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `permission_id` int(2) NOT NULL AUTO_INCREMENT,
  `alias` varchar(25) NOT NULL,
  `description` varchar(80) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `role_permissions`
DROP TABLE IF EXISTS role_permissions ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `role_permission_id` int(1) NOT NULL AUTO_INCREMENT,
  `role_id` tinyint(1) NOT NULL,
  `permission_id` int(2) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_permission_id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `paypal_purchases`
DROP TABLE IF EXISTS paypal_purchases ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchases` (
  `paypal_purchase_Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `date_paypal` datetime DEFAULT NULL,
  `paypal_id` varchar(255) DEFAULT NULL,
  `confirm_id` varchar(255) DEFAULT NULL,
  `cancelled` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`paypal_purchase_Id`),
  KEY `FK_paypal_user_id` (`user_id`),
  CONSTRAINT `FK_paypal_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1900 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `paypal_purchase_links`
DROP TABLE IF EXISTS paypal_purchase_links ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_links` (
  `paypal_purchase_link_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `paypal_purchase_Id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`paypal_purchase_link_id`),
  KEY `paypal_purchase_Id` (`paypal_purchase_Id`),
  CONSTRAINT `paypal_purchase_links_ibfk_1` FOREIGN KEY (`paypal_purchase_Id`) REFERENCES `paypal_purchases` (`paypal_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `paypal_purchase_orders`
DROP TABLE IF EXISTS paypal_purchase_orders ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_orders` (
  `paypal_purchase_order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paypal_purchase_Id` bigint(20) NOT NULL,
  `order_id` bigint(20) unsigned NOT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`paypal_purchase_order_id`),
  KEY `fk_purchase_id_` (`paypal_purchase_Id`),
  KEY `FK_paypal_orders_order_id` (`order_id`),
  CONSTRAINT `FK_paypal_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `fk_purchase_id_` FOREIGN KEY (`paypal_purchase_Id`) REFERENCES `paypal_purchases` (`paypal_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2300 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `paypal_purchase_status`
DROP TABLE IF EXISTS paypal_purchase_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_status` (
  `paypal_status_Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paypal_purchase_Id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `datecreated` datetime DEFAULT NULL,
  `datemodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`paypal_status_Id`),
  KEY `paypal_purchase_Id` (`paypal_purchase_Id`),
  CONSTRAINT `t_paypal_purchase_status_ibfk_1` FOREIGN KEY (`paypal_purchase_Id`) REFERENCES `paypal_purchases` (`paypal_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2500 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `o_games`
DROP TABLE IF EXISTS o_games ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_games` (
  `o_game_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `numbers` int(11) DEFAULT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  PRIMARY KEY `o_game_id` (`o_game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=150 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `o_draws`
DROP TABLE IF EXISTS o_draws ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_draws` (
  `o_draws_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `o_game_id` bigint(20) unsigned NOT NULL,
  `jackpot` decimal(20,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `vendorCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY `o_draws_id` (`o_draws_id`),
  CONSTRAINT `o_games_ibfk_1` FOREIGN KEY (`o_game_id`) REFERENCES `o_games` (`o_game_id`)
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `sessions`
DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `session_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20)  NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expires` datetime NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY `session_id` (`session_id`),
  CONSTRAINT `o_user_session_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=190 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `recovery_table`
DROP TABLE IF EXISTS `recovery_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recovery_table` (
  `recovery_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `transactionId` varchar(255) NOT NULL,
  `transactionData` text NOT NULL,
  `status` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`recovery_id`),
  CONSTRAINT `o_user_recovery_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=390 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



-- View structure for table `purchase_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW 
`purchase_log` AS select `paypal_purchases`.`paypal_purchase_Id` AS `purchaseId`,`paypal_purchases`.`cost` 
AS `purchaseCost`,`paypal_purchases`.`date_paypal` AS `purchaseDate`,`paypal_purchase_status`.`status` 
AS `purchaseStatus`,(case when (`paypal_purchase_status`.`deleted` = 0) then 'Active' else 'Inactive' end) 
AS `purchaseStatusActive`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified` 
AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on((`paypal_purchases`.`paypal_purchase_Id` = `paypal_purchase_status`.`paypal_purchase_Id`))) 
order by `paypal_purchases`.`paypal_purchase_Id`,`paypal_purchase_status`.`dateModified`;


-- View structure for table `purchase_current_status_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW
`purchase_current_status_log` AS select `paypal_purchases`.`paypal_purchase_Id` AS `purchaseId`,`paypal_purchases`.`cost`
AS `purchaseCost`,`paypal_purchases`.`date_paypal` AS `purchaseDate`,`paypal_purchase_status`.`status` AS 
`purchaseStatus`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified`
AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on((`paypal_purchases`.`paypal_purchase_Id` = `paypal_purchase_status`.`paypal_purchase_Id`))) 
where (`paypal_purchase_status`.`deleted` = 0) order by `paypal_purchases`.`paypal_purchase_Id`,`paypal_purchase_status`.`dateModified`;



-- View structure for table `active_purchase_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW 
`active_purchase_log` AS select `paypal_purchases`.`paypal_purchase_Id` AS `purchaseId`,`paypal_purchases`.`cost` 
AS `purchaseCost`,`paypal_purchases`.`date_paypal` AS `purchaseDate`,`paypal_purchase_status`.`status` AS 
`purchaseStatus`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified` 
AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on(((`paypal_purchases`.`paypal_purchase_Id` = `paypal_purchase_status`.`paypal_purchase_Id`) 
and (`paypal_purchase_status`.`status` <> 'completed')))) order by `paypal_purchases`.`paypal_purchase_Id`,`paypal_purchase_status`.`dateModified`;



-- Table structure for table `languageSerialized`
DROP TABLE IF EXISTS `languageSerialized`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languageSerialized` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `json` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



-- Table structure for table `language_entries`
DROP TABLE IF EXISTS `language_entries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language_entries` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `languageId` bigint(20) NOT NULL,
  `fieldId` bigint(20) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `language_fields`
DROP TABLE IF EXISTS `language_fields`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `language_fields` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NOT NULL DEFAULT '0',
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



-- Table structure for table `languages`
DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draw_statuses`
--
LOCK TABLES `draw_statuses` WRITE;
/*!40000 ALTER TABLE `draw_statuses` DISABLE KEYS */;
INSERT INTO `draw_statuses` VALUES (1,'Active'),(2,'Inactive'),(3,'Ended');
/*!40000 ALTER TABLE `draw_statuses` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `users`
--
LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO lottery.users(
   user_id
  ,user_name
  ,password
  ,first_name
  ,last_name
  ,gender
  ,email_id
) VALUES (
   1 -- user_id - IN bigint(20)
  ,"Admin" -- user_name - IN varchar(20)
  ,"adminPass" -- password - IN varchar(25)
  ,"Admin Fname" -- first_name - IN varchar(25)
  ,"Admin Lname" -- last_name - IN varchar(25)
  ,'f'  -- gender - IN varchar(15)
  ,'admintestjavaemail@gmail.com'  -- email_id - IN varchar(50)
);


--
-- Dumping data for table `games`
--
LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES 
(100,'Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),
(101,'Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),
(102,'Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),
(103,'Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `draws`
--
LOCK TABLES `draws` WRITE;
/*!40000 ALTER TABLE `draws` DISABLE KEYS */;
INSERT INTO `draws` VALUES (200,100,73377937.00,'2014-08-15'),(201,100,12387875.00,'2014-08-16'),
(202,101,23581519.00,'2014-08-15'),(203,101,84861982.00,'2014-08-16'),(204,102,62800237.00,'2014-08-15'),
(205,102,66775157.00,'2014-08-16'),(206,103,29687870.00,'2014-08-15'),(207,103,34456957.00,'2014-08-16'),
(208,101,1200000.00,'2014-08-17'),(209,101,1200000.00,'2014-08-17'),(210,103,43000000.00,'2014-08-22');
/*!40000 ALTER TABLE `draws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `draw_status`
--
LOCK TABLES `draw_status` WRITE;
/*!40000 ALTER TABLE `draw_status` DISABLE KEYS */;
INSERT INTO `draw_status` VALUES (1000,200,1,'2014-08-13 13:17:35',1),(1001,200,2,'2014-08-13 13:24:28',1),(1002,200,3,'2014-08-13 13:24:31',1),
(1003,200,1,'2014-08-13 13:24:33',1),(1004,201,1,'2014-08-13 13:25:08',0),(1005,202,1,'2014-08-13 13:25:08',1),(1006,203,1,'2014-08-13 13:25:13',0),
(1007,204,1,'2014-08-13 13:25:13',0),(1008,205,1,'2014-08-13 13:25:20',0),(1009,206,1,'2014-08-13 13:25:20',0),(1010,207,1,'2014-08-13 13:25:25',1),
(1011,207,2,'2014-08-13 13:25:33',1),(1012,207,1,'2014-08-13 13:25:35',0),(1013,200,3,'2014-08-13 13:31:36',1),(1014,200,1,'2014-08-13 13:31:42',0),
(1015,202,2,'2014-08-13 14:30:25',1),(1016,202,1,'2014-08-13 14:30:29',0),(1017,209,2,'2014-08-15 17:29:43',1),(1018,209,1,'2014-08-15 17:29:52',0),
(1019,210,2,'2014-08-15 17:31:54',1),(1020,210,1,'2014-08-17 16:45:41',1),(1021,210,2,'2014-08-18 17:02:27',0);
/*!40000 ALTER TABLE `draw_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `tickets`
--
LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'a','2014-11-24 07:00:00',10.00,0.00),(2,'b','2014-11-24 10:00:00',40.00,0.00);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `systems`
--
LOCK TABLES `systems` WRITE;
/*!40000 ALTER TABLE `systems` DISABLE KEYS */;
INSERT INTO `systems` VALUES ('10',10),('11',11),('12',12),('5R',5),('6',6),('7',7),('8',8),('9',9);
/*!40000 ALTER TABLE `systems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `roles`
--
LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrator'),(2,'User'),(3,'Customer Support Representative');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_roles`
--
LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_statuses`
--
LOCK TABLES `user_statuses` WRITE;
/*!40000 ALTER TABLE `user_statuses` DISABLE KEYS */;
INSERT INTO `user_statuses` VALUES (1,'Active'),(2,'Locked'),(3,'Deleted');
/*!40000 ALTER TABLE `user_statuses` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `user_status`
--
LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (1,1,1);
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping data for table `permissions`
--
LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'BET_CREATE','Place a bet'),(2,'BET_VIEW','View bet options');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role_permissions`
--
LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (1,1,1,0),(2,1,2,0),(3,2,1,1),(4,2,2,1),(5,3,1,0),(6,3,2,1);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `o_games`
--
LOCK TABLES `o_games` WRITE;
/*!40000 ALTER TABLE `o_games` DISABLE KEYS */;
INSERT INTO `o_games` VALUES (100,'Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),(101,'Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),(102,'Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),(103,'Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `o_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `o_draws`
--
LOCK TABLES `o_draws` WRITE;
/*!40000 ALTER TABLE `o_draws` DISABLE KEYS */;
INSERT INTO `o_draws` VALUES 
(200,100,73377937.00,'2014-08-15','Tonight','Draw abcd'),
(201,100,12387875.00,'2014-08-16','Friday','Draw abce'),
(202,101,23581519.00,'2014-08-15','Tonight','Draw abcd'),
(203,101,84861982.00,'2014-08-16','Friday','Draw abce'),
(204,102,62800237.00,'2014-08-15','Tonight','Draw abcd'),
(205,102,66775157.00,'2014-08-16','Friday','Draw abce'),
(206,103,29687870.00,'2014-08-15','Tonight','Draw abcd'),
(207,103,34456957.00,'2014-08-16','Friday','Draw abce'),
(208,101,1200000.00,'2014-08-17','','abc'),
(209,101,1200000.00,'2014-08-17','','abc'),
(210,103,43000000.00,'2014-08-22','','234');
/*!40000 ALTER TABLE `o_draws` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `sessions`
--
LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35', 1,'2014-08-02 10:10:52'), (2,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'); 
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `recovery_table`
--
LOCK TABLES `recovery_table` WRITE;
/*!40000 ALTER TABLE `recovery_table` DISABLE KEYS */;
INSERT INTO `recovery_table` VALUES (1,1,'0','','Initiate funding request from paypal','2014-09-30 16:51:11'),(2,1,'1c4536bc7ba0b5ef7dd436260c959fee52d89282','{\n  \"bets\" : [ {\n    \"id\" : 30,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"10\", \"12\", \"19\", \"27\", \"30\", \"34\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-01 03:49:29');
/*!40000 ALTER TABLE `recovery_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `languageSerialized`
--
LOCK TABLES `languageSerialized` WRITE;
/*!40000 ALTER TABLE `languageSerialized` DISABLE KEYS */;
INSERT INTO `languageSerialized` VALUES (1,'en','English','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Games\",\"RESULTS\":\"Results\",\"HOW_TO_PLAY\":\"How to Play\",\"BUY_LOTTO\":\"Buy Lotto\",\"ABOUT\":\"About\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Purchases\",\"APP_SETTINGS\":\"Application Settings\",\"REPORTS\":\"Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Announcements\",\"MORE_ANNOUNCEMENTS\":\"More Announcements\",\"RESULTS\":\"Results\",\"WINNERS\":\"{{result.date}} {{result.winners}} winner(s)\"},\"RESULTS\":{\"GAMES\":\"Games\",\"MORE_RESULTS\":\"More {{game.name}} Results\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Choose a Game\",\"CHOOSE_YOUR_DRAW\":\"Choose Your Draw\",\"CHOOSE_YOUR_SYSTEM\":\"Choose Your System\",\"CHOOSE_YOUR_NUMBERS\":\"Choose Your Numbers\"},\"BUTTONS\":{\"RESET\":\"Reset\",\"ADD_TO_CART\":\"Add to Cart\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Selected Numbers\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Orders\",\"NO_ORDERS\":\"No orders\",\"REMOVE\":\"Remove\",\"DRAWS\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"BUY_NOW\":\"Buy Now\"},\"PURCHASES\":{\"DETAILS\":\"Details\",\"GAME\":\"Game\",\"DRAW\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"AMOUNT\":\"Amount\"}}'),(2,'tg','Tagalog','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Laro\",\"RESULTS\":\"Resulta\",\"HOW_TO_PLAY\":\"Paano Maglaro\",\"BUY_LOTTO\":\"Bumili\",\"ABOUT\":\"Tungkol sa Lotto\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Mga Bilihin\",\"APP_SETTINGS\":\"Mga Settings\",\"REPORTS\":\"Mga Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Mga Paunawa\",\"MORE_ANNOUNCEMENTS\":\"Higit Pa\",\"RESULTS\":\"Mga Resulta\",\"WINNERS\":\"{{result.date}} {{result.winners}} na panalo\"},\"RESULTS\":{\"GAMES\":\"Mga Laro\",\"MORE_RESULTS\":\"Higit pa na Resulta ng {{game.name}}\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Pumili ng Laro\",\"CHOOSE_YOUR_DRAW\":\"Pumili ng Araw\",\"CHOOSE_YOUR_SYSTEM\":\"Pumili ng Sistema\",\"CHOOSE_YOUR_NUMBERS\":\"Pumili ng Numero\"},\"BUTTONS\":{\"RESET\":\"I-reset\",\"ADD_TO_CART\":\"Idagdag sa order\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Mga Piniling Numero\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Mga Order\",\"NO_ORDERS\":\"Walang order\",\"REMOVE\":\"Tanggalin\",\"DRAWS\":\"Araw\",\"SYSTEM\":\"Sistema\",\"NUMBERS\":\"Numero\",\"BUY_NOW\":\"Bilhin na\"},\"PURCHASES\":{\"DETAILS\":\"Mga Detalye\",\"GAME\":\"Laro\",\"DRAW\":\"Araw\",\"SYSTEM\":\"Sistema\",\"NUMBERS\":\"Numero\",\"AMOUNT\":\"Halaga\"}}'),(3,'Test','test','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Games\",\"RESULTS\":\"Results\",\"HOW_TO_PLAY\":\"How to Play\",\"BUY_LOTTO\":\"Buy Lotto\",\"ABOUT\":\"About\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Purchases\",\"APP_SETTINGS\":\"Application Settings\",\"REPORTS\":\"Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Announcements\",\"MORE_ANNOUNCEMENTS\":\"More Announcements\",\"RESULTS\":\"Results\",\"WINNERS\":\"{{result.date}} {{result.winners}} winner(s)\"},\"RESULTS\":{\"GAMES\":\"Games\",\"MORE_RESULTS\":\"More {{game.name}} Results\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Choose a Game\",\"CHOOSE_YOUR_DRAW\":\"Choose Your Draw\",\"CHOOSE_YOUR_SYSTEM\":\"Choose Your System\",\"CHOOSE_YOUR_NUMBERS\":\"Choose Your Numbers\"},\"BUTTONS\":{\"RESET\":\"Reset\",\"ADD_TO_CART\":\"Add to Cart\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Selected Numbers\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Orders\",\"NO_ORDERS\":\"No orders\",\"REMOVE\":\"Remove\",\"DRAWS\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"BUY_NOW\":\"Buy Now\"},\"PURCHASES\":{\"DETAILS\":\"Details\",\"GAME\":\"Game\",\"DRAW\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"AMOUNT\":\"Amount\"}}');
/*!40000 ALTER TABLE `languageSerialized` ENABLE KEYS */;
UNLOCK TABLES;


--
-- Dumping data for table `language_entries`
--
LOCK TABLES `language_entries` WRITE;
/*!40000 ALTER TABLE `language_entries` DISABLE KEYS */;
INSERT INTO `language_entries` VALUES (1,1,2,'Home'),(2,2,2,'Bahay');
/*!40000 ALTER TABLE `language_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `language_fields`
--
LOCK TABLES `language_fields` WRITE;
/*!40000 ALTER TABLE `language_fields` DISABLE KEYS */;
INSERT INTO `language_fields` VALUES (1,0,'PAGE_HEADINGS'),(2,1,'HOME');
/*!40000 ALTER TABLE `language_fields` ENABLE KEYS */;
UNLOCK TABLES;



--
-- Dumping data for table `languages`
--
LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'en','English'),(2,'tg','Tagalog');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

         LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES
(1,100,200,1,'6',20,1,1),
(2,100,200,1,'6',20,0,1),
(3,100,200,1,'6',20,0,0),
(4,101,202,1,'6',20,0,0),
(5,100,201,1,'6',20,0,0),
(6,102,204,1,'6',10,0,0),
(7,100,200,1,'6',40,0,0),
(8,103,206,1,'7',70,0,0),
(9,101,202,1,'8',560,0,1),
(10,100,200,1,'6',20,1,1),
(11,103,207,1,'6',10,0,0),
(12,103,206,1,'6',10,0,0),
(13,101,202,1,'7',280,0,0),
(14,100,200,1,'6',20,1,1),
(15,100,201,1,'6',20,0,0),
(16,100,201,1,'6',20,0,0),
(17,100,201,1,'6',20,0,0),
(18,100,201,1,'6',20,1,1),
(19,100,201,1,'6',20,1,1),
(20,100,201,1,'6',20,1,1),
(21,100,201,1,'6',20,1,1),
(22,100,201,1,'6',20,1,1),
(23,100,201,1,'6',20,0,0),
(24,101,202,1,'7',140,0,0),
(25,101,203,1,'7',140,1,1);

/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;


LOCK TABLES `orders_numbers` WRITE;
/*!40000 ALTER TABLE `orders_numbers` DISABLE KEYS */;
INSERT INTO `orders_numbers` VALUES 
(1,2,'7,12,15,18,41,54,'),
(2,3,'7,8,23,36,47,54,'),
(3,4,'6,12,21,26,34,41,'),
(4,5,'13,19,23,41,42,44,'),
(5,6,'22,23,24,30,33,37,'),
(6,7,'21,30,39,46,49,55,'),
(7,7,'2,15,22,37,45,47,'),
(8,8,'2,5,17,21,23,37,40,'),
(9,9,'7,8,11,37,44,47,48,49,'),
(10,10,'10,14,39,42,47,50,'),
(11,12,'17,23,27,29,32,40,'),
(12,11,'17,23,27,29,32,40,'),
(13,13,'4,6,23,26,28,36,40,'),
(14,13,'13,17,19,22,24,39,49,'),
(15,14,'2,3,4,6,28,53,'),(16,15,'9,16,19,39,40,42,'),
(17,16,'9,10,13,31,35,49,'),(18,17,'5,6,7,9,48,53,'),
(19,18,'6,15,17,27,32,38,'),(20,19,'6,15,17,27,32,38,'),
(21,20,'2,7,20,30,35,41,'),(22,21,'3,5,21,34,45,50,'),
(23,22,'14,16,24,27,38,52,'),(24,23,'2,23,25,37,43,49,'),
(25,24,'16,23,25,31,37,45,49,'),(26,25,'16,23,25,31,37,45,49,')
;
/*!40000 ALTER TABLE `orders_numbers` ENABLE KEYS */;
UNLOCK TABLES;
