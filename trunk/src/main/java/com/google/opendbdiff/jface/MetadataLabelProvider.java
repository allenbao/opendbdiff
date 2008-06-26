package com.google.opendbdiff.jface;

import com.google.opendbdiff.Column;
import com.google.opendbdiff.diff.ColumnDiff;
import com.google.opendbdiff.diff.TableDiff;
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
    
//    private static final ImageData IMAGE_ADDED = new ImageData(
//            MetadataLabelProvider.class.getResourceAsStream("added.png"));
//    private static final ImageData IMAGE_MODIFIED = new ImageData(
//            MetadataLabelProvider.class.getResourceAsStream("modified.png"));
//    private static final ImageData IMAGE_REMOVED = new ImageData(
//            MetadataLabelProvider.class.getResourceAsStream("removed.png"));
//    private static final ImageData IMAGE_NOCHANGE = new ImageData(
//            MetadataLabelProvider.class.getResourceAsStream("nochange.png"));
    
    private static final ImageData IMAGE_TABLE = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("table.png"));
    private static final ImageData IMAGE_COLUMN = new ImageData(
            MetadataLabelProvider.class.getResourceAsStream("column.png"));
    
    @Override
    public String getText(Object element) {
        if (element instanceof TableDiff) {
            return ((TableDiff) element).getTable().getTableName();
        } else
        if (element instanceof ColumnDiff) {
            Column c = ((ColumnDiff) element).getColumn();
            return c.getColumnName() + ' ' + c.getDataType();
        }
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public Image getImage(Object element) {
//        Difference d = null;
//        if (element instanceof TableDiff) {
//            d = ((TableDiff) element).getDifference();
//        } else
//        if (element instanceof ColumnDiff) {
//            d = ((ColumnDiff) element).getDifference();
//        }
//        Image image = null;
//        if (d != null) {
//            switch (d) {
//            case ADDED:
//                image = new Image(Display.getCurrent(), IMAGE_ADDED);
//                break;
//            case CHANGED:
//                image = new Image(Display.getCurrent(), IMAGE_MODIFIED);
//                break;
//            case REMOVED:
//                image = new Image(Display.getCurrent(), IMAGE_REMOVED);
//                break;
//            case NO_CHANGE:
//                image = new Image(Display.getCurrent(), IMAGE_NOCHANGE);
//                break;
//            default:
//                throw new UnsupportedOperationException(
//                        "Unsupported difference " + d);
//            }
//        }
//        return image;
        Image image = null;
        if (element instanceof TableDiff) {
            image = new Image(Display.getCurrent(), IMAGE_TABLE);
        } else
        if (element instanceof ColumnDiff) {
            image = new Image(Display.getCurrent(), IMAGE_COLUMN);
        }
        return image;
    }
}
