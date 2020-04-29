package application.controller;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LogInController {
	
	private User data = new User();
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML 
	private TextField userName;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Label message;
	
	/*
	 * public void backButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the back button. It will take the user go back to the Main
	 * Page.
	 * 
	 */
	public void backButtonClicked(ActionEvent event) throws Exception{
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/Main.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * public void resetButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the reset button. It will clear all the fields so user can
	 * input again
	 * 
	 */
	@FXML 
	public void resetButtonClicked(ActionEvent event) throws Exception{
		userName.clear();
		password.clear();
	}
	
	/*
	 * public void logInButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the SignUp button. It will check if the userName existed 
	 * if it existed -> check for password
	 * if it doesn't existed -> Inform the user that userName doesn't existed
	 *
	 * if the password is right -> take the user to the HomePage 
	 * if the password is wrong -> inform the user that the password is wrong
	 * 
	 */
	@FXML
	public void logInButtonClicked(ActionEvent event) throws Exception{
		String userid = userName.getText();
		String passwd = password.getText();
		boolean check;
		boolean success;
		
		data.setData();
		check = data.checkLogInName(userid.trim());
		
		if(!check) {
			message.setText("User Name DOES NOT exist");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			userName.clear();
			password.clear();
		}
		else {
			success = data.checkPassword(userid, passwd);
			if(!success) {
				message.setText("Password is WRONG");
				message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
				userName.clear();
				password.clear();
			}
			else {
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getClassLoader().getResource("application/view/HomePage.fxml"));
				mainPane = loader.load();
				HomePageController name = loader.getController();
				name.getName(userid);
				Scene scene = new Scene(mainPane);
				window.setScene(scene);
				window.show();
			}
		}
	}
}
