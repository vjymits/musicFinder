package com.scraping.spider.search.mp3pm;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.SearchSpider;
import com.scraping.spider.generic.GenericSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

//http://mp3pm.com/s/f/mujhe+pyar+hai/

public class Mp3pmSearchSpider implements SearchSpider{
	
	private String query;
	private Map<String,String> props;
	private Map<String,String> allowedUrls;
	private SongDao songDao;
	private String url;
	
	public Mp3pmSearchSpider(String q){
		this.query = q;
		this.props = ConfigUtil.getConfigByComponent("Mp3pmSearchSpider-config");
		this.allowedUrls = ConfigUtil.getConfigByComponent("Mp3pmSearchSpider-allowedurls");
		this.songDao = new SongDao(getTable());
	}

	@Override
	public void run() {
		String request =  createUrl();
		url=request;
		System.out.println("request to mp3pm : "+request);
		Set<String>	result = LinkUtil.getAllLinks(request);
		//System.out.println("alllinks: "+result);
		for (String url : result){
			if(!isUrlAllowed(url))
				continue;
			url = "http://www.mp3pm.com"+url;
			try{
				GenericSpider gs = new GenericSpider(url);
				gs.run();
				//System.out.println("Url: "+url+" getAll: "+gs.getLinks());
				for(String mp3 : gs.getAllMp3Links()){
					actOnMp3(mp3);
				}
			}
			catch(Exception ex){
			System.out.println("URL: "+url+" Skiping url ...");
		    }
		}
	}

	private void actOnMp3(String mp3) {
		System.out.println("Mp3: "+mp3);
		SongVO vo = new SongVO();
		vo.setSongUrl(mp3);
		vo.setSongUri(mp3);
		vo.setStatus(0);
		vo.setSearchQueries(this.query);
		vo.setTitle(mp3.substring(mp3.lastIndexOf('/')+1));	
		try {
			songDao.persist(vo);
		} catch (SQLException e) {
			System.out.println("Skiping to add in DB, mp3: "+mp3);
			e.printStackTrace();
		}
		
	}

	@Override
	public String getQuery() {
		return this.query;
	}

	@Override
	public void setQuery(String query) {
		this.query = query;
		
	}
	
	private boolean isUrlAllowed(String url){
		boolean f = false;
		for(String one : allowedUrls.values())
		{
			if(url.contains(one)){
				f= true;
				break;
			}
		}
		
		//System.out.println("url: "+url+" flag: "+f);
		return f;
	}	

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
		
	}

	@Override
	public String createUrl() {
		String searchUrl = props.get("mp3pm.config.searchurl");
		String q = this.query;
		q = q.replace(" ", "+");
		String htmlUrl = searchUrl.replace("w1+w2+w3", q);
		return htmlUrl;
	}

	@Override
	public String getTable() {
		return props.get("mp3pm.config.table");
	}

}
