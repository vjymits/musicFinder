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