package com.vjy.test;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.scarping.search.DBSearch;
import com.scarping.search.searchquery.SearchQueryDao;
import com.scarping.search.searchquery.SearchQueryVO;
import com.scraping.db.DBConnection;
import com.scraping.spider.koolwap.KoolwapSearchSpider;
import com.scraping.spider.mp3mad.Mp3MadSpider;
import com.scraping.spider.mp3skull.Mp3SkullSpider;
import com.scraping.spider.xsongspk.XsongsPKSearchSpider;
import com.scraping.vo.song.SongDao;
import com.scraping.vo.song.SongVO;
 
public class TestGoogle {
	public static void main(String[] args) throws URISyntaxException, MalformedURLException, UnsupportedEncodingException, SQLException{
		
		DBSearch s = new DBSearch("kahte hai khuda");
		s.search();
		System.out.println(s.toJSON());
		for(int one : s.getSearchResult().keySet()){
			System.out.println(s.getSearchResult().get(one));
		}
		
		//String q = new String("ankhiyo se goli mare");
		//SearchQueryDao da= new SearchQueryDao();
		//SearchQueryVO vo = new SearchQueryVO();
		//vo.setQuery(q);
		//da.persist(vo);
		/*try{
		XsongsPKSearchSpider xspd = new XsongsPKSearchSpider(q);
		Mp3MadSpider mspd = new Mp3MadSpider(q);
		KoolwapSearchSpider kspd = new KoolwapSearchSpider(q);
		Mp3SkullSpider skullspd = new Mp3SkullSpider(q);
		
		xspd.run();
		Thread.sleep(1000);
		mspd.run();
		Thread.sleep(1000);
		skullspd.run();
		Thread.sleep(1000);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		//new Thread(kspd).start();
		
		}
		/*
		SearchQueryDao xDao = new SearchQueryDao("xsongspk_searchquery");
		SearchQueryDao mp3MadDao = new SearchQueryDao("mp3mad_searchquery");
		SearchQueryDao koolWap = new SearchQueryDao("koolwap_searchquery");*/
		/*SearchQueryDao dao = new SearchQueryDao();
		for(int i ='A'; i<='Z'; i++){
		    SearchQueryVO vo = dao.selectOneById(i);
		
			String q = ""+i;
			try{
			XsongsPKSearchSpider xspd = new XsongsPKSearchSpider(q);
			Mp3MadSpider mspd = new Mp3MadSpider(q);
			KoolwapSearchSpider kspd = new KoolwapSearchSpider(q);
			//new Thread(xspd).start();
			//Thread.sleep(2000);
			new Thread(kspd).start();
			//Thread.sleep(2000);
			//mspd.start()
			//new Thread(mspd).start();
			//new Thread(kspd).run();
			
				Thread.sleep(20000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		/*SongDao dao = new SongDao("djmp3fun");
		SearchQueryDao queryDao = new SearchQueryDao();
		Connection con = DBConnection.getSingleConnection();
		for(int i=1; i<79405; i++){
			try{
			SongVO one = dao.selectOneById(i);
			String url = one.getSongUrl();
			url = url.substring(url.lastIndexOf('/'));
			int ind = url.indexOf('(');
			if(ind == -1)
				ind = url.lastIndexOf('@');
			if(ind == -1)
				ind= url.lastIndexOf('.');*/
			/*if(ind == -1)
				continue;
			
			String q = url.substring(url.lastIndexOf('/')+1, ind).trim();
			q= q.replace('_', ' ').replace('-', ' ');
			System.out.println("query: "+q);
			
			SearchQueryVO vo = new SearchQueryVO();
			vo.setQuery(q);
			queryDao.persist(vo, con);
			Thread.sleep(1);
			
			}
			catch (Exception e){
				e.printStackTrace();
			}
						
		}
		//con.close();*/
		
		/*DBSearch s = new DBSearch("jiye to jiye");
		s.search();
		System.out.println(s.toJSON());
		/*for(int one : s.getSearchResult().keySet()){
			System.out.println(s.getSearchResult().get(one));
		}
		
		/*Mp3Spider spd = new GenericGoogleSearcher("didi tera devar");
		  spd.run();
		  for(String url: spd.getAllMp3Links())
			  System.out.println("mp3: "+url);
		
		/*GoogleSpider gs = new GoogleSpider("kuch door hamare saath chalo mp3");
	gs.run();
	Set<String> links = gs.getLinks();
	if (links == null){
		System.out.println("No links");
	}
	List<String> listOfLink = new ArrayList<String>(links);
	Collections.sort(listOfLink);
	//links = new HashSet<Link>(lisOfLink);
	
	//Set<String> setOfLinks = new HashSet<String>();
	for(String lnk : listOfLink){
		System.out.println(lnk);
		System.out.println(LinkUtil.responseCodeOfURI(lnk));
		//System.out.println(URLDecoder.decode(myUrl.toString(), "UTF-8"));
		/*String link = lnk.getTarget();
		setOfLinks.add(link);*/
		
		//URL myUrl = new URL(lnk.getTarget());
		
		//System.out.println(URLDecoder.decode(myUrl.toString(), "UTF-8"));
        /*System.out.println("Host: "+myUrl.getHost());
        System.out.println("Port: "+myUrl.getPort());
        System.out.println("Athority of the URL: "+myUrl.getAuthority());
        System.out.println("Query: "+myUrl.getQuery());*/
        
	}
	
	public static Set<String> getAlbum(){
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
				
				set.add(url);
				
			}
			System.out.println("size: "+set.size());
			return set;
	  }
	
//	System.out.println("#"+setOfLinks.size());
	
}
  /*private static Pattern patternDomainName;
  private Matcher matcher;
  private static final String DOMAIN_NAME_PATTERN 
	= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
  static {
	patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
  }
 
  public static void main(String[] args) {
 
	TestGoogle obj = new TestGoogle();
	Set<Link> result = obj.getDataFromGoogle("crawl+google+search+in+java");
	for(Link temp : result){
		System.out.println("Link: "+temp.getTarget());
		System.out.println("Text: "+temp.getTarget());
	}
	System.out.println(result.size());
  }
 
  public String getDomainName(String url){
 
	String domainName = "";
	matcher = patternDomainName.matcher(url);
	if (matcher.find()) {
		domainName = matcher.group(0).toLowerCase().trim();
	}
	return domainName;
 
  }
 
  private Set<Link> getDataFromGoogle(String query) {
 
	Set<Link> result = new HashSet<Link>();	
	String request = "https://www.google.com/search?q=scrap+google+search+with+Jsoup&num=20";
	System.out.println("Sending request..." + request);
 
	try {
 
		// need http protocol, set this as a Google bot agent :)
		Document doc = Jsoup
			.connect(request)
			.userAgent(
			  "Mozilla/5.0")
			.timeout(5000).get();
 
		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {
 
			String l = link.attr("href");
			String t = link.text();
			
			
			if(l.startsWith("/url?q=")){
                                //use regex to get domain name
				result.add(new LinkVO(l,t));
				System.out.println(getDomainName(l));
			}
 
		}
 
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	return result;
  }
 */

