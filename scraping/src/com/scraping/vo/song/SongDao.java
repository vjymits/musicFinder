package com.scraping.vo.song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;

public class SongDao implements BaseDao<SongVO>{
	private static SongDao dao = null;
	private String tableName;
	
	public SongDao(){
		tableName= "songs";
	}
	
	public SongDao(String table){
		this.tableName = table;
		createTableIfNotExist();
	}
	
	
	
	public int createTableIfNotExist(){
	String sql = "create table if not exists `music`.`"+this.tableName+"`("+
    "`id` bigint unsigned NOT NULL UNIQUE AUTO_INCREMENT,"+
	"`title` varchar(256),"+
    "`song_id` bigint,"+
    "`song_uri` varchar(256),"+
    "`song_url` varchar(256) UNIQUE,"+
    "`status` smallint,"+
    "`artists` varchar(500),"+
    "`album` varchar(256),"+
    "`Searchq` varchar(1000),"+
    "`timestamp` datetime,"+
    "FULLTEXT search_idx (`song_url`,`artists`,`album`,`searchq`)"+
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
	
	

	@Override
	public int persist(SongVO obj) throws SQLException {
		
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into "+tableName+" (song_id,song_uri,song_url,status,timestamp,artists,album,searchq)values(?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setLong(1, obj.getSongId());
		preparedStatement.setString(2, obj.getSongUri());
		preparedStatement.setString(3, obj.getSongUrl());
		preparedStatement.setInt(4, obj.getStatus());
		preparedStatement.setDate(5, obj.getTimestamp());
		preparedStatement.setString(6, obj.getArtist());
		preparedStatement.setString(7, obj.getAlbum());
		preparedStatement.setString(8, obj.getSearchQueries());
		r=preparedStatement.executeUpdate(); 
		
		}
		catch(MySQLIntegrityConstraintViolationException ex){
			System.out.println( "url: "+obj.getSongUrl() +"is already there, nd uri: "+obj.getSongUri());
		}
		finally{
			con.close();
			
		}
		
		return r;
		
	
	}

	@Override
	public int update(long id, SongVO obj) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<SongVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SongVO> selectOneById(long id) {
		// TODO Auto-generated method stub
		return null;
	}


	public static SongDao getDao() {
		if(dao == null){
            dao = new SongDao();
        }
        return dao;

	}
	
	

}
