-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 28, 2018 at 11:19 AM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fontys_auction`
--

-- --------------------------------------------------------

--
-- Table structure for table `bids`
--

CREATE TABLE `bids` (
  `ID` bigint(20) NOT NULL,
  `AMOUNT` longblob,
  `TIME` longblob,
  `BUYER_ID` bigint(20) NOT NULL,
  `ITEM_ID` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `bids`
--

INSERT INTO `bids` (`ID`, `AMOUNT`, `TIME`, `BUYER_ID`, `ITEM_ID`) VALUES
(2, 0xaced0005737200146e6c2e666f6e7479732e7574696c2e4d6f6e657900000000000000010200024a000563656e74734c000863757272656e63797400124c6a6176612f6c616e672f537472696e673b787000000000000249f0740003e282ac, NULL, 6, 6);

-- --------------------------------------------------------

--
-- Table structure for table `items`
--

CREATE TABLE `items` (
  `ID` bigint(20) NOT NULL,
  `DTYPE` varchar(31) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `cat_description` varchar(255) DEFAULT NULL,
  `SELLER_ID` bigint(20) DEFAULT NULL,
  `PAINTER` varchar(255) DEFAULT NULL,
  `TITLE` varchar(255) DEFAULT NULL,
  `MATERIAL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `items`
--

INSERT INTO `items` (`ID`, `DTYPE`, `DESCRIPTION`, `cat_description`, `SELLER_ID`, `PAINTER`, `TITLE`, `MATERIAL`) VALUES
(6, 'Furniture', 'broodkast', 'cat2', 5, NULL, NULL, 'ijzer'),
(7, 'Painting', 'omsch1', 'cat2', 5, 'Rembrandt', 'Nachtwacht', NULL),
(8, 'items', 'The science of Discworld', 'boek', 5, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` bigint(20) NOT NULL,
  `EMAIL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `EMAIL`) VALUES
(5, 'iemand1@def'),
(6, 'iemand2@def');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bids`
--
ALTER TABLE `bids`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_BIDS_ITEM_ID` (`ITEM_ID`),
  ADD KEY `FK_BIDS_BUYER_ID` (`BUYER_ID`);

--
-- Indexes for table `items`
--
ALTER TABLE `items`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `FK_ITEMS_SELLER_ID` (`SELLER_ID`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `EMAIL` (`EMAIL`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bids`
--
ALTER TABLE `bids`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `items`
--
ALTER TABLE `items`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bids`
--
ALTER TABLE `bids`
  ADD CONSTRAINT `FK_BIDS_BUYER_ID` FOREIGN KEY (`BUYER_ID`) REFERENCES `users` (`ID`),
  ADD CONSTRAINT `FK_BIDS_ITEM_ID` FOREIGN KEY (`ITEM_ID`) REFERENCES `items` (`ID`);

--
-- Constraints for table `items`
--
ALTER TABLE `items`
  ADD CONSTRAINT `FK_ITEMS_SELLER_ID` FOREIGN KEY (`SELLER_ID`) REFERENCES `users` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
