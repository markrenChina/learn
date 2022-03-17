package com.ccand99.rxjava1.webserver.version2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientConnection implements Runnable {

    public static final byte[] RESPONSE = (
            "HTTP/1.1 200 OK\r\n" +
                    "Content-length: 2\r\n" +
                    "\r\n" +
                    "OK"
    ).getBytes();

    public static final byte[] SERVICE_UNAVAILABLE =
            "HTTP/1.1 503 Service unavailable\r\n".getBytes();

    private final Socket client;

    public ClientConnection(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()){
                readFullRequest();
                client.getOutputStream().write(RESPONSE);
            }
        }catch (Exception e){
            e.printStackTrace();
            try {
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void readFullRequest() throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(client.getInputStream()));
        String line = reader.readLine();
        while (line != null && !line.isEmpty()){
            System.out.println(line);
            line = reader.readLine();
        }
    }

    public void serviceUnavailable(){
        try {
            client.getOutputStream().write(SERVICE_UNAVAILABLE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
