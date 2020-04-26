package application.model;

import java.util.*;
import java.io.*;

public class Spending {	
	
	public void addSpendingInfo(String userid, String localDate, String spendingType, int money){
		
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
	
	
}
