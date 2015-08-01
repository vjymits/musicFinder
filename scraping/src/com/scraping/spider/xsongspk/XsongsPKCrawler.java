package com.scraping.spider.xsongspk;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.scraping.crawler.Crawler;
import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class XsongsPKCrawler extends Crawler{
	
	private String query;
	private SongDao songDao;
	
	
	public XsongsPKCrawler(String url, int level, String q) {
		super(url, level);
		this.query = q;
		this.setMaxLevel(Integer.parseInt(props.get("maxlevel")));
		this.tableName = props.get("tablename");
		this.songDao = new SongDao(getTable());
	}
	
	

	private static Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("xsongsPKConfigFile"));
    private String tableName ;
    
    public String getTable(){
    	return tableName;
    }
    
    
    
	@Override
	public boolean isCrawlable() {
		String url = getUrl();
		boolean flag = false;
		List<String> allowedUrlList = Arrays.asList(props.get("allowedurls").split(","));
		for(String allowedUrl : allowedUrlList){
			if(url.contains(allowedUrl)){
				flag = true;
				break;
			}
			    
			else{
				flag = false;
			}				
		}		
		//System.out.println("Req: "+url+" Flag: "+flag);
		return flag;
	}

	@Override
	public void crawl() {
		System.out.println("-----------XsongsPKCrawler----------");		
		for(String link : LinkUtil.getAllLinks(getUrl())){
			
			int level = getLevel();
			XsongsPKCrawler crw = new XsongsPKCrawler(link,++level,this.query);
			//crw.setQuery(this.query);
			crw.setName("xsongspk_Thread_level_"+level);
			crw.start();
		
		}
	}
	
	@Override
	public void actOnMp3(){
		System.out.println("Mp3: "+getUrl());
		SongVO vo = new SongVO();
		vo.setSongUrl(getUrl());
		vo.setSongUri(getUrl());
		vo.setStatus(0);
		
		String name = getUrl();
		name = name.substring(0,name.lastIndexOf('/'));
		name = name.substring(name.lastIndexOf('/')+1);
		vo.setTitle(name);
		System.out.println("xsongspk : query: "+this.query);
		vo.setSearchQueries(this.query);
		
		try {
			songDao.persist(vo);
		} catch (SQLException e) {
			System.out.println("Skiping to add in DB, mp3: "+getUrl());
			e.printStackTrace();
		}
	}
	
	
}
