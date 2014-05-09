-- MySQL dump 10.13  Distrib 5.1.41, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: pesse_project
-- ------------------------------------------------------
-- Server version	5.1.41-3ubuntu12.8

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
-- Table structure for table `EMPLOYEE`
--

DROP TABLE IF EXISTS `EMPLOYEE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EMPLOYEE` (
  `EMPNO` varchar(100) NOT NULL,
  `USERNAME` varchar(100) DEFAULT NULL,
  `FIRSTNAME` varchar(100) NOT NULL,
  `LASTNAME` varchar(100) NOT NULL,
  `DESIGNATION` varchar(100) DEFAULT NULL,
  `MOBILENO` varchar(11) NOT NULL,
  `EMAIL` varchar(100) DEFAULT NULL,
  `BIRTHDATE` date DEFAULT NULL,
  `PERMID` varchar(100) NOT NULL,
  `DEPT` varchar(100) NOT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  PRIMARY KEY (`EMPNO`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EMPLOYEE`
--

LOCK TABLES `EMPLOYEE` WRITE;
/*!40000 ALTER TABLE `EMPLOYEE` DISABLE KEYS */;
/*!40000 ALTER TABLE `EMPLOYEE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `EVENT`
--

DROP TABLE IF EXISTS `EVENT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EVENT` (
  `EVENTID` int(11) NOT NULL AUTO_INCREMENT,
  `EVENTDESC` varchar(200) DEFAULT NULL,
  `DEPT` varchar(100) NOT NULL,
  PRIMARY KEY (`EVENTID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `EVENT`
--

LOCK TABLES `EVENT` WRITE;
/*!40000 ALTER TABLE `EVENT` DISABLE KEYS */;
/*!40000 ALTER TABLE `EVENT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `GUARDIAN`
--

DROP TABLE IF EXISTS `GUARDIAN`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `GUARDIAN` (
  `GUARDIANID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) DEFAULT NULL,
  `FIRSTNAME` varchar(100) NOT NULL,
  `LASTNAME` varchar(100) DEFAULT NULL,
  `MOBILENO` varchar(11) NOT NULL,
  `ADDRESS` varchar(100) NOT NULL,
  `CITY` varchar(100) NOT NULL,
  `STATE` varchar(100) NOT NULL,
  `COUNTRY` varchar(100) DEFAULT NULL,
  `PINCODE` varchar(100) NOT NULL,
  `EMAILID` varchar(100) DEFAULT NULL,
  `PASSWORD` varchar(100) NOT NULL,
  `PERMID` enum('HEADOFDEPT','FACULTY','MANAGEMENT','ADMINISTRATIVE','STUDENT','GUARDIAN','ADMIN') NOT NULL,
  PRIMARY KEY (`GUARDIANID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `GUARDIAN`
--

LOCK TABLES `GUARDIAN` WRITE;
/*!40000 ALTER TABLE `GUARDIAN` DISABLE KEYS */;
/*!40000 ALTER TABLE `GUARDIAN` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MESSAGE`
--

DROP TABLE IF EXISTS `MESSAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MESSAGE` (
  `ID` varchar(100) NOT NULL,
  `TO` varchar(100) DEFAULT NULL,
  `FROM` varchar(100) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  `SUBJECT` varchar(100) DEFAULT NULL,
  `MESSAGE` text,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MESSAGE`
--

LOCK TABLES `MESSAGE` WRITE;
/*!40000 ALTER TABLE `MESSAGE` DISABLE KEYS */;
/*!40000 ALTER TABLE `MESSAGE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PERSON`
--

DROP TABLE IF EXISTS `PERSON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PERSON` (
  `EMAILID` varchar(100) NOT NULL,
  `USN` varchar(10) NOT NULL,
  `FIRSTNME` varchar(100) NOT NULL,
  `LASTNAME` varchar(100) NOT NULL,
  `MOBILENO` varchar(11) NOT NULL,
  `PARENT_MOBILE_NO` varchar(100) DEFAULT NULL,
  `DESIGNATION` varchar(100) NOT NULL,
  `DATEOFJOIN` date DEFAULT NULL,
  `BIRTHDATE` date DEFAULT NULL,
  `ADDRESS` varchar(100) NOT NULL,
  `CITY` varchar(100) NOT NULL,
  `STATE` varchar(100) NOT NULL,
  `COUNTRY` varchar(100) DEFAULT NULL,
  `PINCODE` varchar(100) NOT NULL,
  `DEPT` varchar(100) NOT NULL,
  `SECTION` varchar(10) DEFAULT NULL,
  `SEMESTER` int(11) NOT NULL,
  `PASSWORD` varchar(100) DEFAULT NULL,
  `ISACTIVE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`EMAILID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PERSON`
--

LOCK TABLES `PERSON` WRITE;
/*!40000 ALTER TABLE `PERSON` DISABLE KEYS */;
/*!40000 ALTER TABLE `PERSON` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-04-16 16:09:45
