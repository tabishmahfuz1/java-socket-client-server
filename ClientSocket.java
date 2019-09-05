import java.net.*; 
import java.io.*; 
class ClientSocket {
	private final Socket socket;
	public String username;
	private DataInputStream inStream = null;
	private DataInputStream outStream = null;

	public ClientSocket(Socket socket){
		this.socket = socket;
		this.inStream = new DataInputStream( 
                new BufferedInputStream(this.socket.getInputStream())); 
		this.outStream = new DataOutputStream( 
                new BufferedInputStream(this.socket.getOutputStream())); 
	}

	public DataInputStream getOutputStream() {
		return this.outStream;
	}

	public DataInputStream getInputStream() {
		return this.inStream;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}