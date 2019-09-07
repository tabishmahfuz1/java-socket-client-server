import java.net.*; 
import java.io.*; 

public class SocketInputHandler extends InputHandler {

	public SocketInputHandler(Client client) {
		super(client);
	}

	public void run(){
		listen();
	}

	public DataInputStream getInputStream() {
		return client.getInputStream(Client.SOCKET);
	}

	public void listen() {
		
	}
}