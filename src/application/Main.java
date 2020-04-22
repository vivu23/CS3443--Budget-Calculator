package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	Stage window = new Stage();

	@Override
	public void start(Stage primaryStage) throws Exception{
		
		window = primaryStage;
		window.setTitle("Gold Wallet");
		
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
	


