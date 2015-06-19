package com.scraping.search.searchquery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;

public class SearchQueryDao implements BaseDao<SearchQueryVO>{
	
	private String tableName;
	
	public SearchQueryDao(){
	
		tableName = "searchquery";
	}
	public SearchQueryDao(String name){
		tableName = name;
	}
	@Override
	public int persist(SearchQueryVO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into "+tableName+" (query,jsonpath,result,timestamp)values(?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		//preparedStatement.setLong(1, obj.getSongId());
		preparedStatement.setString(1, obj.getQuery());
		preparedStatement.setString(2, obj.getJsonPath());
		preparedStatement.setString(3, obj.getResult());
		preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
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
	
	public int createTableIfNotExist(){
		String sql = "create table if not exists `music`.`"+this.tableName+"`("+
	    "`id` bigint unsigned NOT NULL UNIQUE AUTO_INCREMENT,"+
		"`query` varchar(256),"+
	    "`jsonpath` bigint,"+
	    "`result` varchar(256),"+
	    "`timestamp` datetime,"+
	    "fulltext search_query_idx(`query`)"+
	    ")engine=myisam";
		
		Connection con=DBConnection.getSingleConnection();
		try{
			Statement create = con.createStatement();
			System.out.println(sql);
			return create.executeUpdate(sql);
		}
		catch(SQLException ex){
			System.out.println(ex);
			
		}
		return -1;
		}
	
	
	
	public int persist(SearchQueryVO obj, Connection con ) throws SQLException {
		int r =0;
		
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into "+tableName+" (query,jsonpath,result,timestamp)values(?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		//preparedStatement.setLong(1, obj.getSongId());
		preparedStatement.setString(1, obj.getQuery());
		preparedStatement.setString(2, obj.getJsonPath());
		preparedStatement.setString(3, obj.getResult());
		preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
		r=preparedStatement.executeUpdate(); 
		
		}
		catch(MySQLIntegrityConstraintViolationException ex){
			System.out.println( "QUERY IS ALREADY THERE: "+obj.getQuery());
		}
		
		finally{
						
		}
		
		return r;
		
	
	}
	@Override
	public int update(long id, SearchQueryVO obj) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public List<SearchQueryVO> selectAll() {
		String query = "select * from "+tableName;
		List<SearchQueryVO> list = new ArrayList<SearchQueryVO>();
		Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				SearchQueryVO vo = new SearchQueryVO();
				vo.setId(rs.getLong("id"));
				vo.setQuery(rs.getString("query"));
				vo.setJsonPath(rs.getString("jsonpath"));
				vo.setResult(rs.getString("result"));
				vo.setTime(rs.getTimestamp("timestamp"));
				list.add(vo);
			}
			return list;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;}
	
	@Override
	public SearchQueryVO selectOneById(long id) {

		String query = "select * from "+tableName+" where id = "+id;
		
		Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			SearchQueryVO vo = new SearchQueryVO();
			if(rs.next()){
				
				vo.setId(rs.getLong("id"));
				vo.setQuery(rs.getString("query"));
				vo.setJsonPath(rs.getString("jsonpath"));
				vo.setResult(rs.getString("result"));
				vo.setTime(rs.getTimestamp("timestamp"));
				
			}
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
}

	
	


