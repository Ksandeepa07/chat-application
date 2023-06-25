import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

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

    @FXML
    private AnchorPane ancpane;

    @FXML
    private ScrollPane sPane;
    @FXML
    private Pane pane;

    @FXML
    private VBox vBoxLeft;

    @FXML
    private VBox vBoxImoji;

    @FXML
    private ScrollPane sBarImoji;

    Label label;


    String filePath;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message;
    File selectedFile;

    @FXML
    void joinBtnOnAction(ActionEvent event) {
        group01.setVisible(false);
        group02.setVisible(true);
        chatRoomLbl.setText(clientNameTxt.getText());

    }

    public void run(){
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("client_login_form.fxml"));
//        Parent root1 = null;
//        try {
//            root1 = (Parent) fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Stage stage = new Stage();
//
//        stage.setTitle("ABC");
//        stage.setScene(new Scene(root1,768, 543));
//        stage.show();
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        try {

            dataOutputStream.writeUTF(chatRoomLbl.getText()+" :"+senMessageTxt.getText());
            dataOutputStream.flush();
            VBox vBox=new VBox();
            vBox.setAlignment(Pos.BASELINE_RIGHT);
            vBox.setPrefWidth(10);
            vBox.setStyle("-fx-background-color: #00C6FF;"+"-fx-background-radius: 0 10 10 10 ;"+"-fx-font-weight: bold");
            vBox.setPadding(new Insets(10,10,10,10));

            vBox.getChildren().add(new Text("Me : "+senMessageTxt.getText()));
            vBoxLeft.getChildren().add(vBox);



//            clientMessageTxtArea.app10endText("\n"+"Me: "+senMessageTxt.getText());


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void getServerMessage(){
        new Thread(()->{

                while(true){
                    try {

                         message=dataInputStream.readUTF();

                        VBox vBox = new VBox();
                        vBox.setStyle("-fx-background-color: #25D366;"+"-fx-background-radius: 0 10 10 10 ;"+"-fx-font-weight: bold");
                        vBoxLeft.setAlignment(Pos.BASELINE_LEFT);
                        vBox.setPadding(new Insets(10,10,10,10));
                        vBox.setMaxWidth(10);

                        if(message.charAt(0)=='#'){
                            String cut=message.substring(1);
                            Image image=new Image(cut);
                            ImageView imageView = new ImageView(image);
                            vBox.getChildren().add((imageView));
                        } else if(message.charAt(0)=='@'){
                            String neww=message.substring(1);
                            Label label=new Label(neww);
                            vBox.getChildren().add((label));
                        }
                        else {
                            vBox.getChildren().add(new Text(message));

                        }

                        Platform.runLater(() -> {


                            vBoxLeft.getChildren().add(vBox);


                        });




                    } catch (IOException e) {
                        e.printStackTrace();
                    }


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
        loadEmoji("\uD83D\uDE00"); // Grinning Face
       loadEmoji("\uD83C\uDF1E"); // Sun with Face
       loadEmoji("\uD83D\uDC36"); // Dog Face
       loadEmoji("\uD83C\uDF08"); // Rainbow
       loadEmoji("\uD83C\uDF55"); // Slice of Pizza
       loadEmoji("\uD83C\uDF88"); // Party Popper
       loadEmoji("\uD83D\uDE80"); // Rocket
       loadEmoji("\uD83C\uDFB5"); // Musical Note
       loadEmoji("\uD83C\uDF3A"); // Hibiscus
       loadEmoji("\uD83C\uDFC6");

        vBoxLeft.setSpacing(10);
        vBoxLeft.setStyle("-fx-background-color: #191E23;");

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

    private void loadEmoji(String grinningFace) {
         label=new Label(grinningFace);
        label.setPadding(new Insets(30,30,30,30));
        vBoxImoji.getChildren().add(label);

        label.setOnMouseClicked(event -> {

            try {
                dataOutputStream.writeUTF("@"+grinningFace);
                dataOutputStream.flush();
                Label label=new Label(grinningFace);
                VBox vBox=new VBox();
                vBox.setStyle("-fx-background-color: #00C6FF;"+"-fx-background-radius: 0 10 10 10 ;"+"-fx-font-weight: bold");
                vBox.setPrefWidth(10);
                vBox.setAlignment(Pos.BASELINE_RIGHT);
                vBox.setPadding(new Insets(10,10,10,10));
                vBox.getChildren().addAll(label);
                vBoxLeft.getChildren().add(vBox);

            } catch (IOException e) {
                e.printStackTrace();
            }


        });

    }



    public void imageBtnOnAction(ActionEvent actionEvent) {
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
                senImage(selectedFile);
            }

            }

    private void senImage(File imageFile) {
         try {

             dataOutputStream.writeUTF("#"+imageFile.toURI().toString());
             dataOutputStream.flush();

             Image image=new Image(imageFile.toURI().toString());
             ImageView imageView = new ImageView(image);
             VBox vBox=new VBox();
             vBox.setAlignment(Pos.BASELINE_RIGHT);
             vBox.setStyle("-fx-background-color: #00C6FF;"+"-fx-background-radius: 0 10 10 10 ;"+"-fx-font-weight: bold");
             vBox.setPrefWidth(10);
             vBox.setPadding(new Insets(10,10,10,10));
             vBox.getChildren().addAll(imageView);
             vBoxLeft.getChildren().add(vBox);



         } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }







    public void sendImojiOnAction(ActionEvent actionEvent) {

        sBarImoji.setVisible(true);
        vBoxImoji.setVisible(true);
        vBoxImoji.setStyle("-fx-background-color: #00C6FF;");
    }
}
