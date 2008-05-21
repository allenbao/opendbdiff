package com.google.opendbdiff.oracle;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.Constraint;
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
            "select TABLE_NAME from DBA_TABLES order by TABLE_NAME";
    
    private static final String QUERY_TABLES_BY_SCHEMA =
            "select TABLE_NAME from DBA_TABLES " +
            "where OWNER=? order by TABLE_NAME";
    
    private static final String QUERY_ALL_COLUMNS =
            "select TABLE_NAME, COLUMN_NAME, DATA_TYPE, NULLABLE, " +
            "DATA_PRECISION, DATA_SCALE, CHAR_LENGTH, DATA_DEFAULT " +
            "from DBA_TAB_COLS " +
            "order by TABLE_NAME, COLUMN_NAME";
    
    private static final String QUERY_COLUMNS_BY_SCHEMA =
            "select TABLE_NAME, COLUMN_NAME, DATA_TYPE, NULLABLE, " +
            "DATA_PRECISION, DATA_SCALE, CHAR_LENGTH, DATA_DEFAULT " +
            "from DBA_TAB_COLS " +
            "where OWNER=? " +
            "order by TABLE_NAME, COLUMN_NAME";
    
    private static final String QUERY_ALL_CONSTRAINTS =
            "select CONSTRAINT_NAME, TABLE_NAME, SEARCH_CONDITION, " +
            "DELETE_RULE,\"GENERATED\" " +
            "from DBA_CONSTRAINTS";
    
    private static final String QUERY_CONSTRAINTS_BY_SCHEMA =
            "select CONSTRAINT_NAME, TABLE_NAME, SEARCH_CONDITION, " +
            "DELETE_RULE,\"GENERATED\" " +
            "from DBA_CONSTRAINTS " +
            "where OWNER=?";
    
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
    
    public List<Table> getTables(String schema) {
        List<Table> tables = new ArrayList<Table>();
        try {
            ResultSet rs;
            PreparedStatement ps;
            
            // load columns
            Map<String, List<Column>> allColumns =
                    new TreeMap<String, List<Column>>();
            if (schema == null) {
                ps = getConnection().prepareStatement(QUERY_ALL_COLUMNS);
            } else {
                ps = getConnection()
                        .prepareStatement(QUERY_COLUMNS_BY_SCHEMA);
                ps.setString(1, schema);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
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
                            } else if (!dataPrecision.equals(0L)) {
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
            ps.close();
            
            // load constraints
            Map<String, List<Constraint>> allConstraints =
                    new TreeMap<String, List<Constraint>>();
            if (schema == null) {
                ps = getConnection().prepareStatement(QUERY_ALL_CONSTRAINTS);
            } else {
                ps = getConnection()
                        .prepareStatement(QUERY_CONSTRAINTS_BY_SCHEMA);
                ps.setString(1, schema);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                Constraint constraint = new Constraint(
                        rs.getString("CONSTRAINT_NAME"),
                        rs.getString("SEARCH_CONDITION"),
                        rs.getString("DELETE_RULE"),
                        rs.getString("GENERATED").equals("GENERATED NAME"));
                List<Constraint> constraints = allConstraints.get(tableName);
                if (constraints == null) {
                    constraints = new ArrayList<Constraint>();
                }
                constraints.add(constraint);
                allConstraints.put(tableName, constraints);
            }
            ps.close();
            
            // load tables
            if (schema == null) {
                ps = getConnection()
                        .prepareStatement(QUERY_ALL_TABLES);
            } else {
                ps = getConnection()
                        .prepareStatement(QUERY_TABLES_BY_SCHEMA);
                ps.setString(1, schema);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                Table t = new Table(tableName, allColumns.get(tableName),
                        allConstraints.get(tableName));
                tables.add(t);
            }
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
}
