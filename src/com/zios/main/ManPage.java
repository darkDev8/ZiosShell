package com.zios.main;

import com.zios.root.ToolBox;

public class ManPage {
    private ToolBox box;

    public ManPage() {
        box = new ToolBox();
    }

    private void printCommandTemplate(String commandName, String description, int parameters) {
        box.print("\n", false);
        box.print("\t".concat(commandName), true);
        box.print("\t", false);

        box.printCharacters('-', 60, true, true);
        box.print("\t".concat(description).concat("\n"), true);

        if (parameters == 1) {
            box.print("\tParameters", true);
            box.print("\t", false);
            box.printCharacters('-', 50, true, true);
        }
    }

    public void exit() {
        printCommandTemplate("Exit", "Exits the current shell.", 1);

        box.print("\t[-y] -> Yes exit without asking.", true);
        box.pressEnter(true, false);
    }

    public void cat() {
        printCommandTemplate("Cat", "Prints the content of a text file.", 1);

        box.print("\t[-r] -> Reverse the file text to end.", true);
        box.print("\t[Name] -> The name of file.", true);

        box.pressEnter(true, false);
    }

    public void pwd() {
        printCommandTemplate("Pwd", "Prints the current directory path working in.", 0);
        box.pressEnter(true, false);
    }

    public void count() {
        printCommandTemplate("Count", "Count number of files and sub directories in current directory.", 1);

        box.print("\t[-f] -> Prints number of files in the directory.", true);
        box.print("\t[-d] -> Prints number of directories in the directory.", true);
        box.print("\t[-sf] -> Prints the sub files in the directory.", true);
        box.print("\t[-sd] -> Prints the sub directories in the directory.", true);

        box.pressEnter(true, false);
    }

    public void info() {
        printCommandTemplate("Info", "Prints the information of file or direcory based on their extension.", 1);

        box.print("\tInformation of (audio,video,documents,pdf,executables,image) files.", true);
        box.print("\tInformation of directories.\n", true);

        box.print("\t[Name] -> The name of file or directory.", true);

        box.pressEnter(true, false);
    }

    public void os() {
        printCommandTemplate("OS", "Prints the current login user and operating system information.", 0);
        box.pressEnter(true, false);
    }

    public void cp() {
        printCommandTemplate("Cp", "Copy a file or directory to another directory.", 1);

        box.print("\t[-f] -> Shows the source if file.", true);
        box.print("\t[-d] -> Shows the source is directory.", true);
        box.print("\t[Source] -> The source file or directory.", true);
        box.print("\t[Destination] -> The destination directory.", true);

        box.pressEnter(true, false);
    }

    public void rm() {
        printCommandTemplate("Rm", "Remove a file or directory", 1);

        box.print("\t[Name] -> The name of file or directory.", true);
        box.pressEnter(true, false);
    }

    public void mv() {
        printCommandTemplate("Mv", "Move a file or directory to another directory.", 1);

        box.print("\t[-f] -> Shows the source if file.", true);
        box.print("\t[-d] -> Shows the source is directory.", true);
        box.print("\t[Source] -> The source file or directory.", true);
        box.print("\t[Destination] -> The destination directory.", true);

        box.pressEnter(true, false);
    }

    public void man() {
        printCommandTemplate("Man", "Show the man page of a command.", 1);

        box.print("\t[Name] -> The name of a command.", true);
        box.pressEnter(true, false);
    }

    public void rn() {
        printCommandTemplate("Rn", "Rename a file or direcotry.", 1);

        box.print("\t[-f] -> Rename a file.", true);
        box.print("\t[-d] -> Rename a directory.", true);
        box.print("\t[Name] -> The file or directory name.", true);

        box.pressEnter(true, false);
    }

    public void cd() {
        printCommandTemplate("Cd", "Change directory to the new directory.", 1);

        box.print("\t[Name] -> Name of the new direcotry.", true);
        box.pressEnter(true, false);
    }

    public void cdDot() {
        printCommandTemplate("Cd..", "Change directory to previous directory,", 0);
        box.pressEnter(true, false);
    }

    public void get() {
        printCommandTemplate("Get", "Downloads a file from url in the current directory.", 1);

        box.print("\t[URL] -> The url of the file or page.", true);
        box.pressEnter(true, false);
    }

    public void ls() {
        printCommandTemplate("Ls", "List all files and directories int the current directory.", 1);

        box.print("\t[-f] -> List only files.", true);
        box.print("\t[-d] -> List only directories.", true);
        box.print("\t[-s] -> Show file or directory size.", true);
        box.print("\t[-t] -> Show the type of file or directory.", true);

        box.pressEnter(true, false);
    }

    public void clear() {
        printCommandTemplate("Clear", "Clears the console.", 1);

        box.print("\t[Number] -> Number of lines to clear.", true);
        box.pressEnter(true, false);
    }

    public void tan() {
        printCommandTemplate("Tao", "Turn animation on.", 0);
        box.pressEnter(true, false);
    }

    public void taf() {
        printCommandTemplate("Taf", "Turn animation off.", 0);
        box.pressEnter(true, false);
    }

    public void sat() {
        printCommandTemplate("Sat", "Set animation time.", 1);

        box.print("\t[Time] -> Time of the animation.", true);
        box.pressEnter(true, false);
    }

    public void mkdir() {
        printCommandTemplate("Mkdir", "Make new directory.", 1);

        box.print("\t[Name] -> Name of the new directory.", true);
        box.pressEnter(true, false);
    }

    public void ping() {
        printCommandTemplate("Ping", "Test the reachability of a host(URL)", 1);

        box.print("[Http URL] -> URL of the server.", true);
        box.print("[Number] -> Number of requests to server.", true);
        box.pressEnter(true, false);
    }
}
