import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Server {
    int port = 3000;

    DatagramSocket dSocket;
    DatagramPacket inPacket;
    DatagramPacket outPacket;

    int clientPort;
    InetAddress clientAddress;

    byte[] bufferIn, bufferOut;
    String messageIn, messageOut;

    public void avvio() {
        try {
            dSocket = new DatagramSocket(port);
            System.out.println("Apertura porta in corso!");
            System.out.println("Server in ascolto sulla porta " + port + "\n");
        }catch (BindException e){
            System.err.println("Errore: porta già in uso!");
        }catch (SocketException e) {
            System.err.println("Errore nella creazione del Datagram Socket");
        }
    }

    public void riceviServer(){
        if(dSocket != null) {
            try {
                bufferIn = new byte[256];
                inPacket = new DatagramPacket(bufferIn, bufferIn.length);
                dSocket.receive(inPacket);

                clientAddress = inPacket.getAddress();
                clientPort = inPacket.getPort();

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("SONO IL CLIENT " + clientAddress + ": " + clientPort + " > " + messageIn);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void inviaServer(){
        if(dSocket != null) {
            try {
                Scanner wr = new Scanner(System.in);
                System.out.println("Risposta Server:");
                messageOut = wr.nextLine();

                bufferOut = messageOut.getBytes();
                outPacket = new DatagramPacket(bufferOut, bufferOut.length, clientAddress, clientPort);
                dSocket.send(outPacket);
                System.out.println("Risposta inviata!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
