package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;

public class CreateGroupOperator  implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "create");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		System.out.println("creation was triggered");
		for(String arg : args)System.out.println(arg);
		if (args.length < 2) return;//sender.sendMessage();
		Server.cm.addGroup(args[1], sender.getName());
		
	}

}