package com.scraping.search.searchquery;

import java.sql.Timestamp;

public class AsyncSearchVO {

	long id, timeinMS;
	String uuid,query, resultUri, searchType,tables;
	Timestamp timestamp;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getTimeinMS() {
		return timeinMS;
	}
	public void setTimeinMS(long timeinMS) {
		this.timeinMS = timeinMS;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getResultUri() {
		return resultUri;
	}
	public void setResultUri(String resultUri) {
		this.resultUri = resultUri;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getTables() {
		return tables;
	}
	public void setTables(String tables) {
		this.tables = tables;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	

}
