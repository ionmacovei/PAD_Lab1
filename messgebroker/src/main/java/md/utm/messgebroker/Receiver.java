package md.utm.messgebroker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Receiver {

	public static void main(String[] args) {
		String serverName = "localhost";
		int port = 9001;
		try {
			Socket client = new Socket(serverName, port);

			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF(XmlUtil.objectToXmlString("read"));
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			XmlMesage ms = XmlUtil.deserealizeMessageObbject(in.readUTF().toString());

			System.out.println("Message from server " + ms.getMessage());
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
