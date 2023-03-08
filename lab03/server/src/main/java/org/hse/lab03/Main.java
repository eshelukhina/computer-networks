package org.hse.lab03;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Must be only one argument");
        }

        Integer concurrencyLevel = Integer.parseInt(args[0]);

        MultithreadedServer server = new MultithreadedServer(concurrencyLevel);
        server.run();
    }
}
