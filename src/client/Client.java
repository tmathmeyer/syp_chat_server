package client;

import java.io.DataOutputStream;
import java.net.Socket;

import protocol.Packet;

public interface Client extends Runnable{
	
	/**
	 * 
	 * @param p the incoming packet
	 * @throws Exception
	 */
	public void processPacket(Packet p) throws Exception;
	
	
	/**
	 * 
	 * @return the version of the client (for server use, not yet implemented)
	 */
	public byte getVersionID();
	
	
	/**
	 * 
	 * @param p the packet that tells the client whether it has been authenticated or denied
	 * @return whether the client has been authenticated
	 * @throws Exception all methods that involve packets in their parameters or returns throw exceptions
	 */
	public boolean authenticate(Packet p) throws Exception;
	
	
	/**
	 * 
	 * @param username the provided username
	 * @param password the provided password
	 * @throws Exception all methods that involve packets in their parameters or returns throw exceptions
	 */
	public void login(String username, String password) throws Exception;
	
	
	/**
	 * 
	 * @param message the provided message
	 * @throws Exception all methods that involve packets in their parameters or returns throw exceptions
	 */
	public void sendMessage(String message) throws Exception;
	
	
	/**
	 * 
	 * @return the output stream provided by the socket
	 */
	public DataOutputStream getByteOutputStream();
	
	
	/**
	 * 
	 * @param r the reciever to be started
	 */
	public void startReciever(Reciever r);
	
	
	/**
	 * 
	 * @return the socket currently in use
	 */
	public Socket getSocket();
	
	
	/**
	 * 
	 * @throws Exception Objects are people too, and they dont always like being shutdown like that. jerk.
	 */
	public void closeOutStream() throws Exception;
	
	
	/**
	 * 
	 * @param b this method can be used by the client to get the packet header byte for any operation sans packet handling
	 */
	public void print(byte b);
}
