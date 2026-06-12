package com.furniture.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类：SHA-256 + 随机盐值哈希
 * 替代原来的明文存储（admin）和 MySQL MD5（member）
 */
public class PasswordUtils {

    private static final int SALT_LENGTH = 16;

    /**
     * 对密码进行哈希（SHA-256 + 随机盐值）
     * @return Base64 编码的 "盐值 + 哈希" 字符串
     */
    public static String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashed = md.digest(password.getBytes("UTF-8"));

            byte[] combined = new byte[salt.length + hashed.length];
            System.arraycopy(salt, 0, combined, 0, salt.length);
            System.arraycopy(hashed, 0, combined, salt.length, hashed.length);

            return Base64.getEncoder().encodeToString(combined);
        } catch (Exception e) {
            throw new RuntimeException("密码哈希失败", e);
        }
    }

    /**
     * 验证密码是否匹配存储的哈希值
     * @param password 待验证的明文密码
     * @param storedHash 数据库中的哈希值（Base64 格式）
     */
    public static boolean checkPassword(String password, String storedHash) {
        if (password == null || storedHash == null) {
            return false;
        }
        try {
            byte[] combined = Base64.getDecoder().decode(storedHash);

            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(combined, 0, salt, 0, SALT_LENGTH);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] computedHash = md.digest(password.getBytes("UTF-8"));

            int hashLength = combined.length - SALT_LENGTH;
            for (int i = 0; i < hashLength; i++) {
                if (combined[SALT_LENGTH + i] != computedHash[i]) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 计算字符串的 MD5 十六进制（32位，用于兼容旧密码）
     */
    public static String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder(32);
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("MD5 计算失败", e);
        }
    }

    /**
     * 判断字符串是否为旧的 MD5 十六进制格式（32位十六进制）
     */
    public static boolean isMd5Hex(String s) {
        return s != null && s.length() == 32 && s.matches("[0-9a-fA-F]+");
    }

    /**
     * 密码验证（自动检测格式：SHA-256+盐值 或 MD5）
     */
    public static boolean verify(String input, String stored) {
        if (checkPassword(input, stored)) {
            return true;
        }
        if (isMd5Hex(stored) && stored.equalsIgnoreCase(md5Hex(input))) {
            return true;
        }
        return false;
    }
}
