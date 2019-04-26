package ru.geekbrains.lesson6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

            Socket socket = new Socket("localhost", 7777);

            Thread recieveMsg  = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        DataInputStream in = new DataInputStream(socket.getInputStream());
                        while (true) {
                            try {
                                System.out.println("Новое сообщение > " + in.readUTF());
                            } catch (IOException ex) {
                                ex.printStackTrace();
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            recieveMsg.start();

            Thread sendMsg = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Scanner scanner = new Scanner(System.in);
                        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                        System.out.print("Введите сообщение > ");
                        while (scanner.hasNextLine()) {
                            System.out.print("Введите сообщение > ");
                            String line = scanner.nextLine();
                            out.writeUTF(line);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            sendMsg.start();
    }
}
