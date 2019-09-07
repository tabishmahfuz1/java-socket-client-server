import java.net.*; 
import java.io.*; 
class ClientSocket {
	private final Socket socket;
	public String username;
	private DataInputStream inStream = null;
	private DataOutputStream outStream = null;

	public ClientSocket(Socket socket){
		this.socket = socket;
		try {
			this.inStream = new DataInputStream( 
                new BufferedInputStream(this.socket.getInputStream())); 
			this.outStream = new DataOutputStream( 
	                new BufferedOutputStream(this.socket.getOutputStream())); 
		} catch(IOException ex) {
			System.out.println(ex);
		}
		
	}

	/**
	* Writes a message to Client
	* @param message String
	* @return void
	*/
	public void write(String message) {
		outputStream.writeUTF(message);
	}

	/**
	* Reads a message from Clientd
	* @return String
	*/
	public String read() throws IOException {
		return inStream.readUTF();
	}


	/**
	* Returns the Output Stream Buffer for the base Socket
	* @return DataOutputStream
	*/
	public DataOutputStream getOutputStream() {
		return this.outStream;
	}

	/**
	* Returns the Input Stream Buffer for the base Socket
	* @return DataInputStream
	*/
	public DataInputStream getInputStream() {
		return this.inStream;
	}

	/**
	* Returns the base Socket
	* @return Socket
	*/
	public Socket getBaseSocket() {
		return this.socket;
	}

	/**
	* Closes the underlying base Socket
	* @return boolean
	*/
	public boolean close(){
		try{
			this.socket.close();
			return true;
		} catch (IOException ex) {
			System.out.println(ex);
			return false;
		}		
	}

	/**
	* Sets the given username to the Client Socket
	* @param username String
	* @return void
	*/
	public void setUsername(String username) {
		this.username = username;
	}
}