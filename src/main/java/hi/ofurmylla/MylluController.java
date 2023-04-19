package hi.ofurmylla;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;



public class MylluController implements Initializable {

    public String Player1 = TaknSelectController.Player1;
    public String Player2 = TaknSelectController.Player2;
    private static final String HIGHLIGHT = "-fx-background-color: #FFFFD4";
    private static final String TOMUR = "-fx-background-color: #00000000";
    public static int turn = 0;
    public static int winner;

    private MylluModel model;
    @FXML
    private GridPane Mylla;

    @FXML
    private Button aframButton;

    @FXML
    void nextScene(ActionEvent event) {
        ViewSwitcher.switchTo(View.STIG);
        aframButton.setVisible(false);
        turn = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MylluModel(Player1, Player2);
        highlightNextBoard();
    }

    // Controller sér að ýtt er á takka
    public void handleButtonToggle(ActionEvent event) {
        // Takki sem ýtt er á
        Button reitur = (Button) event.getSource();
        geraLeik(reitur);
        turn++;
    }

    // Stjórna hvað gerist á leikborði
    private void geraLeik(Button reitur) {
        int reiturL = GridPane.getRowIndex(reitur);
        int reiturD = GridPane.getColumnIndex(reitur);
        // Línu index í mylluboxi í notkun
        int boardR = GridPane.getRowIndex(reitur.getParent());
        // Dálka index af mylluboxi í notkun
        int boardC = GridPane.getColumnIndex(reitur.getParent());
        if (model.loglegtMove(boardR, boardC, reiturL, reiturD)) {
            // player moved
            updateReitur(reitur);
            model.setLocalBoard(boardR, boardC, reiturL, reiturD);
            if (model.localBoardSigurvegari(boardR, boardC)) {
                // Annarhvor leikmaður sigrar stakt myllubox
                GridPane bord = (GridPane) reitur.getParent();
                updateBordView(bord);
                model.setGlobalBoard(boardR, boardC);
                athugaSigurvegara();
            }
            SKodaJafntefli();
            endaLeik(reiturL, reiturD);
        }
    }

    // Skoðar hvort leikur endi á jafntefli (forced draw)
    private void SKodaJafntefli() {
        if (!model.loglegtGameState()) {
            enginnVann();
        }
    }

    // Skoðar hvort leikur sé uninn
    private void athugaSigurvegara() {
        if (model.mylluSigurvegari()) {
            leikurUninn();
        }
    }

    // Upfærir logic áður en næsti leikmaður á að gera
    private void endaLeik(int nextBoardR, int nextBoardC) {
        model.setLastBoard(nextBoardR, nextBoardC);
        model.togglePlayer();
        highlightNextBoard();
    }

    // Texti ef enginn vinnur
    private void enginnVann() {
        Mylla.getChildren().clear();
        Mylla.getStyleClass().clear();
        Mylla.setStyle("-fx-background-color: #000000");
        Label sigurvegari = new Label("JAFNT!");
        updateLabel(sigurvegari);
        aframButton.setVisible(true);
    }

    // Texti ef annar leikmanna sigrar
    private void leikurUninn() {
        updateBordView(Mylla);
        Label sigurvegari = new Label("ÞÚ VANNST!");
        aframButton.setVisible(true);
        updateLabel(sigurvegari);
        winner = turn;
    }
    // Lagar label undir lok leiks
    private void updateLabel(Label label) {
        label.setFont(new Font("Helvetica", 70));
        label.setTextFill(Paint.valueOf("BLACK"));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.BOTTOM_CENTER);
        Mylla.add(label, 1, 1);


    }

    // updatea borð eftir að ýtt er á takka
    private void updateReitur(Button reitur) {
        ImageView imageView = (ImageView) reitur.getGraphic();
        Image newImage = model.getTakn();
        imageView.setImage(newImage);
        reitur.setOpacity(100.0);
        reitur.setDisable(true);
    }

    // Uppfærist þegar myllubox er unnið
    private void updateBordView(GridPane bord) {
        bord.getChildren().clear();
       BackgroundImage bgImage = new BackgroundImage(model.getTakn(),BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(900, 900, true, true, true, false));
       bord.setBackground(new Background(bgImage));

    }

    // Sér um að highlighta rétt myllubox
    private void highlightNextBoard() {
        ObservableList<Node> boards = Mylla.getChildren();
        for (Node board : boards) {
            int boardRow = GridPane.getRowIndex(board);
            int boardCol = GridPane.getColumnIndex(board);
            if (!model.bordUnnid(boardRow, boardCol)) {
                board.setStyle(TOMUR);
            }
            if (model.loglegtBord(boardRow, boardCol)) {
                board.setStyle(HIGHLIGHT);
            }
        }
    }

}
