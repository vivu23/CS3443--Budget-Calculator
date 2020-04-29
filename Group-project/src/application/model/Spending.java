package application.model;

import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Spending {	
	
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
}