//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mao.cn.learnRxJava2.utils.tools.utils;

import com.mao.cn.learnRxJava2.utils.tools.StringU;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
    private static final String[] strDigits = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    public MD5() {
    }

    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strDigits[iD1] + strDigits[iD2];
    }

    private static String byteToNum(byte bByte) {
        int iRet = bByte;
        System.out.println("iRet1=" + bByte);
        if(bByte < 0) {
            iRet = bByte + 256;
        }

        return String.valueOf(iRet);
    }

    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();

        for(int i = 0; i < bByte.length; ++i) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }

        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;

        try {
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return resultString;
    }

    public static String GetMD5Code16(String strObj) {
        String resultString = null;

        try {
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }

        return StringU.substring(resultString, 0, 16);
    }

    public static String GetMD5Code16() {
        String resultString = null;

        try {
            MessageDigest ex = MessageDigest.getInstance("MD5");
            resultString = byteToString(ex.digest("com.mao.cn.learnDevelopProject".getBytes()));
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
        }

        return StringU.substring(resultString, 0, 16);
    }

}
