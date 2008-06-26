package com.google.opendbdiff;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 20 May 2008
 */
public interface MetadataProvider {
    
    static Map<DbType, MetadataProvider> PROVIDERS =
            new HashMap<DbType, MetadataProvider>();
    
    List<Table> getTables();
    List<Table> getTables(String schema);
    
}
