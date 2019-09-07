// A Java program for a Client 
import java.net.*; 
import java.io.*; 
  
public class Client 
{ 
    public static final int SOCKET          = 2;
    public static final int TERMINAL        = 1;
    // initialize socket and input output streams 
    private Socket socket                   = null; 
    private String username                 = null; 
    private DataInputStream  socketInput    = null; 
    private DataInputStream  terminalInput  = null; 
    private DataOutputStream socketOutput   = null; 
  
    // constructor to put ip address and port 
    public Client(String address, int port) 
    { 
        // establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            terminalInput   = new DataInputStream(System.in); 

            // takes input from the socket 
            socketInput     = new DataInputStream(socket.getInputStream()); 
  
            // sends output to the socket 
            socketOutput             = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }

        // Read and Set Username
        try{  
            System.out.println("=> " + socketInput.readUTF()); 
            this.setUsername(terminalInput.readLine());
        } catch (IOException e) {
            System.out.println(e);
        }
        new Thread(() -> listenSocket()).start();    
        new Thread(() -> listenTerminal()).start();    
        // listenTerminal();    
    }

    public void listenSocket() {
        String line = "";
        System.out.println("Listening from Socket");
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = socketInput.readUTF(); 
                System.out.println("=> " + line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
                closeConnection();
                break;
            } 
        }
    }

    public void listenTerminal() {
        String line = "";
        // keep reading until "Over" is input 
        System.out.println("Listening from Terminal");
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = terminalInput.readLine(); 
                socketOutput.writeUTF(line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
                break;
            } 
        }
        closeConnection();
    }

    public DataInputStream getInputStream(int inputType) {
        if(inputType == TERMINAL) {
            return this.terminalInput;
        } else {
            return this.socketInput;
        }
    }

    public DataOutputStream getOutputStream() {
        return this.socketOutput;
    }

    public boolean setUsername(String username) throws IOException{
        socketOutput.writeUTF(username);
        if(socketInput.readUTF().equals("ADDED")) {
            this.username = username;
            System.out.println("ADDED");
            return true;
        } 
        return false;
    }

    public Socket getBaseSocket() {
        return socket;
    }
  
    public static void main(String args[]) 
    { 
		String serverAddress 	= "127.0.0.1";
		int port 				= 5000;
		if(args.length == 1) {
			serverAddress = args[0];
		} else if(args.length > 1) {
			serverAddress = args[0];
			port = Integer.parseInt(args[1]);
		}
		
		 
        Client client = new Client(serverAddress, port); 
    } 

    public void closeConnection(){
        // close the connection 
        try
        { 
            terminalInput.close(); 
            socketOutput.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
    }
} 