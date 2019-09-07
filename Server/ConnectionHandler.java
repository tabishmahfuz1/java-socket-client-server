import java.net.*; 
import java.io.*; 
import java.util.*; 


/**
* Connection Handler Thread for individual Client Connections
*/
class ConnectionHandler implements Runnable {
	private final ClientSocket clientSocket;
	private Server mainServer;
	
	public ConnectionHandler(ClientSocket clientSocket, Server server){
		this.clientSocket 	= clientSocket;
		this.mainServer 	= server;
	}
	
	public void run(){
		// Start Listening from the Client
		this.listen();

		System.out.println("Closing connection from " 
			+ clientSocket.getBaseSocket().getInetAddress().getHostAddress()); 

		// Close the Client Connection
		this.closeConnection();
    }

    /**
    * Initialises Client Connection
    * @return boolean
    */
    public boolean initializeClient() {
    	String nextline;
    	try {
    		this.clientSocket.write("Enter Your Username: ");
			nextline = this.clientSocket.read();

			// TODO: Check duplicate Username

			this.clientSocket.setUsername(nextline);

			if(this.mainServer.addClient(this.clientSocket)) {
				this.clientSocket.write("Welcome to the chatroom!\n");
				return true;
			} else {
				this.clientSocket.write("Couldn't initialize your connection. Please try again");	
				this.closeConnection();
				return false;
			}
    	} catch (IOException ex) {
    		System.out.println(ex);
    		return false;
    	}
		
    }

    /**
    * Listens and handles Incoming Messages from the Client
    *
    */
    public void listen() {
    	if(!this.initializeClient()) {
    		return;
    	}
    	String msg;
    	try {
    		while (!(msg = this.clientSocket.read()).equals("Over")) 
			{ 
				// nextline = in.readUTF(); 
				System.out.println(this.clientSocket.username + " => " + msg);
				this.broadcastMsg(this.clientSocket.username, msg);
			} 
    	} catch (IOException ex) {
    		System.out.println(ex);
    		return;
    	}
		
    }

    public void broadcastMsg(String username, String msg) {
    	for (Map.Entry<String, ClientSocket> client 
    			: mainServer.getClients().entrySet()) {
		    if(client.getKey().equals(username))
		    	continue;
		    try{
			    client.getValue().write(username + ": " + msg);
		    } catch (IOException ex) {
		    	System.out.println(ex);
		    }
		}
    }

    /**
    * Closes the Client Connection
    *
    */
    public void closeConnection() {
    	this.clientSocket.close();
		this.mainServer.removeClient(this.clientSocket.username);
    }
}