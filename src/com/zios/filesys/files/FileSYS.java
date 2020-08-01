package com.zios.filesys.files;

import com.zios.filesys.DirectorySYS;
import com.zios.root.Strings;
import com.zios.root.ToolBox;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

public abstract class FileSYS {
    private String name;
    private String path;
    private String create;
    private String baseName;
    private String extension;
    private String readableSize;

    private int size;

    public FileSYS(String path) {
        this.path = new File(path).getAbsolutePath();                   //get file path

        if (exist(this.path)) {
            Path p = Paths.get(path);
            this.name = p.getFileName().toString();                 //get file name
            this.size = (int) new File(path).length();                  //get file size
            this.extension = FilenameUtils.getExtension(path);                  //get file extension
            this.baseName = FilenameUtils.getBaseName(path);                    //get file base name

            this.create = new ToolBox().getCreationTime(p);                   //get file creation time
            this.readableSize = new Strings().getReadableSize(size);                    //get file readable size
        }
    }

    /**
     * Get only the name of the file.
     *
     * @return the file name.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the full path of the file.
     *
     * @return the file path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Get only the creation time of the file.
     *
     * @return the file creation time.
     */
    public String getCreate() {
        return create;
    }

    /**
     * Get file extension.
     *
     * @return the file extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Get file size.
     *
     * @return the file size.
     */
    public int getSize() {
        return size;
    }

    /**
     * Get readable size of the file.
     *
     * @return the readable file size.
     */
    public String getReadableSize() {
        return readableSize;
    }

    /**
     * Get file base name.
     *
     * @return the file base name.
     */
    public String getBaseName() {
        return baseName;
    }

    /**
     * Check the file path.
     *
     * @return the existence of the file.
     */
    public boolean exist() {
        File f = new File(path);
        return f.exists() && f.isFile();
    }

    /**
     * Check the file path.
     *
     * @param path path of the file
     * @return the existence of the file.
     */
    public boolean exist(String path) {
        File f = new File(path);
        return f.exists() && f.isFile();
    }

    public String[] getFileInfo(String path) {
        if (exist(path)) {
            Unkown unkown = new Unkown(path);

            String name = unkown.getName(), size = unkown.getReadableSize(),
                    create = unkown.getCreate(), type = unkown.findFileType();

            return new String[] {name, size, create, type};
        }

        return null;
    }

    public boolean copy(String src, String des) {
        try {
            FileUtils.copyFile(new File(src), new File(des));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String path) {
        if (exist(path)) {
            return new File(path).delete();
        }
        return false;
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


}
