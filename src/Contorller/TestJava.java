package Contorller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TestJava {
	public static Vector<String> readFile() {
//		String path = "war\\WEB-INF\\lib\\projectData";
		String fileName = "category.txt";
		
		Vector<String> cat = new Vector<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				cat.add(line.toLowerCase().trim());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cat;
	}
	
	public static void main(String[] args) {
	
		Vector<String> cat = new Vector<String>();
		cat = readFile();
		for (int i = 0; i < cat.size(); i++) {
			System.out.println(cat.get(i));
		}
		
	}
}
