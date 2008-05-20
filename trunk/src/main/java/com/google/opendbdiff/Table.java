package com.google.opendbdiff;

import java.util.List;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public class Table {
    
    private String tableName;
    private List<Column> columns;
    
    public Table(String tableName) {
        this.tableName = tableName;
    }
    
    public Table(String tableName, List<Column> columns) {
        this.tableName = tableName;
        // TODO make list immutable too!
        this.columns = columns;
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public List<Column> getColumns() {
        return columns;
    }
    
    @Override
    public String toString() {
        return tableName;
    }
}
