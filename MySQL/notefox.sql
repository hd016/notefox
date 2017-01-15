-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 173.194.104.160
-- Erstellungszeit: 08. Jan 2017 um 19:48
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
(6002, 'LESEN', 3, 2012, 1000),
(6003, 'LESEN', 0, 2005, 0),
(6004, 'EDITIEREN', 0, 2012, 1000),
(6005, 'EDITIEREN', 0, 2005, 1000),
(6006, 'EDITIEREN', 1, NULL, 1000),
(6007, 'LOESCHEN', 1, 2012, 1008),
(6008, 'LOESCHEN', NULL, NULL, 1037),
(6009, 'EDITIEREN', NULL, NULL, 1037),
(6010, 'EDITIEREN', NULL, NULL, 1037),
(6011, 'LESEN', NULL, NULL, 1037),
(6012, 'LESEN', NULL, NULL, 1037),
(6013, 'LESEN', NULL, NULL, 1037),
(6015, 'LESEN', NULL, 2007, 1037),
(6016, 'LESEN', 67, NULL, 1037),
(6017, 'LOESCHEN', 67, NULL, 1037),
(6018, 'LESEN', NULL, 2025, 1037),
(6019, 'LESEN', 72, NULL, 1037),
(6020, 'LOESCHEN', NULL, 2007, 1037),
(6021, 'LESEN', 89, NULL, 1037),
(6022, 'LESEN', NULL, 2027, 1037),
(6023, 'LOESCHEN', 112, NULL, 1037);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `notiz`
--

CREATE TABLE `notiz` (
  `id` int(11) UNSIGNED NOT NULL,
  `eigentuemer` int(11) NOT NULL,
  `titel` text NOT NULL,
  `subtitel` varchar(50) NOT NULL,
  `inhalt` text NOT NULL,
  `erstelldatum` date NOT NULL,
  `modifikationsdatum` date NOT NULL,
  `notizbuch` int(11) NOT NULL,
  `faelligkeitsdatum` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `notiz`
--

INSERT INTO `notiz` (`id`, `eigentuemer`, `titel`, `subtitel`, `inhalt`, `erstelldatum`, `modifikationsdatum`, `notizbuch`, `faelligkeitsdatum`) VALUES
(3, 1000, 'Hallo', 'WI7', 'hier_ist_inhalt', '2016-12-11', '2016-12-11', 0, '2016-12-12 00:00:00'),
(4, 2000, 'Mansur', 'null', 'INhalt', '2016-12-11', '2016-12-11', 0, '2016-12-12 00:00:00'),
(45, 1025, 'unbenannt123', '', '', '2016-12-18', '2016-12-18', 2014, '2016-12-18 00:00:00'),
(47, 1025, 'unbenannt111111111111', '', '', '2016-12-18', '2016-12-18', 2014, '2016-12-18 00:00:00'),
(61, 1025, 'NeuNotiz', '', 'LIZGli Pgl<div>t h</div><div>r</div><div>zt</div><div>j</div><div>tj</div><div>zk</div><div>7k</div><div>c7k</div><div>7ck</div><div>7kc</div><div>k</div><div>7k7</div><div>k7</div><div>k7</div><div>k7</div><div>k7</div><div>k777&nbsp;</div><div>77</div><div>i7</div><div>i7</div><div>i7</div><div>i7u</div><div>7</div><div>7i</div><div>7i</div><div>7i</div><div>7i</div><div>7i7</div><div>i7</div><div>i7</div><div>7</div><div><br></div>', '2016-12-18', '2016-12-18', 2019, '2016-12-18 00:00:00'),
(62, 1008, 'http://s.codepen.io/boomerang/449c616f49146604f5d92219a9ee92da1482089467037/index.html', '', '<!DOCTYPE html><html class=''''><head><script src=''//production-assets.codepen.io/assets/editor/live/console_runner-5710c30fb566082d9fcb6e7d97ee7e3f2a326128c9f334a4231b6fd752b29965.js''></script><script src=''//production-assets.codepen.io/assets/editor/live/events_runner-d5e4bf42585b8da8c18f7d963dbfc17cd66a79aa586c9448c4de8d6952ee9d97.js''></script><script src=''//production-assets.codepen.io/assets/editor/live/css_live_reload_init-25d1423d5d6fb975e7d61832d2c061422a94963ca446583b965dfc5569147311.js''></script><meta charset=''UTF-8''><meta name="robots" content="noindex"><link rel="shortcut icon" type="image/x-icon" href="//production-assets.codepen.io/assets/favicon/favicon-8ea04875e70c4b0bb41da869e81236e54394d63638a1ef12fa558a4a835f1164.ico" /><link rel="mask-icon" type="" href="//production-assets.codepen.io/assets/favicon/logo-pin-f2d2b6d2c61838f7e76325261b7195c27224080bc099486ddd6dccb469b8e8e6.svg" color="#111" /><link rel="canonical" href="http://codepen.io/pen/" /><style class="cp-pen-styles"></style></head><body> <a id="note-fox-link" href="#">Link</a><script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script></body></html>', '2016-12-18', '2016-12-18', 2023, '2016-12-18 00:00:00'),
(63, 1029, 'Notiz-Muster1', '', 're zgrthtzjtz<div>j j</div><div>uru</div><div>krk</div><div>&nbsp;r</div><div>ik&nbsp;</div><div>zik irzk r</div><div>k r</div><div>ikr</div><div>kr</div><div>zkr</div><div>zk</div><div>hil</div><div>zu löz</div><div>öo</div><div>ö</div><div>i</div><div>öäö</div><div>i</div><div>0ä</div><div>0ä</div><div>0ä</div><div>0ä</div><div>00</div><div>ä</div><div>0ä</div><div>0ä</div><div>j0</div><div>ää</div><div>0ä</div><div>0ä</div><div>0ä</div><div>0ä</div><div>0öhä</div><div>0</div><div>ä0ä0</div><div>ä</div><div>0ä</div><div>0ä</div><div>0ä</div>', '2016-12-18', '2016-12-18', 2022, '2016-12-18 00:00:00'),
(64, 1030, 'http://127.0.0.1:8888/test.html', '', '<html><head></head><body>	<a id="note-fox-link" href="#">Link</a>	<script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script></body></html>', '2016-12-18', '2016-12-18', 2024, '2016-12-18 00:00:00'),
(66, 1030, 'http://127.0.0.1:8888/test.html', '', '<html><head></head><body>	<a id="note-fox-link" href="#">Link</a>	<script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script></body></html>', '2016-12-18', '2016-12-18', 2024, '2016-12-18 00:00:00'),
(70, 1032, 'Fußball1', '', 'Inhalt<div>Inhalt</div><div>Inhalt</div><div><br></div>', '2016-12-20', '2016-12-20', 2026, '2016-12-20 00:00:00'),
(71, 1012, 'Hallo DU da', '', 'ok ok ok ok ok ok ok ok ok ok &nbsp;ok ok ok &nbsp;ok ok', '2016-12-20', '2017-01-08', 2027, '2016-12-20 00:00:00'),
(72, 1009, 'AAAAAAAAAAAAAAA', '', '          <p><strong>Füge diese Webseiten-URL auf NoteFox-Notiz ein!</strong></p><p><a href="http://2-dot-notefox-150119.appspot.com?url=http://www.google.de" target="_blank">Link</a></p>    <p>&nbsp;</p>    <a id="note-fox-link" href="#"><img src="http://leqsico.de/notefox/notefox+.png" alt="" width="500" height="300"></a><script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script>    ', '2016-12-20', '2017-01-08', 2007, '2016-12-20 00:00:00'),
(74, 1032, 'http://leqsico.de/notefox/notefoxfremd.html', '', '      <p><strong>Kapitel:&nbsp;Lorem ipsum dolor sit amet und seine Geschichte</strong></p>  <p><strong>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</strong></p>  <p>&nbsp;</p>  <p><strong>Teile diesen Inhalt auf Notefox!</strong></p>  <p>&nbsp;</p>  <p>&nbsp;</p>    <a id="note-fox-link" href="#"><img src="http://leqsico.de/notefox/notefox+.png" alt="" width="500" height="300"></a><script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script>    ', '2016-12-20', '2017-01-08', 2007, '2016-12-20 00:00:00'),
(76, 1029, 'esratzzttz', '', '      <p><strong>Kapitel:&nbsp;Lorem ipsum dolor sit amet und seine Geschichte</strong></p>  <p><strong>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</strong></p>  <p>&nbsp;</p>  <p><strong>Teile diesen Inhalt auf Notefox!</strong></p>  <p>&nbsp;</p>  <p>&nbsp;</p>    <a id="note-fox-link" href="#"><img src="http://leqsico.de/notefox/notefox+.png" alt="" width="500" height="300"></a><script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script>    ', '2016-12-20', '2017-01-08', 2007, '2016-12-20 00:00:00'),
(77, 1035, 'Datenbank', '', '<b>Inhalt</b><div><b><br></b><div><font color="#ff0000" style="background-color: green;">Inhalt</font></div><div><br></div><div>Inhalt</div><div>Inhalt</div><div>Inhalt</div></div>', '2016-12-20', '2016-12-20', 2029, '2016-12-20 00:00:00'),
(78, 1036, 'Game Design', '', '&nbsp;e rxh<div>j</div><div>ctj</div><div>t</div><div><span style="background-color: black;">j7kk7k</span></div>', '2016-12-20', '2016-12-20', 2030, '2016-12-20 00:00:00'),
(80, 1036, 'fallligkeittest', '', 'fallligkeittest', '2016-12-20', '2016-12-20', 2030, '2016-12-20 00:00:00'),
(82, 1029, 'ssgsdgsdg', '', '      <p><strong>Kapitel:&nbsp;Lorem ipsum dolor sit amet und seine Geschichte</strong></p><p><strong>Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.</strong></p>  <p>&nbsp;</p><p><strong>Teile diesen Inhalt auf Notefox!</strong></p><a id="note-fox-link" href="#"><img src="http://leqsico.de/notefox/notefox+.png" alt="" width="auto" height="50"></a><script>var notefoxUrl = "http://127.0.0.1:8888/";document.getElementById("note-fox-link").setAttribute("href", notefoxUrl + "?url=" + window.location);</script>    ', '2016-12-20', '2017-01-08', 2007, '2016-12-20 00:00:00'),
(83, 1040, 'harun', '', 'harun', '2017-01-08', '2017-01-08', 2031, '2017-01-08 00:00:00');

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
(2004, 1000, 'Hallo', 'WI7', '2016-12-11', '2016-12-11'),
(2005, 1000, 'Hallo', 'WI7', '2016-12-13', '2016-12-13'),
(2008, 1018, 'PK', '', '2016-12-18', '2016-12-18'),
(2010, 1019, '', '', '2016-12-18', '2016-12-18'),
(2011, 1018, 'OKOKOK', '', '2016-12-18', '2016-12-18'),
(2016, 1026, 'unbenannt', '', '2016-12-18', '2016-12-18'),
(2017, 1025, 'Neu111', 'Subtitel111', '2016-12-18', '2016-12-18'),
(2018, 1025, 'unbenannt', '', '2016-12-18', '2016-12-18'),
(2019, 1025, 'NeueNeue', 'Subtitelll', '2016-12-18', '2016-12-18'),
(2020, 1027, 'Notizbuch1', 'Studium', '2016-12-18', '2016-12-18'),
(2021, 1028, 'Externe Webseiten', '', '2016-12-18', '2016-12-18'),
(2022, 1029, 'NeuNotizbuch1', 'Subtitel1', '2016-12-18', '2016-12-18'),
(2023, 1008, 'Externe Webseiten', '', '2016-12-18', '2016-12-18'),
(2024, 1030, 'Externe Webseiten', '', '2016-12-18', '2016-12-18'),
(2026, 1032, 'Hobbys', 'Fußball', '2016-12-20', '2016-12-20'),
(2028, 1033, 'dsdsds', 'dsds', '2016-12-20', '2016-12-20'),
(2029, 1035, 'Studium', 'IT-Projekt', '2016-12-20', '2016-12-20'),
(2030, 1036, 'Notizbuch1', 'Subtitel1', '2016-12-20', '2016-12-20'),
(2031, 1040, 'unbenannt', '', '2017-01-08', '2017-01-08');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `nutzer`
--

CREATE TABLE `nutzer` (
  `nutzerId` int(10) UNSIGNED NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `nutzer`
--

INSERT INTO `nutzer` (`nutzerId`, `name`, `email`) VALUES
(1000, '', 'mansur@gmail.com'),
(1001, '', 'dejan@gmail.com'),
(1004, '', 'muhammed@gmail.com '),
(1005, 'muhammed', 'muhammed1@gmail.com'),
(1008, 'null', 'harun@example.com'),
(1009, 'null', 'Esra@example.com'),
(1012, 'null', 'Waldemar@example.com'),
(1013, 'null', 'Waldemar2@example.com'),
(1014, 'null', 'Irgendwas@example.com'),
(1020, 'null', 'neriman@google.de'),
(1021, 'null', 'HDM@example.com'),
(1022, 'null', 'Stuttgart@gmail.com'),
(1023, 'null', 'HuHu@example.com'),
(1025, 'null', 'Muhammed2@example.com'),
(1027, 'null', 'MansurBek@example.com'),
(1028, 'null', 'admin@example.com'),
(1029, 'null', 'harun12@google.com'),
(1030, 'null', 'ppppp@example.com'),
(1032, 'null', 'MansurUz@google.com'),
(1034, 'null', 'MAXMUSTERMAN@example.com'),
(1035, 'null', 'Irgendjemand@example.com'),
(1036, 'null', 'Dejan2@example.com'),
(1037, 'null', 'harun11@gmail.com'),
(1039, 'null', 'test@example.com'),
(1040, 'null', 'neri@example.com');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `berechtigung`
--
ALTER TABLE `berechtigung`
  ADD PRIMARY KEY (`berechtigungId`);

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
  MODIFY `berechtigungId` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6024;
--
-- AUTO_INCREMENT für Tabelle `notiz`
--
ALTER TABLE `notiz`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=127;
--
-- AUTO_INCREMENT für Tabelle `notizbuch`
--
ALTER TABLE `notizbuch`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2035;
--
-- AUTO_INCREMENT für Tabelle `nutzer`
--
ALTER TABLE `nutzer`
  MODIFY `nutzerId` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1041;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
