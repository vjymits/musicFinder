package com.scraping.spider.indiamp3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.scraping.db.BaseDao;
import com.scraping.db.DBConnection;

public class IndiaMp3Dao implements BaseDao<IndiaMp3VO>{
	
	

	@Override
	public int persist(IndiaMp3VO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "insert into indiamp3_com_db (song_id,song_uri,song_url,status,timestamp)values(?,?,?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setLong(1, obj.getSongsId());
		preparedStatement.setString(2, obj.getSongsUri());
		preparedStatement.setString(3, obj.getSongsUrl());
		preparedStatement.setInt(4, obj.getStatus());
		preparedStatement.setDate(5, obj.getTimestamp());
		r=preparedStatement.executeUpdate(); 
		
		}
		finally{
			DBConnection.returnConnection(con);
			
		}
		
		return r;
		
	}

	@Override
	public int update(long id, IndiaMp3VO obj) throws SQLException {
		int r =0;
		Connection con=DBConnection.getConnection();
		try{
		 //con = DBConnection.getConnection();
		String query  = "update indiamp3_com_db set song_id=?,song_uri=?,song_url=?,status=?,timestamp=? where id=?";
		PreparedStatement preparedStatement = con.prepareStatement(query);
		preparedStatement.setLong(1, obj.getSongsId());
		preparedStatement.setString(2, obj.getSongsUri());
		preparedStatement.setString(3, obj.getSongsUrl());
		preparedStatement.setInt(4, obj.getStatus());
		preparedStatement.setDate(5, obj.getTimestamp());
		preparedStatement.setLong(6, obj.getId());
		r=preparedStatement.executeUpdate(); 
		
		}
		finally{
			DBConnection.returnConnection(con);
			
		}
		System.out.println("Affercted Rows = "+r);
		return r;
		
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IndiaMp3VO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IndiaMp3VO> selectOneById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	

}
