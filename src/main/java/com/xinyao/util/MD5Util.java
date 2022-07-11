package com.xinyao.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.security.MessageDigest;


/**
 * MD5撒盐加密 及MD5加密
 * @author bzy
 * @date 2019/12/5 09:46
 */
public class MD5Util {

    private static Logger logger = LoggerFactory.getLogger(MD5Util.class);
    /**
     * 散列算法类型为MD5
     */
    private static final String ALGORITH_NAME = "md5";

    /**
     * hash的次数
     */
    private static final int HASH_ITERATIONS = 51;


    /**
     * @param password 密码明文
     * @param salt     盐
     * @return 加密后密文
     */
    public static String encrypt(String password, String salt) {
        if (StringUtils.isBlank(password) || StringUtils.isBlank(salt)) {
            logger.error("密码加密失败原因： password and salt cannot be empty");
            throw new RuntimeException("密码加密失败");
        }
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(salt), HASH_ITERATIONS).toHex();
    }

    /**
     * @param target 待校验密码
     * @param source 原密码
     * @param salt   加密原密码的盐
     */
    public static boolean verifyPassword(String target, String source, String salt) {
        if (StringUtils.isBlank(target) || StringUtils.isBlank(source) || StringUtils.isBlank(salt)) {
            logger.info("校验密码失败，原因 target ={}， source ={}， salt ={}", target, source, salt);
            return false;
        }
        String targetEncryptPwd = encrypt(target, salt);
        return targetEncryptPwd.equals(source);
    }

    public static String md5(String str) {
        StringBuffer sb = new StringBuffer(32);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(str.getBytes("UTF-8"));
            for (int i = 0; i<array.length;i++)
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }


    /**
     * @param source 参数字符串
     * @return 生成的hash值
     */
    public static String generateHash(String source) {
        try {
            //参数校验
            if (StringUtils.isEmpty(source)) {
                return null;
            }
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source.getBytes());
            byte[] digest = md.digest();
            BigInteger bi = new BigInteger(1, digest);
            StringBuilder hashText = new StringBuilder(bi.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("MD5 hash运算失败，原因: {}", e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
      String encrypt = MD5Util.encrypt(MD5Util.generateHash("123456"), "admin");
      System.out.println("encrypt{  " + encrypt);

    }
}
