package com.google.opendbdiff.jface;

import com.google.opendbdiff.diff.Difference;
import com.google.opendbdiff.diff.Modifiable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 24 May 2008
 */
public class DiffLabelDecorator implements ILabelDecorator {
    
    private static final ImageDescriptor OV_ADDED = ImageDescriptor
            .createFromFile(DiffLabelDecorator.class, "added_ov.gif");
    private static final ImageDescriptor OV_DELETED = ImageDescriptor
            .createFromFile(DiffLabelDecorator.class, "deleted_ov.gif");
    private static final ImageDescriptor OV_EDITED = ImageDescriptor
            .createFromFile(DiffLabelDecorator.class, "edited_ov.gif");
    
    public String decorateText(String label, Object arg1) {
        return label;
    }
    
    public Image decorateImage(Image baseImage, Object element) {
        ImageDescriptor overlay = null;
        if (element instanceof Modifiable) {
            Modifiable m = (Modifiable) element;
            Difference d = m.getDifference();
            if (d == null) {
                d = Difference.NO_CHANGE;
            }
            switch (d) {
                case ADDED:
                    overlay = OV_ADDED;
                    break;
                case CHANGED:
                    overlay = OV_EDITED;
                    break;
                case NO_CHANGE:
                    break;
                case REMOVED:
                    overlay = OV_DELETED;
                    break;
                default:
                    throw new UnsupportedOperationException(
                            "Unsupported difference " + d);
            }
        }
        if (overlay != null) {
            DecorationOverlayIcon doi = new DecorationOverlayIcon(baseImage,
                    overlay, IDecoration.BOTTOM_RIGHT);
            return doi.createImage();
        }
        return baseImage;
    }
    
    public void addListener(ILabelProviderListener labelProviderListener) {
    }
    
    public void removeListener(ILabelProviderListener labelProviderListener) {
    }
    
    public boolean isLabelProperty(Object element, String property) {
        return false;
    }
    
    public void dispose() {
    }
}
