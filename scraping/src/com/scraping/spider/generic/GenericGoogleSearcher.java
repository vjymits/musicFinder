package com.scraping.spider.generic;

import java.util.HashSet;
import java.util.Set;

import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.google.GoogleSpider;

public class GenericGoogleSearcher implements Mp3Spider{
	
	private String query ;
	private GoogleSpider gs;
	private Set<String> mp3Links;
	private Set<String> GLinks;
	public GenericGoogleSearcher(String query){
		this.query = query;
	}

	@Override
	public Set<String> getLinks() {
		return this.GLinks;
	}

	@Override
	public void run() {
		gs = new GoogleSpider(query);
		gs.run();
		Set<String> lnks = gs.getLinks();
		this.GLinks = lnks;
		Set<String> mp3Links = new HashSet<String>();
		lnks = filterValidUrl(lnks);
		for(String lnk: lnks){
			Mp3Spider sp = new GenericSpider(lnk);
			sp.run();
			Set<String> mp3Lnks = sp.getAllMp3Links();
			mp3Lnks = filterValidUrl(mp3Lnks);
			mp3Links.addAll(mp3Lnks);
		}
		this.mp3Links = mp3Links;
				
	}

	@Override
	public Set<String> getAllMp3Links() {
		return this.mp3Links;
	}
	
	private Set<String> filterValidUrl(Set<String> urls){
		Set<String> validUrls = new HashSet<String>();
		for(String url: urls){
			if(url.indexOf(' ')!=-1){
				url = url.replaceAll(" ", "%20");
				//System.out.println("URL Changed: "+url);
			}
			
			if(LinkUtil.isUriValid(url))
				validUrls.add(url);
		}
		return validUrls;
	}

}
