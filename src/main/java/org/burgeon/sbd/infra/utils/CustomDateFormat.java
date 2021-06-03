package org.burgeon.sbd.infra.utils;

import java.text.*;
import java.util.Date;
import java.util.Locale;

/**
 * 自定义时间格式解析器
 *
 * @author Sam Lu
 * @date 2021/6/3
 */
public class CustomDateFormat extends DateFormat {

    private String pattern1 = "yyyy-M-d";
    private String pattern2 = "yyyy-MM-dd";
    private String pattern3 = "yyyy-M-d H:m:s";
    private String pattern4 = "yyyy-MM-dd HH:mm:ss";
    private String pattern5 = "MMM d, yyyy h:m:s aa";
    private String pattern6 = "EEE MMM d HH:mm:ss 'CST' yyyy";

    private SimpleDateFormat format1 = new SimpleDateFormat(pattern1);
    private SimpleDateFormat format2 = new SimpleDateFormat(pattern2);
    private SimpleDateFormat format3 = new SimpleDateFormat(pattern3);
    private SimpleDateFormat format4 = new SimpleDateFormat(pattern4);
    private SimpleDateFormat format5 = new SimpleDateFormat(pattern5, Locale.ENGLISH);
    private SimpleDateFormat format6 = new SimpleDateFormat(pattern6, Locale.ENGLISH);

    private DateFormat dateFormat;

    public CustomDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        return dateFormat.format(date, toAppendTo, fieldPosition);
    }

    /**
     * 解析时间
     *
     * @param source
     * @param pos
     * @return
     */
    @Override
    public Date parse(String source, ParsePosition pos) {

        Date date;
        try {
            date = getSimpleDateFormat(source).parse(source, pos);
        } catch (Exception e) {
            date = dateFormat.parse(source, pos);
        }

        return date;
    }

    /**
     * 解析时间
     *
     * @param source
     * @return
     * @throws ParseException
     */
    @Override
    public Date parse(String source) throws ParseException {

        Date date;
        try {
            date = getSimpleDateFormat(source).parse(source);
        } catch (Exception e) {
            date = dateFormat.parse(source);
        }

        return date;
    }

    /**
     * clone
     *
     * @return
     */
    @Override
    public Object clone() {
        Object format = dateFormat.clone();
        return new CustomDateFormat((DateFormat) format);
    }

    private SimpleDateFormat getSimpleDateFormat(String str) {
        if (str == null) {
            return null;
        }

        if (str.length() >= pattern1.length() && str.length() < pattern2.length()) {
            return format1;
        } else if (str.length() == pattern2.length()) {
            return format2;
        } else if (str.length() >= pattern3.length() && str.length() < pattern4.length()) {
            return format3;
        } else if (str.length() == pattern4.length()) {
            return format4;
        } else if (str.length() >= pattern5.length() && str.length() < pattern6.length()) {
            return format5;
        } else if (str.length() >= pattern6.length()) {
            return format6;
        }

        return null;
    }

}
