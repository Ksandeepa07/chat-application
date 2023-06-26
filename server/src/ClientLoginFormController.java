import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLoginFormController extends Thread {

    Label label;
    String filePath;
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message;
    File selectedFile;
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
    private HBox vBoxImoji;
    @FXML
    private ScrollPane sBarImoji;

    @FXML
    void joinBtnOnAction(ActionEvent event) {
        group01.setVisible(false);
        group02.setVisible(true);
        chatRoomLbl.setText(clientNameTxt.getText());

    }

    public void run() {
    }

    @FXML
    void sendBtnOnAction(ActionEvent event) {
        try {


            dataOutputStream.writeUTF(chatRoomLbl.getText() + " :" + senMessageTxt.getText());
            dataOutputStream.flush();

            Text text = new Text("Me : " + senMessageTxt.getText());
            text.setStyle("-fx-font-size: 14");
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-text-fill: white; -fx-background-radius: 0 10 10 10");
            textFlow.setPadding(new Insets(10, 8, 10, 10));

            HBox vBox = new HBox();
            vBox.setAlignment(Pos.CENTER_RIGHT);


            vBox.getChildren().add(textFlow);

            vBoxLeft.getChildren().add(vBox);
            senMessageTxt.setText("");


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    void getServerMessage() {
        new Thread(() -> {
            while (true) {
                try {
                    message = dataInputStream.readUTF();
                    Text text = new Text(message);
                    text.setStyle("-fx-font-size: 14");
                    TextFlow textFlow = new TextFlow(text);
                    textFlow.setStyle("-fx-background-color: #25D366; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 0 10 10 10");
                    textFlow.setPadding(new Insets(10, 8, 10, 10));

                    HBox vBox = new HBox(10);
                    vBox.setAlignment(Pos.CENTER_LEFT);

                    if (message.charAt(0) == '#') {
                        String cut = message.substring(1);
                        Image image = new Image(cut);
                        ImageView imageView = new ImageView(image);
                        vBox.getChildren().add((imageView));
                    } else if (message.charAt(0) == '@') {
                        String neww = message.substring(1);
                        text = new Text(neww);
                        text.setStyle("-fx-font-size: 14");
                        textFlow = new TextFlow(text);
                        textFlow.setStyle("-fx-background-color: #25D366; -fx-font-weight: bold; -fx-color: white; -fx-background-radius:0 10 10 10");
                        textFlow.setPadding(new Insets(10, 8, 10, 10));
                        vBox.getChildren().add((textFlow));
                    } else {
                        vBox.getChildren().add(textFlow);

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

    void getConnection() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3001);
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                getServerMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }


    @FXML
    void initialize() {
        getConnection();

        loadEmoji("\uD83D\uDE00"); // Grinning Face
        loadEmoji("\uD83C\uDF1E"); // Sun with Face
        loadEmoji("\u2764\ufe0f");

        vBoxLeft.setSpacing(7);
        vBoxLeft.setStyle("-fx-background-color: #ffffff;");
        group01.setVisible(true);
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
        label = new Label(grinningFace);
        label.setPadding(new Insets(5, 5, 5, 5));
        vBoxImoji.getChildren().add(label);
        label.setOnMouseClicked(event -> {

            try {
                dataOutputStream.writeUTF("@" + grinningFace);
                dataOutputStream.flush();
                HBox vBox = new HBox(10);

                Text text = new Text(grinningFace);
                text.setStyle("-fx-font-size: 14");
                TextFlow textFlow = new TextFlow(text);
                textFlow.setStyle("-fx-background-color: #0693e3; -fx-font-weight: bold; -fx-color: white; -fx-background-radius: 0 10 10 10");
                textFlow.setPadding(new Insets(10, 8, 10, 10));

                vBox.setAlignment(Pos.BASELINE_RIGHT);
                vBox.getChildren().addAll(textFlow);
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

            dataOutputStream.writeUTF("#" + imageFile.toURI());
            dataOutputStream.flush();

            Image image = new Image(imageFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.BASELINE_RIGHT);
            vBox.setPrefWidth(10);
            vBox.setPadding(new Insets(10, 10, 10, 10));
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
        vBoxImoji.setStyle("-fx-background-color: #ffffff;");
    }
}
