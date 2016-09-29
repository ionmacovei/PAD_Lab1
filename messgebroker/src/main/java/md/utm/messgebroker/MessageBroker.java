package md.utm.messgebroker;

import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

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

public class MessageBroker implements Runnable {
	private ServerSocket serverSocket;
	protected int serverPort = 9001;
	private Queue<Document> queue;
	protected boolean isStopped = false;
	protected Thread runningThread = null;

	public MessageBroker(int port) throws IOException {

		this.serverPort = port;

		queue = new ConcurrentLinkedQueue<Document>();
	}

	public void run() {
		synchronized (this) {
			this.runningThread = Thread.currentThread();
		}
		openServerSocket();
		while (!isStopped()) {

			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
			Socket clientSocket = null;
			try {
				clientSocket = this.serverSocket.accept();
			} catch (IOException e) {
				if (isStopped()) {
					System.out.println("Server Stopped.");
					return;
				}
				throw new RuntimeException("Error accepting client connection", e);
			}
			new Thread(new WorkerRunnable(clientSocket, queue)).start();
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
			MessageBroker server = new MessageBroker(port);
			new Thread(server).start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void openServerSocket() {
		try {
			this.serverSocket = new ServerSocket(this.serverPort);
		} catch (IOException e) {
			throw new RuntimeException("Cannot open port (9001", e);
		}
	}

	private synchronized boolean isStopped() {
		return this.isStopped;
	}

	public synchronized void stop() {
		this.isStopped = true;
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			throw new RuntimeException("Error closing server", e);
		}
	}
}
