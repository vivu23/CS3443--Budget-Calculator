package application.model;

import java.util.HashMap;

import java.io.*;
import java.util.*;


public class User {
	
	HashMap<String, String> list;
	
	public boolean checkUserNameAuthentication(String name) {
		String regex = "[A-Za-z]{3}[0-9]{3}"; //the format of the userid
		if(!name.matches(regex)) {
			return false;
		}
		return true;
	}
	
	public boolean addUsers(String name, String password) {
		
		File file = new File("files/users.txt");
		String line ="";
		String parts[];
		Scanner scanner;
		
			if(file.length() == 0) { //if the file is empty
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
						else { // if the user is not in the list
							try {
								FileWriter w = new FileWriter(file, true);
								w.write(name + " ");
								w.write(password + "\n");
								w.close();
								break;
							} catch (IOException e) {
								e.printStackTrace();
							} 					    
						}
					}
					scanner.close();
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		
		return true;
	}
	
	public void setData() {
		list = new HashMap<String, String>();
		File file = new File("files/users.txt");
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
	
	public boolean checkPWAuthentication(String password) {
		String regex = "[A-Za-z]+[0-9]+";
		if(password.matches(regex) && password.length() >=6) {
			return true;
		}
		return false;
	}
	
	public boolean checkConfirmPassWord(String password, String confirmPassword) {
		if(confirmPassword.equals(password)) {
			return true;
		}
		return false;
	}
	
	
	public boolean checkLogInName(String userid) {
		if(list.containsKey(userid)) {
			return true;
		}
		return false;
	}
	
	
	public boolean checkPasswword(String userid, String password) {
			if(password.equals(list.get(userid))){
				return true;
		}
		return false;
	}

}
