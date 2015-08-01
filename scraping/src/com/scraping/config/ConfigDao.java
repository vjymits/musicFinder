package com.scraping.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;
import com.scraping.search.searchquery.AsyncSearchVO;

public class ConfigDao{

	
	public int persist(ConfigVO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into music.config (category,instance,component,name,value,description)values(?,?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		
		preparedStatement.setString(1, obj.getCategory());
		preparedStatement.setString(2, obj.getInstance());
		preparedStatement.setString(3, obj.getComponent());
		preparedStatement.setString(4, obj.getName());
		preparedStatement.setString(5, obj.getValue());
		preparedStatement.setString(6, obj.getDescription());
		
		r=preparedStatement.executeUpdate(); 
		
		}
		catch(MySQLIntegrityConstraintViolationException ex){
			System.out.println( "Config IS ALREADY THERE: "+obj.getName());
		}
		finally{
			con.close();
			
		}
		
		return r;	
	}

	
	public int update(String name, ConfigVO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "update music.config set category = ?, instance = ?, component = ?, name = ?,value = ?, description = ? where name = ?";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		
		preparedStatement.setString(1, obj.getCategory());
		preparedStatement.setString(2, obj.getInstance());
		preparedStatement.setString(3, obj.getComponent());
		preparedStatement.setString(4, obj.getName());
		preparedStatement.setString(5, obj.getValue());
		preparedStatement.setString(6, obj.getDescription());
		
		preparedStatement.setString(7, obj.getName());
		r=preparedStatement.executeUpdate(); 
		
		}
				
		finally{
			con.close();
			
		}
		
		return r;	
	}

	
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Map<String,String> selectAllByComponent(String comp) {
		String sql = "select name, value from music.config where component = '"+comp+"'";
	    Connection con = DBConnection.getSingleConnection();
	    Map<String,String> props = new HashMap<String, String>();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				props.put(rs.getString("name"), rs.getString("value"));
				
			}
			
			
		} catch (SQLException e) {
			System.out.println("SQL: "+sql);
			e.printStackTrace();
			return null;
			
		}
		return props;
	}
	

	
	public ConfigVO selectOneByName(String name) throws SQLException {
		String sql = "select * from music.config where name = '"+name+"'";
	    Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			ConfigVO vo = new ConfigVO();
			if(rs.next()){
				
				vo.setCategory(rs.getString("category"));
				vo.setComponent(rs.getString("component"));
				vo.setDescription(rs.getString("description"));
				vo.setInstance(rs.getString("instance"));
				vo.setName(rs.getString("name"));
				vo.setValue(rs.getString("value"));
								
			}
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getValueByName(String name){
		String sql = "select value from config where name = '"+name+"'";
	    Connection con = DBConnection.getSingleConnection();
	    String val = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				
				val = rs.getString("value");
								
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
	
	

}
