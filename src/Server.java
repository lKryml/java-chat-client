import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<ClientHandler> clients;
    private ArrayList<BufferedWriter> writer;
    private static Server server;

    public static void main(String[] args) {
        server = new Server();
    }

    public Server() {
        try {
            this.serverSocket = new ServerSocket(12345);
            this.clients = new ArrayList<>();
            this.writer = new ArrayList<>();
            this.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        Thread listenThread = new Thread(() -> {
            while (true) {
                try {
                    clientSocket = serverSocket.accept();
                    ClientHandler client = new ClientHandler(server, new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));
                    clients.add(client);
                    writer.add(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                    Thread clientThread = new Thread(client);
                    clientThread.start();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        listenThread.start();
    }

    public void sendToAllClients(String message) {
        if (!"".equals(message)) {
            for (BufferedWriter bw : writer) {
                try {
                    bw.write(message + "\r\n");
                    bw.flush();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    public ArrayList<ClientHandler> getClients() {
        return this.clients;
    }
}