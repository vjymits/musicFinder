package com.api.search;

import java.util.Arrays;
import java.util.HashMap;
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
import org.json.simple.JSONObject;

import com.scraping.search.DBSearch;
import com.scraping.search.SearchUtil;
import com.scraping.search.searchquery.AsyncSearchDao;
import com.scraping.search.searchquery.AsyncSearchVO;
import com.scraping.spider.generic.GenericGoogleSearcher;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.mp3skull.Mp3SkullSpider;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;
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
		DBSearch srch = new DBSearch(query);
		srch.search();
		log.info("First log msg into scraping.log");
		System.err.println("nothing   ........");
		return Response.status(200).entity(srch.toJSON()).build();
	}
	
	@GET
	@Path("/sites/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response siteSearch(@PathParam("query")String q) throws Exception {
		q= q.trim();
		ThreadGroup tg = new ThreadGroup(q);
		XsongsPKSearchSpider xspd = new XsongsPKSearchSpider(q);
		Mp3MadSpider mspd = new Mp3MadSpider(q);
		//KoolwapSearchSpider kspd = new KoolwapSearchSpider(q);
		Mp3SkullSpider skullspd = new Mp3SkullSpider(q);
		//xspd.run();
		//xspd.getAllMp3Links();
		//mspd.run();
		
		//skullspd.run();
		
		return null;
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
	public Response getSearchResult(@PathParam("uuid")String uuid) throws Exception{
		AsyncSearchVO asv = asyncSearchDao.selectOneByUuid(uuid);
		String tbls = asv.getTables();
		String query = asv.getQuery();
		Set<SongVO> setOfSong = new HashSet<SongVO>();
		List<String> tableList = Arrays.asList(tbls.split(","));
		for (String table : tableList){
			SongDao dao = new SongDao();
			setOfSong.addAll(dao.selectAllByQuery(query));
		}
		String json = SearchUtil.convertSongListToJSON(setOfSong, query);		
		return Response.status(200).entity(json).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test/{q}")
	public Response test(@PathParam("q")String query){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("test", "aaj janeeee ki jid");
		map.put("q",query);
		log.info("First log msg into scraping.log111");
		System.err.println("nothing   else........");
		String json = "{"+JSONObject.toString("JSON1", map)+"}";		
		return Response.status(200).entity(json).build();
	}

	
}
