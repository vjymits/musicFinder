package com.vjy.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scarping.search.DBSearch;
import com.scraping.link.Link;
import com.scraping.link.LinkUtil;
import com.scraping.link.LinkVO;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.generic.GenericGoogleSearcher;
import com.scraping.spider.google.GoogleSpider;
 
public class TestGoogle {
	public static void main(String[] args) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException, SQLException{
		
		DBSearch s = new DBSearch("jiye to jiye");
		s.search();
		for(String one : s.getResult()){
			System.out.println(one);
		}
		
		/*Mp3Spider spd = new GenericGoogleSearcher("didi tera devar");
		  spd.run();
		  for(String url: spd.getAllMp3Links())
			  System.out.println("mp3: "+url);
		
		/*GoogleSpider gs = new GoogleSpider("kuch door hamare saath chalo mp3");
	gs.run();
	Set<String> links = gs.getLinks();
	if (links == null){
		System.out.println("No links");
	}
	List<String> listOfLink = new ArrayList<String>(links);
	Collections.sort(listOfLink);
	//links = new HashSet<Link>(lisOfLink);
	
	//Set<String> setOfLinks = new HashSet<String>();
	for(String lnk : listOfLink){
		System.out.println(lnk);
		System.out.println(LinkUtil.responseCodeOfURI(lnk));
		//System.out.println(URLDecoder.decode(myUrl.toString(), "UTF-8"));
		/*String link = lnk.getTarget();
		setOfLinks.add(link);*/
		
		//URL myUrl = new URL(lnk.getTarget());
		
		//System.out.println(URLDecoder.decode(myUrl.toString(), "UTF-8"));
        /*System.out.println("Host: "+myUrl.getHost());
        System.out.println("Port: "+myUrl.getPort());
        System.out.println("Athority of the URL: "+myUrl.getAuthority());
        System.out.println("Query: "+myUrl.getQuery());*/
        
	}
//	System.out.println("#"+setOfLinks.size());
	
}
  /*private static Pattern patternDomainName;
  private Matcher matcher;
  private static final String DOMAIN_NAME_PATTERN 
	= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
  static {
	patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
  }
 
  public static void main(String[] args) {
 
	TestGoogle obj = new TestGoogle();
	Set<Link> result = obj.getDataFromGoogle("crawl+google+search+in+java");
	for(Link temp : result){
		System.out.println("Link: "+temp.getTarget());
		System.out.println("Text: "+temp.getTarget());
	}
	System.out.println(result.size());
  }
 
  public String getDomainName(String url){
 
	String domainName = "";
	matcher = patternDomainName.matcher(url);
	if (matcher.find()) {
		domainName = matcher.group(0).toLowerCase().trim();
	}
	return domainName;
 
  }
 
  private Set<Link> getDataFromGoogle(String query) {
 
	Set<Link> result = new HashSet<Link>();	
	String request = "https://www.google.com/search?q=scrap+google+search+with+Jsoup&num=20";
	System.out.println("Sending request..." + request);
 
	try {
 
		// need http protocol, set this as a Google bot agent :)
		Document doc = Jsoup
			.connect(request)
			.userAgent(
			  "Mozilla/5.0")
			.timeout(5000).get();
 
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
 
			String l = link.attr("href");
			String t = link.text();
			
			
			if(l.startsWith("/url?q=")){
                                //use regex to get domain name
				result.add(new LinkVO(l,t));
				System.out.println(getDomainName(l));
			}
 
		}
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	return result;
  }
 */

