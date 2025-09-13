package com.event_website.Utils;

public class QueryUtil {
    public static int getOffset(Integer page, Integer pageLimit) {
        return (page - 1) * pageLimit;
    }
}
