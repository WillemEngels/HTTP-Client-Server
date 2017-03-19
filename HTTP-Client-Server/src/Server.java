import java.io.*;
import java.net.*;

public class Server implements Runnable{
	
	protected int serverPort = 80;
	protected ServerSocket serverSocket = null;
	protected boolean isStopped = false;
	protected Thread runningThread = null;
	
	public Server(int port){
		this.serverPort = port;
	}
	
	public void run(){
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while(!isStopped()){
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e){
				if (isStopped()){
					System.out.println("Server stopped");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			new Thread(new Handler(clientSocket, "Multithreaded server")
					).start();
		}
		System.out.println("Server stopped");
	}
	
	private synchronized boolean isStopped(){
		return this.isStopped;
	}
	
	public synchronized void stop(){
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}
	
	private void openServerSocket(){
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e){
			throw new RuntimeException("Cannot open port 80", e);
		}
	}
	
	
	public static void main(String[] args){
		Server server = new Server(80);
		new Thread(server).start();
		
		
	}

//	static final String HTML_START =
//			"<html>" +
//					"<title>HTTP Server in java</title>" +
//					"<body>";
//
//	static final String HTML_END =
//			"</body>" +
//					"</html>";
//
//	Socket connectedClient = null;
//	BufferedReader inFromClient = null;
//	DataOutputStream outToClient = null;
//
//
//	public Server(Socket client) {
//		connectedClient = client;
//	}
//
//	public void run() {
//
//		try {
//
//			System.out.println( "The Client "+
//					connectedClient.getInetAddress() + ":" + connectedClient.getPort() + " is connected");
//
//			inFromClient = new BufferedReader(new InputStreamReader (connectedClient.getInputStream()));
//			outToClient = new DataOutputStream(connectedClient.getOutputStream());
//
//			String requestString = inFromClient.readLine();
//			String headerLine = requestString;
//
//			StringTokenizer tokenizer = new StringTokenizer(headerLine);
//			String httpMethod = tokenizer.nextToken();
//			String httpQueryString = tokenizer.nextToken();
//
//			StringBuffer responseBuffer = new StringBuffer();
//			responseBuffer.append("<b> This is the HTTP Server Home Page.... </b><BR>");
//			responseBuffer.append("The HTTP Client request is ....<BR>");
//
//			System.out.println("The HTTP request string is ....");
//			while (inFromClient.ready())
//			{
//				// Read the HTTP complete HTTP Query
//				responseBuffer.append(requestString + "<BR>");
//				System.out.println(requestString);
//				requestString = inFromClient.readLine();
//			}
//
//			if (httpMethod.equals("GET")) {
//				if (httpQueryString.equals("/")) {
//					// The default home page
//					sendResponse(200, responseBuffer.toString(), false);
//				} else {
//					//This is interpreted as a file name
//					String fileName = httpQueryString.replaceFirst("/", "");
//					fileName = URLDecoder.decode(fileName);
//					if (new File(fileName).isFile()){
//						sendResponse(200, fileName, true);
//					}
//					else {
//						sendResponse(404, "<b>The Requested resource not found ...." +
//								"Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false);
//					}
//				}
//			}
//			else sendResponse(404, "<b>The Requested resource not found ...." +
//					"Usage: http://127.0.0.1:5000 or http://127.0.0.1:5000/</b>", false);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void sendResponse (int statusCode, String responseString, boolean isFile) throws Exception {
//
//		String statusLine = null;
//		String serverdetails = "Server: Java HTTPServer";
//		String contentLengthLine = null;
//		String fileName = null;
//		String contentTypeLine = "Content-Type: text/html" + "\r\n";
//		FileInputStream fin = null;
//
//		if (statusCode == 200)
//			statusLine = "HTTP/1.1 200 OK" + "\r\n";
//		else
//			statusLine = "HTTP/1.1 404 Not Found" + "\r\n";
//
//		if (isFile) {
//			fileName = responseString;
//			fin = new FileInputStream(fileName);
//			contentLengthLine = "Content-Length: " + Integer.toString(fin.available()) + "\r\n";
//			if (!fileName.endsWith(".htm") && !fileName.endsWith(".html"))
//				contentTypeLine = "Content-Type: \r\n";
//		}
//		else {
//			responseString = Server.HTML_START + responseString + Server.HTML_END;
//			contentLengthLine = "Content-Length: " + responseString.length() + "\r\n";
//		}
//
//		outToClient.writeBytes(statusLine);
//		outToClient.writeBytes(serverdetails);
//		outToClient.writeBytes(contentTypeLine);
//		outToClient.writeBytes(contentLengthLine);
//		outToClient.writeBytes("Connection: close\r\n");
//		outToClient.writeBytes("\r\n");
//
//		if (isFile) sendFile(fin, outToClient);
//		else outToClient.writeBytes(responseString);
//
//		outToClient.close();
//	}
//
//	public void sendFile (FileInputStream fin, DataOutputStream out) throws Exception {
//		byte[] buffer = new byte[1024] ;
//		int bytesRead;
//
//		while ((bytesRead = fin.read(buffer)) != -1 ) {
//			out.write(buffer, 0, bytesRead);
//		}
//		fin.close();
//	}
//
//	public static void main (String args[]) throws Exception {
//
//		ServerSocket Server = new ServerSocket (5000, 10, InetAddress.getByName("127.0.0.1"));
//		System.out.println ("TCPServer Waiting for client on port 5000");
//
//		while(true) {
//			Socket connected = Server.accept();
//			(new Server(connected)).start();
//		}
//	}
}

