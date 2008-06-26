package com.google.opendbdiff.postgresql;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.DbType;
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
import org.apache.log4j.Logger;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 24 May 2008
 */
public class PostgreSQLMetadataProvider implements MetadataProvider {
    
    private final Logger logger =
            Logger.getLogger(PostgreSQLMetadataProvider.class);
    
    private static final String QUERY_ALL_TABLES =
            "select TABLE_NAME " +
            "from INFORMATION_SCHEMA.TABLES " +
            "where TABLE_TYPE = 'BASE TABLE'";
    
    private static final String QUERY_TABLES_BY_SCHEMA =
            "select TABLE_NAME " +
            "from INFORMATION_SCHEMA.TABLES " +
            "where TABLE_TYPE = 'BASE TABLE' " +
            "and TABLE_SCHEMA = ?";
    
    private static final String QUERY_ALL_COLUMNS =
            "select TABLE_NAME, COLUMN_NAME, DATA_TYPE, IS_NULLABLE, " +
            "NUMERIC_PRECISION, NUMERIC_SCALE, CHARACTER_MAXIMUM_LENGTH, " +
            "COLUMN_DEFAULT " +
            "from INFORMATION_SCHEMA.COLUMNS";
    
    private static final String QUERY_COLUMNS_BY_SCHEMA =
            "select TABLE_NAME, COLUMN_NAME, DATA_TYPE, IS_NULLABLE, " +
            "NUMERIC_PRECISION, NUMERIC_SCALE, CHARACTER_MAXIMUM_LENGTH, " +
            "COLUMN_DEFAULT " +
            "from INFORMATION_SCHEMA.COLUMNS " +
            "where TABLE_SCHEMA = ?";
    
//    private static final String QUERY_ALL_CONSTRAINTS =
//            "select CONSTRAINT_NAME, TABLE_NAME, SEARCH_CONDITION, " +
//            "DELETE_RULE,\"GENERATED\" " +
//            "from DBA_CONSTRAINTS";
//    
//    private static final String QUERY_CONSTRAINTS_BY_SCHEMA =
//            "select CONSTRAINT_NAME, TABLE_NAME, SEARCH_CONDITION, " +
//            "DELETE_RULE,\"GENERATED\" " +
//            "from DBA_CONSTRAINTS " +
//            "where OWNER=?";
    
    private Connection connection;
    
    public PostgreSQLMetadataProvider(Connection connection) {
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
            if (logger.isDebugEnabled()) {
                logger.debug("Loading columns...");
            }
            Map<String, List<Column>> allColumns =
                    new TreeMap<String, List<Column>>();
            if (schema == null) {
                ps = getConnection().prepareStatement(QUERY_ALL_COLUMNS,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
            } else {
                ps = getConnection().prepareStatement(QUERY_COLUMNS_BY_SCHEMA,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, schema);
            }
            ps.setFetchDirection(ResultSet.FETCH_FORWARD);
            ps.setFetchSize(1000);
            rs = ps.executeQuery();
            rs.setFetchDirection(ResultSet.FETCH_FORWARD);
            ps.setFetchSize(1000);
            while (rs.next()) {
                String dataType = rs.getString("DATA_TYPE");
                Long charLength = rs.getLong("CHARACTER_MAXIMUM_LENGTH");
                if (!dataType.startsWith("TIMESTAMP")) {
                    if (charLength != null && !charLength.equals(0L)) {
                        dataType = dataType + '(' + charLength + ')';
                    } else {
                        Long dataPrecision = rs.getLong("NUMERIC_PRECISION");
                        if (dataPrecision != null) {
                            Long dataScale = rs.getLong("NUMERIC_SCALE");
                            if (dataScale != null && !dataScale.equals(0L)) {
                                dataType = dataType + '(' + dataPrecision +
                                        ',' + dataScale + ')';
                            } else if (!dataPrecision.equals(0L)) {
                                dataType = dataType + '(' + dataPrecision + ')';
                            }
                        }
                    }
                }
                boolean nullable = rs.getString("IS_NULLABLE").equals("Y");
                String columnName = rs.getString("COLUMN_NAME");
                if (logger.isTraceEnabled()) {
                    logger.trace("Column " + columnName);
                }
                Column column = new Column(columnName, dataType,
                        rs.getString("COLUMN_DEFAULT"), nullable);
                String tableName = rs.getString("TABLE_NAME");
                List<Column> columns = allColumns.get(tableName);
                if (columns == null) {
                    columns = new ArrayList<Column>();
                }
                columns.add(column);
                allColumns.put(tableName, columns);
            }
            rs.close();
            ps.close();
            
            // load constraints
//            if (logger.isDebugEnabled()) {
//                logger.debug("Loading constraints...");
//            }
//            Map<String, List<Constraint>> allConstraints =
//                    new TreeMap<String, List<Constraint>>();
//            if (schema == null) {
//                ps = getConnection().prepareStatement(QUERY_ALL_CONSTRAINTS,
//                        ResultSet.TYPE_FORWARD_ONLY,
//                        ResultSet.CONCUR_READ_ONLY);
//            } else {
//                ps = getConnection()
//                        .prepareStatement(QUERY_CONSTRAINTS_BY_SCHEMA,
//                            ResultSet.TYPE_FORWARD_ONLY,
//                            ResultSet.CONCUR_READ_ONLY);
//                ps.setString(1, schema);
//            }
//            ps.setFetchDirection(ResultSet.FETCH_FORWARD);
//            ps.setFetchSize(1000);
//            rs = ps.executeQuery();
//            rs.setFetchDirection(ResultSet.FETCH_FORWARD);
//            ps.setFetchSize(1000);
//            while (rs.next()) {
//                String tableName = rs.getString("TABLE_NAME");
//                Constraint constraint = new Constraint(
//                        rs.getString("CONSTRAINT_NAME"),
//                        rs.getString("SEARCH_CONDITION"),
//                        rs.getString("DELETE_RULE"),
//                        rs.getString("GENERATED").equals("GENERATED NAME"));
//                if (logger.isTraceEnabled()) {
//                    logger.trace("Constraint " +
//                            constraint.getConstraintName());
//                }
//                List<Constraint> constraints = allConstraints.get(tableName);
//                if (constraints == null) {
//                    constraints = new ArrayList<Constraint>();
//                }
//                constraints.add(constraint);
//                allConstraints.put(tableName, constraints);
//            }
//            rs.close();
//            ps.close();
            
            // load tables
            if (logger.isDebugEnabled()) {
                logger.debug("Loading tables...");
            }
            if (schema == null) {
                ps = getConnection().prepareStatement(QUERY_ALL_TABLES,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
            } else {
                ps = getConnection().prepareStatement(QUERY_TABLES_BY_SCHEMA,
                        ResultSet.TYPE_FORWARD_ONLY,
                        ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, schema);
            }
            ps.setFetchDirection(ResultSet.FETCH_FORWARD);
            ps.setFetchSize(100);
            rs = ps.executeQuery();
            rs.setFetchDirection(ResultSet.FETCH_FORWARD);
            rs.setFetchSize(100);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (logger.isTraceEnabled()) {
                    logger.trace("Table " + tableName);
                }
                Table t = new Table(tableName, allColumns.get(tableName),
                        null /*allConstraints.get(tableName)*/);
                tables.add(t);
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
}
