package com.setoncios.mobileappdevelopment.whattheoutsidelookslike;

import java.util.Locale;

public class StringExtension {
    public static String capitalize(String text) {
        if (text == null) {
            return null;
        }
        String[] words = text.trim().split(" ");
        StringBuilder builder = new StringBuilder();
        for(String word : words) {
            if (word.length() > 0) {
                builder.append(word.substring(0, 1).toUpperCase(Locale.getDefault()));
                builder.append(word.substring(1, word.length()).toLowerCase(Locale.getDefault()));
                builder.append(" ");
            }
        }
        return builder.toString().trim();
    }
}
