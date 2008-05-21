package com.google.opendbdiff.jface;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.Table;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class MetadataLabelProvider extends LabelProvider {
    
    @Override
    public String getText(Object obj) {
        if (obj instanceof Table) {
            return ((Table) obj).getTableName();
        } else
        if (obj instanceof Column) {
            return ((Column) obj).getColumnName();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
