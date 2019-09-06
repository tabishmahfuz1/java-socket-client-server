// A Java program for a Server 
import java.net.*; 
import java.io.*; 

public class Server 
{ 
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 

    private HashMap<String, ClientSocket> ClientList = null;
  
    public Server(int port) 
    { 
        // starts server and waits for a connection 
        try
        { 
            server = new ServerSocket(port); 
            System.out.println("Server started"); 
  
			while(true) {
				System.out.println("Waiting for a client ..."); 
  
				socket = server.accept();
                ClientSocket client = new ClientSocket(socket);
				InetAddress addr = socket.getInetAddress();
				System.out.println("Client accepted From " 
									+ addr.getHostName() + " (" 
									+ addr.getHostAddress() + ")"); 
	  
				// takes input from the client socket 
				(new Thread(new ConnectionHandler(client, this))).start();
			}
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 

    /**
    * Add a Client Connection to Client List
    * @param client ClientSocket The connection to add to the list
    * @return boolean
    */
    public boolean addClient(ClientSocket client) {
        this.ClientList.put(client.username, client);
        return true;
    }


    /**
    * Remove a Client Connection from Client List
    * @param username Username of the Client to be removed from the client list
    * @return boolean
    */
    public boolean removeClient(String username) {
        if(this.ClientList.containsKey(username)) {
            this.ClientList.remove(username);
            return true;
        }
        return false;
    }

    /**
    * Returns List of Client Connected to the server
    * @return HashMap<String, ClientSocket>
    */
    public boolean getClients() {
        if(this.ClientList.containsKey(username)) {
            this.ClientList.remove(username);
            return true;
        }
        return false;
    }
  
    public static void main(String args[]) 
    { 
        Server server = new Server(5000); 
    } 
} 