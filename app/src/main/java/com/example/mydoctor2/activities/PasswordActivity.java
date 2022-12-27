package com.example.mydoctor2.activities;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class PasswordActivity {

        private static final String ALGORITHM = "AES";
        private static final String KEY = "1Hbfh667adfDEJ78";

        public String encrypt(String value) throws Exception
        {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(PasswordActivity.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] encryptedByteValue = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);

        }

        private Key generateKey() {
            return new SecretKeySpec(PasswordActivity.KEY.getBytes(),PasswordActivity.ALGORITHM);
        }
}
