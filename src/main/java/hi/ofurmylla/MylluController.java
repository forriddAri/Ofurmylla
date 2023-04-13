package hi.ofurmylla;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class MylluController implements Initializable {
    private static final String HOVER = "-fx-Background-color: #e0e084;";
    private static final String TOMUR = "-fx-Background-color: #e0e0e0;";
    private MylluModel model;
    @FXML
    private GridPane Mylla;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MylluModel();
        //highlightNextMyllubox();
    }

    //Þegar ytt er á takka
    public void Krossa(ActionEvent krossa) {
        Button reitur = (Button) krossa.getSource();
        geraLeik(reitur);
    }

    private void geraLeik(Button reitur) {
        int reiturL = GridPane.getRowIndex(reitur);
        int reiturD = GridPane.getColumnIndex(reitur);
        // row index af Mylluborði
        int bordL = GridPane.getRowIndex(reitur.getParent());
        //col index af Mylluborði
        int bordD = GridPane.getColumnIndex(reitur.getParent());
        if (model.leikurleifdur(bordL,bordD,reiturL,reiturD)) {
            //leikmadur á leik
            updateReitView(reitur);
            model.setMyllubox(bordL, bordD, reiturL, reiturD);
            if (model.mylluboxSigurvegari(bordL,bordD)) {
                //leikmadur sigrar myllubox
                GridPane bord = (GridPane) reitur.getParent();
                updateBordView(bord);
                model.setMylla(bordL,bordD);
                athugaSigurvegara();
            }
            athugaJafntefli();
            endaLeik(reiturL,reiturD);
        }
    }
    //skoðahvort til sé leifilegur leikur
    private void athugaJafntefli() {
        if (!model.gildStada()) {
            enginnVann();
        }
    }
    //Skoðahvort leikmaður vann
    private void athugaSigurvegara() {
        if (model.globalBordUnnid()) {
            leikmadurVann();
        }
    }
    //uppfærir logic fyrir næsta leik
    private void endaLeik(int bordL, int bordD) {
        model.setLastBord(bordL, bordD);
        model.getTaknStyle();
        //highlightNextMyllubox();
    }

    //skoða hvort það sé sigurvegari
    private void enginnVann(){
        Mylla.getChildren().clear();
        Mylla.getStyleClass().clear();
        Label sigurvegari = new Label("Jafnteffli!");
    }

    //uppfæra leikbord eftir að leikmaður vinnur
    private void leikmadurVann() {
        updateBordView(Mylla);
        Label sigurvegari = new Label("Þú vannst!");
        updateLabel(sigurvegari);
    }

    private void updateLabel(Label label){
        label.setFont(new Font("Comic Sans", 50));
        label.setTextFill(Paint.valueOf("WHITE"));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        Mylla.add(label, 1, 1);
    }

    //uppfærir leikbord eftir að ýtt er á takka
    private void updateReitView(Button reitur) {
        reitur.setStyle(model.getTaknStyle());
        reitur.setVisible(true);
        reitur.setDisable(true);
    }

    private void updateBordView(GridPane bord) {
        bord.getChildren().clear();
        bord.getStyleClass().clear();
        bord.setStyle(model.getTaknStyle());
    }

    private void highlightNextMyllubox() {
        ObservableList<Node> myllubox = Mylla.getChildren();
        for (Node bord : myllubox){
            int bordDalkur = GridPane.getColumnIndex(bord);
            int bordLina = GridPane.getRowIndex(bord);
            if (!model.bordUnnid(bordLina, bordDalkur)) {
                bord.setStyle(TOMUR);
            }
            if (!model.bordUnnid(bordLina, bordDalkur)) {
                bord.setStyle(HOVER);
            }
        }
        }
    }
