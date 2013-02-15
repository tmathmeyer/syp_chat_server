package edu.wpi.tmathmeyer.chat.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import edu.wpi.tmathmeyer.chat.protocol.ControlPacket;
import edu.wpi.tmathmeyer.chat.protocol.MessageGroupListPacket;
import edu.wpi.tmathmeyer.chat.protocol.MessagePacket;
import edu.wpi.tmathmeyer.chat.protocol.UsersPacket;
import edu.wpi.tmathmeyer.chat.protocol.Packet;

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
				c.print(read);
				Packet p = null;
				if (read==0x01)
					p = new MessagePacket(in);
				if (read==0x09)
					p = new UsersPacket(in);
				if (read==0x15)
					p = new ControlPacket(in);
				if (read==0x03)
					p = new MessageGroupListPacket(in);
				
				
				this.c.processPacket(p);
				
			}
		}
		catch(Exception e){
			receiving = true;
		}
	}
	
	
	
	
}