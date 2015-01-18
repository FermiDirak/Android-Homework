package manuele.bryan.homework.adapters;

import android.graphics.Color;

import java.util.GregorianCalendar;

public class ListModel {
    public int imageViewColor;
    public String title;
    public String subject;
    public GregorianCalendar date;

    public ListModel(String title, String subject, GregorianCalendar date) {
        this.imageViewColor = colorFromString(subject);
        this.title = title;
        this.subject = subject;
        this.date = date;
    }

    public static int colorFromString(String string) {
        String colorHex = String.format("#ff" + "%06X",  (0xFFFFFF & string.hashCode()));
        return Color.parseColor(colorHex);
    }

    public String toString() {
        return imageViewColor + " " + title + " " + subject + " " + date;
    }

}
