package com.google.opendbdiff.exceptions;

/**
 * Designates a problem in configuration files.
 * 
 * @author Octavian Ciubotaru
 * @date 25 May 2008
 */
public class ConfigurationException extends Exception {
    
    private static final long serialVersionUID = 0;
    
    public ConfigurationException(Throwable cause) {
        super(cause);
    }
    
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ConfigurationException(String message) {
        super(message);
    }
    
    public ConfigurationException() {
    }
}
