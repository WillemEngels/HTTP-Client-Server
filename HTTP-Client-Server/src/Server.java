import java.io.*;
import java.net.*;

public class Server {

	private static int serverPort = 80;

	public static void main(String[] args) throws IOException {
		
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
