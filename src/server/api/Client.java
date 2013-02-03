package server.api;

import java.net.Socket;
import java.io.*;

import protocol.CommandPacket;
import protocol.ControlPacket;
import protocol.LoginPacket;
import protocol.MessagePacket;
import protocol.Packet;

public class Client extends Thread{

	private String userName;
	
	private boolean authenticated;
	private boolean receiving=true;
	private byte clientID;
	
	private Socket myConnection;
	private DataInputStream in;
    private DataOutputStream out;
    
    
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
				
			}
		}
		catch(Exception e){
			this.killMe();
			receiving = false;
			System.out.println(this.getUserName() + " has disconnected");
		}
	}
	
	
	public void broadcastMessage(MessagePacket m) throws IOException{
		Server.cm.broadcastMessage(m);
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
		} catch (IOException e) {}
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
}
