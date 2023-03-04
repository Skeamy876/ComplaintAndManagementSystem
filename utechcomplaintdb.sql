-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 04, 2023 at 06:58 PM
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
-- Table structure for table `advisor`
--

CREATE TABLE `advisor` (
  `advisor_ID` int(20) NOT NULL,
  `advisor_firstname` varchar(20) NOT NULL,
  `advisor_lastname` varchar(20) NOT NULL,
  `advisor_email` varchar(25) NOT NULL,
  `advisor_phoneNumber` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `assign_complaintsqueries`
--

CREATE TABLE `assign_complaintsqueries` (
  `assigned_ID` int(20) NOT NULL,
  `complaint_ID` int(20) NOT NULL,
  `query_ID` int(20) NOT NULL,
  `advisor_ID` int(20) NOT NULL,
  `assigned_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `category_ID` int(11) NOT NULL,
  `category_name` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `complaints`
--

CREATE TABLE `complaints` (
  `complaint_ID` int(20) NOT NULL,
  `student_ID` int(11) NOT NULL,
  `category_ID` int(11) NOT NULL,
  `complaint_detail` int(11) NOT NULL,
  `complaint_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(5) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `query`
--

CREATE TABLE `query` (
  `query_ID` int(20) NOT NULL,
  `category_ID` int(11) NOT NULL,
  `query_detail` varchar(250) NOT NULL,
  `student_ID` int(20) NOT NULL,
  `query_date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `status` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE `students` (
  `student_ID` int(20) NOT NULL,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `email_address` varchar(25) NOT NULL,
  `phone_number` int(10) NOT NULL,
  `query_ID` int(20) NOT NULL COMMENT 'foreign key',
  `complaint_ID` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `supervisor`
--

CREATE TABLE `supervisor` (
  `supervisor_ID` int(20) NOT NULL,
  `supervisor_firstname` varchar(20) NOT NULL,
  `supervisor_lastname` varchar(20) NOT NULL,
  `supervisor_email` varchar(25) NOT NULL,
  `supervisor_phoneNumber` int(10) NOT NULL,
  `query_ID` int(20) NOT NULL COMMENT 'foreign key',
  `student_ID` int(20) NOT NULL COMMENT 'foreign key',
  `advisor_ID` int(20) NOT NULL COMMENT 'foreign key'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `advisor`
--
ALTER TABLE `advisor`
  ADD PRIMARY KEY (`advisor_ID`);

--
-- Indexes for table `assign_complaintsqueries`
--
ALTER TABLE `assign_complaintsqueries`
  ADD PRIMARY KEY (`assigned_ID`),
  ADD KEY `complaint_ID` (`complaint_ID`,`query_ID`,`advisor_ID`),
  ADD KEY `advisor_ID` (`advisor_ID`),
  ADD KEY `query_ID` (`query_ID`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_ID`);

--
-- Indexes for table `complaints`
--
ALTER TABLE `complaints`
  ADD PRIMARY KEY (`complaint_ID`),
  ADD KEY `complaint_ID` (`complaint_ID`),
  ADD KEY `student_ID` (`student_ID`,`category_ID`),
  ADD KEY `category_ID` (`category_ID`);

--
-- Indexes for table `query`
--
ALTER TABLE `query`
  ADD PRIMARY KEY (`query_ID`),
  ADD UNIQUE KEY `student_ID` (`student_ID`),
  ADD KEY `category_ID` (`category_ID`);

--
-- Indexes for table `students`
--
ALTER TABLE `students`
  ADD PRIMARY KEY (`student_ID`),
  ADD UNIQUE KEY `query_ID` (`query_ID`),
  ADD KEY `student_ID` (`student_ID`),
  ADD KEY `complaint_ID` (`complaint_ID`);

--
-- Indexes for table `supervisor`
--
ALTER TABLE `supervisor`
  ADD PRIMARY KEY (`supervisor_ID`),
  ADD UNIQUE KEY `query_ID` (`query_ID`,`student_ID`,`advisor_ID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `assign_complaintsqueries`
--
ALTER TABLE `assign_complaintsqueries`
  ADD CONSTRAINT `assign_complaintsqueries_ibfk_1` FOREIGN KEY (`advisor_ID`) REFERENCES `advisor` (`advisor_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `assign_complaintsqueries_ibfk_2` FOREIGN KEY (`complaint_ID`) REFERENCES `complaints` (`complaint_ID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `assign_complaintsqueries_ibfk_3` FOREIGN KEY (`query_ID`) REFERENCES `query` (`query_ID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `complaints`
--
ALTER TABLE `complaints`
  ADD CONSTRAINT `complaints_ibfk_1` FOREIGN KEY (`category_ID`) REFERENCES `categories` (`category_ID`);

--
-- Constraints for table `query`
--
ALTER TABLE `query`
  ADD CONSTRAINT `query_ibfk_1` FOREIGN KEY (`category_ID`) REFERENCES `categories` (`category_ID`);

--
-- Constraints for table `students`
--
ALTER TABLE `students`
  ADD CONSTRAINT `students_ibfk_1` FOREIGN KEY (`query_ID`) REFERENCES `query` (`query_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `students_ibfk_2` FOREIGN KEY (`complaint_ID`) REFERENCES `complaints` (`complaint_ID`);

--
-- Constraints for table `supervisor`
--
ALTER TABLE `supervisor`
  ADD CONSTRAINT `supervisor_ibfk_1` FOREIGN KEY (`query_ID`) REFERENCES `query` (`query_ID`) ON DELETE CASCADE ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
