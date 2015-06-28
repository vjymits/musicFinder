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
    fulltext search_idx(`song_url`,`artists`,`album`,`Searchq`)

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
	`uuid` varchar(40)
	`query` varchar(256),
	`result_uri` varchar(256) UNIQUE,
	`search_type` varchar(256),
	`tables` varchar(256),
	`timeinMS` bigint,
	`timestamp` datetime
	
)engine=myisam;