package com.zios.root;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ToolBox {

    private static int time;

    static  {
        time = 1;
    }

    public void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void print(String input, int time, boolean nextLine) {
        char[] in = input.toCharArray();
        if (in.length == 0) {
            return;
        }

        for (Character c : in) {
            sleep(time);
            System.out.printf("%c", c);
        }

        if (nextLine) {
            System.out.println();
        }
    }

    public void print(char input, int time, boolean nextLine) {
        sleep(time);
        System.out.printf("%c", input);

        if (nextLine) {
            System.out.println();
        }
    }

    public void print(String input, boolean nextLine) {
        print(input, time, nextLine);
    }

    public void print(char input, boolean nextLine) {
        print(input, time, nextLine);
    }

    public void printCharacters(char c, int length, int time, boolean nextLine, boolean animation) {
        if (length == 0) {
            return;
        }

        for (int n = 0; n < length; n++) {
            if (animation) {
                print(c, time, false);
            } else {
                System.out.print(c);
            }
        }

        if (nextLine) {
            System.out.println();
        }
    }

    public void printCharacters(char c, int length, boolean nextLine, boolean animation) {
        printCharacters(c, length, time, nextLine, animation);
    }


    public void pressEnter(boolean before, boolean after) {
        if (before) {
            System.out.println();
        }

        System.out.println("Press enter to continue!...");
        new Strings().getString(false);
        if (after) {
            System.out.println();
        }
    }

    public int ask(String message, boolean repeat) {
        String answer = null;

        end:
        while (true) {
            print(message.concat("?[y/n]: "), false);
            answer = new Strings().getString(1, true);

            switch (answer) {
                case "y":
                    return 1;

                case "n":
                    return 0;

                default:
                    if (repeat) {
                        continue;
                    }
                    break end;
            }
        }
        return -1;
    }

    public String getCreationTime(Path p) {
        try {
            BasicFileAttributes view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
            return view.creationTime().toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String convertSecondsMinute(float seconds) {
        String tmp = String.valueOf(seconds / 60);
        int dotIndex = tmp.indexOf('.');

        int minute = Integer.parseInt(tmp.substring(0, dotIndex)),
                second = Integer.parseInt(tmp.substring(dotIndex + 1, tmp.length())) * 60;

        return minute + ":" + String.valueOf(second).substring(0, 2);
    }

    public int convertMillisecondSecond(long millisecond) {
        return (int) TimeUnit.MILLISECONDS.toSeconds(millisecond);
    }

    public String convertMillisecondsTime(long milliseconds) {
        if (milliseconds <= 0) {
            return null;
        }

        return DurationFormatUtils.formatDuration(milliseconds, "HH:mm:ss");
    }

    public static void setTime(int time) {
        ToolBox.time = time;
    }
}
