-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 04. Dez 2016 um 21:59
-- Server-Version: 10.1.16-MariaDB
-- PHP-Version: 5.6.24

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
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
-- Tabellenstruktur für Tabelle `datum`
--

CREATE TABLE `datum` (
  `faelligkeitId` int(50) UNSIGNED NOT NULL,
  `status` tinyint(1) NOT NULL,
  `faelligkeitsdatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `datum`
--

INSERT INTO `datum` (`faelligkeitId`, `status`, `faelligkeitsdatum`) VALUES
(4000, 1, '2016-12-01'),
(4001, 0, '2016-12-02'),
(4002, 1, '2016-12-01'),
(4003, 0, '2016-12-02');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizobjekt`
--

CREATE TABLE `notizobjekt` (
  `id` int(11) UNSIGNED NOT NULL,
  `titel` char(100) NOT NULL,
  `subtitel` char(100) NOT NULL,
  `erstelldatum` date NOT NULL,
  `modifikationsdatum` date NOT NULL,
  `inhalt` text NOT NULL,
  `eigentuemer` int(11) NOT NULL,
  `typ` enum('NOTIZ','NOTIZBUCH','','') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizobjekt`
--

INSERT INTO `notizobjekt` (`id`, `titel`, `subtitel`, `erstelldatum`, `modifikationsdatum`, `inhalt`, `eigentuemer`, `typ`) VALUES
(2, 'Studium', 'WI', '2016-12-01', '2016-12-02', 'Hier ist Inhalt', 1, 'NOTIZBUCH');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizquelle`
--

CREATE TABLE `notizquelle` (
  `notizquelleId` int(50) UNSIGNED NOT NULL,
  `notizquelleName` char(100) NOT NULL,
  `url` char(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizquelle`
--

INSERT INTO `notizquelle` (`notizquelleId`, `notizquelleName`, `url`) VALUES
(3000, 'google', 'www.google.de'),
(3001, 'facebook', 'www.facebook.de');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `nutzer`
--

CREATE TABLE `nutzer` (
  `nutzerId` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `nutzer`
--

INSERT INTO `nutzer` (`nutzerId`, `name`, `email`) VALUES
(2002, 'mansur', 'mansur@gmail.com'),
(2003, 'dejan', 'deyan@gmail.com');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `datum`
--
ALTER TABLE `datum`
  ADD PRIMARY KEY (`faelligkeitId`);

--
-- Indizes für die Tabelle `notizobjekt`
--
ALTER TABLE `notizobjekt`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `notizobjekt` ADD FULLTEXT KEY `titel` (`titel`);
ALTER TABLE `notizobjekt` ADD FULLTEXT KEY `subtitel` (`subtitel`);

--
-- Indizes für die Tabelle `notizquelle`
--
ALTER TABLE `notizquelle`
  ADD PRIMARY KEY (`notizquelleId`);
ALTER TABLE `notizquelle` ADD FULLTEXT KEY `notizquelleName` (`notizquelleName`);

--
-- Indizes für die Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  ADD PRIMARY KEY (`nutzerId`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `datum`
--
ALTER TABLE `datum`
  MODIFY `faelligkeitId` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4004;
--
-- AUTO_INCREMENT für Tabelle `notizobjekt`
--
ALTER TABLE `notizobjekt`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `notizquelle`
--
ALTER TABLE `notizquelle`
  MODIFY `notizquelleId` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3002;
--
-- AUTO_INCREMENT für Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  MODIFY `nutzerId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2004;COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
