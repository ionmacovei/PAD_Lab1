package md.utm.messgebroker;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageBroker implements Runnable {
    private ServerSocket serverSocket;
    private int serverPort = 9001;
    private Queue<XmlMesage> queue;
    private boolean isStopped = false;
    private Long startTime = null;
    private Thread runningThread = null;
    private File f = null;

    public MessageBroker(int port) throws IOException {

        this.serverPort = port;
        queue = new ConcurrentLinkedQueue<XmlMesage>();
        if (fileExist()) {
            Queue q = XmlUtil.getMesagesFromFile(f);
            if (q.equals(null)) {
                queue.addAll(q);
            }

        }
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

    public void run() {
        synchronized (this) {
            this.runningThread = Thread.currentThread();
        }
        openServerSocket();
        saveQueueToFile();

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

    public Boolean fileExist() {
        f = new File("messages.xml");
        if (f.exists()) return true;
        return false;
    }

    public void saveQueueToFile() {
        SaveFile task = new SaveFile(queue);
        Timer timer = new Timer();
        timer.schedule(task, 0, 6000);

    }
}
