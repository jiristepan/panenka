# MySQL-Front Dump 2.5
#
# Host: localhost   Database: panenka
# --------------------------------------------------------
# Server version 4.0.20a-nt-log

USE panenka;


#
# Table structure for table 'aux_reservation_person'
#

DROP TABLE IF EXISTS aux_reservation_person;
CREATE TABLE aux_reservation_person (
  person_id int(11) NOT NULL default '0',
  reservation_id int(11) NOT NULL default '0'
) TYPE=MyISAM;



#
# Dumping data for table 'aux_reservation_person'
#

INSERT INTO aux_reservation_person VALUES("1", "23");
INSERT INTO aux_reservation_person VALUES("1", "28");
INSERT INTO aux_reservation_person VALUES("1", "27");
INSERT INTO aux_reservation_person VALUES("1", "31");
INSERT INTO aux_reservation_person VALUES("40", "31");
INSERT INTO aux_reservation_person VALUES("90", "33");
INSERT INTO aux_reservation_person VALUES("90", "39");
INSERT INTO aux_reservation_person VALUES("90", "41");
INSERT INTO aux_reservation_person VALUES("106", "41");
INSERT INTO aux_reservation_person VALUES("107", "41");
INSERT INTO aux_reservation_person VALUES("108", "41");
INSERT INTO aux_reservation_person VALUES("90", "42");
INSERT INTO aux_reservation_person VALUES("109", "42");
INSERT INTO aux_reservation_person VALUES("112", "43");
INSERT INTO aux_reservation_person VALUES("111", "43");
INSERT INTO aux_reservation_person VALUES("109", "43");
INSERT INTO aux_reservation_person VALUES("90", "43");
INSERT INTO aux_reservation_person VALUES("113", "44");
INSERT INTO aux_reservation_person VALUES("114", "44");
INSERT INTO aux_reservation_person VALUES("115", "44");
INSERT INTO aux_reservation_person VALUES("116", "44");
INSERT INTO aux_reservation_person VALUES("117", "44");
INSERT INTO aux_reservation_person VALUES("118", "44");
INSERT INTO aux_reservation_person VALUES("119", "44");
INSERT INTO aux_reservation_person VALUES("120", "44");
INSERT INTO aux_reservation_person VALUES("121", "44");
INSERT INTO aux_reservation_person VALUES("122", "44");
INSERT INTO aux_reservation_person VALUES("123", "44");
INSERT INTO aux_reservation_person VALUES("124", "44");
INSERT INTO aux_reservation_person VALUES("125", "44");
INSERT INTO aux_reservation_person VALUES("126", "44");
INSERT INTO aux_reservation_person VALUES("127", "44");
INSERT INTO aux_reservation_person VALUES("128", "44");
INSERT INTO aux_reservation_person VALUES("129", "44");
INSERT INTO aux_reservation_person VALUES("130", "44");
INSERT INTO aux_reservation_person VALUES("131", "44");
INSERT INTO aux_reservation_person VALUES("132", "44");
INSERT INTO aux_reservation_person VALUES("133", "44");
INSERT INTO aux_reservation_person VALUES("134", "45");
INSERT INTO aux_reservation_person VALUES("135", "45");
INSERT INTO aux_reservation_person VALUES("136", "45");
INSERT INTO aux_reservation_person VALUES("137", "45");
INSERT INTO aux_reservation_person VALUES("138", "45");
INSERT INTO aux_reservation_person VALUES("139", "45");
INSERT INTO aux_reservation_person VALUES("140", "45");
INSERT INTO aux_reservation_person VALUES("141", "45");
INSERT INTO aux_reservation_person VALUES("142", "45");
INSERT INTO aux_reservation_person VALUES("143", "45");
INSERT INTO aux_reservation_person VALUES("144", "45");
INSERT INTO aux_reservation_person VALUES("145", "45");
INSERT INTO aux_reservation_person VALUES("146", "45");
INSERT INTO aux_reservation_person VALUES("147", "45");
INSERT INTO aux_reservation_person VALUES("148", "45");
INSERT INTO aux_reservation_person VALUES("149", "45");
INSERT INTO aux_reservation_person VALUES("150", "45");
INSERT INTO aux_reservation_person VALUES("151", "45");
INSERT INTO aux_reservation_person VALUES("152", "45");
INSERT INTO aux_reservation_person VALUES("153", "45");
INSERT INTO aux_reservation_person VALUES("154", "45");
INSERT INTO aux_reservation_person VALUES("155", "45");
INSERT INTO aux_reservation_person VALUES("156", "45");


#
# Table structure for table 'board'
#

DROP TABLE IF EXISTS board;
CREATE TABLE board (
  id int(10) unsigned NOT NULL auto_increment,
  date datetime NOT NULL default '0000-00-00 00:00:00',
  message text,
  title varchar(255) NOT NULL default '',
  published tinyint(3) unsigned NOT NULL default '0',
  category int(11) NOT NULL default '0',
  author int(10) unsigned NOT NULL default '0',
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'board'
#

INSERT INTO board VALUES("1", "2004-11-06 00:00:00", "pokusna hlaska", "Pokus", "1", "1", "1");
INSERT INTO board VALUES("2", "2004-11-08 21:37:56", "nìco pro výbor", "Pro výbor", "1", "1", "1");
INSERT INTO board VALUES("3", "2004-11-08 21:37:30", "<strong>schùze</strong>", "Schùze výboru", "1", "1", "1");
INSERT INTO board VALUES("4", "2004-11-08 21:44:16", NULL, "nová", "1", "0", "1");
INSERT INTO board VALUES("5", "2004-11-08 21:44:19", NULL, "nová", "1", "0", "1");
INSERT INTO board VALUES("6", "2004-11-08 21:47:00", NULL, "nová", "1", "0", "1");
INSERT INTO board VALUES("10", "2004-11-08 22:00:27", "<ul>\r\n<li>adfkljfdsf</li>\r\n</ul>", "Informace pro èleny", "1", "2", "1");
INSERT INTO board VALUES("8", "2004-11-08 21:52:11", "huhua", "Test", "1", "3", "1");


#
# Table structure for table 'board_category'
#

DROP TABLE IF EXISTS board_category;
CREATE TABLE board_category (
  id int(10) unsigned NOT NULL auto_increment,
  title varchar(50) NOT NULL default '',
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'board_category'
#

INSERT INTO board_category VALUES("1", "Veøejná informace");
INSERT INTO board_category VALUES("2", "Informace pro èleny");
INSERT INTO board_category VALUES("3", "Informace pro výbor");
INSERT INTO board_category VALUES("4", "Pro administrátory");
INSERT INTO board_category VALUES("5", "Nepublikovaná informace");


#
# Table structure for table 'can_subscribe'
#

DROP TABLE IF EXISTS can_subscribe;
CREATE TABLE can_subscribe (
  subscriber int(11) NOT NULL default '0',
  subject int(11) NOT NULL default '0'
) TYPE=MyISAM;



#
# Dumping data for table 'can_subscribe'
#

INSERT INTO can_subscribe VALUES("5", "2");
INSERT INTO can_subscribe VALUES("5", "6");
INSERT INTO can_subscribe VALUES("6", "5");
INSERT INTO can_subscribe VALUES("1", "5");
INSERT INTO can_subscribe VALUES("2", "2");
INSERT INTO can_subscribe VALUES("1", "2");
INSERT INTO can_subscribe VALUES("6", "2");
INSERT INTO can_subscribe VALUES("1", "40");


#
# Table structure for table 'event_log'
#

DROP TABLE IF EXISTS event_log;
CREATE TABLE event_log (
  id int(11) NOT NULL auto_increment,
  event_time datetime NOT NULL default '0000-00-00 00:00:00',
  logged_user_id int(11) NOT NULL default '0',
  logged_user_name varchar(60) NOT NULL default '',
  event_type varchar(10) NOT NULL default '',
  event_params varchar(255) NOT NULL default '',
  event_ip varchar(20) NOT NULL default '',
  event_remote_host text,
  event_user_cookie varchar(50) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'event_log'
#

INSERT INTO event_log VALUES("1", "2004-05-19 22:29:30", "1", "Jiri Stepan", "ADD_RESERV", "{action=[Ljava.lang.String;@df9095, date_from=[Ljava.lang.String;@18d4c9a, submitReservation=[Ljava.lang.String;@1a02097, date_to=[Ljava.lang.String;@1bbc779, note=[Ljava.lang.String;@124e935, nights=[Ljava.lang.String;@4ac866}", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("2", "2004-05-19 22:30:13", "1", "Jiri Stepan", "ADD_RESERV", "{action=[Ljava.lang.String;@1cdfd19, date_from=[Ljava.lang.String;@a37c6a, submitReservation=[Ljava.lang.String;@13d21d6, date_to=[Ljava.lang.String;@7881db, member=[Ljava.lang.String;@1956391, note=[Ljava.lang.String;@bbf7aa, nights=[Ljava.lang.String;@8", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("3", "2004-05-19 22:31:26", "1", "Jiri Stepan", "CHANGE_RES", "{action=[Ljava.lang.String;@75b3ee, date_from=[Ljava.lang.String;@177fa9a, submitReservation=[Ljava.lang.String;@1e5ba24, date_to=[Ljava.lang.String;@11fc4b8, member=[Ljava.lang.String;@ac4d3b, note=[Ljava.lang.String;@29d75, nights=[Ljava.lang.String;@1a", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("4", "2004-05-19 22:31:59", "1", "Jiri Stepan", "CHANGE_RES", "{action=[Ljava.lang.String;@294f62, date_from=[Ljava.lang.String;@1abf87, submitReservation=[Ljava.lang.String;@1450bd, date_to=[Ljava.lang.String;@1c0bee6, member=[Ljava.lang.String;@1352447, note=[Ljava.lang.String;@1e0e954, nights=[Ljava.lang.String;@f", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("5", "2004-05-19 22:42:33", "1", "Jiri Stepan", "CHANGE_RES", "(action:[Ljava.lang.String;@152643)(date_from:[Ljava.lang.String;@8321c8)(submitReservation:[Ljava.lang.String;@11ee017)(date_to:[Ljava.lang.String;@a39de)(member:[Ljava.lang.String;@15d45d9)(note:[Ljava.lang.String;@94aa42)(nights:[Ljava.lang.String;@35d", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("6", "2004-05-19 22:48:29", "1", "Jiri Stepan", "CHANGE_RES", "(action:[Ljava.lang.String;@1f9f0f2)(date_from:[Ljava.lang.String;@39d325)(submitReservation:[Ljava.lang.String;@5539d8)(date_to:[Ljava.lang.String;@148c02f)(member:[Ljava.lang.String;@14189d0)(note:[Ljava.lang.String;@1bef5e8)(nights:[Ljava.lang.String;@", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("7", "2004-05-19 22:49:39", "1", "Jiri Stepan", "CHANGE_RES", "(action:[Ljava.lang.String;@1cb048e)(date_from:[Ljava.lang.String;@1983ad7)(submitReservation:[Ljava.lang.String;@13f348b)(date_to:[Ljava.lang.String;@92997e)(member:[Ljava.lang.String;@9b601d)(note:[Ljava.lang.String;@c3362f)(nights:[Ljava.lang.String;@1", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("8", "2004-05-19 22:50:37", "1", "Jiri Stepan", "ADD_RESERV", "!(action:[Ljava.lang.String;@1408325)!(date_from:[Ljava.lang.String;@623367)!(submitReservation:[Ljava.lang.String;@11ce2ad)!(date_to:[Ljava.lang.String;@16602cb)!(member:[Ljava.lang.String;@4178d0)!(note:[Ljava.lang.String;@62be97)!(nights:[Ljava.lang.St", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("9", "2004-05-19 22:58:17", "1", "Jiri Stepan", "ADD_RESERV", "(date_to:27.6.2004,)(nights:3,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:24.6.2004,)(member:1,40,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("10", "2004-05-20 21:08:29", "90", "Jiøí Štìpán", "ADD_RESERV", "(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(action:new,)(note:,)(date_to:5.5.2004,)(nights:1,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("11", "2004-05-20 21:08:39", "90", "Jiøí Štìpán", "ADD_RESERV", "(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(action:new,)(member:90,)(note:,)(date_to:8.5.2004,)(nights:4,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("12", "2004-05-20 21:31:08", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:8.5.2004,)(nights:4,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(new_nonmebers:Klára Bezpalcová\r\nJan Jurèák				,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("13", "2004-05-20 21:34:23", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:9.5.2004,)(nights:5,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(new_nonmebers:Jiri Jirikovic\r\nHuhan Zeleny					,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("14", "2004-05-20 21:34:52", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:9.5.2004,)(nights:5,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(new_nonmebers:Jiri Jirikovic\r\nHuhan Zeleny					,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("15", "2004-05-20 21:35:47", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:9.5.2004,)(nights:5,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(new_nonmebers:Huhan Zeleny\r\nJosef Visarionovic,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("16", "2004-05-20 21:37:46", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:9.5.2004,)(nights:5,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:4.5.2004,)(new_nonmebers:Huhan Zeleny\r\nJosef Visarionovic,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("17", "2004-05-20 21:39:53", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:18.5.2004,)(nights:7,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:11.5.2004,)(new_nonmebers:neclen neclenovic\r\nkarel manas,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("18", "2004-05-20 21:41:26", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:24.5.2004,)(nights:6,)(new_nonmembers:karel nulovic\r\nvodic zeleny\r\nhulan modry,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:18.5.2004,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("19", "2004-05-20 21:46:23", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:24.5.2004,)(nights:6,)(new_nonmembers:karel nulovic\r\nvodic zeleny\r\nhulan modry,)(note:,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:18.5.2004,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("20", "2004-05-20 21:48:23", "90", "Jiøí Štìpán", "ADD_RESERV", "(date_to:3.6.2004,)(nights:1,)(new_nonmembers:jarek zeleny	,)(note:huh,)(action:new,)(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:2.6.2004,)(member:90,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("21", "2004-05-20 21:58:23", "90", "Jiøí Štìpán", "ADD_RESERV", "(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:31.5.2004,)(action:new,)(member:90,)(new_nonmembers:kamil novak\r\nhuhan modry	,)(note:,)(nonmember:109,)(nights:5,)(date_to:5.6.2004,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("22", "2004-05-20 21:58:43", "90", "Jiøí Štìpán", "CHANGE_RES", "(submitReservation: -----Zmìnit rezervaci----- ,)(date_from:31.5.2004,)(action:update,)(new_nonmembers:jan stoklasa,)(member:90,)(note:,)(nonmember:109,111,)(date_to:5.6.2004,)(nights:5,)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("23", "2004-05-20 22:21:49", "91", "Tereza Štìpánová", "UNAUTHORIZ", "Pokus o editaci uzivatele Kristýna Tìlupilová(104)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("24", "2004-05-20 22:22:17", "91", "Tereza Štìpánová", "UNAUTHORIZ", "Pokus o editaci uzivatele Kristýna Tìlupilová(104)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("25", "2004-05-20 22:22:24", "91", "Tereza Štìpánová", "UNAUTHORIZ", "Pokus o editaci uzivatele null null(0)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("26", "2004-05-20 22:27:46", "90", "Jiøí Štìpán", "UNAUTHORIZ", "Pokus o editaci uzivatele Tereza Štìpánová(91)", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("27", "2004-05-20 22:36:38", "1", "Administrator", "ADD_RESERV", "(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:12.5.2004,)(action:new,)(new_nonmembers:asdf\r\nasf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasdfsadf\r\nasdf\r\nddd\r\ndd\r\ndddd\r\nddd\r\nddd\r\ndfadsf\r\nasdf\r\nasdf\r\nasdf\r\ndasf\r\ndfas					,)(note:,)(date_to:16.5.2", "127.0.0.1", "localhost", "---");
INSERT INTO event_log VALUES("28", "2004-05-20 22:37:28", "1", "Administrator", "ADD_RESERV", "(submitReservation: ---- Potvrdit rezervaci --- ,)(date_from:13.5.2004,)(action:new,)(new_nonmembers:as adskjh\r\nafk asdf\r\nadf \r\nas\r\nasdf\r\nadsf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasf\r\nasf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasfd\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasdf\r\nasdf,)(note:,)(da", "127.0.0.1", "localhost", "---");


#
# Table structure for table 'person'
#

DROP TABLE IF EXISTS person;
CREATE TABLE person (
  id int(11) NOT NULL auto_increment,
  login varchar(20) default NULL,
  password varchar(10) default NULL,
  name varchar(20) NOT NULL default '',
  surname varchar(20) NOT NULL default '',
  apendix varchar(5) default '',
  email varchar(255) default NULL,
  phone1 varchar(30) default NULL,
  phone2 varchar(30) default NULL,
  address varchar(255) default NULL,
  member tinyint(4) NOT NULL default '0',
  admin tinyint(4) NOT NULL default '0',
  manager tinyint(4) NOT NULL default '0',
  granted_by int(11) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'person'
#

INSERT INTO person VALUES("1", "admin", "admin", "Administrator", "", "", "jiri.stepan@etnetera.cz", "602603906", "", "Fr. Kloze 1846, Kladno", "1", "1", "1", NULL);
INSERT INTO person VALUES("7", "jakub.becka", "test", "Jakub", "Beèka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("8", "tomas.becka", "test", "Tomáš", "Beèka", "", "", "", "", "", "1", "0", "0", "-1");
INSERT INTO person VALUES("9", "zdenek.becka", "test", "Zdenìk", "Beèka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("10", "zdenek.becka.ml", "test", "Zdenìk", "Beèka", "ml.", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("11", "ladislava.beckova", "test", "Ladislava", "Beèková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("12", "veronika.beckova", "test", "Veronika", "Beèková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("13", "zuzana.beckova", "test", "Zuzana", "Beèková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("14", "sona.benesova", "test", "Soòa", "Benešová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("15", "stanislav.cech", "test", "Stanislav", "Èech", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("16", "simona.cechova", "test", "Simona", "Èechová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("17", "kveta.cerna", "test", "Kvìta", "Èerná", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("18", "zdenka.cerna", "test", "Zdeòka", "Èerná", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("19", "kvetoslav.cerny", "test", "Kvìtoslav", "Èerný", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("20", "dada.funge", "test", "Dáda", "Funge", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("21", "jiri.hamouz", "test", "Jiøí", "Hamouz", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("22", "jan.hanus", "test", "Jan", "Hanuš", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("23", "micha.hanus", "test", "Michal", "Hanuš", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("24", "jitka.hanusova", "test", "Jitka", "Hanušová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("25", "milos.hasek", "test", "Miloš", "Hašek", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("26", "lucie.haskova", "test", "Lucie", "Hašková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("27", "miroslav.hering", "test", "Miroslav", "Herink", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("28", "zdenek.hoeniger", "test", "Zdenìk", "Höniger", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("29", "zdenka.hoenigerova", "test", "Zdeòka", "Hönigerová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("30", "jiri.kamensky", "test", "Jiøí", "Kamenský", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("31", "tomas.kamensky", "test", "Tomáš", "Kamenský", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("32", "jindrich.kohout", "test", "Jindøich", "Kohout", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("33", "pavel.kolouch", "test", "Pavel", "Kolouch", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("34", "petr.kolouch", "test", "Petr", "Kolouch", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("35", "petr.kolouch.ml", "test", "Petr", "Kolouch", "ml.", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("36", "dasa.kolouchova", "test", "Dáša", "Kolouchová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("37", "eva.koutna", "test", "Eva", "Koutná", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("38", "petr.kratochvil", "test", "Petr", "Kratochvíl", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("39", "ilona.kratochvilova", "test", "Ilona", "Kratochvílová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("40", "karel.kraus", "test", "Karel", "Kraus", "", "", "", "", "", "1", "0", "0", NULL);
INSERT INTO person VALUES("41", "libuse.krausova", "test", "Libuše", "Krausová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("42", "petr.krten", "test", "Petr", "Køtìn", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("43", "pavla.kulikovska", "test", "Pavla", "Kulikovská", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("44", "jiri.laurenc", "test", "Jiøí", "Laurenc", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("45", "jiri.laurenc.ml", "test", "Jiøí", "Laurenc", "ml.", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("46", "ota.laurenc", "test", "Ota", "Laurenc", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("47", "radek.laurenc", "test", "Radek", "Laurenc", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("48", "alena.laurencova", "test", "Alena", "Laurencová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("49", "eva.laurencova", "test", "Eva", "Laurencová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("50", "jirina.laurencova", "test", "Jiøina", "Laurencová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("51", "katerina.laurencova", "test", "Kateøina", "Laurencová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("52", "petra.laurencova", "test", "Petra", "Laurencová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("53", "radek.lhota", "test", "Radek", "Lhota", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("54", "jiri.lukes", "test", "Jiøí", "Lukeš", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("55", "marie.lukesova", "test", "Marie", "Lukešová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("56", "marie.lukesova.ml", "test", "Marie", "Lukešová", "ml.", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("57", "marie.makalousova", "test", "Marie", "Makaloušová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("58", "jana.martinova", "test", "Jana", "Martinová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("59", "hana.nocarova", "test", "Hana", "Nocarová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("60", "miluse.nohejlova", "test", "Miluše", "Nohejlová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("61", "vlasta.nohejlova", "test", "Vlasta", "Nohejlová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("62", "lenka.obrovcova", "test", "Lenka", "Obrovcová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("63", "michaela.obrovcova", "test", "Michaela", "Obrovcová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("64", "vaclav.obrovec", "test", "Václav", "Obrovec", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("65", "milan.plesinger", "test", "Milan", "Plešinger", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("66", "helena.plesingerova", "test", "Helena", "Plešingerová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("67", "irena.plesingerova", "test", "Irena", "Plešingerová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("68", "iva.posekana", "test", "Iva", "Posekaná", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("69", "radek.posekany", "test", "Radek", "Posekaný", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("70", "alois.prochazka", "test", "Alois", "Procházka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("71", "pavel.prochazka", "test", "Pavel", "Procházka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("72", "alena.prochazkova", "test", "Alena", "Procházková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("73", "alena.prochazkova.ml", "test", "Alena", "Procházková", "ml.", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("74", "pavla.prochazkova", "test", "Pavla", "Procházková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("75", "stanislav.rataj", "test", "Stanislav", "Rataj", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("76", "vaclav.rataj", "test", "Václav", "Rataj", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("77", "eva.ratajova", "test", "Eva", "Ratajová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("78", "petra.ratajova", "test", "Petra", "Ratajová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("79", "zdenka.rezacova", "test", "Zdeòka", "Øezáèová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("80", "petr.sklenar", "test", "Petr", "Sklenáø", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("81", "zdenek.sklenar", "test", "Zdenìk", "Sklenáø", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("82", "jana.sklenarova", "test", "Jana", "Sklenáøová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("83", "stanislav.skolil", "test", "Stanislav", "Skolil", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("84", "jindrich.sojka", "test", "Jindøich", "Sojka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("85", "tomas.sojka", "test", "Tomáš", "Sojka", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("86", "radek.sefcik", "test", "Radek", "Šefèík", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("87", "romana.sefcikova", "test", "Romana", "Šefèíková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("88", "irena.sipova", "test", "Irena", "Šípová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("89", "petra.sipova", "test", "Petra", "Šípová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("90", "jiri.stepan", "test", "Jiøí", "Štìpán", "", "jiri.stepan@etnetera.cz", "602603906", "", "", "1", "0", "0", "-1");
INSERT INTO person VALUES("91", "tereza.stepanova", "test", "Tereza", "Štìpánová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("92", "martin.stepanek", "test", "Martin", "Štìpánek", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("93", "ludmila.stepankova", "test", "Ludmila", "Štìpánková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("94", "miroslav.svejda", "test", "Miroslav", "Švejda", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("95", "tereza.talackova", "test", "Tereza", "Talacková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("96", "martin.taticek", "test", "Martin", "Tatíèek", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("97", "miroslav.taticek", "test", "Miroslav", "Tatíèek", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("98", "jana.tatickova", "test", "Jana", "Tatíèková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("99", "martina.tatickova", "test", "Martina", "Tatíèková", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("100", "karel.telupil", "test", "Karel", "Tìlupil", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("101", "marie.telupilova", "test", "Marie", "Tìlupilová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("102", "roman.vacha", "test", "Roman", "Vácha", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("103", "jitka.vachova", "test", "Jitka", "Váchová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("104", "kristyna.telupilova", "test", "Kristýna", "Tìlupilová", "", NULL, NULL, NULL, NULL, "1", "0", "0", NULL);
INSERT INTO person VALUES("109", NULL, NULL, "jarek", "zeleny	", "", NULL, NULL, NULL, NULL, "0", "0", "0", "90");
INSERT INTO person VALUES("110", NULL, NULL, "kamil", "novak", "", NULL, NULL, NULL, NULL, "0", "0", "0", "90");
INSERT INTO person VALUES("111", NULL, NULL, "huhan", "modry	", "", NULL, NULL, NULL, NULL, "0", "0", "0", "90");
INSERT INTO person VALUES("151", NULL, NULL, "", "asdf", "", NULL, NULL, NULL, NULL, "0", "0", "0", "1");


#
# Table structure for table 'reservation'
#

DROP TABLE IF EXISTS reservation;
CREATE TABLE reservation (
  id int(11) NOT NULL auto_increment,
  date_from date NOT NULL default '0000-00-00',
  date_to date NOT NULL default '0000-00-00',
  date_reservation datetime NOT NULL default '0000-00-00 00:00:00',
  reserved_by int(11) default NULL,
  Note text,
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'reservation'
#

INSERT INTO reservation VALUES("23", "2004-06-18", "2004-06-20", "2004-05-19 20:52:10", "1", "");
INSERT INTO reservation VALUES("24", "0000-00-00", "0000-00-00", "2004-05-19 22:27:09", NULL, NULL);
INSERT INTO reservation VALUES("25", "0000-00-00", "0000-00-00", "2004-05-19 22:28:03", NULL, NULL);
INSERT INTO reservation VALUES("26", "2004-06-26", "2004-07-01", "2004-05-19 22:29:30", "1", "");
INSERT INTO reservation VALUES("27", "2004-06-25", "2004-07-05", "2004-05-19 22:30:13", "1", "test");
INSERT INTO reservation VALUES("28", "2004-06-25", "2004-06-30", "2004-05-19 22:50:37", "1", "");
INSERT INTO reservation VALUES("29", "0000-00-00", "0000-00-00", "2004-05-19 22:51:55", NULL, NULL);
INSERT INTO reservation VALUES("30", "0000-00-00", "0000-00-00", "2004-05-19 22:57:51", NULL, NULL);
INSERT INTO reservation VALUES("31", "2004-06-24", "2004-06-27", "2004-05-19 22:58:17", "1", "");
INSERT INTO reservation VALUES("32", "2004-05-04", "2004-05-05", "2004-05-20 21:08:29", "90", "");
INSERT INTO reservation VALUES("33", "2004-05-04", "2004-05-08", "2004-05-20 21:08:39", "90", "");
INSERT INTO reservation VALUES("34", "0000-00-00", "0000-00-00", "2004-05-20 21:31:08", NULL, NULL);
INSERT INTO reservation VALUES("35", "0000-00-00", "0000-00-00", "2004-05-20 21:34:23", NULL, NULL);
INSERT INTO reservation VALUES("36", "0000-00-00", "0000-00-00", "2004-05-20 21:34:52", NULL, NULL);
INSERT INTO reservation VALUES("37", "0000-00-00", "0000-00-00", "2004-05-20 21:35:47", NULL, NULL);
INSERT INTO reservation VALUES("38", "2004-05-04", "2004-05-09", "2004-05-20 21:37:46", "90", "");
INSERT INTO reservation VALUES("39", "2004-05-11", "2004-05-18", "2004-05-20 21:39:53", "90", "");
INSERT INTO reservation VALUES("40", "0000-00-00", "0000-00-00", "2004-05-20 21:41:26", NULL, NULL);
INSERT INTO reservation VALUES("41", "2004-05-18", "2004-05-24", "2004-05-20 21:46:23", "90", "");
INSERT INTO reservation VALUES("42", "2004-06-02", "2004-06-03", "2004-05-20 21:48:23", "90", "huh");
INSERT INTO reservation VALUES("43", "2004-05-31", "2004-06-05", "2004-05-20 21:58:23", "90", "");
INSERT INTO reservation VALUES("44", "2004-05-12", "2004-05-16", "2004-05-20 22:36:38", "1", "");
INSERT INTO reservation VALUES("45", "2004-05-13", "2004-05-18", "2004-05-20 22:37:28", "1", "");
