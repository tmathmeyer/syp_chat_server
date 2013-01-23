package server.api.info;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import server.api.Server;

public class ServerInformation extends JPanel implements Runnable{
	Server s;
	JTextArea information;
	
	public ServerInformation(Server s){
		this.s = s;
		information = new JTextArea();
		information.setEditable(false);
		this.setLayout(new GridLayout(1,1));
		this.add(information);
		new Thread(this).start();
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(5000);
				information.setText(s.getName()+"\n");
			}
			catch(Exception e){}
		}
	}
}
