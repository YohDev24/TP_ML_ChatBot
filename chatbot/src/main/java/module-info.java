module yobi {
    requires javafx.controls;
    requires javafx.fxml;

    opens yobi to javafx.fxml;
    exports yobi;
}
