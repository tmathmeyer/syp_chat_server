package server.api;

import java.io.IOException;
import java.util.ArrayList;

import protocol.MessagePacket;
import protocol.UsersPacket;

import server.api.mem.RoomFileManager;

public class Group implements Runnable{
	private ArrayList<Client> clients = new ArrayList<Client>();
	private String groupName;
	private boolean open = true;
	private byte ID;
	
	private ArrayList<String> mutedUserNames = new ArrayList<String>();
	private ArrayList<String> modUserName = new ArrayList<String>();
	private String executiveUserName = "";
	
	
	public Group(String name, Client c1, Client c2, boolean open){
		this(RoomFileManager.readMuted(name), RoomFileManager.readMods(name), RoomFileManager.readExec(name));
		this.groupName = name;
		if (c1!=null)clients.add(c1);
		if (c2!=null)clients.add(c2);
		if (c1!=null)
			this.executiveUserName = c1.getUserName();
		else
			this.executiveUserName = "admin";
		this.open = open;
		new Thread(this).start();
	}
	
	private Group(ArrayList<String> mutedUserNames, ArrayList<String> modUserName, String executiveUserName){
		if(executiveUserName!=null)this.executiveUserName = executiveUserName;
		if(modUserName!=null)this.modUserName = modUserName;
		if(mutedUserNames!=null)this.mutedUserNames = mutedUserNames;
	}
	
	public byte getID(){
		return ID;
	}
	
	public Group(String name){
		this(name, null, null, true);
	}
	
	public Group(){
		this("#HOME", null, null, true);
	}
	public void broadcast(MessagePacket m) throws IOException{
		ArrayList<Client> copyof = new ArrayList<Client>(this.clients);
		for(Client c : copyof)c.sendMessage(m);
	}
	
	public void updateUsers(){
		
	}
	
	
	public void run(){
		while(true){
			try{
				Thread.sleep(5000);
				this.sendUserInformation();
			}
			catch(Exception e){
				
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void kick(Client c){
		clients.remove(c);
	}
	
	public void addToGroup(Client c){
		if (open && !hasMember(c))
			clients.add(c);
	}
	
	public boolean hasMember(Client c){
		return clients.contains(c);
	}
	
	public String getName(){
		return groupName;
	}
	
	public void kill(){
		if (open)RoomFileManager.saveInstanceOfGroup(this);
	}
	
	public void setExecutive(String name){
		this.executiveUserName = name;
	}
	
	public String getExecutive() {
		return this.executiveUserName!=null ? this.executiveUserName : "mdacheksum1-34-34"+this.toString();
	}
	
	public ArrayList<String> getMuted() {
		return this.mutedUserNames!=null ? this.mutedUserNames : new ArrayList<String>();
	}
	
	public ArrayList<String> getMods() {
		return this.modUserName!=null ? this.modUserName : new ArrayList<String>();
	}

	public void removeUser(Client c) {
		this.kick(c);
	}
	
	public void sendUserInformation(){
		String[] names = new String[this.clients.size()];
		for(int i = 0; i < names.length; i++)names[i] = this.clients.get(i).getUserName();
		for(Client c : this.clients){
			c.sendPacket(new UsersPacket(names, this.ID));
		}
	}
}
