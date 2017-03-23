import java.io.*;
import java.net.*;

public class Server {

	private static int serverPort = 8080;

	public static void main(String[] args) throws IOException {
		//a new serversocket is created
		// if a new socket connects to the serversocket: a new thread is started
		ServerSocket serverSocket = new ServerSocket(serverPort);
		while (true){
			Socket clientSocket = serverSocket.accept();
			if (clientSocket != null){
				Handler h = new Handler(clientSocket);
				Thread thread = new Thread(h);
				thread.start();
				
			}
			
		}

		
		
	}

}
