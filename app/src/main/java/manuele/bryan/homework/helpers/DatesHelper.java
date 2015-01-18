package manuele.bryan.homework.helpers;

import java.util.Date;
import java.util.GregorianCalendar;

public final class DatesHelper {

    public static String[] getDate(GregorianCalendar date) {
        return new String[] {String.valueOf(date.get(GregorianCalendar.MONTH) + 1),
                String.valueOf(date.get(GregorianCalendar.DAY_OF_MONTH)),
                String.valueOf(date.get(GregorianCalendar.YEAR)) };
    }

//    public static int difference(int month, int day, int year) {
//        Date today = new GregorianCalendar().getTime();
//        Date dueDate = new GregorianCalendar(year, month, day).getTime();
//
//        long diff = dueDate.getTime() - today.getTime();
//        int diffDays =  (int) (diff / (24* 1000 * 60 * 60));
//
//        if (diffDays < 0) diffDays = -1;
//
//        return diffDays;
//
//    }

    public static double difference(GregorianCalendar due) {
        Date today = new GregorianCalendar().getTime();
        Date dueDate = due.getTime();

        long diff = dueDate.getTime() - today.getTime();
        double diffDays = (1.0 * diff / (24* 1000 * 60 * 60)) + 1;

        if (diffDays <= 0) diffDays = -1;

        return diffDays;

    }

    public static String dateToString(GregorianCalendar date) {
        String[] dateArray = getDate(date);
        return dateArray[0] + "-" + dateArray[1] + "-" + Integer.parseInt(dateArray[2])%100;
    }

    public static GregorianCalendar stringToDate(String date) {
        String month = "";
        String day = "";
        String year = "";

        int numberOfHyphens = 2;
        for (int i = 0; i < date.length(); i++) {
            if (date.charAt(i) == '-') {
                numberOfHyphens--;
            } else if (numberOfHyphens == 2) {
                month = month + date.charAt(i);
            } else if (numberOfHyphens == 1) {
                day = day + date.charAt(i);
            } else if (numberOfHyphens == 0) {
                year = year + date.charAt(i);
            }
        }

        return new GregorianCalendar(Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day));
    }


}
