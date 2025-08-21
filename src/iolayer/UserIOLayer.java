package iolayer;

import java.util.Scanner;

public class UserIOLayer {

    private Scanner scanner;

    public UserIOLayer(Scanner scanner) {
        this.scanner = scanner;
    }
    
    
    
    public String readLine() {
    	return scanner.nextLine();
    }
    
   
    
    
    
    
    public void createUser() {
    	System.out.println("입력하세요");
    	String name = readLine();
    	
    }
    
    
    
    
    
    
}
