DROP database IF exists music;
CREATE database music;

DROP table if exists `music`.`indiamp3_com_db`;
create table `music`.`indiamp3_com_db`(
    `id` bigint unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `song_id` bigint UNIQUE,
    `song_uri` varchar(256),
    `song_url` varchar(256),
    `status` smallint,
    `timestamp` datetime

);

DROP table if exists `music`.`songs`;
create table if not exists `music`.`songs`(
    `id` bigint unsigned NOT NULL UNIQUE AUTO_INCREMENT,
    `song_id` bigint,
    `song_uri` varchar(256),
    `song_url` varchar(256),
    `status` smallint,
    `artists` varchar(500),
    `album` varchar(256),
    `Searchq` varchar(1000),
    `timestamp` datetime,
    fulltext search_idx(`song_url`,`title`,`Searchq`)

)engine=myisam;

DROP table if exists `music`.`searchquery`;
create table if not exists `music`.`searchquery`(
	`id` bigint unsigned primary key AUTO_INCREMENT,
	`query` varchar(256) UNIQUE,
	`jsonpath` varchar(256) UNIQUE,
	`result` varchar(256),
	`timestamp` datetime,
	fulltext search_query_idx(`query`)
)engine=myisam;

DROP table if exists `music`.`async_search`;
create table if not exists `music`.`async_search`(
	`id` bigint unsigned primary key AUTO_INCREMENT,
	`uuid` varchar(40) UNIQUE,
	`query` varchar(256),
	`result_uri` varchar(256) UNIQUE,
	`search_type` varchar(256),
	`tables` varchar(256),
	`timeinMS` bigint,
	`timestamp` datetime
	
)engine=myisam;

DROP table if exists `music`.`config`;
CREATE TABLE  `music`.`config` (
  `category` varchar(255) NOT NULL DEFAULT 'Advanced',
  `instance` varchar(255),
  `component` varchar(255) NOT NULL DEFAULT 'mp3',
  `name` varchar(255) NOT NULL,
  `value` varchar(4095),
  `description` varchar(1024),
  PRIMARY KEY (`name`),
  INDEX `i_configuration__name`(`name`)
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
 
#mp3pm config
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','Mp3pmSearchSpider-config','mp3pm.config.searchurl','http://mp3pm.com/s/f/w1+w2+w3/','w1 w2 w3 to be add by +');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','Mp3pmSearchSpider-config','mp3pm.config.table','mp3pm','DB table name for this crawler');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','Mp3pmSearchSpider-allowedurls','mp3pm.allowedurls-1','mp3pm.com/s/f','This is allowed url');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','Mp3pmSearchSpider-allowedurls','mp3pm.allowedurls-2','/song','This is allowed url');

INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','KoolwapSearchSpider-config','koolwap.config.searchurl','http://koolwap.in/mp3_songs_w1-w2-w3.html','w1 w2 w3 to be add by -');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','Integer','KoolwapSearchSpider-config','koolwap.config.maxlevel','3','max level of crawler');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','KoolwapSearchSpider-config','koolwap.config.table','koolwap','DB table name for this crawler');
INSERT INTO `music`.`config` (`category`,`instance`,`component`,`name`,`value`,`description`) values('searchSpider','String','KoolwapSearchSpider-allowedurls','koolwap.allowedurls-1','koolwap.in/mp3','This is allowed url');

