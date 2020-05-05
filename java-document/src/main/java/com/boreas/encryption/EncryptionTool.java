package com.boreas.encryption;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;
/**
 * @author boreas
 * 单向加密算法
 * BASE64 严格地说，属于编码格式，而非加密算法
 * MD5(Message Digest algorithm 5，信息摘要算法)
 * SHA(Secure Hash Algorithm，安全散列算法)
 * 复杂的加密算法
 * RSA(算法的名字以发明者的名字命名：Ron Rivest, AdiShamir 和Leonard Adleman)
 * DES/3DES(Digital Signature Algorithm，数字签名)
 * 国密算法
 * SM2/SM4(是由国家密码管理局编制的一种商用密码分组标准对称算法)
 * @create 2020-03-08 下午 10:39
 */
public class EncryptionTool {

    private static final String PRIVATE_KEY = "";
    private static final String PUBLIC_KEY = "";

    // base64
    public static byte[] encode2Base64(byte[] bytes) {
        byte[] bts = Base64.encodeBase64(bytes);
        return bts;
    }

    public static byte[] decode2Base64(String str) {
        byte[] bts = Base64.decodeBase64(str);
        return bts;
    }
    // MD5
    public static String md5(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return new String(digest.digest(str.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    // SHA-1
    public static byte[] sha1ToBytes(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte[] bts = digest.digest(str.getBytes());
            return bts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    // DES/3DES
    public static byte[] encryptMode(String keyStr, byte[] src) {

        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keyStr.getBytes(), "DESede");
            // 加密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }


    public static byte[] decryptMode(String keyStr, byte[] src) {
        try {
            SecretKey deskey = new SecretKeySpec(keyStr.getBytes(), "DESede");
            // 解密
            Cipher c1 = Cipher.getInstance("DESede");
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }
    // RSA
    /** * 初始化公密钥 * * @return * @throws Exception */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator kpGenerator = KeyPairGenerator.getInstance("RSA");
        kpGenerator.initialize(2048);
        KeyPair keyPair = kpGenerator.generateKeyPair();
        // 存储公钥
        PublicKey publicKey = keyPair.getPublic();
        // 存储密钥
        PrivateKey privateKey = keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);

        return keyMap;
    }

    /** * 取出公钥 * * @param keyMap * @return
     * @return*/
    public static byte[] getPublicKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get("RSAPublicKey");
        return encode2Base64(new String(key.getEncoded()).getBytes());
    }

    /** * 取出密钥 * * @param keyMap * @return
     * @return*/
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get("RSAPrivateKey");
        return encode2Base64(new String(key.getEncoded()).getBytes());
    }

    /** * 公钥加密 * * @param data * @param publicKey * @return * @throws Exception */
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)
            throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        // 模长
        int key_len = publicKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }
        return mi;
    }

    /** * 私钥加密 * * @param data * @param privateKey * @return * @throws Exception */
    public static String encryptByPrivateKey(String data,
                                             RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        // 加密数据长度 <= 模长-11
        String[] datas = splitString(data, key_len - 11);
        String mi = "";
        // 如果明文长度大于模长-11则要分组加密
        for (String s : datas) {
            mi += bcd2Str(cipher.doFinal(s.getBytes()));
        }

        return mi;
    }

    /** * 私钥解密 * * @param data * @param privateKey * @return * @throws Exception */
    public static String decryptByPrivateKey(String data,
                                             RSAPrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] bytes = data.getBytes();
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
        System.err.println(bcd.length);
        // 如果密文长度大于模长则要分组解密
        String ming = "";
        byte[][] arrays = splitArray(bcd, key_len);
        for (byte[] arr : arrays) {
            ming += new String(cipher.doFinal(arr));
        }
        return ming;
    }

    /** * ASCII码转BCD码 * */
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
        byte[] bcd = new byte[asc_len / 2];
        int j = 0;
        for (int i = 0; i < (asc_len + 1) / 2; i++) {
            bcd[i] = asc_to_bcd(ascii[j++]);
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
        }
        return bcd;
    }

    public static byte asc_to_bcd(byte asc) {
        byte bcd;

        if ((asc >= '0') && (asc <= '9'))
            bcd = (byte) (asc - '0');
        else if ((asc >= 'A') && (asc <= 'F'))
            bcd = (byte) (asc - 'A' + 10);
        else if ((asc >= 'a') && (asc <= 'f'))
            bcd = (byte) (asc - 'a' + 10);
        else
            bcd = (byte) (asc - 48);
        return bcd;
    }

    /** * BCD转字符串 */
    public static String bcd2Str(byte[] bytes) {
        char temp[] = new char[bytes.length * 2], val;

        for (int i = 0; i < bytes.length; i++) {
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');

            val = (char) (bytes[i] & 0x0f);
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
        }
        return new String(temp);
    }

    /** * 拆分字符串 */
    public static String[] splitString(String string, int len) {
        int x = string.length() / len;
        int y = string.length() % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        String[] strings = new String[x + z];
        String str = "";
        for (int i = 0; i < x + z; i++) {
            if (i == x + z - 1 && y != 0) {
                str = string.substring(i * len, i * len + y);
            } else {
                str = string.substring(i * len, i * len + len);
            }
            strings[i] = str;
        }
        return strings;
    }

    /** * 拆分数组 */
    public static byte[][] splitArray(byte[] data, int len) {
        int x = data.length / len;
        int y = data.length % len;
        int z = 0;
        if (y != 0) {
            z = 1;
        }
        byte[][] arrays = new byte[x + z][];
        byte[] arr;
        for (int i = 0; i < x + z; i++) {
            arr = new byte[len];
            if (i == x + z - 1 && y != 0) {
                System.arraycopy(data, i * len, arr, 0, y);
            } else {
                System.arraycopy(data, i * len, arr, 0, len);
            }
            arrays[i] = arr;
        }
        return arrays;
    }
}
