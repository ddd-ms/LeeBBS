package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import com.NJU.SWI.LeeBBS.util.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Value("${LeeBBS.path.upload}")
    private String uploadPath;
    @Value("${LeeBBS.path.domain}")
    private String domain;
    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private UserService userService;
    @Autowired
    private HostHolder hostHolder;

    @RequestMapping(value = "/setting",method = RequestMethod.GET)
    public String setting()
    {
        return "/site/setting";
    }
    @RequestMapping(value = "/upload",method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model){
        if(headerImage == null){
            model.addAttribute("error","您还没有选择图片");
            return "/site/setting";
        }
        String name = headerImage.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf("."));
        if(suffix == null){
            model.addAttribute("error","文件格式不正确");
            return "/site/setting";
        }
        String newName = BBSUtil.generateUUID() + suffix;
        File tgt = new File(uploadPath + "/" + newName);
        try {
            headerImage.transferTo(tgt);

        } catch (IOException e) {
            logger.error("上传文件失败:"+e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！");
        }

        String headerUrl = domain + contextPath + "/user/header/" + newName;
        userService.updateHeader(hostHolder.getUser().getId(),headerUrl);
        return "redirect:/index";
    }
}
