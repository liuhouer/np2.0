package cn.northpark.utils.encrypt;

import cn.northpark.utils.EnvCfgUtil;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * AES加解密
 *
 * @Author : bruce
 */
class AESUtils {
	/** 默认秘钥 */
	protected static final String KEY = EnvCfgUtil.getValByCfgName("AES_KEY");


    /**
     *  AES解密
     *
     * @Author : bruce
     * @param encryptValue
     * 						待解密内容
     * @param key
     * 						秘钥
     * @return
     * @throws Exception
     */
    protected static String decrypt(String encryptValue, String key) throws Exception {
        return aesDecryptByBytes(base64Decode(encryptValue), key);
    }

    /**
     * AES加密
     *
     * @Author : bruce
     *
     * @param value
     * 					待加密内容
     * @param key
     * 					秘钥
     * @return
     * @throws Exception
     */
    protected static String encrypt(String value, String key) throws Exception {
        return base64Encode(aesEncryptToBytes(value, key));
    }

    private static String base64Encode(byte[] bytes){
        return Base64Utils.encrypt(bytes);
    }

    @SuppressWarnings("static-access")
    private static byte[] base64Decode(String base64Code) throws Exception{
        return base64Code == null ? null : new Base64Utils().decrypt(base64Code);
    }

    private static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(encryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }

    private static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128, new SecureRandom(decryptKey.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

}
