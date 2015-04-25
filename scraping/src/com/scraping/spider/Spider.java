package com.scraping.spider;

import java.util.Set;

import com.scraping.link.Link;

public interface Spider extends Runnable {
	

	Set<String> getLinks();
	
	
	

}
