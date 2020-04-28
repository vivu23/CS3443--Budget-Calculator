package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class ViewController {

	private static final boolean String = false;

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

	// Will only show items purchased within one week
	@FXML
	public void week1(ActionEvent event) throws Exception {
		int oneWeek = 7;
		getData(oneWeek);

		// Will only show items purchased within one month
	}

	@FXML
	public void oneMonth(ActionEvent event) throws Exception {
		int oneMonth = 30;
		getData(oneMonth);
	}

	// Will only show items purchased within two months
	@FXML
	public void twoMonth(ActionEvent event) throws Exception {
		int twoMonth = 60;
		getData(twoMonth);
	}

	// Will only show items purchased within the past three months
	@FXML
	public void threeMonth(ActionEvent event) throws Exception {
		int threeMonth = 90;
		getData(threeMonth);
	}

	@FXML
	public void sixMonth(ActionEvent event) throws Exception {
		int sixMonth = 180;
		getData(sixMonth);
	}

	// Will only show items purchased within the past year
	@FXML
	public void oneYear(ActionEvent event) throws Exception {
		int oneYear = 365;
		getData(oneYear);

	}

	// This Method is in charge of pulling all the data necessary to generate
	// the pie chart.
	// The parameter "days" represents the amount of days that will be
	// subtracted from today's current date.

	public void getData(int days) throws Exception {

		ArrayList<Double> categoryTotals = new ArrayList<Double>();

		// Will be used to store the total of each category
		double tuitionTotal = 0.0;
		double BillsTotal = 0.0;
		double shoppingTotal = 0.0;
		double foodTotal = 0.0;
		double otherTotal = 0.0;

		// get todays time
		LocalDate oneWeekAgoLocal = LocalDate.now().minusDays(days);

		// We want type Date not localdate.
		Date weekAgoDate = java.sql.Date.valueOf(oneWeekAgoLocal);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		FileReader file = new FileReader(new File("Group-project/files/" + userid + ".txt"));

		BufferedReader br = new BufferedReader(file);
		String temp = br.readLine();

		// loop through the file and use and blank space as delimiter
		while (temp != null) {
			String[] spaceSeparatorArr = temp.split(" ");

			String dateInString = spaceSeparatorArr[0];

			Date usrDate = formatter.parse(dateInString);
			
			// **First** check if the date on file is after a week ago or
			// whatever time set by the method calling

			// **Second** check if the category is matched, then start updating
			// the totals for that specific category
			if (!usrDate.before(weekAgoDate)) {
				if (spaceSeparatorArr[1].equals("Tuition")) {
					tuitionTotal += Double.parseDouble(spaceSeparatorArr[2]);
				}
				if (spaceSeparatorArr[1].equals("Bills")) {
					BillsTotal += Double.parseDouble(spaceSeparatorArr[2]);
				}
				if (spaceSeparatorArr[1].equals("Shopping")) {
					shoppingTotal += Double.parseDouble(spaceSeparatorArr[2]);
				}
				if (spaceSeparatorArr[1].equals("Food")) {
					foodTotal += Double.parseDouble(spaceSeparatorArr[2]);
				}
				if (spaceSeparatorArr[1].equals("Other")) {
					otherTotal += Double.parseDouble(spaceSeparatorArr[2]);
				}

			}

			temp = br.readLine();
		}
		// starting adding all the total to our arraylist.
		categoryTotals.add(BillsTotal);
		categoryTotals.add(shoppingTotal);
		categoryTotals.add(foodTotal);
		categoryTotals.add(tuitionTotal);
		categoryTotals.add(otherTotal);

		// Since we have all the values saved in an array, we can call the
		// method that will create the pie Chart.

		piechartGenerator(categoryTotals);
		br.close();
	}

	// This method will generate the actual graph. It will take a list of all
	// values as the parameter.
	public void piechartGenerator(List<Double> categoryTotals) {

		// Put all the elements from the list to this new array.
		Double[] values = categoryTotals.toArray(new Double[0]);

		// print to console test
		//for (Double value : categoryTotals)
		//	System.out.print(value + " ");

		PieChart.Data data[] = new PieChart.Data[5];

		// All the different categories
		String key[] = { "Bills", "Shopping", "Food", "Tuition", "Other" };

		// Loop through 5 times to add the key and value to the piechart data
		for (int i = 0; i < 5; i++) {
			data[i] = new PieChart.Data(key[i], values[i]);
		}

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(data);
		piechart.setData(pieChartData);
		piechart.setTitle("Spending:");

		// THis will show the values next each category
		pieChartData.forEach(datas -> datas.nameProperty()
				.bind(Bindings.concat(datas.getName(), " ", "$", datas.pieValueProperty())));

	}

	public void initialize() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
			time.setText(LocalDateTime.now().format(formatter));
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();
	}

	public void getName(String userid) {
		this.name.setText("Hello, " + userid + "!");
		this.userid = userid;
	}
}
