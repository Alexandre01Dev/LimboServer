package be.alexandre01.limbo;


import be.alexandre01.limbo.connection.TCPServer;

import java.util.Scanner;

/*
 ↬   Made by Alexandre01Dev 😎
 ↬   done on 17/06/2024 at 17:23
*/
public class Main {

    public static int increment = 0;
    public static void main(String[] args) {
        System.out.println("Hello world!");

        int port = 25566;
        String s = System.getProperty("port");
        if(s != null){
            try {
                port = Integer.parseInt(s);
            }catch (NumberFormatException e){
                System.out.println("Invalid port");
            }
        }

        new TCPServer(port);

        Scanner scanner = new Scanner(System.in);
        while (true){
            int line = scanner.nextInt();
            increment = line;
        }
    }
}