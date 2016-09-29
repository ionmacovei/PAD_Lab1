package md.utm.messgebroker;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**

 */
public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected String serverText   = null;
    protected Queue<Document> queue= null;

    public WorkerRunnable(Socket clientSocket, Queue<Document> queue) {
        this.clientSocket = clientSocket;
        this.queue=queue;
    }

    public void run() {
        try {
        	DataInputStream in = new DataInputStream(clientSocket.getInputStream());
        	DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

			Document doc = CreatorXML.loadXML(in.readUTF().toString());

			if (checkMessage(doc).equals("send")) {

				queue.add(doc);
			} else if (checkMessage(doc).equals("read")) {
				
				if (queue.size() != 0) {
					Document document = queue.poll();
					out.writeUTF(documentToString(document));
				} else {
					out.writeUTF(documentToString(
							CreatorXML.loadXML(CreatorXML.getXmlMsgBrok().getBuffer().toString())));
				}
			}
			//Thread.sleep(20 * 1000);
            out.close();
            in.close();
           
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
		} /*
			 * catch (InterruptedException e) { // TODO Auto-generated catch
			 * block e.printStackTrace(); }
			 */
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
}