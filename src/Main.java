import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {

	public void start(Stage primaryStage) throws IOException {
		 Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
	        primaryStage.setTitle("File Chiper");
	        primaryStage.setScene(new Scene(root, 648, 357));
	        primaryStage.setMaxHeight(450);
	        primaryStage.setMaxWidth(750);
	        primaryStage.show();
	        //записує в файл масв байт
	        FileOutputStream fos = new FileOutputStream(new File("C:\\Users\\Roman Chernish\\Desktop\\someFile"));
	        fos.write(Chiphers.xorЕncrypt("волапотвоdfgdfgdfgdалп", "авпловаптв"));
	        fos.close();
	        //зчитує з файлу масив байт
	        byte[] array = Files.readAllBytes(Paths.get("C:\\Users\\Roman Chernish\\Desktop\\someFile"));
	        System.out.println(Chiphers.xorDecrypt(array, "авпловаптв"));
	       // System.out.println(Chiphers.xorDecrypt(Chiphers.xorЕncrypt("волапотвоdfgdfgdfgdалп", "авпловаптв"), "авпловаптв"));
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
