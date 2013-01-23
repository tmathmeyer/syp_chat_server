package server.api.mem;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import server.api.Group;

public class RoomFileManager {
	public static void saveInstanceOfGroup(Group g){
		String groupName = g.getName();
		new File(groupName).mkdir();
		try{
			File index = new File(groupName+"/index.gr");
			if(!index.exists())index.createNewFile();
			
			FileWriter indexStream = new FileWriter(groupName+"/index.gr",true);
			BufferedWriter indexWriter = new BufferedWriter(indexStream);
			indexWriter.write("name="+g.getName());
			indexWriter.write("\nmod-count="+g.getMods().size());
			indexWriter.write("\nmuted-count="+g.getMuted().size());
			indexWriter.write("\nexecutive-name="+g.getExecutive());
			indexWriter.close();
			
			
			
			
			File mods = new File(groupName+"/mods.gr");
			mods.createNewFile();
			
			FileWriter modsStream = new FileWriter(groupName+"/mods.gr",true);
			BufferedWriter modWriter = new BufferedWriter(modsStream);
			for(int i = 0; i < g.getMods().size(); i++){
				modWriter.write(g.getMods().get(i));
				if (i!=0)modWriter.write("\n");
			}
			modWriter.close();
			
			
			
			
			File muted = new File(groupName+"/muted.gr");
			muted.createNewFile();
			
			FileWriter mutedStream = new FileWriter(groupName+"/muted.gr",true);
			BufferedWriter mutedWriter = new BufferedWriter(mutedStream);
			for(int i = 0; i < g.getMuted().size(); i++){
				mutedWriter.write(g.getMuted().get(i));
				if (i!=0)mutedWriter.write("\n");
			}
			mutedWriter.close();
		}
		catch(Exception e){
			try{
				FileWriter errorStream = new FileWriter("operation.error",true);
				BufferedWriter errorWriter = new BufferedWriter(errorStream);
				errorWriter.append(e.toString());
				errorWriter.close();
			}
			catch(Exception e2){}
		}
	}
	
	
	
	
	
	
	
	public static ArrayList<String> readMods(String groupName){
		try{
			if (!new File(groupName).exists())return null;
			ArrayList<String> res = new ArrayList<String>();
			Scanner reader = new Scanner(new File(groupName+"/mods.gr"));
			while(reader.hasNextLine())res.add(reader.nextLine());
			return res;
		}
		catch(Exception e){return null;}
	}


	public static ArrayList<String> readMuted(String groupName){
		try{
			if (!new File(groupName).exists())return null;
			ArrayList<String> res = new ArrayList<String>();
			Scanner reader = new Scanner(new File(groupName+"/muted.gr"));
			while(reader.hasNextLine())res.add(reader.nextLine());
			return res;
		}
		catch(Exception e){return null;}
	}
	
	public static String readExec(String groupName){
		try{
			if (!new File(groupName).exists())return null;
			Scanner reader = new Scanner(new File(groupName+"/muted.gr"));
			reader.nextLine(); // name
			reader.nextLine(); // count1
			reader.nextLine(); // count 2
			return reader.nextLine();
		}
		catch(Exception e){return null;}
	}
}
