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
	
	public void backButtonClicked(ActionEvent event) throws Exception{
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/Main.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	@FXML 
	public void resetButtonClicked(ActionEvent event) throws Exception{
		userName.clear();
		password.clear();
	}
	
	public void logInButtonClicked(ActionEvent event) throws Exception{
		String userid = userName.getText();
		String passwd = password.getText();
		boolean check;
		boolean success;
		
		data.setData();
		check = data.checkLogInName(userid);
		
		if(!check) {
			message.setText("User Name DOES NOT exist");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			userName.clear();
			password.clear();
		}
		else {
		success = data.checkPasswword(userid, passwd);
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
