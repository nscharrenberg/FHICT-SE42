package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private JFXPasswordField passwordTxt;

    @FXML
    private JFXButton encryptBtn;

    @FXML
    private JFXTextField fileNameTxt;

    @FXML
    private JFXButton decryptBtn;

    @FXML
    private JFXTextArea messageTxt;

    Encryptor encryptor;
    Decryptor decryptor;

    public Controller() {
        encryptor = new Encryptor();
        decryptor = new Decryptor();
    }

    @FXML
    void decryptAction(ActionEvent event) {
        try {
            messageTxt.setText(decryptor.decrypt(passwordTxt.getText(), fileNameTxt.getText()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
        }
    }

    @FXML
    void encryptAction(ActionEvent event) {
        encryptor.encrypt(messageTxt.getText(), passwordTxt.getText(), fileNameTxt.getText());
    }
}
