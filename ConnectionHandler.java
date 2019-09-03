import java.net.*; 
import java.io.*; 

class ConnectionHandler implements Runnable {
	private final Socket clientSocket;
	private DataInputStream in       =  null; 
	
	public ConnectionHandler(Socket clientSocket){
		this.clientSocket = clientSocket;
	}
	
	public void run(){
		try {
			in = new DataInputStream( 
                new BufferedInputStream(clientSocket.getInputStream())); 
			
			String nextline;
			System.out.println("Going in Loop");
			while (!(nextline = in.readUTF()).equals("Over")) 
			{ 
				// nextline = in.readUTF(); 
				System.out.println(nextline); 
			} 
			System.out.println("Going in Loop");
			System.out.println("Closing connection from " + clientSocket.getInetAddress().getHostAddress()); 
			clientSocket.close();
		} catch(IOException ex) {
			System.out.println(ex);
		}
    }
}