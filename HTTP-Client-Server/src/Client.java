import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		
		makeRequest(uri);
		
		
	  }

	private static void makeRequest(String uri) throws IOException {
		
		if (command.equals("GET")){
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, port);
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    //create HTML file & PrintStream to write on file
		    File file = new File("C:/Users/phili_000/Desktop/CN/HTML.html");
		    file.getParentFile().mkdirs();
		    PrintStream ps = new PrintStream(file);
		    
		    // send an HTTP request to the web server
		    out.println("GET / HTTP/1.1");
		    out.println("host: "+uri);
		    out.println("Connection: Close");
		    out.println();
		    

		    // The output of the "terminal" is saved in buffered reader
		    //stringbuilder iterates over the bufferedreader in to make a string
		    boolean loop = true;
		    StringBuilder sb = new StringBuilder();
		    while (loop){
		    	if (in.ready()){
		    		int i = 0;
		    		while (i != -1){
		    			i = in.read();
		    			sb.append((char) i);
		    		}
		    		loop = false;
		    	}
		    }
		   
		    
		    System.out.println(sb.toString());
		    socket.close();
		    //The header is cut off to create a clean html file
		    String s = "<";
		    while(sb.toString().charAt(0)!=s.charAt(0)){
		    	sb.deleteCharAt(0);
		    }
		    
		    //write on HTML file
		    String HTML = sb.toString();
		    ps.print(HTML);
		    
		    
		    //parse using jsoup
		    parse(HTML);
		    
		}
		
		else if (command.equals("HEAD")){
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, port);
		    PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    // send an HTTP request to the web server
		    out.println("HEAD / HTTP/1.1");
		    out.println("host: "+uri);
		    out.println("Connection: Close");
		    out.println();
		    
		    // read the response
		    boolean loop = true;
		    StringBuilder sb = new StringBuilder();
		    while (loop){
		    	if (in.ready()){
		    		int i = 0;
		    		while (i != -1){
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
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, port);
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
			
			
			//Ask for new input in the terminal
		    // this is saved in the string message
			System.out.println("Enter message: ");
			Scanner scanner = new Scanner(System.in);
			StringBuffer sb = new StringBuffer();
			String message = scanner.nextLine();
			while (scanner.hasNext()){
				sb = sb.append(scanner.nextLine());
			}
			message = sb.toString();
			
			// send an http request to the server
			// also the headers content-Type and content length and the message are sent
			out.println("PUT " + uri + " HTTP/1.1\r\n");
			
			out.println("Content-Length: " + message.length() + "\r\n");
			out.println("Content-Type: application/x-www-form-urlencoded\r\n");
			out.println("\r\n");

			out.println(message);
			out.flush();

			//the response from the server is printed to the terminal
		    String line;
		    while ((line = in.readLine()) != null) {
		      System.out.println(line);
		    }
		    out.close();
		    in.close();
		    socket.close();
		    scanner.close();
			
			
			
		}
		
		else if (command == "POST"){
			//works almost the same as a PUT request
			
			InetAddress addr = InetAddress.getByName(uri);
		    Socket socket = new Socket(addr, port);
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
			
			
			
			System.out.println("Enter message: ");
			Scanner scanner = new Scanner(System.in);
			StringBuffer sb = new StringBuffer();
			String message = scanner.nextLine();
			while (scanner.hasNext()){
				sb = sb.append(scanner.nextLine());
			}
			message = sb.toString();
			
			out.println("POST " + uri + " HTTP/1.1\r\n");
			
			out.println("Content-Length: " + message.length() + "\r\n");
			out.println("Content-Type: application/x-www-form-urlencoded\r\n");
			out.println("\r\n");

			out.println(message);
			out.flush();

		    String line;
		    while ((line = in.readLine()) != null) {
		      System.out.println(line);
		    }
		    out.close();
		    in.close();
		    socket.close();
		    scanner.close();
		}
	}
	
	private static void parse(String HTML) throws IOException {
			
			//using external library jsoup to parse the HTML file
			
			Document doc = Jsoup.parse(HTML);
			Elements img = doc.select("img");
			
			//loops over all images in the file
			for (Element element : img){
				
				//for every image the source is selected
				String source = element.attr("src");
				
				//when the source starts with http, the embedded image is ignored 
				String s ="http";
				if (!source.contains(s)){
					System.out.println("src  : "+source);
					getIMG(source);
				}
			}	    
	}
	
	private static void getIMG(String source) throws IOException{
		
		//The original connection is lost so a new connection is created
		InetAddress addr = InetAddress.getByName(uri);
	    Socket socket = new Socket(addr, port);
	    
	    //create new file to store the image
		File file = new File("C:/Users/phili_000/Desktop/CN/"+source);
	    file.getParentFile().mkdirs();
	    
	    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
	    dos.writeBytes("GET /"+ source +" HTTP/1.1\r\n");
	    dos.writeBytes("Host: "+uri+":80\r\n\r\n");
	    dos.flush();

	    DataInputStream in = new DataInputStream(socket.getInputStream());
	    OutputStream os = new FileOutputStream(file);
	    
	    //delete content length header
	    int off;//offset
	    int len;//length
	    
	    byte[] bite = new byte[2048];
	    boolean end_of_header = false;
	    while ((len = in.read(bite)) != -1){//len== number of bytes read+bytes are stored in bite
	    	off = 0;
	    	if (end_of_header==false){//
	    		String str = new String(bite,0,len);//make new string with amount of bites that equals len
	    		int end_of_header_index = str.indexOf("\r\n\r\n");// this is the end of the http header
	    		if(end_of_header_index != -1){//if end_of_header_index == -1 this means the end of the header is not found in str
	    			len = len-end_of_header_index-4;//4 equals length of a bite
	    			off = end_of_header_index + 4;
	    			end_of_header = true;
	    		}
	    		
	    	}
	    	//System.out.println(off+" "+len);
	    	os.write(bite, off, len);//write bite[off] through bite[off+len-1]
	    	os.flush();
	    }
	    
	    
	    in.close();
	    os.close();
	    socket.close();
	    
	}
}