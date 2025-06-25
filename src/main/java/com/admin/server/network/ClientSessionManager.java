package com.admin.server.network;

import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientSessionManager {
    private static final Map<String, Socket> onlineClients = new ConcurrentHashMap<>();

    public static void registerClient(String clientId, Socket socket) {
        onlineClients.put(clientId, socket);
        System.out.println("+ Client kết nối: " + clientId + " | Tổng online: " + onlineClients.size());
    }

    public static void removeClient(String clientId) {
        onlineClients.remove(clientId);
        System.out.println("- Client ngắt kết nối: " + clientId + " | Còn lại: " + onlineClients.size());
    }

    public static int getOnlineCount() {
        return onlineClients.size();
    }

    public static Map<String, Socket> getAllClients() {
        return onlineClients;
    }
}
