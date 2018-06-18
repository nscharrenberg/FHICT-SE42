package sample;

import sun.plugin2.message.Message;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class Decryptor {
    private static final String CIPHER_INSTANCE = "AES";
    private static final int CIPHER_LENGTH = 128;
    private static final String MESSAGEDIGEST_INSTANCE = "SHA-1";
    private Cipher cipher;

    public Decryptor() {

    }

    public String decrypt(String password, String fileName) throws IOException, ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        EncryptedData data = readFile(fileName);
        cipher = Cipher.getInstance(CIPHER_INSTANCE);
        cipher.init(Cipher.DECRYPT_MODE, GlobalVariables.createKey(password, data.getSalt(), MESSAGEDIGEST_INSTANCE, CIPHER_INSTANCE));

        return decryptedData(data.getMessage());
    }

    private String decryptedData(byte[] message) throws BadPaddingException, IllegalBlockSizeException {
        byte[] decryptedMessage = cipher.doFinal(message);
        return new String(decryptedMessage);
    }

//    private Key createKey(String password, byte[] salt) throws NoSuchAlgorithmException {
//        byte[] bytes = password.getBytes();
//        MessageDigest md = MessageDigest.getInstance(MESSAGEDIGEST_INSTANCE);
//        bytes = md.digest(bytes);
//        bytes = Arrays.copyOf(bytes, 16);
//
//        SecretKey sc = new SecretKeySpec(bytes, CIPHER_INSTANCE);
//
//        return sc;
//    }

    private EncryptedData readFile(String fileName) throws IOException, ClassNotFoundException {
        EncryptedData data = null;

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName + ".txt"));
        data = (EncryptedData) ois.readObject();

        return data;
    }
}
