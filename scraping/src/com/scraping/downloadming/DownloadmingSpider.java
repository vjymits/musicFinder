package com.scraping.downloadming;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;

public class DownloadmingSpider implements Mp3Spider{
	
	char ch ;
	String cnfFile = "downloadming_nu_ConfigFile";
	Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get(cnfFile));
	
	
	public DownloadmingSpider(char ch){
	
		setCh(ch);
	}
		
	public char getCh() {
		return ch;
	}



	public void setCh(char ch) {
		this.ch = ch;
	}



	public Map<String, String> getProps() {
		return props;
	}



	public void setProps(Map<String, String> props) {
		this.props = props;
	}



	/*
	 * Methode works only when azindex <div> is there in body.
	 * 
	 */
	public static Set<String> parseA2ZIndex(String url){
		Set<String> result = new HashSet<String>();
		Document doc = LinkUtil.makeCall(url);
		Element elmnt = doc.getElementById("content");
		Elements links = elmnt.select("a[href]");
		for (Element link : links) {
				String l = link.attr("href");
				result.add(l);
		}
		
		return result;
	}
	
	public Set<String> getAllA2ZUrls(){
		Set<String> set = new HashSet<String>();
		for(char ch = 'a'; ch<='z'; ch++){
			set.add(getA2ZUrl(ch));
		}
		return set;
	}
	public String getA2ZUrl(char ch){
		String url;
		String a2zUrl = props.get("a2zurl");
		url = a2zUrl.replace("$ID", ""+ch);
		return url;
	}

	@Override
	public Set<String> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		String url = getA2ZUrl(ch);
		Crawler crw = new DownloadmingCrawlerA2Z(url, 0,cnfFile);
		crw.start();
		
	}

	@Override
	public Set<String> getAllMp3Links() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
