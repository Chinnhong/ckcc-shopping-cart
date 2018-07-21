-- phpMyAdmin SQL Dump
-- version 4.8.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 21, 2018 at 10:24 AM
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
('001', 'Coca Cola', 'Drink', '4', '80'),
('002', 'Fanta', 'drink', '5', '4'),
('003', '7UP', 'Drink', '5', '99');

-- --------------------------------------------------------

--
-- Table structure for table `tblsale`
--

CREATE TABLE `tblsale` (
  `order_id` int(5) NOT NULL,
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
(1, '123', '123', '123', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(3, '111', '111', '111', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(5, '111', '111', '111', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(6, '1', '1', '1', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(7, '1', '1', '1', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(8, '1231111', '1', '1', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(9, '12q', '12q', '12q', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(10, '12q', '12q', '12q', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(11, 'Chinnhong', '012327310', 'pp', '001', 'Coca Cola', '4.00', '1.00', '0.04', '3.96'),
(12, 'Chinnhong', '012327310', 'pp', '003', '7UP', '5.00', '1.00', '0.05', '4.95');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tblproduct`
--
ALTER TABLE `tblproduct`
  ADD PRIMARY KEY (`pro_id`);

--
-- Indexes for table `tblsale`
--
ALTER TABLE `tblsale`
  ADD PRIMARY KEY (`order_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tblsale`
--
ALTER TABLE `tblsale`
  MODIFY `order_id` int(5) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
