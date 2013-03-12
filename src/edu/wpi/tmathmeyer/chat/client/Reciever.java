package edu.wpi.tmathmeyer.chat.client;

import java.io.IOException;
import java.net.Socket;
import edu.wpi.tmathmeyer.protocol.DataReciever;
import edu.wpi.tmathmeyer.protocol.Packet;

public class Reciever extends DataReciever{
	public Reciever(Socket socket, Client c, Packet[] pkts) throws IOException{
		super(socket, c, pkts);
	}
}