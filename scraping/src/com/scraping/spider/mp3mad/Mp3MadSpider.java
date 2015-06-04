package com.scraping.spider.mp3mad;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class Mp3MadSpider implements Mp3Spider{
	
	private String htmlUrl="";
	private String query;
	private Set<String> allLinks;
	private Set<String> allMp3Links;
	private SongDao dao;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("MP3MadConfigFile"));
	
	public Mp3MadSpider(String query){
		
		this.query=query.trim();
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		String q =this.query.replaceAll(" ", "+");
		this.htmlUrl=this.htmlUrl.replaceAll("qqqq", q);
								
	}
	
	@Override
	public void run() {
		allLinks = getAllLinks();
		//System.out.println(allLinks);
		allMp3Links = new HashSet<String>();
		for(String link: allLinks){
			if(!isUrlAllowed(link))
				continue;
			allMp3Links.addAll(LinkUtil.getLinksOfExt(link, ".mp3"));
						
		}
		addAllMp3InDB();
		
	}

	@Override
	public Set<String> getLinks() {
		return allLinks;
	}
	
	public int addAllMp3InDB() {
		String tableName = props.get("tablename");
		SongDao dao = new SongDao(tableName);
		int c = 0;
		for (String mp3 : allMp3Links){
			SongVO vo = new SongVO();
			vo.setSongUrl(mp3);
			vo.setSongUri(mp3);
			System.out.println("mp3: "+mp3);
			vo.setSearchQueries(query);
			vo.setStatus(0);
			try {
				dao.persist(vo);
				c++;
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return c;
	}
	
	private Set<String> getAllLinks(){
			
		Set<String> result ;	
		allMp3Links = new HashSet<String>();	
		String request = this.htmlUrl;
		System.out.println("Request To mp3Mad.com: "+request);
		result = LinkUtil.getAllLinks(request);
		return result;
			
		}
	private boolean isUrlAllowed(String url){
		boolean flag = false;
		List<String> allowedDomainList = Arrays.asList(props.get("allwedDomains").split(","));
		String domain = LinkUtil.getDomainName(url);
		if(allowedDomainList.contains(domain) && (LinkUtil.getExt(url).equalsIgnoreCase(".html")))
			flag = true;
		//System.out.println("URL: "+url+"\ndomain: "+domain+"\nflag: "+flag);
		return flag;
		
		
	}
	
	@Override
	public Set<String> getAllMp3Links(){
		return this.allMp3Links;
		
	}

}
