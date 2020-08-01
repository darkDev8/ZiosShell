package com.zios.filesys.files;

import org.apache.commons.io.FilenameUtils;

public class Executable extends FileSYS {
    private String osType;

    private String[] linux = {"sh", "run"};
    private String[] windows = {"exe", "bat"};
    private String[] mac = {"app"};
    private String[] android = {"apk"};

    public Executable(String path) {
        super(path);

        if (exist()) {
            String[][] type = {linux, windows, mac, android};
            String extension = FilenameUtils.getExtension(path).toLowerCase();
            int index = 0;

            out:
            for (int i = 0; i < type.length; i++) {
                for (int j = 0; j < type[i].length; j++) {
                    if (type[i][j].equals(extension)) {
                        index = i;
                        break out;
                    }
                }
            }

            switch (index) {
                case 1:
                    this.osType = "Linux file.";
                    break;

                case 2:
                    this.osType = "Windows file.";
                    break;

                case 3:
                    this.osType = "Mac file.";
                    break;

                case 4:
                    this.osType = "Android file.";
                    break;

                default:
                    this.osType = "Unkown";
                    break;
            }
        }
    }


    public String getOsType() {
        return osType;
    }
}
