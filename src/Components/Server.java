package Components;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import DataModels.Operator;

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
        waitForResponse();
    }

    private void waitForResponse()
    {
        String data = "";
        while(true)
        {
            try {
                data = input.readUTF();
                if (data.equals("finish"))
                {
                    close();
                    break;
                }
                output.writeUTF(calculate(data));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String calculate(String data)
    {
        String[] dataArray = data.split(SEPARATOR);
        int firstOperand = Integer.parseInt(dataArray[0]);
        int secondOperand = Integer.parseInt(dataArray[1]);
        Operator operator = Operator.values()[Integer.parseInt(dataArray[2])];
        switch (operator)
        {
            case ADD:
                return "" + (firstOperand + secondOperand);
            case SUB:
                return "" + (firstOperand - secondOperand);
            case MUL:
                return "" + (firstOperand * secondOperand);
            case DIV:
                if (secondOperand == 0)
                {
                    return "Invalid";
                }
                return "" + (firstOperand / secondOperand);
        }
        return "Unknown";
    }

    private void close()
    {
        try {
            input.close();
            output.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
