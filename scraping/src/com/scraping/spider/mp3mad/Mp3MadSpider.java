package com.scraping.spider.mp3mad;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;

public class Mp3MadSpider implements Mp3Spider{
	
	private String htmlUrl="";
	private String query;
	private Set<String> allLinks;
	private Set<String> allMp3Links;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("MP3MadConfigFile"));
	
	public Mp3MadSpider(String query){
		
		this.query=query.trim();
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		this.query=this.query.replaceAll(" ", "+");
		this.htmlUrl=this.htmlUrl.replaceAll("qqqq", this.query);
								
	}
	
	@Override
	public void run() {
		allLinks = getAllLinks();
		//System.out.println(allLinks);
		allMp3Links = new HashSet<String>();
		for(String link: allLinks){
			if(!isUrlAllowed(link))
				continue;
			allMp3Links.addAll(LinkUtil.getLinksOfExt(link, ".mp3"));
						
		}
		
	}

	@Override
	public Set<String> getLinks() {
		return allLinks;
	}
	
	private Set<String> getAllLinks(){
			
		Set<String> result ;	
		allMp3Links = new HashSet<String>();	
		String request = this.htmlUrl;
		System.out.println("Request To mp3Mad.com: "+request);
		result = LinkUtil.getAllLinks(request);
		return result;
			
		}
	private boolean isUrlAllowed(String url){
		boolean flag = false;
		List<String> allowedDomainList = Arrays.asList(props.get("allwedDomains").split(","));
		String domain = LinkUtil.getDomainName(url);
		if(allowedDomainList.contains(domain) && (LinkUtil.getExt(url).equalsIgnoreCase(".html")))
			flag = true;
		System.out.println("URL: "+url+"\ndomain: "+domain+"\nflag: "+flag);
		return flag;
		
		
	}
	
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}

}
