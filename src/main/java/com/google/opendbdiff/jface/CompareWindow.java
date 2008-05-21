package com.google.opendbdiff.jface;

import com.google.opendbdiff.App;
import com.google.opendbdiff.MetadataProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class CompareWindow extends ApplicationWindow {
    
    public CompareWindow() {
        super(null);
    }
    
    @Override
    protected Control createContents(Composite parent) {
        getShell().setText("Widget Window");
        parent.setSize(300, 600);
        
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        parent.setLayout(gridLayout);
        
        MetadataProvider mp = App.getTestProvider();
        
        // left
        TreeViewer treeViewer = new TreeViewer(parent);
        treeViewer.setContentProvider(new MetadataTreeContentProvider());
        treeViewer.setInput(mp.getTables("SCOTT"));
        treeViewer.setLabelProvider(new MetadataLabelProvider());
        treeViewer.expandAll();
        
        // right
        TreeViewer treeViewer2 = new TreeViewer(parent);
        treeViewer2.setContentProvider(new MetadataTreeContentProvider());
        treeViewer2.setInput(mp.getTables("TOAD"));
        treeViewer2.setLabelProvider(new MetadataLabelProvider());
        treeViewer2.expandAll();
        
        return parent;
    }
    
    public static void main(String args[]) {
        CompareWindow cwin = new CompareWindow();
        cwin.setBlockOnOpen(true);
        cwin.open();
        Display.getCurrent().dispose();
    }
}
