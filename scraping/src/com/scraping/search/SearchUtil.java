package com.scraping.search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import com.scraping.db.DBConnection;
import com.scraping.vo.song.SongVO;

public class SearchUtil {
	
	public static Set<SongVO> SearchInTable(String sql) throws SQLException{
		Connection con = DBConnection.getSingleConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		LinkedHashSet<SongVO> set = new LinkedHashSet<SongVO>();
		
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
			set.add(vo);
		}
		
		return set;
	}

}
