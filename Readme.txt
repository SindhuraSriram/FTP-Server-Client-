To implement this project, you will need to write two separate programs: FtpClient and FtpServer.

FtpServer:
The server program will listen on a TCP port, specified in the command line (e.g. 5106).
The server should wait for incoming connections from the client.
When the client connects, the server should receive commands from the client (e.g. "get filename" or "upload filename").
If the client requests to get a file, the server should read the file in chunks of 1K bytes and send each chunk to the client, until the entire file has been sent.
If the client requests to upload a file, the server should receive each chunk of the file from the client, until the entire file has been received, and write it to disk as "newUploadTestFile.pptx".

FtpClient:
The client program should take the server's port number as an argument in the command line (e.g. "ftpclient 5106").
The client should connect to the server and send commands to retrieve or upload files.
If the client requests to get a file, it should receive each chunk of the file from the server and write it to disk as "newDownloadTestFile.pptx".
If the client requests to upload a file, it should read the file in chunks of 1K bytes and send each chunk to the server, until the entire file has been sent.
You can use socket programming in Python to implement this project. The socket module provides a low-level interface for network programming in Python. You can use it to create client and server programs for transferring data over the network.

This is a high-level overview of the project. You can find more details in the programming documentation and online tutorials. Good luck with your implementation!

Execution from server side :
javac FtpServer.java
java FtpServer
Buffers will be printed on this side for get and upload operations.

Exceution from client side :
javac FtpClient.java
java FtpClient 5106
"FTP client ready. Enter 'get <filename>' to download a file or 'upload <filename>' to upload a file.
get downloadTestFile.pptx or upload uploadTestFile.pptx