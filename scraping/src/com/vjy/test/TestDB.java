package com.vjy.test;

import java.util.HashSet;
import java.util.Set;

//import com.scraping.link.LinkUtil;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

public class TestDB {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Set<String> set = new HashSet<String>();
		SongDao dao = new SongDao();
		for(SongVO vo : dao.selectAll()){
			String url = vo.getSongUrl();
			url = url.substring(0, url.lastIndexOf('/'));
			//url = LinkUtil.removeSpaceFromUrl(url)(url);
			
			url = url.substring(url.lastIndexOf('/')+1);
			url = url.replaceAll("%20", " ");
			if(url.indexOf('(')!=-1)
				url = url.substring(0, url.indexOf('('));
			
			if(url.indexOf('-')!=-1)
				url = url.substring(0, url.indexOf('-'));
			
			url = url.trim();
			//System.out.println("album: "+url+" added : "+set.add(url));
			//set.add(url);
			
		}
		//System.out.println("Size: "+set.size());
	}

}
