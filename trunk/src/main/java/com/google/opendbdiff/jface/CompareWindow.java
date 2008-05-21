package com.google.opendbdiff.jface;

import com.google.opendbdiff.App;
import com.google.opendbdiff.MetadataProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 
 */
public class CompareWindow extends ApplicationWindow {
    
    public CompareWindow() {
        super(null);
    }
    
    @Override
    protected Control createContents(Composite parent) {
        getShell().setText("Widget Window");
        parent.setSize(300, 600);
        
        MetadataProvider mp = App.getTestProvider();
        
        TreeViewer treeViewer = new TreeViewer(parent);
        treeViewer.setContentProvider(new MetadataTreeContentProvider());
        treeViewer.setInput(mp);
        treeViewer.setLabelProvider(new MetadataLabelProvider());
        
        return parent;
    }
    
    public static void main(String args[]) {
        CompareWindow cwin = new CompareWindow();
        cwin.setBlockOnOpen(true);
        cwin.open();
        Display.getCurrent().dispose();
    }
}
