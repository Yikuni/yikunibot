package com.yikuni.robot.client.image;

import com.yikuni.robot.client.config.DataConfig;
import com.yikuni.robot.client.util.FileUtil;
import love.forte.simbot.event.ContactMessageEvent;
import love.forte.simbot.event.FriendMessageEvent;
import love.forte.simbot.event.GroupMessageEvent;
import love.forte.simbot.resources.ByteArrayResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;

public class ImageManager implements Serializable {
    private HashMap<String, ImageGroup> groups = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(ImageManager.class);
    private static final ImageManager manager = new ImageManager();


    private ImageManager(){
        try{
            File managerFolder = new File(DataConfig.getConfig().getConfUrl() + "/images");
            System.out.println(DataConfig.getConfig().getConfUrl() + "/images");
            if (!(managerFolder.exists() && managerFolder.isDirectory())){
                throw new FileNotFoundException("Failed to find ImageFile with path: " + DataConfig.getConfig().getConfUrl() + "/images");
            }
            // 如果能够正常加载资源
            File[] groupFiles = managerFolder.listFiles();
            log.info("Loading ImageGroup");
            for(File groupFile: groupFiles){
                if(groupFile.isDirectory()){
                    // 如果是group
                    ImageGroup imageGroup = new ImageGroup(groupFile);
                    if(imageGroup.isValid()){
                        // 如果imageGroup合法
                        groups.put(imageGroup.getName(), imageGroup);
                        log.info("Loaded ImageGroup: " + imageGroup.getName());
                    }else{
                        // 如果imageGroup不合法
                        log.warn("Failed to Load imageGroup with path: " + imageGroup.getGroupDir().getAbsolutePath());
                    }
                }
            }
            log.info("All ImageGroup loaded, total num is: " + groups.size());
        } catch (FileNotFoundException e) {
            log.error("Failed to find Image folder, please check if you have dir named 'images' in dir /conf");
            e.printStackTrace();
        }
    }


    public static ImageManager getInstance(){
        return manager;
    }

    /**
     * 刷新分组
     * @param groupName 分组名
     */
    public boolean refresh(String groupName){
        if(groups.containsKey(groupName)){
            return groups.get(groupName).refresh();
        }else{
            return false;
        }
    }

    /**
     * 发送组中的随机图片
     * @param event 事件
     * @param groupName 组名
     */
    public void sendRandom(FriendMessageEvent event, String groupName){
        if(groups.containsKey(groupName)){
            ImageGroup group = groups.get(groupName);
            File file = group.getRandomImage().getFile();
            byte[] imageBytes = FileUtil.getFileBytes(file);
            ByteArrayResource resource = new ByteArrayResource("image", imageBytes);
            event.getSource().sendIfSupportBlocking(event.getBot().uploadImageBlocking(resource));
        }
    }
    /**
     * 发送组中的随机图片
     * @param event 事件
     * @param groupName 组名
     */
    public void sendRandom(GroupMessageEvent event, String groupName){
        if(groups.containsKey(groupName)){
            ImageGroup group = groups.get(groupName);
            File file = group.getRandomImage().getFile();
            byte[] imageBytes = FileUtil.getFileBytes(file);
            ByteArrayResource resource = new ByteArrayResource("image", imageBytes);
            event.getSource().sendIfSupportBlocking(event.getBot().uploadImageBlocking(resource));
        }
    }
    /**
     * 发送组中的随机图片
     * @param event 事件
     * @param groupName 组名
     */
    public void sendRandom(ContactMessageEvent event, String groupName){
        if(groups.containsKey(groupName)){
            ImageGroup group = groups.get(groupName);
            File file = group.getRandomImage().getFile();
            byte[] imageBytes = FileUtil.getFileBytes(file);
            ByteArrayResource resource = new ByteArrayResource("image", imageBytes);
            event.getSource().sendIfSupportBlocking(event.getBot().uploadImageBlocking(resource));
        }
    }

    public boolean hasGroup(String name){
        return groups.containsKey(name);
    }

    public HashMap<String, ImageGroup> getGroups() {
        return groups;
    }

    public void setGroups(HashMap<String, ImageGroup> groups) {
        this.groups = groups;
    }


}
