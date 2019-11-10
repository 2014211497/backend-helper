package net.engyne.backend.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.UUID;

public class BaseUtil {

    public static String toJsonString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue);
    }

    public static String genUUID() {
        return UUID.randomUUID().toString();
    }

    public static String encrypt(String text) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bs = md5.digest(text.getBytes("utf-8"));
        BASE64Encoder base64 = new BASE64Encoder();
        return base64.encode(bs);
    }

    public static String passwordEncrypt(String password) throws Exception {
        return BaseUtil.encrypt(password);
    }

}
