package com.google.opendbdiff;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 3 Jun 2008
 */
public enum DbType {
    POSTGRESQL,
    ORACLE;
    
    static DbType fromDriver(String driver) {
        DbType dbType;
        if (driver.equals("org.postgresql.Driver")) {
            dbType = POSTGRESQL;
        } else {
            throw new UnsupportedOperationException("Driver " + driver +
                    " not supported.");
        }
        return dbType;
    }
}
