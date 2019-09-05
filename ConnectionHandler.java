import java.net.*; 
import java.io.*; 

class ConnectionHandler implements Runnable {
	private final Socket clientSocket;
	private DataInputStream in       =  null; 
	private Server mainServer;
	
	public ConnectionHandler(Socket clientSocket, Server server){
		this.clientSocket 	= clientSocket;
		this.mainServer 	= server;
	}
	
	public void run(){
		try {
			in = clientSocket.getInputStream();
			out = clientSocket.getOutputStream(); 

			// TODO: Check duplicate Username

			this.initializeClient();

			while (!(nextline = in.readUTF()).equals("Over")) 
			{ 
				// nextline = in.readUTF(); 
				System.out.println(nextline); 
			} 

			System.out.println("Closing connection from " + clientSocket.socket.getInetAddress().getHostAddress()); 
			this.closeConnection();
		} catch(IOException ex) {
			System.out.println(ex);
		}
    }

    public void initializeClient() {
    	String nextline;

		out.write("Enter Your Username: ");
		nextline = in.readUTF();

		// TODO: Check duplicate Username

		this.clientSocket.setUsername(nextline);

		if(this.mainServer.addClient(this.clientSocket)) {
			out.write("Welcome to the chatroom!\n");
		} else {
			out.write("Couldn't initialize your connection. Please try again");	
			this.closeConnection();
		}
    }

    public void listen() {

    }

    public void closeConnection() {
    	this.clientSocket.socket.close();
		this.mainServer.removeClient(this.clientSocket.username);
    }
}