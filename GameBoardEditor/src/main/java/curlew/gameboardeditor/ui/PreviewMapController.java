

package curlew.gameboardeditor.ui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.ImageView;


public class PreviewMapController {

    

    @FXML
    private Button backButton;

    @FXML
    private Label mapNameLabel;

    @FXML
    private ScrollBar mapScrollBar;

    @FXML
    private Button nextButton;

    @FXML
    private ImageView previewImageView;

    @FXML
    private Label titleLabel;

    @FXML
    void clickNext(ActionEvent event) throws IOException {
    	App.setRoot("mapEditor");
    }

    @FXML
    void clickedBack(ActionEvent event) throws IOException {
    	App.setRoot("mainMenu");
    }

}
