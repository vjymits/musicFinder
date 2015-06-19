package com.vjy.test;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.scraping.db.ConfigUtil;


// Plain old Java Object it does not extend as class or implements 
// an interface

// The class registers its methods for the HTTP GET request using the @GET annotation. 
// Using the @Produces annotation, it defines that it can deliver several MIME types,
// text, XML and HTML. 

// The browser requests per default the HTML MIME type.

//Sets the path to base URL + /hello
@Path("/hello")
public class Hello {

  // This method is called if TEXT_PLAIN is request
  @GET
  @Produces("application/json")
  public String sayPlainTextHello() {
    try {
		return new JSONObject("Hello Vijay, U r amazing, U'll rule one day Insha Allaha").toString();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    return null;
  }

  // This method is called if XML is request
  @GET
  @Produces(MediaType.TEXT_XML)
  public String sayXMLHello() {
	  
    return "<?xml version=\"1.0\"?>" + "<hello> Hello Vijay, U r amazing, U'll rule one day Insha Allaha" + "</hello>";
  }

  // This method is called if HTML is request
  @GET
  @Produces(MediaType.TEXT_HTML)
  public String sayHtmlHello() {
	  Map<String, String> props = ConfigUtil.getProperties(ConfigUtil.getProperties(ConfigUtil.getConfigFile()).get("dbsearchConfigFile"));
	  String tables = props.get("tables").trim();
    return "<html> " + "<title>" + "Hello Vijay, U r amazing, U'll rule one day Insha Allaha" + "</title>"
        + "<body><h1>" + "Hello Jersey  : "+tables + "</body></h1>" + "</html> ";
  }

} 