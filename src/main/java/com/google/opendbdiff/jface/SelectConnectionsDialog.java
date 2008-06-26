package com.google.opendbdiff.jface;

import java.util.List;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 25 May 2008
 */
public class SelectConnectionsDialog extends Dialog {
    
    private List<String> connectionNames;
    
    public SelectConnectionsDialog(Shell shell) {
        super(shell);
    }
    
    @Override
    public void setBlockOnOpen(boolean arg0) {
        super.setBlockOnOpen(arg0);
    }
    
    public void setConnectionNames(List<String> connectionNames) {
        this.connectionNames = connectionNames;
    }
    
    @Override
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText("Select connections");
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite comp = (Composite) super.createDialogArea(parent);
        
        GridLayout layout = (GridLayout)comp.getLayout();
        layout.numColumns = 2;
        
        Label srcLabel = new Label(comp, SWT.RIGHT);
        srcLabel.setText("Source database: ");
        
        Combo srcCombo = new Combo(comp, SWT.READ_ONLY);
        srcCombo.setItems(connectionNames.toArray(new String[0]));
        
        Label destLabel = new Label(comp, SWT.RIGHT);
        destLabel.setText("Destination database: ");
        
        Combo destCombo = new Combo(comp, SWT.READ_ONLY);
        destCombo.setItems(connectionNames.toArray(new String[0]));
        
        Button okButton = getButton(IDialogConstants.OK_ID);
        okButton.setEnabled((srcCombo.getSelectionIndex() >= 0) &&
                (destCombo.getSelectionIndex() >= 0));
        
        return comp;
    }
}
