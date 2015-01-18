package manuele.bryan.homework.helpers;

import android.graphics.Color;

public class ColorHelper {

    public static int hexToInt(String hex) {
        return Color.parseColor(hex);
    }

    public static String generateHexFromString(String string) {
        return String.format("#ff" + "%06X",  (0xFFFFFF & string.hashCode()));
    }

    public static int generateIntFromString(String string) {
        return hexToInt(generateHexFromString(string));
    }

}
