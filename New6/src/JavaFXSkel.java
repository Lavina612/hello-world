import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.geometry.*;

import java.time.LocalDate;

/**
 * Created by Elizabeth on 13.06.2017.
 */
public class JavaFXSkel extends Application {
    Label response;
    CheckBox web;
    CheckBox desktop;
    public static void main (String [] args) {
        launch(args);
    }
    public void start (Stage myStage) {
        myStage.setTitle("CheckBoxex");
        GridPane rootNode = new GridPane();
        HBox hbox = new HBox(10);
        FlowPane pane = new FlowPane(10,10);
        rootNode.setAlignment(Pos.CENTER);
        Scene myScene = new Scene(rootNode, 500, 500);
//        myStage.setScene(second);

        myStage.setScene(myScene);
        DatePicker datePick = new DatePicker();
        datePick.setValue(LocalDate.of(2017,6,13));
        datePick.setShowWeekNumbers(true);
        ProgressBar prBar = new ProgressBar();
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPrefWidth(250);
        ColumnConstraints columnConstraints1 = new ColumnConstraints();
        columnConstraints1.setPrefWidth(250);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPrefHeight(100);
        rootNode.getColumnConstraints().addAll(columnConstraints, columnConstraints1);
        rootNode.getRowConstraints().addAll(rowConstraints);
        web = new CheckBox("Web");
        desktop = new CheckBox("Desktop");
        response = new Label("No deployment selected");
        web.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (web.isSelected())
                    response.setText("Web deployment selected");
                else response.setText("Web deployment cleared");
            }
        });
        desktop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (desktop.isSelected())
                    response.setText("Desktop deployment selected");
                else response.setText("Desktop deployment cleared");
            }
        });
        rootNode.setGridLinesVisible(true);
        hbox.getChildren().addAll(datePick, prBar);
        pane.getChildren().addAll(web, desktop, response);
        rootNode.add(hbox,0,0);
        rootNode.add(pane, 1,1);
        myStage.show();
    }
}
