import java.io.*;
import java.net.*;
import java.util.*;

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
	private static int port;
	private static String command;

	
	public static void main(String[] args) throws Exception{
		
		command = args[0];
		uri = args[1];
		port =  Integer.parseInt(args[2]);
		
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
		    Socket socket = new Socket(addr, port);
		    boolean autoflush = true;
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		  //create HTML file & PrintStream to write on file
		    File file = new File("C:/Users/phili_000/Desktop/HTML.html");
		    PrintStream ps = new PrintStream(file);
		    
		    // send an HTTP request to the web server
		    out.println("GET / HTTP/1.1");
		    out.println("host: "+uri);
		    out.println("Connection: Close");
		    out.println();

		    // read the response
		    boolean loop = true;
		    StringBuilder sb = new StringBuilder();
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
		    
		    String s = "<";
		    while(sb.toString().charAt(0)!=s.charAt(0)){
		    	sb.deleteCharAt(0);
		    }
		    
		    //write on HTML file
		    String HTML = sb.toString();
		    ps.print(HTML);
		    
		    //search for embedded images
		    //search image with java.imageio
		    
		    String image = "img src=\"";
		    //array om links op te slaan
		    List<String> list = new ArrayList<String>();
		    
		    boolean bool = true;
		    int index = 0;
		    while (bool=true){
		    	if(HTML.indexOf(image,index)==-1){
		    		bool = false;
		    	}
		    	else{
		    		index = HTML.indexOf(image,index);
		    		int endIndex = HTML.indexOf("\"", index+9);
		    		String imageAdress = HTML.substring(index+9,endIndex-1);
		    		list.add(imageAdress);
		    	}
		    }
		   System.out.println("/////////////////////////////");
		    System.out.println(list.get(0)+"   "+ list.get(1));
		   
		    
		    socket.close();
		}
		
		else if (command.equals("HEAD")){
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, port);
		    boolean autoflush = true;
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), autoflush);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    // send an HTTP request to the web server
		    out.println("HEAD / HTTP/1.1");
		    out.println("host: "+uri);
		    out.println("Connection: Close");
		    out.println();
		    
		    // read the response
		    boolean loop = true;
		    StringBuilder sb = new StringBuilder();
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
		
		else if (command == "PUT"){
			
		}
		
		else if (command == "POST"){
			
		}
	}
	
	public static void readHTML(File file){
		
		
		
	}

	
}