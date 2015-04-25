package com.scraping.vo.song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;

public class SongDao implements BaseDao<SongVO>{
	private static SongDao dao = null;
	
	private SongDao(){}

	@Override
	public int persist(SongVO obj) throws SQLException {
		
		int r =0;
		Connection con=DBConnection.getSingleConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into songs (song_id,song_uri,song_url,status,timestamp,artists,album,searchq)values(?,?,?,?,?,?,?,?)";
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
