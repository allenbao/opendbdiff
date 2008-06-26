package com.google.opendbdiff.util;

/**
 * Very useful utils for comparing objects.
 * 
 * @author Octavian Ciubotaru
 * @date 23 May 2008
 */
public class EqualsUtil {
    
    public static boolean eq(Object r, Object l) {
        return r != null && r.equals(l);
    }
    
    public static boolean eqn(Object r, Object l) {
        return (r == l) || (r != null && r.equals(l));
    }
    
    public static boolean eq(boolean r, boolean l) {
        return r == l;
    }
}
