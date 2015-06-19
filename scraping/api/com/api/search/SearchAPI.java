package com.api.search;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.scraping.search.DBSearch;
import com.scraping.spider.generic.GenericGoogleSearcher;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.mp3skull.Mp3SkullSpider;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;

@Path("/search")
public class SearchAPI {
	
	Logger log = Logger.getLogger(SearchAPI.class);
	
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
		GenericGoogleSearcher ggs = new GenericGoogleSearcher(q);
		ggs.run();
		return Response.status(200).entity(ggs.resultToJSON()).build();
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
