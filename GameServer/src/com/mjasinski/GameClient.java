package com.mjasinski;

import java.io.*;
import java.net.Socket;

public class GameClient extends Thread {

    private Socket socket;

    public GameClient(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("New client connected. Remote socket address: " + socket.getRemoteSocketAddress());

            Game guessTheNumber = new Game();
            guessTheNumber.start();
            System.out.println("Hint: drawn number is " + guessTheNumber.getDrawnNumber());
            int userNumber;

            while(true){
                String recievedString = input.readLine();
                userNumber = Integer.parseInt(recievedString);

                if(userNumber == guessTheNumber.getDrawnNumber()){
                    output.println("You've won!");
                    break;
                } else {
                    if(guessTheNumber.getDivisors().contains(userNumber)){
                        output.println("You've guessed one of the divisors!");
                    } else {
                        output.println("Nope, try again...");
                    }
                }
            }

            System.out.println("Client " + socket.getRemoteSocketAddress() + " disconnected.");

        } catch(IOException e) {
            System.out.println("Oops, I've lost connection: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch(IOException e) {
                System.out.println("Error while closing socket: " + e.getMessage());
            }
        }
    }
}

