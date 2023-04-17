package hi.ofurmylla;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;




public class TaknSelectController {

    private int leikamadur = 0;

    @FXML
    private Label Leikmadur1Demacia;

    @FXML
    private Label Leikmadur1Noxus;

    @FXML
    private Label Leikmadur1Shurima;

    @FXML
    private Label Leikmadur2Demacia;

    @FXML
    private Label Leikmadur2Noxus;

    @FXML
    private Label Leikmadur2Shurima;

    @FXML
    void VeljaDemacia(MouseEvent event) {
        if (leikamadur == 0) {
            Leikmadur1Demacia.setVisible(true);
            leikamadur++;
        }
        else {
            Leikmadur2Demacia.setVisible(true);
        }
        demaciaButton.setDisable(true);
    }

    @FXML
    void VeljaNoxus(MouseEvent event) {
        if (leikamadur == 0) {
            Leikmadur1Noxus.setVisible(true);
            leikamadur++;
        }
        else {
            Leikmadur2Noxus.setVisible(true);
        }
        noxusButton.setDisable(true);

    }

    @FXML
    void VeljaShurima(MouseEvent event) {
        if (leikamadur == 0) {
            Leikmadur1Shurima.setVisible(true);
            leikamadur++;
        }
        else {
            Leikmadur2Shurima.setVisible(true);
        }
        shurimaButton.setDisable(true);

    }
    @FXML
    private Button demaciaButton;

    @FXML
    private Button noxusButton;

    @FXML
    private Button shurimaButton;


    @FXML
    void hoverExitDemacia(MouseEvent event) {
        demaciaButton.styleProperty().set("-fx-background-color: #00000000");
    }

    @FXML
    void hoverExitNoxus(MouseEvent event) {
        noxusButton.styleProperty().set("-fx-background-color: #00000000");

    }

    @FXML
    void hoverExitShurima(MouseEvent event) {
        shurimaButton.styleProperty().set("-fx-background-color: #00000000");

    }

    @FXML
    void hoveringDemacia(MouseEvent event) {
        noxusButton.styleProperty().set("-fx-background-color: #3399FF");

    }

    @FXML
    void hoveringNoxus(MouseEvent event) {
        noxusButton.styleProperty().set("-fx-background-color: #FF3333");
    }

    @FXML
    void hoveringShurima(MouseEvent event) {
        shurimaButton.styleProperty().set("-fx-background-color: #FFFF00");

    }

}


