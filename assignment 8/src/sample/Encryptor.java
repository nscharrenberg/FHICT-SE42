package sample;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Logger;

public class Encryptor {
    private static final Logger logger = Logger.getLogger(Encryptor.class.getSimpleName());
    private static final String CIPHER_INSTANCE = "AES";
    private static final int CIPHER_LENGTH = 128;
    private static final String MESSAGEDIGEST_INSTANCE = "SHA-1";
    private Cipher cipher;

   public Encryptor() {

   }

   // Based from https://github.com/tbuktu/ntru/blob/master/src/main/java/net/sf/ntru/demo/AesExample.java
   public void encrypt(String message, String password, String fileName) {
       try {
           char[] passwordCharacters = password.toCharArray();
           byte[] salt = createSaltUsingCipher(password);
           byte[] encryptedMessage = encryptedData(message);

           persistMessage(salt, encryptedMessage, fileName);
       } catch (NoSuchPaddingException e) {
           e.printStackTrace();
       } catch (NoSuchAlgorithmException e) {
           e.printStackTrace();
       } catch (InvalidKeyException e) {
           e.printStackTrace();
       } catch (BadPaddingException e) {
           e.printStackTrace();
       } catch (IllegalBlockSizeException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    private void persistMessage(byte[] salt, byte[] encryptedMessage, String fileName) throws IOException {
        EncryptedData data = new EncryptedData(encryptedMessage, salt);

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName + ".txt"));
        oos.writeObject(data);
    }

    private byte[] encryptedData(String message) throws BadPaddingException, IllegalBlockSizeException {
       byte[] dataToEncrypt = message.getBytes();
       byte[] cipherText = cipher.doFinal(dataToEncrypt);

       return cipherText;
    }

    private byte[] createSaltUsingCipher(String password) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] salt = createSalt();

        cipher = Cipher.getInstance(CIPHER_INSTANCE);
        cipher.init(Cipher.ENCRYPT_MODE, GlobalVariables.createKey(password, salt, MESSAGEDIGEST_INSTANCE, CIPHER_INSTANCE));

        return salt;
    }

//    private Key createKey(String password, byte[] salt) throws NoSuchAlgorithmException {
//        byte[] bytes = password.getBytes();
//        MessageDigest md = MessageDigest.getInstance(MESSAGEDIGEST_INSTANCE);
//        bytes = md.digest(bytes);
//        bytes = Arrays.copyOf(bytes, 16);
//
//        SecretKey sk = new SecretKeySpec(bytes, CIPHER_INSTANCE);
//
//        return sk;
//    }

    private byte[] createSalt() {
       byte[] bytes = new byte[CIPHER_LENGTH / 8];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(bytes);

        return bytes;
    }

}
