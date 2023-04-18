package hi.ofurmylla;


public enum View {
    LOGIN("TaknSelect.fxml"),
    MAIN("Mylla.fxml"),
    ABOUT("about.fxml");

    private String fileName;

    View(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
