package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class LoginPacket implements Packet{
	
	private DataInputStream in;
	private short[] length;
	private String username;
	private String password;
	
	/**
	 * EXAMPLE PACKET
	 * client->server
	 * 
	 * [----packetID: 0x00-----]
	 * [----usernameLength: 5--]
	 * [----passwordLength: 4--]
	 * [----username: admin----]
	 * [----password: pass-----]
	 * 
	 * END EXAMPLE
	 * @param in the DataInputStream from which byte information is being read
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public LoginPacket(DataInputStream in) throws IOException{
		this.in = in;
		setLength(this.getPacketSize());
		this.setUsername(this.getUsername(this.getLength()[0]));
		this.setPassword(this.getPassword(this.getLength()[1]));
	}
	
	/**
	 * 
	 * @return the type of packet
	 */
	public byte getPacketID() {
		return 0x00;
	}
	
	
	/**
	 * 
	 * @return an array which details the size of the username and password in an array
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public short[] getPacketSize() throws IOException {
		short[] shorts = {in.readShort(), in.readShort()};
		return shorts;
	}
	
	/**
	 * 
	 * @param length the length in characters of the username
	 * @return the username
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public String getUsername(short length) throws IOException {
		char[] chars = new char[length];
		for(int i = 0; i < length; i++)chars[i] = in.readChar();
		return new String(chars);
	}
	
	/**
	 * 
	 * @param length the length of in characters of the password
	 * @return the password
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public String getPassword(short length) throws IOException {
		char[] chars = new char[length];
		for(int i = 0; i < length; i++)chars[i] = in.readChar();
		return new String(chars);
	}

	/**
	 * @return the length
	 */
	public short[] getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(short[] length) {
		this.length = length;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void write(DataOutputStream d) {
		// TODO Auto-generated method stub
		
	}

}
