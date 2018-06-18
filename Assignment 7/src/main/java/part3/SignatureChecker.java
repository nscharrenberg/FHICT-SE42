package part3;

import part2.KeySignatureWriter;
import shared.GlobalVariables;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SignatureChecker {
    private static final Logger logger = Logger.getLogger(SignatureChecker.class.getName());

    private byte[] signature;
    private String content;

    public SignatureChecker() {

    }

    public boolean isSigned(String fileName) {
        try {
            readFile(fileName);
            System.out.println("File has been read!");

            PublicKey publicKey = readPublicKey();
            System.out.println("Public key has been read!");

            Signature contentSignature = Signature.getInstance(GlobalVariables.SIGNATURE_ALGORITHM);
            System.out.println("Signature has been instantiated!");

            contentSignature.initVerify(publicKey);
            System.out.println("Signature Verification has been initialized!");

            contentSignature.update(content.getBytes());
            System.out.println("Signature has been updated!");

            logger.log(Level.INFO, "Key and File has been checked!");
            return contentSignature.verify(signature);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "No Such Algorithm Exception " + e.getMessage());
        } catch (InvalidKeyException e) {
            logger.log(Level.SEVERE, "Invalid Key Exception " + e.getMessage());
        } catch (SignatureException e) {
            logger.log(Level.SEVERE, "Signature Exception " + e.getMessage());
        }

        return false;
    }

    private PublicKey readPublicKey() {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(GlobalVariables.PUBLIC_KEY_FILE));
            System.out.println("All bytes from PUBLIC_KEY_FILE has been read.");

            KeyFactory keyFactory = KeyFactory.getInstance(GlobalVariables.ALGORITHM);
            System.out.println("KeyFactory has been instantiated!");

            logger.log(Level.INFO, "Public key has been read!");
            return keyFactory.generatePublic(new X509EncodedKeySpec(bytes));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input or Output Exception " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "No Such Algorithm Exception " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.log(Level.SEVERE, "Invalid Key Spec Exception " + e.getMessage());
        }

        return null;
    }

    private void readFile(String fileName) {
        try {
            ObjectInput ois = new ObjectInputStream(new FileInputStream(fileName));
            signature = (byte[]) ois.readObject();
            System.out.println("Signature has been read from file!");

            content = (String) ois.readObject();
            System.out.println("Content has been read from file!");

            logger.log(Level.INFO, "File has been read!");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input or Output Exception " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Class not found Exception " + e.getMessage());
        }
    }



}
