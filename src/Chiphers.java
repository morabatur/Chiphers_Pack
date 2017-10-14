import sun.security.pkcs.PKCS8Key;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class Chiphers implements Serializable {


    public Chiphers() throws NoSuchPaddingException, NoSuchAlgorithmException {
    }

    public static byte[] xorЕncrypt(String text, byte[] keyWord) throws UnsupportedEncodingException {
        byte[] arr = text.getBytes("UTF-8");

        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = (byte) (arr[i] ^ keyWord[i % keyWord.length]);
        }
        return result;
    }

    public static String xorDecrypt(String text, byte[] keyWord) throws UnsupportedEncodingException {
        byte[] result = text.getBytes("UTF-8");
        byte[] keyarr = keyWord;
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) (result[i] ^ keyarr[i % keyarr.length]);
        }
        return new String(result);
    }


    private static final String KEY_TYPE = "AES";

    public String getRawKey() throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_TYPE);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(464641112);
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();


        return DatatypeConverter.printBase64Binary(skey.getEncoded());
    }


    public byte[] getRawKey(String str) throws NoSuchAlgorithmException {
        KeyGenerator kgen = KeyGenerator.getInstance(KEY_TYPE);
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(131213555);
        kgen.init(128, sr);
        SecretKey skey = kgen.generateKey();
        System.out.println(skey.getEncoded().length);
        return skey.getEncoded();
    }

    public String encrypt(String data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(KEY_TYPE);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, KEY_TYPE));
        return DatatypeConverter.printBase64Binary(cipher.doFinal(data.getBytes()));
    }

    public String decrypt(String data, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(KEY_TYPE);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, KEY_TYPE));
        return new String(cipher.doFinal(DatatypeConverter.parseBase64Binary(data)));
    }

    /*          sdfsdfsdfsdfsfsdfsdfsdfsdfsfsd           */

    public SecretKey keyDESgenerate(){
        KeyGenerator keygenerator = null;
        try {
            keygenerator = KeyGenerator.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKey myDesKey = keygenerator.generateKey();
        return myDesKey;
    }

    Cipher desCipher= Cipher.getInstance("DES/ECB/PKCS5Padding");

    public byte[] encryptDes(String str, SecretKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {


        // Create the cipher


        // Initialize the cipher for encryption
        desCipher.init(Cipher.ENCRYPT_MODE, key);
        //sensitive information
        byte[] text = str.getBytes();

        System.out.println("Text [Byte Format] : " + text);
        System.out.println("Text : " + new String(text));

        // Encrypt the text
        byte[] textEncrypted = desCipher.doFinal(text);
        System.out.println("Text Encryted : " + textEncrypted);
        return textEncrypted;
    }

    public String decryptDes(byte []textEncrypted, SecretKey key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        // Initialize the same cipher for decryption
        desCipher.init(Cipher.DECRYPT_MODE, key);

        // Decrypt the text
        byte[] textDecrypted = desCipher.doFinal(textEncrypted);

        System.out.println("Text Decryted : " + new String(textDecrypted));
        return new String(textDecrypted);
    }

    public static byte[] encodeXOR(String pText, String pKey) {
        byte[] txt = pText.getBytes();
        byte[] key = pKey.getBytes();
        byte[] res = new byte[pText.length()];

        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }

        return res;
    }

    public static String decodeXOR(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();

        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }

        return new String(res);
    }

}
