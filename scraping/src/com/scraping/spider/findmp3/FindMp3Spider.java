package com.scraping.spider.findmp3;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.search.SearchUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.generic.GenericSpider;

import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class FindMp3Spider implements Mp3Spider {
	
	private String query;
	private String htmlUrl="";
	
	private Set<SongVO> searchResult;
	private Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("FindMp3ConfigFile"));
	private SongDao dao = new SongDao(props.get("table"));
	public FindMp3Spider(String query){
		this.query=query;
		searchResult = SearchUtil.getSearchResultSet(query);
	}

	@Override
	public Set<String> getLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() {
		
		System.err.println("html url: "+htmlUrl);
		createUrl();
		String request = htmlUrl;
		Set<String>	result = LinkUtil.getAllLinks(request);
		for (String url : result){
			if(!isUrlAllowed(url))
				continue;
			GenericSpider gs = new GenericSpider(url);
			gs.run();
			Set<String> mp3s = gs.getAllMp3Links();
			for(String mp3 : mp3s){
				try {
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
		List<String> allowedDomainList = Arrays.asList(props.get("allwedDomains").split(","));
		String domain = LinkUtil.getDomainName(url);
		if(allowedDomainList.contains(domain) && (LinkUtil.getExt(url).equalsIgnoreCase(".html")))
			flag = true;
		//System.out.println("URL: "+url+"\ndomain: "+domain+"\nflag: "+flag);
		return flag;
	}
	
	private void actOnMp3(String mp3) throws SQLException{
		SongVO vo = new  SongVO();
		vo.setSearchQueries(query);
		vo.setSongUri(mp3);
		vo.setSongUrl(mp3);
		vo.setStatus(0);
		dao.persist(vo);
		if (searchResult != null)
			searchResult.add(vo);
		
	}
	
	public String createUrl() {
		this.htmlUrl=this.htmlUrl+props.get("searchurl");
		String q=this.query.replace(" ", "-");
		this.htmlUrl=this.htmlUrl.replace("w1-w2-w3", q);
		return htmlUrl;
	}

	

}
