package persistence;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	//파일 읽어주는 메서드 빈 리스트 반환 null 반환 안하는게 목적
	public static <T> List<T> readObject(Path file) {
		try {
			if(file == null) {
				return new ArrayList<T>();
			}
			if(Files.notExists(file) || Files.size(file) == 0) {
				return new ArrayList<T>();
			}
			try(ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))){
				Object obj = ois.readObject();
				List<T> received = (List<T>) obj;
				System.out.println("파일 읽어짐");
				return (received != null) ? received : new ArrayList<T>();
			}
		}catch(EOFException | StreamCorruptedException e) {
			return new ArrayList<T>();
		}catch(IOException | ClassNotFoundException e) {
			return new ArrayList<T>();
		}
	}
	//파일 써주는 메서드
	public static <T> void writeObject(Path file, List <T> data){
		try {
			Path parent = file.getParent();
			Files.createDirectories(parent);
			if(data == null) {
				data = new ArrayList<T>();
			}
			try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))){
				oos.writeObject(data);
				oos.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
	
	
	
	
	
	
