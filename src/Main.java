import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javax.crypto.*;


public class Main extends Application {

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("File Chiper");
        primaryStage.setScene(new Scene(root, 648, 395));
        primaryStage.setMaxHeight(450);
        primaryStage.setMaxWidth(750);
        primaryStage.show();
        //записує в файл масв байт
/*
        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Roman Chernish\\Desktop\\someFile"));
        Chiphers c = new Chiphers();
        fos.write(c.xorЕncrypt("sdfsdfsdf", "sdfsdfsf"));

        fos.close();
        //зчитує з файлу масив байт
        byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\Roman Chernish\\Desktop\\someFile"));

        try {
            String str = c.encrypt("Ciper text");
            System.out.print(str);
            String str2 = c.decrypt(str);
            System.out.println(str2);
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
        }*/

    }

    public static void main(String[] args) {
        launch(args);
        System.out.println("ШИФРУВАННЯ DES:");
        try {
            Chiphers a= new Chiphers();
            SecretKey key = a.keyDESgenerate();
            FileControl.serializeSecretKey("E:\\KeyBlet.dat", key);
            SecretKey key2 = FileControl.deserializeSecretKey("E:\\KeyBlet.dat");
           byte[] str =  a.encryptDes("Fuck you",key2 );
           System.out.println(
           a.decryptDes(str,key2 ));
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

        System.out.println("ШИФРУВАННЯ XOR:");
        byte secretText[] = encode("Fuck you", "no-no-no");
        System.out.println("Зашифрований текст byte" + secretText + '\n' + "Зашифрований текст String"  + new String(secretText));
        String decodeText = decode(secretText, "no-no-no");
        System.out.println("Розшифрований текст " + decodeText);

    }

    public static byte[] encode(String pText, String pKey) {
        byte[] txt = pText.getBytes();
        byte[] key = pKey.getBytes();
        byte[] res = new byte[pText.length()];

        for (int i = 0; i < txt.length; i++) {
            res[i] = (byte) (txt[i] ^ key[i % key.length]);
        }

        return res;
    }

    public static String decode(byte[] pText, String pKey) {
        byte[] res = new byte[pText.length];
        byte[] key = pKey.getBytes();

        for (int i = 0; i < pText.length; i++) {
            res[i] = (byte) (pText[i] ^ key[i % key.length]);
        }

        return new String(res);
    }
}
