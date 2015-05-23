package com.scarping.search;

import java.sql.Timestamp;
import java.util.Set;


public interface Searcher {
	
	long getId();
	void setId(long id);
	
	String getQuery();
	void setQuery(String q);
	
	Timestamp getTimestamp();
	void setTimestamp(Timestamp time);
	
	Set<String> getResult();
	void setResult(Set<String> res);
	
	String toJSON();
	
	
	

}
