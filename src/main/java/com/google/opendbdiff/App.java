package com.google.opendbdiff;

import com.google.opendbdiff.oracle.OracleMetadataProvider;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    
    public void showTables() throws ClassNotFoundException, SQLException {
        String driverCls = "oracle.jdbc.OracleDriver";
        Class.forName(driverCls);
        String url = "jdbc:oracle:thin:@//80.96.217.45:1521/TINREAD";
        Driver driver = DriverManager.getDriver(url);
	DriverManager.registerDriver(driver);
        Properties props = new Properties();
        props.put("user","SIBI");
        props.put("password","SIBI");
        Connection connection = DriverManager.getConnection(url, props);
        MetadataProvider mp = new OracleMetadataProvider(connection);
        for (Table t : mp.getTables("SIBI")) {
            System.out.println(t);
            for (Column c : t.getColumns()) {
                System.out.println("  " + c);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        new App().showTables();
    }
}
