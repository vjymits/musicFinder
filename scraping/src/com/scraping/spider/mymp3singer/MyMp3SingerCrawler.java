package com.scraping.spider.mymp3singer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.crawler.GenericCrawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class MyMp3SingerCrawler extends GenericCrawler{

	private Map<String,String> props;
	String cnfName = "";
	public MyMp3SingerCrawler(String url, int level, String cnfName) {
		super(url, level, cnfName);		
		props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get(cnfName));
		this.cnfName = cnfName;
	}
	
	@Override
	public void crawl(){
		System.out.println("Url decided to crawl: "+getUrl());
		
		if(isPathToMp3()){
			System.out.println("Yes above url is path to mp3");
			actOnMp3Path();
		}
		else{
			    for(String link: LinkUtil.getAllLinks(getUrl())){
			    	//System.out.println("link : "+link);
			    	    int l = getLevel();
			    	    link = "http://mymp3singer.com/"+link;
			    	    Crawler crw = new MyMp3SingerCrawler(link, ++l, this.cnfName);
			    	    //System.out.println("Going to crawl: "+link);
			    	    crw.start();
			    }
			
		}
				
		
	}
	
	private boolean isPathToMp3(){
		
		if(getUrl().contains(props.get("pathtomp3"))){
			return true;
		}
		return false;
	}
	 
	private String actOnMp3Path(){
		System.out.println("Path: "+getUrl());
		String redirectedUrl=""; 
		try {
			redirectedUrl =  LinkUtil.getFinalRedirectedUrl(getUrl());
			System.out.println("Mp3: "+redirectedUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return redirectedUrl;
		
	}
	
	
	

}
