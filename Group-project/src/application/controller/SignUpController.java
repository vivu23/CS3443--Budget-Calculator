package application.controller;

import application.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignUpController {
	
	private User data = new User();
	
	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private TextField userName;
	
	@FXML
	private PasswordField passwd;
	
	@FXML
	private PasswordField confirmPasswd;
	
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
		passwd.clear();
		confirmPasswd.clear();
		userName.setStyle(null);
		passwd.setStyle(null);
		confirmPasswd.setStyle(null);
	}
	
	public void doneButtonClicked(ActionEvent event) throws Exception{
		String userid = userName.getText();
		String password = passwd.getText();
		String cfpasswd = confirmPasswd.getText();
		boolean confirm, check, pwAuthentication, unAuthentication;
		
		unAuthentication = data.checkUserNameAuthentication(userid);
		if(!unAuthentication) {
			message.setText("UserName is INVALID!");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			passwd.clear();
			confirmPasswd.clear();
		}
		else {
		pwAuthentication = data.checkPWAuthentication(password);
		if(!pwAuthentication) {
			message.setText("Password is INVALID");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			passwd.clear();
			confirmPasswd.clear();
		}
		else {
		confirm = data.checkConfirmPassWord(password, cfpasswd);
		if(!confirm) {
			message.setText("Confirm Password is INCORRECT");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			passwd.clear();
			confirmPasswd.clear();
		}
		else {
		check = data.addUsers(userid, password); 
		if(!check) {
			message.setText("User Name already existed!");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			passwd.clear();
			confirmPasswd.clear();
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
		userName.clear();
		passwd.clear();
		confirmPasswd.clear();
		}
		}
		}
		}
	}
}
