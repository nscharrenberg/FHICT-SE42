package sample;

import java.io.Serializable;

public class EncryptedData implements Serializable {
    private byte[] message;
    private byte[] salt;

    public EncryptedData(byte[] message, byte[] salt) {
        this.message = message;
        this.salt = salt;
    }

    public byte[] getMessage() {
        return message;
    }

    public byte[] getSalt() {
        return salt;
    }
}
