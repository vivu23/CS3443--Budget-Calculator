package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	
	Stage window = new Stage();
	
	@Override
	public void start(Stage primaryStage) throws IOException {

		window = primaryStage;
		window.setTitle("Gold Wallet");
		window.getIcons().add(new Image("file:Group-project/images/dollar-coin.png"));
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/Main.fxml"));
		AnchorPane layout = new AnchorPane();
		layout = loader.load();
		Scene scene = new Scene(layout);		
		window.setScene(scene);
		window.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
