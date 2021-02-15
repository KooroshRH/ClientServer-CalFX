import java.io.*;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public Client(String ipAddress, int port)
    {
        try {
            socket = new Socket(ipAddress, port);
            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void send(String data)
    {
        try {
            output.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receive()
    {
        String inputData = "";
        while(inputData.equals(""))
        {
            try {
                inputData = input.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputData;
    }

    private void close()
    {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
