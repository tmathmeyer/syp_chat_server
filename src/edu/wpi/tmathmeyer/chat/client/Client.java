package edu.wpi.tmathmeyer.chat.client;

import edu.wpi.tmathmeyer.protocol.DataHandler;

public interface Client extends DataHandler{
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
}
