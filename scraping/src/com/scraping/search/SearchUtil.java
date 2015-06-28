package com.scraping.search;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import com.scraping.db.DBConnection;
import com.scraping.search.searchquery.AsyncSearchDao;
import com.scraping.search.searchquery.AsyncSearchVO;
import com.scraping.spider.findmp3.FindMp3Spider;
import com.scraping.spider.generic.GenericGoogleSearcher;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.mp3skull.Mp3SkullSpider;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;
import com.scraping.vo.song.SongVO;

public class SearchUtil {
	static private Map<String, Set<SongVO>> searchResultSet = new HashMap<String, Set<SongVO>>();
	static private AsyncSearchDao searchResultDao = new AsyncSearchDao();
	public static Set<SongVO> SearchInTable(String sql) throws SQLException{
		Connection con = DBConnection.getSingleConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		LinkedHashSet<SongVO> set = new LinkedHashSet<SongVO>();
		
		while(rs.next()){
			SongVO vo = new SongVO();
			vo.setAlbum(rs.getString("album"));
			vo.setArtist(rs.getString("artists"));
			vo.setId(rs.getLong("id"));
			vo.setSearchQueries(rs.getString("searchq"));
			vo.setSongId(rs.getLong("song_id"));
			vo.setSongUri(rs.getString("song_uri"));
			vo.setSongUrl(rs.getString("song_url"));
			//System.out.println(vo.getSongUrl());
			vo.setTimestamp(rs.getTimestamp("timestamp"));
			vo.setStatus(rs.getInt("status"));
			set.add(vo);
		}
		
		return set;
	}
	
	public static Set<SongVO> searchInSites(String q){
		XsongsPKSearchSpider xspd = new XsongsPKSearchSpider(q);
		Mp3MadSpider mspd = new Mp3MadSpider(q);
		FindMp3Spider fspd = new FindMp3Spider(q);
		//Mp3SkullSpider skullspd = new Mp3SkullSpider(q);
		openNewSearchResultSet(q);
		Thread txspd = new Thread(xspd);
		Thread tmspd = new Thread(mspd);
		Thread tfspd = new Thread(fspd);
		
		String r = "{task/search-result/"+q+"}";
		return null;
			
	}
	
	public static String gglSearch(String q){
		String uuid = UUID.fromString(q+"-"+"ggl").toString();
		GenericGoogleSearcher ggs = new GenericGoogleSearcher(q);
		AsyncSearchVO as=  new AsyncSearchVO();
		as.setQuery(q);
		as.setUuid(uuid);
		as.setSearchType("ggl");
		as.setTables(as.getTables());
		as.setTimeinMS(System.currentTimeMillis());		
		new Thread(ggs).start();
		String resultUri = "resultUri : "+"/scraping/mp3/search/search-result/"+uuid;	
		as.setResultUri(resultUri);
		try {
			AsyncSearchVO vo = searchResultDao.selectOneByUuid(uuid);
			if(vo == null)
			   searchResultDao.persist(as);
			else
				as.setId(vo.getId());
				searchResultDao.update(vo.getId(), as);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultUri;
	}
	
	public static String convertSongListToJSON(Set<SongVO> listOfSong,String query) throws JsonGenerationException, JsonMappingException, IOException{
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json="";
		json = ow.writeValueAsString(listOfSong);
		return "{"+"\""+query+"\":"+json+"}";
	
	}
	
	public static Set<SongVO> getSearchResultSet(String str){
		return searchResultSet.get(str);
	}
	public static void openNewSearchResultSet(String str){
		Set<SongVO> set = new HashSet<SongVO>();
		searchResultSet.put(str, set);
		
	}
	public static void closeSearchResultSet(String str){
		searchResultSet.remove(str);
	}
	
	

}
