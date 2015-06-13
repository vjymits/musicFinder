package com.scraping.search;

import java.sql.SQLException;
import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import com.scarping.search.DBSearch;

@Path("/search")
public class Search {
	
	@GET
	@Path("/db/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetDBSearch(@PathParam("query")String query) throws Exception{
		//TODO
		DBSearch srch = new DBSearch(query);
		//srch.search();
		String jsonRes = srch.toJSON();
		
		return Response.status(200).entity(jsonRes).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/test/{q}")
	public Response test(@PathParam("q")String query){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("test", "aaj jane ki jid");
		map.put("q",query);
		String json = "{"+JSONObject.toString("JSON", map)+"}";		
		return Response.status(200).entity(json).build();
	}

	
}
