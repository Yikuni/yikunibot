package com.yikuni.robot.client.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageItem implements Serializable {
    private File file;
    private String fileName;

    public ImageItem(File file){
        this.file = file;
        this.fileName = file.getName();
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
