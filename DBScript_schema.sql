
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
  `draw_statuses_id` varchar(36) NOT NULL ,
  `status_name` varchar(15) NOT NULL UNIQUE,
  PRIMARY KEY (`draw_statuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

CREATE TRIGGER before_insert_draw_statuses_id
  BEFORE INSERT ON draw_statuses 
  FOR EACH ROW
  SET new.draw_statuses_id = uuid();
  
-- Table structure for table `users`
DROP TABLE IF EXISTS users ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` varchar(36) NOT NULL ,
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `games`
DROP TABLE IF EXISTS games ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `game_id` varchar(36) NOT NULL ,
  `game_name` varchar(50) NOT NULL UNIQUE,
  `game_numbers` int(2) NOT NULL,
  `game_cost` decimal(10,2) NOT NULL,
  `game_logo` varchar(100) NOT NULL,
  PRIMARY KEY (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_games_id
  BEFORE INSERT ON games 
  FOR EACH ROW
  SET new.game_id = uuid();


-- Table structure for table `draws`
DROP TABLE IF EXISTS draws ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draws` (
  `draw_id` varchar(36) NOT NULL ,
  `game_id` varchar(36) NOT NULL,
  `draw_jackpot` decimal(10,2) NOT NULL,
  `draw_date` date NOT NULL,
  PRIMARY KEY (`draw_id`),
  KEY `game_id` (`game_id`),
  CONSTRAINT `FK_draw_game_id` FOREIGN KEY (`game_id`) REFERENCES `games` (`game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;  
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_draws_id
  BEFORE INSERT ON draws 
  FOR EACH ROW
  SET new.draw_id = uuid();


-- Table structure for table `draw_status`
DROP TABLE IF EXISTS draw_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draw_status` (
  `draw_status_id` varchar(36) NOT NULL ,
  `draw_id` varchar(36) NOT NULL,
  `status_id` varchar(36) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`draw_status_id`),
  KEY `draw_id` (`draw_id`),
  KEY `status_id` (`status_id`),
  CONSTRAINT `FK_draw_status_detail_id` FOREIGN KEY (`status_id`) REFERENCES `draw_statuses` (`draw_statuses_id`),
  CONSTRAINT `FK_draw_status_draw_id` FOREIGN KEY (`draw_id`) REFERENCES `o_draws` (`o_draw_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_draw_status
  BEFORE INSERT ON draw_status 
  FOR EACH ROW
  SET new.draw_status_id = uuid();


-- Table structure for table `tickets`
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
DROP TABLE IF EXISTS tickets ;
CREATE TABLE `tickets` (
  `ticket_id` varchar(36) NOT NULL ,
  `ticket_serialnumber` varchar(255) NOT NULL,
  `ticket_timestamp` datetime NOT NULL,
  `ticket_cost` decimal(10,2) NOT NULL,
  `ticket_Tax` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_tickets_id
  BEFORE INSERT ON tickets 
  FOR EACH ROW
  SET new.ticket_id = uuid();


-- Table structure for table `bets`
DROP TABLE IF EXISTS bets ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bets` (
  `bet_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `draw_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
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
  `system_name` varchar(255) NOT NULL UNIQUE,
  `system_number` int(11) NOT NULL UNIQUE,
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
  `order_id` varchar(36) NOT NULL,
  `game_id` varchar(36) NOT NULL,
  `draw_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
  `system_name` varchar(255) NOT NULL,
  `cost` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`order_id`),
  KEY `orders_gameId_ibfk_1` (`game_id`),
  KEY `orders_drawId_ibfk_1` (`draw_id`),
  KEY `FK_orders_user_id` (`user_id`),
  CONSTRAINT `FK_orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `orders_drawId_ibfk_1` FOREIGN KEY (`draw_id`) REFERENCES `o_draws` (`o_draw_id`),
  CONSTRAINT `orders_gameId_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `o_games` (`o_game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_orders_id
  BEFORE INSERT ON orders 
  FOR EACH ROW
  SET new.order_id = uuid();


-- Table structure for table `orders_numbers`
DROP TABLE IF EXISTS orders_numbers ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_numbers` (
  `orders_number_id` varchar(36)  NOT NULL ,
  `order_id` varchar(36) NOT NULL,
  `numbers` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`orders_number_id`),
  KEY `FK_orders_order_id` (`order_id`),
  CONSTRAINT `FK_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_orders_numbers_id
  BEFORE INSERT ON orders_numbers 
  FOR EACH ROW
  SET new.orders_number_id = uuid();

-- Table structure for table `purchases`
DROP TABLE IF EXISTS purchases ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `purchases_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `order_id` varchar(36) NOT NULL,
  `user_id` varchar(36) NOT NULL,
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
  `role_id` varchar(36) NOT NULL ,
  `role_name` varchar(32) UNIQUE  NOT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_roles_id
  BEFORE INSERT ON roles 
  FOR EACH ROW
  SET new.role_id = uuid();


-- Table structure for table `user_roles`
DROP TABLE IF EXISTS user_roles ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_role_id` varchar(36) NOT NULL ,
  `user_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
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
  `user_statuses_id` varchar(36) NOT NULL,
  `user_statuses_name` varchar(12) UNIQUE NOT NULL,
  PRIMARY KEY (`user_statuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_user_statuses_id
  BEFORE INSERT ON user_statuses 
  FOR EACH ROW
  SET new.user_statuses_id = uuid();


-- Table structure for table `user_status`
DROP TABLE IF EXISTS user_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `user_status_id` varchar(36) NOT NULL ,
  `user_id` varchar(36) NOT NULL,
  `user_statuses_id` varchar(36) NOT NULL,
  PRIMARY KEY (`user_status_id`),
  KEY `user_status_ibfk_1` (`user_id`),
  KEY `user_status_ibfk_2` (`user_statuses_id`),
  CONSTRAINT `user_status_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `user_status_ibfk_2` FOREIGN KEY (`user_statuses_id`) REFERENCES `user_statuses` (`user_statuses_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;




-- Table structure for table `permissions`
DROP TABLE IF EXISTS permissions ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `permission_id` varchar(36) NOT NULL,
  `alias` varchar(25) UNIQUE NOT NULL,
  `description` varchar(80) NOT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_permissions_id
  BEFORE INSERT ON permissions 
  FOR EACH ROW
  SET new.permission_id = uuid();
  


-- Table structure for table `role_permissions`
DROP TABLE IF EXISTS role_permissions ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `role_permission_id` varchar(36) NOT NULL ,
  `role_id` varchar(36) NOT NULL,
  `permission_id` varchar(36) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`role_permission_id`),
  KEY `role_id` (`role_id`),
  KEY `permission_id` (`permission_id`),
  CONSTRAINT `role_permissions_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`),
  CONSTRAINT `role_permissions_ibfk_2` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_role_permissions_id
  BEFORE INSERT ON role_permissions 
  FOR EACH ROW
  SET new.role_permission_id = uuid();



-- Table structure for table `payment_purchases`
DROP TABLE IF EXISTS payment_purchases ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purchases` (
  `payment_purchase_Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(36) NOT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `date_payment` datetime DEFAULT NULL,
  `payment_id` varchar(255) DEFAULT NULL,
  `confirm_id` varchar(255) DEFAULT NULL,
  `cancelled` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`payment_purchase_Id`),
  KEY `FK_payment_user_id` (`user_id`),
  CONSTRAINT `FK_payment_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1900 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `payment_purchase_links`
DROP TABLE IF EXISTS payment_purchase_links ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purchase_links` (
  `payment_purchase_link_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `payment_purchase_Id` bigint(20) NOT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`payment_purchase_link_id`),
  KEY `payment_purchase_Id` (`payment_purchase_Id`),
  CONSTRAINT `payment_purchase_links_ibfk_1` FOREIGN KEY (`payment_purchase_Id`) REFERENCES `payment_purchases` (`payment_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2100 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `payment_purchase_orders`
DROP TABLE IF EXISTS payment_purchase_orders ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purchase_orders` (
  `payment_purchase_order_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_purchase_Id` bigint(20) NOT NULL,
  `order_id` varchar(36) NOT NULL,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`payment_purchase_order_id`),
  KEY `fk_purchase_id_` (`payment_purchase_Id`),
  KEY `FK_payment_orders_order_id` (`order_id`),
  CONSTRAINT `FK_payment_orders_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `fk_purchase_id_` FOREIGN KEY (`payment_purchase_Id`) REFERENCES `payment_purchases` (`payment_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2300 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `payment_purchase_status`
DROP TABLE IF EXISTS payment_purchase_status ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payment_purchase_status` (
  `payment_status_Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `payment_purchase_Id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `datecreated` datetime DEFAULT NULL,
  `datemodified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`payment_status_Id`),
  KEY `payment_purchase_Id` (`payment_purchase_Id`),
  CONSTRAINT `t_payment_purchase_status_ibfk_1` FOREIGN KEY (`payment_purchase_Id`) REFERENCES `payment_purchases` (`payment_purchase_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2500 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


-- Table structure for table `o_games`
DROP TABLE IF EXISTS o_games ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_games` (
  `o_game_id` varchar(36) NOT NULL ,
  `o_game_name` varchar(255) NOT NULL,
  `o_game_numbers` int(11) NOT NULL,
  `o_game_cost` decimal(20,2) NOT NULL,
  `o_game_logo` varchar(255) NOT NULL,
  PRIMARY KEY `o_game_id` (`o_game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_o_games_id
  BEFORE INSERT ON o_games 
  FOR EACH ROW
  SET new.o_game_id = uuid();

-- Table structure for table `o_draws`
DROP TABLE IF EXISTS o_draws ;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_draws` (
  `o_draw_id` varchar(36)  NOT NULL ,
  `o_game_id` varchar(36) NOT NULL,
  `o_draw_jackpot` decimal(20,2) DEFAULT NULL,
  `o_draw_date` date DEFAULT NULL,
  `o_draw_day` varchar(255) DEFAULT NULL,
  `o_draw_vendorCode` varchar(255) DEFAULT NULL,
  PRIMARY KEY `o_draw_id` (`o_draw_id`),
  CONSTRAINT `o_games_ibfk_1` FOREIGN KEY (`o_game_id`) REFERENCES `o_games` (`o_game_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_o_draws_id
  BEFORE INSERT ON o_draws 
  FOR EACH ROW
  SET new.o_draw_id = uuid();



-- Table structure for table `sessions`
DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `session_id` varchar(36)  NOT NULL ,
  `user_id` varchar(36)  NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expires` datetime NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY `session_id` (`session_id`),
  CONSTRAINT `o_user_session_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



CREATE TRIGGER before_insert_sessions_id
  BEFORE INSERT ON sessions 
  FOR EACH ROW
  SET new.session_id = uuid();


-- Table structure for table `recovery_table`
DROP TABLE IF EXISTS `recovery_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recovery_table` (
  `recovery_id` varchar(36) NOT NULL ,
  `user_id` varchar(36) NOT NULL,
  `transactionId` varchar(255) NOT NULL,
  `transactionData` text NOT NULL,
  `status` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`recovery_id`),
  CONSTRAINT `o_user_recovery_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


CREATE TRIGGER before_insert_recovery_table_id
  BEFORE INSERT ON recovery_table 
  FOR EACH ROW
  SET new.recovery_id = uuid();

CREATE TRIGGER  after_insert_users_id AFTER INSERT ON users
  FOR EACH ROW 
    INSERT into user_roles (`user_id`, `role_id`)
values (NEw.user_id, (select role_id FROM roles where role_name='User'));

CREATE TRIGGER  after_insert_users_id1 AFTER INSERT ON users
  FOR EACH ROW 
INSERT into user_status (user_id, user_statuses_id)
values (NEw.user_id, (select user_statuses_id FROM user_statuses where user_statuses_name='Active'));


-- View structure for table `purchase_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW 
`purchase_log` AS select `payment_purchases`.`payment_purchase_Id` AS `purchaseId`,`payment_purchases`.`cost` 
AS `purchaseCost`,`payment_purchases`.`date_payment` AS `purchaseDate`,`payment_purchase_status`.`status` 
AS `purchaseStatus`,(case when (`payment_purchase_status`.`deleted` = 0) then 'Active' else 'Inactive' end) 
AS `purchaseStatusActive`,`payment_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`payment_purchase_status`.`dateModified` 
AS `purchaseStatusModified` from (`payment_purchases` join `payment_purchase_status` on((`payment_purchases`.`payment_purchase_Id` = `payment_purchase_status`.`payment_purchase_Id`))) 
order by `payment_purchases`.`payment_purchase_Id`,`payment_purchase_status`.`dateModified`;


-- View structure for table `purchase_current_status_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW
`purchase_current_status_log` AS select `payment_purchases`.`payment_purchase_Id` AS `purchaseId`,`payment_purchases`.`cost`
AS `purchaseCost`,`payment_purchases`.`date_payment` AS `purchaseDate`,`payment_purchase_status`.`status` AS 
`purchaseStatus`,`payment_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`payment_purchase_status`.`dateModified`
AS `purchaseStatusModified` from (`payment_purchases` join `payment_purchase_status` on((`payment_purchases`.`payment_purchase_Id` = `payment_purchase_status`.`payment_purchase_Id`))) 
where (`payment_purchase_status`.`deleted` = 0) order by `payment_purchases`.`payment_purchase_Id`,`payment_purchase_status`.`dateModified`;



-- View structure for table `active_purchase_log`
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW 
`active_purchase_log` AS select `payment_purchases`.`payment_purchase_Id` AS `purchaseId`,`payment_purchases`.`cost` 
AS `purchaseCost`,`payment_purchases`.`date_payment` AS `purchaseDate`,`payment_purchase_status`.`status` AS 
`purchaseStatus`,`payment_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`payment_purchase_status`.`dateModified` 
AS `purchaseStatusModified` from (`payment_purchases` join `payment_purchase_status` on(((`payment_purchases`.`payment_purchase_Id` = `payment_purchase_status`.`payment_purchase_Id`) 
and (`payment_purchase_status`.`status` <> 'completed')))) order by `payment_purchases`.`payment_purchase_Id`,`payment_purchase_status`.`dateModified`;



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


-- Table structure for table `games_vendor_reqs`
DROP TABLE IF EXISTS `games_vendor_reqs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games_vendor_reqs` (
games_vendor_req_id varchar(36) NOT NULL ,
game_id varchar(36) NOT NULL,
game_numbers_display  int(2) DEFAULT NULL,
luckypick tinyint(1) DEFAULT 1,
autoplay tinyint(1) DEFAULT 1,
game_color varchar(25) DEFAULT 'red',
PRIMARY KEY (`games_vendor_req_id`),
CONSTRAINT `games_vendor_reqs_ibfk_1` FOREIGN KEY (`game_id`) REFERENCES `o_games` (`o_game_id`)
)ENGINE=InnoDB  DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;




CREATE TRIGGER before_insert_users_id
  BEFORE INSERT ON users 
  FOR EACH ROW
  SET new.user_id = uuid();
  
CREATE TRIGGER before_insert_user_status_id
  BEFORE INSERT ON user_status 
  FOR EACH ROW
  SET new.user_status_id = uuid();
  
CREATE TRIGGER before_insert_user_roles_id
  BEFORE INSERT ON user_roles 
  FOR EACH ROW
  SET new.user_role_id = uuid();
  
  
  
  CREATE TRIGGER after_insert_draws_id
  AFTER INSERT ON O_DRAWS 
  FOR EACH ROW
INSERT into Draw_Status (draw_id, status_id, created,deleted)
values (NEw.o_draw_id, (select draw_statuses_id FROM draw_statuses where status_name="Active") ,NOW(), CURRENT_DATE()+2 );




-- CREATE TRIGGER after_insert_users_id
-- AFTER INSERT ON users FOR EACH ROW
-- begin
-- INSERT into user_roles (user_id, role_id)
-- values (NEw.user_id, (select role_id FROM roles where role_name="User"));
-- INSERT into user_status (user_id, user_statuses_id)
-- values (NEw.user_id, (select user_statuses_id FROM user_statuses where user_statuses_name="Active"));
-- end;





drop table if exists dummy_card_details;

CREATE table dummy_card_details(
card_details_uuid varchar(36),
card_firstname varchar(30) not null,
card_lastname varchar(30) not null,
card_type varchar(10) not null,
card_number varchar(16) not null,
card_cvv_number int(3) not null,
card_Street_address varchar(60) not null,
card_apt_bdg varchar(30) not null,
card_city varchar(10) not null,
card_state varchar(20) not null,
card_country varchar(15) not null,
card_zipcode int(6) not null,
card_phone_number int(10) not null,
card_email_id varchar(40) not null,
card_save tinyint(1) DEFAULT 0,
payment_purchase_Id bigint(20),
user_id varchar(36) not null,
PRIMARY KEY (`card_details_uuid`),
UNIQUE KEY card_details_uniquekey1(card_number,card_cvv_number,card_type),
CONSTRAINT `card_details_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
CONSTRAINT `card_details_ibfk_1` FOREIGN KEY (`payment_purchase_Id`) REFERENCES `payment_purchases` (`payment_purchase_Id`)
)ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Dumping data for table `draw_statuses`
--
-- LOCK TABLES `draw_statuses` WRITE;
/*!40000 ALTER TABLE `draw_statuses` DISABLE KEYS */;
INSERT INTO `draw_statuses`  (status_name) VALUES ('Active'),('Inactive'),('Ended');
/*!40000 ALTER TABLE `draw_statuses` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `permissions`
--
-- LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` (alias, description) VALUES ('BET_CREATE','Place a bet'),('BET_VIEW','View bet options');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `roles`
--
-- LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` (role_name) VALUES ('Administrator'),('User'),('Customer Support Representative');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
-- UNLOCK TABLES;



-- Dumping data for table `user_statuses`
--
-- LOCK TABLES `user_statuses` WRITE;
/*!40000 ALTER TABLE `user_statuses` DISABLE KEYS */;
INSERT INTO `user_statuses` (user_statuses_name) VALUES ('Active'),('Locked'),('Deleted');
/*!40000 ALTER TABLE `user_statuses` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `users`
--
-- LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO lottery.users(
 user_name
  ,password
  ,first_name
  ,last_name
  ,gender
  ,email_id
) VALUES (
   "Admin" -- user_name - IN varchar(20)
  ,"adminPass" -- password - IN varchar(25)
  ,"Admin Fname" -- first_name - IN varchar(25)
  ,"Admin Lname" -- last_name - IN varchar(25)
  ,'f'  -- gender - IN varchar(15)
  ,'ssanapureddy@ilts.com'  -- email_id - IN varchar(50)
);


--
-- Dumping data for table `games`
--
-- LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` (game_name, game_numbers ,game_cost, game_logo ) VALUES 
('Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),
('Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),
('Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),
('Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `draws`
--
-- LOCK TABLES `draws` WRITE;
-- /*!40000 ALTER TABLE `draws` DISABLE KEYS */;
-- INSERT INTO `draws` (game_id, draw_jackpot, draw_date) VALUES 
-- ((select game_id from games where games.game_name = "Grand Lotto 6/55"), 73377937.00,'2014-08-15'),
-- ((select game_id from games where games.game_name = "Grand Lotto 6/55"), 12387875.00,'2014-08-16'),
-- ((select game_id from games where games.game_name = "Super Lotto 6/49"), 23581519.00,'2014-08-15'),
-- ((select game_id from games where games.game_name = "Super Lotto 6/49"),84861982.00,'2014-08-16'),
-- ((select game_id from games where games.game_name = "Mega Lotto 6/45"),62800237.00,'2014-08-15'),
-- ((select game_id from games where games.game_name = "Mega Lotto 6/45"),66775157.00,'2014-08-16'),
-- ((select game_id from games where games.game_name = "Lotto 6/42"),29687870.00,'2014-08-15'),
-- ((select game_id from games where games.game_name = "Lotto 6/42"),34456957.00,'2014-08-16'),
-- ((select game_id from games where games.game_name = "Grand Lotto 6/55"),1200000.00,'2014-08-17'),
-- ((select game_id from games where games.game_name = "Grand Lotto 6/55"),1200000.00,'2014-08-17'),
-- ((select game_id from games where games.game_name = "Lotto 6/42"),43000000.00,'2014-08-22');
-- /*!40000 ALTER TABLE `draws` ENABLE KEYS */;
-- UNLOCK TABLES;





INSERT INTO `draws` (game_id, draw_jackpot, draw_date) VALUES 
((select game_id from games where games.game_name = "Grand Lotto 6/55"), 73377937.00,'2014-08-15'),
((select game_id from games where games.game_name = "Grand Lotto 6/55"), 12387875.00,'2014-08-16'),
((select game_id from games where games.game_name = "Super Lotto 6/49"), 23581519.00,'2014-08-15'),
((select game_id from games where games.game_name = "Super Lotto 6/49"), 84861982.00,'2014-08-15'),
((select game_id from games where games.game_name = "Mega Lotto 6/45"), 62800237.00,'2014-08-15'),
((select game_id from games where games.game_name = "Mega Lotto 6/45"), 66775157.00,'2014-08-15'),
((select game_id from games where games.game_name = "Lotto 6/42"), 29687870.00,'2014-08-15'),
((select game_id from games where games.game_name = "Lotto 6/42"), 34456957.00,'2014-08-15'),
((select game_id from games where games.game_name = "Grand Lotto 6/55"), 1200000.00,'2014-08-15'),
((select game_id from games where games.game_name = "Grand Lotto 6/55"), 1200000.00,'2014-08-15'),
((select game_id from games where games.game_name = "Lotto 6/42"), 43000000.00,'2014-08-15');
-- select draw_id ;
-- INSERT INTO `draw_status` (draw_id, status_id, created, deleted ) VALUES ((select UUID()), (select draw_statuses_id from draw_statuses where draw_statuses.status_name = "Active"),'2014-08-13 13:17:35',1);
-- COMMIT;

--
-- Dumping data for table `tickets`
--
-- LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` ( ticket_serialnumber, ticket_timestamp, ticket_cost, ticket_Tax ) VALUES
('a','2014-11-24 07:00:00',10.00,0.00),
('b','2014-11-24 10:00:00',40.00,0.00);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `systems`
--
-- LOCK TABLES `systems` WRITE;
/*!40000 ALTER TABLE `systems` DISABLE KEYS */;
INSERT INTO `systems` VALUES ('10',10),('11',11),('12',12),('5R',5),('6',6),('7',7),('8',8),('9',9);
/*!40000 ALTER TABLE `systems` ENABLE KEYS */;
-- UNLOCK TABLES;




-- CREATE PROCEDURE proc_userroles (IN rolename varchar(32), IN stausname varchar(12))
-- BEGIN
-- INSERT into user_roles (user_id, role_id)
-- values (NEw.user_id, (select role_id FROM roles where role_name="User"))
--
-- Dumping data for table `user_roles`
--
-- LOCK TABLES `user_roles` WRITE;
-- /*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
-- INSERT INTO `user_roles` VALUES (1,,1);
-- /*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `user_status`
--
-- LOCK TABLES `user_status` WRITE;
-- /*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
-- INSERT INTO `user_status` VALUES (1,"1",1);
-- /*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
-- UNLOCK TABLES;



--
-- Dumping data for table `role_permissions`
--
-- LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` (permission_id, role_id, value) VALUES 
((select permission_id FROM permissions where alias ="BET_CREATE"),(select role_id FROM roles where role_name="User"),0),
((select permission_id FROM permissions where alias ="BET_VIEW"),(select role_id FROM roles where role_name="User"),0),
((select permission_id FROM permissions where alias ="BET_CREATE"),(select role_id FROM roles where role_name="Administrator"),1),
((select permission_id FROM permissions where alias ="BET_VIEW"),(select role_id FROM roles where role_name="Administrator"),1),
((select permission_id FROM permissions where alias ="BET_CREATE"),(select role_id FROM roles where role_name="Customer Support Representative"),0),
((select permission_id FROM permissions where alias ="BET_VIEW"),(select role_id FROM roles where role_name="Customer Support Representative"),1);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
-- --UNLOCK TABLES;


--
-- Dumping data for table `o_games`
--
-- LOCK TABLES `o_games` WRITE;
/*!40000 ALTER TABLE `o_games` DISABLE KEYS */;
INSERT INTO `o_games` (o_game_name, o_game_numbers, o_game_cost, o_game_logo) VALUES
('Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),
('Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),
('Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),
('Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `o_games` ENABLE KEYS */;
-- UNLOCK TABLES;

--
-- Dumping data for table `o_draws`
--
-- LOCK TABLES `o_draws` WRITE;
/*!40000 ALTER TABLE `o_draws` DISABLE KEYS */;
INSERT INTO `o_draws` (o_game_id, o_draw_jackpot, o_draw_date, o_draw_day, o_draw_vendorCode) VALUES 
((select o_game_id from o_games where o_games.o_game_name = "Grand Lotto 6/55"),73377937.00,'2014-08-15','Tonight','Draw abcd'),
((select o_game_id from o_games where o_games.o_game_name = "Grand Lotto 6/55"),12387875.00,'2014-08-16','Friday','Draw abce'),
((select o_game_id from o_games where o_games.o_game_name = "Super Lotto 6/49"),23581519.00,'2014-08-15','Tonight','Draw abcd'),
((select o_game_id from o_games where o_games.o_game_name = "Super Lotto 6/49"),84861982.00,'2014-08-16','Friday','Draw abce'),
((select o_game_id from o_games where o_games.o_game_name = "Mega Lotto 6/45"),62800237.00,'2014-08-15','Tonight','Draw abcd'),
((select o_game_id from o_games where o_games.o_game_name = "Mega Lotto 6/45"),66775157.00,'2014-08-16','Friday','Draw abce'),
((select o_game_id from o_games where o_games.o_game_name = "Lotto 6/42"),29687870.00,'2014-08-15','Tonight','Draw abcd'),
((select o_game_id from o_games where o_games.o_game_name = "Lotto 6/42"),34456957.00,'2014-08-16','Friday','Draw abce'),
((select o_game_id from o_games where o_games.o_game_name = "Grand Lotto 6/55"),1200000.00,'2014-08-17','','abc'),
((select o_game_id from o_games where o_games.o_game_name = "Grand Lotto 6/55"),1200000.00,'2014-08-17','','abc'),
((select o_game_id from o_games where o_games.o_game_name = "Lotto 6/42"),43000000.00,'2014-08-22','','234');
/*!40000 ALTER TABLE `o_draws` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `sessions`
--
-- LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` (user_id, token, expires, deleted, modified) VALUES 
((select user_id FROM users where users.user_name ="Admin"),'c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35', 1,'2014-08-02 10:10:52'),
((select user_id FROM users where users.user_name ="Admin"),'c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'); 
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
-- UNLOCK TABLES;


--
-- Dumping data for table `recovery_table`
--
-- LOCK TABLES `recovery_table` WRITE;
/*!40000 ALTER TABLE `recovery_table` DISABLE KEYS */;
INSERT INTO `recovery_table` (user_id, transactionId, transactionData, status, created) VALUES
((select user_id FROM users where users.user_name ="Admin"),'0','','Initiate funding request from payment','2014-09-30 16:51:11'),
((select user_id FROM users where users.user_name ="Admin"),'1c4536bc7ba0b5ef7dd436260c959fee52d89282','{\n  \"bets\" : [ {\n    \"id\" : 30,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"10\", \"12\", \"19\", \"27\", \"30\", \"34\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-01 03:49:29');
/*!40000 ALTER TABLE `recovery_table` ENABLE KEYS */;
-- UNLOCK TABLES;

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


CREATE TRIGGER before_insert_games_vendor_reqs_id
  BEFORE INSERT ON games_vendor_reqs 
  FOR EACH ROW
  SET new.games_vendor_req_id = uuid();


-- LOCK TABLES `games_vendor_reqs` WRITE;
/*!40000 ALTER TABLE `games_vendor_reqs` DISABLE KEYS */;
INSERT into games_vendor_reqs (game_id, game_numbers_display, luckypick, autoplay, game_color ) values 
((select o_game_id from o_games where o_games.o_game_name = "Grand Lotto 6/55"), 11, 1,0,'red'),
((select o_game_id from o_games where o_games.o_game_name = "Super Lotto 6/49"), 7, 1,1,'blue'),
((select o_game_id from o_games where o_games.o_game_name = "Mega Lotto 6/45"), 9, 0,1,'yellow'),
((select o_game_id from o_games where o_games.o_game_name = "Lotto 6/42"), 6, 0,0,'maroon');
/*!40000 ALTER TABLE `games_vendor_reqs` ENABLE KEYS */;
-- UNLOCK TABLES;

drop TRIGGER before_insert_games_vendor_reqs_id;
drop TRIGGER before_insert_draw_statuses_id;
drop TRIGGER before_insert_games_id;
drop TRIGGER before_insert_draws_id;
drop TRIGGER before_insert_draw_status;
drop TRIGGER before_insert_tickets_id;
drop TRIGGER before_insert_orders_id;
drop TRIGGER before_insert_orders_numbers_id;
drop TRIGGER before_insert_roles_id;
drop TRIGGER before_insert_user_statuses_id;
drop TRIGGER before_insert_permissions_id;
drop TRIGGER before_insert_role_permissions_id;
drop TRIGGER before_insert_o_games_id;
drop TRIGGER before_insert_o_draws_id;
drop TRIGGER before_insert_sessions_id;
drop TRIGGER before_insert_recovery_table_id;
drop TRIGGER before_insert_users_id;
drop TRIGGER before_insert_user_status_id;
drop TRIGGER before_insert_user_roles_id;
drop TRIGGER after_insert_users_id;
drop TRIGGER after_insert_users_id1;
drop TRIGGER after_insert_draws_id;







