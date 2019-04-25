package com.fenixcommunity.centralspace.utils;

import java.time.ZonedDateTime;

public class TimeTool {

    public static boolean IS_EQUAL(ZonedDateTime dateFirst, ZonedDateTime dateSecond) {
        return dateFirst.isEqual(dateSecond);
    }
}
