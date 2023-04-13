module hi.ofurmylla {
    requires javafx.controls;
    requires javafx.fxml;


    opens hi.ofurmylla to javafx.fxml;
    exports hi.ofurmylla;
}
