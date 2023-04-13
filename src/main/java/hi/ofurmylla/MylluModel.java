package hi.ofurmylla;

public class Model {
    private static final int Dim = 3;
    private static final String Takn1 = "-fx-Background-color: #00000ff";
    private static final String Takn2 = "-fx-Background-color: #ff00000";

    private final Boolean[][][][] ofurMylla;
    private final Boolean[][] globalBord;
    private int lastBordL;
    private int lastBordD;
    private boolean aLeik;

    public Model(){
        ofurMylla = new Boolean[Dim][Dim][Dim][Dim];
        globalBord = new Boolean[Dim][Dim];
        lastBordL = Dim;
        lastBordD = Dim;
        aLeik = true;
    }
    private boolean localBordFullt(int bordLina, int bordDalkur){
        for ( int bilLina = 0; bilLina < Dim ; bilLina++ ) {
            for ( int bilDalkur = 0; bilDalkur < Dim ; bilDalkur++ ) {
                if (globalBord[bordLina][bordDalkur] == null && !localBordFullt(bordLina,bordDalkur)) {
                    return true;
                }
            }

        }
        return false;
    }
    public boolean gildStada(){
        for (int bordLina = 0; bordLina < Dim ; bordLina++ ) {
            for (int bordDalkur = 0; bordDalkur < Dim ;bordDalkur++ ) {
                if (globalBord[bordLina][bordDalkur] == null && !localBordFullt(bordLina,bordDalkur)) {
                    return true;
                }
            }
        }
        return false;
    }
    public void setLastBord(int lina, int dalkur){
        lastBordL = lina;
        lastBordD = dalkur;
        if (globalBord[lina][dalkur] != null || localBordFullt(lina, dalkur)) {
            lastBordL = Dim;
            lastBordD = Dim;
        }
    }

    //Ákveður hvor leikur sé löglegur
    public boolean leikurleifdur(int bordLina,int bordDalkur, int bilLina, int bilDalkur){
        boolean bilOpid = ofurMylla[bordLina][bordDalkur][bilLina][bilDalkur] == null;
        //Ef gamestate er löglegt og það er veljanlegur reitur fær leikmaður að gera
        return (loglegtBord(bordLina,bordDalkur) && bilOpid);
    }
    public boolean loglegtBord(int bordLina, int bordDalkur){
        return (globalBord[bordLina][bordDalkur] == null && !localBordFullt(bordLina,bordDalkur))
        && (lastBordL == Dim || bordLina == lastBordL && bordDalkur == lastBordD);
    }

    private boolean bordSigurvegari(Boolean[][] bord){
        //Skoðar láréttan sigur
        for (int lina = 0; lina < Dim; lina++){
            if (bord[lina][0] != null && bord[lina][0] == bord[lina][1] && bord[lina][0] == bord[lina][2]) {
                return true;
            }
        }
        //skoðar Lóðréttan sigur
        for (int dalkur = 0; dalkur < Dim ; dalkur ++ ) {
            if (bord[0][dalkur] != null && bord[0][dalkur] == bord[1][dalkur] && bord[0][dalkur] == bord[2][dalkur]) {
                return true;
            }
        }
        //skoðar ská sigur
        return (bord[0][0] != null && bord[0][0] == bord[1][1] && bord[0][0] == bord[2][2]) || (bord[0][2]
                != null && bord[0][2] == bord[1][1] && bord[0][2] == bord[2][0]);
    }
    //Ákvarðar hvort myllubox sé unnið
    public boolean bordUnnid(int bordLina, int bordDalkur){
        return globalBord[bordLina][bordDalkur] != null;
    }
    public boolean mylluboxSigurvegari(int bordL, int bordD){
        return bordSigurvegari(ofurMylla[bordL][bordD]);
    }
    //Ákvarðar hvort leikur sé uninn
    public boolean globalBordUnnid(){
        return bordSigurvegari(globalBord);
    }
    public void setMyllubox(int bordLina,int bordDalkur, int bilLina,int bilDalkur){
        ofurMylla[bordLina][bordDalkur][bilLina][bilDalkur] = aLeik;
    }
    public void setMylla(int bordLina, int bordDalkur){
        globalBord[bordLina][bordDalkur] = aLeik;
    }
    //skiptir um leikmann
    public String getTaknStyle(){
        return aLeik ? Takn1 : Takn2;
    }



}
