package com.scraping.search;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
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
import com.scraping.spider.koolwap.KoolwapSearchSpider;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.search.mp3pm.Mp3pmSearchSpider;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;
import com.scraping.vo.song.SongVO;

public class SearchUtil {
	
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
	
	public static String searchInSites(String q){
		byte [] charBytes = (q+"-"+"sites").getBytes();
		String uuid = UUID.nameUUIDFromBytes(charBytes).toString();
		ThreadGroup tg = new ThreadGroup(uuid);
		
		XsongsPKSearchSpider xspd = new XsongsPKSearchSpider(q);
		Mp3MadSpider mspd = new Mp3MadSpider(q);
		FindMp3Spider fspd = new FindMp3Spider(q);
		Mp3pmSearchSpider mp3pm = new Mp3pmSearchSpider(q);
		KoolwapSearchSpider koolwap = new KoolwapSearchSpider(q);
		
		AsyncSearchVO as = new AsyncSearchVO();
		as.setQuery(q);
		as.setUuid(uuid);
		as.setSearchType("sites");
		as.setTables(fspd.getTable()+","+xspd.getTable()+","+mspd.getTable()+","+mp3pm.getTable()+","+koolwap.getTable());
		//as.setTables(mspd.getTable());
		as.setTimeinMS(System.currentTimeMillis());
		//Mp3SkullSpider skullspd = new Mp3SkullSpider(q);
		//openNewSearchResultSet(q);
		
		Thread tfspd = new Thread(tg,fspd);
		Thread txspd = new Thread(tg,xspd);
		Thread ThrKool =  new Thread(tg,koolwap);
		Thread tmspd = new Thread(tg,mspd);
		Thread ThrMp3Pm = new Thread(tg, mp3pm);
		
		ThrKool.start();
		tfspd.start();
		txspd.start();
		tmspd.start();
		ThrMp3Pm.start();
		
		
		String res = "/scraping/mp3/search/search-result/"+uuid;
		String resultUri = "{"+"\""+"resultUri"+"\":"+"\""+res+"\""+"}";
		as.setResultUri(resultUri);
		try {
			AsyncSearchVO vo = searchResultDao.selectOneByUuid(uuid);
			if(vo == null)
			   searchResultDao.persist(as);
			else{
				System.out.println("ID: "+vo.getId());
				as.setId(vo.getId());
				searchResultDao.update(vo.getId(), as);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resultUri;
			
	}
	
	public static String gglSearch(String q){
		byte [] charBytes = (q+"-"+"ggl").getBytes();
		String uuid = UUID.nameUUIDFromBytes(charBytes).toString();
		GenericGoogleSearcher ggs = new GenericGoogleSearcher(q);
		AsyncSearchVO as=  new AsyncSearchVO();
		as.setQuery(q);
		as.setUuid(uuid);
		as.setSearchType("ggl");
		as.setTables(GenericGoogleSearcher.getTable());
		as.setTimeinMS(System.currentTimeMillis());		
		new Thread(ggs).start();
		String res = "/scraping/mp3/search/search-result/"+uuid;
		String resultUri = "{"+"\""+"resultUri"+"\":"+"\""+res+"\""+"}";
		as.setResultUri(resultUri);
		try {
			AsyncSearchVO vo = searchResultDao.selectOneByUuid(uuid);
			if(vo == null)
			   searchResultDao.persist(as);
			else{
				System.out.println("ID: "+vo.getId());
				as.setId(vo.getId());
				searchResultDao.update(vo.getId(), as);
			}
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
	
	
}
