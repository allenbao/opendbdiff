package com.google.opendbdiff.diff.pg;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.diff.ColumnDiff;
import com.google.opendbdiff.diff.DiffScript;
import com.google.opendbdiff.diff.TableDiff;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 1 Jun 2008
 */
public class PgDiffScript implements DiffScript {
    
    public String getScript(TableDiff tableDiff) {
        String script;
        switch (tableDiff.getDifference()) {
            case ADDED:
                script = createTable(tableDiff);
                break;
            case CHANGED:
                script = "changed";
                break;
            case NO_CHANGE:
                script = null;
                break;
            case REMOVED:
                script = dropTable(tableDiff);
                break;
            default:
                throw new UnsupportedOperationException(
                        tableDiff.getDifference() + " is not supported");
        }
        return script;
    }
    
    private String createTable(TableDiff tableDiff) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ");
        sb.append(tableDiff.getTable().getTableName());
        sb.append(" (\n");
        for (ColumnDiff columnDiff : tableDiff.getColumnDiffs()) {
            sb.append("    ");
            Column column = columnDiff.getColumn();
            sb.append(column.getColumnName());
            sb.append(" ");
            sb.append(column.getDataType());
            if (column.getDefaultValue() != null) {
                sb.append(" DEFAULT ");
                sb.append(column.getDefaultValue());
            }
            if (!column.isNullable()) {
                sb.append(" NOT NULL");
            }
            sb.append("\n");
        }
        sb.append(");");
        return sb.toString();
    }
    
    private String dropTable(TableDiff tableDiff) {
        return "DROP TABLE " + tableDiff.getTable().getTableName() + ";";
    }
}
