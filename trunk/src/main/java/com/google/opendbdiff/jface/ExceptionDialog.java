package com.google.opendbdiff.jface;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 25 May 2008
 */
public class ExceptionDialog {
    
    public static int open(Shell shell, Exception exception) {
        Status status = new Status(IStatus.ERROR, "dummy plugin", IStatus.OK,
                exception.getMessage(), exception);
        return new ErrorDialog(shell, "An exception occured",
                exception.getMessage(), status, IStatus.ERROR).open();
    }
}
