// A Java program for a Server 
import java.net.*; 
import java.io.*;
import java.util.HashMap; 

public class Server 
{ 
    //initialize socket and input stream 
    private Socket          socket   = null; 
    private ServerSocket    server   = null; 
    private DataInputStream in       =  null; 

    private HashMap<String, ClientSocket> ClientList = new HashMap<String,ClientSocket>();
  
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
    public boolean addClient(ClientSocket client) throws IOException {
        this.ClientList.put(client.username, client);
        client.write("Connection Successful");
        System.out.println("New User Added to Client list: " + client.username);
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
    public HashMap<String, ClientSocket> getClients() {
        return this.ClientList;
    }
  
    public static void main(String args[]) 
    {
        int port                = 5000;
        if(args.length > 0) {
            port = Integer.parseInt(args[1]);
        }
        Server server = new Server(port); 
    } 
} 