module curlew.gameboardeditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;

    opens curlew.gameboardeditor to javafx.fxml;
    exports curlew.gameboardeditor;
}
