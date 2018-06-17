import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Keygen {
    private static final Logger logger = Logger.getLogger(Keygen.class.getName());
    public static final String RANDOM_ALGORITHM = "SHA1PRNG";
    public static final String ALGORITHM = "RSA";
    public static final int BIT_SIZE = 1024;
    public static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    public static final String PUBLIC_KEY_FILE = "public.txt";
    public static final String PRIVATE_KEY_FILE = "private.txt";
    public static final String CONTENTS_FILE = "contents.txt";



    public Keygen() {

    }

    public void generateKey() {
        try {
            SecureRandom random = SecureRandom.getInstance(RANDOM_ALGORITHM);
            KeyPairGenerator gen = KeyPairGenerator.getInstance(ALGORITHM);
            gen.initialize(BIT_SIZE, random);
            System.out.println("Processing... KeyPairGenerator has been initialized with a 1024 bit size.");

            KeyPair key = gen.genKeyPair();
            System.out.println("Processing... KeyPair has been created!");

            // Persist Private Key
            persistKey(key.getPrivate().getEncoded(), PRIVATE_KEY_FILE);
            System.out.println("Processing... Private key has been successfully generated!");

            // Persist Public Key
            persistKey(key.getPublic().getEncoded(), PUBLIC_KEY_FILE);
            System.out.println("Processing... Public key has been successfully generated!");

            logger.log(Level.INFO, "Key Generation Successful. Public & Private key generated.");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Algorithm not found Exception: " + e.getMessage());
        }
    }

    private void persistKey(byte[] bytes, String type) {
        try {
            FileOutputStream fos = new FileOutputStream(type);
            fos.write(bytes);
            logger.log(Level.INFO, "Bytes have been written to file with type " + type);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File Not Found Exception: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input and/or Output Exception: " + e.getMessage());
        }
    }

}
