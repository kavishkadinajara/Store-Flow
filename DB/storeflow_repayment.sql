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
-- Table structure for table `repayment`
--

DROP TABLE IF EXISTS `repayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `repayment` (
  `repayment_id` int NOT NULL AUTO_INCREMENT,
  `repayment_date` date NOT NULL,
  `repayment_amount` double NOT NULL,
  `borrowed_item_id` int NOT NULL,
  PRIMARY KEY (`repayment_id`),
  KEY `borrowed_item_id_idx` (`borrowed_item_id`),
  CONSTRAINT `borrowed_item_id` FOREIGN KEY (`borrowed_item_id`) REFERENCES `borrowed_item` (`borrowed_item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `repayment`
--

LOCK TABLES `repayment` WRITE;
/*!40000 ALTER TABLE `repayment` DISABLE KEYS */;
INSERT INTO `repayment` VALUES (1,'2023-09-24',5000,2),(2,'2023-09-24',1000,1),(3,'2023-09-24',4000,4),(4,'2023-09-24',1000,4),(5,'2023-09-24',1800,2),(6,'2023-09-24',1000,2),(7,'2023-09-24',1000,2),(8,'2023-09-24',1000,3),(9,'2023-09-24',6213,6),(10,'2023-09-24',654,2),(11,'2023-09-24',654,6),(12,'2023-09-24',200,2),(13,'2023-09-24',200,2),(14,'2023-10-03',10000,7);
/*!40000 ALTER TABLE `repayment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-04 22:00:38
