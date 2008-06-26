package com.google.opendbdiff.diff;

import static com.google.opendbdiff.util.EqualsUtil.eq;
import com.google.opendbdiff.Column;
import com.google.opendbdiff.Table;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The class that effectively calculates diff.
 * 
 * @author Octavian Ciubotaru
 * @date 23 May 2009
 */
public class Differ {
    
    public TableDiff[] diff(List<Table> src, List<Table> dest) {
        Set<Table> destTables = new HashSet<Table>(dest);
        List<TableDiff> result = new ArrayList<TableDiff>(dest.size());
        
        boolean found;
        for (Table srcTable : src) {
            found = false;
            for (Table destTable : dest) {
                if (eq(srcTable.getTableName(), destTable.getTableName())) {
                    result.add(diff(srcTable, destTable));
                    destTables.remove(destTable);
                    found = true;
                    break;
                }
            }
            if (!found) {
                result.add(new TableDiff(srcTable, null, Difference.REMOVED,
                        toColumnDiffs(srcTable.getColumns())));
            }
        }
        for (Table destTable : destTables) {
            result.add(new TableDiff(destTable, null, Difference.ADDED,
                    toColumnDiffs(destTable.getColumns())));
        }
        
        return result.toArray(new TableDiff[0]);
    }
    
    private ColumnDiff[] toColumnDiffs(List<Column> columns) {
        ColumnDiff[] result = new ColumnDiff[columns.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ColumnDiff(columns.get(i), null, null);
        }
        return result;
    }
    
    public TableDiff diff(Table srcTable, Table destTable) {
        List<ColumnDiff> colDiffs = new ArrayList<ColumnDiff>(
                destTable.getColumns().size());
        boolean tabChanged = false;
        Set<Column> destCols = new HashSet<Column>(destTable.getColumns());
        
        boolean found;
        for (Column srcColumn : srcTable.getColumns()) {
            found = false;
            for (Column destColumn : destTable.getColumns()) {
                if (eq(srcColumn.getColumnName(), destColumn.getColumnName())) {
                    Difference d = srcColumn.equals(destColumn) ?
                                Difference.NO_CHANGE : Difference.CHANGED;
                    if (d == Difference.CHANGED) {
                        tabChanged = true;
                    }
                    colDiffs.add(new ColumnDiff(srcColumn, destColumn, d));
                    found = true;
                    destCols.remove(destColumn);
                    break;
                }
            }
            if (!found) {
                colDiffs.add(new ColumnDiff(srcColumn, null,
                        Difference.REMOVED));
            }
        }
        for (Column destColumn : destCols) {
            colDiffs.add(new ColumnDiff(destColumn, null,
                    Difference.ADDED));
        }
        
        return new TableDiff(srcTable, destTable,
                tabChanged ? Difference.CHANGED : Difference.NO_CHANGE,
                colDiffs.toArray(new ColumnDiff[0]));
    }
    
//    TODO implement Levenshtein distance (fuzzy search)
//    private boolean equals(String str1, String str2, int dist) {
//        if (dist == 0) {
//            return eq(str1, str2);
//        } else {
//            throw new NotImplementedException();
//        }
//    }
}
