package org.hse.lab03;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientHandler implements Runnable {
    private final Socket client;

    public ClientHandler(Socket client) throws IOException {
        this.client = client;
    }

    @Override
    public void run() {
        try (client) {
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));

            while (!input.ready()) ;

            StringBuilder request = new StringBuilder();
            while (input.ready()) {
                String nextLine = input.readLine();
                request.append(nextLine).append("\n");
            }
            String strRequest = request.toString();

            String firstLine = strRequest.split("\n")[0];
            String filePath = firstLine.split(" ")[1].substring(1);

            StringBuilder response = new StringBuilder();
            Path path = Paths.get(filePath);
            if (!Files.exists(path) || !Files.isRegularFile(path)) {
                response.append("HTTP/1.1 404 Not Found\n");
                response.append("\n");
            } else {
                String fileContent = Files.readString(path);
                response.append("HTTP/1.1 200 OK\n");
                response.append("Content-Type: text/plain; charset=utf-8\n");
                response.append("Content-Length: ").append(fileContent.length()).append("\n");
                response.append("\n");
                response.append(fileContent);
            }

            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            output.write(response.toString());
            output.flush();
        } catch (IOException ignore) {
        }
    }
}
