package com.google.opendbdiff;

import com.google.opendbdiff.diff.DiffScript;
import com.google.opendbdiff.diff.Differ;
import com.google.opendbdiff.diff.TableDiff;
import com.google.opendbdiff.diff.pg.PgDiffScript;
import com.google.opendbdiff.postgresql.PostgreSQLMetadataProvider;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class App {
    
    public static MetadataProvider getTestProvider() {
        try {
//            String driverCls = "oracle.jdbc.OracleDriver";
            String driverCls = "org.postgresql.Driver";
            Class.forName(driverCls);
            String url = "jdbc:postgresql://localhost:5432/ltu";
//            String url = "jdbc:oracle:thin:@//80.96.217.45:1521/TINREAD";
//            String url = "jdbc:oracle:thin:@//192.168.1.2:1521/TINREAD_216";
            Driver driver = DriverManager.getDriver(url);
            DriverManager.registerDriver(driver);
            Properties props = new Properties();
            props.put("user", "postgres");
//            props.put("password", "");
            props.put("defaultRowPrefetch", "30");
            Connection connection = DriverManager.getConnection(url, props);
            return new PostgreSQLMetadataProvider(connection);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static MetadataProvider getTestProvider2() {
        try {
//            String driverCls = "oracle.jdbc.OracleDriver";
            String driverCls = "org.postgresql.Driver";
            Class.forName(driverCls);
            String url = "jdbc:postgresql://localhost:5432/nfu";
//            String url = "jdbc:oracle:thin:@//80.96.217.45:1521/TINREAD";
//            String url = "jdbc:oracle:thin:@//192.168.1.2:1521/TINREAD_216";
            Driver driver = DriverManager.getDriver(url);
            DriverManager.registerDriver(driver);
            Properties props = new Properties();
            props.put("user", "postgres");
//            props.put("password", "");
            props.put("defaultRowPrefetch", "30");
            Connection connection = DriverManager.getConnection(url, props);
            return new PostgreSQLMetadataProvider(connection);
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
    
    private String getScript(TableDiff[] tableDiffs) {
        StringBuilder sb = new StringBuilder();
        DiffScript ds = new PgDiffScript();
        String script;
        for (TableDiff tableDiff : tableDiffs) {
            script = ds.getScript(tableDiff);
            if (script != null) {
                sb.append(script);
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }
    
    public void showDiff() {
        
        MetadataProvider mp1 = App.getTestProvider();
        List<Table> src = mp1.getTables("public");
        
        MetadataProvider mp2 = App.getTestProvider2();
        List<Table> dest = mp2.getTables("public");
        
        TableDiff tableDiffs[] = new Differ().diff(src, dest);
        
        System.out.print(getScript(tableDiffs));
    }
    
    public static void main(String[] args) {
        new App().showDiff();
    }
}
