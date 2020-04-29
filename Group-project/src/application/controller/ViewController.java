package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import application.controller.BudgetController;
import application.model.Spending;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ViewController {
	
	private Spending userSpending = new Spending();

	private String userid;

	@FXML
	private Label time;

	@FXML
	private Label name;

	@FXML
	private AnchorPane mainPane;

	@FXML
	private PieChart piechart;

	@FXML
	private Button budget;
	
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
		HomePageController homepageController = loader.getController();
		homepageController.getName(userid);
		Scene scene = new Scene(mainPane);
		window.setScene(scene);
		window.show();
	}

	/*
	 * public void week1(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the 1 week button. It will show the percentage of spending
	 * in 1 week
	 * 
	 */
	@FXML
	public void week1(ActionEvent event) throws Exception {
		int oneWeek = 7;
		getData(oneWeek);
		// Will only show items purchased within one month
	}
	
	/*
	 * public void oneMonth(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the 1 month button. It will show the percentage of spending
	 * in 1 month
	 * 
	 */
	@FXML
	public void oneMonth(ActionEvent event) throws Exception {
		int oneMonth = 30;
		getData(oneMonth);
	}

	/*
	 * public void threeMonth(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the 3 month button. It will show the percentage of spending
	 * in 3 months
	 * 
	 */
	@FXML
	public void threeMonth(ActionEvent event) throws Exception {
		int threeMonth = 90;
		getData(threeMonth);
	}

	/*
	 * public void sixMonth(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the 6 month button. It will show the percentage of spending
	 * in 6 months
	 * 
	 */
	@FXML
	public void sixMonth(ActionEvent event) throws Exception {
		int sixMonth = 180;
		getData(sixMonth);
	}

	/*
	 * public void oneYear(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the 1 year sbutton. It will show the percentage of spending
	 * in 1 year
	 * 
	 */
	@FXML
	public void oneYear(ActionEvent event) throws Exception {
		int oneYear = 365;
		getData(oneYear);

	}
	
	/*
	 * public void budgetList(ActionEvent)
	 * output: None
	 * 
	 * This method is handling the View Budget button. It will pass the Spending 
	 * information in 1 year to the Budget Controller. 
	 * It'll also take the user to the View Budget Page
	 * 
	 */
	@FXML
	public void budgetList(ActionEvent event) throws Exception {
		ArrayList<Double> categoryTotals = new ArrayList<Double>();
		categoryTotals = userSpending.setUpList(365, userid);
	
		try {
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getClassLoader().getResource("application/view/Budget.fxml"));
			mainPane = loader.load();
			BudgetController data = loader.getController();
			data.passList(categoryTotals);
			BudgetController name = loader.getController();
			name.getName(userid);
			Scene scene = new Scene(mainPane);
			window.setScene(scene);
			window.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * public void getData(int) 
	 * input: int
	 * output: None
	 * 
	 * This Method is in charge of pulling all the data necessary to generate
	 * the pie chart.
	 * The parameter "days" represents the amount of days that will be
	 * subtracted from today's current date.
	 * 
	 */

	public void getData(int days) throws Exception {
		ArrayList<Double> categoryTotals = new ArrayList<Double>();
		categoryTotals = userSpending.setUpList(days, userid);
		// Since we have all the values saved in an array, we can call the
		// method that will create the pie Chart.
		piechartGenerator(categoryTotals);
	}

	/*
	 * public void piechartGenerator(List<Double>)
	 * input: List<Double>
	 * output: None
	 * 
	 * This method will generate the actual graph. It will take a list of all 
	 * values as the parameter.
	 */
	public void piechartGenerator(List<Double> categoryTotals) {

		// Put all the elements from the list to this new array.
		Double[] values = categoryTotals.toArray(new Double[0]);
		Double percentage[];
		Double total = 0.0;

		PieChart.Data data[] = new PieChart.Data[5];

		// All the different categories
		String key[] = { "Bills", "Shopping", "Food", "Tuition", "Other" };

		//calculate the total of all the money
		for (int i = 0; i < 5; i++) {
			total += values[i];
		}
		// Loop through 5 times to add the key and percentage to the piechart data
		for (int i = 0; i < 5; i++) {
			data[i] = new PieChart.Data(key[i], Math.round(100.0*(values[i]/total)));
		}
		
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
		piechart.setData(pieChartData);
		piechart.setTitle("Spending:");

		// This will show the values next each category
		pieChartData.forEach(datas -> datas.nameProperty()
				.bind(Bindings.concat(datas.getName(), " ", datas.pieValueProperty(), "%")));
		budget.setVisible(true);
	}

	/*
	 * public void initialize()
	 * output: None
	 * 
	 * This method is creating a live clock that demonstrate the real time and Date. 
	 * It also set the "View Budget" button to be invisible.
	 * 
	 */
	public void initialize() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			time.setText(LocalDateTime.now().format(formatter));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
		budget.setVisible(false);
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
