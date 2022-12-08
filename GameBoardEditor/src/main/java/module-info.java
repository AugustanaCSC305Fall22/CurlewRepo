module curlew.gameboardeditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires org.json;
	requires com.google.gson;
	requires javafx.base;
	requires java.desktop;

    opens curlew.gameboardeditor.ui to javafx.fxml, javafx.graphics;
    opens curlew.gameboardeditor.datamodel to com.google.gson;
    exports curlew.gameboardeditor.ui;
}

