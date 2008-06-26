package com.google.opendbdiff.jface;

import com.google.opendbdiff.diff.TableDiff;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class MetadataTreeContentProvider implements ITreeContentProvider {
    
    public Object[] getElements(Object input) {
        return (TableDiff[]) input;
    }
    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public void dispose() {
    }
    
    public Object[] getChildren(Object element) {
        if (element instanceof TableDiff) {
            return ((TableDiff) element).getColumnDiffs();
        }
        return null;
    }
    
    public Object getParent(Object element) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public boolean hasChildren(Object element) {
        if (element instanceof TableDiff) {
            return ((TableDiff) element).getColumnDiffs().length > 0;
        }
        return false;
    }
}
