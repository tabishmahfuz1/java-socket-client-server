import java.net.*; 
import java.io.*;

public abstract class InputHandler implements Runnable {
	Client client;
	Socket ClientSocket;
	DataInputStream inStream;

	public InputHandler(Client client) {
		this.client 		= client;
		this.clientSocket 	= client.getBaseSocket();
		this.inStream 		= getInputStream();
	}

	public void run();

	public DataInputStream getInputStream();
}