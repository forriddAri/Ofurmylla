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

    private static final String HIGHLIGHT = "-fx-background-color: #FFFFD4";
    private static final String TOMUR = "-fx-background-color: #00000000";

    private MylluModel model;
    @FXML
    private GridPane Mylla;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new MylluModel();
        highlightNextBoard();
    }

    // Controller recognizes a button was pressed
    public void handleButtonToggle(ActionEvent event) {
        // button that was pressed
        Button square = (Button) event.getSource();
        performMove(square);
    }

    // Control what happens to the view and the model
    private void performMove(Button square) {
        int squareR = GridPane.getRowIndex(square);
        int squareC = GridPane.getColumnIndex(square);
        // row index of the local board
        int boardR = GridPane.getRowIndex(square.getParent());
        // col index of the local board
        int boardC = GridPane.getColumnIndex(square.getParent());
        if (model.validMove(boardR, boardC, squareR, squareC)) {
            // player moved
            updateSquareView(square);
            model.setLocalBoard(boardR, boardC, squareR, squareC);
            if (model.localBoardWinner(boardR, boardC)) {
                // player won the local board
                GridPane board = (GridPane) square.getParent();
                updateBoardView(board);
                model.setGlobalBoard(boardR, boardC);
                checkWinner();
            }
            checkTie();
            endTurn(squareR, squareC);
        }
    }

    // Determine if there are any valid moves left, if not, it's a tie game
    private void checkTie() {
        if (!model.validGameState()) {
            // no valid moves left
            nobodyWon();
        }
    }

    // Determine if a player won the game
    private void checkWinner() {
        if (model.globalBoardWinner()) {
            // player won the game!
            playerWon();
        }
    }

    // update logic for the next players turn
    private void endTurn(int nextBoardR, int nextBoardC) {
        model.setLastBoard(nextBoardR, nextBoardC);
        model.togglePlayer();
        highlightNextBoard();
    }

    // view displayed if nobody won
    private void nobodyWon() {
        Mylla.getChildren().clear();
        Mylla.getStyleClass().clear();
        Mylla.setStyle("-fx-background-color: #000000");
        Label winner = new Label("TIE!");
        updateLabel(winner);
    }

    // view displayed if a player won the game
    private void playerWon() {
        updateBoardView(Mylla);
        Label winner = new Label("YOU WON!");
        updateLabel(winner);
    }

    // sets view of the label correctly for end game
    private void updateLabel(Label label) {
        label.setFont(new Font("Arial", 50));
        label.setTextFill(Paint.valueOf("WHITE"));
        label.setMaxWidth(Double.MAX_VALUE);
        label.setMaxHeight(Double.MAX_VALUE);
        label.setAlignment(Pos.CENTER);
        Mylla.add(label, 1, 1);
    }

    // update the view when a valid button is pressed
    private void updateSquareView(Button square) {
        square.setStyle(model.getPlayerStyle());
        square.setOpacity(100.0);
        square.setDisable(true);
    }

    // update the view when a local board is won
    private void updateBoardView(GridPane board) {
        // update view
        board.getChildren().clear();
        board.getStyleClass().clear();
        board.setStyle(model.getPlayerStyle());
    }

    // un-highlight previous boards and highlight the new playable local
    // board/boards
    private void highlightNextBoard() {
        ObservableList<Node> boards = Mylla.getChildren();
        for (Node board : boards) {
            int boardRow = GridPane.getRowIndex(board);
            int boardCol = GridPane.getColumnIndex(board);
            if (!model.boardWon(boardRow, boardCol)) {
                board.setStyle(TOMUR);
            }
            if (model.validBoard(boardRow, boardCol)) {
                board.setStyle(HIGHLIGHT);
            }
        }
    }
}
