module curlew.gameboardeditor {
    requires javafx.controls;
    requires javafx.fxml;

    opens curlew.gameboardeditor to javafx.fxml;
    exports curlew.gameboardeditor;
}
