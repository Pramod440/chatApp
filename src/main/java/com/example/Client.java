package com.example;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            System.out.println("Connected to chat server");

            new ReadThread(socket).start();
            new WriteThread(socket).start();

        } catch (IOException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
    }

    static class ReadThread extends Thread {
        private BufferedReader in;

        ReadThread(Socket socket) throws IOException {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }

        public void run() {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        }
    }

    static class WriteThread extends Thread {
        private PrintWriter out;
        private BufferedReader console;

        WriteThread(Socket socket) throws IOException {
            out = new PrintWriter(socket.getOutputStream(), true);
            console = new BufferedReader(new InputStreamReader(System.in));
        }

        public void run() {
            try {
                String message;
                while ((message = console.readLine()) != null) {
                    out.println(message);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server.");
            }
        }
    }
}

