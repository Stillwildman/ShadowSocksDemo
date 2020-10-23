package com.vincent.shadowsocksdemo.utilities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.HKDF;

/**
 * Implements AES (Advanced Encryption Standard) with Cipher Block Chaining (CBC), which is a mode of
 * operation for symmetric key cryptographic block ciphers. For integrity it uses HMAC with SHA-256,
 * using the encrypt-then-mac schema.
 * <p>
 * The iv, mac and encrypted content will be encoded to the following format:
 * <p>
 * out = byte[] {x y y y y y y y y y y y y i j j ... z z z ...}
 * <p>
 * x = IV length as byte
 * y = IV bytes
 * i = mac length as byte
 * j = mac bytes
 * z = content bytes (encrypted content, auth tag)
 *
 * @author Patrick Favre-Bulle
 * @since 27.10.2018
 * @deprecated this is only meant for Kitkat backwards compatibility as this version and below does not
 * support AES-GCM via JCA/JCE.
 */
@SuppressWarnings({"WeakerAccess"})
public final class AESEncryptUtils implements AuthenticatedEncryption {
    private static final String ALGORITHM = "AES/CFB/NoPadding";
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final int IV_LENGTH_BYTE = 16;

    private final SecureRandom secureRandom;
    private final Provider provider;
    private ThreadLocal<Cipher> cipherWrapper = new ThreadLocal<>();

    public AESEncryptUtils() {
        this(new SecureRandom(), null);
    }

    public AESEncryptUtils(SecureRandom secureRandom) {
        this(secureRandom, null);
    }

    public AESEncryptUtils(SecureRandom secureRandom, Provider provider) {
        this.secureRandom = secureRandom;
        this.provider = provider;
    }

    @Override
    public byte[] encrypt(byte[] rawEncryptionKey, byte[] rawData, @Nullable byte[] associatedData) throws AuthenticatedEncryptionException {
        checkAesKey(rawEncryptionKey);

        byte[] iv = null;
        byte[] encrypted = null;
        byte[] mac = null;
        try {
            iv = new byte[IV_LENGTH_BYTE];
            secureRandom.nextBytes(iv);

            final Cipher cipherEnc = getCipher();
            cipherEnc.init(Cipher.ENCRYPT_MODE, createEncryptionKey(rawEncryptionKey), new IvParameterSpec(iv));
            encrypted = cipherEnc.doFinal(rawData);

            mac = macCipherText(rawEncryptionKey, encrypted, iv, associatedData);

            //ByteBuffer byteBuffer = ByteBuffer.allocate(1 + iv.length + 1 + mac.length + encrypted.length);
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + mac.length + encrypted.length);
            //byteBuffer.put((byte) iv.length);
            byteBuffer.put(iv);
            //byteBuffer.put((byte) mac.length);
            byteBuffer.put(mac);
            byteBuffer.put(encrypted);

            return byteBuffer.array();
        }
        catch (Exception e) {
            throw new AuthenticatedEncryptionException("could not encrypt", e);
        }
        finally {
            Bytes.wrapNullSafe(iv).mutable().secureWipe();
            Bytes.wrapNullSafe(encrypted).mutable().secureWipe();
            Bytes.wrapNullSafe(mac).mutable().secureWipe();
        }
    }

    @NonNull
    private SecretKeySpec createEncryptionKey(byte[] rawEncryptionKey) {
        return new SecretKeySpec(HKDF.fromHmacSha256().expand(rawEncryptionKey, Bytes.from("encKey").array(), rawEncryptionKey.length), "AES");
    }

    private byte[] macCipherText(byte[] rawEncryptionKey, byte[] cipherText, byte[] iv, @Nullable byte[] associatedData) {
        Mac hmac;

        try {
            hmac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKey macKey = createMacKey(rawEncryptionKey);

            hmac.init(macKey);
            hmac.update(iv);
            hmac.update(cipherText);
        }
        catch (InvalidKeyException e) {
            // due to key generation in createMacKey(byte[]) this actually can not happen
            e.printStackTrace();
            throw new IllegalStateException("error during HMAC calculation", e);
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new IllegalStateException("could not get cipher instance", e);
        }

        if (associatedData != null) {
            hmac.update(associatedData);
        }

        return hmac.doFinal();
    }

    @NonNull
    private SecretKey createMacKey(byte[] rawEncryptionKey) {
        byte[] derivedMacKey = HKDF.fromHmacSha256().expand(rawEncryptionKey, Bytes.from("macKey").array(), 32);
        return new SecretKeySpec(derivedMacKey, HMAC_ALGORITHM);
    }

    @Override
    public byte[] decrypt(byte[] rawEncryptionKey, byte[] encryptedData, @Nullable byte[] associatedData) throws AuthenticatedEncryptionException {
        checkAesKey(rawEncryptionKey);

        byte[] iv = null;
        byte[] mac = null;
        byte[] encrypted = null;
        try {
            ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

            //int ivLength = (byteBuffer.get() & 0xFF);
            //LogUtil.i("AESEncryptUtils", "ivLength: " + ivLength);
            //iv = new byte[ivLength];
            iv = Arrays.copyOfRange(encryptedData, 0, IV_LENGTH_BYTE);
            byteBuffer.get(iv);

            //int macLength = (byteBuffer.get() & 0xFF);
            //LogUtil.i("AESEncryptUtils", "macLength: " + macLength);
            //mac = new byte[macLength];
            mac = Arrays.copyOfRange(encryptedData, iv.length, iv.length + 32);
            byteBuffer.get(mac);

            encrypted = new byte[byteBuffer.remaining()];
            //encrypted = Arrays.copyOfRange(encryptedData, mac.length, encryptedData.length);
            byteBuffer.get(encrypted);

            verifyMac(rawEncryptionKey, encrypted, iv, mac, associatedData);

            final Cipher cipherDec = getCipher();
            cipherDec.init(Cipher.DECRYPT_MODE, createEncryptionKey(rawEncryptionKey), new IvParameterSpec(iv));

            return cipherDec.doFinal(encrypted);
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new AuthenticatedEncryptionException("could not decrypt", e);
        }
        finally {
            Bytes.wrapNullSafe(iv).mutable().secureWipe();
            Bytes.wrapNullSafe(encrypted).mutable().secureWipe();
            Bytes.wrapNullSafe(mac).mutable().secureWipe();
        }
    }

    @Override
    public int byteSizeLength(@KeyStrength int keyStrengthType) {
        return ((keyStrengthType == STRENGTH_HIGH) ? 16 : 32);
    }

    private void verifyMac(byte[] rawEncryptionKey, byte[] cipherText, byte[] iv, byte[] mac, @Nullable byte[] associatedData) throws AuthenticatedEncryptionException {
        byte[] actualMac = macCipherText(rawEncryptionKey, cipherText, iv, associatedData);

        if (!Bytes.wrap(mac).equalsConstantTime(actualMac)) {
            throw new AuthenticatedEncryptionException("encryption integrity exception: mac does not match");
        }
    }

    private void checkAesKey(byte[] rawAesKey) throws IllegalArgumentException {
        int keyLen = rawAesKey.length;

        if ((keyLen != 16) && (keyLen != 32)) {
            throw new IllegalArgumentException("AES key length must be 16, 24, or 32 bytes");
        }
    }

    private Cipher getCipher() {
        Cipher cipher = cipherWrapper.get();
        if (cipher == null) {
            try {
                if (provider != null) {
                    cipher = Cipher.getInstance(ALGORITHM, provider);
                } else {
                    cipher = Cipher.getInstance(ALGORITHM);
                }
            } catch (Exception e) {
                throw new IllegalStateException("could not get cipher instance", e);
            }
            cipherWrapper.set(cipher);
            return cipherWrapper.get();
        } else {
            return cipher;
        }
    }
}
