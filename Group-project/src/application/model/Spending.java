package application.model;

import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Spending {	
	
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
	
	public boolean checkForDate(LocalDate localDate) {
		LocalDate curDate = LocalDate.now();
		if(localDate.compareTo(curDate) > 0) {
			return false;
		}
		return true;
	}
	
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