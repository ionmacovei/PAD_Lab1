package md.utm.messgebroker;

import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

@SuppressWarnings("restriction")
public class CreatorXML {

	public static StringWriter getXmlForPublisher() {
		try {
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument("UTF-8", "1.0");// xMLStreamWriter.writeStartDocument();
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

			// System.out.println(xmlString);
			return stringWriter;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static StringWriter getXmlMsgBrok() {
		try {
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument("UTF-8", "1.0");// xMLStreamWriter.writeStartDocument();
			xMLStreamWriter.writeStartElement("XmlMessage");

			xMLStreamWriter.writeStartElement("type");
			xMLStreamWriter.writeCharacters("command");
			xMLStreamWriter.writeEndElement();

			xMLStreamWriter.writeStartElement("command");
			xMLStreamWriter.writeCharacters("send");
			xMLStreamWriter.writeEndElement();

			xMLStreamWriter.writeStartElement("message");
			xMLStreamWriter.writeCharacters("nu sunt mesaje");
			xMLStreamWriter.writeEndElement();

			xMLStreamWriter.writeEndElement();
			xMLStreamWriter.writeEndDocument();

			xMLStreamWriter.flush();
			xMLStreamWriter.close();

			String xmlString = stringWriter.getBuffer().toString();

			stringWriter.close();

			// System.out.println(xmlString);
			return stringWriter;
		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static StringWriter getXmlForSubscriber() {
		try {
			StringWriter stringWriter = new StringWriter();

			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
			XMLStreamWriter xMLStreamWriter = xMLOutputFactory.createXMLStreamWriter(stringWriter);

			xMLStreamWriter.writeStartDocument("UTF-8", "1.0");

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
			return stringWriter;

		} catch (XMLStreamException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Document loadXML(String xml) {
		try {
			DocumentBuilderFactory fctr = DocumentBuilderFactory.newInstance();
			DocumentBuilder bldr = fctr.newDocumentBuilder();
			InputSource insrc = new InputSource(new StringReader(xml));
			Document document = bldr.parse(insrc);

			return document;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void xmlread() {

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			DocumentBuilder builder = factory.newDocumentBuilder();
			StringBuilder xmlStringBuilder = new StringBuilder();
			xmlStringBuilder.append(getXmlForPublisher().getBuffer().toString());
			ByteArrayInputStream input;

			input = new ByteArrayInputStream(xmlStringBuilder.toString().getBytes("UTF-8"));
			Document document = builder.parse(input);
			Element element = document.getDocumentElement();

			// get all child nodes
			NodeList nodes = element.getChildNodes();

			// print the text content of each child
			for (int i = 0; i < nodes.getLength(); i++) {
				System.out.println("" + nodes.item(i).getTextContent());
			}
			/*
			 * System.out.println(doc.getAttributes().toString());
			 * doc.toString();
			 */

		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		getXmlForPublisher();
		getXmlForSubscriber();
		Document doc = loadXML(getXmlForPublisher().getBuffer().toString());

	}

}
