package com.scraping.spider.koolwap;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.vo.song.SongVO;

public class KoolwapCrawler extends Crawler{
	
	
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("KoolwapConfigFile"));
	private String tablename = props.get("tablename");
	
	public KoolwapCrawler(String url, int level){
		super(url,level);
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
				
	}
	
	public KoolwapCrawler(String url, int level, String tablename){
		super(url,level,tablename);
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
	}
		

	@Override
	public boolean isCrawlable() {
		String url = getUrl();
		boolean flag = true;
		List<String> allowedUrlList = Arrays.asList(props.get("allowedurls").split(","));
		for(String allowedUrl : allowedUrlList){
			//System.out.println("aloowedurl: "+allowedUrl);
			if(url.indexOf(allowedUrl)!=-1)
				flag = true;
			else{
				flag = false;
				break;
			}				
		}		
		//System.out.println("URL: "+url+"\nflag: "+flag);
		return flag;
	}

	@Override
	public void crawl() {
		for(String link : LinkUtil.getAllLinks(getUrl())){
			int level = getLevel();
			KoolwapCrawler crw = new KoolwapCrawler(link,++level,tablename);
			crw.setName("Koolwap_Thread_level_"+level);
			crw.start();
			
		}
			
	}
	
	@Override
	public void actOnMp3(){
		if(getUrl().contains("mirchistar.net"))
			return;
		System.out.println("Mp3: "+getUrl());
		
		SongVO vo = new SongVO();
		vo.setSongUrl(getUrl());
		vo.setSongUri(getUrl());
		vo.setStatus(0);
		try {
			dao.persist(vo);
		} catch (SQLException e) {
			System.out.println("Skiping to add in DB, mp3: "+getUrl());
			e.printStackTrace();
		}
	}
	
	
	

}
