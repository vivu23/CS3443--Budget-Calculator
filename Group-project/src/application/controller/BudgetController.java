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
import java.lang.Math;

import javafx.event.ActionEvent;

public class BudgetController {

	private String userid;

    @FXML
    private Label prompt;

    @FXML
    private TextArea income;

	@FXML
	private AnchorPane mainPane;
	
	@FXML
	private Button budget;
	
	ArrayList<Double> catTotals = new ArrayList<Double>();
	
    void passList(ArrayList<Double> data) {
    	catTotals = data;
    }

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
	
	@FXML
	public void budgetGenerator(ActionEvent event) throws Exception {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
    	String incomeAmount = income.getText().toString().trim();
    	
		double total = 0.0, extra = 0.0;
		double BillsMonthlyTotal = catTotals.get(0) / 12;
		double shoppingMonthlyTotal = catTotals.get(1) / 12;
		double foodMonthlyTotal = catTotals.get(2) / 12;
		double tuitionMonthlyTotal = catTotals.get(3) / 12;
		double otherMonthlyTotal = catTotals.get(4) / 12;
		
		for (int i = 0; i < 5; i++) {
			total += catTotals.get(i);
		}
		
		extra = (Double.parseDouble(incomeAmount) - total) / 12;

		String str = "Your annual income: " + formatter.format(Double.parseDouble(incomeAmount)) + "\nYour monthly spenditures:\nTuition: " + formatter.format(tuitionMonthlyTotal) + "/ month\nBills: " + formatter.format(BillsMonthlyTotal) +
				"/ month\nFood: " + formatter.format(foodMonthlyTotal) + "/ month\nShopping: " + formatter.format(shoppingMonthlyTotal) + "/ month\nOther: " + formatter.format(otherMonthlyTotal) + "/ month\n\nYou have: " + formatter.format(extra) + " to yourself each month!";
		
		income.setText(str);
		
		if(extra <= 0) {
			extra = Math.abs(extra);
			new Alert(Alert.AlertType.ERROR, "You are declining funds! Reduce spending " + formatter.format(extra) + " / month").showAndWait();
		}
		else
			new Alert(AlertType.CONFIRMATION, "Your budget is looking good! Welcome to Gold Team!").showAndWait();
		
		prompt.setText("Here's a look at your monthly budget");
	}
	
	public void getName(String username) {
		this.userid = username;
	}
}
