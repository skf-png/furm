package com.example.springforum.common.utils;


import org.apache.commons.codec.digest.DigestUtils;

/**
 * md5加密
 */
public class MD5Util {
    /**
     *
     * @param str 要加密字符串
     * @return
     */
    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     *
     * @param str 原始字符串
     * @param salt 扰动字符串
     * @return
     */
    public static String md5(String str, String salt) {
        return md5(md5(str + salt) + salt);
    }

    public static boolean checkMd5(String realPassword, String salt, String inputPassword) {
        return md5(inputPassword, salt).equalsIgnoreCase(realPassword);
    }
}
