import javafx.application.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.geometry.*;
/**
 * Created by Elizabeth on 13.06.2017.
 */
public class JavaFXSkel extends Application {
    public static void main (String [] args) {
        launch(args);
    }
    public void start (Stage myStage) {
        myStage.setTitle("Image");
        FlowPane pane = new FlowPane();
        pane.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(pane, 500, 500);
        myStage.setScene(myScene);
        Image im1 = new Image("file:///C:\\Users\\Elizabeth\\Desktop\\IMG_20170516_132602.jpg");
        ImageView imv1 = new ImageView(im1);
        pane.getChildren().add(imv1);
        myStage.show();
    }
}
