package com.scraping.db;

import java.io.IOException;
import java.sql.Connection;
import java.util.Map;

public class DBConnection {

	
	static private Map<String,String> propMap = ConfigUtil.getProperties("db/db-config.properties");
	private static ConnectionsPool pool;
	
	static{
		try{
	       pool = ConnectionsPool.getPool(propMap.get("driver"), propMap.get("url"), propMap.get("user"), propMap.get("password"), propMap.get("dbname"), Integer.parseInt(propMap.get("no_of_connections")));
	   }
	   catch(IOException ex){
			
		}
	}
	
	public static Connection getConnection(){
		
		try {
			return pool.getConnection();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return null;
	}
	
	public static Connection getSingleConnection(){
		return  ConnectionsPool.makeConnection(propMap.get("driver"), propMap.get("url"), propMap.get("user"), propMap.get("password"), propMap.get("dbname"));
	}
	
	public static void returnConnection(Connection cn){
		
		pool.releaseConnection(cn);
	}
	
	
}