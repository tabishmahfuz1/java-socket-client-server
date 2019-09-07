import java.net.*; 
import java.io.*; 

public class TerminalInputHandler extends InputHandler {
	
	public TerminalInputHandler(Client client) {
		super(client);
	}

	public DataInputStream getInputStream() {
		return client.getInputStream(Client.TERMINAL);
	}

	public void run(){
		listen();
	}

	public void listen() {
		// string to read message from input 
        String line = "";
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = input.readLine(); 
                out.writeUTF(line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        }
	}
}