package com.google.opendbdiff;

import static com.google.opendbdiff.util.EqualsUtil.eq;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public class Table {
    
    private String tableName;
    private List<Column> columns;
    private List<Constraint> constraints;
    
    public Table(String tableName) {
        this.tableName = tableName;
    }
    
    public Table(String tableName, List<Column> columns,
            List<Constraint> constraints) {
        this.tableName = tableName;
        this.columns = columns;
        if (this.columns == null) {
            this.columns = new ArrayList<Column>();
        }
        this.constraints = constraints;
        if (this.constraints == null) {
            this.constraints = new ArrayList<Constraint>();
        }
    }
    
    public String getTableName() {
        return tableName;
    }
    
    public List<Column> getColumns() {
        return columns;
    }
    
    public List<Constraint> getConstraints() {
        return constraints;
    }
    
    @Override
    public String toString() {
        return tableName;
    }
    
    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj instanceof Table == false)) {
            return false;
        }
        return eq(tableName, ((Table) obj).tableName);
    }
    
    @Override
    public int hashCode() {
        return (tableName != null) ? tableName.hashCode() : 0;
    }
}
