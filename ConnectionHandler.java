import java.net.*; 
import java.io.*; 

/**
* Connection Handler Thread for individual Client Connections
*/
class ConnectionHandler implements Runnable {
	private final Socket clientSocket;
	private DataInputStream in; 
	private Server mainServer;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	public ConnectionHandler(Socket clientSocket, Server server){
		this.clientSocket 	= clientSocket;
		this.mainServer 	= server;
		this.inputStream 	= clientSocket.getInputStream();
		this.outputStream 	= clientSocket.getOutputStream(); 
	}
	
	public void run(){
		try {
			// Start Listening from the Client
			this.listen();

			System.out.println("Closing connection from " 
				+ clientSocket.socket.getInetAddress().getHostAddress()); 

			// Close the Client Connection
			this.closeConnection();
		} catch(IOException ex) {
			System.out.println(ex);
		}
    }

    /**
    * Initialises Client Connection
    * @return boolean
    */
    public void initializeClient() {
    	String nextline;

		outputStream.write("Enter Your Username: ");
		nextline = inputStream.readUTF();

		// TODO: Check duplicate Username

		this.clientSocket.setUsername(nextline);

		if(this.mainServer.addClient(this.clientSocket)) {
			outputStream.write("Welcome to the chatroom!\n");
			return true;
		} else {
			outputStream.write("Couldn't initialize your connection. Please try again");	
			this.closeConnection();
			return false;
		}
		return true;
    }

    /**
    * Listens and handles Incoming Messages from the Client
    *
    */
    public void listen() {
    	if(!this.initializeClient()) {
    		return false;
    	}

		while (!(nextline = inputStream.readUTF()).equals("Over")) 
		{ 
			// nextline = in.readUTF(); 
			System.out.println(nextline); 
		} 
    }

    /**
    * Closes the Client Connection
    *
    */
    public void closeConnection() {
    	this.clientSocket.socket.close();
		this.mainServer.removeClient(this.clientSocket.username);
    }
}