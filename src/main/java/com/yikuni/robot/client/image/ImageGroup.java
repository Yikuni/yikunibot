package com.yikuni.robot.client.image;

import com.yikuni.robot.common.utils.ListUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ImageGroup implements Serializable{
    private File groupDir;
    private List<ImageItem> imageItemList = new ArrayList<>();
    private String name;
    private static final Logger log = LoggerFactory.getLogger(ImageGroup.class);

    public ImageGroup(File groupDir) {
        this.groupDir = groupDir;
        File[] files = groupDir.listFiles();
        for(File file: files){
            if(!file.isDirectory()){
                if(file.getName().equals("name.txt")){
                    // 如果是设置关键词的文档
                    BufferedReader reader = null;
                    FileReader fileReader = null;
                    try {
                        fileReader = new FileReader(file);
                        reader = new BufferedReader(fileReader);
                        name = reader.readLine();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if(reader != null){
                            try {
                                reader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (fileReader != null){
                            try {
                                fileReader.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }else{
                    // 如果是图片
                    imageItemList.add(new ImageItem(file));
                }
            }else{
                log.warn("Find invalid dir in ImageGroup, you shouldn't include dir in your group, in case that it will be ignored");
            }
        }
    }

    /**
     * 文件是否合法
     * @return  true 合法
     */
    public boolean isValid(){
        return name != null;
    }

    public boolean refresh(){
        ArrayList<ImageItem> imageItems = new ArrayList<>();
        File[] files = groupDir.listFiles();
        if(files == null) return false;
        for(File file: files){
            String fileName = file.getName();
            if(!fileName.equals("name.txt")){
                imageItems.add(new ImageItem(file));
            }
        }
        this.imageItemList = imageItems;
        return true;
    }

    public ImageItem getRandomImage(){
        return (ImageItem) ListUtil.getRandom(imageItemList);
    }

    public File getGroupDir() {
        return groupDir;
    }

    public void setGroupDir(File groupDir) {
        this.groupDir = groupDir;
    }

    public List<ImageItem> getImageItemList() {
        return imageItemList;
    }

    public void setImageItemList(List<ImageItem> imageItemList) {
        this.imageItemList = imageItemList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
