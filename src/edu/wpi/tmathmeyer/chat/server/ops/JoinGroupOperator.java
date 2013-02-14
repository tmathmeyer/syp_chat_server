package edu.wpi.tmathmeyer.chat.server.ops;

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
		String group = args[0];
		Group g = Server.cm.getGroupByName(group);
		if (g==null)return;
		if (g.addToGroup(sender)){
			Group old = Server.cm.getGroupByName(sender.getCurrentGroup());
			old.kick(sender);
			sender.setCurrentGroup(g.getName());
		}
		
	}

}
