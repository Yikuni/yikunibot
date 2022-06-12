package com.yikuni.robot.client.image;

import com.yikuni.robot.client.config.DataConfig;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

@Deprecated
public class DefaultImages {
    private static final File[] FILES = getFiles();

    private static File[] getFiles(){
        DataConfig dataConfig = DataConfig.getConfig();
        String path = dataConfig.getConfUrl() + "/images/default";
        File file = new File(path);
        System.out.println(file.exists());
        return file.listFiles();
    }



    public static File randomImage(){
        Random random = new Random();
        int i = random.nextInt(FILES.length);
        System.out.println(FILES[i].exists());
        return FILES[i];
    }
}
