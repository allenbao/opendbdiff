package com.google.opendbdiff;

import java.util.List;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public interface MetadataProvider {
    
    List<Table> getTables();
    List<Table> getTables(String schema);
    
}
