-- MySQL dump 10.13  Distrib 5.6.16, for osx10.7 (x86_64)
--
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

--
-- Table structure for table `action_log`
--

DROP TABLE IF EXISTS `action_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `action_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `request` text NOT NULL,
  `description` text NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `action_log`
--

LOCK TABLES `action_log` WRITE;
/*!40000 ALTER TABLE `action_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `action_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `active_purchase_log`
--

DROP TABLE IF EXISTS `active_purchase_log`;
/*!50001 DROP VIEW IF EXISTS `active_purchase_log`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `active_purchase_log` (
  `purchaseId` tinyint NOT NULL,
  `purchaseCost` tinyint NOT NULL,
  `purchaseDate` tinyint NOT NULL,
  `purchaseStatus` tinyint NOT NULL,
  `purchaseStatusCreated` tinyint NOT NULL,
  `purchaseStatusModified` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `bet_selection_numbers`
--

DROP TABLE IF EXISTS `bet_selection_numbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet_selection_numbers` (
  `betSelectionNumberId` bigint(20) NOT NULL AUTO_INCREMENT,
  `betSelectionId` bigint(20) NOT NULL,
  `betSelectionNumber` int(10) NOT NULL,
  PRIMARY KEY (`betSelectionNumberId`),
  KEY `betSelectionId` (`betSelectionId`),
  CONSTRAINT `FK_bet_selection_numbers_bet_selection` FOREIGN KEY (`betSelectionId`) REFERENCES `bet_selections` (`betSelectionId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bet_selection_numbers`
--

LOCK TABLES `bet_selection_numbers` WRITE;
/*!40000 ALTER TABLE `bet_selection_numbers` DISABLE KEYS */;
INSERT INTO `bet_selection_numbers` VALUES (1,1,1),(2,1,2),(3,2,3),(4,2,4);
/*!40000 ALTER TABLE `bet_selection_numbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bet_selections`
--

DROP TABLE IF EXISTS `bet_selections`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bet_selections` (
  `betSelectionId` bigint(20) NOT NULL AUTO_INCREMENT,
  `betId` bigint(20) NOT NULL,
  `betSelectionBonus` int(10) NOT NULL,
  `bet_selection_system` varchar(255) NOT NULL,
  PRIMARY KEY (`betSelectionId`),
  KEY `betId` (`betId`),
  KEY `bet_selection_system` (`bet_selection_system`),
  CONSTRAINT `FK_bet_selection_bet_id` FOREIGN KEY (`betId`) REFERENCES `bets` (`betId`),
  CONSTRAINT `FK_bet_selection_system` FOREIGN KEY (`bet_selection_system`) REFERENCES `systems` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bet_selections`
--

LOCK TABLES `bet_selections` WRITE;
/*!40000 ALTER TABLE `bet_selections` DISABLE KEYS */;
INSERT INTO `bet_selections` VALUES (1,1,0,'6'),(2,1,2,'6'),(3,2,1,'6');
/*!40000 ALTER TABLE `bet_selections` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bets`
--

DROP TABLE IF EXISTS `bets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bets` (
  `betId` bigint(20) NOT NULL AUTO_INCREMENT,
  `drawId` bigint(20) NOT NULL,
  `userId` bigint(20) NOT NULL,
  `betCost` decimal(10,2) NOT NULL,
  `betTax` decimal(10,2) NOT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`betId`),
  KEY `drawId` (`drawId`),
  KEY `userId` (`userId`),
  CONSTRAINT `FK_bet_user_id` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bets`
--

LOCK TABLES `bets` WRITE;
/*!40000 ALTER TABLE `bets` DISABLE KEYS */;
INSERT INTO `bets` VALUES (1,1,2,40.00,0.00,0),(2,1,2,80.00,16.00,0),(3,2,2,30.00,5.00,0);
/*!40000 ALTER TABLE `bets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draw_status`
--

DROP TABLE IF EXISTS `draw_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draw_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `drawId` bigint(20) NOT NULL,
  `statusId` bigint(20) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `drawId` (`drawId`,`statusId`),
  KEY `statusId` (`statusId`),
  CONSTRAINT `FK_draw_status_detail_id` FOREIGN KEY (`statusId`) REFERENCES `draw_statuses` (`id`),
  CONSTRAINT `FK_draw_status_draw_id` FOREIGN KEY (`drawId`) REFERENCES `draws` (`drawId`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draw_status`
--

LOCK TABLES `draw_status` WRITE;
/*!40000 ALTER TABLE `draw_status` DISABLE KEYS */;
INSERT INTO `draw_status` VALUES (1,1,2,'2014-08-13 13:17:35',1),(2,1,3,'2014-08-13 13:24:28',1),(3,1,4,'2014-08-13 13:24:31',1),(4,1,2,'2014-08-13 13:24:33',1),(5,2,2,'2014-08-13 13:25:08',0),(6,3,2,'2014-08-13 13:25:08',1),(7,4,2,'2014-08-13 13:25:13',0),(8,5,2,'2014-08-13 13:25:13',0),(9,6,2,'2014-08-13 13:25:20',0),(10,7,2,'2014-08-13 13:25:20',0),(11,8,2,'2014-08-13 13:25:25',1),(12,8,3,'2014-08-13 13:25:33',1),(13,8,2,'2014-08-13 13:25:35',0),(14,1,4,'2014-08-13 13:31:36',1),(15,1,2,'2014-08-13 13:31:42',0),(16,3,3,'2014-08-13 14:30:25',1),(17,3,2,'2014-08-13 14:30:29',0),(18,14,3,'2014-08-15 17:29:43',1),(19,14,2,'2014-08-15 17:29:52',0),(20,15,3,'2014-08-15 17:31:54',1),(21,15,2,'2014-08-17 16:45:41',1),(22,15,3,'2014-08-18 17:02:27',0);
/*!40000 ALTER TABLE `draw_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draw_statuses`
--

DROP TABLE IF EXISTS `draw_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draw_statuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draw_statuses`
--

LOCK TABLES `draw_statuses` WRITE;
/*!40000 ALTER TABLE `draw_statuses` DISABLE KEYS */;
INSERT INTO `draw_statuses` VALUES (2,'Active'),(3,'Inactive'),(4,'Ended');
/*!40000 ALTER TABLE `draw_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `draws`
--

DROP TABLE IF EXISTS `draws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `draws` (
  `drawId` bigint(20) NOT NULL AUTO_INCREMENT,
  `gameId` bigint(20) NOT NULL,
  `drawJackpot` decimal(10,2) NOT NULL,
  `drawDate` date NOT NULL,
  PRIMARY KEY (`drawId`),
  KEY `gameId` (`gameId`),
  CONSTRAINT `FK_draw_game_id` FOREIGN KEY (`gameId`) REFERENCES `games` (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `draws`
--

LOCK TABLES `draws` WRITE;
/*!40000 ALTER TABLE `draws` DISABLE KEYS */;
INSERT INTO `draws` VALUES (1,1,73377937.00,'2014-08-15'),(2,1,12387875.00,'2014-08-16'),(3,2,23581519.00,'2014-08-15'),(4,2,84861982.00,'2014-08-16'),(5,3,62800237.00,'2014-08-15'),(6,3,66775157.00,'2014-08-16'),(7,4,29687870.00,'2014-08-15'),(8,4,34456957.00,'2014-08-16'),(13,2,1200000.00,'2014-08-17'),(14,2,1200000.00,'2014-08-17'),(15,4,43000000.00,'2014-08-22');
/*!40000 ALTER TABLE `draws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `games`
--

DROP TABLE IF EXISTS `games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `games` (
  `gameId` bigint(20) NOT NULL AUTO_INCREMENT,
  `gameName` varchar(255) NOT NULL,
  `gameNumbers` int(10) NOT NULL,
  `gameCost` decimal(10,2) NOT NULL,
  `gameLogo` varchar(255) NOT NULL,
  PRIMARY KEY (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `games`
--

LOCK TABLES `games` WRITE;
/*!40000 ALTER TABLE `games` DISABLE KEYS */;
INSERT INTO `games` VALUES (1,'Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),(2,'Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),(3,'Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),(4,'Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languageSerialized`
--

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

--
-- Dumping data for table `languageSerialized`
--

LOCK TABLES `languageSerialized` WRITE;
/*!40000 ALTER TABLE `languageSerialized` DISABLE KEYS */;
INSERT INTO `languageSerialized` VALUES (1,'en','English','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Games\",\"RESULTS\":\"Results\",\"HOW_TO_PLAY\":\"How to Play\",\"BUY_LOTTO\":\"Buy Lotto\",\"ABOUT\":\"About\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Purchases\",\"APP_SETTINGS\":\"Application Settings\",\"REPORTS\":\"Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Announcements\",\"MORE_ANNOUNCEMENTS\":\"More Announcements\",\"RESULTS\":\"Results\",\"WINNERS\":\"{{result.date}} {{result.winners}} winner(s)\"},\"RESULTS\":{\"GAMES\":\"Games\",\"MORE_RESULTS\":\"More {{game.name}} Results\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Choose a Game\",\"CHOOSE_YOUR_DRAW\":\"Choose Your Draw\",\"CHOOSE_YOUR_SYSTEM\":\"Choose Your System\",\"CHOOSE_YOUR_NUMBERS\":\"Choose Your Numbers\"},\"BUTTONS\":{\"RESET\":\"Reset\",\"ADD_TO_CART\":\"Add to Cart\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Selected Numbers\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Orders\",\"NO_ORDERS\":\"No orders\",\"REMOVE\":\"Remove\",\"DRAWS\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"BUY_NOW\":\"Buy Now\"},\"PURCHASES\":{\"DETAILS\":\"Details\",\"GAME\":\"Game\",\"DRAW\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"AMOUNT\":\"Amount\"}}'),(2,'tg','Tagalog','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Laro\",\"RESULTS\":\"Resulta\",\"HOW_TO_PLAY\":\"Paano Maglaro\",\"BUY_LOTTO\":\"Bumili\",\"ABOUT\":\"Tungkol sa Lotto\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Mga Bilihin\",\"APP_SETTINGS\":\"Mga Settings\",\"REPORTS\":\"Mga Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Mga Paunawa\",\"MORE_ANNOUNCEMENTS\":\"Higit Pa\",\"RESULTS\":\"Mga Resulta\",\"WINNERS\":\"{{result.date}} {{result.winners}} na panalo\"},\"RESULTS\":{\"GAMES\":\"Mga Laro\",\"MORE_RESULTS\":\"Higit pa na Resulta ng {{game.name}}\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Pumili ng Laro\",\"CHOOSE_YOUR_DRAW\":\"Pumili ng Araw\",\"CHOOSE_YOUR_SYSTEM\":\"Pumili ng Sistema\",\"CHOOSE_YOUR_NUMBERS\":\"Pumili ng Numero\"},\"BUTTONS\":{\"RESET\":\"I-reset\",\"ADD_TO_CART\":\"Idagdag sa order\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Mga Piniling Numero\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Mga Order\",\"NO_ORDERS\":\"Walang order\",\"REMOVE\":\"Tanggalin\",\"DRAWS\":\"Araw\",\"SYSTEM\":\"Sistema\",\"NUMBERS\":\"Numero\",\"BUY_NOW\":\"Bilhin na\"},\"PURCHASES\":{\"DETAILS\":\"Mga Detalye\",\"GAME\":\"Laro\",\"DRAW\":\"Araw\",\"SYSTEM\":\"Sistema\",\"NUMBERS\":\"Numero\",\"AMOUNT\":\"Halaga\"}}'),(3,'Test','test','{\"PAGE_HEADINGS\":{\"HOME\":\"Home\",\"GAMES\":\"Games\",\"RESULTS\":\"Results\",\"HOW_TO_PLAY\":\"How to Play\",\"BUY_LOTTO\":\"Buy Lotto\",\"ABOUT\":\"About\",\"FAQ\":\"FAQ\'s\",\"PURCHASES\":\"Purchases\",\"APP_SETTINGS\":\"Application Settings\",\"REPORTS\":\"Reports\"},\"HOME\":{\"ANNOUNCEMENTS\":\"Announcements\",\"MORE_ANNOUNCEMENTS\":\"More Announcements\",\"RESULTS\":\"Results\",\"WINNERS\":\"{{result.date}} {{result.winners}} winner(s)\"},\"RESULTS\":{\"GAMES\":\"Games\",\"MORE_RESULTS\":\"More {{game.name}} Results\"},\"BUY_LOTTO\":{\"STEPS\":{\"CHOOSE_A_GAME\":\"Choose a Game\",\"CHOOSE_YOUR_DRAW\":\"Choose Your Draw\",\"CHOOSE_YOUR_SYSTEM\":\"Choose Your System\",\"CHOOSE_YOUR_NUMBERS\":\"Choose Your Numbers\"},\"BUTTONS\":{\"RESET\":\"Reset\",\"ADD_TO_CART\":\"Add to Cart\"},\"BODY\":{\"SELECTED_NUMBERS\":\"Selected Numbers\"}},\"SHOPPING_CART\":{\"ORDERS\":\"Orders\",\"NO_ORDERS\":\"No orders\",\"REMOVE\":\"Remove\",\"DRAWS\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"BUY_NOW\":\"Buy Now\"},\"PURCHASES\":{\"DETAILS\":\"Details\",\"GAME\":\"Game\",\"DRAW\":\"Draw\",\"SYSTEM\":\"System\",\"NUMBERS\":\"Numbers\",\"AMOUNT\":\"Amount\"}}');
/*!40000 ALTER TABLE `languageSerialized` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_entries`
--

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

--
-- Dumping data for table `language_entries`
--

LOCK TABLES `language_entries` WRITE;
/*!40000 ALTER TABLE `language_entries` DISABLE KEYS */;
INSERT INTO `language_entries` VALUES (1,1,2,'Home'),(2,2,2,'Bahay');
/*!40000 ALTER TABLE `language_entries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `language_fields`
--

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

--
-- Dumping data for table `language_fields`
--

LOCK TABLES `language_fields` WRITE;
/*!40000 ALTER TABLE `language_fields` DISABLE KEYS */;
INSERT INTO `language_fields` VALUES (1,0,'PAGE_HEADINGS'),(2,1,'HOME');
/*!40000 ALTER TABLE `language_fields` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

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
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'en','English'),(2,'tg','Tagalog');
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `o_bets`
--

DROP TABLE IF EXISTS `o_bets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_bets` (
  `betId` bigint(20) NOT NULL AUTO_INCREMENT,
  `lottoGame` varchar(255) NOT NULL,
  `drawDay` varchar(255) NOT NULL,
  `numberOfDraws` int(10) NOT NULL,
  `numberOfBetlines` int(10) NOT NULL,
  `selectionType` varchar(255) NOT NULL,
  `numberOfSelections` int(10) NOT NULL,
  `selections` text NOT NULL,
  `bonusSelection` int(10) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ticketSerialNumber` varchar(255) NOT NULL,
  `ticketCost` decimal(10,2) NOT NULL,
  `tax` decimal(10,2) NOT NULL,
  PRIMARY KEY (`betId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `o_bets`
--

LOCK TABLES `o_bets` WRITE;
/*!40000 ALTER TABLE `o_bets` DISABLE KEYS */;
INSERT INTO `o_bets` VALUES (1,'Lotto 6/42','Monday',1,2,'Player Pick',2,'{\n  \"selections\" : [ [ 1, 2, 5, 10, 12, 20 ], [ 21, 22, 30, 32, 40, 45 ] ]\n}',9,'2014-10-22 17:04:54','0123-4567=89AB-CDEF',40.00,0.00),(2,'Lotto 6/42','Monday',1,2,'Player Pick',2,'{\n  \"selections\" : [ [ 1, 2, 5, 10, 12, 20 ], [ 21, 22, 30, 32, 40, 45 ] ]\n}',9,'2014-10-27 09:10:14','7019-0419=TWT5-SFDD',40.00,0.00);
/*!40000 ALTER TABLE `o_bets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `o_draws`
--

DROP TABLE IF EXISTS `o_draws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_draws` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gameId` bigint(20) DEFAULT NULL,
  `jackpot` decimal(20,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `vendorCode` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `o_draws`
--

LOCK TABLES `o_draws` WRITE;
/*!40000 ALTER TABLE `o_draws` DISABLE KEYS */;
INSERT INTO `o_draws` VALUES (1,1,73377937.00,'2014-08-15','Tonight','Draw abcd'),(2,1,12387875.00,'2014-08-16','Friday','Draw abce'),(3,2,23581519.00,'2014-08-15','Tonight','Draw abcd'),(4,2,84861982.00,'2014-08-16','Friday','Draw abce'),(5,3,62800237.00,'2014-08-15','Tonight','Draw abcd'),(6,3,66775157.00,'2014-08-16','Friday','Draw abce'),(7,4,29687870.00,'2014-08-15','Tonight','Draw abcd'),(8,4,34456957.00,'2014-08-16','Friday','Draw abce'),(13,2,1200000.00,'2014-08-17','','abc'),(14,2,1200000.00,'2014-08-17','','abc'),(15,4,43000000.00,'2014-08-22','','234');
/*!40000 ALTER TABLE `o_draws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `o_games`
--

DROP TABLE IF EXISTS `o_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `o_games` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `numbers` int(11) DEFAULT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `logo` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `o_games`
--

LOCK TABLES `o_games` WRITE;
/*!40000 ALTER TABLE `o_games` DISABLE KEYS */;
INSERT INTO `o_games` VALUES (1,'Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),(2,'Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),(3,'Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),(4,'Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `o_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gameId` bigint(20) DEFAULT NULL,
  `drawId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) NOT NULL,
  `system` varchar(255) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  `active` tinyint(1) DEFAULT '1',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,1,0,'6',20,1,1),(2,1,1,0,'6',20,0,1),(3,1,1,1,'6',20,0,0),(4,2,3,1,'6',20,0,0),(5,1,2,1,'6',20,0,0),(6,3,5,1,'6',10,0,0),(7,1,1,1,'6',40,0,0),(8,4,7,1,'7',70,0,0),(9,2,3,1,'8',560,0,1),(10,1,1,1,'6',20,1,1),(11,4,8,2,'6',10,0,0),(12,4,7,2,'6',10,0,0),(13,2,3,2,'7',280,0,0),(14,1,1,1,'6',20,1,1),(15,1,2,4,'6',20,0,0),(16,1,2,4,'6',20,0,0),(17,1,2,2,'6',20,0,0),(18,1,2,2,'6',20,1,1),(19,1,2,2,'6',20,1,1),(20,1,2,2,'6',20,1,1),(21,1,2,2,'6',20,1,1),(22,1,2,2,'6',20,1,1),(23,1,2,4,'6',20,0,0),(24,2,3,4,'7',140,0,0),(25,2,4,4,'7',140,1,1),(26,3,5,4,'6',10,0,0),(27,1,1,4,'6',40,0,0),(28,1,2,4,'6',40,1,1),(29,1,2,4,'6',20,0,0),(30,1,2,4,'6',20,0,0),(31,1,2,4,'6',20,0,0),(32,1,2,4,'6',20,0,0),(33,1,2,4,'6',20,0,0),(34,1,2,2,'6',20,0,0),(35,1,2,2,'6',20,0,0),(36,1,2,2,'6',20,0,1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders_numbers`
--

DROP TABLE IF EXISTS `orders_numbers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `orders_numbers` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` bigint(20) DEFAULT NULL,
  `numbers` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders_numbers`
--

LOCK TABLES `orders_numbers` WRITE;
/*!40000 ALTER TABLE `orders_numbers` DISABLE KEYS */;
INSERT INTO `orders_numbers` VALUES (1,2,'7,12,15,18,41,54,'),(2,3,'7,8,23,36,47,54,'),(3,4,'6,12,21,26,34,41,'),(4,5,'13,19,23,41,42,44,'),(5,6,'22,23,24,30,33,37,'),(6,7,'21,30,39,46,49,55,'),(7,7,'2,15,22,37,45,47,'),(8,8,'2,5,17,21,23,37,40,'),(9,9,'7,8,11,37,44,47,48,49,'),(10,10,'10,14,39,42,47,50,'),(11,12,'17,23,27,29,32,40,'),(12,11,'17,23,27,29,32,40,'),(13,13,'4,6,23,26,28,36,40,'),(14,13,'13,17,19,22,24,39,49,'),(15,14,'2,3,4,6,28,53,'),(16,15,'9,16,19,39,40,42,'),(17,16,'9,10,13,31,35,49,'),(18,17,'5,6,7,9,48,53,'),(19,18,'6,15,17,27,32,38,'),(20,19,'6,15,17,27,32,38,'),(21,20,'2,7,20,30,35,41,'),(22,21,'3,5,21,34,45,50,'),(23,22,'14,16,24,27,38,52,'),(24,23,'2,23,25,37,43,49,'),(25,24,'16,23,25,31,37,45,49,'),(26,25,'16,23,25,31,37,45,49,'),(27,26,'23,28,31,34,42,44,'),(28,28,'11,20,26,50,51,52,'),(29,27,'11,20,26,50,51,52,'),(30,28,'1,14,20,31,46,53,'),(31,27,'1,14,20,31,46,53,'),(32,29,'6,7,9,10,14,21,'),(33,30,'10,12,19,27,30,34,'),(34,31,'3,28,30,40,42,50,'),(35,32,'5,31,36,43,53,54,'),(36,33,'4,5,24,38,41,53,'),(37,34,'10,20,29,35,40,52,'),(38,35,'1,3,6,35,40,48,'),(39,36,'16,17,21,32,42,51,');
/*!40000 ALTER TABLE `orders_numbers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal_purchase_links`
--

DROP TABLE IF EXISTS `paypal_purchase_links`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_links` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `purchaseId` bigint(20) DEFAULT NULL,
  `href` varchar(255) DEFAULT NULL,
  `rel` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_purchase_links`
--

LOCK TABLES `paypal_purchase_links` WRITE;
/*!40000 ALTER TABLE `paypal_purchase_links` DISABLE KEYS */;
INSERT INTO `paypal_purchase_links` VALUES (1,2,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-0GS89798SG3766638KPLW5OY','self','GET',0),(2,2,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-33H294840L140331T','approval_url','REDIRECT',0),(3,2,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-0GS89798SG3766638KPLW5OY/execute','execute','POST',0),(4,3,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-32G27690MB578701EKPRCY6Y','self','GET',0),(5,3,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-93L84160G56260024','approval_url','REDIRECT',0),(6,3,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-32G27690MB578701EKPRCY6Y/execute','execute','POST',0),(7,4,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-50905350BP380272YKPRC6XQ','self','GET',0),(8,4,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5AJ570277U556841D','approval_url','REDIRECT',0),(9,4,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-50905350BP380272YKPRC6XQ/execute','execute','POST',0),(10,5,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-30D61900WP5315354KPRDARA','self','GET',0),(11,5,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-0ML46742HL069450L','approval_url','REDIRECT',0),(12,5,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-30D61900WP5315354KPRDARA/execute','execute','POST',0),(13,6,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3PY07479ET321782YKPRDBYA','self','GET',0),(14,6,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3UW11250WU1206902','approval_url','REDIRECT',0),(15,6,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3PY07479ET321782YKPRDBYA/execute','execute','POST',0),(16,7,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-38G57221NS131703PKPRDDRQ','self','GET',0),(17,7,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-47M95124Y7639012E','approval_url','REDIRECT',0),(18,7,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-38G57221NS131703PKPRDDRQ/execute','execute','POST',0),(19,8,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-2JV51921YV7738626KPRDGQI','self','GET',0),(20,8,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-4G050645EH817752D','approval_url','REDIRECT',0),(21,8,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-2JV51921YV7738626KPRDGQI/execute','execute','POST',0),(22,9,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3YH47423D58340249KPRDJBY','self','GET',0),(23,9,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5B717485UW1050008','approval_url','REDIRECT',0),(24,9,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3YH47423D58340249KPRDJBY/execute','execute','POST',0),(25,10,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-9FT91980KX981162AKPRDJ7Y','self','GET',0),(26,10,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-56F69945F8722574B','approval_url','REDIRECT',0),(27,10,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-9FT91980KX981162AKPRDJ7Y/execute','execute','POST',0),(28,11,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8F064993FT769623CKPRYFDY','self','GET',0),(29,11,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-0G434919E1247972G','approval_url','REDIRECT',0),(30,11,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8F064993FT769623CKPRYFDY/execute','execute','POST',0),(31,12,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-1WJ19738E5450530YKPRYRDQ','self','GET',0),(32,12,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-8F251534UH323983F','approval_url','REDIRECT',0),(33,12,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-1WJ19738E5450530YKPRYRDQ/execute','execute','POST',0),(34,13,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-60020405M3296103TKPRZLBY','self','GET',0),(35,13,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-9XN11210FH5935908','approval_url','REDIRECT',0),(36,13,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-60020405M3296103TKPRZLBY/execute','execute','POST',0),(37,14,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-07470037R3045925AKPRZRNA','self','GET',0),(38,14,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-8WP03014TS900420B','approval_url','REDIRECT',0),(39,14,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-07470037R3045925AKPRZRNA/execute','execute','POST',0),(40,15,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-84030441UW014881BKPRZZIA','self','GET',0),(41,15,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-05121950BG390304A','approval_url','REDIRECT',0),(42,15,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-84030441UW014881BKPRZZIA/execute','execute','POST',0),(43,16,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-64340017CG976272JKPSE35Q','self','GET',0),(44,16,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3AG39065BB6144210','approval_url','REDIRECT',0),(45,16,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-64340017CG976272JKPSE35Q/execute','execute','POST',0),(46,17,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-6LB34138HR082830TKPTPLOY','self','GET',0),(47,17,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3AG67822E62968535','approval_url','REDIRECT',0),(48,17,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-6LB34138HR082830TKPTPLOY/execute','execute','POST',0),(49,18,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-2YE79824FP5277635KPTPMXA','self','GET',0),(50,18,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-06R41155D9992273A','approval_url','REDIRECT',0),(51,18,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-2YE79824FP5277635KPTPMXA/execute','execute','POST',0),(52,19,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-23R92823UW685441NKPVUCLA','self','GET',0),(53,19,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-0EN70120GM573391W','approval_url','REDIRECT',0),(54,19,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-23R92823UW685441NKPVUCLA/execute','execute','POST',0),(55,22,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4XM646764P796124DKP7T3FI','self','GET',0),(56,22,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3SN569310C865812D','approval_url','REDIRECT',0),(57,22,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4XM646764P796124DKP7T3FI/execute','execute','POST',0),(58,23,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-78269825HL965831RKP7ULWA','self','GET',0),(59,23,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-8GN930632S089705X','approval_url','REDIRECT',0),(60,23,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-78269825HL965831RKP7ULWA/execute','execute','POST',0),(61,24,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-1AK15564CJ503164GKQRDECI','self','GET',0),(62,24,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-89B89387U6099461Y','approval_url','REDIRECT',0),(63,24,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-1AK15564CJ503164GKQRDECI/execute','execute','POST',0),(64,25,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8LR28838SW486864UKQRDLMQ','self','GET',0),(65,25,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-7SD588766Y0060400','approval_url','REDIRECT',0),(66,25,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8LR28838SW486864UKQRDLMQ/execute','execute','POST',0),(67,26,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-6TS72788GY861213WKQRDRJQ','self','GET',0),(68,26,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-6WY91092D8792223Y','approval_url','REDIRECT',0),(69,26,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-6TS72788GY861213WKQRDRJQ/execute','execute','POST',0),(70,27,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3GC69929F7913791VKQRFMRI','self','GET',0),(71,27,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-1H582244YY243970P','approval_url','REDIRECT',0),(72,27,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3GC69929F7913791VKQRFMRI/execute','execute','POST',0),(73,31,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4A616364G0565950CKQRM2JA','self','GET',0),(74,31,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-17S20025LN642805M','approval_url','REDIRECT',0),(75,31,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4A616364G0565950CKQRM2JA/execute','execute','POST',0),(76,32,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-38Y32753W27081146KQVNYXI','self','GET',0),(77,32,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5E140180D5424045S','approval_url','REDIRECT',0),(78,32,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-38Y32753W27081146KQVNYXI/execute','execute','POST',0),(79,33,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-87772785DH442410VKQVN67Q','self','GET',0),(80,33,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-7B373209473953444','approval_url','REDIRECT',0),(81,33,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-87772785DH442410VKQVN67Q/execute','execute','POST',0),(82,35,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-64C41818D9583313DKQVXRQY','self','GET',0),(83,35,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-9SC98886MN035453F','approval_url','REDIRECT',0),(84,35,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-64C41818D9583313DKQVXRQY/execute','execute','POST',0),(85,34,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-77R11570BU8264022KQVXRVQ','self','GET',0),(86,34,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-30E82389MN527490M','approval_url','REDIRECT',0),(87,34,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-77R11570BU8264022KQVXRVQ/execute','execute','POST',0),(88,36,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-26L262411C1944629KQVXTFA','self','GET',0),(89,36,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-4KM881820L1976822','approval_url','REDIRECT',0),(90,36,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-26L262411C1944629KQVXTFA/execute','execute','POST',0),(91,38,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4TA29049YR534074NKQV2EII','self','GET',0),(92,38,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-2HR17557KW905121E','approval_url','REDIRECT',0),(93,38,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-4TA29049YR534074NKQV2EII/execute','execute','POST',0),(94,39,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-62X49779N9096824EKQWAIWI','self','GET',0),(95,39,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-83L62595YD571125C','approval_url','REDIRECT',0),(96,39,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-62X49779N9096824EKQWAIWI/execute','execute','POST',0),(97,40,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3E226649CT427811YKQ2NOMA','self','GET',0),(98,40,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-93F19383E8506713R','approval_url','REDIRECT',0),(99,40,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3E226649CT427811YKQ2NOMA/execute','execute','POST',0),(100,41,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-9M473495DV438001WKRD6GPY','self','GET',0),(101,41,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-8RP576127R077104V','approval_url','REDIRECT',0),(102,41,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-9M473495DV438001WKRD6GPY/execute','execute','POST',0),(103,42,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-75X673933C4935340KRD6HNQ','self','GET',0),(104,42,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-1TU04574V91570911','approval_url','REDIRECT',0),(105,42,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-75X673933C4935340KRD6HNQ/execute','execute','POST',0),(106,43,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3U3360807B262043FKRHAX5I','self','GET',0),(107,43,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-51G12234VU050043S','approval_url','REDIRECT',0),(108,43,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-3U3360807B262043FKRHAX5I/execute','execute','POST',0),(109,44,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8XW50253KP489252SKRRUW7A','self','GET',0),(110,44,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-7UH42490GJ466425P','approval_url','REDIRECT',0),(111,44,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8XW50253KP489252SKRRUW7A/execute','execute','POST',0),(112,45,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-5N211419T1783792EKRS2GCQ','self','GET',0),(113,45,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5HP22751V3273554B','approval_url','REDIRECT',0),(114,45,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-5N211419T1783792EKRS2GCQ/execute','execute','POST',0),(115,46,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8UM55177R6693700PKRS7NLA','self','GET',0),(116,46,'https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3670976145772791J','approval_url','REDIRECT',0),(117,46,'https://api.sandbox.paypal.com/v1/payments/payment/PAY-8UM55177R6693700PKRS7NLA/execute','execute','POST',0);
/*!40000 ALTER TABLE `paypal_purchase_links` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal_purchase_orders`
--

DROP TABLE IF EXISTS `paypal_purchase_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_orders` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `purchaseId` bigint(20) DEFAULT NULL,
  `orderId` bigint(20) DEFAULT NULL,
  `deleted` tinyint(1) DEFAULT '0',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_purchase_orders`
--

LOCK TABLES `paypal_purchase_orders` WRITE;
/*!40000 ALTER TABLE `paypal_purchase_orders` DISABLE KEYS */;
INSERT INTO `paypal_purchase_orders` VALUES (1,2,2,0),(2,3,3,0),(3,3,4,0),(4,4,3,0),(5,4,4,0),(6,5,3,0),(7,5,4,0),(8,6,3,0),(9,6,4,0),(10,7,3,0),(11,7,4,0),(12,8,3,0),(13,8,4,0),(14,9,3,0),(15,9,4,0),(16,10,3,0),(17,10,4,0),(18,11,5,0),(19,12,5,0),(20,13,5,0),(21,14,5,0),(22,15,6,0),(23,16,7,0),(24,16,8,0),(25,17,13,0),(26,17,11,0),(27,17,12,0),(28,18,9,0),(29,19,9,0),(30,20,15,0),(31,21,15,0),(32,22,15,0),(33,23,16,0),(34,24,23,0),(35,25,24,0),(36,26,24,0),(37,26,26,0),(38,27,27,0),(39,28,29,0),(40,29,29,0),(41,30,29,0),(42,31,29,0),(43,32,30,0),(44,33,30,0),(45,34,30,0),(46,35,30,0),(47,36,30,0),(48,37,31,0),(49,38,31,0),(50,39,32,0),(51,40,33,0),(52,41,17,0),(53,42,17,0),(54,43,34,0),(55,44,35,0),(56,45,35,0),(57,46,36,0);
/*!40000 ALTER TABLE `paypal_purchase_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal_purchase_status`
--

DROP TABLE IF EXISTS `paypal_purchase_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchase_status` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `purchaseId` bigint(20) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `dateModified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `deleted` tinyint(1) DEFAULT '0',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_purchase_status`
--

LOCK TABLES `paypal_purchase_status` WRITE;
/*!40000 ALTER TABLE `paypal_purchase_status` DISABLE KEYS */;
INSERT INTO `paypal_purchase_status` VALUES (1,2,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(2,3,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(3,4,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(4,5,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(5,6,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(6,7,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(7,8,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(8,9,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(9,10,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(10,10,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(11,11,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(12,12,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(13,13,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(14,14,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(15,14,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(16,15,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(17,15,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(18,16,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(19,16,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(20,17,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(21,17,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(22,18,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(23,19,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(24,20,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(25,21,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(26,22,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(27,22,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(28,23,'created','2014-09-24 10:51:13','2014-09-24 02:51:13',1),(29,23,'completed','2014-09-24 10:51:13','2014-09-24 02:51:13',0),(30,24,'created','2014-09-24 10:52:30','2014-09-24 02:54:55',1),(31,24,'completed','2014-09-24 10:54:55','2014-09-24 02:54:55',0),(32,25,'created','2014-09-24 11:08:30','2014-09-24 03:08:32',1),(33,25,'checking funding','2014-09-24 11:08:32','2014-09-24 03:40:40',1),(34,26,'created','2014-09-24 11:21:06','2014-09-24 03:21:09',1),(35,26,'checking funding','2014-09-24 11:21:09','2014-09-24 03:22:13',1),(36,26,'completed','2014-09-24 11:22:13','2014-09-24 03:22:13',0),(37,25,'funding cancelled','2014-09-24 11:40:40','2014-09-24 03:40:40',0),(38,27,'created','2014-09-24 13:27:28','2014-09-24 05:27:31',1),(39,27,'checking funding','2014-09-24 13:27:31','2014-09-24 05:30:15',1),(40,27,'completed','2014-09-24 13:30:15','2014-09-24 05:30:15',0),(41,30,'created','2014-09-24 09:52:24','2014-09-24 13:52:29',1),(42,30,'checking funding','2014-09-24 09:52:29','2014-09-24 13:52:29',0),(43,31,'created','2014-09-24 09:54:41','2014-09-24 13:54:44',1),(44,31,'checking funding','2014-09-24 09:54:44','2014-09-24 13:58:50',1),(45,31,'completed','2014-09-24 21:58:50','2014-09-24 13:58:50',0),(46,32,'created','2014-10-01 00:37:44','2014-09-30 16:37:48',1),(47,32,'checking funding','2014-10-01 00:37:48','2014-09-30 16:37:48',0),(48,33,'created','2014-10-01 00:51:07','2014-09-30 16:51:09',1),(49,33,'checking funding','2014-10-01 00:51:09','2014-09-30 16:51:09',0),(50,34,'created','2014-10-01 11:44:30','2014-10-01 03:45:17',1),(51,35,'created','2014-10-01 11:45:01','2014-10-01 03:45:05',1),(52,35,'checking funding','2014-10-01 11:45:05','2014-10-01 03:45:05',0),(53,34,'checking funding','2014-10-01 11:45:17','2014-10-01 03:45:17',0),(54,36,'created','2014-10-01 11:48:30','2014-10-01 03:48:32',1),(55,36,'checking funding','2014-10-01 11:48:32','2014-10-01 03:49:29',1),(56,36,'completed','2014-10-01 11:49:29','2014-10-01 03:49:29',0),(57,37,'created','2014-10-01 14:34:16','2014-10-01 06:34:23',1),(58,37,'checking funding','2014-10-01 14:34:23','2014-10-01 06:34:23',0),(59,38,'created','2014-10-01 14:41:34','2014-10-01 06:41:36',1),(60,38,'checking funding','2014-10-01 14:41:36','2014-10-01 06:42:43',1),(61,38,'completed','2014-10-01 14:42:43','2014-10-01 06:42:43',0),(62,39,'created','2014-10-01 21:40:37','2014-10-01 13:40:39',1),(63,39,'checking funding','2014-10-01 21:40:39','2014-10-01 13:41:31',1),(64,39,'completed','2014-10-01 21:41:31','2014-10-01 13:41:31',0),(65,40,'created','2014-10-08 14:18:12','2014-10-08 06:18:22',1),(66,40,'checking funding','2014-10-08 14:18:22','2014-10-08 06:19:32',1),(67,40,'completed','2014-10-08 14:19:32','2014-10-08 06:19:32',0),(68,41,'created','2014-10-23 01:02:50','2014-10-22 17:02:54',1),(69,41,'checking funding','2014-10-23 01:02:54','2014-10-22 17:02:54',0),(70,42,'created','2014-10-23 01:04:50','2014-10-22 17:04:53',1),(71,42,'checking funding','2014-10-23 01:04:53','2014-10-22 17:05:39',1),(72,42,'funding completed','2014-10-23 01:05:39','2014-10-22 17:05:39',0),(73,43,'created','2014-10-27 20:10:08','2014-10-27 09:10:12',1),(74,43,'checking funding','2014-10-27 20:10:12','2014-10-27 09:10:56',1),(75,43,'funding completed','2014-10-27 20:10:56','2014-10-27 09:10:56',0),(76,44,'created','2014-11-12 22:58:48','2014-11-12 11:58:51',1),(77,44,'checking funding','2014-11-12 22:58:51','2014-11-12 11:58:51',0),(78,45,'created','2014-11-14 17:36:54','2014-11-14 06:36:57',1),(79,45,'checking funding','2014-11-14 17:36:57','2014-11-14 06:40:35',1),(80,45,'funding completed','2014-11-14 17:40:35','2014-11-14 06:40:35',0),(81,46,'created','2014-11-14 23:33:40','2014-11-14 12:33:48',1),(82,46,'checking funding','2014-11-14 23:33:48','2014-11-14 12:33:48',0);
/*!40000 ALTER TABLE `paypal_purchase_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paypal_purchases`
--

DROP TABLE IF EXISTS `paypal_purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `paypal_purchases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `date` datetime DEFAULT NULL,
  `paypalId` varchar(255) DEFAULT NULL,
  `confirmId` varchar(255) DEFAULT NULL,
  `cancelId` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paypal_purchases`
--

LOCK TABLES `paypal_purchases` WRITE;
/*!40000 ALTER TABLE `paypal_purchases` DISABLE KEYS */;
INSERT INTO `paypal_purchases` VALUES (1,NULL,20.00,'2014-08-18 00:00:00',NULL,'a3d3012506fe24f9dc93eca3ef4088db8532b82f','1125a3cf4b83e29fb83861cab6460ee405dcc2f2'),(2,NULL,20.00,'2014-08-19 00:00:00',NULL,'667ead28084fcd9a4305d14477678eaf90d7ca24','4d9052cdbaeadfad3e0838ee0817f2b1b49e8a1d'),(3,NULL,40.00,'2014-08-20 00:00:00',NULL,'fbe8e38a1f761271c981c59f3daf4fd33a3b2deb','99e68bdafc85b298acf0d508b44f4876d95c945b'),(4,NULL,40.00,'2014-08-21 00:00:00',NULL,'35da42289bab7422751ec711bce3cf800a38b22c','6fdafa8f345bfe068e25edc03a2acc565f8c50b6'),(5,NULL,40.00,'2014-08-22 00:00:00',NULL,'1ef5123d0305182e50d8ea1bbb453ddb5199789c','6f500b5f36f3958f148f68fd54813af16a4140e6'),(6,NULL,40.00,'2014-08-23 00:00:00',NULL,'b6e5248d65e3481375e3af12176ef1a979375e3e','44dfc48ab49099855eadcdd0ef2ca67f7361ef9d'),(7,NULL,40.00,'2014-08-24 00:00:00',NULL,'ed572c27e874674617686f679ba03261ba6788bc','9f5ada51f36cf2d6554f2880fccf1c6768d73361'),(8,NULL,40.00,'2014-08-25 00:00:00',NULL,'9f8a6842b593119e4c9b3424b9bb7979a4d86572','44d56f6c9e76606ee99eb0e0c292ed6ab3edac39'),(9,NULL,40.00,'2014-08-26 00:00:00',NULL,'368e9daaa10f02f49deafec2097176d8b53ff0a5','219edb6b8ea8c0955b8fb5d17a3aeb7c74397a34'),(10,NULL,40.00,'2014-08-19 00:00:00',NULL,'9e317271a76d795c69474e57ebc60118701945eb','7b89c72c7d488e9a2b6569b1bdd27abe1902a412'),(11,NULL,20.00,'2014-08-20 00:00:00',NULL,'fe9156ec52ea986d41f4e35298aff648a132c154','3dc1d0de0410f265a84b16294cb3192e05e500bc'),(12,NULL,20.00,'2014-08-21 00:00:00',NULL,'396b6f600e8af0b8ec6ee4c63d0cbebfc85a782d','dbc3ef2103232fe6e6679e83730ce7c5d2555041'),(13,NULL,20.00,'2014-08-22 00:00:00',NULL,'5870b799c3c5d8800793f0c9930b6dd455861bcb','059af37b2bab95165e96827855c5e6a00acc0bb2'),(14,NULL,20.00,'2014-08-23 00:00:00',NULL,'0f269e5f18d46aa4abc52681fccbd4c1e90e115d','760f5733e4e31ee3ddbad558da376ac2fe173d99'),(15,NULL,10.00,'2014-08-24 00:00:00',NULL,'ccf622d0afc9bafc2a719f37b1e67660d3123c1d','f6c2f625bf436f31c190c6d7c37ee3c65d6994c2'),(16,1,110.00,'2014-08-25 00:00:00',NULL,'4074c9eb85899c8f63a0334bb23a541681b30f21','89b66f04fdc6213b922d68a2ddf70a9c9ddca6b1'),(17,2,300.00,'2014-08-26 00:00:00',NULL,'6eed9dc172333c11ab2042d1d414a0c1ccbc9bd9','84888c227a146dad3b91afa033b20987acd35bcc'),(18,1,560.00,'2014-08-19 00:00:00',NULL,'87876760b50422601d0a7e4bd9da7ac6b47fd8cd','c2492904031f3d840959c73d99b6301d10f73445'),(19,1,560.00,'2014-08-21 00:00:00',NULL,'2dcb84953f1ba998537b6df6c8e47d37381d96c4','3b27ea004abc6d349832f53479fbb94c2c833dd5'),(20,4,20.00,'2014-08-28 00:00:00',NULL,'dcf9cb7d9769a3ad7d0418d33240df552a0ad852','ca45541edf5651d0d7034f5b87c0fdfadc90a12f'),(21,4,20.00,'2014-08-28 00:00:00',NULL,'4c10741d1578ae1fb22802960a74707e81e27988','b96d4716c7b69c8be1c312eccee2ec5240d3d959'),(22,4,20.00,'2014-08-28 00:00:00',NULL,'2f0cdf8f064d2d2df73d63f073c3a90a03bfb2d0','d2b7479b86947499c2b11adc82cbc40b41f940a6'),(23,4,20.00,'2014-08-28 00:00:00',NULL,'0d9313f3e208bdb84a6889ca710833adf48717c5','b7a9847263b7c4bc4c095e3f2d2a7286bb78bd4c'),(24,4,20.00,'2014-09-24 00:00:00',NULL,'14dc2aa6e1ab7dc0cdfa2e19ce8debe897a422c2','6307af199118e4100da970d2b7c4a4447083c6bc'),(25,4,140.00,'2014-09-24 00:00:00',NULL,'983aa0c9c6f958402f571034c42956fd49d40b2c','0140c8a23dc305e2cf2856da1a6743aac42a70fc'),(26,4,150.00,'2014-09-24 00:00:00',NULL,'8140ef824122d468478dd375abde783883483606','d1a28617bf99599e2d54cadacd91f3d50ae00f51'),(27,4,40.00,'2014-09-24 00:00:00',NULL,'29f0298f4b0e51b859f9374b635ac9b3d9e5c620','cb18a8944438fcfe5db741ef6d2ceb72be4cafdc'),(28,4,20.00,'2014-09-24 00:00:00',NULL,'db66ef35e1203b69e1862fa6e740f5892eaade2d','9a393bf50620057fb673ba09d0e2d4806fc8e33d'),(29,4,20.00,'2014-09-24 00:00:00',NULL,'90e1ac91b4294b4f923989fc987703bbc1fa9d52','5e1f481b87f2cf2a3f6e5043d34d39a6577fbd63'),(30,4,20.00,'2014-09-24 00:00:00',NULL,'d3858e7e85a78f5975ec617554c08bcc5b842d59','4d18c711200c5c1111f7fffacc1a5cbcc55024e8'),(31,4,20.00,'2014-09-24 00:00:00',NULL,'99a310cccd02b4887e9b5fabe2f7591a78789c3c','7c4816f24c90496e9febc4f1b6064efabb68f1e8'),(32,4,20.00,'2014-10-01 00:00:00',NULL,'ceaffdaf97cca76e632ff67627868df1efaaf956','d2b77e4bbc5ced099738ca81583b89f61190ab71'),(33,4,20.00,'2014-10-01 00:00:00',NULL,'c8b50a23624644b228d767c63ae6917c97792e46','bbc2edf52671d18f64259192ef5cd0a1f1cdf027'),(34,4,20.00,'2014-10-01 00:00:00',NULL,'06db1cec8d7116b4b0002dfc0e6efa5477f37739','eeaa788cf4e66e1b26114a662523842f74749a79'),(35,4,20.00,'2014-10-01 00:00:00',NULL,'31045eae3031b9139f46b3564f4590d716406b5c','53ecb4e802038d637670b5d998703d2d6b9c38c6'),(36,4,20.00,'2014-10-01 00:00:00',NULL,'1c4536bc7ba0b5ef7dd436260c959fee52d89282','aab20f8d91e511fe13d7450c7f4eb7e0e565e3fc'),(37,4,20.00,'2014-10-01 00:00:00',NULL,'fe96ac9b64a5c186b1d237600ceec49163a80bce','fa5ac71d5f183f18b3e599f75f88b400e944f9c2'),(38,4,20.00,'2014-10-01 00:00:00',NULL,'37c7cce96ca0b2d8ba9c2f59f28e19e7ae46853c','ad74ae7777d68a9a776ccc143a9bd44a7a417288'),(39,4,20.00,'2014-10-01 00:00:00',NULL,'3997a44fece1053ff24ed97f7b521721250bf2b4','51402c75f44655220ab430ccc0d057d234c3b10f'),(40,4,20.00,'2014-10-08 00:00:00',NULL,'048522aceedf2c652b60db7113e10ef2cd925057','b5dd5bc2a4a3c305b87be60fd31895a54e2d0bbd'),(41,2,20.00,'2014-10-23 00:00:00',NULL,'e42cd26ce35060e34e1eb1b920852c7a22142163','83e68b9b420bb33893dada3d6d6d2ef23a46f101'),(42,2,20.00,'2014-10-23 00:00:00',NULL,'43fa2c3ecc7f9ba02aa6d481eb19ad616ef08946','79ae2fd8954627a8a32804cff6c4203f8199b5ea'),(43,2,20.00,'2014-10-27 00:00:00',NULL,'e1766976bda8942786607f1068ac24c4d8e255c9','4c3f05d7fe388b6773fa87e05ff3e3186e3cd772'),(44,2,20.00,'2014-11-12 00:00:00',NULL,'faf01f8d2b467e00ea38cf03007d03404176d839','fe3cfd6a82a4acdb9694373612037943a0476f6c'),(45,2,20.00,'2014-11-14 00:00:00',NULL,'7a3e41b0567620dcfa86d670aa8e73872b5c7ba2','f8cee992a97ac2736ff0a34844e2253dd3a1f27b'),(46,2,20.00,'2014-11-14 00:00:00',NULL,'2ffdb10082bf5318ce33e4e617b57404bf90269a','11c25ce870c95ed4a3d341ca80a449188bce58d3');
/*!40000 ALTER TABLE `paypal_purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permissions`
--

DROP TABLE IF EXISTS `permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `permissions` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `alias` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissions`
--

LOCK TABLES `permissions` WRITE;
/*!40000 ALTER TABLE `permissions` DISABLE KEYS */;
INSERT INTO `permissions` VALUES (1,'BET_CREATE','Place a bet'),(2,'BET_VIEW','View bet options');
/*!40000 ALTER TABLE `permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `purchase_current_status_log`
--

DROP TABLE IF EXISTS `purchase_current_status_log`;
/*!50001 DROP VIEW IF EXISTS `purchase_current_status_log`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `purchase_current_status_log` (
  `purchaseId` tinyint NOT NULL,
  `purchaseCost` tinyint NOT NULL,
  `purchaseDate` tinyint NOT NULL,
  `purchaseStatus` tinyint NOT NULL,
  `purchaseStatusCreated` tinyint NOT NULL,
  `purchaseStatusModified` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `purchase_log`
--

DROP TABLE IF EXISTS `purchase_log`;
/*!50001 DROP VIEW IF EXISTS `purchase_log`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `purchase_log` (
  `purchaseId` tinyint NOT NULL,
  `purchaseCost` tinyint NOT NULL,
  `purchaseDate` tinyint NOT NULL,
  `purchaseStatus` tinyint NOT NULL,
  `purchaseStatusActive` tinyint NOT NULL,
  `purchaseStatusCreated` tinyint NOT NULL,
  `purchaseStatusModified` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `orderId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `cost` decimal(20,2) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recovery_table`
--

DROP TABLE IF EXISTS `recovery_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recovery_table` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `transactionId` varchar(255) NOT NULL,
  `transactionData` text NOT NULL,
  `status` varchar(255) NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recovery_table`
--

LOCK TABLES `recovery_table` WRITE;
/*!40000 ALTER TABLE `recovery_table` DISABLE KEYS */;
INSERT INTO `recovery_table` VALUES (1,4,'0','','Initiate funding request from paypal','2014-09-30 16:51:11'),(2,4,'1c4536bc7ba0b5ef7dd436260c959fee52d89282','{\n  \"bets\" : [ {\n    \"id\" : 30,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"10\", \"12\", \"19\", \"27\", \"30\", \"34\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-01 03:49:29'),(3,4,'37c7cce96ca0b2d8ba9c2f59f28e19e7ae46853c','{\n  \"bets\" : [ {\n    \"id\" : 31,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"3\", \"28\", \"30\", \"40\", \"42\", \"50\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-01 06:42:43'),(4,4,'3997a44fece1053ff24ed97f7b521721250bf2b4','{\n  \"bets\" : [ {\n    \"id\" : 32,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"5\", \"31\", \"36\", \"43\", \"53\", \"54\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-01 13:41:31'),(5,4,'048522aceedf2c652b60db7113e10ef2cd925057','{\n  \"bets\" : [ {\n    \"id\" : 33,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"4\", \"5\", \"24\", \"38\", \"41\", \"53\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"test2\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-08 06:19:32'),(6,2,'43fa2c3ecc7f9ba02aa6d481eb19ad616ef08946','{\n  \"bets\" : [ {\n    \"id\" : 17,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"5\", \"6\", \"7\", \"9\", \"48\", \"53\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"testuser\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-22 17:05:39'),(7,2,'e1766976bda8942786607f1068ac24c4d8e255c9','{\n  \"bets\" : [ {\n    \"id\" : 34,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"10\", \"20\", \"29\", \"35\", \"40\", \"52\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"testuser\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-10-27 09:10:56'),(8,2,'7a3e41b0567620dcfa86d670aa8e73872b5c7ba2','{\n  \"bets\" : [ {\n    \"id\" : 35,\n    \"gameName\" : \"Grand Lotto 6/55\",\n    \"gameCost\" : 20.00,\n    \"orderCost\" : 20,\n    \"drawJackpot\" : 12387875.00,\n    \"drawDate\" : \"2014-08-16\",\n    \"drawDay\" : \"Friday\",\n    \"drawCode\" : \"Draw abce\",\n    \"numbers\" : [ [ \"1\", \"3\", \"6\", \"35\", \"40\", \"48\" ] ],\n    \"system\" : \"6\",\n    \"username\" : \"testuser\",\n    \"deleted\" : false,\n    \"active\" : false\n  } ]\n}','Bet funded','2014-11-14 06:40:35');
/*!40000 ALTER TABLE `recovery_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  `value` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`,`permissionId`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (1,1,1,0),(2,1,2,0),(3,2,1,1),(4,2,2,1),(5,3,1,0),(6,3,2,1);
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'Administrator'),(2,'User'),(3,'Support');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sessions` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `userId` varchar(255) DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `expires` datetime NOT NULL,
  `deleted` tinyint(4) NOT NULL DEFAULT '0',
  `modified` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES (1,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(2,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(3,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(4,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(5,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(6,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(7,'1','c4ca4238a0b923820dcc509a6f75849b','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(8,'1','8a794394c5357c1dbf1450e171e9222c','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(9,'1','cdbf7d71c06a481c1abd759e3aa7a818','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(10,'1','48729783b9c57fe6edbbab2ea42b3be2cd88c02e','2014-08-02 18:14:35',1,'2014-08-02 10:10:52'),(11,'1','90c3ccd2cb2c9b579c069eee6acd5c8549349cea','2014-08-03 00:29:55',1,'2014-08-02 10:14:35'),(12,'4','2e9b632963bd909e644742c902be1d35e59d9a72','2014-08-03 01:29:38',1,'2014-08-02 16:29:38'),(13,'1','be0af6a0ad944fd817131e7a459c95212ef2d596','2014-08-03 01:29:55',1,'2014-08-02 16:29:55'),(14,'1','19d3bbee2420f5cd7f563f6c7cbd6b938c82b4b3','2014-08-05 21:32:31',1,'2014-08-05 12:32:31'),(15,'1','9371d122b42ea97e75d2a5110f5d5e89184ded93','2014-08-05 22:33:42',1,'2014-08-05 13:33:42'),(16,'1','4341d1a90af6dc147742d87c901ad702fba9b27d','2014-08-05 22:54:25',1,'2014-08-05 13:54:25'),(17,'1','aff9d4380f4922474f610a49fa246ec01571eb49','2014-08-05 23:08:39',1,'2014-08-05 14:08:39'),(18,'1','141e8480eb836b17486262bc3941fe6566c13541','2014-08-06 22:18:48',1,'2014-08-06 13:18:48'),(19,'1','efeb636fcc328b496a2ca0bad7d935e775fdbd8','2014-08-06 22:34:27',1,'2014-08-06 13:34:27'),(20,'1','dbd72ac11b428a049d3d29450b3c10230de95b00','2014-08-06 22:38:22',1,'2014-08-06 13:38:22'),(21,'1','3801c443ea5256d8ae244957f702b635b8602ae9','2014-08-06 22:42:43',1,'2014-08-06 13:42:43'),(22,'1','1fbe2ecbf1fffb91793eaf67c15f1bbe552abfe3','2014-08-06 22:57:38',1,'2014-08-06 13:57:38'),(23,'4','c288f77bcc2578fb2b2379191250985db343a7e1','2014-08-08 12:24:27',1,'2014-08-08 03:24:27'),(24,'1','db5b21a72f4e0273ad6623f8449a0b0fc27d0fe6','2014-08-08 12:24:42',1,'2014-08-08 03:24:42'),(25,'2','36ff143e93c17f16924f5ba2ebcf4c647cfa9360','2014-08-10 13:31:17',1,'2014-08-10 04:31:17'),(26,'1','c8da461f086372b0ccc7d8d4c749add5d7153840','2014-08-10 13:33:20',1,'2014-08-10 04:33:20'),(27,'1','5f73679100fe268149875406d09d468590cbf28c','2014-08-18 01:56:11',1,'2014-08-17 16:56:11'),(28,'1','a9ce0b38c6a91935e0cd34ed273cd8a7a86205cb','2014-08-18 01:58:58',1,'2014-08-17 16:58:58'),(29,'4','2bed725cf8fde82bd451f726871a98b6624316e1','2014-08-18 02:01:19',1,'2014-08-17 17:01:19'),(30,'1','d1f8224208fb0ff8ff5e522dd1f78974f0879dcc','2014-08-18 02:01:29',1,'2014-08-17 17:01:29'),(31,'1','15be7aec786663b531eef45a47eac9f4902bd731','2014-08-18 02:05:35',1,'2014-08-17 17:05:35'),(32,'1','418de4430ff979a08cfbb0434327fefe73107aa2','2014-08-27 00:25:39',1,'2014-08-26 15:25:39'),(33,'1','909e09ab3cd961f8a2cd24698e8fbe602d9f9b70','2014-08-28 17:43:45',1,'2014-08-28 08:43:45'),(34,'1','adbbb5c1b1435948e48efc738222b1f54bdce907','2014-08-28 17:45:00',1,'2014-08-28 08:45:00'),(35,'1','708087d7d232175a3400f1ffd1bf7fdbee5fb513','2014-08-28 17:45:49',1,'2014-08-28 08:45:49'),(36,'1','bd7c2fdff347f29e1f3dfaabef696008d2e64055','2014-08-28 17:54:31',1,'2014-08-28 08:54:31'),(37,'1','fee546c8846e89a4d2ba91f59868358cd26b5987','2014-08-28 17:55:10',1,'2014-08-28 08:55:10'),(38,'4','34c84aaec6f63a73828d8be909d0c17f2095595f','2014-08-28 18:11:28',1,'2014-08-28 09:11:28'),(39,'1','f06cac05b1000839b9da47324b5b0a188cd410e9','2014-08-28 18:13:00',1,'2014-08-28 09:13:00'),(40,'4','64c83e9ca9cd4f83361e45fb293e6d575db0a685','2014-08-28 18:19:14',1,'2014-08-28 09:19:14'),(41,'1','a5fae3bc903a209a6315898e01efcd2a0fafdd33','2014-08-28 18:19:26',1,'2014-08-28 09:19:26'),(42,'4','a0d86af13513ead161c17c5676b80f75df16bb1b','2014-08-28 23:26:33',1,'2014-08-28 14:26:33'),(43,'1','e7e9ec796de08efad7954bbc5948d481f8a87e1a','2014-08-28 23:34:23',1,'2014-08-28 14:34:23'),(44,'4','a3fe5f287b22ee3a3df4c8de68aa3c04479ad576','2014-08-28 23:57:49',1,'2014-08-28 14:57:49'),(45,'1','78dd4760a4862e18bb8f86167aff3022f16a899f','2014-08-29 00:11:29',1,'2014-08-28 15:11:30'),(46,'1','5fe1f67297eca3236189ea4ca4b408b6c717d009','2014-09-17 16:14:09',1,'2014-09-17 07:14:09'),(47,'2','21bde1c8c46fb79447309d6954e17f3181a3bdc3','2014-09-17 16:14:41',1,'2014-09-17 07:14:41'),(48,'1','9fb79052267a5170ffce48f74510f899437497f5','2014-09-17 16:16:36',1,'2014-09-17 07:16:36'),(49,'1','fa7b194ccf64d1de959394345b8b01fce80aec1f','2014-09-17 16:20:09',1,'2014-09-17 07:20:09'),(50,'1','53cdada172f47e302fb665dd92b7b1e4d34903cc','2014-09-17 16:20:47',1,'2014-09-17 07:20:47'),(51,'1','331b12ae2d5452dd29a18e566eb12a33561da5f0','2014-09-17 16:21:02',1,'2014-09-17 07:21:02'),(52,'2','8bae66f483b08890912366886c5b6d3f4ea45684','2014-09-17 16:21:18',1,'2014-09-17 07:21:18'),(53,'1','976bec21482ba6e7e7b8fe892eadd1c62e64ca2e','2014-09-17 16:28:51',1,'2014-09-17 07:28:51'),(54,'1','d8da212b64fa57d286c9743c3fe4562d33bf341d','2014-09-17 17:49:00',1,'2014-09-17 08:49:00'),(55,'4','4e06a332ea76d333d0a4b3fac70510e65856bfd4','2014-09-24 11:52:04',1,'2014-09-24 02:52:04'),(56,'4','a7a1b91ccd93ee3f45ab9c0a9b5a8ed9abf8f16f','2014-09-24 20:55:53',1,'2014-09-24 11:55:53'),(57,'4','314a0d2a15a8699f9d157ff5f02e84b47e8d6397','2014-09-24 22:54:32',1,'2014-09-24 13:54:32'),(58,'1','2a4c5804962f88d5bbcef626df8e08cf44c6509','2014-10-13 15:58:14',1,'2014-10-13 06:58:14'),(59,'4','f5c1ca87ee878d63b71287afd52fa4797aa7a5f4','2014-10-13 16:03:00',1,'2014-10-13 07:03:00'),(60,'1','923b6bf40e4d2fc605270edcdb2f1c5b8b656796','2014-10-13 16:03:44',1,'2014-10-13 07:03:44'),(61,'2','60a49c5c18b1419d6ce786b5a02802271fa5c77b','2014-10-23 02:02:42',1,'2014-10-22 17:02:42'),(62,'2','3a79064092e705084f4019c6c93a56de09711182','2014-12-07 16:54:42',1,'2014-12-07 07:54:42'),(63,'2','9d42a4d773b6c9f2cdbd6ce19db2ba2f699b2075','2015-01-13 16:50:04',1,'2015-01-13 07:50:04'),(64,'5','60e22ee7b2fa549d8cfa8e5e92c4253fc77f00f1','2015-09-16 15:13:16',1,'2015-09-16 06:13:16'),(65,'1','7b794e629193f14a46502cb3bb575ecfe0416308','2015-09-16 15:14:49',1,'2015-09-16 06:14:49'),(66,'4','1e357ccdd7e11557ed83ee9fda650db86c3c723d','2015-09-16 15:16:09',0,'2015-09-16 06:16:09'),(67,'2','cb686d6eae7bdbed5cf5ba75fab77fd9a013dffe','2015-09-16 15:16:27',0,'2015-09-16 06:16:27'),(68,'1','5f7d0d577e73f3e3dd354c1c842362c146139056','2015-09-16 22:06:30',1,'2015-09-16 13:06:30'),(69,'1','897b31bf0953e5d75c6faf60136846e56b78ca02','2015-12-07 20:47:42',0,'2015-12-07 19:47:42'),(70,'5','cac259097c6b044b4bc1d1883f48e6d47ca23baa','2015-12-07 21:05:51',0,'2015-12-07 20:05:51');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systems`
--

DROP TABLE IF EXISTS `systems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systems` (
  `name` varchar(255) NOT NULL DEFAULT '',
  `numbers` int(11) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systems`
--

LOCK TABLES `systems` WRITE;
/*!40000 ALTER TABLE `systems` DISABLE KEYS */;
INSERT INTO `systems` VALUES ('10',10),('11',11),('12',12),('5R',5),('6',6),('7',7),('8',8),('9',9);
/*!40000 ALTER TABLE `systems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_draws`
--

DROP TABLE IF EXISTS `t_draws`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_draws` (
  `drawId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gameId` bigint(20) NOT NULL,
  `drawJackpot` decimal(20,2) DEFAULT NULL,
  `drawDate` date DEFAULT NULL,
  PRIMARY KEY (`drawId`),
  UNIQUE KEY `id` (`drawId`),
  KEY `gameId` (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_draws`
--

LOCK TABLES `t_draws` WRITE;
/*!40000 ALTER TABLE `t_draws` DISABLE KEYS */;
INSERT INTO `t_draws` VALUES (1,1,73377937.00,'2014-08-15'),(2,1,12387875.00,'2014-08-16'),(3,2,23581519.00,'2014-08-15'),(4,2,84861982.00,'2014-08-16'),(5,3,62800237.00,'2014-08-15'),(6,3,66775157.00,'2014-08-16'),(7,4,29687870.00,'2014-08-15'),(8,4,34456957.00,'2014-08-16'),(13,2,1200000.00,'2014-08-17'),(14,2,1200000.00,'2014-08-17'),(15,4,43000000.00,'2014-08-22');
/*!40000 ALTER TABLE `t_draws` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_games`
--

DROP TABLE IF EXISTS `t_games`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_games` (
  `gameId` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `gameName` varchar(255) DEFAULT NULL,
  `gameNumbers` int(11) DEFAULT NULL,
  `gameCost` decimal(20,2) DEFAULT NULL,
  `gameLogo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`gameId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_games`
--

LOCK TABLES `t_games` WRITE;
/*!40000 ALTER TABLE `t_games` DISABLE KEYS */;
INSERT INTO `t_games` VALUES (1,'Grand Lotto 6/55',55,20.00,'images/ignore/grand-lotto.jpg'),(2,'Super Lotto 6/49',49,20.00,'images/ignore/superloto-banner.jpg'),(3,'Mega Lotto 6/45',45,10.00,'images/ignore/megaloto-banner.jpg'),(4,'Lotto 6/42',42,10.00,'images/ignore/loto-banner.jpg');
/*!40000 ALTER TABLE `t_games` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `ticketId` bigint(20) NOT NULL AUTO_INCREMENT,
  `ticketSerialNumber` varchar(255) NOT NULL,
  `ticketTimeStamp` datetime NOT NULL,
  `ticketCost` decimal(10,2) NOT NULL,
  `ticketTax` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ticketId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES (1,'a','2014-11-24 07:00:00',10.00,0.00),(2,'b','2014-11-24 10:00:00',40.00,0.00);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction_log`
--

DROP TABLE IF EXISTS `transaction_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transaction_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `description` text NOT NULL,
  `data` text NOT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction_log`
--

LOCK TABLES `transaction_log` WRITE;
/*!40000 ALTER TABLE `transaction_log` DISABLE KEYS */;
INSERT INTO `transaction_log` VALUES (1,4,'Bet Start','{\n  \"orders\" : [ 30 ]\n}','2014-09-30 16:37:44'),(2,4,'Paypal funding confirmed','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5E140180D5424045S\"\n  }\n}','2014-09-30 16:37:50'),(3,4,'Bet Start','{\n  \"orders\" : [ 30 ]\n}','2014-09-30 16:51:07'),(4,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-7B373209473953444\"\n  }\n}','2014-09-30 16:51:11'),(5,4,'Invalid session','{\n  \"orders\" : [ 30 ]\n}','2014-10-01 03:43:26'),(6,4,'Bet Start','{\n  \"orders\" : [ 30 ]\n}','2014-10-01 03:44:30'),(7,4,'Bet Start','{\n  \"orders\" : [ 30 ]\n}','2014-10-01 03:45:01'),(8,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-9SC98886MN035453F\"\n  }\n}','2014-10-01 03:45:07'),(9,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-30E82389MN527490M\"\n  }\n}','2014-10-01 03:45:27'),(10,4,'Bet Start','{\n  \"orders\" : [ 30 ]\n}','2014-10-01 03:48:30'),(11,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-4KM881820L1976822\"\n  }\n}','2014-10-01 03:48:38'),(12,4,'Bet Start','{\n  \"orders\" : [ 31 ]\n}','2014-10-01 06:34:16'),(13,4,'Bet Start','{\n  \"orders\" : [ 31 ]\n}','2014-10-01 06:41:34'),(14,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-2HR17557KW905121E\"\n  }\n}','2014-10-01 06:41:38'),(15,4,'Bet Start','{\n  \"orders\" : [ 32 ]\n}','2014-10-01 13:40:37'),(16,4,'Confirming Paypal Funding','{\n  \"paypalData\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-83L62595YD571125C\"\n  }\n}','2014-10-01 13:40:41'),(17,4,'Bet Start','{\n  \"orders\" : [ 33 ]\n}','2014-10-08 06:18:12'),(18,4,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-93F19383E8506713R\"\n  }\n}','2014-10-08 06:18:25'),(19,2,'Bet Start','{\n  \"orders\" : [ 17 ]\n}','2014-10-22 17:02:50'),(20,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-8RP576127R077104V\"\n  }\n}','2014-10-22 17:02:55'),(21,2,'Bet Start','{\n  \"orders\" : [ 17 ]\n}','2014-10-22 17:04:50'),(22,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-1TU04574V91570911\"\n  }\n}','2014-10-22 17:04:54'),(23,2,'Bet Start','{\n  \"orders\" : [ 34 ]\n}','2014-10-27 09:10:08'),(24,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-51G12234VU050043S\"\n  }\n}','2014-10-27 09:10:14'),(25,2,'Bet Start','{\n  \"orders\" : [ 35 ]\n}','2014-11-12 11:58:48'),(26,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-7UH42490GJ466425P\"\n  }\n}','2014-11-12 11:58:53'),(27,2,'Bet Start','{\n  \"orders\" : [ 35 ]\n}','2014-11-14 06:36:54'),(28,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-5HP22751V3273554B\"\n  }\n}','2014-11-14 06:36:59'),(29,2,'Bet Start','{\n  \"orders\" : [ 36 ]\n}','2014-11-14 12:33:40'),(30,2,'Confirming Funding','{\n  \"data\" : {\n    \"status\" : \"success\",\n    \"message\" : null,\n    \"redirectURI\" : \"https://www.sandbox.paypal.com/cgi-bin/webscr?cmd=_express-checkout&token=EC-3670976145772791J\"\n  }\n}','2014-11-14 12:33:50');
/*!40000 ALTER TABLE `transaction_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1,1),(2,2,2),(4,4,2),(5,5,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_status`
--

DROP TABLE IF EXISTS `user_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `status_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_status`
--

LOCK TABLES `user_status` WRITE;
/*!40000 ALTER TABLE `user_status` DISABLE KEYS */;
INSERT INTO `user_status` VALUES (1,1,1),(2,2,1),(3,4,1),(4,5,1);
/*!40000 ALTER TABLE `user_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_statuses`
--

DROP TABLE IF EXISTS `user_statuses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_statuses` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_statuses`
--

LOCK TABLES `user_statuses` WRITE;
/*!40000 ALTER TABLE `user_statuses` DISABLE KEYS */;
INSERT INTO `user_statuses` VALUES (1,'Active'),(2,'Locked'),(3,'Deleted');
/*!40000 ALTER TABLE `user_statuses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  `birthDate` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nicolai','password','','','',''),(2,'testuser','password','','','',''),(3,'test2','password','','','',''),(4,'test2','password','','','',''),(5,'nicolaivasquez','test','','','','');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `active_purchase_log`
--

/*!50001 DROP TABLE IF EXISTS `active_purchase_log`*/;
/*!50001 DROP VIEW IF EXISTS `active_purchase_log`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `active_purchase_log` AS select `paypal_purchases`.`id` AS `purchaseId`,`paypal_purchases`.`cost` AS `purchaseCost`,`paypal_purchases`.`date` AS `purchaseDate`,`paypal_purchase_status`.`status` AS `purchaseStatus`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified` AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on(((`paypal_purchases`.`id` = `paypal_purchase_status`.`purchaseId`) and (`paypal_purchase_status`.`status` <> 'completed')))) order by `paypal_purchases`.`id`,`paypal_purchase_status`.`dateModified` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `purchase_current_status_log`
--

/*!50001 DROP TABLE IF EXISTS `purchase_current_status_log`*/;
/*!50001 DROP VIEW IF EXISTS `purchase_current_status_log`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `purchase_current_status_log` AS select `paypal_purchases`.`id` AS `purchaseId`,`paypal_purchases`.`cost` AS `purchaseCost`,`paypal_purchases`.`date` AS `purchaseDate`,`paypal_purchase_status`.`status` AS `purchaseStatus`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified` AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on((`paypal_purchases`.`id` = `paypal_purchase_status`.`purchaseId`))) where (`paypal_purchase_status`.`deleted` = 0) order by `paypal_purchases`.`id`,`paypal_purchase_status`.`dateModified` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `purchase_log`
--

/*!50001 DROP TABLE IF EXISTS `purchase_log`*/;
/*!50001 DROP VIEW IF EXISTS `purchase_log`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50001 VIEW `purchase_log` AS select `paypal_purchases`.`id` AS `purchaseId`,`paypal_purchases`.`cost` AS `purchaseCost`,`paypal_purchases`.`date` AS `purchaseDate`,`paypal_purchase_status`.`status` AS `purchaseStatus`,(case when (`paypal_purchase_status`.`deleted` = 0) then 'Active' else 'Inactive' end) AS `purchaseStatusActive`,`paypal_purchase_status`.`dateCreated` AS `purchaseStatusCreated`,`paypal_purchase_status`.`dateModified` AS `purchaseStatusModified` from (`paypal_purchases` join `paypal_purchase_status` on((`paypal_purchases`.`id` = `paypal_purchase_status`.`purchaseId`))) order by `paypal_purchases`.`id`,`paypal_purchase_status`.`dateModified` */;
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

-- Dump completed on 2015-12-13 20:51:36
