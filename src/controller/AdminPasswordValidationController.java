package controller;

import java.io.IOException;

import service.AdminPasswordService;

public class AdminPasswordValidationController {
	
	AdminPasswordService aps;
	AdminPasswordValidationController(AdminPasswordService aps){
		this.aps = aps;
	}
	
	public String getPassword(String currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public String setPassword(String currentPassword, String newPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public String initializePassword(String currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
	public String checkManagerByPassword(String currentPassword) throws IOException {
		return aps.getPassword(currentPassword);
	}
}
