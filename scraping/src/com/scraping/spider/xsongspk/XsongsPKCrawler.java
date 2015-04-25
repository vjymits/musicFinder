package com.scraping.spider.xsongspk;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class XsongsPKCrawler extends Crawler{
	
	public XsongsPKCrawler(String url, int level) {
		super(url, level);
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
		
	}

	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("xsongsPKConfigFile"));

	@Override
	public boolean isCrawlable() {
		String url = getUrl();
		boolean flag = false;
		List<String> allowedUrlList = Arrays.asList(props.get("allowedurls").split(","));
		for(String allowedUrl : allowedUrlList){
			if(url.contains(allowedUrl)){
				flag = true;
				break;
			}
			    
			else{
				flag = false;
			}				
		}		
		System.out.println("Req: "+url+" Flag: "+flag);
		return flag;
	}

	@Override
	public void crawl() {
		
		for(String link : LinkUtil.getAllLinks(getUrl())){
			
			int level = getLevel();
			XsongsPKCrawler crw = new XsongsPKCrawler(link,++level);
			crw.setName("xsongspk_Thread_level_"+level);
			crw.start();
		
		}
	}
	
	
}