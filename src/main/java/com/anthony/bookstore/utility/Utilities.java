package com.anthony.bookstore.utility;

public class Utilities {
    public static boolean isNull(String str){
        return str==null || str.isEmpty();
    }
    public static boolean isNull(Integer i){
        return i==null || i<0;
    }
}
