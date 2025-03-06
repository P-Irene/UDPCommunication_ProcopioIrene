import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Client {
    String name;
    int port;
    DatagramSocket dSocket;
    DatagramPacket outPacket;
    DatagramPacket inPacket;
    InetAddress serverAddress;

    byte[] buffer;
    String messageOut;
    String response;

    public Client(String name, int port){
        this.name = name;
        this.port = port;
    }
    public void inizio(){
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
    }

    public void richiedi(){
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

    public void ricevi(){
        buffer = new byte[256];
        inPacket = new DatagramPacket(buffer, buffer.length);
        response = new String(inPacket.getData(), 0, inPacket.getLength());
        try {
            dSocket.receive(inPacket);
            System.out.println("Messaggio del Server: " + response);
        } catch (IOException e) {
            System.err.println("Errore nella ricezione dei dati.");
            throw new RuntimeException(e);
        }
    }

    public void chiudi(){
        if(dSocket != null){
            dSocket.close();
            System.out.println("Comunicazione chiusa!");
        }
    }
}
