import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.*;

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

	
	public static void main(String args[]) throws Exception{
		
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
		    File file = new File("/home/r0579613/Desktop/HTML.html");
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
		    
		    
		    
		    
		    
//		    //words to search for
//		    String image = "img ";
//		    String source = "src=";
//		    //array om strings op te slaan
//		    List<String> list = new ArrayList();//array om strings op te slaan
//		    
//		    //loopen met while
//		    boolean bool = true;
//		    int index = 0;
//		    while(bool = true){
//		    	if(HTML.indexOf(image,index)==-1){
//		    		bool = false;
//		    		break;
//		    	}
//		    	
//		    	 char a_char = HTML.charAt(index+5);
//		    	 String str = Character.toString(a_char);
//		    	 char a_char2 = HTML.charAt(index+6);
//		    	 String str2 = Character.toString(a_char2);
//		    	//zoeken naar eerste aanhalingsteken bij index = index+5
//		    	if ((str=="/")&&(str2=="/")){
//		    		int endIndex = HTML.indexOf('"', index+7);
//		    		String imageAdress = HTML.substring(index+7,endIndex-1);
//		    		list.add(imageAdress);
//		    	}
//		    	else{
//		    		int endIndex = HTML.indexOf('"', index+5);
//		    		String imageAdress = HTML.substring(index+5,endIndex-1);
//		    		list.add(imageAdress);
//		    	//TODO afbeeldigen opslaan
//		    	}
//		    }
		    
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