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
}
