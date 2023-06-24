import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientController  extends Thread{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea clientMessageTxtArea;

    @FXML
    private TextField senMessageTxt;

    @FXML
    private Button sendBtn;

    Socket socket;
    ClientLoginFormController clientLoginFormController;
    DataInputStream dataInputStream;
    String message="";
    String name;


    public ClientController() {
    }

    DataOutputStream dataOutputStream;



//    public ClientController(Socket socket, ClientLoginFormController clientLoginFormController) {
//        this.socket=socket;
//        this.clientLoginFormController=clientLoginFormController;
//
//        try {
//            dataInputStream=new DataInputStream(socket.getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        getOtherClientsMessage();
//
//
//    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        try {
            dataOutputStream.writeUTF(senMessageTxt.getText());
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    void getOtherClientsMessage(){
//        new Thread(()->{
//            try {
//                while(true){
//
//                    clientMessageTxtArea.appendText("\n"+"Server :"+dataInputStream.readUTF());
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }).start();
//
//    }

    void getServerMessage(){
        new Thread(()->{
            try {
                while(true){
                    message=dataInputStream.readUTF();
                    clientMessageTxtArea.appendText("\n"+"Server :"+message);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    void getConnection(){
        new Thread(()->{
            try {
                socket=new Socket("localhost",3001);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                getServerMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }


    @FXML
    void initialize() {
        getConnection();
        assert clientMessageTxtArea != null : "fx:id=\"clientMessageTxtArea\" was not injected: check your FXML file 'ClientForm.fxml'.";
        assert senMessageTxt != null : "fx:id=\"senMessageTxt\" was not injected: check your FXML file 'ClientForm.fxml'.";
        assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'ClientForm.fxml'.";

    }


}
