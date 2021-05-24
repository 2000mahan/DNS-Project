import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket myServerSocket = new ServerSocket(5056);


        while (true)
        {
            Socket socket = null;

            try
            {
                socket = myServerSocket.accept();

                System.out.println("a client is connected : " + socket);

                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                Thread thread = new ClientHandler(socket, input, output);

                thread.start();

            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler extends Thread
{
    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;

    public ClientHandler(Socket socket, DataInputStream input, DataOutputStream output)
    {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run()
    {
        String received;
        String send;
        while (true)
        {
            try {

                output.writeUTF("ask me a favor :)");

                received = input.readUTF();

                if(received.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                switch (received) {

                    case "what is your name?" :
                        send = "My name is Mahan";
                        output.writeUTF(send);
                        break;

                    case "How old are you?" :
                        send = "I am twenty one";
                        output.writeUTF(send);
                        break;

                    default:
                        output.writeUTF("Invalid input");
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            this.input.close();
            this.output.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
