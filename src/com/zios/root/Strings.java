package com.zios.root;

import com.zios.eth.Internet;

import java.util.Arrays;
import java.util.Scanner;

public class Strings {

    private Scanner scanString;

    public Strings() {
        scanString = new Scanner(System.in);
    }

    public String getString(boolean trim) {
        if (trim) {
            return scanString.nextLine().trim();
        }

        return scanString.nextLine();
    }

    public String getString(int toLowerUpper, boolean trim) {
        String input = getString(trim);
        switch (toLowerUpper) {
            case 1:
                return input.toLowerCase();

            case 2:
                return input.toUpperCase();

            default:
                return input;
        }
    }

    public String getString(int toLowerUpper, boolean trim, int length) {
        String input = getString(toLowerUpper, trim);
        StringBuilder buildString = new StringBuilder();

        char[] in = input.toCharArray();
        if (in.length == 0) {
            return null;
        }

        for (int i = 0; i < length; i++) {
            buildString.append(in[i]);
        }

        return buildString.toString();
    }

    public String[] split(String input, String split) {
        if (input == null) {
            return null;
        }

        return input.split(split);
    }

    public String[] removeElement(String[] array, int index) {
        if (array == null || index < 0 || index >= array.length) {
            return array;
        }

        String[] anotherArray = new String[array.length - 1];
        for (int i = 0, k = 0; i < array.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = array[i];
        }

        return anotherArray;
    }

    /**
     * Convert normal size to human readable size.
     * @param size size of the file.
     * @return the file size in readable mode.
     */
    public String getReadableSize(long size) {
        int i = 0;
        double tmp = size;

        if (size <= 0) {
            return String.valueOf(size);
        } else if (size < 1024) {
            return size + " B";
        }

        while (tmp > 1024) {
            tmp /= 1024;
            i++;
        }
        int dotPos = String.valueOf(tmp).indexOf(".");
        String str = String.valueOf(tmp).substring(0, dotPos);
        if ((dotPos + 3) > String.valueOf(tmp).length()) {
            str += String.valueOf(tmp).substring(dotPos);
        } else {
            str += String.valueOf(tmp).substring(dotPos, dotPos + 3);
        }

        switch (i) {
            case 1:
                return str + " KB";
            case 2:
                return str + " MB";
            case 3:
                return str + " GB";
            case 4:
                return str + " TB";

            default:
                return null;
        }
    }

    public String [] convertObjectArrayToStrings(Object [] objects) {
        return  Arrays.copyOf(objects, objects.length, String[].class);
    }

    public int integerParse(String input) {
        int x = 0;

        try {
            x = Integer.parseInt(input);
            return x;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
