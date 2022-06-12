package com.yikuni.robot.client.image;

import love.forte.simbot.event.ContactMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.resources.ByteArrayResource;
import org.springframework.web.client.RestTemplate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public abstract class ImageTemplate {
    protected static BufferedImage templateImage;
    protected BufferedImage image;
    protected BufferedImage userImage;
    protected RestTemplate template = new RestTemplate();

    protected int sliceWidth;
    protected int sliceHeight;
    protected int sliceStartX;
    protected int sliceStartY;

    public void sendImage(ContactMessageEvent event, String imageUrl) throws URISyntaxException, IOException {
        setImage();
        setUserImage(imageUrl);
        draw();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", out);
        byte[] resource = out.toByteArray();
        ByteArrayResource up = new ByteArrayResource("image", resource);
        event.getSource().sendBlocking(event.getBot().uploadImageBlocking(up));


    }

    public void sendImage(GroupMessageEvent event, String imageUrl) throws URISyntaxException, IOException {
        setImage();
        setUserImage(imageUrl);
        draw();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", out);
        byte[] resource = out.toByteArray();
        ByteArrayResource up = new ByteArrayResource("image", resource);
        out.close();
        event.getSource().sendBlocking(event.getBot().uploadImageBlocking(up));
    }

    protected void setImage(){
        this.image = templateImage.getSubimage(0, 0, templateImage.getWidth(), templateImage.getHeight());
    }

    protected void setUserImage(String imageUrl) throws URISyntaxException, IOException {
        byte[] imageBytes = template.getForObject(new URI(imageUrl), byte[].class);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes, 0, imageBytes.length);
        BufferedImage userHead = ImageIO.read(inputStream);
        inputStream.close();

        // 对文件进行压缩
        int currentHeight = userHead.getHeight();
        int currentWidth = userHead.getWidth();
        if(currentHeight * sliceWidth > currentWidth * sliceHeight){
            // 高的放缩比更大, 放缩高
            this.sliceWidth = currentWidth * sliceHeight / currentHeight;
            this.userImage = new BufferedImage(sliceWidth, sliceHeight, BufferedImage.TYPE_INT_BGR);
            this.userImage.getGraphics().drawImage(userHead, 0, 0, currentWidth * sliceHeight / currentHeight, sliceHeight, null);
        }else{
            // 高的放缩比更小, 放缩宽
            this.sliceHeight = currentHeight * sliceWidth / currentWidth;
            this.userImage = new BufferedImage(sliceWidth, sliceHeight, BufferedImage.TYPE_INT_BGR);
            this.userImage.getGraphics().drawImage(userHead, 0, 0, sliceWidth, currentHeight * sliceWidth / currentWidth, null);
        }
    }

    protected void draw(){
        this.image.getGraphics().drawImage(this.userImage, sliceStartX, sliceStartY, sliceWidth, sliceHeight, null);
    }


}
