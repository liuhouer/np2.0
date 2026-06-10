package cn.northpark.np5.utils.encrypt;

import cn.northpark.np5.utils.EnvCfgUtil;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;

public class NorthParkCryptUtils {

    public static String northparkEncrypt(String passport) {
        try {
            String cryptKey = EnvCfgUtil.getValByCfgName("NORTHPARK_CRYPT_KEY");
            if (cryptKey == null || cryptKey.isEmpty()) {
                cryptKey = "abc226@@";
            }
            IvParameterSpec iv = new IvParameterSpec(cryptKey.getBytes("UTF-8"));
            DESKeySpec desKey = new DESKeySpec(cryptKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, securekey, iv);
            byte[] encryptedBytes = cipher.doFinal(passport.getBytes("UTF-8"));
            String base64Encoded = Base64.getEncoder().encodeToString(encryptedBytes);
            return URLEncoder.encode(base64Encoded, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    public static String northparkDecrypt(String passport) {
        try {
            String cryptKey = EnvCfgUtil.getValByCfgName("NORTHPARK_CRYPT_KEY");
            if (cryptKey == null || cryptKey.isEmpty()) {
                cryptKey = "abc226@@";
            }
            String decodedPassport = URLDecoder.decode(passport, "UTF-8");
            byte[] encryptedBytes = Base64.getDecoder().decode(decodedPassport);
            IvParameterSpec iv = new IvParameterSpec(cryptKey.getBytes("UTF-8"));
            DESKeySpec desKey = new DESKeySpec(cryptKey.getBytes("UTF-8"));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, securekey, iv);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }
}