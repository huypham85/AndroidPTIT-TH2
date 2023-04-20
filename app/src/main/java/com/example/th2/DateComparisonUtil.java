package com.example.th2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateComparisonUtil {
    String dateString1, dateString2;

    public DateComparisonUtil(String dateString1, String dateString2) {
        this.dateString1 = dateString1;
        this.dateString2 = dateString2;
    }

    public boolean compare() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        try {
            Date date1 = dateFormat.parse(dateString1);
            Date date2 = dateFormat.parse(dateString2);

            int comparisonResult = date1.compareTo(date2);

            if (comparisonResult < 0) {
                System.out.println(dateString1 + " is before " + dateString2);
            } else if (comparisonResult > 0) {
                System.out.println(dateString1 + " is after " + dateString2);
            } else {
                System.out.println(dateString1 + " is the same as " + dateString2);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}

