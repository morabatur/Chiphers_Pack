
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.swing.*;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.stream.Stream;


public class Controller22 {

    @FXML
    private Button button_encrypt;
    @FXML
    private TextArea textArea_encrypt;
    @FXML
    private TextField textField_encrypt;
    @FXML
    private ComboBox comboBox_encrypt;
    @FXML
    private Button btn_keyGenerate;
    @FXML
    private Button button_dencrypt;
    @FXML
    private TextArea textArea_dencrypt;
    @FXML
    private TextField textField_dencrypt;
    @FXML
    private ComboBox comboBox_dencrypt;
    @FXML
    private Button button_keyDownload;

    @FXML
    public void initialize() {
        addElementComboBox(comboBox_encrypt);
        addElementComboBox(comboBox_dencrypt);

    }


    private void addElementComboBox(ComboBox comboBox) {
        ObservableList fruits = FXCollections.observableArrayList("XOR", "AES", "DES");
        comboBox.setItems(fruits);
    }


    public void encryptClick(ActionEvent actionEvent) {

        if (comboBox_encrypt.getValue().equals("XOR")) {
            try {
                byte secretText[] = Chiphers.encodeXOR(textArea_encrypt.getText().toString(), textField_encrypt.getText().toString());
                System.out.println("Текст зашифрований за алгоритмом XOR = " + secretText
                +'\n' + new String(secretText));
                FileControl.recordFile(FileControl.directoryChoos(button_encrypt) + "\\cryptoXOR.txt", secretText);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (comboBox_encrypt.getValue().equals("AES")) {
            Chiphers a = null;
            try {
                a = new Chiphers();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            try {
                byte[] testStr = FileControl.deserializeObj(textField_encrypt.getText().toString());

                System.out.println(testStr.length);
                String s = a.encrypt(textArea_encrypt.getText(), testStr);
                FileControl.recordFile(FileControl.directoryChoos(button_encrypt) + "\\crypto.txt", s);

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
        if (comboBox_encrypt.getValue().equals("DES")) {
            try {
                Chiphers a = new Chiphers();
                byte secretText[] = a.encryptDes(textArea_encrypt.getText().toString(), FileControl.deserializeSecretKey(textField_encrypt.getText().toString()));//цю байду можна представити у вигляді строки і спробувати записати чисто строкою
                FileControl.recordFile(FileControl.directoryChoos(button_encrypt) + "\\cryptoDES.txt", secretText);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }

    }


    public void generateKeyClick(ActionEvent actionEvent) {
        if (comboBox_encrypt.getValue().equals("AES")) {
            String path = FileControl.directoryChoos(button_encrypt) + "\\newAES_SecretKey.dat";
            try {
                FileControl.serializeKey(path);
                textField_encrypt.setText(path);
                textField_encrypt.setEditable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (comboBox_encrypt.getValue().equals("DES")) {
            String path = FileControl.directoryChoos(button_encrypt) + "\\newDES_SecretKey.dat";
            try {
                Chiphers a = new Chiphers();
                FileControl.serializeSecretKey(path, a.keyDESgenerate());
                textField_encrypt.setText(path);
                textField_encrypt.setEditable(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    public void dencryptClick(ActionEvent actionEvent) {
        if (comboBox_dencrypt.getValue().equals("XOR")) {
            byte secretText[] = FileControl.readFile(FileControl.fileChoos(button_dencrypt));
           String string = Chiphers.decodeXOR(secretText, textField_dencrypt.getText().toString());
           textArea_dencrypt.setText(string);
        }

        if (comboBox_dencrypt.getValue().equals("AES")) {
            //Операції отримання ключа
            String path = textField_dencrypt.getText().toString();
            System.out.println(path);
            byte[] key = FileControl.deserializeObj(path);

            System.out.println("Довжинна масиву байт ключа = " + key.length);
            String text = FileControl.readFile(FileControl.fileChoos(button_dencrypt), null);
            System.out.println("Зчитаний зашифрований текст " + text);
            Chiphers a = null;
            try {
                a = new Chiphers();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            }
            try {
                String str = a.decrypt(text, key);
                System.out.println("Розшифрований текст " + str);
                textArea_dencrypt.setText(str);
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

        if (comboBox_dencrypt.getValue().equals("DES")){
            SecretKey key = FileControl.deserializeSecretKey(textField_dencrypt.getText().toString());
            Chiphers a = null;
            try {
                a = new Chiphers();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            try {
                String str = a.decryptDes(FileControl.readFile(FileControl.fileChoos(button_dencrypt)), key);
                textArea_dencrypt.setText(str);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            } catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            }
        }
    }

    String strKeyDownloadClick;

    public void keyDownloadClick(ActionEvent actionEvent) {
        strKeyDownloadClick = FileControl.fileChoos(button_keyDownload);
        textField_dencrypt.setText(strKeyDownloadClick);


    }
}



