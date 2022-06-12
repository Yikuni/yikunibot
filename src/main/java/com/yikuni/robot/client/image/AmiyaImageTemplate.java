package com.yikuni.robot.client.image;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class AmiyaImageTemplate extends ImageTemplate{

    static {
        InputStream in = AmiyaImageTemplate.class.getResourceAsStream("/images/template/AmiyaImageTemplate.jpg");
        try {
            AmiyaImageTemplate.templateImage = ImageIO.read(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public AmiyaImageTemplate(){
        this.sliceWidth = 250;
        this.sliceHeight = 250;
        this.sliceStartX = 0;
        this.sliceStartY = 0;
    }

}
