package com.NJU.SWI.LeeBBS.util;

import io.micrometer.common.util.StringUtils;
import org.springframework.util.DigestUtils;

public class BBSUtil {
    public static final String constantSalt = "salt058219";
    public static String generateUUID(){
        return java.util.UUID.randomUUID().toString().replaceAll("-","");
    }
    public static String md5(String key){
        if(StringUtils.isBlank(key)){
           return null;
        }
        key = key+constantSalt;
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
    public static String md5(String key,String salt){
        return md5(key + salt);
    }
}
