package edu.wpi.tmathmeyer.chat.server.options;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Scanner;

public class Prefs {

	HashMap<String, String> map = new HashMap<String, String>();


	public Prefs(String prefLoc) throws Exception{
		this.update(prefLoc);
	}

	public void makePrefs(String prefLoc){
		try{
			FileWriter fstream = new FileWriter(prefLoc);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("host = NaN\n");
			out.write("port = 0\n");
			out.write("name = BukkitServer\n");
			out.close();
			this.update(prefLoc);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public String getPref(String name){
		return this.map.get(name);
	}




	public void update(String prefLoc) throws Exception{
		try{
			File f = new File(prefLoc);
			Scanner reader = new Scanner(f);
			while(reader.hasNext()){
				String line = reader.nextLine();
				if (line.contains(" = ")){
					String[] inf = line.split(" = ");
					map.put(inf[0], inf[1]);
				}
			}
			reader.close();
		}
		catch(Exception e){
			e.printStackTrace();
			System.err.println("there was no preferences file found! making default preferences file now.");
			this.makePrefs(prefLoc);
			throw e;
		}
	}
}