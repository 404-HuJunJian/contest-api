package com.watering.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/04/09/19:55
 * @Description:
 */
public class EncryptUtil {

    public static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    public static String encrypt(String source){
        return bCryptPasswordEncoder.encode(source);
    }

}
