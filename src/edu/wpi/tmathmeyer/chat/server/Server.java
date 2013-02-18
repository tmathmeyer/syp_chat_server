package edu.wpi.tmathmeyer.chat.server;

import java.util.HashMap;
import java.util.Scanner;

import edu.wpi.tmathmeyer.chat.protocol.CommandPacket;
import edu.wpi.tmathmeyer.chat.protocol.LoginPacket;
import edu.wpi.tmathmeyer.chat.server.command.CommandOperator;
import edu.wpi.tmathmeyer.chat.server.command.Commands;
import edu.wpi.tmathmeyer.chat.server.mem.UserFileManager;

public class Server implements Runnable{
    public static ClientManager cm;
    public static Server se;
    
    private String name;
    private byte lastID;
    
    private int port;
    
    HashMap<String, CommandOperator> COs = new HashMap<String, CommandOperator>();
    
    public Server(int port, String serverName) throws Exception {
    	name = serverName;
    	this.port = port;
        cm = new ClientManager(port);
        se=this;
        System.out.println("server up!");
        new Commands();
        new Thread(this).start();
    }
    
	public String getName(){
    	return name;
    }
	
	public int getNumberOfUsers(){
		return cm.getNumberOfUsers();
	}
	
	public int getNumberOfChannels(){
		return cm.getGroups().size();
	}
	
	public String getServerInformation(){
		return cm.getSocket().getLocalSocketAddress().toString();
	}
	
	public void login(Client c, LoginPacket l){
		System.out.println("a user attempted to log in with username: "+l.getUsername());
		if (UserFileManager.login(l.getUsername(), l.getPassword())){
			c.setUserName(l.getUsername());
			c.setClientID(this.lastID++);
			c.authenticate();
		}
		else{
			c.denial();
			System.out.println("the attempt was unsuccessful");
		}
	}
	
	public static void main(String[] args){
		try{new Server(25566, "fail");}
		catch(Exception e){e.printStackTrace();}
	}

	public void register(Client client, LoginPacket l) {
		System.out.println("a user attempted to register with username: "+l.getUsername());
		if(UserFileManager.register(l.getUsername(), l.getPassword())){
			System.out.println("the attemp was successful. logging them in now");
			this.login(client, l);
		}
		else{
			System.out.println("the attempt was unsuccessful");
		}
	}

	@Override
	public void run() {
		Scanner reader = new Scanner(System.in);
		while(reader.hasNext()){
			try{
				String cmd = reader.nextLine();
				if (cmd.equals("stop")){
					cm.kill();
					System.exit(0);
				}
				else if (cmd.equals("restart")){
					cm.saveAll();
					cm.kill();
					cm = null;
					cm = new ClientManager(this.port);
				}
				else if (cmd.equals("save data")){
					cm.saveAll();
				}
				else{
					System.out.println("Sorry, I dont recognize that command");
				}
			}
			catch(Exception e){}
			
		}
		reader.close();
	}
	
	
	
	public void operateCommand(CommandPacket cp, Client client){
		System.out.println(client.getName()+" has sent the command: "+cp.getCommand());
		CommandOperator c = this.COs.get(cp.getCommand().toLowerCase());
		if (c!=null)c.parseCommand(client, cp.getArgs());
		else{
			System.out.println("the available comands are:");
			for(String s : this.COs.keySet())
				System.out.println("   "+s);
		}
	}
	
	
	public void addCommandOperator(CommandOperator o, String command){
		this.COs.put(command, o);
	}
	
	public void addUserToGroup(Client c, String gn){
		cm.getGroupByName(gn).addToGroup(c);
	}
	
	public void remUserFromGroup(Client c, String gn){
		cm.getGroupByName(gn).kick(c);
	}
}