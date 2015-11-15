# MySQL-Front Dump 2.5
#
# Host: localhost   Database: panenka
# --------------------------------------------------------
# Server version 4.0.20a-nt-log

USE panenka;


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
