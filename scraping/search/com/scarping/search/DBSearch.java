package com.scarping.search;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

import com.scraping.db.ConfigUtil;
import com.scraping.vo.song.SongVO;

public class DBSearch implements Searcher{
	
	
	String query;
	String[] result;
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
	public String[] getResult() {
		return result;
	}

	@Override
	public void setResult(String[] res) {
		this.result = res;
		
	}

	@Override
	public String toJSON() {
		return null;
	}
	
	public void search() throws SQLException{
		Map<String, String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("dbsearchConfigFile"));
		String tables = props.get("tables").trim();
		String columns = props.get("columns").trim();
		int limit = Integer.parseInt(props.get("rowPerTable").trim());
		String [] arrayOfTable = tables.split(",");
		this.result = new String[limit*arrayOfTable.length];
		int i =0;
		for (String table : arrayOfTable){
			String sql = "SELECT * FROM "+table+" WHERE MATCH("+columns+") AGAINST('"+this.query+"') ORDER BY MATCH("+columns+") AGAINST('"+this.query+"') DESC limit "+limit;
			System.out.println(sql);
			SongVO[] result = SearchUtil.SearchInTable(sql,limit);
			System.out.println("len: "+result.length);
			int j=0;
			for(SongVO one : result){
				//System.out.println(one.getSongUrl());
				if (one != null)
					this.result[arrayOfTable.length*j+i] = one.getSongUrl();
				j++;
			}
			i++;			
		}

	}
	
	

}
