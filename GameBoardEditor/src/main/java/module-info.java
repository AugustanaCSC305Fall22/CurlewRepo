module curlew.gameboardeditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.json;

    opens curlew.gameboardeditor to javafx.fxml;
    exports curlew.gameboardeditor;
}
