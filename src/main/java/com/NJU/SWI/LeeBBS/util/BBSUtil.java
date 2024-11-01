package com.NJU.SWI.LeeBBS.util;

import com.alibaba.fastjson2.JSONObject;
import io.micrometer.common.util.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.Map;

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
    public static String jsonDumps(int code, String msg, Map<String,Object> map){
        JSONObject data = new JSONObject();
        data.put("code",code);
        data.put("msg",msg);
        if(map!=null){
            for(String key:map.keySet()){
                data.put(key,map.get(key));
            }
        }
        return data.toJSONString();
    }
    public static String jsonDumps(int code){
        return jsonDumps(code,null,null);
    }
    public static String jsonDumps(int code, String msg){
        return jsonDumps(code,msg,null);
    }

}
