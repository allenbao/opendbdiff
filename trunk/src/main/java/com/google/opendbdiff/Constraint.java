package com.google.opendbdiff;

/**
 * 
 * @author Octavian Ciubotaru
 * @date 21 May 2008
 */
public class Constraint {
    
    private String constraintName;
    private String searchCondition;
    private String deleteRule;
    private boolean generatedName;
    
    /**
     * 
     * @param constraintName
     * @param searchCondition
     * @param deleteRule
     * @param generatedName
     */
    public Constraint(String constraintName, String searchCondition,
            String deleteRule, boolean generatedName) {
        this.constraintName = constraintName;
        this.searchCondition = searchCondition;
        this.deleteRule = deleteRule;
        this.generatedName = generatedName;
    }
    
    public String getConstraintName() {
        return constraintName;
    }
    
    public String getDeleteRule() {
        return deleteRule;
    }
    
    public String getSearchCondition() {
        return searchCondition;
    }
    
    public boolean isGeneratedName() {
        return generatedName;
    }
    
    @Override
    public String toString() {
        return constraintName + " " + deleteRule + " " + searchCondition +
                " generated " + generatedName;
    }
}
