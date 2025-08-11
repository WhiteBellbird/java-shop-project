package repository;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import domain.User;

public class FileManager {
	//파일 읽어주는 메서드
	public static <T> List<T> readObject(Path file) {
		try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))){
			Object obj = ois.readObject();
			List<T> received = (List<T>) obj;
			System.out.println("파일 읽어짐");
			return received;
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	//파일 써주는 메서드
	public static <T> void writeObject(Path file, List <T> data){
		try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))){
			// null 체크
			if(data == null) {
				System.err.println("[FileManager] 저장할 데이터가 null입니다.");
				return;
			}
			oos.writeObject(data);
			oos.flush();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
