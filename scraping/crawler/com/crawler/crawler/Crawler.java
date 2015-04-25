package com.crawler.crawler;

public interface Crawler extends Cloneable{
	
	public void setUrl(String url);
	public String getUrl();
	
	public void setDepthLevel(int level);
	public int getDepthLevel();
	
	public  boolean isCrawlable();
	
	
	public void setMyLevel(int level);
	public int getMyLevel();

}
