package com.api.search;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.scraping.search.DBSearch;
import com.scraping.search.SearchUtil;
import com.scraping.search.searchquery.AsyncSearchDao;
import com.scraping.search.searchquery.AsyncSearchVO;
import com.scraping.spider.koolwap.KoolwapSearchSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

@Path("/search")
public class SearchAPI {
	
	Logger log = Logger.getLogger(SearchAPI.class);
	AsyncSearchDao asyncSearchDao = new AsyncSearchDao();
	
	
	@GET
	@Path("/db/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetDBSearch(@PathParam("query")String query) throws Exception{
		//TODO
		DBSearch srch = new DBSearch(query.trim());
		srch.search();
		log.info("First log msg into scraping.log");
		System.err.println("nothing   ........");
		return Response.status(200).entity(srch.toJSON()).build();
	}
	
	@GET
	@Path("/sites/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response siteSearch(@PathParam("query")String q) throws Exception {
		return Response.status(202).entity(SearchUtil.searchInSites(q.trim())).build();
	}
	
	@GET
	@Path("/ggl/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response googleSearch(@PathParam("query")String q) throws Exception{
		 		
		return Response.status(202).entity(SearchUtil.gglSearch(q.trim())).build();
		
	}
	
	@GET
	@Path("/search-result/{uuid}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSearchResult(@PathParam("uuid")String uuid) {
		AsyncSearchVO asv = asyncSearchDao.selectOneByUuid(uuid);
		if(asv == null)
			return null;//TODO proper msg 
		String tbls = asv.getTables();
		System.out.println("tables: "+tbls);
		String query = asv.getQuery();
		Set<SongVO> setOfSong = new HashSet<SongVO>();
		List<String> tableList = Arrays.asList(tbls.split(","));
		System.out.println("table list: "+tableList);
		for (String table : tableList){
			System.out.println("table: "+table);
			SongDao dao = new SongDao(table);
			setOfSong.addAll(dao.selectAllByQuery(query));
		}
		String json;
		try {
			json = SearchUtil.convertSongListToJSON(setOfSong, query);
			return Response.status(200).entity(json).build();
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
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test/{q}")
	public Response test(@PathParam("q")String query) throws SQLException{
		KoolwapSearchSpider koolspd = new KoolwapSearchSpider(query);
		Thread thrFSPD = new Thread(koolspd);
		thrFSPD.start();
		return null;
		
	}

	
}
