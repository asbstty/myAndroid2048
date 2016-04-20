package com.example.my2048project.utils;

import java.util.regex.Pattern;

/**
 * Created by 854638 on 2016/4/20.
 */
public class StringUtils {
    public static boolean isDigitForm(String str) {
        String reg = "^(1|2|3|4|5|6|7|8|9)[\\d]*$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str).matches();
    }
}
