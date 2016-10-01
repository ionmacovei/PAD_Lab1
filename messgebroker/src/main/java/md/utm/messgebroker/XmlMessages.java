package md.utm.messgebroker;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Queue;

/**
 * Created by imacovei on 30.09.2016.
 */

@XmlRootElement(name = "XmlMessages")
public class XmlMessages {

    Queue xmlMessages;

    public Queue getXmlMessages() {
        return xmlMessages;
    }

    @XmlElement(name = "Message")

    public void setXmlMessages(Queue xmlMessages)

    {
        this.xmlMessages = xmlMessages;
    }
}
