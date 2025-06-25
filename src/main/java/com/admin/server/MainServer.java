package com.admin.server;

import com.admin.server.network.ClientHandler;
import com.admin.shared.config.Config;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {
        public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(Config.THREAD_POOL_SIZE);

        try (ServerSocket serverSocket = new ServerSocket(Config.SERVER_PORT)) {
            System.out.println("Server đang chạy tại IP: " + Config.SERVER_HOST + " | PORT: " + Config.SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                String clientId = clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort();
                System.out.println("Client kết nối từ " + clientId);

                ClientHandler handler = new ClientHandler(clientSocket);
                executor.submit(handler);
            }

        } catch (IOException e) {
            System.err.println("Lỗi khởi động server: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }
}
