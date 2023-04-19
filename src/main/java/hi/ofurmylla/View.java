package hi.ofurmylla;


public enum View {
    VELJA("TaknSelect.fxml"),
    MAIN("Mylla.fxml"),
    STIG("EndScreen.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
