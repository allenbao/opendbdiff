package com.google.opendbdiff.jface;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.Table;
import java.util.Random;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class MetadataLabelProvider extends LabelProvider {
    
    private static final ImageData IMAGE_ADDED = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("added.png"));
    private static final ImageData IMAGE_MODIFIED = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("modified.png"));
    private static final ImageData IMAGE_REMOVED = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("removed.png"));
    private static final ImageData IMAGE_NOCHANGE = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("nochange.png"));
    
    @Override
    public String getText(Object element) {
        if (element instanceof Table) {
            return ((Table) element).getTableName();
        } else
        if (element instanceof Column) {
            Column c = (Column) element;
            return c.getColumnName() + ' ' + c.getDataType();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Image getImage(Object arg0) {
        // just test the images
        Image image = null;
        switch (new Random().nextInt(4)) {
        case 0:
            image = new Image(Display.getCurrent(), IMAGE_ADDED);
            break;
        case 1:
            image = new Image(Display.getCurrent(), IMAGE_MODIFIED);
            break;
        case 2:
            image = new Image(Display.getCurrent(), IMAGE_REMOVED);
            break;
        case 3:
            image = new Image(Display.getCurrent(), IMAGE_NOCHANGE);
            break;
        }
        return image;
    }
}
