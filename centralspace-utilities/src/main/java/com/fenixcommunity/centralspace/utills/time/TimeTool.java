package com.fenixcommunity.centralspace.utills.time;

import lombok.extern.java.Log;

import java.time.ZonedDateTime;

@Log
public class TimeTool {

    public static boolean IS_EQUAL(ZonedDateTime dateFirst, ZonedDateTime dateSecond) {
        return dateFirst.isEqual(dateSecond);
    }
}
