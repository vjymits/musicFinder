package com.scraping.search;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.scraping.db.ConfigUtil;
import com.scraping.vo.song.SongVO;

public class DBSearch implements Searcher{
	
	
	String query;
	Set<String> result;
	Map<Integer,Set<String>> searchResult;
	Set<SongVO> resultVOs = new HashSet<SongVO>();
	Timestamp timestamp;
	long id;
	

	public DBSearch(String q){
		this.query=q;
		
		timestamp= new Timestamp(System.currentTimeMillis());
	}
	
	@Override
	public void setId(long id) {
		this.id=id;
	}
	
	public long getId(){
		return id;
	}

	@Override
	public String getQuery() {
		return query;
	}

	@Override
	public void setQuery(String q) {
		this.query = q; 
		
	}

	@Override
	public Timestamp getTimestamp() {
		
		return timestamp;
	}

	@Override
	public void setTimestamp(Timestamp time) {
		this.timestamp = time;
				
	}

	@Override
	public Set<String> getResult() {
		return result;
	}

	@Override
	public void setResult(Set<String> res) {
		this.result = (LinkedHashSet<String>) res;
		
	}
	
	public Map<Integer,Set<String>> getSearchResult(){
		return this.searchResult;
	}

	@Override
	public String toJSON() {
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
	
	public void search() throws SQLException{
		Map<String, String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("dbsearchConfigFile"));
		String tables = props.get("tables").trim();
		String columns = props.get("columns").trim();
		int limit = Integer.parseInt(props.get("rowPerTable").trim());
		Set<String> arrayOfTable =new HashSet<String>(Arrays.asList(tables.split(",")));
		this.result = new LinkedHashSet<String>();
		this.searchResult = new HashMap<Integer, Set<String>>();
		int i =0;
		for (String table : arrayOfTable){
			String sql = "SELECT * FROM "+table+" WHERE MATCH("+columns+") AGAINST('"+this.query+"') ORDER BY MATCH("+columns+") AGAINST('"+this.query+"') DESC limit "+limit;
			//System.out.println(sql);
			Set<SongVO> result = SearchUtil.SearchInTable(sql);
			//System.out.println("len: "+result.size());
			int j=0;
			for(SongVO one : result){
				//System.out.println(one.getSongUrl());
				if (one != null){
					Set<String> set = this.searchResult.get(j);
					if(set == null){
						set = new HashSet<String>();
					}
				    set.add(one.getSongUri());   
					this.searchResult.put(j,set);
					this.result.add(one.getSongUrl());
					this.resultVOs.add(one);
				}
				j++;
			}
			i++;			
		}

	}
	
	

}
