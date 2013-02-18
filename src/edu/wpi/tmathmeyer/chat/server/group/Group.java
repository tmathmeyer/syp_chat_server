package edu.wpi.tmathmeyer.chat.server.group;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import edu.wpi.tmathmeyer.chat.protocol.MessagePacket;
import edu.wpi.tmathmeyer.chat.protocol.UsersPacket;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

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
	
	/**
	 * 
	 * @param ID the group's ID
	 */
	public void setID(byte ID){
		this.ID = ID;
	}
	
	/**
	 * 
	 * @param closer the client attempting to close the group
	 */
	public void closeGroup(Client closer){
		if (this.isAdminOrMod(closer.getUserName()))this.open = false;
	}
	
	/**
	 * 
	 * @param closer the client attempting to open the group
	 */
	public void openGroup(Client closer){
		if (this.isAdminOrMod(closer.getUserName()))this.open = true;
	}
	
	/**
	 * 
	 * @param client the client
	 * @return whether the client is an admin or a moderator
	 */
	public boolean isAdminOrMod(String client){
		return this.userperms.get(client).equals("A") || this.userperms.get(client).equals("M");
	}
	
	/**
	 * 
	 * @param name the name of the group
	 * @param founder the name of the founder of the group
	 */
	@SuppressWarnings("unchecked")
	public Group(String name, String founder){
		try {
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(name+".gr"));
			userperms = (HashMap<String, String>) objIn.readObject();
			objIn.close();
		} catch (Exception e) {
			userperms = new HashMap<String, String>();
		}
		
		this.groupName = name;
		this.userperms.put(founder, "A");
		this.userperms.put("Admin", "A");
		new Thread(this).start();
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
				Server.cm.sendGroupInformation();
			}catch(Exception e){}
		}
	}
	
	/**
	 * this uses the rankings:
	 *  ‡A = Administrator
	 *  ‡M = Moderator
	 *  ‡U = user
	 *  ‡D = muted
	 *  ‡B = banned
	 *  
	 *  NOTE THESE ARE NOT PERMISSIONS!
	 *  ‡F = afk
	 *  ‡C = current (not AFK)
	 * 
	 * 
	 * @param c the client who's permission is being modified
	 * @param perm the new permission to be applied
	 */
	public void changePerm(Client c, String perm){
		this.userperms.put(c.getName(), perm);
		if (perm.equals("B"))this.kick(c);
	}
	
	/**
	 * 
	 * @param name the name of the user
	 * @param perm the new permission for the user
	 */
	private void changePerm(String name, String perm){
		this.userperms.put(name, perm);
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
	public boolean addToGroup(Client c){
		if (open && !hasMember(c)){
			currentClients.add(c);
			if (this.userperms.get(c.getName())==null)this.changePerm(c, "U");
			return true;
		}
		else if (!hasMember(c)) {
			String inv = this.getPerm(c);
			if (inv.equals("B") || inv == null);
			else{
				currentClients.add(c);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param clientname the name of the client to be invited (only mods and admins can do this)
	 */
	public void invite(String clientname){
		if (this.userperms.get(clientname) == null)this.changePerm(clientname, "U");
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
	
	/**
	 * sends the information about who is in the group to each member.
	 */
	private void sendUserInformation(){
		String[] names = new String[this.currentClients.size()];
		System.out.println(this.getName());
		for(int i = 0; i < names.length; i++){
			Client c = this.currentClients.get(i);
			names[i] = "[‡" + this.getPerm(c) + "]" + c.getUserName()+"["+c.getLastActivityTime()+"]";
			System.out.println(c.getUserName());
		}
		for(Client c : this.currentClients){
			c.sendPacket(new UsersPacket(names, this.ID));
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void writeToFile() throws IOException{
		String fn = this.getName()+".gr";
		File f = new File(fn);
		if (! f.exists()) f.createNewFile();
		FileOutputStream fos = new FileOutputStream(fn);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(this.userperms);
		oos.close();
	}
}
