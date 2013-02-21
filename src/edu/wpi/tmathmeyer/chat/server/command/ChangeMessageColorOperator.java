package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class ChangeMessageColorOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "messagecolor");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		if (args.length==0)return;
		String newColor = args[1];
		sender.setMessageColor(newColor);
	}

}
