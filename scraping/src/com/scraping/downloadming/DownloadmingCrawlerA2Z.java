package com.scraping.downloadming;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.crawler.GenericCrawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class DownloadmingCrawlerA2Z extends GenericCrawler{
	
	private Map<String,String> props;
	String cnfName = "";
	public DownloadmingCrawlerA2Z(String url, int level, String cnfName) {
		super(url, level, cnfName);		
		props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get(cnfName));
		this.cnfName = cnfName;
	}
	
	@Override
	public void crawl(){
		if(getLevel()==0){
		System.out.println("Level = 0");	
		for(String link: DownloadmingSpider.parseA2ZIndex(getUrl())){
		  	int l = getLevel();
		    Crawler crw = new DownloadmingCrawlerA2Z(link, ++l, this.cnfName);
		    crw.start();
					
		}
		}
		else
			for(String link : LinkUtil.getAllLinks(getUrl())){
				int l = getLevel();
			    Crawler crw = new DownloadmingCrawlerA2Z(link, ++l, this.cnfName);
			    crw.start();
			}
		
	}
	
	
	public List<String> getAllowedUrls(){
		String allowedUrls = props.get("allowedurls");
		List<String> allowedUrlList=Arrays.asList(allowedUrls.split(","));
		return allowedUrlList;
	}
	
	private boolean isPrimaryA2ZUrl(){
		String url = getUrl();
		String allowed = props.get("allowedUrls4A2Z");
		if(url.contains(allowed))
			return true;
		return false;
	}
	 
	/*private String actOnMp3Path(){
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
	*/

}
