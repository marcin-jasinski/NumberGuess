package com.mjasinski;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Server running...");
            while(true) {
                new GameClient(serverSocket.accept()).start();
            }
        } catch(IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
