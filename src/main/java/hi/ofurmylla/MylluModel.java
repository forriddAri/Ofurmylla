package hi.ofurmylla;
public class MylluModel {

    private static final int DIM = 3;

    public String TAKN1 = "-fx-background-color: #3399FF";
    public String TAKN2 = "-fx-background-color: #FF3333";

    // Represents the entire board
    private final Boolean[][][][] ultimateBoard;
    // Represents just the global board (the 3 x 3 local boards)
    private final Boolean[][] Mylla;
    // indices of the local board played on
    private int lastReiturL;
    private int lastReiturD;
    private boolean aLeik;

    public MylluModel() {
        ultimateBoard = new Boolean[DIM][DIM][DIM][DIM];
        Mylla = new Boolean[DIM][DIM];
        lastReiturL = DIM;
        lastReiturD = DIM;
        aLeik = true;
    }

    // Skoðar hvort það sé jafntefli
    private boolean localBoardFull(int bordLina, int bordDalkur) {
        for (int spaceLina = 0; spaceLina < DIM; spaceLina++) {
            for (int spaceDalkur = 0; spaceDalkur < DIM; spaceDalkur++) {
                if (ultimateBoard[bordLina][bordDalkur][spaceLina][spaceDalkur] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Skoða hvort séu leyfð move
    public boolean loglegtGameState() {
        for (int bordLina = 0; bordLina < DIM; bordLina++) {
            for (int bordDalkur = 0; bordDalkur < DIM; bordDalkur++) {
                if (Mylla[bordLina][bordDalkur] == null && !localBoardFull(bordLina, bordDalkur)) {
                    return true;
                }
            }
        }
        return false;
    }

    // SKoða staðsetningu löglegra movea
    public void setLastBoard(int lina, int dalkur) {
        lastReiturL = lina;
        lastReiturD = dalkur;
        // Skoða hvort leikur sé leyfður, ef ekki þá má andstæðingur velja hvaða myllubox sem er
        if (Mylla[lina][dalkur] != null || localBoardFull(lina, dalkur)) {
            lastReiturL = DIM;
            lastReiturD = DIM;
        }
    }

    // Löglegt move
    public boolean loglegtMove(int boardRow, int boardCol, int spaceRow, int spaceCol) {
        boolean spaceOpen = ultimateBoard[boardRow][boardCol][spaceRow][spaceCol] == null;
        // if the board is valid, and the space is open, the player can move
        return (loglegtBord(boardRow, boardCol) && spaceOpen);
    }

    // Skoða hvort borð sé unnið
    public boolean loglegtBord(int boardRow, int boardCol) {
        return (Mylla[boardRow][boardCol] == null && !localBoardFull(boardRow, boardCol)
                && (lastReiturL == DIM || boardRow == lastReiturL && boardCol == lastReiturD));
    }

    // Skoða sigurvegara
    private boolean bordWinner(Boolean[][] board) {
        // Skoðar láréttar myllur
        for (int row = 0; row < DIM; row++) {
            if (board[row][0] != null && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
                return true;
            }
        }
        // Skoðar lórétta myllur
        for (int col = 0; col < DIM; col++) {
            if (board[0][col] != null && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
                return true;
            }
        }

        // skoðar mylly á ská
        return (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2])
                || (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    // Skoða hvort borð sé unnið
    public boolean bordUnnid(int bordLina, int bordDalkur) {
        return Mylla[bordLina][bordDalkur] != null;
    }

    // Sjá hvort local borð sé unnið
    public boolean localBoardSigurvegari(int boardL, int boardD) {
        return (bordWinner(ultimateBoard[boardL][boardD]));
    }

    // Skoða hvort leikur sé uninn
    public boolean mylluSigurvegari() {
        return bordWinner(Mylla);
    }


    public void setLocalBoard(int boardRow, int boardCol, int spaceRow, int spaceCol) {
        ultimateBoard[boardRow][boardCol][spaceRow][spaceCol] = aLeik;
    }


    public void setGlobalBoard(int boardRow, int boardCol) {
        Mylla[boardRow][boardCol] = aLeik;
    }

    // Change who the current player is
    public void togglePlayer() {
        aLeik = !aLeik;
    }

    // SKoðar hver á að gera
    public String getTakn() {
        return aLeik ? TAKN1 : TAKN2;
    }
}
