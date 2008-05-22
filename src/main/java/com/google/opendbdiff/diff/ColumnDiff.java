package com.google.opendbdiff.diff;

import com.google.opendbdiff.Column;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 22 May 2008
 */
public class ColumnDiff {
    
    public Column column;
    public Column refColumn;
    public Difference difference;
    
    public ColumnDiff(Column column, Difference difference, Column refColumn) {
        this.column = column;
        this.difference = difference;
        this.refColumn = refColumn;
    }
    
    public Column getColumn() {
        return column;
    }
    
    public Column getRefColumn() {
        return refColumn;
    }
    
    public Difference getDifference() {
        return difference;
    }
}
