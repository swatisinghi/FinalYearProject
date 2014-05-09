package com.pesse.sms.db;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.sql.DataSource;

import com.pesse.sms.common.Config;
import com.pesse.sms.common.ConfigKeys;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBConnection {

    private static DataSource dataSource;
    private static final boolean noComboPooledDataSource = false;
    private static final String JDBC_URL = Config.getProperty(ConfigKeys.JDBC_URL);
    
    static {
        dataSource = setupDataSource();
    }

    public static Connection getConnection()  {
    	
    	Connection conn = null;
    	
        try
		{
        	if(noComboPooledDataSource) {
        		conn = DriverManager.getConnection(JDBC_URL);
        	} else {
        		conn = dataSource.getConnection();
        	}
		}
		catch (Exception e)
		{
			throw (e instanceof RuntimeException) ? (RuntimeException)e : new RuntimeException(e);
		}
		
		return conn;
    }

    private static DataSource setupDataSource() {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
                cpds.setDriverClass("com.mysql.jdbc.Driver");
        } catch (Exception e) {
        	throw (e instanceof RuntimeException) ? (RuntimeException)e : new RuntimeException(e);
        }
        
        cpds.setJdbcUrl(JDBC_URL);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(50);
        cpds.setTestConnectionOnCheckin(true);
        cpds.setIdleConnectionTestPeriod(30);
        return cpds;
    }
    
    public static void main(String args[]) {
    	System.out.println(JDBC_URL);
    	System.out.println(DBConnection.getConnection());
    }
}
