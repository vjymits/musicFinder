package com.scraping.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.scraping.config.ConfigDao;

public class ConfigUtil {
	
	private static ConfigDao cnfDao = new ConfigDao();
	
	
	public static Map<String,String> getProperties(String filename){
		
		Map<String,String> map = new HashMap<String, String>();
		try {
			File file = new File(filename);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				String value = properties.getProperty(key);
				//System.out.println(key + ": " + value);
				map.put(key, value);				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return map;
	}
	
	public static String getConfigFile(){
		File f = new File(".");
		System.err.println("current file: "+f.getAbsolutePath());
		return "D:\\etc\\apache-tomcat-6.0.44\\webapps\\scraping\\WEB-INF\\db\\db-config.properties";
	}
	
	public static String getConfigValue(String name){
		return cnfDao.getValueByName(name);
	}
	
	public static Map<String,String> getConfigByComponent(String comp){
		return cnfDao.selectAllByComponent(comp);
	}
		

	

}
