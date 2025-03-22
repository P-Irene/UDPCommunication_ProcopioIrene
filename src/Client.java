import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Client {
    String name;
    int port = 3000;
    DatagramSocket dSocket;
    DatagramPacket outPacket;
    DatagramPacket inPacket;
    InetAddress serverAddress;

    byte[] buffer;
    String messageOut;
    String response;

    public Client(String name){
        this.name = name;
    }

    public void inviaClient(){
        if(dSocket != null) {
            try {
                serverAddress = InetAddress.getLocalHost();
                dSocket = new DatagramSocket();
                System.out.println("DatagramSocket Client creato!");
            } catch (SocketException e) {
                System.err.println("Errore nella creazione del DatagramSocket del Client.");
                throw new RuntimeException(e);
            } catch (UnknownHostException e) {
                System.err.println("Errore nel recupero dell'indirizzo del Server.");
                throw new RuntimeException(e);
            }

            System.out.println("Richiesta Client:");
            Scanner wr = new Scanner(System.in);
            messageOut = wr.nextLine();

            outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), serverAddress, port);
            try {
                dSocket.send(outPacket);
            } catch (IOException e) {
                System.err.println("Errore nell'invio della richiesta al Server.");
                throw new RuntimeException(e);
            }
        }
    }

    public void riceviClient() {
        if (dSocket != null) {
            try{
            buffer = new byte[256];
            inPacket = new DatagramPacket(buffer, buffer.length);
            dSocket.receive(inPacket);

            response = new String(inPacket.getData(), 0, inPacket.getLength());
            System.out.println("Messaggio del Server: " + response);

            } catch (IOException e) {
                System.err.println("Errore nella ricezione dei dati.");
                throw new RuntimeException(e);
            }
        }
    }

    public void chiudi(){
        if(dSocket != null){
            dSocket.close();
            System.out.println("Comunicazione chiusa!");
        }
    }
}
