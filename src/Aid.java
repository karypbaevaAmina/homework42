import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Aid {
    private static final Random r = new Random();

    private static final List<String> name = List.of("Sanjar", "Amina", "Aleksei", "Loan", "Kairat", "Andrei", "DSS DSS", "Asem", "Tariel'");

    public static String clientName =name.get(r.nextInt(name.size()));

    static void handle(Socket socket){

        // логика обработки
        System.out.printf("Connected client: %s %s%n", clientName);
// создадим объекты через которые будем читать
// запросы от клиента и отправлять ответы

        try (Scanner reader = getReader(socket);
             PrintWriter writer = getWriter(socket);
             socket) {
            sendResponse("Hello " + clientName, writer);

            while (true) {
                String message = reader.nextLine();
                System.out.printf("User write: %s%n", message);
                if ("Bye".equalsIgnoreCase(message)){
                    System.out.println("Bye bye!%n");
                    return;
                }
                if (isEmptyMsg(message) || isQuitMsg(message)) {
                    break;
                }
// отправим ответ
                sendResponse(message.toUpperCase(), writer);
            }
        } catch (NoSuchElementException ex) {

// если scanner не сможет ничего прочитать из потока,
// то будет исключение
            System.out.println("The client closed the connection!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("Client disconnected: %s %s%n", clientName, socket);
    }

    private static PrintWriter getWriter(Socket socket)
            throws IOException { OutputStream stream = socket.getOutputStream();
        return new PrintWriter(stream);
    }

    private static Scanner getReader(Socket socket)throws IOException {
        InputStream stream = socket.getInputStream();
        InputStreamReader input = new InputStreamReader(stream,"UTF-8");
        return new Scanner(input);
    }

    private static boolean isQuitMsg(String message) {
        return "bye".equals(message.toLowerCase());
    }

    private static boolean isEmptyMsg(String message) {
        return message == null || message.isBlank();
    }

    private static void sendResponse(String response, Writer writer) throws IOException {
        writer.write(response);
        writer.write(System.lineSeparator());
        writer.flush();
    }



}
