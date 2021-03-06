package SER516_Lab2_Client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * ClientThread.java-a class to govern reading data stream from serversocket
 * depending on the frequency defined by the user via GUI.
 *
 * @author Shaunak Shah
 * @author Chiranjeevi Ramamurthy
 * @version 1.0
 */

public class ClientThread implements Runnable {

    final String hostName = "localhost";
    final int portNumber = Consts.PORT_NUMBER;
    private InputStream inputStream;
    private OutputStream outputStream;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    public Socket clientSocket;
    private int channels;
    private int channelCount;
    private int frequency;


    public ClientThread(int channelCount, int frequency) {

        this.channels = channelCount;
        this.frequency = frequency;
    }

    /**
     * Creates client socket establishes connection with the server. Reads data received from the server at frequency
     * mentioned earlier and passes it to handler for making changes to UI accordingly.
     */

    @Override
    public void run() {
        try {
            clientSocket = new Socket(hostName, portNumber);
            outputStream = clientSocket.getOutputStream();
            inputStream = clientSocket.getInputStream();
            dataInputStream = new DataInputStream(inputStream);
            dataOutputStream = new DataOutputStream(outputStream);
            sendChannelNumber(dataOutputStream);

            while (true) {
                boolean isClientClosed = false;
                try {
                    String data = "";
                    if(dataInputStream.available() > 0) {
                        data = dataInputStream.readUTF();
                    }
                    Handlers.getInstance().updateUI(data);
                    Thread.sleep(1000 / this.frequency);
                } catch (IOException |InterruptedException e) {
                    isClientClosed = true;
                    dataInputStream.close();
                    System.out.println("Client Connection closed");
                }
                if (isClientClosed)
                    break;
            }
        }catch (SocketException e){
            System.out.println("Server Connection closed");
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends the number of channels to the server so that server can send the data back for specified channel
     * number
     */

    private void sendChannelNumber(DataOutputStream outputStream) {
        try {
            outputStream.writeUTF(Integer.toString(channels));
        } catch (Exception e) {
            System.out.println("Unable to send channel value to the stream");
        }
    }
}
