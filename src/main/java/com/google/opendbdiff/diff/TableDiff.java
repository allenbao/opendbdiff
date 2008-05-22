package com.google.opendbdiff.diff;

import com.google.opendbdiff.Table;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 22 May 2008
 */
public class TableDiff {
    
    private Table table;
    private Table refTable;
    private Difference difference;
    private ColumnDiff columnDiffs[];
    
    public TableDiff(Table table, Table refTable, Difference difference,
            ColumnDiff[] columnDiffs) {
        this.table = table;
        this.refTable = refTable;
        this.difference = difference;
        this.columnDiffs = columnDiffs;
    }
    
    public Table getTable() {
        return table;
    }
    
    public Table getRefTable() {
        return refTable;
    }
    
    public Difference getDifference() {
        return difference;
    }
}
