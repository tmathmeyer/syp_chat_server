package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

import protocol.ControlPacket;
import protocol.MessagePacket;
import protocol.UsersPacket;
import protocol.Packet;

public class Reciever implements Runnable{
	
	private DataInputStream in;
	private boolean receiving = true;
	Client c;
	
	
	public Reciever(Socket socket, Client c) throws IOException{
		this.in = new DataInputStream(socket.getInputStream());
		this.c = c;
	}
	
	
	
	@Override
	public void run() {
		try{
			while(receiving){
				byte read = this.in.readByte();
				Packet p = null;
				if (read==0x01) //message
					p = new MessagePacket(in);
				if (read==0x09) //list
					p = new UsersPacket(in);
				if (read==0x15){
					p = new ControlPacket(in);
				}
				
				this.c.processPacket(p);
				
			}
		}
		catch(Exception e){
			receiving = true;
		}
	}
	
	
	
	
}