package SER516_Lab2_Server;

import SER516_Lab2_Server.UIComponents.*;

import java.awt.*;
import java.io.IOException;

class ServerMain {


	public static void initialiseGUI()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI window = new ServerUI();
				} catch (Exception e) {
					//e.printStackTrace();
					ServerUtils.displayConsoleMessage(e.toString());
				}
			}
		});
	}

	public static void main(String[] args) throws InterruptedException, IOException {

		initialiseGUI();

	}
}