package com.metric.skava.app.util;

import android.util.Base64;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Created by metricboy on 5/30/14.
 */
public class SkavaBasicAuthenticator {

    private static final int PBKDF2IterCount = 1000; // default for Rfc2898DeriveBytes
    private static final int PBKDF2SubkeyLength = 256 / 8; // 256 bits
    private static final int SaltSize = 128 / 8; // 128 bits

    public static boolean verifyHashedPassword(String hashedPassword, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        if (hashedPassword == null)
        {
            throw new IllegalArgumentException("hashedPassword");
        }
        if (password == null)
        {
            throw new IllegalArgumentException("password");
        }

        boolean result = false;

        byte[] hashedPasswordBytes = Base64.decode(hashedPassword, Base64.DEFAULT);

        if (hashedPasswordBytes.length != (1 + SaltSize + PBKDF2SubkeyLength) || hashedPasswordBytes[0] != 0x00)
        {
            // Wrong length or version header.
            return false;
        }

        byte[] salt = new byte[SaltSize];
        System.arraycopy(hashedPasswordBytes, 1, salt, 0, SaltSize);
        byte[] storedSubkey = new byte[PBKDF2SubkeyLength];
        System.arraycopy(hashedPasswordBytes, 1 + SaltSize, storedSubkey, 0, PBKDF2SubkeyLength);

        byte[] generatedSubkey;

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, PBKDF2IterCount, SaltSize * 16);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        generatedSubkey = factory.generateSecret(keySpec).getEncoded();

        result = Arrays.equals(storedSubkey, generatedSubkey);
        return result;
    }

}
