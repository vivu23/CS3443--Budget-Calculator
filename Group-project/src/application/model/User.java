package application.model;

import java.util.HashMap;

import java.io.*;
import java.util.*;


public class User {
	
	HashMap<String, String> list; 
	
	/*
	 * public boolean checkUserNameAuthentication(String)
	 * 
	 * input: a String
	 * output: a Boolean
	 * 
	 * This method is use to check if the user input a right userName or not.
	 * The userName needs to follow a format abc123.
	 * 
	 */
	public boolean checkUserNameAuthentication(String name) {
		String regex = "[A-Za-z]{3}[0-9]{3}"; //the format of the userid
		if(!name.matches(regex)) {
			return false;
		}
		return true;
	}
	
	/*
	 * public boolean addUsers(String, String)
	 * input: two strings
	 * output: a boolean
	 * 
	 * this method will take userName and password, check if the userName is already existed or 
	 * not, then write those information to a users.txt file.
	 * 
	 * return true -> if userName is not repeated and successfully printed to users.txt
	 * return false -> if userName already existed.
	 * 
	 */
	public boolean addUsers(String name, String password) {
		
		File file = new File("Group-project/files/users.txt");
		String line ="";
		String parts[];
		Scanner scanner;
		
		//if the file is empty then just write in it
		if(file.length() == 0) { 
			try {
				FileWriter w = new FileWriter(file, true);
				w.write(name + " ");
				w.write(password + "\n");
				w.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else { //if the file is not empty
			try {
				scanner = new Scanner(file);
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
					parts = line.split(" ");
					if(name.equals(parts[0])) { //if the user is already in the list
						return false;
					}
				}
				scanner.close();
				try {
					FileWriter w = new FileWriter(file, true);
					w.write(name + " ");
					w.write(password + "\n");
					w.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}		
		return true;
	}
	
	/*
	 * public void setData()
	 * input: None
	 * output: None
	 * 
	 * This method is used to take the logIn information and put it in the HashMap.
	 * 
	 */
	public void setData() {
		list = new HashMap<String, String>();
		File file = new File("Group-project/files/users.txt");
		String line="";
		String[] parts;
		Scanner sc;
			try {
				sc = new Scanner(file);
				while(sc.hasNextLine()) {
					line = sc.nextLine();
					parts = line.split(" ");
					list.put(parts[0], parts[1]);
				}
				sc.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	/*
	 * public boolean checkPWAuthentication (String)
	 * input: String
	 * output: boolean
	 * 
	 * This method will check the user password if is match the correct format or not
	 * return true -> match
	 * return false -> not match
	 * 
	 */
	public boolean checkPWAuthentication(String password) {
		String regex = "[A-Za-z]+[0-9]+";
		if(password.matches(regex) && password.length() >=6) {
			return true;
		}
		return false;
	}
	
	/*
	 * public boolean checkConfirmPassWord(String, String)
	 * input: String, String
	 * output: boolean
	 * 
	 * This method is used to check if the confirm password match the password in the SignUp scene
	 * return true -> match
	 * return false -> not match 
	 * 
	 */
	public boolean checkConfirmPassWord(String password, String confirmPassword) {
		if(confirmPassword.equals(password)) {
			return true;
		}
		return false;
	}
	
	/*
	 * public boolean checkLogInName(String)
	 * input: String
	 * output: boolean
	 * 
	 * This method will check if the userName match with the LogIn information or not
	 * return true -> match
	 * return false -> not match
	 * 
	 */
	public boolean checkLogInName(String userid) {
		if(list.containsKey(userid)) {
			return true;
		}
		return false;
	}
	
	/*
	 * public boolean checkPassword(String, String)
	 * input: String, String
	 * output: boolean
	 * 
	 * This method is will take the userName then look for the password from the LogIn info then 
	 * compare with the password that the user put in the password box in SignIn page
	 * 
	 */
	public boolean checkPasswword(String userid, String password) {
			if(password.equals(list.get(userid))){
				return true;
		}
		return false;
	}
}
