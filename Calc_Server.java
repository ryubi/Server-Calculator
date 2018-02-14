import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import static java.lang.System.exit;

/**
 * Created by ryanubinger on 10/16/17.
 */
public class Calc_Server
{
    public static void main(String[] args) throws IOException
    {
        int tokenCount;
        double num1, num2, result;
        String input, op;

        ServerSocket ss = new ServerSocket(5556);
        Socket s = ss.accept();

        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        while (true)
        {
            try
            {
                input = dis.readUTF();

                if (input.equals("0 / 0"))
                    throw new EOFException();

                StringTokenizer st = new StringTokenizer(input);
                tokenCount = st.countTokens();

                if (tokenCount != 3)
                {
                    dos.writeUTF("Invalid question! Please enter the math question again.");
                    continue;
                }

                try
                {
                    num1 = Double.parseDouble(st.nextToken());
                    op = st.nextToken();
                    num2 = Double.parseDouble(st.nextToken());
                } catch (NumberFormatException nfe) {
                    dos.writeUTF("Invalid question! Please enter the math question again.");
                    continue;
                }

                if (op.equals("+")) {
                    result = num1 + num2;
                    dos.writeUTF(Double.toString(result));
                } else if (op.equals("-")) {
                    result = num1 - num2;
                    dos.writeUTF(Double.toString(result));
                } else if (op.equals("*")) {
                    result = num1 * num2;
                    dos.writeUTF(Double.toString(result));
                } else if (op.equals("/")) {
                    result = num1 / num2;
                    dos.writeUTF(Double.toString(result));
                } else
                    dos.writeUTF("Invalid question! Please enter the math question again.");
            } catch (EOFException eof) {
                System.out.println("Connection closed.");
                s.close();
                break;
            }
        }
    }
}
