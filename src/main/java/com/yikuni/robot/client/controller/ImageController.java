package com.yikuni.robot.client.controller;

import com.yikuni.robot.client.image.ImageManager;
import com.yikuni.robot.common.domain.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/images")
public class ImageController {

    @RequestMapping("/refresh/{groupName}")
    public CommonResult<Boolean> refresh(@PathVariable("groupName") String groupName){
        ImageManager manager = ImageManager.getInstance();
        if (manager.refresh(groupName)){
            return new CommonResult<>(200, true, "刷新成功");
        }else{
            return new CommonResult<>(500, false, "出现错误, 可能是组名错误");
        }
    }

    @RequestMapping("/getGroups")
    public CommonResult<ImageManager> getGroups(){
        return new CommonResult<>(200, ImageManager.getInstance(), "获取成功"   );
    }
}
