package com.scraping.crawler;

import java.sql.SQLException;

import com.scraping.link.LinkUtil;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;


public abstract class Crawler extends Thread{
	
	private String url;
	protected String query;
	
	protected SongDao dao;
	
	private int level = 0;
	private int maxLevel=1;
	
	
	protected Crawler( String url, int level){
		setUrl(url);
		setLevel(level);
		dao = new SongDao();
	}
	public Crawler(String url, int level, String tableName){
		this(url,level);
		dao = new SongDao(tableName);
	}
	
	 
	
	public void setUrl(String u){
		this.url = u;
	}
	
	public String getUrl(){
		return url;
	}
	
	public abstract boolean isCrawlable();
	
	public abstract void crawl();
	
	public void run(){
		
		if(LinkUtil.isMp3(url)){
			SongVO song = new SongVO();
			song.setSongUri(url);
			song.setSongUrl(url);
						
		    actOnMp3();
		}
		else if(level == 0){
			System.out.println("Level is 0 ");
			crawl();
		}
		   
		else if((maxLevel==0 || level < maxLevel) && isCrawlable()){
			
		    crawl();
		    //System.out.println("\n Thread: "+getName()+" level: "+getLevel());
		}
	}	

	public void actOnMp3() {
		System.out.println("Mp3: "+getUrl());
		SongVO vo = new SongVO();
		vo.setSongUrl(getUrl());
		vo.setStatus(0);
		vo.setSearchQueries(query);
		try {
			dao.persist(vo);
		} catch (SQLException e) {
			System.out.println("Skiping to add in DB, mp3: "+getUrl());
			e.printStackTrace();
		}
				
	}



	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}	

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	public String getQuery(){
		return query;
	}
	public void setQuery(String q){
		this.query = q;
	}

	

}
