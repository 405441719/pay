package com.payinxl.common.util;

import org.apache.commons.beanutils.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter implements Converter
{
    private static String dateFormatStr = "yyyy/MM/dd";
    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(dateFormatStr);

    private static String dateLongFormatStr = dateFormatStr + " HH:mm:ss";
    private static SimpleDateFormat dateTimeLongFormat = new SimpleDateFormat(dateLongFormatStr);

    @Override
	public Object convert(Class arg0, Object arg1)
    {
        if (arg1 != null)
        {
            String className = arg1.getClass().getName();
            // java.sql.Timestamp
            if ("java.sql.Timestamp".equalsIgnoreCase(className))
            {
                try
                {
                    SimpleDateFormat df = new SimpleDateFormat(dateFormatStr + " HH:mm:ss");
                    return df.parse(dateTimeLongFormat.format(arg1));
                }
                catch (Exception e)
                {
                    try
                    {
                        SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
                        return df.parse(dateTimeFormat.format(arg1));
                    }
                    catch (ParseException ex)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
            else
            {// java.util.Date,java.sql.Date
                try
                {
                    SimpleDateFormat df = new SimpleDateFormat(dateFormatStr + " HH:mm:ss");
                    return df.parse(formatLongDateTime(arg1));
                }
                catch (Exception e)
                {
                    try
                    {
                        SimpleDateFormat df = new SimpleDateFormat(dateFormatStr);
                        return df.parse(formatDateTime(arg1));
                    }
                    catch (ParseException ex)
                    {
                        e.printStackTrace();
                        return null;
                    }
                }
            }
        }
        else
        {
            return null;
        }
    }

    public static String formatDateTime(Object obj)
    {
        if (obj != null)
            return dateTimeFormat.format(obj);
        else
            return "";
    }

    public static String formatLongDateTime(Object obj)
    {
        if (obj != null)
            return dateTimeLongFormat.format(obj);
        else
            return "";
    }

}
