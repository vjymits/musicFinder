package com.scraping.vo.song;

import java.sql.Date;

public class SongVO {
	
	long id;
	
	long songId= 0;
	
	String songUrl,songUri,artist,album,searchQueries;
	
	int status=-1;
	
	Date timestamp = new Date(System.currentTimeMillis());

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSongId() {
		return songId;
	}

	public void setSongId(long songId) {
		this.songId = songId;
	}

	public String getSongUrl() {
		return songUrl;
	}

	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	public String getSongUri() {
		return songUri;
	}

	public void setSongUri(String songUri) {
		this.songUri = songUri;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getSearchQueries() {
		return searchQueries;
	}

	public void setSearchQueries(String searchQueries) {
		this.searchQueries = searchQueries;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}		
	

}
