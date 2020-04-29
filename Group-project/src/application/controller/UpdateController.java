package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
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
	private String[] str = {"Bills", "Shopping", "Food", "Tuition","Other"}; 
	
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
	 * This method is handling the "RESET" button. It'll clear all the field when the user
	 * click on the RESET button.
	 * 
	 */
	@FXML
	public void resetButtonClicked(ActionEvent event) {
		date.setValue(null);
		money.clear();
		spendingType.setValue(null);
	}
	
	/*
	 * public void doneButtonClicked(ActionEvent)
	 * output: None 
	 * 
	 * This method is handling the "DONE" button. It'll check if the user input all the 
	 * proper information or not. 
	 * If not -> inform the user what need to be changed
	 * If input is right -> store the Updated info and clear all the field
	 * 
	 */
	@FXML
	public void doneButtonClicked(ActionEvent event) throws IOException{
		LocalDate localDate = date.getValue(); 
		//If the user doesn't choose the date 
		if (localDate == null) {
			message.setText("Date was not set properly.");
			message.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
			return;
		}
		
		String dateToString = null;
		
		dateToString = localDate.toString();
		
		String temp = money.getText();
		temp = temp.replace("$", "");
		double spending = Double.parseDouble(temp);
		String typeOfSpending = (String)spendingType.getValue();
		boolean checkDate = false;
		boolean checkMoney = false;
		
		if(typeOfSpending == null) { //if user doesn't choose the type of spending
			message.setText("Spending type is empty.");
			message.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
			return;
		}
		
		if(!userSpending.checkforNum(temp)){ //if the user doesn't type down the money
											 //or the input is not number
			message.setText("Money value is INVALID.");
			message.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
			return;
		}
		
		checkDate = userSpending.checkForDate(localDate);
			
		if(!checkDate) { // if the date is in the future
			message.setText("Date was not set properly");
			message.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
		}
		else {
			userSpending.addSpendingInfo(userid, dateToString, typeOfSpending, spending);
			message.setText("Spending is successfully updated!");
			message.setStyle("-fx-text-fill: green;-fx-alignment: CENTER");
			date.setValue(null);
			money.clear();
			spendingType.setValue(null);
		}
	}
	
	/*
	 * public void initialize()
	 * output: None
	 * 
	 * This method is creating a live clock that demonstrate the real time and date, and 
	 * set up the options for the comboBox
	 * 
	 */
	@FXML
	public void initialize() {
		 Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		 time.setText(LocalDateTime.now().format(formatter));
		 }), new KeyFrame(Duration.seconds(1)));
		 clock.setCycleCount(Animation.INDEFINITE);
		 clock.play();
		 this.spendingType.getItems().addAll(str);
	}
	
	/*
	 * public void getName(String)
	 * input: String
	 * output: None
	 * 
	 * This method will take the userid that being passed to this Controller and 
	 * set the Label as "Hello, abc123!"
	 */
	public void getName(String userid) {
		this.name.setText("Hello, " + userid + "!");
		this.userid = userid;
	}
	
}
