package md.utm.messgebroker;

import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import md.utm.messgebroker.CreatorXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.*;

public class MessageBroker extends Thread {
	private ServerSocket serverSocket;
	private Queue<Document> queue;

	public MessageBroker(int port) throws IOException {
		serverSocket = new ServerSocket(port);

		queue = new LinkedList<Document>();
	}

	public void run() {
		while (true) {
			try {
				System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
				Socket _clientSocket = serverSocket.accept();
				DataInputStream in = new DataInputStream(_clientSocket.getInputStream());
				Document doc = CreatorXML.loadXML(in.readUTF().toString());

				if (checkMessage(doc).equals("send")) {

					queue.add(doc);
				} else if (checkMessage(doc).equals("read")) {
					DataOutputStream out = new DataOutputStream(_clientSocket.getOutputStream());

					if (queue.size() != 0) {
						Document document = queue.poll();
						out.writeUTF(documentToString(document));
					} else {
						out.writeUTF(documentToString(
								CreatorXML.loadXML(CreatorXML.getXmlMsgBrok().getBuffer().toString())));
					}
				}

				_clientSocket.close();

			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				break;
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
		}
	}

	public static String checkMessage(Document doc) {
		if (doc != null) {
			Element element = doc.getDocumentElement();
			NodeList nodes = element.getChildNodes();
			String s = nodes.item(1).getTextContent().toString();
			return s;
		}
		return null;
	}

	public static void elementToStream(Element element, OutputStream out) {
		try {
			DOMSource source = new DOMSource(element);
			StreamResult result = new StreamResult(out);
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, result);
		} catch (Exception ex) {
		}
	}

	public static String documentToString(Document doc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		elementToStream(doc.getDocumentElement(), baos);
		return new String(baos.toByteArray());
	}

	public static void main(String[] args) {
		int port = 9001;
		try {
			Thread t = new MessageBroker(port);
			t.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}