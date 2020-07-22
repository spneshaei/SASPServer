package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class BankAPI {
    public static final int PORT = 2222;
    public static final String IP = "127.0.0.1";

    private static DataOutputStream outputStream;
    private static DataInputStream inputStream;

    public static void tellBankAndReceiveResponse(String message, ResponseListener listener) {
        try {
            Socket socket = new Socket(IP, PORT);
            outputStream = new DataOutputStream(socket.getOutputStream());
            inputStream = new DataInputStream(socket.getInputStream());
            new Thread(() -> {
                try {
                    outputStream.writeUTF(message);
                    System.out.println("Wrote Message to Bank: " + message);
                    while (true) {
                        String response = inputStream.readUTF();
                        if (response.equals("")) continue;
                        System.out.println("Received response from Bank: " + response);
                        listener.responseReceived(response);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Exception while initiating connection: " + e.getLocalizedMessage());
        }
    }
}

interface ResponseListener {
    void responseReceived(String response);
}