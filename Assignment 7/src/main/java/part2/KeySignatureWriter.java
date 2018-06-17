package part2;

import part1.Keygen;
import shared.GlobalVariables;

import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KeySignatureWriter {
    private static final Logger logger = Logger.getLogger(KeySignatureWriter.class.getName());

    public void sign(String signer) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(GlobalVariables.ALGORITHM);
            System.out.println("Process... KeyFactory has been instantiated!");
            byte[] privateBytes = readPrivate();
            PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateBytes));
            System.out.println("Process... PrivateKey has been generated!");

            Signature signature = Signature.getInstance(GlobalVariables.SIGNATURE_ALGORITHM);
            System.out.println("Process... Signature has been instantiated!");
            signature.initSign(privateKey);
            System.out.println("Process... Signature has been initialized with private key.");

            String content = readContent();
            System.out.println("Process... File content has been read!");
            signature.update(content.getBytes());
            System.out.println("Process... Signature has been updated!");
            byte[] bytes = signature.sign();
            System.out.println("Process... Signature byte array has been created!");

            persistFile(signer, bytes, content);
            logger.log(Level.INFO, "File has been signed by " + signer);

        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Algorithm not found Exception: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.log(Level.SEVERE, "Invalid Key Spec Exception: " + e.getMessage());
        } catch (InvalidKeyException e) {
            logger.log(Level.SEVERE, "Invalid Key Exception: " + e.getMessage());
        } catch (SignatureException e) {
            logger.log(Level.SEVERE, "Signature Exception: " + e.getMessage());
        }
    }

    private void persistFile(String signer, byte[] bytes, String content) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(String.format("Content signed by %s.txt", signer)));
            oos.writeObject(bytes);
            oos.writeObject(content);
            logger.log(Level.INFO, "File has been persisted!");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input and/or Output Exception: " + e.getMessage());
        }
    }

    private String readContent() {
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(GlobalVariables.CONTENTS_FILE));
            String readLines;

            while((readLines = br.readLine()) != null) {
                sb.append(readLines);
            }

            logger.log(Level.INFO, "Contents have been read!");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File Not Found Exception: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input and/or Output Exception: " + e.getMessage());
        }

        return sb.toString();
    }

    private byte[] readPrivate() {
        byte[] bytes = null;

        try {
            FileInputStream fis = new FileInputStream(GlobalVariables.PRIVATE_KEY_FILE);
            bytes = new byte[(int) fis.getChannel().size()];
            fis.read(bytes);
            logger.log(Level.INFO, "Private Key File has been read!");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "File Not Found Exception: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Input and/or Output Exception: " + e.getMessage());
        }

        return bytes;
    }


}
