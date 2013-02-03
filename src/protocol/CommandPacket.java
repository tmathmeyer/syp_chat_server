package protocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class CommandPacket implements Packet{
	private String command;
	private String[] args;
	byte group;
	
	
	/**
	 * EXAMPLE PACKET
	 * 
	 * [----packetID: 0x04--------] [---message packet---------]
	 * [----messageGroup: 0x00----] [---the default channel----]
	 * [----commandLength---------] [---4----------------------]
	 * [----command: join---------] [---join-------------------]
	 * [----args count: 1---------] [---1----------------------]
	 * [----args lengths: 4-------] [---4----------------------]
	 * [----args: name------------] [---name-------------------]
	 * 
	 * END EXAMPLE
	 * @param in the DataInputStream from which byte information is being read
	 * @throws IOException if no data can be read from the data input stream, an IO exception is thrown
	 */
	public CommandPacket(DataInputStream in) throws Exception{
		group = in.readByte();
		short comLen = in.readShort();
		char[] com = new char[comLen];
		for(int i = 0; i < comLen; i++)
			com[i] = in.readChar();
		setCommand(new String(com));
		
		int ac = in.readShort();
		short[] arglens = new short[ac];
		setArgs(new String[ac]);
		for(int i = 0; i < ac; i++)
			arglens[i] = in.readShort();
		for(int i = 0; i < ac; i++){
			char[] arg = new char[arglens[i]];
			for(int j = 0; j < arglens[i]; j++)
				arg[j] = in.readChar();
			getArgs()[i] = new String(arg);
		}
	}
	
	public CommandPacket(String com, String a, byte group){
		String[] args = a.split(" ");
		this.setCommand(com);
		this.setArgs(args);
		this.group = group;
	}
	
	
	@Override
	public byte getPacketID() {
		return 0x04;
	}

	@Override
	public void write(DataOutputStream d) throws IOException {
		d.writeByte(this.getPacketID());
		d.writeByte(this.group);
		d.writeShort(this.getCommand().length());
		d.writeChars(this.getCommand());
		d.writeShort(this.getArgs().length);
		for(int i = 0; i < this.getArgs().length; i++)
			d.writeShort(this.getArgs()[i].length());
		for(int i = 0; i < this.getArgs().length; i++)
			d.writeChars(this.getArgs()[i]);
	}

	/**
	 * @return the command
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * @param command the command to set
	 */
	public void setCommand(String command) {
		this.command = command;
	}

	/**
	 * @return the args
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * @param args the args to set
	 */
	public void setArgs(String[] args) {
		this.args = args;
	}

}
