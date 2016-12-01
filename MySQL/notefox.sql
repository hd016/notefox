-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 25. Nov 2016 um 17:42
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
-- Tabellenstruktur für Tabelle `business object`
--

CREATE TABLE `business object` (
  `id` int(50) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `erstelltVon` varchar(50) NOT NULL,
  `erstelltDatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `business object`
--

INSERT INTO `business object` (`id`, `name`, `erstelltVon`, `erstelltDatum`) VALUES
(1, '1.Business_Object', 'Mansur', '2016-11-24'),
(2, '2.Business_Object', 'Mansur', '2016-11-25');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `datum`
--

CREATE TABLE `datum` (
  `faelligkeitId` int(50) NOT NULL,
  `status` tinyint(1) NOT NULL,
  `faelligkeitsdatum` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `datum`
--

INSERT INTO `datum` (`faelligkeitId`, `status`, `faelligkeitsdatum`) VALUES
(1, 1, '2016-11-02'),
(2, 0, '2016-11-08');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notiz`
--

CREATE TABLE `notiz` (
  `NotizId` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notiz`
--

INSERT INTO `notiz` (`NotizId`) VALUES
(1),
(2);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizbuch`
--

CREATE TABLE `notizbuch` (
  `NotizbuchId` int(50) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizbuch`
--

INSERT INTO `notizbuch` (`NotizbuchId`) VALUES
(1),
(2),
(3);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizobjekt`
--

CREATE TABLE `notizobjekt` (
  `titel` char(100) NOT NULL,
  `subtitel` char(100) NOT NULL,
  `erstelldatum` date NOT NULL,
  `modifikationsdatum` date NOT NULL,
  `inhalt` text NOT NULL,
  `eigentuemer` char(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizobjekt`
--

INSERT INTO `notizobjekt` (`titel`, `subtitel`, `erstelldatum`, `modifikationsdatum`, `inhalt`, `eigentuemer`) VALUES
('Studium', 'IT-Projekt', '2016-11-22', '2016-11-24', 'Hier ist Inhalt', 'Mansur'),
('Hobby', 'Mein Hobbys', '2016-11-03', '2016-11-15', '- Filme schauen\r\n- Strategie Spiele\r\nusv.', 'Mansur');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notizquelle`
--

CREATE TABLE `notizquelle` (
  `NotizquelleId` int(50) NOT NULL,
  `notizquelleName` char(100) NOT NULL,
  `URL` char(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notizquelle`
--

INSERT INTO `notizquelle` (`NotizquelleId`, `notizquelleName`, `URL`) VALUES
(1, '1.Test', 'www.google.de'),
(2, '2.Test', 'www.zeit.de');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `nutzer`
--

CREATE TABLE `nutzer` (
  `nutzerId` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `nutzer`
--

INSERT INTO `nutzer` (`nutzerId`, `name`, `password`) VALUES
(1, 'mansur jumaboev', '1234'),
(2, 'Max Mustermann', '4321');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `business object`
--
ALTER TABLE `business object`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `business object` ADD FULLTEXT KEY `name` (`name`);

--
-- Indizes für die Tabelle `datum`
--
ALTER TABLE `datum`
  ADD PRIMARY KEY (`faelligkeitId`);

--
-- Indizes für die Tabelle `notiz`
--
ALTER TABLE `notiz`
  ADD PRIMARY KEY (`NotizId`);

--
-- Indizes für die Tabelle `notizbuch`
--
ALTER TABLE `notizbuch`
  ADD PRIMARY KEY (`NotizbuchId`);

--
-- Indizes für die Tabelle `notizobjekt`
--
ALTER TABLE `notizobjekt` ADD FULLTEXT KEY `titel` (`titel`);
ALTER TABLE `notizobjekt` ADD FULLTEXT KEY `subtitel` (`subtitel`);

--
-- Indizes für die Tabelle `notizquelle`
--
ALTER TABLE `notizquelle`
  ADD PRIMARY KEY (`NotizquelleId`);
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
-- AUTO_INCREMENT für Tabelle `business object`
--
ALTER TABLE `business object`
  MODIFY `id` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT für Tabelle `notizbuch`
--
ALTER TABLE `notizbuch`
  MODIFY `NotizbuchId` int(50) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT für Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  MODIFY `nutzerId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `datum`
--
ALTER TABLE `datum`
  ADD CONSTRAINT `datum_ibfk_1` FOREIGN KEY (`faelligkeitId`) REFERENCES `notiz` (`NotizId`) ON UPDATE NO ACTION;

--
-- Constraints der Tabelle `notiz`
--
ALTER TABLE `notiz`
  ADD CONSTRAINT `notiz_ibfk_1` FOREIGN KEY (`NotizId`) REFERENCES `notizquelle` (`NotizquelleId`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
