package server.api;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import protocol.MessageGroupListPacket;
import protocol.MessagePacket;

public class ClientManager implements Runnable{
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ServerSocket serverSocket;
	private int logCount = 0;
	private ArrayList<Group> groups = new ArrayList<Group>();
	
	
	
	
	
	public ClientManager(int port) throws Exception{
		serverSocket = new ServerSocket(port, 500);
		Thread me = new Thread(this);
		me.start();
		groups.add(new Group());	
	}
	
	
	public synchronized void broadcastMessage(MessagePacket m) throws IOException{
		Group g = this.getGroupByID(m.getMessageGroup());
		if (g==null){
			System.out.println("well shit, that group is unknown");
			return;
		}
		g.broadcast(m);
	}

	
	public Group getGroupByID(byte ID){
		for(Group g : groups) if (g.getID()==ID)return g;
		return null;
	}
	
	
	public String getNewUsername(){
		return "Client"+(++logCount);
	}
	
	
	
	public void run() {
        while(true) {
            try {
                Socket socket = serverSocket.accept();
                Client bob = new Client(socket);
                clients.add(bob);
                // groups.get(0).kdjaksjdlaksjdlaksjd ADD THEM TO HOME DAMNIT;
            }
            catch(Exception e){
            	e.printStackTrace();
            }
        } 
    }
	
	
	public void createPrivateMessageGroup(Client a, Client b){
		
	}
	
	public Client getClientByName(String name){
		for (Client c : clients)
			if (c.getUserName().equals(name))return c;
		return null;
	}
	
	
	public boolean validName(String s){
		for (Client c : clients)
			if (c.getName().equals(s))return false;
		return true;
	}
	
	
	public synchronized void killClient(Client c) {
		if (this.clients.remove(c)){
			for(Group g : this.groups){
				g.kick(c);
			}
		}
    }
	
	
	public ArrayList<Group> getGroups(){
		return groups;
	}
	public int getNumberOfUsers(){
		return this.clients.size();
	}
	public ServerSocket getSocket(){
		return this.serverSocket;
	}
	
	
	
	
	public Group getGroupByName(String name){
		for(Group g : groups) if (g.getName()==name)return g;
		return null;
	}
	
	public Group makeGroup(String gn){
		Group g = new Group(gn);
		
		return g;
	}
	
	public void sendGroupnformation(){
		String[] names = new String[this.groups.size()];
		byte[] ids = new byte[this.groups.size()];
		
		for(int i = 0; i < names.length; i++){
			names[i] = this.groups.get(i).getName();
			ids[i] = this.groups.get(i).getID();
		}
		for(Client c : this.clients){
			c.sendPacket(new MessageGroupListPacket(ids, names));
		}
	}
}
