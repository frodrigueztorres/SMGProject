package com.example.felip.smgproyect.helper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoHelper {
    public static String hashWithSha(String hashableString) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest(hashableString.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();

    }
}
