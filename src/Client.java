import java.io.*;
import java.net.Socket;

public class Client
{
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream output;

    public Client(String ipAddress, int port)
    {
        try {
            socket = new Socket(ipAddress, port);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Send(String data)
    {
        try {
            output.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String Receive()
    {
        String inputData = "";
        while(inputData.equals(""))
        {
            try {
                inputData = input.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return inputData;
    }

    private void Close()
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
