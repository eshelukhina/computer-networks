package org.hse.lab03;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 3) {
            throw new IllegalArgumentException("Not enough arguments");
        }

        String host = args[0];
        Integer port = Integer.parseInt(args[1]);
        String fileName = args[2];

        Client client = new Client(host, port, fileName);
        client.run();
    }
}