import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HandleClient extends Thread{
    Socket handleClient;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    //    ArrayList <HandleClient> client=new ArrayList<>();
    Server sever;

    public HandleClient(Socket handleClient,Server server) {
        this.handleClient = handleClient;
        this.sever=server;

    }


//    public void addClient(ArrayList<HandleClient> clients) {
//        for (HandleClient clientss : clients) {
//            client.add(clientss);
//        }
//
//    }

    public void run(){
        try {
            dataInputStream=new DataInputStream(handleClient.getInputStream());
            dataOutputStream=new DataOutputStream(handleClient.getOutputStream());

            String ClientMessage="";
            String ServerMessage="";

            while(!ClientMessage.equals("end")){

                ClientMessage=dataInputStream.readUTF();


//                for (HandleClient handleClient1 : client) {
//                    System.out.println(handleClient1);
//                    handleClient1.send(ClientMessage,this);
//                }
                sever.send(ClientMessage,this);



            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String ClientMessage){
        try {
            dataOutputStream.writeUTF(ClientMessage);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
