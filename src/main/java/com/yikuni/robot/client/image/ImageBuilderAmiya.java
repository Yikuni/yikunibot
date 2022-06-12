package com.yikuni.robot.client.image;

import com.yikuni.robot.client.RobotClientAkarinApplication;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.UUID;

@Deprecated
public class ImageBuilderAmiya extends ImageBuilder {

    public ImageBuilderAmiya(){
        this.sliceWidth = 250;
        this.sliceHeight = 250;
        this.sliceStartX = 0;
        this.sliceStartY = 0;
    }


    @Override
    public ImageBuilder setPicture(BufferedImage image) {
        // 对文件进行压缩
        int currentHeight = image.getHeight();
        int currentWidth = image.getWidth();
        if(currentHeight * sliceWidth > currentWidth * sliceHeight){
            // 高的放缩比更大, 放缩高
            this.sliceWidth = currentWidth * sliceHeight / currentHeight;
            this.image = new BufferedImage(sliceWidth, sliceHeight, BufferedImage.TYPE_INT_BGR);
            this.image.getGraphics().drawImage(image, 0, 0, currentWidth * sliceHeight / currentHeight, sliceHeight, null);
        }else{
            // 高的放缩比更小, 放缩宽
            this.sliceHeight = currentHeight * sliceWidth / currentWidth;
            this.image = new BufferedImage(sliceWidth, sliceHeight, BufferedImage.TYPE_INT_BGR);
            this.image.getGraphics().drawImage(image, 0, 0, sliceWidth, currentHeight * sliceWidth / currentWidth, null);
        }
        return this;
    }

    @Override
    public void build() throws IOException {
        this.originPicture.getGraphics().drawImage(this.image, sliceStartX, sliceStartY, sliceWidth, sliceHeight, null);
        UUID uuid = UUID.randomUUID();
        resultFilePath = this.getClass().getResource("/tempImages").getPath();
        resultFilePath = resultFilePath + "/" + uuid + ".jpg";
        File file = new File(resultFilePath);
        file.createNewFile();
        System.out.println(file.getAbsolutePath());
        ImageIO.write(originPicture, "JPEG", file);
    }

    @Override
    public void deleteFile() {
        File file = new File(resultFilePath);
        if(file.exists()){
            file.delete();
        }
    }

    @Override
    public File getImageFile() {
        return new File(resultFilePath);
    }
}
