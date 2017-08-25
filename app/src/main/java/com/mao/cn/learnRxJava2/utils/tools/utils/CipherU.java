//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CipherU {
    private static Cipher encrypt;
    private static Cipher decrypt;

    public CipherU() {
    }

    public static Cipher getEncrypt() throws Exception {
        return getEncrypt("com.mao.cn.learnDevelopProject");
    }

    public static Cipher getEncrypt(String str) throws Exception {
        if(encrypt == null) {
            byte[] raw = MD5.GetMD5Code16(str).getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(1, skeySpec, iv);
            encrypt = cipher;
        }

        return encrypt;
    }

    public static Cipher getDecrypt() throws Exception {
        return getDecrypt("com.mao.cn.learnDevelopProject");
    }

    public static Cipher getDecrypt(String str) throws Exception {
        if(decrypt == null) {
            byte[] raw = MD5.GetMD5Code16(str).getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
            cipher.init(2, skeySpec, iv);
            decrypt = cipher;
        }

        return decrypt;
    }
}
