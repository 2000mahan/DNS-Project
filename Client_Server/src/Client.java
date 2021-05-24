import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scan = new Scanner(System.in);

            InetAddress ip = InetAddress.getByName("localhost");

            Socket s = new Socket(ip, 5056);

            DataInputStream input = new DataInputStream(s.getInputStream());
            DataOutputStream output = new DataOutputStream(s.getOutputStream());

            while (true)
            {
                System.out.println(input.readUTF());
                String send = scan.nextLine();
                output.writeUTF(send);

                if(send.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + s);
                    s.close();
                    System.out.println("Connection closed");
                    break;
                }

                String received = input.readUTF();
                System.out.println(received);
            }

            scan.close();
            input.close();
            output.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

