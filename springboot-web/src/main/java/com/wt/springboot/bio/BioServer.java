package com.wt.springboot.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pat.Wu
 * @date 2021/7/19 17:15
 * 说明:
 */
public class BioServer {
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private ExecutorService bizExecutor= Executors.newFixedThreadPool(8);;

    public void start() throws Exception {

        Runnable initTask = new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(9999);
                    executor.submit(new ServerThread(serverSocket));
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    executor.shutdown();
                }

            }
        };
        new Thread(initTask).start();
    }


    private class ServerThread implements Runnable {

        private ServerSocket serverSocket;

        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while (true) {
                Socket socket =null;
                try {
                    socket=serverSocket.accept();
                    bizExecutor.submit(new HttpWebTask(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class HttpWebTask implements Runnable {
        private Socket socket;

        public HttpWebTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line = null;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BioServer bioServer = new BioServer();
        bioServer.start();
        System.in.read();
    }
}
