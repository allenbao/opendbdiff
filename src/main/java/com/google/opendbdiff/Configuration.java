package com.google.opendbdiff;

import com.google.opendbdiff.exceptions.ConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 25 May 2008
 */
public class Configuration {
    
    private Properties properties = new Properties();
    private List<String> connections = new ArrayList<String>();
    private List<String> connectionNames = new ArrayList<String>();
    
    private final static String configName = ".opendbdiff/config.properties";
    
    public void load() throws ConfigurationException {
        try {
            File cfgFile = new File(System.getProperty("user.home") + "/" +
                    configName);
            if (cfgFile.exists()) {
                properties.load(new FileInputStream(cfgFile));
                Set<String> connSet = new TreeSet<String>();
                for (Enumeration e = properties.propertyNames();
                        e.hasMoreElements(); ) {
                    String propertyName = (String) e.nextElement();
                    if (propertyName.startsWith("conn.")) {
                        int p = propertyName.indexOf('.', 5);
                        if (p > 0) {
                            connSet.add(propertyName.substring(5, p));
                        }
                    }
                }
                connections.addAll(connSet);
                for (String connId : connections) {
                    connectionNames.add(properties.getProperty(
                            "conn." + connId + ".name"));
                }
            }
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }
    
    public Properties getProperties() {
        return properties;
    }
    
    public List<String> getConnections() {
        return connections;
    }
    
    public List<String> getConnectionNames() {
        return connectionNames;
    }
    
    public Connection getConnection(String connectionId)
            throws ConfigurationException {
        Connection connection = null;
        try {
            Class.forName(
                    properties.getProperty("conn." + connectionId + ".driver"));
            String url =
                    properties.getProperty("conn." + connectionId + ".url");
            Driver driver = DriverManager.getDriver(url);
            DriverManager.registerDriver(driver);
            Properties props = new Properties();
            props.put("user","SIBI");
            props.put("password","SIBI");
            props.put("defaultRowPrefetch","30");
            connection = DriverManager.getConnection(url, props);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException(e);
        } catch (SQLException e) {
            throw new ConfigurationException(e);
        }
        return connection;
    }
}
