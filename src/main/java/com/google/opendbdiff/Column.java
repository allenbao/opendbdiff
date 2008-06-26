package com.google.opendbdiff;

import static com.google.opendbdiff.util.EqualsUtil.eq;
import static com.google.opendbdiff.util.EqualsUtil.eqn;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public class Column {
    
    private String columnName;
    private String dataType;
    private String defaultValue;
    private boolean nullable;
    
    public Column(String columnName, String dataType, String defaultValue,
            boolean nullable) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.nullable = nullable;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public String getDataType() {
        return dataType;
    }
    
    public String getDefaultValue() {
        return defaultValue;
    }
    
    public boolean isNullable() {
        return nullable;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(columnName);
        builder.append("\t");
        builder.append(dataType);
        if (defaultValue != null) {
            builder.append("\tDEFAULT ");
            builder.append(defaultValue);
        }
        builder.append(nullable ? "\tNULL" : "\tNOT NULL");
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj instanceof Column == false)) {
            return false;
        }
        Column r = (Column) obj;
        return eq(columnName, r.columnName) &&
                eq(dataType, r.dataType) &&
                eqn(defaultValue, r.defaultValue) &&
                eq(nullable, r.nullable);
    }
}
