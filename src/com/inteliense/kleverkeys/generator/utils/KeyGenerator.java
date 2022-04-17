package com.inteliense.kleverkeys.generator.utils;

import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class KeyGenerator {

    public static String[] generateKey(int bytes) throws NoSuchAlgorithmException {

        if(bytes == 32) {
            String[] result = AES.generateKey(256);
            result[2] = getRandomId();
            result[3] =  "256 bit key";
            return result;
        } else if(bytes == 16) {
            String[] result = AES.generateKey(128);
            result[2] = getRandomId();
            result[3] = "128 bit key";
            return result;
        }

        return null;

    }

    public static String[] generateIv(int bytes) throws NoSuchAlgorithmException {

        if(bytes == 16) {
            String[] result = AES.generateIv(bytes);
            result[2] = getRandomId();
            result[3] = "128 bit IV (for 128 & 256 bit key)";
            return result;
        }

        return null;

    }

    private static String getRandomId() {

        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        String encodedKeyHex = SHA.getHex(ivParameterSpec.getIV());

        return SHA.get256("abc" + encodedKeyHex + "def");

    }

}
