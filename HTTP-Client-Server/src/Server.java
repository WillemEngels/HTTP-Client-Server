
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


}

