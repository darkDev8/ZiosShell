package com.zios.filesys.files;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class Unkown extends FileSYS{

    private String [] music = {"mp3", "wav", "ogg", "aac", "mpa"};
    private String [] document = {"docx", "txt"};
    private String [] video = {"mp4", "mkv", "avi", "3gp"};
    private String [] executable = {"exe", "apk", "sh", "bat", "app"};
    private String [] image = {"png", "bmp", "jpg", "jpeg", "svg"};
    private String [] pdf = {"pdf"};

    public Unkown(String path) {
        super(path);
    }

    public String findFileType() {
        File f = new File(getPath());

        if (f.exists() && f.isFile()) {
            String [][] types = {music, document, video, executable, image, pdf};
            String ext = null;
            int index = -1;

           out: for (int i = 0; i < types.length; i++) {
                for (int j = 0; j < types[i].length; j++) {
                    if (types[i][j].equals(FilenameUtils.getExtension(f.getAbsolutePath()).toLowerCase())) {
                        index = i;
                        ext = types[i][j];
                        break out;
                    }
                }
            }

            switch (index) {
                case 0:
                    return "Music file (".concat(ext).concat(")");

                case 1:
                    return "Document file (".concat(ext).concat(")");

                case 2:
                    return "Video file (".concat(ext).concat(")");

                case 3:
                    return "Executable file (".concat(ext).concat(")");

                case 4:
                    return "Image file (".concat(ext).concat(")");

                case 5:
                    return "Pdf file (".concat(ext).concat(")");

                default:
                    return "Unkown file type";
            }
        }
        return null;
    }

    public String[] getMusic() {
        return music;
    }

    public String[] getDocument() {
        return document;
    }

    public String[] getVideo() {
        return video;
    }

    public String[] getExecutable() {
        return executable;
    }

    public String[] getImage() {
        return image;
    }

    public String[] getPdf() { return pdf;}
}
