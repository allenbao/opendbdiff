package com.google.opendbdiff;

import com.google.opendbdiff.oracle.OracleMetadataProvider;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class App {
    
    public static MetadataProvider getTestProvider() {
        try {
            String driverCls = "oracle.jdbc.OracleDriver";
            Class.forName(driverCls);
            String url = "jdbc:oracle:thin:@//80.96.217.45:1521/TINREAD";
//            String url = "jdbc:oracle:thin:@//192.168.1.2:1521/TINREAD_216";
            Driver driver = DriverManager.getDriver(url);
            DriverManager.registerDriver(driver);
            Properties props = new Properties();
            props.put("user","SIBI");
            props.put("password","SIBI");
            Connection connection = DriverManager.getConnection(url, props);
            return new OracleMetadataProvider(connection);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void showTables() {
        MetadataProvider mp = getTestProvider();
        for (Table t : mp.getTables("SIBI")) {
            System.out.println(t);
            for (Column c : t.getColumns()) {
                System.out.println("     " + c);
            }
            for (Constraint c : t.getConstraints()) {
                System.out.println(" [c] " + c);
            }
            System.out.println();
        }
    }
    
    public static void main(String[] args) {
        new App().showTables();
    }
}
