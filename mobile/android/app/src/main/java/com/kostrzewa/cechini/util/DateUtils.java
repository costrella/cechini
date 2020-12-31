package com.kostrzewa.cechini.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String parse(Date date) {
        if (date == null) return null;
        String pattern = "dd/MM/yyyy HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

}
