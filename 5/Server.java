import java.io.*; 
import java.net.*;

public class Server {
	public static void main(String args[]) 
	{ 
		int port = 6789;
		Server server = new Server( port );
		server.startServer(); 
	}
	ServerSocket echoServer = null;
	Socket clientSocket = null;
	int numConnections = 0; 
	int port;
	public Server( int port ) 
	{
		this.port = port;
	}
	public void stopServer() 
	{
		System.out.println( "Server cleaning up." ); System.exit(0);
	}
	public void startServer() 
	{

		try 
		{
			echoServer = new ServerSocket(port);
		}
		catch (IOException e) 
		{
			System.out.println(e);
		}
		System.out.println( "Server is started and is waiting for connections." ); 
		System.out.println( "With multi-threading, multiple connections are allowed." ); System.out.println( "Any client can send -1 to stop the server." );
		while ( true ) 
		{
			try {
				clientSocket = echoServer.accept();
				numConnections ++;
				Server2Connection oneconnection = new Server2Connection(clientSocket, numConnections, this);
				new Thread(oneconnection).start();
			}
			catch (IOException e) 
			{ 
				System.out.println(e);
			}

		}
	}
}
class Server2Connection implements Runnable 
{ 
	BufferedReader is;
	PrintStream os; Socket clientSocket; int id;
	Server server;
	public Server2Connection(Socket clientSocket, int id, Server server) 
	{ 
		this.clientSocket = clientSocket;
		this.id = id; this.server = server;
		System.out.println( "Connection " + id + " established with: " + clientSocket ); 
		try 
		{
			is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			os = new PrintStream(clientSocket.getOutputStream());
		} catch (IOException e) 
		{ 
			System.out.println(e);
		}
	}
	public void run() 
	{ 
		String line;
		try {
			boolean serverStop = false;
			while (true) 
			{
				line = is.readLine(); 
				int n = Integer.parseInt(line);
				if ( n == -1 ) 
				{
					serverStop = true;
					break;
				}
				if ( n == 0 ) 
					break;
				os.println(" " + n*n ); 
				System.out.println( "Received " + line + " from Connection " + id + "=" + n*n );
			}
			System.out.println( "Connection " + id + " closed." );
			is.close();
			os.close(); clientSocket.close();
			if ( serverStop ) 
			{
				server.stopServer();
			}
		} 
		catch (IOException e) 
		{ 
			System.out.println(e);
		}
	}
}
