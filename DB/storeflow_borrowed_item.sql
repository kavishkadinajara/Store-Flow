CREATE DATABASE  IF NOT EXISTS `storeflow` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `storeflow`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: storeflow
-- ------------------------------------------------------
-- Server version	8.0.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `borrowed_item`
--

DROP TABLE IF EXISTS `borrowed_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowed_item` (
  `borrowed_item_id` int NOT NULL AUTO_INCREMENT,
  `borrowed_item_date` date NOT NULL,
  `borrowed_item_amount` double NOT NULL,
  `company_name` varchar(45) NOT NULL,
  `deu_date` date NOT NULL,
  PRIMARY KEY (`borrowed_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowed_item`
--

LOCK TABLES `borrowed_item` WRITE;
/*!40000 ALTER TABLE `borrowed_item` DISABLE KEYS */;
INSERT INTO `borrowed_item` VALUES (1,'2023-09-23',0,'Manchee','2023-09-23'),(2,'2023-09-23',146,'Sunlight','2023-09-23'),(3,'2023-11-23',5000,'viva','2023-11-23'),(4,'2023-09-23',0,'Milo','2023-09-23'),(5,'2023-09-23',10000,'magi','2023-12-23'),(6,'2023-09-24',1133,'Siddhalepa','2023-12-24'),(7,'2023-09-24',10000,'Milo','2023-09-30'),(8,'2023-10-03',10000,'signal','2023-10-12'),(9,'2023-10-03',15000,'munchee','2023-10-25');
/*!40000 ALTER TABLE `borrowed_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04 22:00:37
