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
		if (args.length < 1) return;//sender.sendMessage();
		Server.cm.addGroup(args[0], sender.getName());
		Server.se.addUserToGroup(sender, args[0]);
	}

}