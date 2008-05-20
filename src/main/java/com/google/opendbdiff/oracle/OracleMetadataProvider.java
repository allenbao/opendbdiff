package com.google.opendbdiff.oracle;

import com.google.opendbdiff.MetadataProvider;
import com.google.opendbdiff.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public class OracleMetadataProvider implements MetadataProvider {
    
    private static final String QUERY_ALL_TABLES =
            "select TABLE_NAME from DBA_TABLES";
    
    private static final String QUERY_TABLES_BY_TABLESPACE =
            "select TABLE_NAME from DBA_TABLES where TABLESPACE_NAME=?";
    
//    private static final String QUERY_ALL_COLUMNS =
//            "select TABLE_NAME from DBA_TABLES";
    
    private Connection connection;
    
    public OracleMetadataProvider(Connection connection) {
        this.connection = connection;
    }
    
    private Connection getConnection() {
        return connection;
    }
    
    public List<Table> getTables() {
        return getTables(null);
    }
    
    public List<Table> getTables(String tablespaceName) {
        List<Table> tables = new ArrayList<Table>();
        try {
            ResultSet rs;
            Statement statement;
            
            // load columns
//            if (tablespaceName == null) {
//                statement = getConnection().createStatement();
//                rs = statement.executeQuery(QUERY_ALL_TABLES);
//            } else {
//                PreparedStatement ps = getConnection()
//                        .prepareStatement(QUERY_TABLES_BY_TABLESPACE);
//                ps.setString(1, tablespaceName);
//                rs = ps.executeQuery();
//                statement = ps;
//            }
//            
//            statement.close();
            
            // load tables
            if (tablespaceName == null) {
                statement = getConnection().createStatement();
                rs = statement.executeQuery(QUERY_ALL_TABLES);
            } else {
                PreparedStatement ps = getConnection()
                        .prepareStatement(QUERY_TABLES_BY_TABLESPACE);
                ps.setString(1, tablespaceName);
                rs = ps.executeQuery();
                statement = ps;
            }
            while (rs.next()) {
                Table t = new Table(rs.getString("TABLE_NAME"));
                tables.add(t);
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
}
