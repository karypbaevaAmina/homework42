import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class ServerSomething extends Thread {

    private Socket socket;

    private BufferedReader in;

    private BufferedWriter out;


    public ServerSomething(Socket socket) throws IOException{
        this.socket = socket;

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException ignored) {}
    }
}
