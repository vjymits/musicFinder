package com.crawler.crawler;

public interface Crawler extends Cloneable, Runnable{
	
	public void setUrl(String url);
	public String getUrl();
	
	public void setDepthLevel(int level);
	public int getDepthLevel();
	
	public abstract boolean isCrawlable();
	
	public void setMyLevel(int level);
	public int getMyLevel();

}
