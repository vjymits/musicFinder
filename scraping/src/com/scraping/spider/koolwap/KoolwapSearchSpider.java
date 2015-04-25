package com.scraping.spider.koolwap;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;

public class KoolwapSearchSpider implements Mp3Spider{

	private String htmlUrl="";
	private String query;
	private Set<String> allLinks;
	private Set<String> allMp3Links;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("KoolwapConfigFile"));
	
	public KoolwapSearchSpider(String query){
		
		this.query=query;
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		this.query=this.query.replace(" ", "-");
		this.htmlUrl=this.htmlUrl.replace("w1-w2-w3", this.query);
				
	}
	
	@Override
	public void run() {
		
		    System.out.println("htmlUrl: "+this.htmlUrl);
			Crawler crw = new KoolwapCrawler(this.htmlUrl, 0);	
			crw.start();
						
	}
	
	private boolean isUrlAllowed(String url){
		boolean flag = true;
		List<String> allowedUrlList = Arrays.asList(props.get("allowedurls").split(","));
		for(String allowedUrl : allowedUrlList){
			if(url.indexOf(allowedUrl)!=-1 && LinkUtil.getExt(url).equalsIgnoreCase(".html"))
				flag = true;
			else{
				flag = false;
				break;
			}				
		}		
		System.out.println("URL: "+url+"\nflag: "+flag);
		return flag;
		
		
	}
	
	private Set<String> getAllLinks(){
		
		Set<String> result ;	
		allMp3Links = new HashSet<String>();	
		String request = this.htmlUrl;
		System.out.println("Request To mp3Mad.com: "+request);
		result = LinkUtil.getAllLinks(request);
		return result;
			
	}

	@Override
	public Set<String> getLinks() {
		return allLinks;
	}
	
		
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}

}
