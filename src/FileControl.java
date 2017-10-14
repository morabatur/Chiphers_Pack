import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileControl {
    /*
     * Дозволяє вибрати потрібну директорію
     */
    public static String directoryChoos(Button button) {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Виберіть місце збереження...");
        File file = chooser.showDialog(button.getScene().getWindow());
        if (file != null) {
            String path = file.getAbsolutePath();
            return path;
        }
        return null;
    }

    /*
    Дозволяє вибрати потрібний файл з файлової системи Повертає абсолютний шлях файлу
     */
    public static String fileChoos(Button button) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Виберіть файл");
        File file = chooser.showOpenDialog(null);
        if (file != null) {
            String string = file.getAbsolutePath().toString();
            System.out.println("Метод fileChoos(Button button) повертає значення для обраного файлу - " + string);
            return string;
        } else {
            JOptionPane.showMessageDialog(null, "Не вибрано файл");
        }

        return null;
    }

    /*
    Записує дані у файл
     */
    public static void recordFile(String path, byte[] info) {
        System.out.println("Метод recordFile(String path, byte[] info) за аргумент path має - " + path);
        try (FileOutputStream fos = new FileOutputStream(new File(path))) {
            fos.write(info);
        } catch (java.io.IOException e) {
            System.err.println("Запис файлу не вдався");
            e.printStackTrace();
        }
    }

    /*
    Записує дані у файл
     */
    public static void recordFile(String path, String info) {
        System.out.println("Метод recordFile String path, String info за аргумент path має - " + path);
        try (FileWriter writer = new FileWriter(path, false)) {
            writer.write(info);
        } catch (java.io.IOException e) {
            System.err.println("Запис файлу не вдався");
            e.printStackTrace();
        }
    }

    /*
    Зчитує дані у масив байт
     */
    public static byte[] readFile(String path) {
        byte[] array = new byte[0];
        try {
            array = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {

        }
        return array;
    }

    public static String readFile(String path, String nullStr) {
        String line= "";
        try (FileReader reader = new FileReader(path)){
            int c;
            while((c=reader.read())!=-1){
                line += (char)c;
            }
        } catch (Exception e) {

        }
        return line;
    }

public static void serializeKey(String path){
    try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
    {
       Chiphers a = new Chiphers();

        oos.writeObject(a.getRawKey(null));
    }
    catch(Exception ex){

        System.out.println(ex.getMessage());
    }
}
    public static  byte[] deserializeObj(String path){
        byte key[] = new byte[0];
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)))
        {
             key = (byte[]) ois.readObject();

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return key;
    }


    /*
    * Дозволяє серіалізувати СекретнийКлюч*/
    public static void serializeSecretKey(String path, SecretKey key){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path)))
        {


            oos.writeObject(key);
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
    }
    /*
    Дозволяє десералізувати СекретнийКлюч
     */
    public static SecretKey deserializeSecretKey(String path){

        SecretKey key1 = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path)))
        {
            key1 = (SecretKey) ois.readObject();

        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }
        return key1;
    }

}





