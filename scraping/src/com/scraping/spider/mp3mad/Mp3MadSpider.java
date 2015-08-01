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
	
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("MP3MadConfigFile"));
	String tableName = props.get("tablename");
	SongDao songDao = new SongDao(tableName);
	public Mp3MadSpider(String query){
		this.query = query;
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		String q = this.query;
		if(this.query.contains(" "))
		   q =this.query.replaceAll(" ", "+");
		this.htmlUrl=this.htmlUrl.replaceAll("qqqq", q);
								
	}
	
	public String getTable(){
		return tableName;
	}
	
	@Override
	public void run() {
		System.out.println("----------Mp3MadSpider-----------");
		allLinks = getAllLinks();
		System.out.println(allLinks);
		allMp3Links = new HashSet<String>();
		for(String link: allLinks){
			if(!isUrlAllowed(link))
				continue;
			Set<String> setOfMp3 = LinkUtil.getLinksOfExt(link, ".mp3");
			if(setOfMp3 == null)
				continue;
			allMp3Links.addAll(setOfMp3);
			for(String mp3 : setOfMp3){
				actOnMp3(mp3);
			}
						
		}
		
		
	}
	
	public void actOnMp3(String mp3){
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
	public Set<String> getLinks() {
		return allLinks;
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
