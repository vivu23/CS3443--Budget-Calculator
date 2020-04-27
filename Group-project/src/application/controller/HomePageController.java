package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomePageController {
	
	private String userName;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Button update;
	@FXML
	private Label time;
	@FXML
	private Button view;
	@FXML
	private Label name;

	@FXML
	public void updateButtonClicked(ActionEvent event) throws Exception{
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/Update.fxml"));
		mainPane = loader.load();
		UpdateController updateController = loader.getController();
		updateController.getName(userName);		
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	@FXML
	public void viewButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/View.fxml"));
		mainPane = loader.load();
		ViewController viewController = loader.getController();
		viewController.getName(userName);
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	@FXML
	public void logOutButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/Main.fxml"));
		mainPane = loader.load();
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	public void initialize() {
		 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		 time.setText(LocalDateTime.now().format(formatter));
		 }), new KeyFrame(Duration.seconds(1)));
		 clock.setCycleCount(Animation.INDEFINITE);
		 clock.play();
	}
	
	public void getName(String userid) {
		this.name.setText("Hello, " + userid + "!");
		userName = userid;
	}

		
}
