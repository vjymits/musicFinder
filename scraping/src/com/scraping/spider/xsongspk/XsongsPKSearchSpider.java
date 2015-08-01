package com.scraping.spider.xsongspk;

import java.util.Map;
import java.util.Set;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.SearchSpider;


public class XsongsPKSearchSpider implements Mp3Spider,SearchSpider{

	private String htmlUrl="";
	private String query;
	
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("xsongsPKConfigFile"));
	
	public XsongsPKSearchSpider(String query){
		
		this.query=query;
		createUrl();
				
	}
	
	@Override
	public void run() {
		System.out.println("sending query as : "+this.query);
		Crawler crw = new XsongsPKCrawler(this.htmlUrl, 0, this.query);	
		crw.start();
						
	}
	public String getTable(){
		return props.get("tablename");
	}

	@Override
	public Set<String> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> getAllMp3Links() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
		
	}

	@Override
	public String getUrl() {
		
		return htmlUrl;
	}

	@Override
	public void setUrl(String url) {
		this.htmlUrl=url;
		
	}

	@Override
	public String createUrl() {
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		String query=this.query.replace(" ", "-");
		this.htmlUrl=this.htmlUrl.replace("w1-w2-w3", query);
		return htmlUrl;
	}
}