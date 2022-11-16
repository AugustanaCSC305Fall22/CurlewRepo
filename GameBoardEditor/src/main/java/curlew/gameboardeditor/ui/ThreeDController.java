

package curlew.gameboardeditor.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.RandomMapGenerator;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class ThreeDController extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static double BOX_WIDTH =10;
	private static double BOX_HEIGHT =10;
	private static double BOX_DEPTH =5;
 
	private Button backButton;
	
    public void start(Stage primaryStage) throws Exception {
    	TerrainMap map = App.getMap();

		
		Box[][] boxArray = new Box[map.getRows()][map.getColumns()];
		SmartGroup group = new SmartGroup();
		for(int i=0;i<map.getRows();i++) {
			for(int j=0;j<map.getColumns();j++) {
				boxArray[i][j] = new Box(BOX_WIDTH,BOX_HEIGHT,BOX_DEPTH*map.getHeight(i, j));
				group.getChildren().add(boxArray[i][j]);
				boxArray[i][j].translateXProperty().set((BOX_WIDTH/2)+j*(BOX_WIDTH));
				boxArray[i][j].translateYProperty().set((BOX_HEIGHT/2)+i*(BOX_HEIGHT));
				boxArray[i][j].translateZProperty().set(-map.getHeight(i, j)*BOX_DEPTH/2);
//				Transform transform = new Rotate(60,Rotate.X_AXIS);
//				boxArray[i][j].getTransforms().add(transform);
			}
		}
//		AmbientLight light = new AmbientLight();
	    //Set light color
//	    light.setColor(Color.DEEPSKYBLUE);
//
//		group.getChildren().add(light);
		
//		group.getChildren().add(new Box(2,10,2));
        //Create new Camera
        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH, HEIGHT,true);
        scene.setFill(Color.SILVER);
        //Attach to scene
        scene.setCamera(camera);
        
 
        //Move back a little to get a good view of the sphere
       camera.translateYProperty().set(map.getRows()*BOX_WIDTH/2);
       camera.translateXProperty().set((map.getRows())*BOX_WIDTH/2);
       camera.translateZProperty().set(-1000);
 
        //Set the clipping planes
        camera.setNearClip(1);
        camera.setFarClip(10000);
 
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
              case W:
                group.translateZProperty().set(group.getTranslateZ() + 100);
                break;
              case S:
                group.translateZProperty().set(group.getTranslateZ() - 100);
                break;
              case Q:
                group.rotateByX(10);
                break;
              case E:
                group.rotateByX(-10);
                break;
              case A:
                group.rotateByY(10);
                break;
              case Z:
                group.rotateByY(-10);
                break;
            }
          });
 
 //       primaryStage.setTitle("Genuine Coder");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
 
 
    public static void main(String[] args) {
        launch(args);
    }
    
    class SmartGroup extends Group {
    	 
        Rotate r;
        Transform t = new Rotate();
     
        void rotateByX(int ang) {
          r = new Rotate(ang, Rotate.X_AXIS);
          t = t.createConcatenation(r);
          this.getTransforms().clear();
          this.getTransforms().addAll(t);
        }
     
        void rotateByY(int ang) {
          r = new Rotate(ang, Rotate.Y_AXIS);
          t = t.createConcatenation(r);
          this.getTransforms().clear();
          this.getTransforms().addAll(t);
        }
      }
}


	

	


