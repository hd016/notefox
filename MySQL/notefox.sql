-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 173.194.104.160
-- Erstellungszeit: 15. Dez 2016 um 16:06
-- Server-Version: 5.6.31
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
-- Tabellenstruktur für Tabelle `berechtigung`
--

CREATE TABLE `berechtigung` (
  `berechtigungId` int(11) UNSIGNED NOT NULL,
  `berechtigungsart` enum('LESEN','EDITIEREN','LOESCHEN','') NOT NULL,
  `notiz` int(11) DEFAULT NULL,
  `notizbuch` int(11) DEFAULT NULL,
  `berechtigter` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `berechtigung`
--

INSERT INTO `berechtigung` (`berechtigungId`, `berechtigungsart`, `notiz`, `notizbuch`, `berechtigter`) VALUES
(6002, 'LESEN', 0, 0, 0),
(6003, 'LESEN', 0, 0, 0),
(6004, 'EDITIEREN', 1, NULL, 1000),
(6005, 'EDITIEREN', 1, NULL, 1000),
(6006, 'EDITIEREN', 1, NULL, 1000),
(6007, 'EDITIEREN', 1, 2002, 1000);

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
(5000, 1, '2016-12-01'),
(5001, 0, '2016-12-08');

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
  `notizbuch` int(11) NOT NULL,
  `faelligkeitsdatum` date NOT NULL,
  `faelligkeitsstatus` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notiz`
--

INSERT INTO `notiz` (`id`, `eigentuemer`, `titel`, `subtitel`, `inhalt`, `erstelldatum`, `modifikationsdatum`, `notizbuch`, `faelligkeitsdatum`, `faelligkeitsstatus`) VALUES
(1, 1, 'Titel-Muster', 'Subtitel-Muster', 'Hier ist Inhalt', '2016-12-01', '2016-12-09', 0, '0000-00-00', 0),
(2, 1, 'Titel-Muster2', 'Subtitel-Muster2', 'Inhalt', '2016-12-02', '2016-12-21', 0, '0000-00-00', 0),
(3, 1000, 'Hallo', 'WI7', 'hier_ist_inhalt', '2016-12-11', '2016-12-11', 0, '0000-00-00', 0),
(4, 2000, 'Mansur', 'null', 'INhalt', '2016-12-11', '2016-12-11', 0, '0000-00-00', 0),
(5, 1000, 'Hallo', 'WI7', 'hier_ist_inhalt', '2016-12-11', '2016-12-11', 0, '0000-00-00', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizbuch`
--

CREATE TABLE `notizbuch` (
  `id` int(11) UNSIGNED NOT NULL,
  `eigentuemer` int(11) NOT NULL,
  `titel` varchar(50) NOT NULL,
  `subtitel` varchar(50) NOT NULL,
  `erstelldatum` date NOT NULL,
  `modifikationsdatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizbuch`
--

INSERT INTO `notizbuch` (`id`, `eigentuemer`, `titel`, `subtitel`, `erstelldatum`, `modifikationsdatum`) VALUES
(2002, 1, 'Titel-Muster1', 'Subtitel-Muster2', '2016-12-01', '2016-12-16'),
(2003, 1, 'Titel-Muster2', 'Titel-Muster2', '2016-12-02', '2016-12-20'),
(2004, 1000, 'Hallo', 'WI7', '2016-12-11', '2016-12-11'),
(2005, 1000, 'Hallo', 'WI7', '2016-12-13', '2016-12-13');

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
(1000, '', 'mansur@gmail.com'),
(1001, '', 'dejan@gmail.com'),
(1002, '', 'mansurggbg@gmail.com'),
(1003, '', 'manfgfsur@gmail.com '),
(1004, '', 'muhammed@gmail.com '),
(1005, 'muhammed', 'muhammed1@gmail.com'),
(1006, 'null', 'mansur@example.com'),
(1007, 'null', 'mansur414@example.com'),
(1008, 'null', 'harun@example.com'),
(1009, 'null', 'Esra@example.com'),
(1010, 'null', 'HALLO@example.com'),
(1011, 'null', 'TEST@example.com'),
(1012, 'null', 'Waldemar@example.com'),
(1013, 'null', 'Waldemar2@example.com'),
(1014, 'null', 'Irgendwas@example.com');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `berechtigung`
--
ALTER TABLE `berechtigung`
  ADD PRIMARY KEY (`berechtigungId`);

--
-- Indizes für die Tabelle `datum`
--
ALTER TABLE `datum`
  ADD PRIMARY KEY (`faelligkeitId`);

--
-- Indizes für die Tabelle `notiz`
--
ALTER TABLE `notiz`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `notizbuch`
--
ALTER TABLE `notizbuch`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  ADD PRIMARY KEY (`nutzerId`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `berechtigung`
--
ALTER TABLE `berechtigung`
  MODIFY `berechtigungId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6008;
--
-- AUTO_INCREMENT für Tabelle `datum`
--
ALTER TABLE `datum`
  MODIFY `faelligkeitId` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5002;
--
-- AUTO_INCREMENT für Tabelle `notiz`
--
ALTER TABLE `notiz`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT für Tabelle `notizbuch`
--
ALTER TABLE `notizbuch`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2006;
--
-- AUTO_INCREMENT für Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  MODIFY `nutzerId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1015;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
