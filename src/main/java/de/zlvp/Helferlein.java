package de.zlvp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Helferlein {

    public static String dateToString(Date datum) {
        if (datum != null) {
            return new SimpleDateFormat("dd.MM.yy").format(datum);
        }
        return null;
    }

}
