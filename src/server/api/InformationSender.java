package server.api;

import java.util.ArrayList;

import protocol.Packet;

public class InformationSender implements Runnable{

	private ArrayList<Client> targets;
	private Packet information;
	
	public InformationSender(ArrayList<Client> clients, Packet info){
		this.information = info;
		this.targets = clients;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		for(Client c : targets){
			c.sendPacket(information);
		}
	}
}


/*
* HOW TO MAKE SHAWN MAD IN TWO EASY STEPS
* 1) BE NOT KOREAN
* 2) LOOK AT PICTURE OF KOREAN GIRL ON SHAWNS FACEBOOK AND SAY 'SHES HOT'.
* 
* FOR SUPER ANGER INDUCTION:
* 3) BANG HER
*/