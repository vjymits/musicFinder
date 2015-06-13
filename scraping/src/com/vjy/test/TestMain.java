package com.vjy.test;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.crawler.crawler.GenericWebSiteCrawler;
import com.scraping.acquirer.GenericAcquirer;
import com.scraping.crawler.Crawler;
import com.scraping.downloadming.DownloadmingCrawlerA2Z;
import com.scraping.downloadming.DownloadmingSpider;
import com.scraping.link.LinkUtil;
import com.scraping.spider.Mp3Spider;
import com.scraping.spider.SearchSpider;
import com.scraping.spider.Spider;
import com.scraping.spider.generic.GenericGoogleSearcher;
import com.scraping.spider.generic.GenericSpider;
import com.scraping.spider.google.GoogleSpider;
import com.scraping.spider.indiamp3.IndiaMp3Spider;
import com.scraping.spider.koolwap.KoolwapCrawler;
import com.scraping.spider.koolwap.KoolwapSearchSpider;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.mp3skull.Mp3SkullSpider;
import com.scraping.spider.mymp3singer.MyMp3SingerCrawler;
import com.scraping.spider.mymp3singer.MyMp3SingerSearchSpider;
import com.scraping.spider.xsongspk.XsongsPKCrawler;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;
 
/**
 * @author sharvija
 *
 */
public class TestMain {
 
  public static void main(String[] args) {
	 
	  
	  
	    //System.out.println("Time: "+new );
	  try {
		 // SongDao dao = new SongDao("tmptable1");
		//GenericAcquirer acq = new GenericAcquirer("db/indiamp3-config.properties");
		//GenericAcquirer acq1 =  new GenericAcquirer("db/mymp3singer.config");
		GenericAcquirer djmp2fun1 = new GenericAcquirer("http://djmp3fun.com/files/download/id/$ID",116000,117000,"djmp3fun");
		GenericAcquirer djmp2fun2 = new GenericAcquirer("http://djmp3fun.com/files/download/id/$ID",117000,118000,"djmp3fun");
		//GenericAcquirer djmp2fun3 = new GenericAcquirer("http://djmp3fun.com/files/download/id/$ID",100000,102000,"djmp3fun");
		//acq1.start();
		//acq.start();
		//djmp2fun1.start();
		djmp2fun2.start();
		
		djmp2fun1.start();
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  
	  //System.out.println(LinkUtil.getDomainName("https://www.drupal.org/drupalcon"));
	  /*String Parrenturl = "https://www.drupal.org/node/1588782";
	  String url = "https://www.drupal.org/drupalcon";
	  if(LinkUtil.isChildUrl(url))
		  url= LinkUtil.attachParentUrl(LinkUtil.getDomainName(Parrenturl), url);
	  System.out.println(url);*/
	  
	  /*GenericWebSiteCrawler crw = new GenericWebSiteCrawler("https://www.drupal.org/",0,2,true);
	  crw.run();
	  
	  /*for(String url : LinkUtil.getAllLinks("https://www.drupal.org/drupalcon")){
		  
		  System.out.println("Url: "+url);
		  
	  }
	  
	 
	  /*Mp3Spider spd = new GenericGoogleSearcher("mere sayian super star mp3 download");
	  spd.run();
	  for(String mp3 : spd.getAllMp3Links()){
		  System.out.println("mp3: "+mp3);
	  }
	  /*for(char ch= 'l' ; ch<='z'; ch++) {
		 System.out.println("ch: "+ch);
	 DownloadmingSpider spd = new DownloadmingSpider(ch);
	  spd.run();
	  try {
		Thread.sleep(30000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 }
	   SearchSpider crw = new MyMp3SingerSearchSpider("sahar dar sahar");
	  crw.run();
	  
	 
	 /* for(String link : LinkUtil.getAllLinks("http://xsongs.pk/tere-bin-jiya-nhi-jaye.html")){
		  	System.out.println("link: "+link);
	  }
	  /*Crawler crw = new XsongsPKCrawler("http://xsongs.pk/tere-bin-jiya-nhi-jaye.html",0);
	 crw.start();
	 
	  
	  /*
	  Mp3Spider spd = new KoolwapSearchSpider("band kamre me pyar karnge");
	  spd.run();
	  
	  /* Crawler crw = new KoolwapCrawler("http://koolwap.in/mp3/dld.php?id=1661841&name=Pyar+Pyar", 0);	
		crw.start();
	  
	 
	  /*GenericSpider mp3Spider = new GenericSpider("http://koolwap.in/mp3/dld.php?id=1661841&name=Pyar+Pyar");
	  mp3Spider.run();
	  Set<String> links = mp3Spider.getAllMp3Links();
	  
	  for(String mp3: links){
		  System.out.println("Mp3: "+mp3);
		  System.out.println("is Uri Valid: "+LinkUtil.isUriValid(mp3));
	  }
	  /* 
	  Mp3Spider spd = new KoolwapSearchSpider("aaj phir tum pe");
	  spd.run();
	  
	  
	  /*
	  try {
		GenericAcquirer acq = new GenericAcquirer("db/djmp3_acquire.config");
		Map<String,String> map = acq.acquire();
		for(String key : map.keySet()){
			System.out.println("key: "+key+" val: "+map.get(key));			
		}
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	 
	  
	  /*Mp3MadSpider msp = new Mp3MadSpider("aaj fir tum pe");
	  msp.run();
	  Set<String> links = msp.getAllMp3Links();
	  for(String mp3: links){
		  System.out.println("Mp3: "+mp3);
		  //System.out.println("is Uri Valid: "+LinkUtil.isUriValid(mp3));
	  }
	  
	  
	  /*Mp3Spider msp = new GenericGoogleSearcher("tu hi re mp3 download");
	  msp.run();
	  Set<String> links = msp.getAllMp3Links();
	  for(String mp3: links){
		  System.out.println("Mp3: "+mp3);
		  System.out.println("is Uri Valid: "+LinkUtil.isUriValid(mp3));
	  }*/
	  
	  /*String url = "http://mp3skull.com/mp3/sawan_aaya_hai.html";
	  GenericSpider mp3Spider = new GenericSpider(url);
	  mp3Spider.run();
	  Set<String> links = mp3Spider.getAllMp3Links();
	  
	  for(String mp3: links){
		  System.out.println("Mp3: "+mp3);
		  System.out.println("is Uri Valid: "+LinkUtil.isUriValid(mp3));
	  }
	  
	  
	  
	  
	  /*IndiaMp3Spider spider = new IndiaMp3Spider();
	  long c = spider.updateIndiaMp3();
	  System.out.println("updated rows: "+c);
	  	  
	 /* System.out.println(new File(".").getAbsolutePath());

     System.out.println("con: "+DBConnection.getConnection());
	  
	  */
	  /*try{
	  String uri = "http://www.indiamp3.com/music/download.php?song_id=93859";
	  HttpURLConnection con = (HttpURLConnection)(new URL( uri ).openConnection());
	  con.setInstanceFollowRedirects( false );
	  con.connect();
	  int responseCode = con.getResponseCode();
	  System.out.println( responseCode );
	  String location = con.getHeaderField( "Location" );
	  System.out.println( location );
	  }
	  catch(Exception ex){
		  
	  }
 
	Document doc;
	try {
 
		// need http protocol
		doc = Jsoup.connect("http://mp3skull.com/mp3/sawan_aaya_hai.html").userAgent(
				  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(5000).get();;
 
		// get page title
		String title = doc.title();
		System.out.println("title : " + title);
 
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
 
			// get the value from href attribute
			System.out.println("\nlink : " + link.attr("href"));
			System.out.println("text : " + link.text());
 
		}
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 */
  }
  public Set<String> getAlbum(){
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
			set.add(url);
			
		}
		return set;
  }
  
 
  
}