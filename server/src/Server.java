import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ArrayList<HandleClient> clients=new ArrayList<>();
    ArrayList<String> clientNames=new ArrayList();
    String ClientName;

    public void eccute() {

        int count=0;
        try {
            ServerSocket serverSocket=new ServerSocket(3001);

            while(true){
                count++;
                Socket socket=serverSocket.accept();
                System.out.println("Client "+count+" joined");

                HandleClient handleClient=new HandleClient(socket,this);
                clients.add(handleClient);

//                handleClient.addClient(clients,this);
                handleClient.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void send(String ClientMessage,HandleClient handleClient){
        for(HandleClient clientt:clients){
            if(clientt!=handleClient) {
                clientt.sendMessage(ClientMessage);
            }

        }

    }



    public static void main(String[] args) {
        Server server=new Server();
        server.eccute();
    }




//    public static void main(String[] args) {
//        ArrayList<HandleClient> clients=new ArrayList<>();
//        int count=0;
//        try {
//            ServerSocket serverSocket=new ServerSocket(3302);
//
//            while(true){
//                count++;
//                Socket socket=serverSocket.accept();
//                System.out.println("Client "+count+" joined");
//                HandleClient handleClient=new HandleClient(socket,count);
//                clients.add(handleClient);
//
//                handleClient.addClient(clients);
//                handleClient.start();
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

}
