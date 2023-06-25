import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    ArrayList<HandleClient> client=new ArrayList<>();

    public void excute() {

        try {
            ServerSocket serverSocket=new ServerSocket(3001);

            while(true){
                Socket socket=serverSocket.accept();

                HandleClient handleClient=new HandleClient(socket,client);
                client.add(handleClient);
                Thread newClient =new Thread(handleClient);
                newClient.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        Server server=new Server();
        server.excute();
    }



}
