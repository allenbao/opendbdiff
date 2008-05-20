package com.google.opendbdiff.oracle;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.MetadataProvider;
import com.google.opendbdiff.Table;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public class OracleMetadataProvider implements MetadataProvider {
    
    private static final String QUERY_ALL_TABLES =
            "select TABLE_NAME from ALL_TABLES order by TABLE_NAME";
    
    private static final String QUERY_TABLES_BY_TABLESPACE =
            "select TABLE_NAME from ALL_TABLES " +
            "where TABLESPACE_NAME=? order by TABLE_NAME";
    
    private static final String QUERY_ALL_COLUMNS =
            "select c.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.NULLABLE, " +
            "c.DATA_PRECISION, c.DATA_SCALE, c.CHAR_LENGTH, c.DATA_DEFAULT " +
            "from ALL_TAB_COLS c, ALL_TABLES t " +
            "where c.TABLE_NAME=t.TABLE_NAME " +
            "order by c.TABLE_NAME,c.COLUMN_NAME";
    
    private static final String QUERY_COLUMNS_BY_TABLESPACE =
            "select c.TABLE_NAME, c.COLUMN_NAME, c.DATA_TYPE, c.NULLABLE, " +
            "c.DATA_PRECISION, c.DATA_SCALE, c.CHAR_LENGTH, c.DATA_DEFAULT " +
            "from ALL_TAB_COLS c, ALL_TABLES t " +
            "where c.TABLE_NAME=t.TABLE_NAME and t.TABLESPACE_NAME=? " +
            "order by c.TABLE_NAME,c.COLUMN_NAME";
    
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
            PreparedStatement ps;
            
            // load columns
            Map<String, List<Column>> allColumns =
                    new TreeMap<String, List<Column>>();
            if (tablespaceName == null) {
                ps = getConnection().prepareStatement(QUERY_ALL_COLUMNS);
            } else {
                ps = getConnection()
                        .prepareStatement(QUERY_COLUMNS_BY_TABLESPACE);
                ps.setString(1, tablespaceName);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                String dataType = rs.getString("DATA_TYPE");
                Long charLength = rs.getLong("CHAR_LENGTH");
                if (!dataType.startsWith("TIMESTAMP")) {
                    if (charLength != null && !charLength.equals(0L)) {
                        dataType = dataType + '(' + charLength + ')';
                    } else {
                        Long dataPrecision = rs.getLong("DATA_PRECISION");
                        if (dataPrecision != null) {
                            Long dataScale = rs.getLong("DATA_SCALE");
                            if (dataScale != null && !dataScale.equals(0L)) {
                                dataType = dataType + '(' + dataPrecision +
                                        ',' + dataScale + ')';
                            } else {
                                dataType = dataType + '(' + dataPrecision + ')';
                            }
                        }
                    }
                }
                boolean nullable = rs.getString("NULLABLE").equals("Y");
                String columnName = rs.getString("COLUMN_NAME");
                Column column = new Column(columnName, dataType,
                        rs.getString("DATA_DEFAULT"), nullable);
                String tableName = rs.getString("TABLE_NAME");
                List<Column> columns = allColumns.get(tableName);
                if (columns == null) {
                    columns = new ArrayList<Column>();
                }
                columns.add(column);
                allColumns.put(tableName, columns);
            }
            rs.close();
            
            // load tables
            if (tablespaceName == null) {
                ps = getConnection()
                        .prepareStatement(QUERY_ALL_TABLES);
            } else {
                ps = getConnection()
                        .prepareStatement(QUERY_TABLES_BY_TABLESPACE);
                ps.setString(1, tablespaceName);
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                Table t = new Table(tableName, allColumns.get(tableName));
                tables.add(t);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
}
