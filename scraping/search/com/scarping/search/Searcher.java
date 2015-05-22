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
	
	String[] getResult();
	void setResult(String[] res);
	
	String toJSON();
	
	
	

}
