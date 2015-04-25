package com.scraping.spider.koolwap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class KoolwapCrawler extends Crawler{
	
	
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("KoolwapConfigFile"));
	
	
	public KoolwapCrawler(String url, int level){
		super(url,level);
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
		
		
	}

	@Override
	public boolean isCrawlable() {
		String url = getUrl();
		boolean flag = true;
		List<String> allowedUrlList = Arrays.asList(props.get("allowedurls").split(","));
		for(String allowedUrl : allowedUrlList){
			//System.out.println("aloowedurl: "+allowedUrl);
			if(url.indexOf(allowedUrl)!=-1)
				flag = true;
			else{
				flag = false;
				break;
			}				
		}		
		//System.out.println("URL: "+url+"\nflag: "+flag);
		return flag;
	}

	@Override
	public void crawl() {
		for(String link : LinkUtil.getAllLinks(getUrl())){
			int level = getLevel();
			KoolwapCrawler crw = new KoolwapCrawler(link,++level);
			crw.setName("Koolwap_Thread_level_"+level);
			crw.start();
			
		}
			
	}
	
	

}
