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
	private TextField userName; //The TextField for user's abc123
	
	@FXML
	private PasswordField passwd; 
	
	@FXML
	private PasswordField confirmPasswd;
	
	@FXML
	private Label message;
	
	/*
	 * public void backButtonClicked(ActionEvent) 
	 * output: None
	 * 
	 * This method will handle the Back button and take the user back to the Main Page 
	 * when the user click on the back button
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
	 * this method will handle the reset button clear everything when the user click
	 * on the Reset button
	 * 
	 */
	@FXML 
	public void resetButtonClicked(ActionEvent event) throws Exception{
		userName.clear();
		passwd.clear();
		confirmPasswd.clear(); 
	}
	
	/*
	 * public void doneButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the DONE button and text the user's inputs and check.
	 * 
	 * If the userName is inauthentical there will be a message to inform the user.
	 * If the Password is inauthentical there will be a message to inform the user.
	 * If the confirm password is not matched with the password there will be a message
	 * to inform the user
	 * If the userName existed there will be a message to inform the user
	 * 
	 * If None of the things above happens, then it will store the user SignUn info
	 * and take the user to the HomePage
	 */
	@FXML
	public void doneButtonClicked(ActionEvent event) throws Exception{
		String userid = userName.getText();
		String password = passwd.getText();
		String cfpasswd = confirmPasswd.getText();
		boolean confirm, check, pwAuthentication, unAuthentication;
		
		//Check for userName Authentication
		unAuthentication = data.checkUserNameAuthentication(userid);
		if(!unAuthentication) { //If the userName is inauthentical
			message.setText("UserName is INVALID!");
			message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
			passwd.clear();
			confirmPasswd.clear();
		}
		else {
			//Check for Password Authentication
			pwAuthentication = data.checkPWAuthentication(password);
			if(!pwAuthentication) { //If password is inauthentical
				message.setText("Password is INVALID");
				message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
				passwd.clear();
				confirmPasswd.clear();
			}
			else {
				//Check if confirm password match Password or not
				confirm = data.checkConfirmPassWord(password, cfpasswd);
				if(!confirm) { //if confirm password is not matching password
					message.setText("Confirm Password is INCORRECT");
					message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
					passwd.clear();
					confirmPasswd.clear();
				}
				else {
					//Check if the userName existed or not
					check = data.addUsers(userid, password); 
					if(!check) { //If the userName existed
						message.setText("User Name already existed!");
						message.setStyle("-fx-text-fill: red; -fx-font-size: 18px");
						passwd.clear();
						confirmPasswd.clear();
					}
					else { //SignUp successfully
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
