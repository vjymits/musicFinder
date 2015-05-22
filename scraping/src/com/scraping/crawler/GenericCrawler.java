package com.scraping.crawler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class GenericCrawler extends Crawler implements Cloneable{
	
	
	private String configName;
	
	public GenericCrawler(String url, int level, String configName) {
		super(url, level);
		setConfigName(configName);
		props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get(configName));
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
	}
	
	public GenericCrawler(String url, int level, int maxLevel, String allowedUrls){
		super(url,level);
		this.setMaxLevel(maxLevel);
		
	}

	private Map<String,String> props;

	@Override
	public boolean isCrawlable() {
		//System.out.println("level: "+getLevel());
		if(getLevel() == 0)
			return true;
		String url = getUrl();
		boolean flag = false;
		List<String> allowedUrlList = getAllowedUrls();
		if (allowedUrlList == null)
			return true;
		for(String allowedUrl : allowedUrlList){
			if(url.contains(allowedUrl)){
				flag = true;
				break;
			}
			    
			else{
				flag = false;
			}				
		}		
		//System.out.println("Req: "+url+" Flag: "+flag);
		return flag;
	}

	@Override
	public void crawl() {
		
		for(String link : LinkUtil.getAllLinks(getUrl())){
			
			int level = getLevel();
			GenericCrawler crw;
			try {
				crw = (GenericCrawler)this.clone();
				crw.setUrl(link);
				crw.setLevel(++level);
				crw.setName("Generic_crawler_Thread_level_"+level);
				crw.start();
				
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	public List<String> getAllowedUrls(){
		String allowedUrls = props.get("allowedurls");
		if (allowedUrls==null || allowedUrls.equalsIgnoreCase("*"))
			return null;
		List<String> allowedUrlList=Arrays.asList(allowedUrls.split(","));
		return allowedUrlList;
	}
		

}
