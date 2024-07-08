package test;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class MyServer{

    ClientHandler ch;
    Integer port;
    boolean stop;
    BufferedReader in;
    PrintWriter out;

    MyServer(int port, ClientHandler ch) {
        this.ch = ch;
        this.port = port;;
    }
    public void start() {
        stop = false;
        new Thread(() -> {
            try {
                startServer();
            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    void startServer() throws SocketException {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(1000);
            while (!stop) {
                try {
                    Socket client = server.accept();
                    try {
                        ch.handleClient(client.getInputStream(), client.getOutputStream());
                        client.getInputStream().close();
                        client.getOutputStream().close();
                        client.close();
                    } catch (IOException e) {
                    }
                } catch(SocketTimeoutException e) {}

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
        public void close()
        {
            stop = true;
        }


   }