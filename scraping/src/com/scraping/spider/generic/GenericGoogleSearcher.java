package com.scraping.spider.generic;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.google.GoogleSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class GenericGoogleSearcher implements Mp3Spider{
	private static SongDao dao = new SongDao("GenericGoogleSearcher");
	private String query ;
	private GoogleSpider gs;
	private Set<String> mp3Links;
	private Set<SongVO> resultVOs = new HashSet<SongVO>();;
	private Set<String> GLinks;
	public GenericGoogleSearcher(String query){
		this.query = query;
	}

	@Override
	public Set<String> getLinks() {
		return this.GLinks;
	}

	@Override
	public void run() {
		gs = new GoogleSpider(query);
		gs.run();
		Set<String> lnks = gs.getLinks();
		this.GLinks = lnks;
		Set<String> mp3Links = new HashSet<String>();
		lnks = filterValidUrl(lnks);
		for(String lnk: lnks){
			
			Mp3Spider sp = new GenericSpider(lnk);
			sp.run();
			Set<String> mp3Lnks = sp.getAllMp3Links();
			for (String oneMp3 : mp3Lnks){
				SongVO vo = new SongVO();
				vo.setSearchQueries(this.query);
				vo.setSongUri(oneMp3);
				vo.setSongUrl(oneMp3);
				vo.setStatus(0);
				try {
					dao.persist(vo);
					resultVOs.add(vo);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//mp3Lnks = filterValidUrl(mp3Lnks);
			//mp3Links.addAll(mp3Lnks);
		}
		this.mp3Links = mp3Links;
				
	}

	@Override
	public Set<String> getAllMp3Links() {
		return this.mp3Links;
	}
	public Set<SongVO> getResultVos(){
		return this.resultVOs;
		
	}
	public String resultToJSON(){
		System.err.println("search Result: "+resultVOs);
		//JSONObject json = new JSONObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json="";
		try {
			json = ow.writeValueAsString(this.resultVOs);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String jsonMap  = JSONObject.toJSONString(this.searchResult);
		
		return "{"+"\""+this.query+"\":"+json+"}";
	}
	
	private Set<String> filterValidUrl(Set<String> urls){
		Set<String> validUrls = new HashSet<String>();
		for(String url: urls){
			if(url.indexOf(' ')!=-1){
				url = url.replaceAll(" ", "%20");
				//System.out.println("URL Changed: "+url);
			}
			
			if(LinkUtil.isUriValid(url))
				validUrls.add(url);
		}
		return validUrls;
	}

}
