module curlew.gameboardeditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.json;
	requires com.google.gson;

    opens curlew.gameboardeditor.ui to javafx.fxml;
    opens curlew.gameboardeditor.datamodel to com.google.gson;
    exports curlew.gameboardeditor;
}
