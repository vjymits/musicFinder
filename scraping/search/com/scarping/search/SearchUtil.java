package com.scarping.search;

import java.util.Map;
import java.util.TreeSet;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;
import com.scraping.db.ConfigUtil;
import com.scraping.db.ConnectionsPool;
import com.scraping.db.DBConnection;
import com.scraping.vo.song.SongVO;

public class SearchUtil {
	
	public static SongVO[] SearchInTable(String sql, int limit) throws SQLException{
		Connection con = DBConnection.getSingleConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		SongVO[] set = new SongVO[limit];
		int i=0;
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
			vo.setTimestamp(rs.getDate("timestamp"));
			vo.setStatus(rs.getInt("status"));
			set[i++]=vo;
		}
		
		return set;
	}

}
