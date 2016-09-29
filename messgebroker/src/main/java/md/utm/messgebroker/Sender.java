package md.utm.messgebroker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Sender {
	public static void main(String[] args) {
		String serverName = "localhost";
		int port = 9001;
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);

			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF(CreatorXML.getXmlForPublisher().getBuffer().toString());
			InputStream inFromServer = client.getInputStream();

			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
