package server.api.mem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class UserFileManager {
	public static boolean availableUsername(String name){
		File f = new File(name+".chatUser");
		return !f.exists();
	}
	public static boolean login(String name, String password){
		System.out.println("login combo: "+name+"/"+password);
		File f = new File(name+".chatUser");
		try{
			Scanner reader = new Scanner(f);
			String onFile = reader.nextLine();
			if (onFile.equals(password)){
				return true;
			}
			else{
				System.out.println("the password on file was: "+ onFile+" yet we were provided with "+password);
				return false;
			}
		}
		catch(Exception e){
			System.out.println("that username was not found");
			return false;
		}
	}
	public static boolean register(String name, String password){
		try{
			File f = new File(name+".chatUser");
			f.createNewFile();
			FileWriter fstream = new FileWriter(name+".chatUser");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(password);
			out.close();
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	
	
	public static String getWelcomMessage(){
		File f = new File("welcome.file");
		String s;
		try{
			Scanner reader = new Scanner(f);
			s = reader.nextLine();
			return s;
		}
		catch(Exception e){
			return "The server admin is a ditz";
		}
	}
}
