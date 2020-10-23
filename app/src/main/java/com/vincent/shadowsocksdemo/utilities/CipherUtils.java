package com.vincent.shadowsocksdemo.utilities;

import org.apache.http.util.EncodingUtils;

import java.security.MessageDigest;

/**
 * Created by Vincent on 2020/1/17.
 */
class CipherUtils {

    static byte[] getKey(String password, byte[] salt) {
        try {
            byte[] passwordSalt = EncodingUtils.getAsciiBytes(password);
            passwordSalt = concatenateByteArrays(passwordSalt, salt);

            byte[] hash1 = getHashForHash(null, passwordSalt);
            byte[] hash2 = getHashForHash(hash1, passwordSalt);

            return concatenateByteArrays(hash1, hash2);
        }
        catch (Exception e) {
            return null;
        }

    }

    static byte[] getIV(String password, byte[] salt) {
        try {
            byte[] passwordSalt = EncodingUtils.getAsciiBytes(password);
            passwordSalt = concatenateByteArrays(passwordSalt, salt);
            byte[] hash1 = getHashForHash(null, passwordSalt);
            byte[] hash2 = getHashForHash(hash1, passwordSalt);
            return getHashForHash(hash2, passwordSalt);
        }
        catch (Exception e) {
            return null;
        }

    }

    private static byte[] getHashForHash(byte[] hash, byte[] passwordSalt) {
        try {
            byte[] hashMaterial = concatenateByteArrays(hash, passwordSalt);
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(hashMaterial);
        }
        catch (Exception e) {
            return null;
        }
    }

    private static byte[] concatenateByteArrays(byte[] a, byte[] b) {
        if (a == null) return b;
        if (b == null) return a;

        byte[] result = new byte[a.length + b.length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);

        return result;
    }

}
