package com.pesse.sms.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pesse.sms.db.DBLayer;

public class Config
{
	private static Logger logger = Logger.getLogger(Config.class);
	
	private static String configFileName = "sms.properties";
	
	private static Map<String, String> properties = null;
	
	public synchronized static boolean reloadConfig(boolean forceLoading)
	{
		if(!forceLoading && properties != null)
			return false;
		BufferedReader rd = null;
		boolean success = false;
		try
		{
			String filepath = new File(Config.class.getClassLoader().getResource(configFileName).toURI()).getAbsolutePath();
			InputStream in = new FileInputStream(filepath);
			rd = new BufferedReader(new InputStreamReader(in));
			Map<String, String> prop = new HashMap<String, String>(10);
			while(true)
			{
				String line = rd.readLine();
				if(line == null) 
					break;
				line = line.trim();
				if(line.length() == 0 || line.startsWith("#"))
					continue;
				int indx = line.indexOf('=');
				if(indx == -1)
					continue;
				String key = line.substring(0, indx);
				String value = line.substring(indx+1);
				key = key.trim();
				value = value.trim();
				prop.put(key, value);
				success = true;
			}
			properties = prop;
			logger.info("Config reloaded");
			logger.info(properties.toString());
			rd.close();
		}
		catch (Exception e) 
		{
			logger.error("reloadConfig exception", e);
		}
		finally {
			if(rd != null) {
				try {
					rd.close();
				} catch (Exception ignored) {}
			}
		}
		return success;
	}
	
	public static String getProperty(ConfigKeys key)
	{
		if(properties == null)
		{
			reloadConfig(false);
		}
		return properties.containsKey(key.name()) ? properties.get(key.name()) : "";
	}
	
	public static void main(String [] args) throws Exception
	{
		System.out.println(System.getProperties());
		DBLayer.checkDB();
	}
}

