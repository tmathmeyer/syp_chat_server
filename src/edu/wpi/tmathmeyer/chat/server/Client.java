package edu.wpi.tmathmeyer.chat.server;

import java.net.Socket;
import java.io.*;

import edu.wpi.tmathmeyer.chat.server.group.Group;
import edu.wpi.tmathmeyer.protocol.Packet;
import edu.wpi.tmathmeyer.protocol.chat.CommandPacket;
import edu.wpi.tmathmeyer.protocol.chat.ControlPacket;
import edu.wpi.tmathmeyer.protocol.chat.LoginPacket;
import edu.wpi.tmathmeyer.protocol.chat.MessagePacket;

public class Client extends Thread{

	private String userName;
	
	private boolean authenticated;
	private boolean receiving=true;
	private byte clientID;
	
	private Socket myConnection;
	private DataInputStream in;
    private DataOutputStream out;
    
    private String messageColor="00AA00", usernameColor="000000";
    
    private Group currentGroup = Server.cm.getGroupByID((byte) 0x00);
    
    private long lastActivity=0;
    
    
	public Client(Socket socket) throws Exception{
		this.myConnection = socket;
		this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
		
        this.start();
	}
	
	
	
	
	
	
	
	public void run(){
		try{
			while(receiving){
				byte read = this.in.readByte();
				System.out.print(read + " ");
				
				if (read==0x00) //login
					this.login(new LoginPacket(in));
				if (read==0x10) //logout
					this.killMe();
				if (read==0x21) //register
					this.register(new LoginPacket(in));
				if (!authenticated) //control
					this.killMe();
				if (read==0x01) //message
					this.broadcastMessage(new MessagePacket(in));
				
				
				
				if (read==0x04)
					Server.se.operateCommand(new CommandPacket(in), this);
				if (read==0x05); //for later use
				
				
				lastActivity = System.nanoTime();
			}
		}
		catch(Exception e){
			this.killMe();
			receiving = false;
			System.out.println(this.getUserName() + " has disconnected");
			e.printStackTrace();
		}
	}
	
	
	public void broadcastMessage(MessagePacket m) throws IOException{
		m.setMessageHex(messageColor);
		m.setUserHex(usernameColor);
		this.getCurrentGroup().broadcast(m);
	}
	
	
	
	
	public void login(LoginPacket l){
		Server.se.login(this, l);
	}
	
	
	
	
	public void register(LoginPacket l){
		Server.se.register(this, l);
	}
	
	
	
	
	public void sendMessage(MessagePacket m) throws IOException{
		m.write(this.out);
	}
	
	
	
	
	public void denial(){
		this.sendPacket(new ControlPacket((byte) 0x00));
	}
	
	
	
	
	public void sendPacket(Packet p){
		try {
			p.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	public void killMe(){
		try {
			Server.cm.killClient(this);
			in.close();
			out.close();
			myConnection.close();
			this.setReceiving(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}






	/**
	 * @return the authenticated
	 */
	public boolean isAuthenticated() {
		return authenticated;
	}





	/**
	 * 
	 */
	public void authenticate() {
		this.authenticated = true;
		this.sendPacket(new ControlPacket((byte) 0x01));
	}





	/**
	 * @return the clientID
	 */
	public byte getClientID() {
		return clientID;
	}





	/**
	 * @param clientID the clientID to set
	 */
	public void setClientID(byte clientID) {
		this.clientID = clientID;
	}





	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}





	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}





	/**
	 * @return the receiving
	 */
	public boolean isReceiving() {
		return receiving;
	}





	/**
	 * @param receiving the receiving to set
	 */
	public void setReceiving(boolean receiving) {
		this.receiving = receiving;
	}







	/**
	 * @return the currentGroup
	 */
	public Group getCurrentGroup() {
		return currentGroup;
	}







	/**
	 * @param currentGroup the currentGroup to set
	 */
	public void setCurrentGroup(Group currentGroup) {
		this.currentGroup = currentGroup;
	}
	
	
	
	
	
	
	/**
	 * 
	 * @return
	 */
	public String getLastActivityTime(){
		long currentTime = System.nanoTime();
		long elapsedTime = currentTime-this.lastActivity;
		return "‡"+(elapsedTime*1000000<300?"C":"F");
	}







	/**
	 * @return the messageColor
	 */
	public String getMessageColor() {
		return messageColor;
	}







	/**
	 * @param messageColor the messageColor to set
	 */
	public void setMessageColor(String messageColor) {
		this.messageColor = messageColor;
	}







	/**
	 * @return the usernameColor
	 */
	public String getUsernameColor() {
		return usernameColor;
	}







	/**
	 * @param usernameColor the usernameColor to set
	 */
	public void setUsernameColor(String usernameColor) {
		this.usernameColor = usernameColor;
	}
}
