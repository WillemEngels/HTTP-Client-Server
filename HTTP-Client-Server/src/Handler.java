import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Handler implements Runnable{

	private Socket clientSocket;
	private BufferedReader inFromClient;
	private DataOutputStream outToClient;
	private String host;
	private String modifiedDate = "";
	private String contentLength = "";
	private String contentType = "";




	public Handler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}


	@Override
	public void run() {

		try  {
			inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			outToClient =  new DataOutputStream(clientSocket.getOutputStream());




			String cmd = inFromClient.readLine();
			String[] Command = cmd.split(" ");
			String command = Command[0];
			String fileName = Command[1];
			String httpVersion = Command[2];
			String newLine = ".";
			while (!(newLine = inFromClient.readLine()).equals("")) {
				newLine = inFromClient.readLine();
				if (newLine.startsWith("Host: ")){
					this.host = newLine.substring(6);
				}
				if (newLine.startsWith("If-Modified-Since: ")){
					this.modifiedDate = newLine.substring(19);
				}
				if (newLine.startsWith("Content-Length: ")){
					this.contentLength = newLine;
				}
				if (newLine.startsWith("Content-Type: ")){
					this.contentType = newLine;
				}
						
				
			}

			if ( command.equals("GET"))
			{

				if (this.host.isEmpty()){
					String statusLine = "404 Not Found" + "\r\n";
					String date = "Date: " + new Date().toString() + "\r\n";
					outToClient.writeBytes(statusLine);
					outToClient.writeBytes(date);
					outToClient.writeBytes("\r\n");
				}
				else {

					if (fileName.equals("/favicon.ico")){

					}
					else{


						System.out.println("hostname " + clientSocket.getLocalAddress().getHostName());
						File file = new File("src/ServerHTML.html");
						System.out.println(file.exists());


						URL url = null;
						
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
							
							
							url = new URL(fileName);
							is = url.openStream();
							br = new BufferedReader(new InputStreamReader(is));
							while ((line = br.readLine()) != null){
								System.out.println(line);
								fw.write(line);


							}

							if (!this.modifiedDate.equals("")) {
								if (HasNotBeenModifiedSince(modifiedDate, file.lastModified())){


									//304 Not Modified
									String statusLine = "304 Not Modified" + "\r\n";
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
										outToClient.writeBytes(line);
									}
									fin.close();
								}
							}
							else{
								//sendResponse(200, file.toString());
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
									outToClient.writeBytes(line);


								}

								fin.close();
							}
						} catch (Exception e){
							e.printStackTrace();
							//System.out.println("Error 404");
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

							fin.close();
						}

						fw.close();
						inFromClient.close();
						outToClient.close();

					}

				}

			}






			else if (command.equals("HEAD")){
				if (this.host.isEmpty()){
					//404
					String statusLine = "404 Not Found" + "\r\n";
					String date = "Date: " + new Date().toString() + "\r\n";
					outToClient.writeBytes(statusLine);
					outToClient.writeBytes(date);
					outToClient.writeBytes("\r\n");
				}
				else {

					if (fileName.equals("/favicon.ico")){

					}
					else{


						File file = new File("src\\file.html");
						URL url = null;
						

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
							
							url = new URL(fileName);
							is = url.openStream();
							br = new BufferedReader(new InputStreamReader(is));
							System.out.println(url.toString());
							while ((line = br.readLine()) != null){
								System.out.println(line);
								fw.write(line);


							}

							if (!this.modifiedDate.equals("")) {
								if (HasNotBeenModifiedSince(modifiedDate, file.lastModified())){


									//304 Not Modified
									String statusLine = "304 Not Modified" + "\r\n";
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
									fin.close();
								}
							}
							else{

								//sendResponse(200, file.toString());
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
									outToClient.writeBytes(line);


								}
								fin.close();
							}
						} catch (Exception e){
							e.printStackTrace();
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
							fin.close();
						}

						fw.close();
						inFromClient.close();
						outToClient.close();

					}

				}

			}
			else if (command.equals("PUT")){
				

                try
                {
                    byte [] buffer = new byte[20000];
                    String str = inFromClient.readLine();
                  
                    FileOutputStream fos = null;
                    
                    fos = new FileOutputStream(new URI(this.host + fileName).toString());
                    

                    Integer bytesRead = 0;
                    
                    str = inFromClient.readLine();

                    bytesRead = Integer.parseInt(str);
                    str = inFromClient.readLine();
                    buffer = str.getBytes();
                    fos.write(buffer, 0, bytesRead);
                    while (bytesRead == 20000);

                  //sendResponse(200, file.toString());
					String statusLine = "200 OK" + "\r\n";
					String date = "Date: " + new Date().toString() + "\r\n";
					outToClient.writeBytes(statusLine);
					outToClient.writeBytes(date);
					

                    fos.close();
                }

                catch(Exception e)
                {
                	e.printStackTrace();
					//hier 404 teruggeven
					String statusLine = "404 Not Found" + "\r\n";
					String date = "Date: " + new Date().toString() + "\r\n";
					outToClient.writeBytes(statusLine);
					outToClient.writeBytes(date);
					
                }
				

			}
			
			
			else if (command.equals("POST")){
				System.out.println("POST request");
				
				try {

				String currentLine = inFromClient.readLine();

				String boundary = this.contentType.split("boundary=")[1];


				
				String filename;
				while (true) {
					currentLine = inFromClient.readLine();
					if (currentLine.indexOf("--" + boundary) != -1) {
						filename = inFromClient.readLine().split("filename=")[1].replaceAll("\"", "");                                
						String [] filelist = filename.split("\\" + System.getProperty("file.separator"));
						filename = filelist[filelist.length - 1];                  
						System.out.println("File to be uploaded = " + filename);
						break;
					}              
				}

				String fileContentType = inFromClient.readLine().split(" ")[1];
				System.out.println("File content type = " + fileContentType);

				inFromClient.readLine(); 

				PrintWriter fout = new PrintWriter(filename);
				String prevLine = inFromClient.readLine();
				currentLine = inFromClient.readLine();      

				while (true) {
					if (currentLine.equals("--" + boundary + "--")) {
						fout.print(prevLine);
						break;
					}
					else {
						fout.println(prevLine);
					}
					prevLine = currentLine;              
					currentLine = inFromClient.readLine();
				}



				String statusLine = "200 OK" + "\r\n";
				String date = "Date: " + new Date().toString() + "\r\n";
				outToClient.writeBytes(statusLine);
				outToClient.writeBytes(date);
				outToClient.writeBytes("\r\n");



				
				fout.close();           

				} catch (Exception e){
					e.printStackTrace();
					//hier 404 teruggeven
					String statusLine = "404 Not Found" + "\r\n";
					String date = "Date: " + new Date().toString() + "\r\n";
					outToClient.writeBytes(statusLine);
					outToClient.writeBytes(date);
					outToClient.writeBytes("\r\n");
					
				}
			}



		} catch (Exception e){
			e.printStackTrace();
			//hier 500 ??
			try {
				String statusLine = "500 Server Error" + "\r\n";
				String date = "Date: " + new Date().toString() + "\r\n";
				outToClient.writeBytes(statusLine);
				outToClient.writeBytes(date);
				outToClient.writeBytes("\r\n");
			} catch (Exception f){

			}


		}

	}


	private boolean HasNotBeenModifiedSince(String modifiedCeckDate, long lastModified) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM YYYY");
		if (sdf.parse(modifiedCeckDate).getTime() < lastModified){
			return true;
		}
		
		return false;
	}


	


}