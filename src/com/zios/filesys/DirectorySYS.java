package com.zios.filesys;

import com.zios.root.Strings;
import com.zios.root.ToolBox;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import javax.tools.Tool;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DirectorySYS {
    private String name;
    private String path;
    private String create;
    private String readableSize;

    private int size;
    private int files;
    private int directories;

    private int subFiles;
    private int subDirectories;

    public DirectorySYS(String path) {
        this.path = new File(path).getAbsolutePath();

        if (exist(this.path)) {
            this.name = getDirectoryName(this.path);
            Path p = Paths.get(path);

            this.size = (int) FileUtils.sizeOfDirectory(new File(this.path));
            this.files = countFiles();
            this.directories = countDirectories();

            this.subFiles = countSubFiles();
            this.subDirectories = countSubDirectories();
            this.readableSize = new Strings().getReadableSize(size);
            this.create = new ToolBox().getCreationTime(p);
        }
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public String getCreate() {
        return create;
    }

    public String getReadableSize() {
        return readableSize;
    }

    public int getSize() {
        return size;
    }

    public int getFiles() {
        return files;
    }

    public int getDirectories() {
        return directories;
    }

    public int getSubFiles() {
        return subFiles;
    }

    public int getSubDirectories() {
        return subDirectories;
    }

    /**
     * Check the directory path.
     *
     * @return the existence of the directory.
     */
    public boolean exist() {
        File f = new File(path);
        return f.exists() && f.isDirectory();
    }


    /**
     * Check the directory path.
     *
     * @param path path of the directory
     * @return the existence of the directory.
     */
    public boolean exist(String path) {
        File f = new File(path);
        return f.exists() && f.isDirectory();
    }

    public int countFiles() {
        return countFiles(path);
    }

    public int countFiles(String path) {
        List<String> fileNames = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path));
            for (Path pth : directoryStream) {
                if (new File(pth.toString()).isFile()) {
                    fileNames.add(pth.toString());
                }

            }

            return fileNames.size();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int countDirectories() {
        return countDirectories(path);
    }

    public int countDirectories(String path) {
        List<String> direcoryNames = new ArrayList<>();
        try {
            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path));
            for (Path pth : directoryStream) {
                if (exist(pth.toString())) {
                    direcoryNames.add(pth.toString());
                }

            }

            return direcoryNames.size();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public int countSubDirectories() {
        return countSubDirectories(path);
    }

    public int countSubDirectories(String path) {
        try {
            Path dir = Paths.get(path);
            return (int) Files.walk(dir).parallel().filter(p -> p.toFile().isDirectory()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int countSubFiles() {
        return countSubFiles(path);
    }

    public int countSubFiles(String path) {
        try {
            Path dir = Paths.get(path);
            return (int) Files.walk(dir).parallel().filter(p -> p.toFile().isFile()).count();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public String[] getDirectoryInfo() {
        return getDirectoryInfo(path);
    }

    public String[] getDirectoryInfo(String path) {
        if (exist(path)) {
            DirectorySYS sys = new DirectorySYS(path);

            String name = sys.getName(), size = sys.getReadableSize(),
                    create = sys.getCreate(), files = String.valueOf(sys.getFiles()),
                    directories = String.valueOf(sys.getDirectories()), subFiles =
                    String.valueOf(sys.getSubFiles()), subDirectories = String.valueOf(
                    sys.getSubDirectories()
            );

            return new String[] {name, size, create, files, directories, subFiles, subDirectories};
        }

        return null;
    }

    public boolean copy(String src, String des) {
        try {
            FileUtils.copyDirectory(new File(src), new File(des));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String path) {
        try {
            FileUtils.deleteDirectory(new File(path));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        return delete(path);
    }

    public boolean move(String src, String des) {
        return copy(src, des) && delete(src);
    }

    public boolean rename(String path, String newName) {
        if (exist(path)) {
            String [] tmp = new Strings().split(getPath(), "/");
            StringBuilder build = new StringBuilder();

            for (int i = 0; i < tmp.length - 1; i++) {
                build.append(tmp[i]).append("/");
            }

            return new File(getPath()).renameTo(new File(build.toString().concat("/").concat(newName)));
        }

        return false;
    }

    public String getDirectoryName(String path) {
        if (exist(path)) {
            String [] drs = new Strings().split(path, "/");
            return  drs[drs.length - 1];
        }

        return null;
    }

    public boolean create(String path) {
        return new File(path).mkdir();
    }
}
