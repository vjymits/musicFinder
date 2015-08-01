package com.scraping.link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scraping.db.ConfigUtil;

public class LinkUtil {


	public static boolean isMp3(Link link){
		
		return isMp3(link.getTarget());
	}
	
	public static boolean isMp3(String link){
		boolean f = false;
		link = link.trim();
		if(link.lastIndexOf('.') == -1)
			f=false;	
		else if(link.indexOf('?') != -1 || link.indexOf("&") != -1)
			f=false;
		else if (link.substring(link.lastIndexOf('.')).equalsIgnoreCase(".mp3")){
			f=true;
		}
		return f;
	}
	
	public static String getExt(String url){
		String domain = getDomainName(url);
		String temp = url.replaceAll(domain, "");
		temp = temp.trim();
		if(temp.lastIndexOf('.') == -1)
			return "";				
		String ext = temp.substring(temp.lastIndexOf('.'));
		
		return ext;
	}
	
	public static boolean isUri(Link link){
		return false;
	}
	
	public static boolean isUrl(Link link){
		
		return false;
	}
	
	public static String getDomainName(String url){
		Pattern patternDomainName;
		Matcher matcher;
		patternDomainName = Pattern.compile("([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}");
		String domainName = "";
		matcher = patternDomainName.matcher(url);
		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}
		return domainName;
	 
	  }
	
	public static boolean isURIEncoded(String uri){
		
		return (uri.indexOf('%')==-1)?false:true;
	}
	
	public static int responseCodeOfURI(String uri){
		HttpURLConnection connection = null;
		try{         
		    URL url = new URL(uri);        
		    connection = (HttpURLConnection) url.openConnection(); 
		     
		    connection.setRequestMethod("HEAD");         
		    int code = connection.getResponseCode();        
		    return code;
		} catch (Exception ex){
			return -1;		
		}
		
	}
	public static boolean isUriValid(String uri){
		uri = uri.trim();
		
		if(responseCodeOfURI(uri)==200)
			return true;
		return false;
	}
	
	public static String getFinalRedirectedUrl(String url) throws MalformedURLException, IOException
	  {
	      HttpURLConnection connection;
	      String finalUrl = url;
	      
	      do {
	              connection = (HttpURLConnection) new URL(finalUrl)
	              .openConnection();
	              connection.setInstanceFollowRedirects(false);
	              connection.setUseCaches(false);
	              connection.setRequestMethod("GET");
	              connection.usingProxy();
	              connection.connect();
	              int responseCode = connection.getResponseCode();
	              if (responseCode >=300 && responseCode <400)
	              {
	                  String redirectedUrl =    connection.getHeaderField("Location");
	                  if(null== redirectedUrl)
	                      break;
	                  finalUrl =redirectedUrl;
	                                      //System.out.println( "redirected url: " + finalUrl);
	              }
	              else
	                  break;
	          }while (connection.getResponseCode() != HttpURLConnection.HTTP_OK);
	          connection.disconnect();
	      
	      
	      return finalUrl;
	  }
	public static String URItoURL(String u){
		return null;
	}
	
    public static Set<String> getAllLinks(String htmlUrl){
		
		
		Set<String> result = new HashSet<String>();	
		
		
		String request = htmlUrl;
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
				    		
			}
		}
		catch(Exception ex){
				return null;
			}
		return result;
			
		}
    public static String removeSpaceFromUrl(String url){
		
			if(url.indexOf(' ')!=-1){
				url = url.replaceAll(" ", "%20");
				//System.out.println("URL Changed: "+url);
			}
					
		return url;
	}
     public static Set<String> getLinksOfExt(String url, String ext){
    	 Set<String> allLinks = getAllLinks(url);
    	 Set<String> extLinks = new HashSet<String>();
    	 for(String link: allLinks){
    		 if(getExt(link).equalsIgnoreCase(ext))
    			 extLinks.add(link);
    	 }
    	 return extLinks;
    	 
    	 
     }
     
     static public Document makeCall(String request){
    	 Document doc=null;
    	 try {
    		 		doc = Jsoup
					.connect(request)
					.userAgent(
					  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(5000).get();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
    	 return doc;
    	 
 	}
     
    static public boolean isChildUrl(String url){
    	return url.startsWith("/");
    }
    
    static public String attachParentUrl(String domain, String url){
    	return "https://"+domain+url;
    }
    
    public static boolean isUrlAllowed(String url, String domain)
    {
    	String prefix = "allowed-urls-";
    	String component = prefix+domain;
    	Map<String,String> allowedUrls = ConfigUtil.getConfigByComponent(component);
    	if (allowedUrls == null)
    		return false;
    	for(String key : allowedUrls.keySet()){
    		String val = allowedUrls.get(key);
    		if(url.contains(val))
    			return true;
    		
    	}
    	return false;
    }

	
	
	
}