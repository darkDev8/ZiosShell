package com.zios.root;

import com.zios.database.Database;
import com.zios.eth.Internet;
import com.zios.filesys.DirectorySYS;
import com.zios.filesys.files.*;
import com.zios.main.ManPage;
import com.zios.main.Shell;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.print.Doc;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.AccessDeniedException;
import java.nio.file.Paths;

public class Software {

    private ToolBox box;
    private Strings strings;
    private DirectorySYS directory;
    private RootOS rootOS;
    private Database database;

    public Software() {
        box = new ToolBox();
        strings = new Strings();
        rootOS = new RootOS();
        database = new Database();
    }

    private String[] returnCommandParameters(String command) {
        return strings.removeElement(strings.split(command, " "), 0);
    }

    public int getBuildNumber() {
        Document document = new Document("build.txt");
        if (!document.exist()) {
            return -1;
        }

        String tmp = document.read("build.txt");
        if (tmp.equals("")) {
            return -1;
        }

        if (tmp.endsWith("\n")) {
            tmp = tmp.substring(0, tmp.length() - 1);
        }

        return Integer.parseInt(tmp);
    }

    public boolean increaseBuildNumber() {
        Document document = new Document("build.txt");

        int x = getBuildNumber();
        if (x == -1) {
            return false;
        }

        x += 1;
        if (document.write("build.txt", String.valueOf(x), false) == 1) {
            return true;
        }
        return false;
    }

    public void title() {
        box.print("\nZios Shell", true);
        box.print("Software version: 1.0 (beta)", true);
        box.print("Creator: darkDeveloper", true);
        box.print("Build number: ".concat(String.valueOf(getBuildNumber())), true);

        box.printCharacters('-', 50, true, true);
    }

    public void exit(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index == 0) {
            if (box.ask("\tAre you sure you want to exit", true) == 1) {
                System.exit(1);
            }
        } else if (index == 1) {
            if (params[0].equals("-y")) {
                System.exit(1);
            } else {
                box.print("\tInvalid arguments.", true);
            }
        } else {
            box.print("\tInvalid command.", true);
        }
    }

    public void cat(String command, String currentPath) {
        try {
            String[] params = returnCommandParameters(command);
            int index = params.length;
            boolean reverse = false;
            String path = null;

            if (index == 0) {
                box.print("\tInvalid command.", true);
            } else if (index == 1) {
                path = currentPath.concat("/").concat(params[0]);
            } else {
                if (params[0].equals("-r")) {
                    path = currentPath.concat("/").concat(params[1]);
                    reverse = true;
                } else {
                    box.print("\tInvalid arguments.", true);
                    return;
                }
            }

            Document document = new Document(path);
            System.out.println(document.getPath());

            switch (index) {
                case 1:
                    box.print(document.read(document.getPath()), true);
                    break;

                case 2:
                    box.print(document.read(document.getPath(), reverse), true);
                    break;

                default:
                    box.print("\tInvalid command.", true);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        box.pressEnter(false, false);
    }

    public void pwd(String path) {
        box.print(path, true);
    }

    public void count(String command, String path) {
        String[] params = returnCommandParameters(command);
        directory = new DirectorySYS(path);
        int files = directory.countFiles(), directories = directory.countDirectories(), index = params.length;
        int subFiles = directory.countSubFiles(), subDirectories = directory.countSubDirectories();

        String showFiles = "\tFiles: ".concat(String.valueOf(files)),
                showDirectories = "\tDirectories: ".concat(String.valueOf(directories));

        String showSubFiles = "\tFiles: ".concat(String.valueOf(subFiles)),
                showSubDirectories = "\tDirectories: ".concat(String.valueOf(subDirectories));

        if (index == 0) {
            box.print(showFiles.concat("\t"), false);
            box.print(showDirectories.concat("\t"), true);
        } else if (index == 1) {
            if (params[0].equals("-f")) {
                box.print(showFiles, true);
            } else if (params[0].equals("-d")) {
                box.print(showDirectories, true);
            } else if (params[0].equals("-sf") || params[0].equals("fs")) {
                box.print(showSubFiles, true);
            } else if (params[0].equals("-sd") || params[0].equals("ds")) {
                box.print(showSubDirectories, true);
            } else {
                box.print("\tInvalid arguments.", true);
                return;
            }
        } else {
            box.print("\tInvalid command.", true);
        }
    }

    public void info(String command, String path) {
        String[] params = returnCommandParameters(command);
        int index = params.length;
        String pth = null;

        if (index == 0) {
            pth = path;
        } else if (index == 1) {
            pth = params[0];
        } else {
            box.print("\tInvalid command.", true);
            return;
        }

        File f = new File(pth);
        if (f.exists()) {
            directory = new DirectorySYS(pth);

            String[] td = {"Name: ", "Size: ", "Create: ", "Files: ", "Directories: ",
                    "Sub files: ", "Sub directories: "};
            String[] tf = {"Name: ", "Size: ", "Create: ", "Type: "};

            box.print("\n", false);
            box.printCharacters('-', 60, true, true);
            if (index == 0 || f.isDirectory()) {
                String[] info = directory.getDirectoryInfo();

                for (int i = 0; i < info.length; i++) {
                    box.print("\t".concat(td[i]).concat(info[i]), true);
                }
            } else {
                Unkown unkown = new Unkown(pth);
                String type = unkown.findFileType();
                String[] info = unkown.getFileInfo(pth);

                for (int i = 0; i < tf.length; i++) {
                    box.print("\t".concat(tf[i]).concat(info[i]), true);
                }

                if (type.toLowerCase().startsWith("music")) {
                    Audio audio = new Audio(pth);

                    box.print("\tDuration: ".concat(box.convertSecondsMinute(Integer.parseInt(audio.getDuration()))), true);
                    box.print("\tQuality: ".concat(audio.getQuality()), true);
                    box.print("\tCustom tag: ".concat(audio.getHasCustomTag()), true);
                    box.print("\tSample rate: ".concat(audio.getSampleRate()), true);
                    box.print("\tArtist: ".concat(audio.getArtist()), true);
                    box.print("\tTitle: ".concat(audio.getTitle()), true);
                    box.print("\tComment: ".concat(audio.getComment()), true);
                    box.print("\tYear: ".concat(audio.getYear()), true);
                    box.print("\tGenre: ".concat(audio.getGenre()), true);
                } else if (type.toLowerCase().startsWith("image")) {
                    Image image = new Image(pth);

                    box.print("\tWidth: ".concat(String.valueOf(image.getWidth()).concat(" px")), true);
                    box.print("\tHeight: ".concat(String.valueOf(image.getHeight()).concat(" px")), true);
                } else if (type.toLowerCase().startsWith("executable")) {
                    Executable executable = new Executable(pth);
                    box.print("\tOS type file: ".concat(executable.getOsType()), true);
                } else if (type.toLowerCase().startsWith("video")) {
                    Video video = new Video(pth);

                    box.print("\tDuration: ".concat(box.convertMillisecondsTime(Long.parseLong(video.getDuration()))), true);
                    box.print("\tWidth: ".concat(video.getWidth()), true);
                    box.print("\tHeight: ".concat(video.getHeight()), true);
                    box.print("\tBit rate: ".concat(video.getBitRate()), true);
                    box.print("\tAudio sample rate: ".concat(video.getAudioSampleRate()), true);
                    box.print("\tAudio channels: ".concat(video.getAudioChannels()), true);
                    box.print("\tVideo frame rate: ".concat(video.getVideoFrameRate()), true);
                } else if (type.toLowerCase().startsWith("pdf")) {
                    PDF pdf = new PDF(pth);

                    box.print("\tPages: ".concat(pdf.getPages()), true);
                    box.print("\tLines: ".concat(pdf.getLines()), true);
                    box.print("\tCharacters: ".concat(pdf.getCharacters()), true);
                } else if (type.toLowerCase().startsWith("document")) {
                    Document document = new Document(pth);

                    box.print("\tLines: ".concat(String.valueOf(document.getLines())), true);
                    box.print("\tWords: ".concat(String.valueOf(document.getWords())), true);
                    box.print("\tCharacters: ".concat(String.valueOf(document.getCharacters())), true);
                    box.print("\tEmpty lines: ".concat(String.valueOf(document.getEmptyLines())), true);
                }
            }

            box.printCharacters('-', 60, true, true);
        } else {
            box.print("\tInvalid file.", true);
        }
    }

    public void os(String command) {
        String[] params = returnCommandParameters(command);

        if (params.length > 0) {
            box.print("\tInvalid command.", true);
        } else {
            String username = Shell.getUsername();
            Document document = new Document("info.sh");

            if (!document.exist()) {
                box.print("\tThe command file has been removed.", true);
            } else {
                if (!rootOS.executeCommandShell("chmod +x info.sh")) {
                    box.print("\tUnable to take permision of the file.", true);
                    return;
                }

                if (!rootOS.executeCommandShell("./info.sh ".concat(username))) {
                    box.print("\tUnable to get the system information.", true);
                    return;
                }

                box.print("\tGetting system information...\n", true);
                box.sleep(1000);

                String[] info = strings.split(document.read(username), "\n");
                String[] tf = {"OS type: ", "Kernel: ", "Cpu ", "Username: ", "Uptime: ", "Total ram: ",
                        "Used ram: ", "Root partition size: "};

                for (int i = 0; i < info.length; i++) {
                    box.print("\t".concat(tf[i]).concat(info[i]), true);
                }

                box.print("\n", false);
            }
        }
    }

    public void copy(String command) {
        String[] params = returnCommandParameters(command);
        String srcPath = null, desPath = null;
        boolean file = false, directory = false;
        int index = params.length;

        if (index != 3) {
            box.print("\tInvalid command.", true);
        } else {
            srcPath = params[1];
            desPath = params[2];

            if (desPath.equals("/")) {
                desPath = Shell.getPath();
            }

            DirectorySYS directorySYS = new DirectorySYS(desPath);
            String desFile = desPath.concat("/").concat(srcPath), newName = null;
            long i = 0;

            if (!directorySYS.exist()) {
                box.print("\tInvalid destination directory.", true);
                return;
            }

            if (params[0].equals("-f")) {
                Unkown unkown = new Unkown(srcPath);
                if (!unkown.exist()) {
                    box.print("\tInvalid file.", true);
                } else {
                    if (unkown.exist(desFile)) {
                        if (box.ask("\tThe file is already exists.Do you want to continue", true) == 0) {
                            return;
                        } else {

                            newName = unkown.getBaseName().concat(String.valueOf(i)).concat(".").concat(unkown.getExtension());
                            while (i < directorySYS.getFiles()) {
                                newName = unkown.getBaseName().concat(String.valueOf(i)).concat(".").concat(unkown.getExtension());
                                if (!new Unkown(newName).exist()) {
                                    break;
                                }

                                i++;
                            }

                            desFile = desPath.concat("/").concat(newName);
                        }
                    }

                    if (unkown.copy(srcPath, desFile)) {
                        box.print("\tThe file copied successfully.", true);
                    } else {
                        box.print("\tFailed to copy the file.", true);
                    }
                }
            } else if (params[0].equals("-d")) {
                if (!new DirectorySYS(srcPath).exist()) {
                    box.print("\tInvalid directory.", true);
                } else {
                    if (directorySYS.exist(desFile)) {
                        if (box.ask("\tThe directory is already exists.Do you want to continue", true) == 0) {
                            return;
                        } else {
                            newName = params[1];

                            while (i < directorySYS.getDirectories()) {
                                newName = newName.concat(String.valueOf(i));

                                if (!new DirectorySYS(newName).exist()) {
                                    break;
                                }

                                i++;
                            }

                            desFile = desPath.concat("/").concat(newName);
                        }
                    }
                    if (directorySYS.copy(srcPath, desFile)) {
                        box.print("\tThe directory copied successfully.", true);
                    } else {
                        box.print("\tFailed to copy the directory.", true);
                    }
                }
            } else {
                box.print("\tInvalid arguments.", true);
            }
        }
    }

    public void remove(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 0) {
            box.print("\tInvalid command.", true);
        } else {
            if (!new Unkown(params[0]).exist() && !new DirectorySYS(params[0]).exist()) {
                box.print("\tInvalid file or directory.", true);
            } else {
                Unkown unkown = new Unkown(params[0]);
                if (unkown.exist()) {
                    if (box.ask("\tAre you sure you want to delete the file", true) == 0) {
                        return;
                    }

                    if (unkown.delete()) {
                        box.print("\tThe file deleted successfully.", true);
                    } else {
                        box.print("\tFailed to delete the file.", true);
                    }

                } else {
                    DirectorySYS sys = new DirectorySYS(params[0]);

                    if (box.ask("\tAre you sure you want to delete the directory", true) == 0) {
                        return;
                    }

                    if (sys.delete()) {
                        box.print("\tThe directory deleted successfully.", true);
                    } else {
                        box.print("\tFailed to delete the directory.", true);
                    }
                }
            }
        }
    }

    public void move(String command) {
        copy(command);
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 3) {
            box.print("\tInvalid command.", true);
        } else {
            StringBuilder build = new StringBuilder();

            for (int i = 1; i < params.length - 1; i++) {
                build.append(params[i]);
            }

            String removeFile = build.toString();
            if (!new Unkown(removeFile).exist() && !new DirectorySYS(removeFile).exist()) {
                box.print("\tInvalid file or directory.", true);
            } else {
                Unkown unkown = new Unkown(removeFile);
                if (unkown.exist()) {
                    if (unkown.delete()) {
                        box.print("\tThe file deleted successfully.", true);
                    } else {
                        box.print("\tFailed to delete the file.", true);
                    }
                } else {
                    DirectorySYS sys = new DirectorySYS(removeFile);
                    if (sys.delete()) {
                        box.print("\tThe directory deleted successfully.", true);
                    } else {
                        box.print("\tFailed to delete the directory.", true);
                    }
                }
            }
        }
    }

    public void rename(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 3) {
            box.print("\tInvalid command.", true);
            return;
        }

        String srcPath = params[1], newName = params[2];
        if (params[0].equals("-f")) {
            Unkown unkown = new Unkown(srcPath);
            if (!unkown.rename(srcPath, newName)) {
                box.print("\tUnable to change the file name.", true);
            }
        } else if (params[0].equals("-d")) {
            DirectorySYS sys = new DirectorySYS(srcPath);

            if (!sys.rename(srcPath, newName)) {
                box.print("\tUnable to change the directory name.", true);
            }
        } else {
            box.print("\tInvalid arguments.", true);
        }
    }

    public void cd(String command, String currentPath) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 1) {
            box.print("\tInvalid command.", true);
        } else {
            try {
                String path = null;

                if (currentPath.endsWith("/")) {
                    path = currentPath.concat(params[0]);
                } else {
                    path = currentPath.concat("/").concat(params[0]);
                }

                DirectorySYS sys = new DirectorySYS(path);

                if (sys.exist()) {
                    Shell.setPath(path);
                } else {
                    box.print("\tUnable to find the directory.", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void cdDot(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index > 0) {
            box.print("\tInvalid command.", true);
        } else {
            String[] path = strings.split(Shell.getPath(), "/");
            StringBuilder build = new StringBuilder();

            if (path.length > 0) {
                for (int i = 0; i < path.length - 1; i++) {
                    build.append(path[i]).append("/");
                }

                Shell.setPath(build.toString());
            }
        }
    }

    public void get(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 1) {
            box.print("\tInvalid command.", true);
        } else {
            Internet internet = new Internet(params[0]);
            if (!internet.validate()) {
                box.print("\tThe link is invalid or no connection.", true);
                return;
            }

            if (new Unkown("").exist(internet.getName())) {
                if (box.ask("\tThe file already exist.Do you want to download it again", true) == 0) {
                    return;
                }
            }

            if (params[0].length() > 40) {
                params[0] = params[0].substring(0, 40);
            }

            box.print("\t", false);
            box.printCharacters('-', 50, true, true);

            box.print("\tLink: ".concat(params[0]), true);
            box.print("\tFile name: ".concat(internet.getName()), true);
            box.print("\tFile size: ".concat(internet.getReadableSize()), true);

            box.print("\t", false);
            box.printCharacters('-', 50, true, true);

            box.print("\tDownloading...", true);
            if (internet.download()) {
                box.print("\tFile downloaded successfully.", true);
            } else {
                box.print("\tFailed to download the file.", true);
            }
        }

    }

    public void list(String command, String path) {
        String[] params = returnCommandParameters(command);
        int index = params.length;
        boolean hidden = false;

        File[] list = new File(path).listFiles();

        if (index == 0) {
            for (int i = 0; i < list.length; i++) {
                if (list[i].isFile()) {
                    box.print("\t-F\t", false);
                } else {
                    box.print("\t-D\t", false);
                }

                box.print("\t".concat(list[i].getName()), true);
            }
        } else if (index == 1) {
            if (params[0].equals("-")) {
                box.print("\tInvalid command.", true);
                return;
            }

            if (params[0].contains("h")) {
                hidden = true;
            }

            if ((params[0].contains("f") && params[0].contains("d")) ||
                    !params[0].contains("f") && !params[0].contains("d")) {
                box.print("\tInvalid file or directory command.", true);
                return;
            }

            for (int i = 0; i < list.length; i++) {
                if (params[0].contains("f")) {
                    if (list[i].isFile()) {
                        if (hidden) {
                            box.print("\t-F\t".concat(list[i].getName()), false);

                            if (params[0].contains("s")) {
                                box.print("\t".concat(new Unkown(list[i].getAbsolutePath()).getReadableSize()), false);
                            }

                            if (params[0].contains("t")) {
                                box.print("\t".concat(new Unkown(list[i].getAbsolutePath()).findFileType()), false);
                            }
                            box.print("\n", false);

                        } else {
                            if (!list[i].getName().startsWith(".")) {
                                box.print("\t-F\t".concat(list[i].getName()), false);

                                if (params[0].contains("s")) {
                                    box.print("\t".concat(new Unkown(list[i].getAbsolutePath()).getReadableSize()), false);
                                }

                                if (params[0].contains("t")) {
                                    box.print("\t".concat(new Unkown(list[i].getAbsolutePath()).findFileType()), false);
                                }
                                box.print("\n", false);
                            }
                        }


                    }
                } else if (params[0].contains("d")) {
                    if (list[i].isDirectory()) {
                        if (hidden) {
                            box.print("\t-D\t".concat(list[i].getName()), false);

                            if (params[0].contains("s")) {
                                box.print("\t".concat(new DirectorySYS(list[i].getAbsolutePath()).getReadableSize()), false);
                            }

                            if (params[0].contains("t")) {
                                box.print("\t Directory", false);
                            }

                            box.print("\n", false);
                        } else {
                            if (!list[i].getName().startsWith(".")) {
                                box.print("\t-D\t".concat(list[i].getName()), false);

                                if (params[0].contains("s")) {
                                    box.print("\t".concat(new DirectorySYS(list[i].getAbsolutePath()).getReadableSize()), false);
                                }

                                if (params[0].contains("t")) {
                                    box.print("\t Directory", false);
                                }

                                box.print("\n", false);
                            }
                        }
                    }
                }
            }
        } else {
            box.print("\tInvalid arguments.", true);
        }
    }

    public void clear(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index == 0) {
            for (int i = 0; i < 100; i++) {
                box.print("\n", false);
            }
        } else {
            int num = strings.integerParse(params[0]);

            if (num < 0) {
                box.print("\tInvalid number.", true);
            } else {
                for (int i = 0; i < num; i++) {
                    box.print("\n", false);
                }
            }
        }
    }

    public void turnAnimationON() {
        if (database.updateAnimationTime(0)) {
            ToolBox.setTime(1);
            box.print("\tThe animation is on.", true);
        } else {
            box.print("\tUnable to turn animation on.", true);
        }
    }

    public void turnAnimationOFF() {
        if (database.updateAnimationTime(0)) {
            ToolBox.setTime(0);
            box.print("\tThe animation is off.", true);
        } else {
            box.print("\tUnable to turn animation off.", true);
        }
    }

    public void setAnimationTime(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index == 0) {
            box.print("\tInvalid command.", true);
        } else if (index == 1) {
            int time = strings.integerParse(params[0]);

            if (time < 0) {
                box.print("\tInvalid number.", true);
            } else {
                if (database.updateAnimationTime(time)) {
                    ToolBox.setTime(database.getAnimationTime());
                    box.print("\tTime updated successfully.", true);
                } else {
                    box.print("\tUnable to update the time.", true);
                }
            }
        } else {
            box.print("\tInvalid arguments.", true);
        }
    }

    public void makeDirectory(String command) {
        String [] params = returnCommandParameters(command);
        int index = params.length;

        if (index == 0) {
            box.print("\nInvalid command.", true);
        } else {
            DirectorySYS sys = new DirectorySYS(params[0]);

            if (sys.exist()) {
                if (box.ask("The directory already exist.Do you want to create new one", true) == 1) {
                    if (!sys.delete()) {
                        box.print("\tUnable to delete the directory.", true);
                        return;
                    }

                    if (!sys.create(params[0])) {
                        box.print("\tUnable to create new directory.", true);
                    }
                }
            } else {
                if (!sys.create(params[0])) {
                    box.print("\tUnable to create new directory.", true);
                }
            }
        }
    }

    public void ping(String command) {
        String [] params = returnCommandParameters(command);
        int index = params.length, timeout = 10;
        Internet internet = new Internet();

        if (index == 0) {
            box.print("\tInvalid command.", true);
        } else {
            internet.setUrl(params[0]);

            if (internet.validate()) {
                if (index == 1) {
                    for (int i = 0; i < timeout; i++) {
                        internet.ping(params[0]);
                    }
                } else if (index == 2) {
                    if (strings.integerParse(params[1]) != -1) {
                        for (int i = 0; i < Integer.parseInt(params[1]); i++) {
                            internet.ping(params[0]);
                        }
                    } else {
                        box.print("\tInvalid timeout.", true);
                    }
                } else {
                    box.print("\tInvalid arguments,", true);
                }
            } else {
                box.print("\tIncorrect url address.", true);
            }
        }
    }

    public void manPage(String command) {
        String[] params = returnCommandParameters(command);
        int index = params.length;

        if (index != 1) {
            box.print("\tInvalid command.", true);
        } else {
            ManPage manPage = new ManPage();

            switch (params[0]) {
                case "exit":
                    manPage.exit();
                    break;

                case "cat":
                    manPage.cat();
                    break;

                case "pwd":
                    manPage.pwd();
                    break;

                case "count":
                    manPage.count();
                    break;

                case "info":
                    manPage.info();
                    break;

                case "os":
                    manPage.os();
                    break;

                case "cp":
                    manPage.cp();
                    break;

                case "rm":
                    manPage.rm();
                    break;

                case "mv":
                    manPage.mv();
                    break;

                case "rn":
                    manPage.rn();
                    break;

                case "cd":
                    manPage.cd();
                    break;

                case "cd..":
                    manPage.cdDot();
                    break;

                case "get":
                    manPage.get();
                    break;

                case "ls":
                    manPage.ls();
                    break;

                case "clear":
                    manPage.clear();
                    break;

                case "tan":
                    manPage.tan();
                    break;

                case "taf":
                    manPage.taf();
                    break;

                case "sat":
                    manPage.sat();
                    break;

                case "ping":
                    manPage.ping();
                    break;

                case "mkdir":
                    manPage.mkdir();
                    break;

                case "man":
                    manPage.man();
                    break;

                default:
                    box.print("\tThere is no man page for this command.", true);
                    break;
            }
        }
    }
}
