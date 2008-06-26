package com.google.opendbdiff;

import com.google.opendbdiff.exceptions.ConfigurationException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 3 Jun 2008
 */
public class Connection {
    
    private String driver;
    private String url;
    private String user;
    private String password;
    
    private DbType dbType;
    
    public Connection(String driver, String url, String user, String password) {
        this.driver = driver;
        this.url = url;
        this.user = user;
        this.password = password;
        dbType = DbType.fromDriver(driver);
    }
    
    public java.sql.Connection getJdbcConnection()
            throws ConfigurationException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationException("Check classpath. " +
                    "Could not load " + driver + " driver", e);
        }
//        DriverManager.registerDriver(DriverManager.getDriver(url));
        Properties props = new Properties();
        props.put("user", user);
        props.put("password", password);
        props.put("defaultRowPrefetch", "30");
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new ConfigurationException("Cannot connect to database " +
                    url, e);
        }
        return connection;
    }
    
    public MetadataProvider getMetadataProvider() {
        return MetadataProvider.PROVIDERS.get(dbType);
    }
}
