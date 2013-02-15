package edu.wpi.tmathmeyer.chat.protocol;

import java.io.DataOutputStream;
import java.io.IOException;

public interface Packet {
	/**
	 * 
	 * @return the ID of the packet
	 */
	public byte getPacketID();
	
	
	/**
	 * 
	 * @param d the dataoutputstream for the packet data to be written to
	 * @throws IOException a closed or malformed DoS
	 */
	public void write(DataOutputStream d) throws IOException;
	
	
	/**
	 * 0x00       login client->server
	 * 
	 * 0x01       message client->server
	 * 0x01       message server->client
	 * 
	 * 0x02       message groups server->client
	 *               (client is a member of)
	 *               
	 * 0x03       message groups server->client
	 *               (all of them)
	 *               
	 * 0x04       Users in a group server->client
	 * 
	 * 0x05       join a group client->server
	 * 
	 * 0x06       leave a group client->server
	 * 0x16       leave a group server->client
	 * 0x26       kicked from a group server->client
	 * 
	 * 0x99       control packet (depends on whats going on)
	 * 
	 */
}
