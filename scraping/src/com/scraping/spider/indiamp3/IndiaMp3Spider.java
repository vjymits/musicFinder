package com.scraping.spider.indiamp3;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;

import com.scraping.db.ConfigUtil;
import com.scraping.link.LinkUtil;

public class IndiaMp3Spider {
	
	Map<String,String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("IndiaMp3ConfigFile"));
	private IndiaMp3Dao dao = new IndiaMp3Dao();
	
	public long accuireIndiaMp3(){
		
		long start=0,end =0;
		start = Long.parseLong(props.get("startSongId"));
		end = Long.parseLong(props.get("endSongId"));
		String uri = props.get("genericUri");
		long count = 0;
		for(long i=start;i<=end;i++){
			
			String oneUri = uri+i;
			IndiaMp3VO song = new IndiaMp3VO();
			String finalUrl="";
			int status;
			try {
				finalUrl = LinkUtil.getFinalRedirectedUrl(oneUri);
				status = 200;
			} catch (MalformedURLException e1) {
				status=400;
				
			} catch (IOException e1) {
				status=404;
				
			}
			
			song.setSongsId(i);
			song.setSongsUri(oneUri);
			song.setSongsUrl(finalUrl);
			song.setStatus(status);
			song.setTimestamp(new Date(System.currentTimeMillis()));
			System.out.println("Adding in songId: "+i+" uri: "+oneUri+" Finalurl: "+finalUrl);
			
			
			try {
				Thread.sleep(1);
				dao.persist(song);
				
				count ++;
			}catch(SQLIntegrityConstraintViolationException ex){
				System.out.println("Error: SQLIntegrityConstraintViolationException cought, Now Skipping...");
				
			}
			
			catch (SQLException e) {
				System.out.println("Exception in songId: "+i+" uri: "+oneUri+" url: "+finalUrl);
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Added "+count+" rows");
		return count;
		
	}
	
	public long updateIndiaMp3(){
		long start=0,end =0;
		start = Long.parseLong(props.get("startSongId"));
		end = Long.parseLong(props.get("endSongId"));
		String uri = props.get("genericUri");
		long count = 0;
		for(long i=start;i<=end;i++){
			
			String oneUri = uri+i;
			IndiaMp3VO song = new IndiaMp3VO();
			String finalUrl="";
			int status;
			try {
				finalUrl = LinkUtil.getFinalRedirectedUrl(oneUri);
				status = 200;
			} catch (MalformedURLException e1) {
				status=400;
				
			} catch (IOException e1) {
				status=404;
				
			}
			
			song.setSongsId(i);
			song.setSongsUri(oneUri);
			song.setSongsUrl(finalUrl);
			song.setStatus(status);
			song.setTimestamp(new Date(System.currentTimeMillis()));
			System.out.println("Updating songId: "+i+" uri: "+oneUri+" Finalurl: "+finalUrl);
			
			
			try {
				Thread.sleep(1);
				dao.update(i,song);
				
				count ++;
			}catch(SQLIntegrityConstraintViolationException ex){
				System.out.println("Error: SQLIntegrityConstraintViolationException cought, Now Skipping...");
				
			}
			
			catch (SQLException e) {
				System.out.println("Exception in songId: "+i+" uri: "+oneUri+" url: "+finalUrl);
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		System.out.println("Added "+count+" rows");
		return count;

	}
	

}
