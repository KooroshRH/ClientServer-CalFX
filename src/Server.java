import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server
{
    private ServerSocket serverSocket;
    private Socket socket;
    private DataOutputStream output;
    private DataInputStream input;

    private final String SEPARATOR = "<>";

    public Server(int port)
    {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForClient()
    {
        try {
            socket = serverSocket.accept();
            output = new DataOutputStream(socket.getOutputStream());
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
