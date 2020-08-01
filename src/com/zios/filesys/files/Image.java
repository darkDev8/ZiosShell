package com.zios.filesys.files;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends FileSYS{

    private int width;
    private int height;
    public Image(String path) {
        super(path);

        if (exist()) {
            try {
                BufferedImage bimg = ImageIO.read(new File(path));
                this.width = bimg.getWidth();
                this.height = bimg.getHeight();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
