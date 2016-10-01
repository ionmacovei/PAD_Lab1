package md.utm.messgebroker;

import java.util.Queue;
import java.util.TimerTask;

/**
 * Created by imacovei on 01.10.2016.
 */
public class SaveFile extends TimerTask
{
    Queue queue;

    public SaveFile(Queue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        XmlUtil.exportToXmlFile(queue);
    }
}
