package com.scraping.spider.indiamp3;

import java.sql.Date;

public class IndiaMp3VO {
	
	private long id;
	
	private long songsId;
	
	private String songsUri;
	
	private String songsUrl;
	
	private int status;
	
	private Date timestamp;
	
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSongsId() {
		return songsId;
	}

	public void setSongsId(long songs_id) {
		this.songsId = songs_id;
	}

	public String getSongsUri() {
		return songsUri;
	}

	public void setSongsUri(String songs_uri) {
		this.songsUri = songs_uri;
	}

	public String getSongsUrl() {
		return songsUrl;
	}

	public void setSongsUrl(String songs_url) {
		this.songsUrl = songs_url;
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
