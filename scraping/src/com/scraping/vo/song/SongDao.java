package com.scraping.vo.song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
		
	}
	
	
	
	public int createTableIfNotExist(){
	String sql = "create table if not exists `music`.`"+this.tableName+"`("+
    "`id` bigint unsigned NOT NULL UNIQUE AUTO_INCREMENT,"+
	"`title` varchar(256),"+
    "`song_id` bigint,"+
    "`song_uri` varchar(256),"+
    "`song_url` varchar(256),"+
    "`status` smallint,"+
    "`artists` varchar(500),"+
    "`album` varchar(256),"+
    "`Searchq` varchar(1000),"+
    "`timestamp` datetime,"+
    "FULLTEXT search_idx (`song_url`,`artists`,`album`,`Searchq`,`title`)"+
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
		String query  = "insert into "+tableName+" (song_id,song_uri,song_url,status,timestamp,artists,album,searchq,title)values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setLong(1, obj.getSongId());
		preparedStatement.setString(2, obj.getSongUri());
		preparedStatement.setString(3, obj.getSongUrl());
		preparedStatement.setInt(4, obj.getStatus());
		preparedStatement.setTimestamp(5, obj.getTimestamp());
		preparedStatement.setString(6, obj.getArtist());
		preparedStatement.setString(7, obj.getAlbum());
		preparedStatement.setString(8, obj.getSearchQueries());
		preparedStatement.setString(9, obj.getTitle());
		r=preparedStatement.executeUpdate(); 
		
		}
		catch(MySQLIntegrityConstraintViolationException ex){
			System.out.println( "DUPLICATE URL: "+obj.getSongUrl() +" And uri: "+obj.getSongUri());
		}
		catch(SQLException ex){
			ex.printStackTrace();
			//createTableIfNotExist();
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
		String query = "select * from "+tableName;
		List<SongVO> listOfSongs = new ArrayList<SongVO>();
		Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				SongVO vo = new SongVO();
				vo.setAlbum(rs.getString("album"));
				vo.setArtist(rs.getString("artists"));
				vo.setId(rs.getLong("id"));
				vo.setSearchQueries(rs.getString("searchq"));
				vo.setSongId(rs.getLong("song_id"));
				vo.setSongUri(rs.getString("song_uri"));
				vo.setSongUrl(rs.getString("song_url"));
				//System.out.println(vo.getSongUrl());
				vo.setTimestamp(rs.getTimestamp("timestamp"));
				vo.setStatus(rs.getInt("status"));
				vo.setTable(tableName);
				listOfSongs.add(vo);
			}
			return listOfSongs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<SongVO> selectAllByQuery(String query){
		String sql = "select * from "+tableName+" where Searchq = ?";
		List<SongVO> listOfSongs = new ArrayList<SongVO>();
		Connection con = DBConnection.getSingleConnection();
		try {
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				SongVO vo = new SongVO();
				vo.setAlbum(rs.getString("album"));
				vo.setArtist(rs.getString("artists"));
				vo.setId(rs.getLong("id"));
				vo.setSearchQueries(rs.getString("searchq"));
				vo.setSongId(rs.getLong("song_id"));
				vo.setSongUri(rs.getString("song_uri"));
				vo.setSongUrl(rs.getString("song_url"));
				//System.out.println(vo.getSongUrl());
				vo.setTimestamp(rs.getTimestamp("timestamp"));
				vo.setStatus(rs.getInt("status"));
				vo.setTable(tableName);
				vo.setTitle(rs.getString("title"));
				listOfSongs.add(vo);
			}
			return listOfSongs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public SongVO selectOneById(long id) throws SQLException {
		String query = "select * from "+tableName+" where id = ?";
		//List<SongVO> listOfSongs = new ArrayList<SongVO>();
		Connection con = DBConnection.getSingleConnection();
		SongVO vo = null;
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				vo = new SongVO();
				vo.setAlbum(rs.getString("album"));
				vo.setArtist(rs.getString("artists"));
				vo.setId(rs.getLong("id"));
				vo.setSearchQueries(rs.getString("searchq"));
				vo.setSongId(rs.getLong("song_id"));
				vo.setSongUri(rs.getString("song_uri"));
				vo.setSongUrl(rs.getString("song_url"));
				//System.out.println(vo.getSongUrl());
				vo.setTimestamp(rs.getTimestamp("timestamp"));
				vo.setStatus(rs.getInt("status"));
				vo.setTable(tableName);
				
			}
			return vo;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			con.close();
		}
		return null;
	}


	public static SongDao getDao() {
		if(dao == null){
            dao = new SongDao();
        }
        return dao;

	}
	
	

}
