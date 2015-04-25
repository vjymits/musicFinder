package com.crawler.crawler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scraping.crawler.GenericCrawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class GenericWebSiteCrawler extends Thread implements Crawler{
	
	//Mandatory params
	private String url;
	private int myLevel=0,MaxDepthLevel=0;
	
	//Optional params
	
	private Set<String> allowedUrls, notAllowedUrls,specialUrls;
	
	public GenericWebSiteCrawler(String url, int myLevel, int depthLevel){
		setUrl(url);
		setDepthLevel(depthLevel);
		setMyLevel(myLevel);
		
	}
	
	public GenericWebSiteCrawler(String url, int myLevel, int depth, String configName){
		this(url,myLevel,depth);
		Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get(configName));
		this.allowedUrls = getListByComma(props.get("allowedUrls"));
		this.notAllowedUrls= getListByComma(props.get("notAllowedUrls"));
		this.specialUrls=getListByComma(props.get("specialUrls"));
		
		
	}
	
	public GenericWebSiteCrawler(){}

	@Override
	public void run() {
		if(isCrawlable())
			crawl();
		
			
		
	}
	
    public void crawl() {
		
		for(String link : LinkUtil.getAllLinks(getUrl())){
			
			int level = getMyLevel();
			GenericWebSiteCrawler crw;
			try {
				crw = (GenericWebSiteCrawler)this.clone();
				crw.setUrl(link);
				crw.setMyLevel(++level);
				crw.setName("Generic_website_crawler_Thread_level_"+level);
				crw.start();
				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
	}

	@Override
	public void setUrl(String url) {
		this.url=url;
		
	}

	@Override
	public String getUrl() {

		return url;
	}

	@Override
	public void setDepthLevel(int level) {
		this.MaxDepthLevel=level;
	}

	@Override
	public int getDepthLevel() {
		return MaxDepthLevel;
	}

	@Override
	public boolean isCrawlable() {
		boolean flag=false;
		if(getDepthLevel()==0)
			return true;
		else if(getDepthLevel()<=getMyLevel())
			return false;
		if(allowedUrls==null || CrawlerUtil.isAnyElementContains(getUrl(), allowedUrls))
			flag=true;
		if(notAllowedUrls==null || !CrawlerUtil.isAnyElementContains(getUrl(), notAllowedUrls))
			flag=true;
		return flag;
	}

	@Override
	public void setMyLevel(int level) {
		this.myLevel=level;
		
	}

	@Override
	public int getMyLevel() {
		return myLevel;
	}
	
	private Set<String> getListByComma(String allowedUrls){
		
		if (allowedUrls==null || allowedUrls.equalsIgnoreCase("*"))
			return null;
		List<String> allowedUrlList=Arrays.asList(allowedUrls.split(","));
		return new HashSet<String>(allowedUrlList);
	}
	
	

}
