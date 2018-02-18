package com.mjasinski;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends JFrame {

    private static String hostAddress;
    private static int port;

    public static void main(String[] args) {

        Scanner connectionScanner = new Scanner(System.in);
        System.out.println("Connecting to the server...");
        System.out.print("Host address: ");
        hostAddress = connectionScanner.nextLine();
        System.out.print("Port: ");
        port = connectionScanner.nextInt();

        try (Socket socket = new Socket(hostAddress, port)) {
            BufferedReader serverOutputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connection established.");
            System.out.println("\n<== GUESS THE NUMBER ==>");

            Scanner scanner = new Scanner(System.in);
            String userInput;
            String serverResponse;
            int attempts = 0;

            while(true){
                System.out.println("Enter your guess: ");
                userInput = scanner.nextLine();
                if(!(userInput.chars().allMatch( Character::isDigit ))){
                    System.out.println("Wrong input. You can only type numbers in here."); continue; }
                if(userInput.equals("exit")){ break; }
                clientOutput.println(userInput);

                serverResponse = serverOutputReader.readLine();
                System.out.println(serverResponse);
                if(serverResponse.equals("You've won!")){
                    clientOutput.println("exit");
                    System.out.println("Total attempts: " + ++attempts);
                    System.out.println("Exiting...");
                    break;
                }

                attempts++;
            }

        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
