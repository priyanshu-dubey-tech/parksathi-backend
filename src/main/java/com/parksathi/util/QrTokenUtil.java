package com.parksathi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class QrTokenUtil {

    private static final String SECRET = "1234567890123456";

    public static String encrypt(String value) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET.getBytes(),
                    "AES"
            );

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Base64.getUrlEncoder()
                    .encodeToString(cipher.doFinal(value.getBytes()));

        } catch (Exception e) {
            throw new RuntimeException("QR encryption failed");
        }
    }

    public static String decrypt(String token) {
        try {
            SecretKeySpec key = new SecretKeySpec(
                    SECRET.getBytes(),
                    "AES"
            );

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(
                    cipher.doFinal(Base64.getUrlDecoder().decode(token))
            );

        } catch (Exception e) {
            throw new RuntimeException("QR decryption failed");
        }
    }
}
