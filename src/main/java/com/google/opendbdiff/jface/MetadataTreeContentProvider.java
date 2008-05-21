package com.google.opendbdiff.jface;

import com.google.opendbdiff.Table;
import java.util.List;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class MetadataTreeContentProvider implements ITreeContentProvider {
    
    public Object[] getElements(Object input) {
        return ((List) input).toArray();
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public void dispose() {
    }
    
    public Object[] getChildren(Object obj) {
        if (obj instanceof Table) {
            Table table = (Table) obj;
            return table.getColumns().toArray();
        }
        return null;
    }
    
    public Object getParent(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean hasChildren(Object obj) {
        if (obj instanceof Table) {
            return ((Table) obj).getColumns().size() > 0;
        }
        return false;
    }
}
