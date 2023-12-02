import java.io.*;
import java.net.*;

public class FtpServer {
    public static final int PORT = 5106;
    public static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                System.out.println("FtpServer waiting for connections...");
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
                    handleClientRequest(clientSocket);
                } catch (IOException e) {
                    System.out.println("Error while handling client request: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error starting server: " + e.getMessage());
        }
    }

    private static void handleClientRequest(Socket clientSocket) throws IOException {
        System.out.println("Inside Handle client");
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String request = in.readLine();
            String[] parts = request.split(" ");
            if (parts[0].equals("get")) {
                File newFile = new File(
                        "newdownloadTestFile.pptx");
                downloadFile(clientSocket, parts[1], newFile);
            } else if (parts[0].equals("upload")) {
                uploadFile(clientSocket, parts[1]);
            }
        } catch (IOException e) {
            System.out.println("Error while reading client request: " + e.getMessage());
        }
    }

    private static void downloadFile(Socket clientSocket, String filename, File newFilename) throws IOException {
        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File " + file + " not found on server");
            return;
        }
        try (OutputStream out = clientSocket.getOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;

            // File newFile = new File(newFilename);
            try (FileOutputStream fileOutputStream = new FileOutputStream(newFilename)) {
                while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                    System.out.println(buffer);
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                System.out.println("Error while writing file " + newFilename + ": " + e.getMessage());
            }
        } catch (IOException e) {
            System.out.println("Error while sending file " + filename + ": " + e.getMessage());
        }
    }

    private static void uploadFile(Socket clientSocket, String filename) throws IOException {
        File file = new File(filename);
        try (InputStream in = clientSocket.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                System.out.println(buffer);
                fileOutputStream.write(buffer, 0, bytesRead);
                System.out.println(buffer);
            }
        } catch (IOException e) {
            System.out.println("Error while receiving file " + filename + ": " + e.getMessage());
        }
    }
}
