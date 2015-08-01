package com.scraping.spider.koolwap;

import java.sql.SQLException;

import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.generic.GenericSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class KoolwapSearchSpider implements Mp3Spider{

	private String htmlUrl="";
	private String query;
	private Set<String> allMp3Links;
	private Map<String,String> props = ConfigUtil.getConfigByComponent("KoolwapSearchSpider-config");
	private Map<String,String> allowedUrls = ConfigUtil.getConfigByComponent("KoolwapSearchSpider-allowedurls");
	private SongDao dao = new SongDao(props.get("koolwap.config.table"));
	public KoolwapSearchSpider(String query){
		
		this.query=query;
		this.htmlUrl=this.htmlUrl+props.get("koolwap.config.searchurl");
		String q=this.query.replace(" ", "-");
		this.htmlUrl=this.htmlUrl.replace("w1-w2-w3", q);
				
	}
	
	@Override
	public void run() {
		
		    System.out.println("htmlUrl: "+this.htmlUrl);
		    //System.out.println("request to koolwap : "+htmlUrl);
			Set<String>	result = LinkUtil.getAllLinks(htmlUrl);
			//System.out.println("alllinks: "+result);
			for (String url : result){
				if(!isUrlAllowed(url))
					continue;
				
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
			dao.persist(vo);
		} catch (SQLException e) {
			System.out.println("Skiping to add in DB, mp3: "+mp3);
			e.printStackTrace();
		}
		
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
		
		System.out.println("url: "+url+" flag: "+f);
		return f;
		
	}
	

	@Override
	public Set<String> getLinks() {
		return null;
	}
	
		
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}
	public String getTable(){
		return props.get("koolwap.config.table");
	}

}
