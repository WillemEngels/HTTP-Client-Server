import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 
 * TODO
 * uitzoeken hoe java executable wordt opgeroepen in terminal
 * de Reader haalt het HTTPCommand eruit: (GET HEAD PUT POST)
 * URI en Port (onze server heeft port 80)
 * 
 * request wordt behandeld:
 * toon response op terminal EN sla op in html bestand
 * Als er images in het html bestand staan: gebruik GET om ze terug te geven
 * 
 * voor PUT en POST geef een nieuwe input lijn
 * 
 *
 */
public class Client {
	private static String uri;
	private static String port;
	private static String command;

	
	public static void main(String args[]) throws Exception{
		
		command = args[0];
		uri = args[1];
		port = args[2];
		System.out.println(command);
		System.out.println(uri);
		System.out.println(port);
		//getinput
		
		makeRequest();
		
		
	  }

	private static void makeRequest() throws IOException {
		System.out.println("code reaches here");
		System.out.println(command);
		System.out.println(command.equals("GET"));
		// TODO Auto-generated method stub
		if (command.equals("GET")){
			System.out.println("command is get");
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, 80);
		    boolean autoflush = true;
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
		    BufferedReader in = new BufferedReader(

		    new InputStreamReader(socket.getInputStream()));
		    // send an HTTP request to the web server
		    out.println("GET / HTTP/1.1");
		    out.println("Host: "+uri+":"+port);
		    out.println("Connection: Close");
		    out.println();

		    // read the response
		    boolean loop = true;
		    StringBuilder sb = new StringBuilder(8096);
		    while (loop) {
		      if (in.ready()) {
		        int i = 0;
		        while (i != -1) {
		          i = in.read();
		          sb.append((char) i);
		        }
		        loop = false;
		      }
		    }
		    System.out.println(sb.toString());
		    socket.close();
		}
		
		else if (command == "HEAD"){
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, 80);
		    boolean autoflush = true;
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
		    BufferedReader in = new BufferedReader(

		    new InputStreamReader(socket.getInputStream()));
		    // send an HTTP request to the web server
		    out.println("HEAD / HTTP/1.1");
		    out.println("Host: "+uri+":"+port);
		    out.println("Connection: Close");
		    out.println();
		}
		
		else if (command == "PUT"){
			
		}
		
		else if (command == "POST"){
			
		}
		
		
	}
}