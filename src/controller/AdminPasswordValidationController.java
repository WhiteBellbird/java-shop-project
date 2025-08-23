package controller;

import java.io.IOException;
import java.util.ArrayList;

import service.AdminPasswordService;

public class AdminPasswordValidationController {
	
	AdminPasswordService aps;
	
	public AdminPasswordValidationController(AdminPasswordService aps){
		this.aps = aps;
	}
	
	public ArrayList<String> getPassword(ArrayList<String> currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public ArrayList<String> setPassword(ArrayList<String> currentPassword, ArrayList<String> newPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public ArrayList<String> initializePassword(ArrayList<String> currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public ArrayList<String> checkManagerByPassword(ArrayList<String> currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
}
