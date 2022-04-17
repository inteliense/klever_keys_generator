package com.inteliense.kleverkeys.generator.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class AES {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static IvParameterSpec iv;
    private static byte[] _iv;

    public static String[] generateKey(int bits) throws NoSuchAlgorithmException {

        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(bits); // for example
        SecretKey secretKey = keyGen.generateKey();
        String encodedKeyBase64 = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        String encodedKeyHex = SHA.getHex(secretKey.getEncoded());

        return new String[]{encodedKeyHex, encodedKeyBase64, null, null};

    }

    public static String[] generateIv(int bytes) throws NoSuchAlgorithmException {

        byte[] iv = new byte[bytes];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String encodedKeyBase64 = Base64.getEncoder().encodeToString(ivParameterSpec.getIV());
        String encodedKeyHex = SHA.getHex(ivParameterSpec.getIV());

        return new String[]{encodedKeyHex, encodedKeyBase64, null, null};

    }

}