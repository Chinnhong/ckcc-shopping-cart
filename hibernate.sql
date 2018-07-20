-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 20, 2018 at 07:09 PM
-- Server version: 10.1.34-MariaDB
-- PHP Version: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hibernate`
--

-- --------------------------------------------------------

--
-- Table structure for table `tblproduct`
--

CREATE TABLE `tblproduct` (
  `pro_id` varchar(20) NOT NULL,
  `pro_name` varchar(50) NOT NULL,
  `pro_des` varchar(100) DEFAULT NULL,
  `pro_price` decimal(10,0) NOT NULL,
  `pro_qty` decimal(10,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblproduct`
--

INSERT INTO `tblproduct` (`pro_id`, `pro_name`, `pro_des`, `pro_price`, `pro_qty`) VALUES
('001', 'CoCa', 'Drink', '5', '88'),
('002', 'Fanta', 'Drink', '3', '183'),
('003', '7UP', 'Drink', '2', '94'),
('004', 'Co', 'Test', '22', '122221'),
('005', 'Sprite', 'Drink', '3', '1000');

-- --------------------------------------------------------

--
-- Table structure for table `tblsale`
--

CREATE TABLE `tblsale` (
  `order_id` varchar(5) NOT NULL,
  `cust_name` varchar(20) NOT NULL,
  `cust_phone` varchar(20) NOT NULL,
  `cust_address` varchar(50) NOT NULL,
  `pro_id` varchar(20) NOT NULL,
  `pro_name` varchar(20) NOT NULL,
  `pro_price` decimal(10,2) NOT NULL,
  `pro_qty` decimal(10,2) NOT NULL,
  `pro_dis` decimal(10,2) NOT NULL,
  `pro_totalprice` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblsale`
--

INSERT INTO `tblsale` (`order_id`, `cust_name`, `cust_phone`, `cust_address`, `pro_id`, `pro_name`, `pro_price`, `pro_qty`, `pro_dis`, `pro_totalprice`) VALUES
('1', 'Chinnhong', '012327310', 'PP', '001', 'CoCa', '5.00', '1.00', '0.05', '4.95'),
('4', 'Chinnhong', '012327310', 'PP', '004', 'Co', '22.00', '1.00', '0.22', '21.78'),
('5', 'Abc', '012', 'PP', '001', 'CoCa', '5.00', '1.00', '0.05', '4.95'),
('1', 'Chinnhong', '012327310', 'PP', '001', 'CoCa', '5.00', '1.00', '0.05', '4.95'),
('3', 'Chinnhong', '012327310', 'PP', '003', '7UP', '2.00', '1.00', '0.02', '1.98');

-- --------------------------------------------------------

--
-- Table structure for table `tblstudent`
--

CREATE TABLE `tblstudent` (
  `id` int(11) NOT NULL,
  `FirstName` varchar(50) NOT NULL,
  `LastName` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tblstudent`
--

INSERT INTO `tblstudent` (`id`, `FirstName`, `LastName`, `Email`) VALUES
(1, 'Chinnhong', 'Seng', 'hong@gmail.com'),
(3, 'Chinnhong', 'Seng', 'chinnhong@gmail.com'),
(4, 'Chinnhong', 'Seng', 'hong@gmail.com'),
(5, 'Chinnhong', 'Seng', 'hong@gmail.com'),
(6, 'Chinnhong', 'Seng', 'hong@gmail.com'),
(7, 'Chinnhong', 'Seng', 'hong@gmail.com'),
(8, 'Chinnhong', 'Seng', 'hong@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblproduct`
--
ALTER TABLE `tblproduct`
  ADD PRIMARY KEY (`pro_id`);

--
-- Indexes for table `tblstudent`
--
ALTER TABLE `tblstudent`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblstudent`
--
ALTER TABLE `tblstudent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
