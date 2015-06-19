package com.scraping.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionsPool {
	private static ConnectionsPool obj=null;
	private static Connection [] cns;
	private static boolean [] free;
	private static String driver,url,user,pass,dbName;
	private static int noc;
	private static String excmsg;
	
	public static Connection makeConnection()
	{
		
		
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			excmsg="driver not found";
		}
		try {
			return DriverManager.getConnection(url+dbName,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
			excmsg="db info is incorrect";
		}
		return null;
		
		
	}
	
	private static void fullPool()	{
		cns=new Connection[noc];
		free=new boolean[noc];
		for(int i=0;i<noc;i++)
		{
			
				cns[i]=makeConnection();
			
			free[i]=true;
		}
		
	}
	/**
	 * fetch a connection object and returns if all connection is using then
	 * makes a new connection, never returns null
	 * @return Connection
	 * 
	 */
	public  Connection getConnection() throws Exception
	{
		if (excmsg!=null)
			throw new Exception(excmsg);
		for(int i=0;i<noc;i++){
			if(free[i]==true)
			{   free[i]=false;
				System.out.println("connn "+i+" assigned");
				return(cns[i]);
			}
		}
		return (makeConnection());
	}
	/*
	 * release the prefetched connection
	 * @param Connection
	 * 
	 */
	public  void releaseConnection(Connection cn)
	{
		for(int i=0;i<noc;i++)
		{
			if(free[i]==false)
			{
				if(cns[i]==cn)
				{	free[i]=true;
				System.out.println("connn "+i+" released");
			
				}
			}
		}
	}
	public static synchronized ConnectionsPool getPool(String driver,String url,String user,String pass,String dbName,int noc) throws IOException
	{
		if(obj ==null)
			obj=new ConnectionsPool();
		obj.driver=driver;
		obj.user=user;
		obj.pass=pass;
		obj.url=url;
		obj.noc=noc;
		obj.dbName=dbName;
		
		fullPool();
		return obj;
		
	}
	
	private ConnectionsPool(){}

	public static Connection makeConnection(String driver, String url,
			String user, String pass, String dbName) {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e1) {
			excmsg="driver not found";
		}
		try {
			return DriverManager.getConnection(url+dbName,user,pass);
		} catch (SQLException e) {
			e.printStackTrace();
			excmsg="db info is incorrect";
		}
		return null;
		
		
	}


}
