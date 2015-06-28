package com.scraping.spider.mp3skull;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.search.SearchUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class Mp3SkullSpider implements Mp3Spider{
	
	private String htmlUrl="";
	private String query;
	private Set<String> allLinks;
	private Set<String> allMp3Links;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("Mp3SkullConfigFile"));
	
	public Mp3SkullSpider(String query){
		
		this.query=query;
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		this.query=this.query.replaceAll(" ", "+");
		this.htmlUrl+=this.query+"&"+props.get("session_param")+"="+props.get("session_id");
				
	}
	
	@Override
	public void run() {
		allLinks = getAllLinks();
		addAllMp3InDB();
	}
	
	public int addAllMp3InDB() {
		String tableName = props.get("tablename");
		SongDao dao = new SongDao(tableName);
		Set<SongVO> set = SearchUtil.getSearchResultSet(query);
		int c = 0;
		for (String mp3 : allMp3Links){
			SongVO vo = new SongVO();
			vo.setSongUrl(mp3);
			vo.setSongUri(mp3);
			System.out.println("mp3: "+mp3);
			vo.setSearchQueries(query);
			vo.setStatus(0);
			set.add(vo);
			try {
				dao.persist(vo);
				c++;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return c;
	}

	@Override
	public Set<String> getLinks() {
		return allLinks;
	}
	
	private Set<String> getAllLinks(){
		
		
		Set<String> result = new HashSet<String>();	
		allMp3Links = new HashSet<String>();	
		
		String request = this.htmlUrl;
		
		System.out.println("Req: "+request);
			 
		try {
	 			
			Document doc = Jsoup
				.connect(request)
				.userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				.timeout(5000).get();
	 			
			Elements links = doc.select("a[href]");
			for (Element link : links) {
	 				String l = link.attr("href");
	 				l=LinkUtil.removeSpaceFromUrl(l);
	 				result.add(l);
				    if (LinkUtil.isMp3(l)){
				    	   this.allMp3Links.add(l);
				    }
		
			}
		}
		catch(IOException ex){
				
			}
		return result;
			
		}
	
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}

}
