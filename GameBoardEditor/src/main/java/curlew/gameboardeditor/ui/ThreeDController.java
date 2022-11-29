

package curlew.gameboardeditor.ui;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import curlew.gameboardeditor.datamodel.GameBoardIO;
import curlew.gameboardeditor.datamodel.TerrainMap;
import curlew.gameboardeditor.datamodel.TestClass;
import curlew.gameboardeditor.generators.RandomMapGenerator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class ThreeDController extends Application {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;
    private static double BOX_WIDTH =10;
	private static double BOX_HEIGHT =10;
	private static double BOX_DEPTH =5;
 
	private Button backButton;
	
	private final DoubleProperty angleX = new SimpleDoubleProperty(0);
	private final DoubleProperty angleY = new SimpleDoubleProperty(0);
	private double anchorX, anchorY;
	private double anchorAngleX = 0;
	private double anchorAngleY = 0;
	
    public void start(Stage primaryStage) throws Exception {
    	TerrainMap map = App.getMap();

		
		Box[][] boxArray = new Box[map.getRows()][map.getColumns()];
		SmartGroup group = new SmartGroup();
		for(int i=0;i<map.getRows();i++) {
			for(int j=0;j<map.getColumns();j++) {
				boxArray[i][j] = new Box(BOX_WIDTH,BOX_HEIGHT,BOX_DEPTH*map.getHeight(i, j));
				group.getChildren().add(boxArray[i][j]);
//				group.getChildren().addAll(prepareLightSource());
				boxArray[i][j].translateXProperty().set((BOX_WIDTH/2)+j*(BOX_WIDTH));
				boxArray[i][j].translateYProperty().set((BOX_HEIGHT/2)+i*(BOX_HEIGHT));
				boxArray[i][j].translateZProperty().set(-map.getHeight(i, j)*BOX_DEPTH/2);
			}
		}
		group.getChildren().addAll(prepareLightSource());

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
              case DOWN:
                group.rotateByX(10);
                break;
              case UP:
                group.rotateByX(-10);
                break;
              case LEFT:
                group.rotateByY(10);
                break;
              case RIGHT:
                group.rotateByY(-10);
                break;
            }
          });
 
 //       primaryStage.setTitle("Genuine Coder");
        
        initMouseControl(group, scene, primaryStage);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				// TODO Auto-generated method stub
				lightSource.setRotate(lightSource.getRotate() + 1);
			}
        	
        };
        timer.start();
    }
    
    //Got information from Genuine Coder.
    private final PointLight lightSource = new PointLight();
    
    private Node[] prepareLightSource() {
    	lightSource.setColor(Color.RED);
    	lightSource.getTransforms().add(new Translate(150, -150, 100));
    	lightSource.setRotationAxis(Rotate.X_AXIS);
    	
    	Sphere lightSphere = new Sphere(5);
    	lightSphere.getTransforms().setAll(lightSource.getTransforms());
    	lightSphere.rotateProperty().bind(lightSource.rotateProperty());
    	lightSphere.rotationAxisProperty().bind(lightSource.rotationAxisProperty());
    	
//    	Node[] lightNode = new Node[] {lightSource, lightSphere};
    	
    	return new Node[] {lightSource, lightSphere};
    }
    
    private void initMouseControl(SmartGroup group, Scene scene, Stage stage) {
    	Rotate rotateX;
    	Rotate rotateY;
    	group.getTransforms().addAll(rotateX = new Rotate(0, Rotate.X_AXIS), rotateY = new Rotate(0, Rotate.Y_AXIS));
    	rotateX.angleProperty().bind(angleX);
        rotateY.angleProperty().bind(angleY);
        
        scene.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = angleX.get();
            anchorAngleY = angleY.get();
          });

          scene.setOnMouseDragged(event -> {
            angleX.set(anchorAngleX - (anchorY - event.getSceneY()));
            angleY.set(anchorAngleY + anchorX - event.getSceneX());
          });

          stage.addEventHandler(ScrollEvent.SCROLL, event -> {
            double delta = event.getDeltaY();
            group.translateZProperty().set(group.getTranslateZ() + delta);
          });
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


	

	


