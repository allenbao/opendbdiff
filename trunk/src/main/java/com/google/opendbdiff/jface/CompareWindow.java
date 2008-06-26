package com.google.opendbdiff.jface;

import com.google.opendbdiff.diff.DiffScript;
import com.google.opendbdiff.diff.TableDiff;
import com.google.opendbdiff.diff.pg.PgDiffScript;
import org.eclipse.jface.window.ApplicationWindow;
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
    protected Control createContents(final Composite parent) {
        
        parent.setSize(700, 800);
        
        getShell().setText("Open DbDiff");
        
//        SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
//        
//        MetadataProvider mp1 = App.getTestProvider();
//        List<Table> src = mp1.getTables("public");
//        
//        MetadataProvider mp2 = App.getTestProvider2();
//        List<Table> dest = mp2.getTables("public");
//        
//        TableDiff tableDiffs[] = new Differ().diff(src, dest);
//        
//        TreeViewer treeViewer = new TreeViewer(sashForm);
//        treeViewer.setContentProvider(new MetadataTreeContentProvider());
//        treeViewer.setInput(tableDiffs);
//        ILabelProvider labelProvider = new MetadataLabelProvider();
//        ILabelDecorator labelDecorator = new DiffLabelDecorator();
//        DecoratingLabelProvider decoratingLabelProvider =
//                new DecoratingLabelProvider(labelProvider, labelDecorator);
//        treeViewer.setLabelProvider(decoratingLabelProvider);
//        
//        TextViewer textViewer =
//                new TextViewer(sashForm, SWT.MULTI | SWT.V_SCROLL);
//        Document document = new Document();
//        document.set(getScript(tableDiffs));
//        textViewer.setDocument(document);
//        textViewer.setEditable(false);
//        FontRegistry fr = JFaceResources.getFontRegistry();
//        textViewer.getControl().setFont(fr.get("Lucida Console"));
        
        return parent;
    }
    
    public static void main(String args[]) {
        CompareWindow cwin = new CompareWindow();
        cwin.setBlockOnOpen(true);
        cwin.open();
        Display.getCurrent().dispose();
    }
    
    private String getScript(TableDiff[] tableDiffs) {
        StringBuilder sb = new StringBuilder();
        DiffScript ds = new PgDiffScript();
        String script;
        for (TableDiff tableDiff : tableDiffs) {
            script = ds.getScript(tableDiff);
            if (script != null) {
                sb.append(script);
                sb.append("\n\n");
            }
        }
        return sb.toString();
    }
}
