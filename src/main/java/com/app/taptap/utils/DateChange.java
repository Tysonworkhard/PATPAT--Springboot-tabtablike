package com.app.taptap.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateChange {
    // 将时间字符串解析为日期对象
    public Date parseDate(String timeString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(timeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 将日期对象格式化为时间字符串
    public String formatDate(DateChange date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
