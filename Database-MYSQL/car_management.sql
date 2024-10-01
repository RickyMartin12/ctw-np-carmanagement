-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 02-Out-2024 às 01:26
-- Versão do servidor: 10.4.24-MariaDB
-- versão do PHP: 8.0.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `car_management`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `databasechangeloglock`
--

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` tinyint(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `databasechangeloglock`
--

INSERT INTO `databasechangeloglock` (`ID`, `LOCKED`, `LOCKGRANTED`, `LOCKEDBY`) VALUES
(1, 0, NULL, NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `t_car`
--

CREATE TABLE `t_car` (
  `ID` binary(16) NOT NULL DEFAULT unhex(replace(uuid(),'-','')),
  `BRAND` varchar(255) NOT NULL,
  `MODEL` varchar(255) NOT NULL,
  `ENGINE_TYPE` varchar(10) NOT NULL,
  `CREATED_AT` timestamp NOT NULL DEFAULT current_timestamp(),
  `CREATED_BY` varchar(255) DEFAULT NULL,
  `IMAGE` varchar(255) DEFAULT NULL,
  `COLOR` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `t_car`
--

INSERT INTO `t_car` (`ID`, `BRAND`, `MODEL`, `ENGINE_TYPE`, `CREATED_AT`, `CREATED_BY`, `IMAGE`, `COLOR`) VALUES
(0x38653161346632312d303065302d3437, 'BMW', '3-series', 'BEV', '2024-09-26 20:09:08', 'system_user', 'http://localhost:8080/uploads/psi.png', 'Red'),
(0x4e251cd3bd8547b99c3404aa61111d87, 'BWM', 'LAS', 'BEV', '2024-10-01 20:07:17', NULL, 'http://localhost:8080/uploads/plugin3.png', 'Red2'),
(0x63393330356164302d303466322d3436, 'BMW', '5-series from REST', 'BEV', '2024-09-26 20:06:45', 'system_user', 'http://localhost:8080/uploads/dining_table.jpg', 'Red'),
(0x63626161643663652d313765322d3431, 'BMW', '7-series', 'BEV', '2024-09-26 20:09:31', 'system_user', NULL, NULL),
(0x64633434623639372d626230662d3461, 'BMW', '2-series', 'GASOLINE', '2024-09-26 20:09:18', 'system_user', NULL, NULL),
(0x6e4d13d6734c4c5da060081e0ac71a73, 'BWM', '5-series', 'BEV', '2024-09-30 16:25:07', 'system', NULL, NULL),
(0x70a91abffb7148939ffe74c5547e2982, 'BWM', 'LAS', 'BEV', '2024-09-30 18:11:01', 'system', NULL, NULL),
(0x71002a8cee1c4777b74e8b5120edbc29, 'BWM', 'LAS', 'BEV', '2024-10-01 10:15:07', 'system', NULL, NULL),
(0xc21f0caa27784fc4be48e322179ba725, 'BWM', 'LAS', 'PHEV', '2024-10-01 10:15:28', 'system', NULL, NULL),
(0xc982e40b4f0743418892dc99bf325953, 'BWM', 'LAS', 'BEV', '2024-10-01 22:53:35', NULL, 'http://localhost:8080/uploads/emperor_bed.jpg', 'der'),
(0xed9f592be11b42e4a8d2f193ba803f2b, 'BWM', 'LAS', 'DIESEL', '2024-10-01 10:17:41', 'system', NULL, NULL),
(0xffc7b8893aa948c5acd524c58cb5806d, 'BMW', 'LAS', 'BEV', '2024-10-01 19:49:41', NULL, NULL, 'Red');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `databasechangeloglock`
--
ALTER TABLE `databasechangeloglock`
  ADD PRIMARY KEY (`ID`);

--
-- Índices para tabela `t_car`
--
ALTER TABLE `t_car`
  ADD PRIMARY KEY (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
