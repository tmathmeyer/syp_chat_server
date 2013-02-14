package edu.wpi.tmathmeyer.chat.client;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import edu.wpi.tmathmeyer.chat.protocol.ControlPacket;
import edu.wpi.tmathmeyer.chat.protocol.Packet;

public class ConsoleClient implements Client{
	DataOutputStream writer;
	boolean authenticated = false;
	Socket s;
	
	public ConsoleClient(Socket s) throws Exception{
		this.writer = new DataOutputStream(s.getOutputStream());
		this.s = s;
		new Thread(this).start();
	}
	
	@Override
	public void login(String username, String password) throws Exception{
		writer.writeByte(0x00);
		writer.writeShort(username.length());
		writer.writeShort(password.length());
		writer.writeChars(username);
		writer.writeChars(password);
		writer.flush();
	}
	
	@Override
	public void sendMessage(String message) throws Exception {
		writer.writeByte(0x01);
		writer.writeByte(0x00);
		writer.writeShort(5);
		writer.writeShort(14);
		writer.writeShort(37);
		writer.writeShort(message.length());
		writer.flush();
		writer.writeChars(message);
		writer.flush();
		writer.writeShort(10);
		writer.writeChars("tmathmeyer");
		writer.flush();
		System.out.println("sent message: "+message);
	}
	
	@Override
	public void closeOutStream() throws Exception{
		writer.close();
    }
	
	
	
	
	
	
	@SuppressWarnings("resource")
	public static void main(String[] args){
		try{
			
			Socket s = new Socket("localhost",25566);
			ConsoleClient se = new ConsoleClient(s);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	
	

	@Override
	public void processPacket(Packet p) {
		if (p instanceof ControlPacket)System.out.println(this.authenticate(p));
		else System.out.println(p.toString());
	}


	@Override
	public byte getVersionID() {
		return 0x00;
	}


	@Override
	public boolean authenticate(Packet p) {
		if (!(p instanceof ControlPacket))return false;
		this.authenticated = ((ControlPacket)p).data == 0x01;
		return this.authenticated;
	}


	@Override
	public DataOutputStream getByteOutputStream() {
		return this.writer;
	}


	@Override
	public void startReciever(Reciever r) {
		new Thread(r).start();
	}


	@Override
	public Socket getSocket() {
		return this.s;
	}

	@Override
	public void run() {
		try{
			this.startReciever(new Reciever(this.getSocket(), this));
			this.login("tmathmeyer", "pass");
			Scanner s = new Scanner(System.in);
			
			while(true){
				while(authenticated){
					while(s.hasNext()){
						this.sendMessage(s.nextLine());
					}
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void print(byte b) {
		// TODO Auto-generated method stub
		
	}
}


