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

public class Subscriber {

	public static void main(String[] args) {
		String serverName = "localhost";
		int port = 9001;
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket client = new Socket(serverName, port);

			OutputStream outToServer = client.getOutputStream();
			DataOutputStream out = new DataOutputStream(outToServer);

			out.writeUTF(CreatorXML.getXmlForSubscriber().getBuffer().toString());
			InputStream inFromServer = client.getInputStream();
			DataInputStream in = new DataInputStream(inFromServer);
			Document doc = CreatorXML.loadXML(in.readUTF().toString());

			System.out.println("Server says " + checkMessage(doc));
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String checkMessage(Document doc) {
		if (doc != null) {
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();
			String s = nodes.item(2).getTextContent().toString();
			return s;
		}
		return null;
	}
}
