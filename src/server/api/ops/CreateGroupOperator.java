package server.api.ops;

import server.api.Client;
import server.api.Server;

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
		Server.se.addUserToGroup(sender, args[1]);
	}

}