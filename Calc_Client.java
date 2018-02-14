import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by ryanubinger on 10/16/17.
 */
public class Calc_Client
{
    public static void main(String[] args) throws IOException
    {
        int port = 5556;
        String input, answer;

        Scanner sc = new Scanner(System.in);

        InetAddress ip = InetAddress.getLocalHost();

        Socket s = new Socket(ip, port);

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true)
        {
            System.out.print("Enter an equation of the form:");
            System.out.println("[operand] [operator] [operand]");
            System.out.println("Supported operators: + - * /");

            input = sc.nextLine();

            if (input.equals("0 / 0"))
                break;

            dos.writeUTF(input);

            answer = dis.readUTF();

            if (answer.equals("Invalid question! Please enter the math question again."))
            {
                System.out.println(answer);
                continue;
            }

            System.out.println("Answer from server: " + answer);
        }

        s.close();
    }
}
