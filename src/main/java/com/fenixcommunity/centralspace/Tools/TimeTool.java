package com.fenixcommunity.centralspace.Tools;

import java.time.LocalDate;

public class TimeTool {

    public static LocalDate NOW() {
        return LocalDate.now();
    }

    public static boolean IS_AFTER(LocalDate date) {
        return date.isAfter(NOW());
    }

    public static boolean IS_BEFORE(LocalDate date) {
        return date.isBefore(NOW());
    }

    public static boolean IS_EQUAL(LocalDate dateFirst, LocalDate dateSecond) {
        return dateFirst.equals(dateSecond);
    }
}
