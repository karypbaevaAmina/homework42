import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class EchoServer {

    private final int port;
    private EchoServer(int port) {
        this.port = port;
    }
    public static EchoServer bindToPort(int port) {
        return new EchoServer(port);
    }

    private final ExecutorService pool =
            Executors.newCachedThreadPool();

    public static LinkedList<ServerSomething> serverList = new LinkedList<>();

    public void run() {
        try (var server = new ServerSocket(port)) {
            while (!server.isClosed()) {
                Socket clientSocket = server.accept();
                pool.submit( () -> Aid.handle(clientSocket));


                serverList.add(new ServerSomething(clientSocket));

            }
        } catch (IOException e) {
            System.out.printf("Most probably port %s is busy.%n", port);
            e.printStackTrace();

        }
    }




}

