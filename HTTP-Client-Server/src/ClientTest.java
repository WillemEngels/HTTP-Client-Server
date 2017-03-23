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
	private static String uri = "www.tldp.org";
	public static void main(String[] args) throws IOException {
		InetAddress addr = InetAddress.getByName(uri);
	    Socket socket = new Socket(addr, 80);
	    
		File file = new File("C:/Users/phili_000/Desktop/TEST/ldp.gif");
	    file.getParentFile().mkdirs();
	
	    DataOutputStream bw = new DataOutputStream(socket.getOutputStream());
	    bw.writeBytes("GET /images/ldp.gif HTTP/1.1\r\n");
	    bw.writeBytes("Host: "+uri+":80\r\n\r\n");
	    bw.flush();
	    
	    DataInputStream in = new DataInputStream(socket.getInputStream());
	    
	    OutputStream dos = new FileOutputStream(file);
	    int count;
	    byte[] buffer = new byte[2048];
	    boolean eohFound = false;
	    while ((count = in.read(buffer)) != -1)
	    {
	        if(!eohFound){
	            String string = new String(buffer, 0, count);
	            int indexOfEOH = string.indexOf("\r\n\r\n");
	            if(indexOfEOH != -1) {
	                count = count-indexOfEOH-4;
	                buffer = string.substring(indexOfEOH+4).getBytes();
	                eohFound = true;
	            } else {
	                count = 0;
	            }
	        }
	      dos.write(buffer, 0, count);
	      dos.flush();
	    }
	    
	    in.close();
	    dos.close();
	    socket.close();
	    
	    
//	 // Initialize the streams.
//	    final FileOutputStream fileOutputStream = new FileOutputStream(file);
//	    final InputStream inputStream = socket.getInputStream();
//	    
//	    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
//        out.println("" +
//                "Get /images/ldp.gif HTTP/1.1\n" +
//                "Host: "+uri+"\n"+
//                "\n");
//        out.println();
//        out.flush();
//	    
//	    // Header end flag.
//	    boolean headerEnded = false;
//
//	    byte[] bytes = new byte[2048];
//	    int length;
//	    while ((length = inputStream.read(bytes)) != -1) {
//	        // If the end of the header had already been reached, write the bytes to the file as normal.
//	        if (headerEnded)
//	            fileOutputStream.write(bytes, 0, length);
//
//	        // This locates the end of the header by comparing the current byte as well as the next 3 bytes
//	        // with the HTTP header end "\r\n\r\n" (which in integer representation would be 13 10 13 10).
//	        // If the end of the header is reached, the flag is set to true and the remaining data in the
//	        // currently buffered byte array is written into the file.
//	        else {
//	            for (int i = 0; i < 2045; i++) {
//	                if (bytes[i] == 13 && bytes[i + 1] == 10 && bytes[i + 2] == 13 && bytes[i + 3] == 10) {
//	                    headerEnded = true;
//	                    fileOutputStream.write(bytes, i+4 , 2048-i-4);
//	                    break;
//	                }
//	            }
//	        }
//	    }
//	    inputStream.close();
//	    fileOutputStream.close();
//	    socket.close();
	}

	

}
