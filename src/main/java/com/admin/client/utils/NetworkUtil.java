package com.admin.client.utils;

import com.admin.shared.config.Config;
import com.admin.shared.protocol.Request;
import com.admin.shared.protocol.Response;
import com.admin.shared.util.JsonUtils;

import java.io.*;
import java.net.Socket;

public class NetworkUtil implements AutoCloseable{
    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public NetworkUtil() throws IOException {
        socket = new Socket(Config.SERVER_HOST, Config.SERVER_PORT);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String json) throws IOException {
        if (json == null || json.trim().isEmpty()) {
            throw new IOException("Không thể gửi dữ liệu rỗng (null hoặc empty JSON).");
        }
        writer.write(json);
        writer.newLine();
        writer.flush();
    }

    public String receive() throws IOException {
        return reader.readLine();
    }

    public static Response send(Request request) {
        try (NetworkUtil net = new NetworkUtil()) {
            String json = JsonUtils.toJson(request);
            System.out.println("[Client gửi]: " + json);

            net.send(json);

            String responseJson = net.receive();
            System.out.println("[Client nhận]: " + responseJson);

            return JsonUtils.fromJson(responseJson, Response.class);
        } catch (IOException e) {
            System.err.println("Lỗi khi gửi request đến server: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
