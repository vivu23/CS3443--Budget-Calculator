package application.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	public AnchorPane mainPane;
	
	/*
	 * public void signUpButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will signUp button and will take the user to the SignUp Page when the 
	 * user click on the SignUp button
	 * 
	 */
	@FXML
	public void signUpButtonClicked(ActionEvent event) throws Exception {
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/SignUp.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * public void logInButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the SignIn button and will take the user to the LogIn Page 
	 * when the user click on the SignIn button
	 * 
	 */
	@FXML
	public void logInButtonClicked(ActionEvent event) throws Exception {
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/LogIn.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
		
	}
}
