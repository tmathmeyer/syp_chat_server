package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class InviteOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "invite");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		if (args.length==0)return;
		String username = args[1];
		sender.getCurrentGroup().invite(username);
	}
}

