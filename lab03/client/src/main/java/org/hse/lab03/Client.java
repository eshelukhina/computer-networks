package org.hse.lab03;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private final Socket socket;
    private final String filePath;

    public Client(String host, Integer port, String fileName) throws IOException {
        socket = new Socket(InetAddress.getByName(host), port);
        this.filePath = fileName;
    }

    public void run() {
        try (socket) {
            StringBuilder request = new StringBuilder();
            request.append("GET /" + filePath + " HTTP/1.1\n");
            request.append("\n");

            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            output.write(request.toString());
            output.flush();

            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (!input.ready()) ;

            while (input.ready()) {
                String nextLien = input.readLine();
                System.out.println(nextLien);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
