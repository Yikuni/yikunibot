package com.yikuni.robot.client.image;

import java.io.IOException;

@Deprecated
public class ImageBuilderFactory {

    public static final Class amiya = ImageBuilderAmiya.class;

    public ImageBuilder getBuilder(Class builderClass){
        ImageBuilder builder = null;
        try {
            builder = (ImageBuilder) builderClass.newInstance();
            builder.setOriginPicture("/imageBuilder/" + builderClass.getSimpleName() + ".jpg");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException | IOException e) {
            e.printStackTrace();
        }


        return builder;
    }
}
