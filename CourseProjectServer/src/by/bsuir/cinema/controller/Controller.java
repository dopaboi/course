package by.bsuir.cinema.controller;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

public class Controller implements Runnable{
    private static ServerSocket server;
    private static Socket connection;
    private static InputStream input;
    private final Object lock = new Object();

    public static void main(String[] args) throws IOException {
        server = new ServerSocket(3351, 10);
        for (int i = 0; i < 10; i++){
            new Thread(new Controller()).start();
        }
    }

    @Override
    public void run() {
        try {
            FileWriter fileWriter = new FileWriter("out.txt", true);
            System.out.println("Ожидание клиента!");
            connection = server.accept();
            while (true) {
                System.out.println("Клиент подключился!");
                input = connection.getInputStream();
                DataInputStream dataInputStream = new DataInputStream(input);
                String string = dataInputStream.readUTF();
                synchronized (lock) {
                    fileWriter.write(string + " " + LocalDateTime.now() + "\n");
                }
                fileWriter.flush();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
