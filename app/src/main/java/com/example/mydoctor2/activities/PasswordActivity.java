package com.example.mydoctor2.activities;

import android.util.Base64;

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
            byte [] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
            String encryptedValue64 = Base64.encodeToString(encryptedByteValue, Base64.DEFAULT);
            return encryptedValue64;

        }

        public String decrypt(String value) throws Exception
        {
            Key key = generateKey();
            Cipher cipher = Cipher.getInstance(PasswordActivity.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedValue64 = Base64.decode(value, Base64.DEFAULT);
            byte [] decryptedByteValue = cipher.doFinal(decryptedValue64);
            String decryptedValue = new String(decryptedByteValue,"utf-8");
            return decryptedValue;

        }

        private Key generateKey() throws Exception
        {
            Key key = new SecretKeySpec(PasswordActivity.KEY.getBytes(),PasswordActivity.ALGORITHM);
            return key;
        }
}
