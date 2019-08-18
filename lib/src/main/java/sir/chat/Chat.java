package sir.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Chat {
    String serverAddress;
    int serverTcpPort;
    int serverHttpPort;
    Socket client;
    PrintWriter printwriter;
    BufferedReader bufferedReader;

    public Chat(String address, int tcpPort, int httpPort) {
        this.serverAddress = address;
        this.serverTcpPort = tcpPort;
        this.serverHttpPort = httpPort;
    }

    public Void connect(String username, String password) {
        try {
            client = new Socket(serverAddress, serverTcpPort);

            if (client != null) {
                printwriter = new PrintWriter(client.getOutputStream(), true);
                send("{\"name\":\"" + username + "\",\"password\":\"" + password + "\"}");

                InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
            } else {
                System.out.println("Server has not bean started on port 4444.");
            }
        } catch (UnknownHostException e) {
            System.out.println("Faild to connect server " + serverAddress);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Faild to connect server " + serverAddress);
            e.printStackTrace();
        } finally {
//            Receiver receiver = new Receiver(); // Initialize chat receiver AsyncTask.
//            receiver.execute();
        }
        return null;

//
//        Socket socket = null;
//
//        try {
//            socket = new Socket(serverAddress, serverTcpPort);
//
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
//            byte[] buffer = new byte[1024];
//
//            int bytesRead;
//            InputStream inputStream = socket.getInputStream();
//
//            /*
//             * notice: inputStream.read() will block if no data return
//             */
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                byteArrayOutputStream.write(buffer, 0, bytesRead);
//                response += byteArrayOutputStream.toString("UTF-8");
//            }
//
//        } catch (UnknownHostException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response = "UnknownHostException: " + e.toString();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            response = "IOException: " + e.toString();
//        } finally {
//            if (socket != null) {
//                try {
//                    socket.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
    }

    public void setRecieveListener(RecieveListener listener) {
        Receiver receiver = new Receiver(listener);
        receiver.start();
    }

    public Void send(String message) {
        printwriter.write(message + "\n");
        printwriter.flush();

        return null;
    }

    public Void send(Message message) {
        printwriter.write(message.toJson() + "\n");
        printwriter.flush();

        return null;
    }

    public interface RecieveListener {
        void onRecieveMessage(Message message);
    }

    class Receiver extends Thread {
        RecieveListener listener;

        Receiver(RecieveListener listener) {
            this.listener = listener;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (bufferedReader.ready()) {
                        String jsonMessage = bufferedReader.readLine();
                        listener.onRecieveMessage(Message.fromJson(jsonMessage));
                    }
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }
}
