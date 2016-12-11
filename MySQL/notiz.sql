-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 11. Dez 2016 um 18:42
-- Server-Version: 10.1.16-MariaDB
-- PHP-Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `notefox`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notiz`
--

CREATE TABLE `notiz` (
  `id` int(11) UNSIGNED NOT NULL,
  `eigentuemer` int(11) NOT NULL,
  `titel` varchar(50) NOT NULL,
  `subtitel` varchar(50) NOT NULL,
  `inhalt` text NOT NULL,
  `erstelldatum` date NOT NULL,
  `modifikationsdatum` date NOT NULL,
  `notizbuch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notiz`
--

INSERT INTO `notiz` (`id`, `eigentuemer`, `titel`, `subtitel`, `inhalt`, `erstelldatum`, `modifikationsdatum`, `notizbuch`) VALUES
(1, 1, 'Titel-Muster', 'Subtitel-Muster', 'Hier ist Inhalt', '2016-12-01', '2016-12-09', 0),
(2, 1, 'Titel-Muster2', 'Subtitel-Muster2', 'Inhalt', '2016-12-02', '2016-12-21', 0),
(3, 1000, 'Hallo', 'WI7', 'hier_ist_inhalt', '2016-12-11', '2016-12-11', 0),
(4, 2000, 'Mansur', 'null', 'INhalt', '2016-12-11', '2016-12-11', 0);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `notiz`
--
ALTER TABLE `notiz`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `notiz`
--
ALTER TABLE `notiz`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
