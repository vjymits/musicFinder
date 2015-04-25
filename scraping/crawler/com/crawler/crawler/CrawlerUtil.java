package com.crawler.crawler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrawlerUtil {
	
    public static  Set<String> getListByComma(String allowedUrls){
		
		if (allowedUrls==null || allowedUrls.equalsIgnoreCase("*"))
			return null;
		List<String> allowedUrlList=Arrays.asList(allowedUrls.split(","));
		return new HashSet<String>(allowedUrlList);
		}

	

    public static boolean isAnyElementContains(String str, Collection<String> col){
    	
    	for(String url : col){
			if(str.contains(url)){
				return true;
			}
    	}
    	return false;
    }
    
    
    
    
    
    
}    
    
