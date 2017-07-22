package de.zlvp;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;

public class Helferlein {

    public static String dateToString(Date datum) {
        if (datum != null) {
            return new SimpleDateFormat("dd.MM.yy").format(datum);
        }
        return null;
    }

    public static <T> DefaultListModel<T> toListModel(List<T> list) {
        DefaultListModel<T> model = new DefaultListModel<>();
        for (T person : list) {
            model.addElement(person);
        }
        return model;
    }

}
