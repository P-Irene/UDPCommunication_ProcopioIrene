public class MainServer {
    public static void main(String[] args) {
        Server s = new Server();
        s.avvio();
        while(true){
            s.riceviServer();
            s.inviaServer();
        }
    }

}
