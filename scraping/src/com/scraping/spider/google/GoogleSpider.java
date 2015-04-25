package com.scraping.spider.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.stylesheets.LinkStyle;

import com.scraping.link.Link;
import com.scraping.link.LinkUtil;
import com.scraping.link.LinkVO;
import com.scraping.spider.Spider;

public class GoogleSpider implements Spider{

	private String query;
	
	private Set<String> allLinks;
	
		
	public GoogleSpider(String q){
		query = replaceSpaceWithPlus(q);
		//System.out.println("quer:"+query);
	}
	
	private String replaceSpaceWithPlus(String q){
		
		char [] str = new char[q.length()];
		int i=0;
		for(char c : q.toCharArray() ){
			if(c==' ')
				c='+';
			str[i++]=c;
			
		}
		
		return new String(str);
	}
	
	@Override
	public Set<String> getLinks() {
		
		System.out.println("allLinks# "+allLinks.size());
		return allLinks;
	}
	
	public void run(){
		allLinks = getDataFromGoogle();
		//System.out.println("alllinks# "+allLinks.size());
		
	}
	
	private Set<String> getDataFromGoogle() {
		 
		Set<String> result = new HashSet<String>();	
		String request = "http://www.google.com/search?q="+query+"&num=40";
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
												
				if(l.startsWith("/url?q=")){
					result.add(findURIFromGoogleSearchResult(l));
					
	                
				}
	 
			}
	 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	  }
	
	private String findURIFromGoogleSearchResult(String uri){
		
		uri = uri.substring(7);
		try {
			int limit =1;
			while(LinkUtil.isURIEncoded(uri) && (limit<10)){
				uri = URLDecoder.decode(uri, "UTF-8");
				limit++;
				
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		if (LinkUtil.getDomainName(uri).equals("webcache.googleusercontent.com")){
			
			String tmp = uri.substring(uri.lastIndexOf("http://"));
			
			uri = tmp;
		}
		int index = uri.indexOf('&');
		if(index!=-1){
			uri = uri.substring(0, index);
		}
		
		if(uri.indexOf(".html")!=-1){
			int i = uri.indexOf(".html");
			uri = uri.substring(0, i+5);
			//System.out.println("HTML URI: "+uri);
			
		}
		
		else if(uri.indexOf(".htm")!=-1){
			int i = uri.indexOf(".htm");
			uri = uri.substring(0, i);
			
		}	
		//URLDecoder.decode(myUrl.toString(), "UTF-8");
		//System.out.println("tmp: "+uri);
		return uri;
		
	}

}
