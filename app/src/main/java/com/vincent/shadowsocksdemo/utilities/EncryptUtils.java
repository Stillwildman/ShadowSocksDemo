package com.vincent.shadowsocksdemo.utilities;

import android.util.Base64;

import androidx.annotation.NonNull;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.HKDF;

/**
 * AES encryption and decryption
 * <p>
 * 1. key's length >= 16
 * 2. iv's length > 16
 * 3. "transformation": AES/CBC/PKCS5Padding
 * 4. iv=12(bytes) length=128(bits)
 * 5. iv=24(bytes) length=192(bits)
 * 6. iv=32(bytes) length=256(bits)
 * <p>
 *
 * Created by Vincent on 2020/1/17.
 */
public class EncryptUtils {

    private static final String TAG = "EncryptUtils";
    private static final String AES = "AES";
    private static final String TRANSFORMATION = "AES/CFB/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";

    private static final int IV_BYTE_LENGTH = 16;

    /**
     * Base64 decode then AES decrypt
     *
     * @param data           Data to decrypt
     * @param key            Decrypt key
     * @return Decrypted bytes
     */
    public static String decryptBase64EncodedData(String data, String key) {
        if (data == null || data.isEmpty() || key == null || key.length() < 16) {
            throw (new InvalidParameterException());
        }

        try {
            byte[] rawKey = key.getBytes(StandardCharsets.UTF_8);

            byte[] encryptedBytes = Base64.decode(data.getBytes(StandardCharsets.UTF_8), Base64.DEFAULT);
            LogUtil.INSTANCE.i(TAG, "encryptedByte: " + Arrays.toString(encryptedBytes) + " Length: " + encryptedBytes.length);

            byte[] iv = Arrays.copyOfRange(encryptedBytes, 0, 16);
            LogUtil.INSTANCE.i(TAG, "ivByte: " + Arrays.toString(iv) + " Length: " + iv.length);

            byte[] mac = Arrays.copyOfRange(encryptedBytes, 16, 48);
            LogUtil.INSTANCE.i(TAG, "mac: " + Arrays.toString(mac) + " Length: " + mac.length);

            byte[] textBytes = Arrays.copyOfRange(encryptedBytes, 48, encryptedBytes.length);
            LogUtil.INSTANCE.i(TAG, "textByte: " + Arrays.toString(textBytes) + " Length: " + textBytes.length);

//            byte[] encKey = HKDF.fromHmacSha256().expand(rawKey, Bytes.from("encKey").array(), 16);
//            byte[] authKey = HKDF.fromHmacSha256().expand(rawKey, Bytes.from("authKey").array(), 32); //HMAC-SHA256 key is 32 byte

            Mac hmac = Mac.getInstance(HMAC_ALGORITHM);

//            SecretKey macKey = new SecretKeySpec(authKey, HMAC_ALGORITHM);
            SecretKey macKey = new SecretKeySpec(rawKey, hmac.getAlgorithm());

            hmac.init(macKey);
//            hmac.update(iv);
//            hmac.update(textBytes);

            byte[] refMac = hmac.doFinal(textBytes);
//            byte[] refMac = getMacCipherText(rawKey, textBytes, iv);

            if (!MessageDigest.isEqual(refMac, mac)) {
                throw new SecurityException("could not authenticate");
            }

            SecretKeySpec newKeySpec = new SecretKeySpec(rawKey, AES);
            SecretKeySpec encKeySpec = createEncryptionKey(rawKey);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, newKeySpec, new IvParameterSpec(iv));

            byte[] original = cipher.doFinal(textBytes);

            return new String(original, StandardCharsets.UTF_8);
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException |
                BadPaddingException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptAndBase64Encode(String data, String key) {
        byte[] rawData = data.getBytes(StandardCharsets.UTF_8);
        byte[] rawKey = key.getBytes(StandardCharsets.UTF_8);

        checkAesKey(rawKey);

        byte[] iv = new byte[IV_BYTE_LENGTH];
        byte[] encryptedText = null;
        byte[] mac = null;

        try {
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            final Cipher cipherEnc = Cipher.getInstance(TRANSFORMATION);

            SecretKeySpec newKeySpec = new SecretKeySpec(rawKey, AES);
            SecretKeySpec encKeySpec = createEncryptionKey(rawKey);

            cipherEnc.init(Cipher.ENCRYPT_MODE, newKeySpec, new IvParameterSpec(iv));
            encryptedText = cipherEnc.doFinal(rawData);

//            mac = getMacCipherText(rawKey, encryptedText, iv);
            Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKey macKey = new SecretKeySpec(rawKey, hmac.getAlgorithm());

            hmac.init(macKey);
//            hmac.update(iv);
//            hmac.update(encryptedText);

            mac = hmac.doFinal(encryptedText);

            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + mac.length + encryptedText.length);
            byteBuffer.put(iv);
            byteBuffer.put(mac);
            byteBuffer.put(encryptedText);

            return Base64.encodeToString(byteBuffer.array(), Base64.DEFAULT);
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException |
                NoSuchPaddingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            Bytes.wrapNullSafe(iv).mutable().secureWipe();
            Bytes.wrapNullSafe(encryptedText).mutable().secureWipe();
            Bytes.wrapNullSafe(mac).mutable().secureWipe();
        }
    }

    private static void checkAesKey(byte[] rawAesKey) throws IllegalArgumentException {
        int keyLen = rawAesKey.length;

        if ((keyLen != 16) && (keyLen != 32)) {
            throw new IllegalArgumentException("AES key length must be 16, 24, or 32 bytes");
        }
    }

    private static byte[] getMacCipherText(byte[] rawEncryptionKey, byte[] cipherText, byte[] iv) {
        SecretKey macKey = createMacKey(rawEncryptionKey);

        try {
            Mac hmac = Mac.getInstance(HMAC_ALGORITHM);
            hmac.init(macKey);
            hmac.update(iv);
            hmac.update(cipherText);

            return hmac.doFinal();
        }
        catch (NoSuchAlgorithmException | InvalidKeyException e) {
            // due to key generation in createMacKey(byte[]) this actually can not happen
            e.printStackTrace();
            throw new IllegalStateException("error during HMAC calculation");
        }
    }

    @NonNull
    private static SecretKey createMacKey(byte[] rawEncryptionKey) {
        byte[] derivedMacKey = HKDF.fromHmacSha256().expand(rawEncryptionKey, Bytes.from("macKey").array(), 32);
        return new SecretKeySpec(derivedMacKey, HMAC_ALGORITHM);
    }

    @NonNull
    private static SecretKeySpec createEncryptionKey(byte[] rawEncryptionKey) {
        return new SecretKeySpec(HKDF.fromHmacSha256().expand(rawEncryptionKey, Bytes.from("encKey").array(), rawEncryptionKey.length), AES);
    }
}
