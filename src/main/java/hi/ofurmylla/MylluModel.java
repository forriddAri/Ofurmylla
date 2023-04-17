package hi.ofurmylla;
public class MylluModel {

    private static final int DIM = 3;
    private static final String RED = "-fx-background-color: #0000ff";
    private static final String BLUE = "-fx-background-color: #ff0000";

    // Represents the entire board
    private final Boolean[][][][] ultimateBoard;
    // Represents just the global board (the 3 x 3 local boards)
    private final Boolean[][] Mylla;
    // indices of the local board played on
    private int lastReiturL;
    private int lastReiturD;
    private boolean currPlayer;

    public MylluModel() {
        ultimateBoard = new Boolean[DIM][DIM][DIM][DIM];
        Mylla = new Boolean[DIM][DIM];
        lastReiturL = DIM;
        lastReiturD = DIM;
        currPlayer = true;
    }

    // Determine if a local board has is "tied" or not (i.e. no winner and no valid
    // moves)
    private boolean localBoardFull(int boardRow, int boardCol) {
        for (int spaceRow = 0; spaceRow < DIM; spaceRow++) {
            for (int spaceCol = 0; spaceCol < DIM; spaceCol++) {
                if (ultimateBoard[boardRow][boardCol][spaceRow][spaceCol] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Determine if the game is still playable (i.e. still active moves)
    public boolean loglegtGameState() {
        for (int boardRow = 0; boardRow < DIM; boardRow++) {
            for (int boardCol = 0; boardCol < DIM; boardCol++) {
                if (Mylla[boardRow][boardCol] == null && !localBoardFull(boardRow, boardCol)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Set the new position of the allowed playable board/boards
    public void setLastBoard(int lina, int dalkur) {
        lastReiturL = lina;
        lastReiturD = dalkur;
        // edge case to check if a player has sent his opponent to an unplayable/invalid board
        // if so the opponent can move on any board not won/full
        if (Mylla[lina][dalkur] != null || localBoardFull(lina, dalkur)) {
            lastReiturL = DIM;
            lastReiturD = DIM;
        }
    }

    // Determine if the move played at is a valid/playable move
    public boolean validMove(int boardRow, int boardCol, int spaceRow, int spaceCol) {
        boolean spaceOpen = ultimateBoard[boardRow][boardCol][spaceRow][spaceCol] == null;
        // if the board is valid, and the space is open, the player can move
        return (validBoard(boardRow, boardCol) && spaceOpen);
    }

    // Determine if the local board is a valid/playable board
    public boolean validBoard(int boardRow, int boardCol) {
        return (Mylla[boardRow][boardCol] == null && !localBoardFull(boardRow, boardCol)
                && (lastReiturL == DIM || boardRow == lastReiturL && boardCol == lastReiturD));
    }

    // Algorithm to check for a win (i.e. 3 in a row) of a 2D array
    private boolean boardWinner(Boolean[][] board) {
        // check horizontals
        for (int row = 0; row < DIM; row++) {
            if (board[row][0] != null && board[row][0] == board[row][1] && board[row][0] == board[row][2]) {
                return true;
            }
        }
        // check verticals
        for (int col = 0; col < DIM; col++) {
            if (board[0][col] != null && board[0][col] == board[1][col] && board[0][col] == board[2][col]) {
                return true;
            }
        }

        // check diagonals
        return (board[0][0] != null && board[0][0] == board[1][1] && board[0][0] == board[2][2])
                || (board[0][2] != null && board[0][2] == board[1][1] && board[0][2] == board[2][0]);
    }

    // Determine if a specific local board is won or not
    public boolean boardWon(int boardRow, int boardCol) {
        return Mylla[boardRow][boardCol] != null;
    }

    // Determine if someone won a local board
    public boolean localBoardWinner(int boardR, int boardC) {
        return (boardWinner(ultimateBoard[boardR][boardC]));
    }

    // Determine if someone won the game
    public boolean globalBoardWinner() {
        return boardWinner(Mylla);
    }

    // Update local board value
    public void setLocalBoard(int boardRow, int boardCol, int spaceRow, int spaceCol) {
        ultimateBoard[boardRow][boardCol][spaceRow][spaceCol] = currPlayer;
    }

    // Update global board value
    public void setGlobalBoard(int boardRow, int boardCol) {
        Mylla[boardRow][boardCol] = currPlayer;
    }

    // Change who the current player is
    public void togglePlayer() {
        currPlayer = !currPlayer;
    }

    // Give the controller the current player's style
    public String getPlayerStyle() {
        return currPlayer ? RED : BLUE;
    }
}
