package com.lzjlxebr.hurrypush.util;

public class NumbersUtils {
    public static int convertBinaryStringToInt(String bin){
        if (bin.contains("0"))
            return 0;
        else
            return bin.length();
    }
}
