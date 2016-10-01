package md.utm.messgebroker;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;

@SuppressWarnings("restriction")
public class XmlUtil {

    public static Queue<XmlMesage> listMessages() {
        Queue<XmlMesage> msgs = new LinkedList<>();
        XmlMesage msg = new XmlMesage("command", "send", UUID.randomUUID().toString());
        XmlMesage msg2 = new XmlMesage("command", "send", UUID.randomUUID().toString());
        msgs.add(msg);
        msgs.add(msg2);
        return msgs;
    }

    public static String objectToXmlString(String command) {


        StringWriter sw = null;
        try {
            JAXBContext contextObj = JAXBContext.newInstance(XmlMesage.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            XmlMesage xmlmsg = null;
            if (command.equalsIgnoreCase("send")) {
                xmlmsg = new XmlMesage("command", "send", UUID.randomUUID().toString());
            } else if (command.equalsIgnoreCase("read")) {
                xmlmsg = new XmlMesage("command", "read");
            } else if (command.equalsIgnoreCase("forServer")) {
                xmlmsg = new XmlMesage("command", "send", "Nu sunt mesaje in coada, incercati mai tirziu");
            }
            sw = new StringWriter();
            marshallerObj.marshal(xmlmsg, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static String objectMsgToXmlString(XmlMesage msg) {


        StringWriter sw = null;
        try {
            JAXBContext contextObj = JAXBContext.newInstance(XmlMesage.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            sw = new StringWriter();
            marshallerObj.marshal(msg, sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }


    public static void exportToXmlFile(Queue objects) {
        File  msgs = new File("messages.xml");
        XmlMessages messages = new XmlMessages();
        messages.setXmlMessages(objects);
        try {
            JAXBContext contextObj = JAXBContext.newInstance(XmlMessages.class, XmlMesage.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(messages, msgs);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    public static Queue getMesagesFromFile(File f) {
        Queue<XmlMesage> msgs = null;
        XmlMessages messages;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlMessages.class, XmlMesage.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            messages = (XmlMessages) jaxbUnmarshaller.unmarshal(f);
            msgs = new LinkedList<>();
            if(messages.equals(null))
            msgs.addAll(messages.getXmlMessages());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return msgs;
    }

    public static XmlMesage deserealizeMessageObbject(String ms) {

        XmlMesage mes = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlMesage.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader sr = new StringReader(ms);
            mes = (XmlMesage) jaxbUnmarshaller.unmarshal(sr);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return mes;

    }


    public static void main(String[] args) {

        /*getXmlForPublisher();
        getXmlForSubscriber();
        Document doc = loadXML(getXmlForPublisher().getBuffer().toString());*/

        //System.out.println(getListOfXmlObjects(listMessages()));
        // System.out.println(objectToXml("read"));
        //System.out.println(objectToXml("forServer"));
       // getMesagesFromFile(getListOfXmlObjects(listMessages()));

    }

}
