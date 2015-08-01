package com.scraping.spider.findmp3;

import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.generic.GenericSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class FindMp3Spider implements Mp3Spider {
	
	private String query;
	private String htmlUrl="";
	
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("FindMp3ConfigFile"));
	private String table = props.get("tablename");
	private SongDao dao = new SongDao(table);
	public FindMp3Spider(String query){
		this.query=query;
		
	}
	
	public String getTable(){
		return table;
	}

	@Override
	public Set<String> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		System.out.println("----------FindMp3Spider----------");
		createUrl();
		System.err.println("html url: "+htmlUrl);
		
		String request = htmlUrl;
		Set<String>	result = LinkUtil.getAllLinks(request);
		System.out.println("alllinks: "+result);
		for (String url : result){
			url = makeCrawlable(url);			
			if(url == null)
				continue;
			GenericSpider gs = new GenericSpider(url);
			gs.run();
			Set<String> mp3s = gs.getAllMp3Links();
			for(String mp3 : mp3s){
				try {
					System.out.println("found mp3: "+mp3);
					actOnMp3(mp3);
					continue;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
			
		}
		
	}

	@Override
	public Set<String> getAllMp3Links() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean isUrlAllowed(String url){
		boolean flag = false;
		String startsWith = props.get("startswith");
		if(url.startsWith(startsWith)){
			flag = true;
			
		}
		System.out.println("URL: "+url+" allowed: "+flag+" ");
		return flag;
	}
	
	private String makeCrawlable(String url){
		if(!isUrlAllowed(url))
			return null;
		String essentialUrl = props.get("essentialUrl");
		String url2 = essentialUrl+url;
		System.out.println("making crawlable url to run: "+url2);
		return url2;
	}
	
	private void actOnMp3(String mp3) throws SQLException{
		SongVO vo = new  SongVO();
		vo.setSearchQueries(query);
		vo.setSongUri(mp3);
		vo.setSongUrl(mp3);
		vo.setStatus(0);
		vo.setTitle(mp3.substring(mp3.lastIndexOf('/')+1));
		dao.persist(vo);
				
	}
	
	public String createUrl() {
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		String q=this.query.replace(" ", "-");
		this.htmlUrl=this.htmlUrl.replace("w1-w2-w3", q);
		return htmlUrl;
	}

	

}
