package snoopy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    protected static final String dateSecondPattern = "yyyy-MM-dd HH:mm:ss";

    public static String getNowDateSecondFromString(){
        return new SimpleDateFormat(dateSecondPattern).format(new Date());
    }


}
