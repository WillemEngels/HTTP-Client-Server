import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Handler implements Runnable{

	private Socket clientSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private String host;
	private String modifiedDate = null;;
	
	


	public Handler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}


	@Override
	public void run() {
		
		try  {
			inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient =  new DataOutputStream(clientSocket.getOutputStream());
            
            
            
            
            String cmd = inFromClient.readLine();
            System.out.println("command");
            System.out.println(cmd);
            System.out.println();
            String[] Command = cmd.split(" ");
            String command = Command[0];
            String fileName = Command[1];
            String httpVersion = Command[2];
            System.out.println(command);
            System.out.println(fileName);
            System.out.println(httpVersion);
            
            while (inFromClient.ready()){
            	String newLine = inFromClient.readLine();
            	System.out.println(newLine);
            	if (newLine.startsWith("Host: ")){
            		this.host = newLine.substring(6);
            		
            	}
            	if (newLine.startsWith("If-Modified-Since")){
            		this.modifiedDate = newLine.substring(19);
            	}
            }
            
            System.out.println("host: " + host);
            
            
            if ( command.equals("GET"))
            {
            	
            	//controleren of input juist is anders 404
            	if (this.host.isEmpty()){
            		//404
            		String statusLine = "404 Not Found" + "\r\n";
            		String date = "Date: " + new Date().toString() + "\r\n";
//            		String responseString = file.toString();
//            		FileInputStream fin = new FileInputStream(responseString);
//            		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
//            		String contentTypeLine = "Content-Type: text/html" + "\r\n";
//            		if (!fileName.endsWith(".html")){
//            			contentTypeLine = "Content-Type: \r\n";
//            		}
            		outToClient.writeBytes(statusLine);
            		outToClient.writeBytes(date);
//                	outToClient.writeBytes(contentTypeLine);
//                	outToClient.writeBytes(contentLengthLine);
                	outToClient.writeBytes("\r\n");
            	}
            	else {
            		
            		if (fileName.equals("/favicon.ico")){
            			
            		}
            		else{
            		
            	
            		System.out.println("hostname " + clientSocket.getLocalAddress().getHostName());
            		File file = new File("src\\file.html");
            		System.out.println(file.exists());


            		URL url = null;
            		//                url = new URL("http", host, fileName);


            		InputStream is = null;
            		BufferedReader br = null;
            		String line;
            		FileWriter fw = new FileWriter(file);


            		try {
            			if (fileName.startsWith("/")){
                    		fileName = "http:/" + fileName;
                        	
                    	}
                    	if (!fileName.endsWith("index.html")){
                    		if (fileName.endsWith("/")){
                    			fileName = fileName + "index.html";
                    		}
                    		else{
                    		fileName = fileName + "/index.html";
                    		}
                        }
//            			url = new URL("http", host, fileName);

            			System.out.println("code");

            			url = new URL(fileName);
                    	is = url.openStream();
                    	br = new BufferedReader(new InputStreamReader(is));
                    	System.out.println(url.toString());
                    	while ((line = br.readLine()) != null){
                    		System.out.println(line);
                    		fw.write(line);
                    		
                    		
                    	}
                    	
//                    	if (this.modifiedDate) //TODO
                    	
                    	//terug geven met filereader in 200
//                    	sendResponse(200, file.toString());
                    	String statusLine = "200 OK" + "\r\n";
                		String date = "Date: " + new Date().toString() + "\r\n";
                		String responseString = file.toString();
                		FileInputStream fin = new FileInputStream(responseString);
                		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
                		String contentTypeLine = "Content-Type: text/html" + "\r\n";
                		if (!fileName.endsWith(".html")){
                			contentTypeLine = "Content-Type: \r\n";
                		}
                		outToClient.writeBytes(statusLine);
                		outToClient.writeBytes(date);
                    	outToClient.writeBytes(contentTypeLine);
                    	outToClient.writeBytes(contentLengthLine);
                    	outToClient.writeBytes("\r\n");
                    	
                    	
                   	
                    	url = new URL(fileName);
                    	is = url.openStream();
                    	br = new BufferedReader(new InputStreamReader(is));
                    	System.out.println(url.toString());
                    	while ((line = br.readLine()) != null){
//                    		System.out.println(line);
                    		outToClient.writeBytes(line);
                    		
                    		
                    	}
                    	
            		} catch (Exception e){
            			e.printStackTrace();
//            			System.out.println("Error 404");
            			//hier 404 teruggeven
            			String statusLine = "404 Not Found" + "\r\n";
                		String date = "Date: " + new Date().toString() + "\r\n";
                		String responseString = file.toString();
                		FileInputStream fin = new FileInputStream(responseString);
                		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
                		String contentTypeLine = "Content-Type: text/html" + "\r\n";
                		if (!fileName.endsWith(".html")){
                			contentTypeLine = "Content-Type: \r\n";
                		}
                		outToClient.writeBytes(statusLine);
                		outToClient.writeBytes(date);
                    	outToClient.writeBytes(contentTypeLine);
                    	outToClient.writeBytes(contentLengthLine);
                    	outToClient.writeBytes("\r\n");
            		}

            		fw.close();
            		inFromClient.close();
            		outToClient.close();
            		
            	}
                
            	}
                
            }
            
            
            
            
            
            
            else if (command.equals("HEAD")){
            	//controleren of input juist is anders 404
            	if (this.host.isEmpty()){
            		//404
            		String statusLine = "404 Not Found" + "\r\n";
            		String date = "Date: " + new Date().toString() + "\r\n";
//            		String responseString = file.toString();
//            		FileInputStream fin = new FileInputStream(responseString);
//            		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
//            		String contentTypeLine = "Content-Type: text/html" + "\r\n";
//            		if (!fileName.endsWith(".html")){
//            			contentTypeLine = "Content-Type: \r\n";
//            		}
            		outToClient.writeBytes(statusLine);
            		outToClient.writeBytes(date);
//                	outToClient.writeBytes(contentTypeLine);
//                	outToClient.writeBytes(contentLengthLine);
                	outToClient.writeBytes("\r\n");
            	}
            	else {
            		
            		if (fileName.equals("/favicon.ico")){
            			
            		}
            		else{
            		
            	
            		System.out.println("hostname " + clientSocket.getLocalAddress().getHostName());
            		File file = new File("src\\file.html");
            		System.out.println(file.exists());


            		URL url = null;
            		//                url = new URL("http", host, fileName);


            		InputStream is = null;
            		BufferedReader br = null;
            		String line;
            		FileWriter fw = new FileWriter(file);


            		try {
            			if (fileName.startsWith("/")){
                    		fileName = "http:/" + fileName;
                        	
                    	}
                    	if (!fileName.endsWith("index.html")){
                    		if (fileName.endsWith("/")){
                    			fileName = fileName + "index.html";
                    		}
                    		else{
                    		fileName = fileName + "/index.html";
                    		}
                        }
//            			url = new URL("http", host, fileName);

            			System.out.println("code");

            			url = new URL(fileName);
                    	is = url.openStream();
                    	br = new BufferedReader(new InputStreamReader(is));
                    	System.out.println(url.toString());
                    	while ((line = br.readLine()) != null){
                    		System.out.println(line);
                    		fw.write(line);
                    		
                    		
                    	}
                    	
//                    	if (this.modifiedDate) //TODO
                    	
                    	//terug geven met filereader in 200
//                    	sendResponse(200, file.toString());
                    	String statusLine = "200 OK" + "\r\n";
                		String date = "Date: " + new Date().toString() + "\r\n";
                		String responseString = file.toString();
                		FileInputStream fin = new FileInputStream(responseString);
                		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
                		String contentTypeLine = "Content-Type: text/html" + "\r\n";
                		if (!fileName.endsWith(".html")){
                			contentTypeLine = "Content-Type: \r\n";
                		}
                		outToClient.writeBytes(statusLine);
                		outToClient.writeBytes(date);
                    	outToClient.writeBytes(contentTypeLine);
                    	outToClient.writeBytes(contentLengthLine);
                    	outToClient.writeBytes("\r\n");
                    	
                    	
                   	
                    	url = new URL(fileName);
                    	is = url.openStream();
                    	br = new BufferedReader(new InputStreamReader(is));
                    	System.out.println(url.toString());
                    	while ((line = br.readLine()) != null){
//                    		System.out.println(line);
                    		outToClient.writeBytes(line);
                    		
                    		
                    	}
                    	
            		} catch (Exception e){
            			e.printStackTrace();
//            			System.out.println("Error 404");
            			//hier 404 teruggeven
            			String statusLine = "404 Not Found" + "\r\n";
                		String date = "Date: " + new Date().toString() + "\r\n";
                		String responseString = file.toString();
                		FileInputStream fin = new FileInputStream(responseString);
                		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
                		String contentTypeLine = "Content-Type: text/html" + "\r\n";
                		if (!fileName.endsWith(".html")){
                			contentTypeLine = "Content-Type: \r\n";
                		}
                		outToClient.writeBytes(statusLine);
                		outToClient.writeBytes(date);
                    	outToClient.writeBytes(contentTypeLine);
                    	outToClient.writeBytes(contentLengthLine);
                    	outToClient.writeBytes("\r\n");
            		}

            		fw.close();
            		inFromClient.close();
            		outToClient.close();
            		
            	}
                
            	}
            	
            }
            else if (command.equals("PUT")){
            	
            }
            else if (command.equals("POST")){
//            	System.out.println("POST request");
//            	             do {
//            	                 currentLine = inFromClient.readLine();
//            	                                     
//            	                 if (currentLine.indexOf("Content-Type: multipart/form-data") != -1) {
//            	                   String boundary = currentLine.split("boundary=")[1];
//            	                   // The POST boundary                           
//            	           
//            	                  while (true) {
//            	                       currentLine = inFromClient.readLine();
//            	                       if (currentLine.indexOf("Content-Length:") != -1) {
//            	                           contentLength = currentLine.split(" ")[1];
//            	                           System.out.println("Content Length = " + contentLength);
//            	                           break;
//            	                       }              
//            	                   }
//            	           
//            	                   //Content length should be < 2MB
//            	                   if (Long.valueOf(contentLength) > 2000000L) {
//            	                       sendResponse(200, "File size should be < 2MB", false);
//            	                   }
//            	           
//            	                   while (true) {
//            	                       currentLine = inFromClient.readLine();
//            	                       if (currentLine.indexOf("--" + boundary) != -1) {
//            	                           filename = inFromClient.readLine().split("filename=")[1].replaceAll("\"", "");                                
//            	                           String [] filelist = filename.split("\\" + System.getProperty("file.separator"));
//            	                           filename = filelist[filelist.length - 1];                  
//            	                           System.out.println("File to be uploaded = " + filename);
//            	                           break;
//            	                       }              
//            	                   }
//            	           
//            	                   String fileContentType = inFromClient.readLine().split(" ")[1];
//            	                   System.out.println("File content type = " + fileContentType);
//            	           
//            	                   inFromClient.readLine(); //assert(inFromClient.readLine().equals("")) : "Expected line in POST request is "" ";
//            	           
//            	                   fout = new PrintWriter(filename);
//            	                   String prevLine = inFromClient.readLine();
//            	                   currentLine = inFromClient.readLine();      
//            	           
//            	                   //Here we upload the actual file contents
//            	                   while (true) {
//            	                       if (currentLine.equals("--" + boundary + "--")) {
//            	                           fout.print(prevLine);
//            	                           break;
//            	                       }
//            	                       else {
//            	                           fout.println(prevLine);
//            	                       }
//            	                       prevLine = currentLine;              
//            	                       currentLine = inFromClient.readLine();
//            	                   }
//            	           
//            	                   sendResponse(200, "File " + filename + " Uploaded..", false);
//            	                   fout.close();           
//            	                 } //if                                              
//            	            }while (inFromClient.ready()); //End of do-while
            	          
            }
            
            
		
		} catch (Exception e){
			e.printStackTrace();
			//hier 500 ??
			try {
				String statusLine = "500 Server Error" + "\r\n";
	    		String date = "Date: " + new Date().toString() + "\r\n";
//	    		String responseString = file.toString();
//	    		FileInputStream fin = new FileInputStream(responseString);
//	    		String contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
//	    		String contentTypeLine = "Content-Type: text/html" + "\r\n";
//	    		if (!fileName.endsWith(".html")){
//	    			contentTypeLine = "Content-Type: \r\n";
//	    		}
	    		outToClient.writeBytes(statusLine);
	    		outToClient.writeBytes(date);
//	        	outToClient.writeBytes(contentTypeLine);
//	        	outToClient.writeBytes(contentLengthLine);
	        	outToClient.writeBytes("\r\n");
			} catch (Exception f){
				
			}
			

		}

	}
	
	
	public void sendResponse (int statusCode, String responseString) throws Exception {

    	String statusLine = null;
    	String contentLengthLine = null;
    	String fileName = null;
    	String contentTypeLine = "Content-Type: text/html" + "\r\n";
    	FileInputStream fin = null;
    	
    	String date;

    	if (statusCode == 200){
    		statusLine = "200 OK" + "\r\n";
    		date = "Date: " + new Date().toString() + "\r\n";
    		fileName = responseString;
    		fin = new FileInputStream(fileName);
    		contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
    		if (!fileName.endsWith(".html")){
    			contentTypeLine = "Content-Type: \r\n";
    		}
    		outToClient.writeBytes(statusLine);
    		outToClient.writeBytes(date);
        	outToClient.writeBytes(contentTypeLine);
        	outToClient.writeBytes(contentLengthLine);
        	outToClient.writeBytes("\r\n");
        	sendFile(fin, outToClient);
		}
    	else if(statusCode == 500){
    		statusLine = "500 Server Error";
    	}
    	else if (statusCode == 404){
    		statusLine = "404 Not Found";
    	}
    	else if (statusCode == 304){
    		statusLine = "304 Not Modified";
    	}
    		

    	

    	
    }
	
	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
    	byte[] buffer = new byte[2048] ;
    	int bytesRead;

    	while ((bytesRead = fin.read(buffer)) != -1 ) {
    		out.write(buffer, 0, bytesRead);
    	}
    	fin.close();
    }
	
	
	
//	private boolean fileExists(String fileName){
//
//        if(fileName.endsWith("/")){
//            fileName+="INDEX.HTML";
//        }
//
//        if(fileName.startsWith("/")){
//            fileName = fileName.toUpperCase().substring(1);
//        }
//
//        //if(fileName.contains("favicon")){
//        //  return false;
//        //}
//        
//
//        File file = new File(fileName);
//        if(!file.exists()){
//            return false;
//        }
//
//        return true;  
//	}
}