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
-- Table structure for table `leading_loan`
--

DROP TABLE IF EXISTS `leading_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `leading_loan` (
  `leading_id` int NOT NULL AUTO_INCREMENT,
  `leading_date` date NOT NULL,
  `leading_amount` double NOT NULL,
  `borrower_id` int NOT NULL,
  PRIMARY KEY (`leading_id`),
  KEY `borrower_id_idx` (`borrower_id`),
  CONSTRAINT `borrower_id` FOREIGN KEY (`borrower_id`) REFERENCES `borrower` (`borrower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leading_loan`
--

LOCK TABLES `leading_loan` WRITE;
/*!40000 ALTER TABLE `leading_loan` DISABLE KEYS */;
INSERT INTO `leading_loan` VALUES (46,'2023-09-22',3254,12),(55,'2023-09-22',251,11),(70,'2023-09-22',2263,10),(73,'2023-09-22',21,1),(74,'2023-09-22',0,4),(75,'2023-09-22',0,5),(91,'2023-09-23',206,3),(93,'2023-09-24',1200,15),(94,'2023-09-24',500,15),(95,'2023-09-24',8000,15),(98,'2023-10-03',12334,8),(99,'2023-10-03',5000,8),(102,'2023-10-03',10000,7),(104,'2023-10-03',73,6),(105,'2023-10-03',500,9);
/*!40000 ALTER TABLE `leading_loan` ENABLE KEYS */;
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
