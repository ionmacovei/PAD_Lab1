package md.utm.messgebroker;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.UUID;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

@SuppressWarnings("restriction")
public class CreatorXML {

	public static void getXmlForPublisher() {
		try {
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("XmlMessage");
            
			
			xMLStreamWriter.writeStartElement("type");
			xMLStreamWriter.writeCharacters("command");
			xMLStreamWriter.writeEndElement();
			
			xMLStreamWriter.writeStartElement("command");
			xMLStreamWriter.writeCharacters("send");
			xMLStreamWriter.writeEndElement();

			xMLStreamWriter.writeStartElement("message");
			xMLStreamWriter.writeCharacters(UUID.randomUUID().toString());
			xMLStreamWriter.writeEndElement();

			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();

			xMLStreamWriter.flush();
			xMLStreamWriter.close();

			String xmlString = stringWriter.getBuffer().toString();

			stringWriter.close();

			System.out.println(xmlString);

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void getXmlForSubscriber() {
		try {
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("XmlMessage");
            
			
			xMLStreamWriter.writeStartElement("type");
			xMLStreamWriter.writeCharacters("command");
			xMLStreamWriter.writeEndElement();
			
			xMLStreamWriter.writeStartElement("command");
			xMLStreamWriter.writeCharacters("read");
			xMLStreamWriter.writeEndElement();
			
			
			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();

			xMLStreamWriter.flush();
			xMLStreamWriter.close();

			String xmlString = stringWriter.getBuffer().toString();

			stringWriter.close();

			System.out.println(xmlString);

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String [] args){
		
		getXmlForPublisher();
		getXmlForSubscriber();
		// TODO Auto-generated method stub
		
	}
	
	
}
