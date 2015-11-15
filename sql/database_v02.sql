# MySQL-Front Dump 2.5
#
# Host: localhost   Database: panenka
# --------------------------------------------------------
# Server version 4.0.20a-nt

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

INSERT INTO aux_reservation_person VALUES("1", "1");
INSERT INTO aux_reservation_person VALUES("2", "1");
INSERT INTO aux_reservation_person VALUES("3", "1");


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

INSERT INTO board VALUES("1", "2004-11-06 00:00:00", "pokusna hlaska", "Pokus", "1", "0", "1");
INSERT INTO board VALUES("2", "2004-11-05 00:00:00", "<ul>\r\n<li>akhfdj</li>\r\n</ul>", "pokus 2", "1", "0", "2");


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

INSERT INTO can_subscribe VALUES("1", "2");
INSERT INTO can_subscribe VALUES("2", "1");


#
# Table structure for table 'event_log'
#

DROP TABLE IF EXISTS event_log;
CREATE TABLE event_log (
  id int(11) NOT NULL auto_increment,
  event_time datetime NOT NULL default '0000-00-00 00:00:00',
  logged_user_id int(11) NOT NULL default '0',
  logged_user_name varchar(60) NOT NULL default '',
  event_type varchar(20) NOT NULL default '',
  event_params text NOT NULL,
  event_ip varchar(20) NOT NULL default '',
  event_remote_host varchar(255) default NULL,
  event_user_cookie varchar(50) default NULL,
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'event_log'
#



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

INSERT INTO person VALUES("1", "jiri.stepan", "test", "Jiri", "Stepan", "jiri.stepan@etnetera.cz", "602603906", NULL, "Fr. Kloze 1846, Kladno", "1", "1", "1", NULL);
INSERT INTO person VALUES("2", "tereza.stepanova", "test", "Tereza", "Stepan", "thanusova@nfoaisa.com", NULL, NULL, "Fr. Kloze 1846, Kladno", "1", "0", "0", NULL);
INSERT INTO person VALUES("3", NULL, NULL, "Stepana", "Malcova", NULL, NULL, NULL, NULL, "0", "0", "0", "1");


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
  PRIMARY KEY  (id)
) TYPE=MyISAM;



#
# Dumping data for table 'reservation'
#

INSERT INTO reservation VALUES("1", "2003-12-19", "2003-12-26", "2003-12-10 00:00:00", "1");
