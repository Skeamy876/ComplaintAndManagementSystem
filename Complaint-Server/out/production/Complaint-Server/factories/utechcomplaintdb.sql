-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 11, 2023 at 08:38 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `utechcomplaintdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `advisorEntity`
--

CREATE TABLE `advisorEntity` (
  `idNumber` bigint(20) NOT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `Id` bigint(20) NOT NULL,
  `categoryName` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `category`
--

INSERT INTO `category` (`Id`, `categoryName`) VALUES
(1, 'MISSING_GRADES');

-- --------------------------------------------------------

--
-- Table structure for table `complaintEntities`
--

CREATE TABLE `complaintEntities` (
  `complaintId` bigint(20) NOT NULL,
  `complaint_date` datetime DEFAULT NULL,
  `complaint_detail` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `category_ID` bigint(20) DEFAULT NULL,
  `student_idNumber` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `queryEntity`
--

CREATE TABLE `queryEntity` (
  `queryId` bigint(20) NOT NULL,
  `query_date` datetime DEFAULT NULL,
  `query_detail` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `category_ID` bigint(20) DEFAULT NULL,
  `student_idNumber` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `queryEntity`
--

INSERT INTO `queryEntity` (`queryId`, `query_date`, `query_detail`, `status`, `category_ID`, `student_idNumber`) VALUES
(1, '2023-03-11 14:06:57', 'No grades for Advanced Programming', 'OPEN', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `studentEntities`
--

CREATE TABLE `studentEntities` (
  `idNumber` bigint(20) NOT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentEntities`
--

INSERT INTO `studentEntities` (`idNumber`, `email_address`, `first_name`, `last_name`, `phone_number`) VALUES
(1, 'jackedaniels@hotmail.com', 'Jack', 'Daniels', 9875643);

-- --------------------------------------------------------

--
-- Table structure for table `students_complaints`
--

CREATE TABLE `students_complaints` (
  `students_idNumber` bigint(20) NOT NULL,
  `complaints_complaintId` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `students_query`
--

CREATE TABLE `students_query` (
  `students_idNumber` bigint(20) NOT NULL,
  `queries_queryId` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supervisorEntity`
--

CREATE TABLE `supervisorEntity` (
  `idNumber` bigint(20) NOT NULL,
  `email_address` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone_number` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supervisor_advisor`
--

CREATE TABLE `supervisor_advisor` (
  `supervisor_idNumber` bigint(20) NOT NULL,
  `advisors_idNumber` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supervisor_complaints`
--

CREATE TABLE `supervisor_complaints` (
  `supervisor_idNumber` bigint(20) NOT NULL,
  `complaints_complaintId` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supervisor_query`
--

CREATE TABLE `supervisor_query` (
  `supervisor_idNumber` bigint(20) NOT NULL,
  `queries_queryId` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advisorEntity`
--
ALTER TABLE `advisorEntity`
  ADD PRIMARY KEY (`idNumber`);

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `complaintEntities`
--
ALTER TABLE `complaintEntities`
  ADD PRIMARY KEY (`complaintId`),
  ADD KEY `FK3jfwmcitaywmlxxlcfx6saait` (`category_ID`),
  ADD KEY `FKhyx0sjis16ooph0aoctpr7d9j` (`student_idNumber`);

--
-- Indexes for table `queryEntity`
--
ALTER TABLE `queryEntity`
  ADD PRIMARY KEY (`queryId`),
  ADD KEY `FK3iuyahxhymmut2dkgylrlbqhg` (`category_ID`),
  ADD KEY `FKf69xkdeq0ano3b3qrf721tp48` (`student_idNumber`);

--
-- Indexes for table `studentEntities`
--
ALTER TABLE `studentEntities`
  ADD PRIMARY KEY (`idNumber`);

--
-- Indexes for table `students_complaints`
--
ALTER TABLE `students_complaints`
  ADD UNIQUE KEY `UK_ovxsyo592j5mnhc2sj54562qm` (`complaints_complaintId`),
  ADD KEY `FKj1j52xoiq7odo64uxs6un4f06` (`students_idNumber`);

--
-- Indexes for table `students_query`
--
ALTER TABLE `students_query`
  ADD UNIQUE KEY `UK_hti4j2veolxyel3rh8jffe0t2` (`queries_queryId`),
  ADD KEY `FKsx9h3dd2ju87m9bo2osnu3r82` (`students_idNumber`);

--
-- Indexes for table `supervisorEntity`
--
ALTER TABLE `supervisorEntity`
  ADD PRIMARY KEY (`idNumber`);

--
-- Indexes for table `supervisor_advisor`
--
ALTER TABLE `supervisor_advisor`
  ADD KEY `FKk87wppsvm5xw1yp8ge81jcdmg` (`advisors_idNumber`),
  ADD KEY `FKdao1cbbr8nlsw45iagmi7o07q` (`supervisor_idNumber`);

--
-- Indexes for table `supervisor_complaints`
--
ALTER TABLE `supervisor_complaints`
  ADD KEY `FK1igt2xnqsmw4u5pvifrb2gdlm` (`complaints_complaintId`),
  ADD KEY `FKqstrrlm782jgnwr7cf3x3rwxc` (`supervisor_idNumber`);

--
-- Indexes for table `supervisor_query`
--
ALTER TABLE `supervisor_query`
  ADD KEY `FK6rru84ckqauq6hkim6rdqk14b` (`queries_queryId`),
  ADD KEY `FK9gvppolpbhud7ccoeexfohu5i` (`supervisor_idNumber`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `advisorEntity`
--
ALTER TABLE `advisorEntity`
  MODIFY `idNumber` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `Id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `complaintEntities`
--
ALTER TABLE `complaintEntities`
  MODIFY `complaintId` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `queryEntity`
--
ALTER TABLE `queryEntity`
  MODIFY `queryId` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `studentEntities`
--
ALTER TABLE `studentEntities`
  MODIFY `idNumber` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `supervisorEntity`
--
ALTER TABLE `supervisorEntity`
  MODIFY `idNumber` bigint(20) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
