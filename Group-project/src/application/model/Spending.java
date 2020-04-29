package application.model;

import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class Spending {	
	
	private double extra = 0.0;
	
	private NumberFormat formatter = NumberFormat.getCurrencyInstance();

	
	/*
	 * public void addSpendingInfo(String, String, String, Double)
	 * input: 3 String and 1 Double
	 * output: None
	 * 
	 * This method will create a file associates with the userName and store all of the
	 * Spending information
	 * 
	 */
	public void addSpendingInfo(String userid, String localDate, String spendingType, Double money){
		
		String fileName = new String("Group-project/files/" + userid + ".txt");
		File file = new File(fileName);
		
		try {
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(localDate + " " + spendingType + " " + money +  "\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * public boolean checkForDate(LocalDate)
	 * input: LocalDate
	 * output: boolean
	 * 
	 * This method will check if the input date is in the future.
	 * If is in the future -> return false
	 * If is not -> return true
	 */
	public boolean checkForDate(LocalDate localDate) {
		LocalDate curDate = LocalDate.now();
		if(localDate.compareTo(curDate) > 0) {
			return false;
		}
		return true;
	}
	
	/*
	 * public boolean checkforNum(String)
	 * input: String
	 * output: a boolean
	 * 
	 * This method will check if the input for money is double or not
	 * if is not -> return false
	 * if is double -> return true
	 * 
	 */
	public boolean checkforNum(String money) {
		Double temp;
		
		try {
			temp = Double.parseDouble(money);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	/*
	 * public ArrayList<Double> setUpList(int, String)
	 * input: a integer, a string
	 * output: an ArrayList<Double>
	 * 
	 * This Method is in charge of pulling all the data necessary for calculating the
	 * user Spending/Budget
	 * The parameter "days" represents the amount of days that will be
	 * subtracted from today's current date.
	 * 
	 */
	public ArrayList<Double> setUpList(int days, String userid) throws Exception {
		ArrayList<Double> categoryTotals = new ArrayList<Double>();
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
		File file = new File("Group-project/files/" + userid + ".txt");
		Scanner sc = new Scanner(file);
		String line="";
		String[] parts;

		
		// loop through the file and use and blank space as delimiter
		while (sc.hasNextLine()){
			line = sc.nextLine();
			parts = line.split(" ");
			String dateInString = parts[0];
			Date usrDate = formatter.parse(dateInString);	
				if (!usrDate.before(weekAgoDate)) 
					if (parts[1].equals("Tuition")) {
						tuitionTotal += Double.parseDouble(parts[2]);
					}
					if (parts[1].equals("Bills")) {
						BillsTotal += Double.parseDouble(parts[2]);
					}
					if (parts[1].equals("Shopping")) {
						shoppingTotal += Double.parseDouble(parts[2]);
					}
					if (parts[1].equals("Food")) {
						foodTotal += Double.parseDouble(parts[2]);
					}
					if (parts[1].equals("Other")) {
						otherTotal += Double.parseDouble(parts[2]);
					}
				}	
		sc.close();
		categoryTotals.add(BillsTotal);
		categoryTotals.add(shoppingTotal);
		categoryTotals.add(foodTotal);
		categoryTotals.add(tuitionTotal);
		categoryTotals.add(otherTotal);
		
		return categoryTotals;
	}
	
	/*
	 * public String printMessage(String, ArrayList<Double>)
	 * input: a String, an ArrayList<Double>
	 * output: a String
	 * 
	 * This method is use to calculate how much the user spends for each category in 
	 * a month and how much he/she has extra each month. 
	 * Then return a String that list all of the spendings for each month.
	 * 
	 */
	public String printMessage(String incomeAmount, ArrayList<Double> catTotals) {
		String str ="";    	
		double total = 0.0;
		double BillsMonthlyTotal = catTotals.get(0) / 12;
		double shoppingMonthlyTotal = catTotals.get(1) / 12;
		double foodMonthlyTotal = catTotals.get(2) / 12;
		double tuitionMonthlyTotal = catTotals.get(3) / 12;
		double otherMonthlyTotal = catTotals.get(4) / 12;
		
		for (int i = 0; i < 5; i++) {
			total += catTotals.get(i);
		}
		
		extra = (Double.parseDouble(incomeAmount) - total) / 12;

		str = "Your annual income: " + formatter.format(Double.parseDouble(incomeAmount)) 
		+ "\nYour monthly spenditures:\nTuition: " + formatter.format(tuitionMonthlyTotal) 
		+ "/ month\nBills: " + formatter.format(BillsMonthlyTotal) 
		+ "/ month\nFood: " + formatter.format(foodMonthlyTotal) + "/ month\nShopping: " 
		+ formatter.format(shoppingMonthlyTotal) + "/ month\nOther: " 
		+ formatter.format(otherMonthlyTotal) + "/ month\n\nYou have: " 
		+ formatter.format(extra) + " to yourself each month!";
		
		return str;
	}
	
	/*
	 * public double getExtra()
	 * input: None
	 * output: a double
	 *  
	 */
	public double getExtra() {
		return extra;
	}

}