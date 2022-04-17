package com.inteliense.kleverkeys.generator.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

    public static String getHash(String input)
    {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");

            byte[] messageDigest = md.digest(input.getBytes());

            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String get256(String input)
    {
        try {

            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);

            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        }

        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";

    }

    public static String getHmac256(String input, String key) throws Exception {

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        return getHex(sha256_HMAC.doFinal(input.getBytes("UTF-8")));

    }

    public static String getHex(byte[] data) {

        final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);

        byte[] hexChars = new byte[data.length * 2];
        for(int j=0; j<data.length; j++) {

            int v = data[j] & 0xFF;
            hexChars[j*2] = HEX_ARRAY[v >>> 4];
            hexChars[j*2+1] = HEX_ARRAY[v & 0x0F];

        }

        return new String(hexChars, StandardCharsets.UTF_8);

    }

    public static byte[] getBytes(String hex) {

        byte[] val = new byte[hex.length() / 2];

        for(int i=0; i<val.length; i++) {

            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            val[i] = (byte) j;

        }

        return val;

    }

}