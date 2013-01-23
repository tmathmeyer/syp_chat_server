package protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ChannelPacket implements Packet{

	private byte PacketID;
	private String name;
	
	public ChannelPacket(DataInputStream in, byte type) throws IOException{
		this.setPacketID(type);
		short length = in.readShort();
		char[] msg = new char[length];
		for(int i = 0; i < msg.length; i++)
			msg[i] = in.readChar();
		this.setName(new String(msg));
	}
	
	private ChannelPacket(){
		this.PacketID = 0x03;
		this.name = "home";
	}

	/**
	 * @return the packetID
	 */
	public byte getPacketID() {
		return PacketID;
	}

	/**
	 * @param packetID the packetID to set
	 */
	public ChannelPacket setPacketID(byte packetID) {
		PacketID = packetID;
		return this;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static final ChannelPacket home = new ChannelPacket();

	@Override
	public void write(DataOutputStream d) throws IOException{
		d.write(this.getPacketID());
		d.flush();
		d.writeShort(this.getName().length());
		d.flush();
		d.writeChars(this.getName());
		d.flush();
	}
}
