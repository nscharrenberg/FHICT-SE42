package sample;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class GlobalVariables {
    public static final String FILE_NAME = "encrypted_file.txt";

    protected static Key createKey(String password, byte[] salt, String messageDigestInstance, String cipherIsntance) throws NoSuchAlgorithmException {
        byte[] bytes = password.getBytes();
        MessageDigest md = MessageDigest.getInstance(messageDigestInstance);
        bytes = md.digest(bytes);
        bytes = Arrays.copyOf(bytes, 16);

        SecretKey sk = new SecretKeySpec(bytes, cipherIsntance);

        return sk;
    }
}
