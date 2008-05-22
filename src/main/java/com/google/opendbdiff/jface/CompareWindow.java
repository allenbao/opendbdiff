package com.google.opendbdiff.jface;

import com.google.opendbdiff.App;
import com.google.opendbdiff.MetadataProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
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
        
        parent.setSize(600, 400);
        
        getShell().setText("Open DbDiff");
        
        Composite comp = new Composite(parent, SWT.NONE);
        
        FillLayout layout = new FillLayout(SWT.HORIZONTAL);
        layout.spacing = 3;
        layout.marginHeight = 3;
        layout.marginWidth = 3;
        comp.setLayout(layout);
        
        MetadataProvider mp = App.getTestProvider();
        
        // left
        TreeViewer treeViewer = new TreeViewer(comp);
        treeViewer.setContentProvider(new MetadataTreeContentProvider());
        treeViewer.setInput(mp.getTables("SCOTT"));
        treeViewer.setLabelProvider(new MetadataLabelProvider());
        treeViewer.expandAll();
        
        // right
        TreeViewer treeViewer2 = new TreeViewer(comp);
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
