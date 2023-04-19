package hi.ofurmylla;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EndScreenController {

    static int P1Stig = 0;
    static int P2Stig = 0;

   static String vann1 ="";
   static String vann2 ="";

   public void initialize() {
       sjaHverSigrar();
   }

    public void sjaHverSigrar() {
        if (MylluController.winner%2==0) {
            P1Stig++;
            vann1 = "P1";
        }
        else {
            P2Stig++;
            vann2 = "P2";
        }
        stigPlus();
    }

    public void stigPlus(){
            stig1.setText(Integer.toString(P1Stig));
            stig2.setText(Integer.toString(P2Stig));
    }

    @FXML
    private Label stig1;



    @FXML
    private Label stig2;

    @FXML
    void nyrLeikur(ActionEvent event) {
        ViewSwitcher.switchTo(View.VELJA);


    }

}
