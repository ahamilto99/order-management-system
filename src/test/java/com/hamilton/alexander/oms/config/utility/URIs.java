package com.hamilton.alexander.oms.config.utility;

public class URIs {
    
    private static final String LOCAL_HOST = "http://localhost";

    private static final char SLASH = '/';
    
    public static final String EMPLOYEES = SLASH + "employees";
    
    public static final String EMPLOYEES_FULL = LOCAL_HOST + EMPLOYEES;
    
    public static final String EMPLOYEES_SLASH_FULL = EMPLOYEES_FULL + SLASH;
    
    public static final String EMPLOYEES_SLASH = EMPLOYEES + SLASH;
    
    public static final String EMPLOYEES_ONE = EMPLOYEES_SLASH + "{id}";
    

}
