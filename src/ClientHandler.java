import java.io.BufferedReader;
import java.io.IOException;

public class ClientHandler extends Thread {

    private BufferedReader br;

    private Server server;

    public ClientHandler(Server server, BufferedReader br) {
        this.server = server;
        this.br = br;

    }
    @Override
    public void run() {
        while (true) {
            try {
                String receivedMessage = br.readLine();
                server.sendToAllClients(receivedMessage);
            } catch (IOException ex) {
                disconnect();
            }
        }
    }

    public void disconnect(){
        server.getClients().remove(this);
    }
}