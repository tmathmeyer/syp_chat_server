package server.api.group;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import protocol.MessagePacket;
import protocol.UsersPacket;

import server.api.Client;

public class Group implements Runnable{
	
	
	private ArrayList<Client> currentClients = new ArrayList<Client>();
	
	private String groupName;
	private boolean open = true;
	private byte ID;
	
	private HashMap<String, String> userperms;
	
	
	
	
	/**
	 * 
	 * @return the group ID
	 */
	public byte getID(){
		return ID;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Group(String name, String founder){
		this.groupName = name;
		this.userperms.put(founder, "A");
		
		try {
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(name+".gr"));
			userperms = (HashMap<String, String>) objIn.readObject();
			objIn.close();
		} catch (Exception e) {
			userperms = new HashMap<String, String>();
		}
	}
	
	
	
	/**
	 * The default constructor for Groups
	 */
	public Group(){
		this("HOME", "Admin");
	}
	
	
	
	/**
	 * 
	 * @param m the message to be be sent
	 * @throws IOException
	 */
	public void broadcast(MessagePacket m) throws IOException{
		ArrayList<Client> copyof = new ArrayList<Client>(this.currentClients);
		for(Client c : copyof)c.sendMessage(m);
	}
	
	
	/**
	 * 
	 * @param c the client being checked
	 * @return whether the client being checked can broadcast
	 */
	public boolean canBroadcast(Client c){
		String rank = this.userperms.get(c.getName());
		return rank.equals("A") || rank.equals("M") || rank.equals("U");
	}
	
	
	
	/**
	 * sends user information periodically
	 */
	public void run(){
		while(true){
			try{
				Thread.sleep(5000);
				this.sendUserInformation();
			}catch(Exception e){}
		}
	}


	/**
	 * this uses the rankings:
	 *  -A = Administrator
	 *  -M = Moderator
	 *  -U = user
	 *  -D = muted
	 *  -B = banned
	 * 
	 * 
	 * @param c the client whos permission is being modified
	 * @param perm the new permission to be applied
	 */
	public void changePerm(Client c, String perm){
		this.userperms.put(c.getName(), perm);
		if (perm.equals("B"))this.kick(c);
	}
	
	
	/**
	 * 
	 * @param c the client to be kicked from the room
	 */
	public void kick(Client c){
		currentClients.remove(c);
	}
	
	
	/**
	 * 
	 * @param c the client to be checked
	 * @return the permission of the client in the group
	 */
	public String getPerm(Client c){
		return this.userperms.get(c.getName());
	}
	
	
	/**
	 * 
	 * @param c the client to be added to the group
	 */
	public void addToGroup(Client c){
		if (open && !hasMember(c)){
			currentClients.add(c);
			this.changePerm(c, "U");
		}
	}
	
	
	
	/**
	 * 
	 * @param c the client to be checked
	 * @return whether the group has the client being checked
	 */
	public boolean hasMember(Client c){
		return currentClients.contains(c);
	}
	
	
	
	/**
	 * 
	 * @return the groupname
	 */
	public String getName(){
		return groupName;
	}
	
	
	
	
	private void sendUserInformation(){
		String[] names = new String[this.currentClients.size()];
		for(int i = 0; i < names.length; i++){
			Client c = this.currentClients.get(i);
			names[i] = "[" + this.userperms.get(c) + "]" + c.getUserName();
		}
		for(Client c : this.currentClients){
			c.sendPacket(new UsersPacket(names, this.ID));
		}
	}
	
	
	/**
	 * 
	 * @throws IOException
	 */
	private void writeToFile() throws IOException{
		FileOutputStream fos = new FileOutputStream(this.getName()+".gr");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this.userperms);
		oos.close();
	}
}
