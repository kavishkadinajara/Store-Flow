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
-- Table structure for table `recived_loan`
--

DROP TABLE IF EXISTS `recived_loan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recived_loan` (
  `recived_loan_id` int NOT NULL AUTO_INCREMENT,
  `recived_loan_date` date NOT NULL,
  `recived_loan_amount` double NOT NULL,
  `borrower_id` int NOT NULL,
  PRIMARY KEY (`recived_loan_id`),
  KEY `borrower_id_fk_idx` (`borrower_id`),
  CONSTRAINT `borrower_id_fk` FOREIGN KEY (`borrower_id`) REFERENCES `borrower` (`borrower_id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recived_loan`
--

LOCK TABLES `recived_loan` WRITE;
/*!40000 ALTER TABLE `recived_loan` DISABLE KEYS */;
INSERT INTO `recived_loan` VALUES (1,'2023-09-22',5000,5),(2,'2023-09-22',500,5),(3,'2023-09-22',1500,3),(4,'2023-09-22',1000,5),(5,'2023-09-22',5214,5),(6,'2023-09-22',4000,1),(7,'2023-09-22',20,3),(8,'2023-09-22',4500,4),(9,'2023-09-22',2000,5),(10,'2023-09-22',2000,6),(11,'2023-09-22',200,9),(12,'2023-09-22',200,10),(13,'2023-09-22',200,11),(14,'2023-09-22',1000,5),(15,'2023-09-22',300,5),(16,'2023-09-22',2000,6),(17,'2023-09-22',200,6),(18,'2023-09-22',200,4),(19,'2023-09-22',20,5),(20,'2023-09-22',20,5),(21,'2023-09-22',500,4),(22,'2023-09-22',2000,10),(23,'2023-09-22',3520,10),(24,'2023-09-22',3520,9),(25,'2023-09-22',100,9),(26,'2023-09-22',500,1),(27,'2023-09-22',14,4),(28,'2023-09-22',4,5),(29,'2023-09-22',10000,7),(30,'2023-09-22',20000,8),(31,'2023-09-22',5000,7),(32,'2023-09-22',15000,7),(33,'2023-09-22',7000,7),(34,'2023-09-22',7000,8),(35,'2023-09-22',900,8),(36,'2023-09-23',1233,7),(37,'2023-09-23',450,3),(38,'2023-09-23',1000,3),(39,'2023-09-23',100,6),(40,'2023-09-24',400,6),(41,'2023-10-03',20000,8),(42,'2023-10-03',767,7),(43,'2023-10-03',1000,7),(44,'2023-10-03',213,9),(45,'2023-10-03',200,6),(46,'2023-10-03',500,9);
/*!40000 ALTER TABLE `recived_loan` ENABLE KEYS */;
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
