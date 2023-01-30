import netzwerk.tcp.Kuchenautomat_Server_TCP;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Kuchen_VerkaufsautomatRun {
    public static void main(String[]args) {
        String argument1 = String.valueOf(args[0]);
        String netzwerk = String.valueOf(args[1]);
        int kapazitaetAutomat = Integer.parseInt(argument1);
        Kuchen_VerkaufsautomatRun automatRun = new Kuchen_VerkaufsautomatRun();
        automatRun.startAutomat(kapazitaetAutomat, netzwerk);
    }

    public void startAutomat(int automatenkapazitaet, String netzwerk){
        switch (netzwerk){
            case "UDP":
                System.out.println("UDP");
                //DatagramSocket datagramSocket = new DatagramSocket(5001);
                //Kuchenautomat_Server_UDP serverUDP = new Kuchenautomat_Server_UDP(datagramSocket, );
                //serverUDP.startServer();
                break;
            case "TCP":
                try(ServerSocket serverSocket=new ServerSocket(5001);) {
                    Socket socket=serverSocket.accept();
                    Kuchenautomat_Server_TCP serverTCP =
                            new Kuchenautomat_Server_TCP(socket, automatenkapazitaet);
                    serverTCP.run();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                CLI cli = new CLI(automatenkapazitaet);
                cli.startCLI();
        }
    }
}
