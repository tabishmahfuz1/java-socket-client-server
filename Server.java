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
  
    // constructor with port 
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
            
            /* 
			in = new DataInputStream( 
                new BufferedInputStream(socket.getInputStream())); 
  
            String line = ""; 
  
            // reads message from client until "Over" is sent 
            while (!line.equals("Over")) 
            { 
                try
                { 
                    line = in.readUTF(); 
                    System.out.println(line); 
  
                } 
                catch(IOException i) 
                { 
                    System.out.println(i); 
                } 
            } 
            System.out.println("Closing connection"); 
  
            // close connection 
            socket.close(); 
            in.close();  */
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    } 

    public boolean addClient(ClientSocket client) {
        this.ClientList.put(client.username, client);
        return true;
    }

    public boolean removeClient(String username) {
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