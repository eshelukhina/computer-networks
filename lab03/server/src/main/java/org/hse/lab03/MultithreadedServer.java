package org.hse.lab03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class MultithreadedServer {
    private final int PORT = 8080;
    private final int concurrencyLevel;

    public MultithreadedServer(Integer concurrencyLevel) {
        this.concurrencyLevel = concurrencyLevel;
    }

    public void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(concurrencyLevel);

            while (true) {
                Socket client = server.accept();
                threadPool.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}