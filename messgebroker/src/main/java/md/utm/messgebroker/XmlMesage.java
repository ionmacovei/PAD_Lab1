package md.utm.messgebroker;

/**
 * Created by imacovei on 30.09.2016.
 */

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class XmlMesage {

    private String type;
    private String command;
    private String message;

    public XmlMesage(String type, String command, String message) {
        this.type = type;
        this.command = command;
        this.message = message;
    }

    public XmlMesage(String type, String command) {
        this.type = type;
        this.command = command;
    }

    public String getType() {
        return type;
    }
    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getCommand() {
        return command;
    }
    @XmlElement
    public void setCommand(String command) {
        this.command = command;
    }

    public String getMessage() {
        return message;
    }
    @XmlElement
    public void setMessage(String message) {
        this.message = message;
    }
}


