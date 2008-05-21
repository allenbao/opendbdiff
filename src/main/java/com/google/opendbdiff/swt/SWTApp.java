package com.google.opendbdiff.swt;

import com.google.opendbdiff.App;
import com.google.opendbdiff.Column;
import com.google.opendbdiff.MetadataProvider;
import com.google.opendbdiff.Table;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class SWTApp {
    
    private static final RGB COLOR_ADDED = new RGB(0xDD, 0xFF, 0xDD);
    
    public void run() {
        final Display display = new Display();
        final Shell shell = new Shell(display);
        shell.setText("Open DbDiff");
        shell.setLayout(new FillLayout());
        
        final Tree tree = new Tree(shell, SWT.BORDER);
        
        MetadataProvider mp = App.getTestProvider();
        for (Table table : mp.getTables("SIBI")) {
            TreeItem item = new TreeItem(tree, 0);
            item.setText(table.getTableName());
            item.setData(table);

            if (item.getText().equals("AUTORITATE")) {
                item.setBackground(new Color(display, COLOR_ADDED));
            }
            for (Column column : table.getColumns()) {
                TreeItem item1 = new TreeItem(item, 0);
                item1.setText(column.getColumnName());
                item1.setData(column);
            }
        }
        
        shell.setSize(300, 600);
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }
    
    public static void main(String args[]) {
        new SWTApp().run();
    }
}
