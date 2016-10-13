package md.utm.messgebroker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Queue;

/**

 */
public class WorkerRunnable implements Runnable {

    protected Socket clientSocket = null;
    protected String serverText = null;
    protected Queue<XmlMesage> queue = null;

    public WorkerRunnable(Socket clientSocket, Queue<XmlMesage> queue) {
        this.clientSocket = clientSocket;
        this.queue = queue;
    }

    public void run() {
        try {
            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            XmlMesage msc = XmlUtil.deserealizeMessageObbject(in.readUTF().toString());

            if (msc.getCommand().equals("send")) {

                queue.add(msc);
            } else if (msc.getCommand().equals("read")) {

                if (queue.size() != 0) {
                    XmlMesage mesage = queue.poll();
                    out.writeUTF(XmlUtil.objectMsgToXmlString(mesage));
                } else {
                    out.writeUTF(XmlUtil.objectToXmlString("forServer"));
                }
            }
            //Thread.sleep(20 * 1000);
            out.close();
            in.close();

        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

}