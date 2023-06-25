import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class HandleClient implements Runnable{
    Socket handleClient;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    ArrayList<HandleClient> client;


    public HandleClient(Socket handleClient, ArrayList<HandleClient> client) {
        this.handleClient = handleClient;
        this.client=client;

    }



    public void run(){
        try {
            dataInputStream=new DataInputStream(handleClient.getInputStream());
            dataOutputStream=new DataOutputStream(handleClient.getOutputStream());

            String ClientMessage="";


            while(!ClientMessage.equals("end")){
                ClientMessage=dataInputStream.readUTF();
                System.out.println(ClientMessage);

              if(ClientMessage.charAt(0)=='#'){
                  for (HandleClient handleClient1 : client) {
                      if(handleClient1!=this){
                          handleClient1.sendImageMessage(ClientMessage);
                      }
                  }


              }else if(ClientMessage.charAt(0)=='@'){
                    for (HandleClient handleClient1 : client) {
                        if(handleClient1!=this){
                            handleClient1.sendEmojiMessage(ClientMessage);
                        }
                    }
                }
              else{

                  for (HandleClient handleClient1 : client) {
                      if (handleClient1 != this) {
                          handleClient1.sendMessage(ClientMessage);
                      }
                  }
              }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendEmojiMessage(String clientMessage) {
        try {
            dataOutputStream.writeUTF(clientMessage);
            dataOutputStream.flush();
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

    public void sendImageMessage(String imageData) {

        try {

            dataOutputStream.writeUTF(imageData);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
