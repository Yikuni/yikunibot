package com.yikuni.robot.client.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class DataConfig {
    private String fileUrl;
    private String confUrl;
    private String logsUrl;
    private String tempUrl;

    private static final DataConfig config = new DataConfig();

    private final Logger log = LoggerFactory.getLogger(DataConfig.class);

    private DataConfig(){
        fileUrl = Objects.requireNonNull(DataConfig.class.getResource("")).getPath();
        try{
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("jar!"));
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("/"));
            fileUrl = fileUrl.substring(0, fileUrl.lastIndexOf("/"));
            if (fileUrl.startsWith("file:")){
                fileUrl = fileUrl.substring(fileUrl.indexOf('/') + 1);
            }else if(fileUrl.startsWith("file")){
                fileUrl = fileUrl.substring(fileUrl.indexOf('/'));
            }
            if(!fileUrl.contains(":/")){
                fileUrl = "/" + fileUrl;
            }
        }catch (Exception e){
            fileUrl = "C:/items/code/java/projects/yikuni-robot/robot-client-akarin/app";
        }
    }

    public String getConfUrl() {
        if(confUrl == null){
            confUrl = fileUrl + "/conf";
        }
        return confUrl;
    }

    public String getLogsUrl() {
        if(logsUrl == null){
            logsUrl = fileUrl + "/logs";
        }
        return logsUrl;
    }

    public String getTempUrl() {
        if(tempUrl == null){
            tempUrl = fileUrl + "/temp";
        }
        return tempUrl;
    }

    public static DataConfig getConfig(){
        return config;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
