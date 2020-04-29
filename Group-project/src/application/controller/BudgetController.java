package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import application.model.Spending;

import java.lang.Math;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;

public class BudgetController {

	private Spending userBudget = new Spending();
	
	private String userid;

	@FXML
	private Label name;
	
	@FXML
	private Label time;
	
    @FXML
    private Label prompt;

    @FXML
    private TextArea income;

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Button budget;
	
	@FXML
	private Label alert;
	
	private ArrayList<Double> catTotals = new ArrayList<Double>();
	
   
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
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getClassLoader().getResource("application/view/HomePage.fxml"));
		mainPane = loader.load();
		HomePageController name = loader.getController();
		name.getName(userid);
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}
	
	/*
	 * public void budgetGenerator(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the Send It button. It will take the user input for
	 * Annual Income and send out the alert if his/her budget looks good or need to
	 * reduce spending, and also shows the user his/her extra money for each month.
	 * 
	 */
	@FXML
	public void budgetGenerator(ActionEvent event) throws Exception {
		String incomeAmount = income.getText().toString().trim();
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		Double extra = 0.0;
		boolean checkInput = false;
		
		checkInput = userBudget.checkforNum(incomeAmount);
		if(!checkInput) {
			alert.setText("Your annual income was not set properly");
			alert.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");	
			return;
		}
		String str = userBudget.printMessage(incomeAmount, catTotals);
		prompt.setText("Here's a look at your monthly budget");
		income.setText(str);
		budget.setVisible(false);
		
		extra = userBudget.getExtra();
		
		if(extra < 0) {
			extra = Math.abs(extra);
			alert.setText("You are declining funds!Reduce spending!");
			alert.setStyle("-fx-text-fill: red;-fx-alignment: CENTER");
		}
		else {
			alert.setText("Your budget is looking good! Welcome to Gold Team!");
			alert.setStyle("-fx-text-fill: green;-fx-alignment: CENTER");		
		}
	}	
		
	/*
	 * public void initialize()
	 * output: None
	 * 
	 * This method is creating a live clock that demonstrate the real time and Date
	 * 
	 */
	public void initialize() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			time.setText(LocalDateTime.now().format(formatter));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}
	
	/*
	 * public void passList(ArrayList<Double>)
	 * input: an ArrayList<Double>
	 * output: an ArrayList<Double>
	 * 
	 * This method is used to assign the List we're using equal to the list from the 
	 * previous scene, which is View page.
	 * 
	 */
	public void passList(ArrayList<Double> data) {
		catTotals = data;
	}
	
	/*
	 * public void getName(String)
	 * input: String
	 * output: None
	 * 
	 * This method will take the userid that being passed to this Controller and 
	 * set the Label as "Hello, abc123!"
	 */
	public void getName(String username) {
		name.setText("Hello, " + username + "!");
		this.userid = username;
	}
}
