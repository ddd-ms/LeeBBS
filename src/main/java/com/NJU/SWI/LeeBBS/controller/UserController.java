package com.NJU.SWI.LeeBBS.controller;

import com.NJU.SWI.LeeBBS.annotation.LoginRequired;
import com.NJU.SWI.LeeBBS.service.UserService;
import com.NJU.SWI.LeeBBS.util.BBSUtil;
import com.NJU.SWI.LeeBBS.util.HostHolder;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

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
    @LoginRequired
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public String setting() {
        return "/site/setting";
    }
    @LoginRequired
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String uploadHeader(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "您还没有选择图片");
            return "/site/setting";
        }
//      检查文件大小
        if (headerImage.getSize() > 1024 * 1024) {
            model.addAttribute("error", "文件大小不能超过1M");
            return "/site/setting";
        }
        String name = headerImage.getOriginalFilename();
        String suffix = name.substring(name.lastIndexOf("."));
        if (suffix == null) {
            model.addAttribute("error", "文件格式不正确");
            return "/site/setting";
        }
        String uidName = hostHolder.getUser().getId() + "_header" + suffix;
//        String uidName = String.valueOf(hostHolder.getUser().getId()) + "_header" + suffix;
//      检查是否存在名为 uidName的图片，若有则删掉
        File oldHeader = new File(uploadPath + "/" + uidName);
        if (oldHeader.exists()) {
            /***
             * TODO：这里可以优化删除流程进行异步删除,把旧头像append到待删除队列
             */
            oldHeader.delete();
        }
        File tgt = new File(uploadPath + "/" + uidName);
        try {
            headerImage.transferTo(tgt);

        } catch (IOException e) {
            logger.error("上传文件失败:" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！");
        }

        String headerUrl = domain + contextPath + "/user/header/" + uidName;
        userService.updateHeader(hostHolder.getUser().getId(), headerUrl);
        return "redirect:/index";
    }

    //    http://localhost:8888/LeeBBS/user/header/155_header.png
    @RequestMapping(value = "/header/{fileName}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        fileName = uploadPath + "/" + fileName;
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        response.setContentType("image/" + suffix);
        try (
                InputStream inputStream = new FileInputStream(fileName);
        ) {
            OutputStream outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int b = 0;
            while ((b = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像失败:{}", e.getMessage());
        }
    }
}
