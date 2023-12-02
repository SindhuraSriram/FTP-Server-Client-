import java.io.*;
import java.net.*;

public class FtpClient {
    public static void main(String[] args) {
        // check if the correct number of arguments has been provided
        if (args.length != 1) {
            System.out.println("Usage: java FtpClient <server port>");
            System.exit(1);
        }

        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Error: Port number must be an integer");
            System.exit(1);
        }

        // create a socket for communication with the server
        Socket socket = null;
        try {
            socket = new Socket("localhost", port);
        } catch (UnknownHostException e) {
            System.out.println("Error: Unknown host");
            System.exit(1);
        } catch (IOException e) {
            System.out.println("Error: Could not establish a connection with the server");
            System.exit(1);
        }

        // start the user interface for sending commands to the server
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        DataOutputStream output = null;
        try {
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Error: Could not create output stream");
            System.exit(1);
        }

        System.out.println(
                "FTP client ready. Enter 'get <filename>' to download a file or 'upload <filename>' to upload a file.");
        while (true) {
            try {
                System.out.print("ftp> ");
                String command = input.readLine();
                if (command.startsWith("get")) {
                    String filename = command.substring(4).trim();
                    output.writeBytes("get " + filename + '\n');
                } else if (command.startsWith("upload")) {
                    String filename = command.substring(7).trim();
                    output.writeBytes("upload " + filename + '\n');
                } else {
                    System.out.println("Error: Invalid command");
                }
            } catch (IOException e) {
                System.out.println("Error: Could not read user input");
                break;
            }
        }

        // close the socket and end the program
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Error: Could not close the socket");
        }
    }
}
