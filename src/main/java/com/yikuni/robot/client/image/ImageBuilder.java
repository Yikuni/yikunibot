package com.yikuni.robot.client.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

@Deprecated
public abstract class ImageBuilder {
    protected BufferedImage image;    // 要放入的图片
    protected static BufferedImage originPicture;    // 容器图片
    protected static int originPictureHeight;    // 图片高度
    protected static int originPictureWidth;     // 图片宽度
    protected int sliceHeight;            // 切片限制高度
    protected int sliceWidth;             // 切片限制宽度
    protected int sliceStartX;            // 切片开始x
    protected int sliceStartY;            // 切片开始y
    protected String resultFilePath;      // 结果路径

    /**
     * 设置切片图片
     * @param image 切片图片
     * @return      返回自己
     */
    public abstract ImageBuilder setPicture(BufferedImage image);

    /**
     * 创建图片
     */
    public abstract void build() throws IOException;

    /**
     * 删除创建的图片
     */
    public abstract void deleteFile();

    /**
     * 获取创建的图片
     * @return  创建的图片
     */
    public abstract File getImageFile();

    /**
     * 设置originPicture, 并设置宽和高
     * @param path
     */
    public static void setOriginPicture(String path) throws IOException {
        InputStream resource = ImageBuilder.class.getResourceAsStream(path);
        BufferedImage bufferedImage = ImageIO.read(resource);
        originPicture = bufferedImage;
        originPictureHeight = bufferedImage.getHeight();
        originPictureWidth = bufferedImage.getWidth();
        resource.close();
    }

}
