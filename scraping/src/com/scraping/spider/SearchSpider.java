package com.scraping.spider;

public interface SearchSpider extends Runnable{
	
	String getQuery();
	void setQuery(String query);
	
	String getUrl();
	void setUrl(String url);
	
	String createUrl();
	

}
