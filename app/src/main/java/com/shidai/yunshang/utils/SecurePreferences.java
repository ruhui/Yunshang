package com.shidai.yunshang.utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.shidai.yunshang.MyApplication;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by wangjie28 on 2015/12/1.
 */
public class SecurePreferences implements SharedPreferences {
    private final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    private final Cipher encrypter;
    private final Cipher decrypter;
    private final static String pass = "9D8ouzWFCBeBawCu";

    public static SecurePreferences getInstance() {
        return SecurePreferenceClassHolder.preferences;
    }


    private SecurePreferences() {
        try {
            //使用SHA-256加密文件
            byte[] key = MessageDigest.getInstance("SHA-256").digest(pass.getBytes());
            // 获得一个密钥生成器（AES加密模式）
            encrypter = Cipher.getInstance("AES");
            // 设置密匙ENCRYPT_MODE（加密模式），
            encrypter.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"));
            decrypter = Cipher.getInstance("AES");
            // 设置密匙ENCRYPT_MODE（解密模式），
            decrypter.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private final static class SecurePreferenceClassHolder {
        public static SecurePreferences preferences = new SecurePreferences();
    }

    @Override
    public Map<String, ?> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getString(String key, String defValue) {
        String v = sp.getString(encrypt(key), defValue == null ? null : encrypt(defValue));
        return v == null ? null : decrypt(v);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInt(String key, int defValue) {
        return Integer.parseInt(getString(key, Integer.toString(defValue)));
    }

    @Override
    public long getLong(String key, long defValue) {
        return Long.parseLong(getString(key, Long.toString(defValue)));
    }

    @Override
    public float getFloat(String key, float defValue) {
        return Float.parseFloat(getString(key, Float.toString(defValue)));
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return Boolean.parseBoolean(getString(key, Boolean.toString(defValue)));
    }

    @Override
    public boolean contains(String key) {
        return sp.contains(encrypt(key));
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public Editor edit() {
        return new SecureEditor(sp.edit());
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener);
    }

    private String encrypt(String value) {
        try {
            return Base64.encodeToString(encrypter.doFinal(value.getBytes()), Base64.DEFAULT).trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private String decrypt(String value) {
        try {
            return new String(decrypter.doFinal(Base64.decode(value, Base64.DEFAULT))).trim();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    private class SecureEditor implements Editor {
        private final Editor editor;

        private SecureEditor(Editor editor) {
            this.editor = editor;
        }

        @Override
        public Editor putString(String key, String value) {
            editor.putString(encrypt(key), encrypt(value));
            return this;
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Editor putInt(String key, int value) {
            editor.putString(encrypt(key), encrypt(Integer.toString(value)));
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            editor.putString(encrypt(key), encrypt(Long.toString(value)));
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            editor.putString(encrypt(key), encrypt(Float.toString(value)));
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            editor.putString(encrypt(key), encrypt(Boolean.toString(value)));
            return this;
        }

        @Override
        public Editor remove(String key) {
            editor.remove(encrypt(key));
            return this;
        }

        @Override
        public Editor clear() {
            editor.clear();
            return this;
        }

        @Override
        public boolean commit() {
            return editor.commit();
        }

        @Override
        public void apply() {
            editor.apply();
        }
    }
}