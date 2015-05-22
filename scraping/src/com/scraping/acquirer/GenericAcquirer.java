package com.scraping.acquirer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;

// will consider $ID is a id

public class GenericAcquirer extends Thread{
	
	private String url;
	private long begin;
	private long end;
	private SongDao dao;
	
	public GenericAcquirer(String url, long begin, long end) throws Exception {
		super();
		this.setUrl(url);
		this.begin = begin;
		this.end = end;
		dao = new SongDao();
	}
	
	public GenericAcquirer(String pathOfconfig) throws Exception{
		Map<String,String> props = ConfigUtil.getProperties(pathOfconfig);
		this.setUrl(props.get("url"));
		this.setBegin(Long.parseLong(props.get("begin")));
		this.setEnd(Long.parseLong(props.get("end")));
		String table = props.get("table");
		if ( table != null)
			dao= new SongDao(table);
		
	}
	
	public GenericAcquirer(String url, long begin, long end, String table) throws Exception{
		this(url,begin,end);
		dao = new SongDao(table);
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) throws Exception {
		if(url.indexOf("$ID")==-1)
			throw new Exception("$ID not found in URL.");
		this.url = url;
	}

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	public Map<String,String> acquire(){
		
		Map<String,String> mapOfUrl = new HashMap<String,String>();
		long i = 0;
		for(i=this.begin; i<=this.end; i++){
			String tmpUrl = url.replace("$ID", ""+i);
			//System.out.println("tmpurl: "+tmpUrl);
			try {
				String redirectedUrl=LinkUtil.getFinalRedirectedUrl(tmpUrl);
				SongVO vo = new SongVO();
				vo.setSongUri(tmpUrl);
				vo.setSongUrl(redirectedUrl);
				vo.setSongId(i);
				dao.persist(vo);
				System.out.println(redirectedUrl);
				mapOfUrl.put(tmpUrl, redirectedUrl);
			 
			} catch (Exception e) {
				System.out.println("url: "+tmpUrl);
				e.printStackTrace();
			}
			
			
		}
		return mapOfUrl;
			
	}
	
	public void run(){
		acquire();
	}
	
	

}
