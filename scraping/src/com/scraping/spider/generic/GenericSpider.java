package com.scraping.spider.generic;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;

public class GenericSpider implements Mp3Spider{
	
	private String htmlUrl;
	private Set<String> allLinks;
	private Set<String> allMp3Links;
	
	public GenericSpider(String htmlUrl){
		this.htmlUrl =htmlUrl;
		
	}

	@Override
	public void run() {
		allLinks = getAllLinks();
		
	}

	@Override
	public Set<String> getLinks() {
		return allLinks;
	}
	
	private Set<String> getAllLinks(){
		
		
		Set<String> result = new HashSet<String>();	
		allMp3Links = new HashSet<String>();	
		
		String request = this.htmlUrl;
		//System.out.println("URL: "+request);
			 
		try {
	 			
			Document doc = Jsoup
				.connect(request)
				.userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();
	 			
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 				String l = link.attr("href");
	 				result.add(l);
				    if (LinkUtil.isMp3(l)){
				    	this.allMp3Links.add(l);
				    }
		
			}
		}
		catch(IOException ex){
				
			}
		return result;
			
		}
	
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}
	
	
	

}
