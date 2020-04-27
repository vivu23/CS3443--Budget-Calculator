package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import application.model.Spending;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class UpdateController {
	
	private Spending userSpending = new Spending();
	
	//This is the array of choices in the checkbox
	private String[] str = {"Bills", "Shopping", "Food", "Tuition"}; 
	
	private String userid;
	
	@FXML
	private Label name;
	
	@FXML
	private Label time;
	
	@FXML 
	private AnchorPane mainPane;
	
	@FXML
	private DatePicker date;

	@FXML
	private TextField money;
	
	@FXML
	private Label message;

	@FXML
	private ComboBox<String> spendingType;
	
	
	/*
	 * public void backButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method will handle the action of a back button. It'll take the user to the previous
	 * scene, which is the HomePage scene.
	 * 
	 */
	@FXML
	public void backButtonClicked(ActionEvent event) throws Exception {
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/HomePage.fxml"));
		mainPane = loader.load();
		HomePageController homepageController = loader.getController();
		homepageController.getName(userid);
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * public void resetButtonClicked(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the "RESET" button. It'll clear all the current information.
	 */
	public void resetButtonClicked(ActionEvent event) {
		date.setValue(null);
		money.clear();
		spendingType.setValue(null);
	}
	
	public void doneButtonClicked(ActionEvent event) throws IOException{
		LocalDate localDate = date.getValue();
		String dateToString = localDate.toString();
		String temp = money.getText();
		temp = temp.replace("$", "");
		
		double spending = Double.parseDouble(temp);
		String typeOfSpending = (String)spendingType.getValue();
		boolean checkDate;
		
		checkDate = userSpending.checkForDate(localDate);
		if(!checkDate) {
			message.setText("Date is INVALID");
			message.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
		}
		else {
			userSpending.addSpendingInfo(userid, dateToString, typeOfSpending, spending);
			message.setText("Spending is successfully updated!");
			date.setValue(null);
			money.clear();
			spendingType.setValue(null);
		}
	}
	
	public void initialize() {
		 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		 time.setText(LocalDateTime.now().format(formatter));
		 }), new KeyFrame(Duration.seconds(1)));
		 clock.setCycleCount(Animation.INDEFINITE);
		 clock.play();
		 this.spendingType.getItems().addAll(str);
	}
	
	public void getName(String userid) {
		this.name.setText("Hello, " + userid + "!");
		this.userid = userid;
	}
	
}
