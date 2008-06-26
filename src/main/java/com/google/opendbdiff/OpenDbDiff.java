package com.google.opendbdiff;

import java.io.OutputStream;

/**
 * Entry point for programmatic access.
 * 
 * @author Octavian Ciubotaru
 * @date 3 Jun 2008
 */
public class OpenDbDiff {
    
    public OutputStream getSchemaDiff(Connection connectionA,
            Connection connectionB, String schema) {
        
        MetadataProvider mpA = connectionA.getMetadataProvider();
        MetadataProvider mpB = connectionA.getMetadataProvider();
        
        return null;
    }
}
