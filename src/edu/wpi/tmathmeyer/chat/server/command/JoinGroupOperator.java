package edu.wpi.tmathmeyer.chat.server.command;

import edu.wpi.tmathmeyer.chat.server.Client;
import edu.wpi.tmathmeyer.chat.server.Server;
import edu.wpi.tmathmeyer.chat.server.group.Group;

public class JoinGroupOperator implements CommandOperator {

	@Override
	public void register() {
		Server.se.addCommandOperator(this, "join");
	}

	@Override
	public void parseCommand(Client sender, String[] args) {
		if (args.length==0)return;
		String group = args[1];
		Group g = Server.cm.getGroupByName(group);
		if (g==null)return;
		if (g.addToGroup(sender)){
			Group old = (sender.getCurrentGroup());
			old.kick(sender);
			sender.setCurrentGroup(g);
		}
	}

}
