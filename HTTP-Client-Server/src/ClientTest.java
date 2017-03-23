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
public class ClientTest {
	//private static String uri = "www.tldp.org";
	public static void main(String[] args) throws IOException {
//		InetAddress addr = InetAddress.getByName(uri);
//	    Socket socket = new Socket(addr, 80);
//	    
//		File file = new File("C:/Users/phili_000/Desktop/TEST/ldp.gif");
//	    file.getParentFile().mkdirs();
//	
//	    DataOutputStream bw = new DataOutputStream(socket.getOutputStream());
//	    bw.writeBytes("GET /images/ldp.gif HTTP/1.1\r\n");
//	    bw.writeBytes("Host: "+uri+":80\r\n\r\n");
//	    bw.flush();
//	    
//	    DataInputStream in = new DataInputStream(socket.getInputStream());
//	    
//	    OutputStream dos = new FileOutputStream(file);
//	    int count;
//	    byte[] buffer = new byte[2048];
//	    boolean eohFound = false;
//	    while ((count = in.read(buffer)) != -1)
//	    {
//	        if(!eohFound){
//	            String string = new String(buffer, 0, count);
//	            int indexOfEOH = string.indexOf("\r\n\r\n");
//	            if(indexOfEOH != -1) {
//	                count = count-indexOfEOH-4;
//	                buffer = string.substring(indexOfEOH+4).getBytes();
//	                eohFound = true;
//	            } else {
//	                count = 0;
//	            }
//	        }
//	      dos.write(buffer, 0, count);
//	      dos.flush();
//	    }
//	    
//	    in.close();
//	    dos.close();
//	    socket.close();
	    
	 
		String domain = "www.manchester.edu";
        String path = "/images/default-source/default-album/slide1.jpg";
        Socket socket = new Socket(domain,80);
        
        
		
		
	}

	

}
