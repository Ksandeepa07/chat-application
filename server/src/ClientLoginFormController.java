import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientLoginFormController extends Thread{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label chatRoomLbl;

    @FXML
    private TextArea clientMessageTxtArea;

    @FXML
    private TextField clientNameTxt;

    @FXML
    private Group group01;

    @FXML
    private Group group02;

    @FXML
    private Button joinBtn;

    @FXML
    private TextField senMessageTxt;

    @FXML
    private Button sendBtn;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";

    @FXML
    void joinBtnOnAction(ActionEvent event) {
        group01.setVisible(false);
        group02.setVisible(true);
        chatRoomLbl.setText(clientNameTxt.getText());

    }

    public void run(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client_login_form.fxml"));
        Parent root1 = null;
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();

        stage.setTitle("ABC");
        stage.setScene(new Scene(root1,768, 543));
        stage.show();
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        try {
            dataOutputStream.writeUTF(chatRoomLbl.getText()+" :"+senMessageTxt.getText());
            dataOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void getServerMessage(){
        new Thread(()->{
            try {
                while(true){
                    message=dataInputStream.readUTF();
                    clientMessageTxtArea.appendText("\n"+message);
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
        group01.setVisible(true);
        getConnection();
        group02.setVisible(false);
        assert chatRoomLbl != null : "fx:id=\"chatRoomLbl\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert clientMessageTxtArea != null : "fx:id=\"clientMessageTxtArea\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert clientNameTxt != null : "fx:id=\"clientNameTxt\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert group01 != null : "fx:id=\"group01\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert group02 != null : "fx:id=\"group02\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert joinBtn != null : "fx:id=\"joinBtn\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert senMessageTxt != null : "fx:id=\"senMessageTxt\" was not injected: check your FXML file 'client_login_form.fxml'.";
        assert sendBtn != null : "fx:id=\"sendBtn\" was not injected: check your FXML file 'client_login_form.fxml'.";

    }

}
