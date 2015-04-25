package com.vjy.test;

import java.util.Map;
import java.util.Set;

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
 
/**
 * @author sharvija
 *
 */
public class TestMain {
 
  public static void main(String[] args) {
	 
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
	 }*/
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
  
 
  
}