package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;
import edu.wpi.tmathmeyer.protocol.chat.CommandPacket;

public class CreateGroupOperator  implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "create");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		if (args.length < 2) return;
		Server.cm.addGroup(args[1], sender.getName());
		Server.se.operateCommand(new CommandPacket("join", ". "+args[1], (byte) 0x00), sender);
	}

}