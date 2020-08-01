package com.zios.main;

import com.zios.database.Conn;
import com.zios.database.Database;
import com.zios.filesys.files.Audio;
import com.zios.filesys.files.Document;
import com.zios.filesys.files.FileSYS;
import com.zios.filesys.files.Unkown;
import com.zios.root.RootOS;
import com.zios.root.Software;
import com.zios.root.Strings;
import com.zios.root.ToolBox;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.print.Doc;
import javax.xml.crypto.Data;
import java.nio.file.FileSystem;

public class Shell {
    private static String username;
    private static String path;
    private static String command;

    private static ToolBox box;
    private static RootOS root;
    private static Database database;

    static {
        root = new RootOS();
        box = new ToolBox();

        username = root.getUsername();
        path = root.getExecutablePath();
        database = new Database();
    }

    public static void main(String[] args) {
        Software soft = new Software();

        if (!soft.increaseBuildNumber()) {
            box.print("Unable to increase the build number.", true);
        }

        if (!Conn.connect()) {
            System.exit(1);
        } else {
            ToolBox.setTime(database.getAnimationTime());
        }

        Strings strings = new Strings();
        soft.title();

        while (true) {
            System.out.printf("(%s)$%s> ", username, path);
            command = strings.getString(0, true);
            String[] str = strings.split(command, " ");

            if (!str[0].equals("")) {
                switch (str[0]) {
                    case "cat":
                        soft.cat(command, path);
                        break;

                    case "pwd":
                        soft.pwd(path);
                        break;

                    case "count":
                        soft.count(command, path);
                        break;

                    case "info":
                        soft.info(command, path);
                        break;

                    case "os":
                        soft.os(command);
                        break;

                    case "cp":
                        soft.copy(command);
                        break;

                    case "rm":
                        soft.remove(command);
                        break;

                    case "mv":
                        soft.move(command);
                        break;

                    case "man":
                        soft.manPage(command);
                        break;

                    case "rn":
                        soft.rename(command);
                        break;

                    case "cd":
                        soft.cd(command, path);
                        break;

                    case "cd..":
                        soft.cdDot(command);
                        break;

                    case "get":
                        soft.get(command);
                        break;

                    case "ls":
                        soft.list(command, path);
                        break;

                    case "clear":
                        soft.clear(command);
                        break;

                    case "tan":
                        soft.turnAnimationON();
                        break;

                    case "taf":
                        soft.turnAnimationOFF();
                        break;

                    case "sat":
                        soft.setAnimationTime(command);
                        break;

                    case "mkdir":
                        soft.makeDirectory(command);
                        break;

                    case "ping":
                        soft.ping(command);
                        break;

                    case "exit":
                        soft.exit(command);
                        break;

                    default:
                        box.print("\tThere is no command \"".concat(command).concat("\""), true);
                        break;
                }
            }
        }
    }

    public static String getPath() {
        return path;
    }

    public static String getUsername() {
        return username;
    }

    public static void setPath(String path) {
        Shell.path = path;
    }
}
