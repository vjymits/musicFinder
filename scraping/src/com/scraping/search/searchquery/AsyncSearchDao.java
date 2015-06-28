package com.scraping.search.searchquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;

public class AsyncSearchDao implements BaseDao<AsyncSearchVO>{

	private String table = "async_search";

	@Override
	public int persist(AsyncSearchVO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into "+table+" (uuid,query,result_uri,search_type,tables,timeinMS,timestamp)values(?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		
		preparedStatement.setString(1, obj.getUuid());
		preparedStatement.setString(2, obj.getQuery());
		preparedStatement.setString(3, obj.getResultUri());
		preparedStatement.setString(4, obj.getSearchType());
		preparedStatement.setString(5, obj.getTables());
		preparedStatement.setLong(6, obj.getTimeinMS());
		preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		r=preparedStatement.executeUpdate(); 
		
		}
		catch(MySQLIntegrityConstraintViolationException ex){
			System.out.println( "QUERY IS ALREADY THERE: "+obj.getQuery());
		}
		finally{
			con.close();
			
		}
		
		return r;	
		
	}

	@Override
	public int update(long id, AsyncSearchVO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "update "+table+" set uuid = ?,query = ?,result_uri ? ,search_type =? ,tables = ?,timeinMS = ? ,timestamp = ? where id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		
		preparedStatement.setString(1, obj.getUuid());
		preparedStatement.setString(2, obj.getQuery());
		preparedStatement.setString(3, obj.getResultUri());
		preparedStatement.setString(4, obj.getSearchType());
		preparedStatement.setString(5, obj.getTables());
		preparedStatement.setLong(6, obj.getTimeinMS());
		preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
		preparedStatement.setLong(8, id);
		r=preparedStatement.executeUpdate(); 
		
		}
		
		finally{
			con.close();
			
		}
		
		return r;	
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AsyncSearchVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AsyncSearchVO selectOneById(long id) throws SQLException {
	    String sql = "select * from "+table+" where id = "+id;
	    Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			AsyncSearchVO vo = new AsyncSearchVO();
			if(rs.next()){
				
				vo.setId(rs.getLong("id"));
				vo.setQuery(rs.getString("query"));
				vo.setUuid(rs.getString("uuid"));
				vo.setResultUri(rs.getString("result_uri"));
				vo.setSearchType(rs.getString("search_type"));
				vo.setTables(rs.getString("tables"));
				vo.setTimeinMS(rs.getLong("timeinMS"));
				vo.setTimestamp(rs.getTimestamp("timestamp"));
				
			}
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	public AsyncSearchVO selectOneByUuid(String uuid){
		String sql = "select * from "+table+" where uuid = "+uuid;
	    Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			AsyncSearchVO vo = new AsyncSearchVO();
			if(rs.next()){
				
				vo.setId(rs.getLong("id"));
				vo.setQuery(rs.getString("query"));
				vo.setUuid(rs.getString("uuid"));
				vo.setResultUri(rs.getString("result_uri"));
				vo.setSearchType(rs.getString("search_type"));
				vo.setTables(rs.getString("tables"));
				vo.setTimeinMS(rs.getLong("timeinMS"));
				vo.setTimestamp(rs.getTimestamp("timestamp"));
				
			}
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	
}
