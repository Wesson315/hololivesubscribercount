package de.wesson.hololivesubscribercount.model.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Simple Utility class to format Dates
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtil {

    private static final SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
    private static final SimpleDateFormat rfc = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    public static String formatRFC3339(String rfc3339Time){
        try {

            Date d = rfc.parse(rfc3339Time);
            return format.format(d);
        } catch (ParseException e) {
            LoggerFactory.getLogger(DateUtil.class).error("Failed to parse date '"+rfc3339Time+"'",e);
        }
        return "Null";
    }

    public static void main(String[] args) {
        System.out.println(formatRFC3339("2017-09-07T03:02:42Z"));
    }
}
