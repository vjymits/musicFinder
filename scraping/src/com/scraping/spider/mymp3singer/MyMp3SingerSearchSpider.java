package com.scraping.spider.mymp3singer;

import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.crawler.GenericCrawler;
import com.scraping.db.ConfigUtil;
import com.scraping.spider.SearchSpider;

public class MyMp3SingerSearchSpider implements SearchSpider{
	
	private String query;
	
	private String url;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("myMap3SingerConfigFile"));
	
	
	public MyMp3SingerSearchSpider(String query){
		setQuery(query);
	}
	

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String createUrl() {
		String q  = this.query;
		String url;
		q= query.replace(" ", "%20");
		url = props.get(("searchurl"));
		url = url.replace("w1%20w2%20w3", q);
		this.setUrl(url);
		return url;		
		
	}

	@Override
	public void run() {
		createUrl();
		System.out.println("Request to Url: "+getUrl());
		Crawler crwl= new MyMp3SingerCrawler(getUrl(), 0, "myMap3SingerConfigFile");
		crwl.start();
		
	}
	
}
