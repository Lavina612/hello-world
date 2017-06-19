import java.io.*;
import java.time.LocalDate;
import java.util.Vector;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.stage.*;

/**
 * @author Elizabeth, P3111 :)
 */

public class Main extends Application{
    private static Vector<Person> vector = new Vector<>();
    private static Person min = null;
    private static Label answer;
    private static Label question;
    private static Label iWant;
    private static CheckBox chb1;
    private static CheckBox chb2;
    private static CheckBox chb3;
    private static Button close;
    private static Button enter;
    private static Button closeInteractive;
    private static RadioButton nameRB;
    private static RadioButton phraseRB;
    private static FlowPane image;
    private static GridPane root;
    private static AnchorPane pane10;
    private static FlowPane pane11;
    private static FlowPane pane20;
    private static TreeView<String> tvPerson = new TreeView<>();
    private static TextField add;
    private static TextField indexField;
    private static TextField change;
    private static TextField phrase;
    private static int i=0;

    public void start(Stage stage) {
        stage.setTitle("Laba06");
        root = new GridPane();
        root.setStyle("-fx-background-color: aliceblue");
        HBox hbox00 = new HBox(15);
        FlowPane pane01 = new FlowPane(10,10);
        pane10 = new AnchorPane();
        pane11 = new FlowPane(10,10);
        pane11.getChildren().add(tvPerson);
        pane20 = new FlowPane(20,15);
        FlowPane pane21 = new FlowPane(10,10);
        image = new FlowPane();

        hbox00.setAlignment(Pos.CENTER);
        pane01.setAlignment(Pos.CENTER);
        pane11.setAlignment(Pos.TOP_CENTER);
        pane20.setAlignment(Pos.CENTER);
        pane21.setAlignment(Pos.CENTER);
        image.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(600);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(250);
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(500);
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(50);
        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2, row3);
        root.setGridLinesVisible(true);

        answer = new Label("");
        question = new Label("Will you give me a variant for laba 8?");
        chb1 = new CheckBox("Yes");
        chb2 = new CheckBox("Of course");
        chb3 = new CheckBox ("Absolutely");
        chb1.setOnAction(event -> {
                if (chb1.isSelected())
                    answer.setText("Thank you!");
                else answer.setText("Don't remove this stick");
            }
        );
        chb2.setOnAction(event -> {
                if (chb2.isSelected())
                    answer.setText("Thank you very much!");
                else answer.setText("Don't remove this stick!");
            }
        );
        chb3.setOnAction(event -> {
                if (chb3.isSelected())
                    answer.setText("It's so nice! You're so kind!");
                else answer.setText("Don't remove this stick!!!");
            }
        );

        ProgressBar bar = new ProgressBar();

        DatePicker datePick = new DatePicker();
        datePick.setValue(LocalDate.of(2017,6,16));
        datePick.setShowWeekNumbers(true);
        datePick.relocate(350, 10);
        Button load = new Button ("load");
//        load.setTextFill(Color.AQUAMARINE);
//        load.setPrefSize(150,50);
//        load.setFont(new Font(20));
//        load.setStyle("-fx-background-color: lawngreen");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        TextField userTextField = new TextField();
//        Text actiontarget = new Text();
        Button remove_last = new Button ("remove last element");
        Button remove = new Button ("remove (index)");
        Button add_if_min = new Button ("add if min");
        Button save = new Button ("save");
        Button update = new Button ("update");
        load.relocate(15, 150);
        update.relocate(15, 185);
        save.relocate(15, 220);
        remove_last.relocate(15, 255);
        remove.relocate(15, 290);
        add_if_min.relocate(15, 325);
        enter = new Button("Enter");
        enter.relocate(440,230);
        closeInteractive = new Button("Close");
        closeInteractive.relocate(440,270);
        load.setOnAction(event -> {
            load();
            getMin();
            rewriting();
        });
        remove_last.setOnAction(event -> {
                remove_last();
                getMin();
                rewriting();
        });
        save.setOnAction(event -> save());
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checking();
                indexField = new TextField();
                indexField.relocate(250,230);
                indexField.setPromptText("Write number of element");
                pane10.getChildren().addAll(indexField, enter, closeInteractive);
                enter.setOnAction(event1 -> index(indexField));
                indexField.setOnAction(event1 -> index(indexField));
                closeInteractive.setOnAction(event1 -> pane10.getChildren().removeAll(indexField, enter, closeInteractive));
            }
        });
        add_if_min.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checking();
                add = new TextField();
                add.relocate(220,230);
                add.setPrefColumnCount(16);
                add.setPromptText("Enter name or JSON-person");
                pane10.getChildren().addAll(add, enter, closeInteractive);
                enter.setOnAction( event1 -> add_if_min(add));
                add.setOnAction( event1 -> add_if_min(add));
                closeInteractive.setOnAction( event1 -> pane10.getChildren().removeAll(add, phrase, enter, closeInteractive));
            }
        });
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checking();
                if (pane10.getChildren().contains(nameRB)) pane10.getChildren().remove(nameRB);
                if (pane10.getChildren().contains(phraseRB)) pane10.getChildren().remove(phraseRB);
                change = new TextField();
                change.relocate(250,230);
                change.setPromptText("Enter number of person");
                pane10.getChildren().addAll(change, enter, closeInteractive);
                enter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });
                change.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            Integer integer = new Integer(change.getText());
                            integer--;
                            if(integer<vector.size()) {
                                if (integer >= 0) {
                                    int a = integer;
                                    change.setText("");
                                    pane10.getChildren().remove(change);
                                    nameRB = new RadioButton("Name");
                                    phraseRB = new RadioButton("Phrase");
                                    ToggleGroup np = new ToggleGroup();
                                    nameRB.setToggleGroup(np);
                                    phraseRB.setToggleGroup(np);
                                    nameRB.relocate(250, 235);
                                    phraseRB.relocate(250, 270);
                                    pane10.getChildren().addAll(nameRB, phraseRB);
                                    nameRB.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            pane10.getChildren().removeAll(nameRB, phraseRB);
                                            //change.relocate(220,230);
                                            change.setPromptText("Enter name");
                                            pane10.getChildren().add(change);
                                            enter.setOnAction(event1 -> {
                                                    vector.get(a).setName(change.getText());
                                                    getMin();
                                                    rewriting();
                                                    }
                                            );
                                            change.setOnAction(event1 -> {
                                                    vector.get(a).setName(change.getText());
                                                    getMin();
                                                    rewriting();
                                                    pane10.getChildren().removeAll(change, enter, closeInteractive);
                                                }
                                            );
                                        }
                                    });
                                    phraseRB.setOnAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            pane10.getChildren().removeAll(nameRB, phraseRB);
                                            change.setPromptText("Enter number of phrase");
                                            pane10.getChildren().add(change);
                                            enter.setOnAction(event1 -> {
                                                try {
                                                    Integer int2 = new Integer(change.getText());
                                                    System.out.println(int2);
                                                    int b = --int2;
                                                    System.out.println(b);
                                                    change.setText("");
                                                    if(int2 < 0)
                                                        System.out.println("Sorry, you're stupid");
                                                    else
                                                    if (int2 >= vector.get(a).getPhrases().size())
                                                        System.out.println("Stupid 2");
                                                    else {
                                                        pane10.getChildren().remove(change);
                                                        change.setPromptText("Enter a phrase");
                                                        pane10.getChildren().add(change);
                                                        change.setOnAction(event2 -> {
                                                            vector.get(a).setPhrase(b, new Phrase (change.getText()));
                                                            change.setText("");
                                                            getMin();
                                                            rewriting();
                                                        });
                                                        enter.setOnAction(event2 -> {
                                                            vector.get(a).setPhrase(b, new Phrase (change.getText()));
                                                            change.setText("");
                                                            getMin();
                                                            rewriting();
                                                        });
                                                    }
                                                } catch (NumberFormatException e) {
                                                    System.out.println("Sorry, but you wrote a nonsense");
                                                    change.setText("");
                                                }
                                            });
                                            change.setOnAction(event1 -> {
                                            try {
                                                Integer int2 = new Integer(change.getText());
                                                System.out.println(int2);
                                                int b = --int2;
                                                System.out.println(b);
                                                change.setText("");
                                                if(int2 < 0)
                                                    System.out.println("Sorry, you're stupid");
                                                else
                                                    if (int2 >= vector.get(a).getPhrases().size())
                                                        System.out.println("Stupid 2");
                                                    else {
                                                        pane10.getChildren().remove(change);
                                                        change.setPromptText("Enter a phrase");
                                                        pane10.getChildren().add(change);
                                                        change.setOnAction(event2 -> {
                                                            vector.get(a).setPhrase(b, new Phrase (change.getText()));
                                                            change.setText("");
                                                            getMin();
                                                            rewriting();
                                                        });
                                                        enter.setOnAction(event2 -> {
                                                            vector.get(a).setPhrase(b, new Phrase (change.getText()));
                                                            change.setText("");
                                                            getMin();
                                                            rewriting();
                                                        });
                                                    }
                                            } catch (NumberFormatException e) {
                                                System.out.println("Sorry, but you wrote a nonsense");
                                                change.setText("");
                                            }
                                        });
                                        }
                                    });
                                } else {
                                    System.out.println("The number less than elements in collection");
                                    change.setText("");
                                }
                            } else {
                                System.out.println("The number more than elements in collection");
                                change.setText("");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Sorry, but you wrote a nonsense");
                            change.setText("");
                        }
                    }
                });
                closeInteractive.setOnAction(event1 -> {
                    pane10.getChildren().removeAll(change, enter, closeInteractive);
                    if (pane10.getChildren().contains(nameRB)) pane10.getChildren().remove(nameRB);
                    if (pane10.getChildren().contains(phraseRB)) pane10.getChildren().remove(phraseRB);
                });

            }
        });

        Image im1 = new Image("/images/123.jpg");
        ImageView imv1 = new ImageView(im1);
        imv1.setPreserveRatio(true);
        imv1.setFitHeight(400);
        imv1.setFitWidth(400);
        Image im2 = new Image("/images/вт2.jpg");
        ImageView imv2 = new ImageView(im2);
        imv2.setPreserveRatio(true);
        imv2.setFitHeight(400);
        imv2.setFitWidth(400);
        Image im3 = new Image("/images/778.jpg");
        ImageView imv3 = new ImageView(im3);
        imv3.setPreserveRatio(true);
        imv3.setFitHeight(400);
        imv3.setFitWidth(400);
        Image im4 = new Image("/images/Письмак.jpg");
        ImageView imv4 = new ImageView(im4);
        imv4.setPreserveRatio(true);
        imv4.setFitHeight(400);
        imv4.setFitWidth(400);
        close = new Button("Close");
        close.setOnAction(event -> root.getChildren().removeAll(image));
        iWant = new Label ("I want to see...");
        RadioButton rb1 = new RadioButton("VT");
        RadioButton rb2 = new RadioButton("duck");
        RadioButton rb3 = new RadioButton("our dreams");
        RadioButton rb4 = new RadioButton("my photo");
        ToggleGroup tg = new ToggleGroup();
        rb1.setToggleGroup(tg);
        rb2.setToggleGroup(tg);
        rb3.setToggleGroup(tg);
        rb4.setToggleGroup(tg);
        rb1.setOnAction(event -> imagination(imv1, imv4, imv2, imv3));
        rb2.setOnAction(event -> imagination(imv2, imv1, imv4, imv3));
        rb3.setOnAction(event -> imagination(imv3, imv1, imv2, imv4));
        rb4.setOnAction(event -> imagination(imv4, imv1, imv2, imv3));

        Button exit = new Button("Exit");
        exit.setOnAction(event -> exit());
        load();
        getMin();
        rewriting();
        hbox00.getChildren().addAll(question,chb1,chb2,chb3,answer);
        pane01.getChildren().addAll(bar);
        pane10.getChildren().addAll(datePick, load, update, save, remove_last, remove, add_if_min);
        pane20.getChildren().addAll(iWant, rb1, rb2, rb3, rb4);
        pane21.getChildren().addAll(exit);
        root.add(hbox00, 0,0);
        root.add(pane01, 1, 0);
        root.add(pane10, 0, 1);
        root.add(pane11, 1, 1);
        root.add(pane20, 0, 2);
        root.add(pane21, 1, 2);
        Scene scene = new Scene(root, 850,600);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest( event -> {
            save();
            System.exit(1);
                }
        );
    }

    /**
     * This command does checking.
     */
    private static void checking() {
        if (pane10.getChildren().contains(indexField)) pane10.getChildren().remove(indexField);
        if (pane10.getChildren().contains(enter)) pane10.getChildren().remove(enter);
        if (pane10.getChildren().contains(closeInteractive)) pane10.getChildren().remove(closeInteractive);
        if (pane10.getChildren().contains(add)) pane10.getChildren().remove(add);
        if (pane10.getChildren().contains(change)) pane10.getChildren().remove(change);
    }

    /**
     * This command removes need element from the Collection.
     */
    private static void index(TextField indexField){
        try {
            System.out.println(indexField.getText());
            Integer integer = new Integer(indexField.getText());
            remove(integer);
            indexField.setText("");
        } catch (NumberFormatException e) {
            if (indexField.getText().equals("")) System.out.println("You didn't write anything");
            else            System.out.println("Sorry, but you wrote a nonsense. Try again!");
            indexField.setText("");
        }
    }

    /**
     * This command works with pictures.
     */
    private static void imagination(ImageView a, ImageView b, ImageView c, ImageView d) {
        image.getChildren().removeAll(b, c, d);
        root.getChildren().remove(image);
        pane20.getChildren().remove(close);
        image.getChildren().add(a);
        root.add(image, 0, 1);
        pane20.getChildren().add(close);
    }

    /**
     * This command rewrites the Collection.
     */
    private static void rewriting() {
        pane11.getChildren().remove(tvPerson);
        TreeItem<String> tiRoot = new TreeItem<>("Persons");
        for (int i=0; i<vector.size(); i++) {
            TreeItem<String> tiPerson = new TreeItem<> ("Person " + (i+1));
            tiRoot.getChildren().add(tiPerson);
            TreeItem<String> tiName = new TreeItem<> ("Name");
            tiPerson.getChildren().add(tiName);
            tiName.getChildren().add(new TreeItem<>(vector.get(i).getName()));
            if (!vector.get(i).getPhrases().isEmpty()) {
                TreeItem<String> tiPhrases = new TreeItem<>("Phrases");
                tiPerson.getChildren().add(tiPhrases);
                for(int j=0; j<vector.get(i).getPhrases().size(); j++) {
                    TreeItem<String> tiPhrase = new TreeItem<>("Phrase " + (j+1));
                    tiPhrases.getChildren().add(tiPhrase);
                    if(vector.get(i).getPhrases().get(j).getPhrase().isEmpty()) {
                        tiPhrase.getChildren().add(new TreeItem<>("I was silent!"));
                    } else
                    tiPhrase.getChildren().add(new TreeItem<>(vector.get(i).getPhrases().get(j).getPhrase()));
                }
            }
        }
        tvPerson = new TreeView<>(tiRoot);
        pane11.getChildren().add(tvPerson);
    }

    /**
     * This command does exit from programme.
     */
    private static void exit() {
        System.out.println("Bye!");
        System.exit(0);
    }

    /**
     * This command finds the minimum from the Collection.
     */
    private static void getMin() {
        if (!vector.isEmpty()) {
            min = vector.get(0);
            for (Person person : vector) {
                if (min.compareTo(person) > 0) min = person;
            }
            System.out.println("min = " + min);
        } else {
            System.out.println("No elements in Collection");
            min = null;
            System.out.println("min = null");
        }
    }

    /**
     * This command removes the last element from the Collection.
     */
    private static void remove_last() {
        if (!vector.isEmpty()) {
            vector.remove(vector.lastElement());
            System.out.println("Everything is good! :) (remove_last)");
        } else System.out.println("Vector is empty");
    }

    /**
     * This command removes the element from the Collection, which have this index.
     */
    private static void remove(int index) {
        if (vector.isEmpty()) {
            System.out.println("Vector is empty");
        } else {
            if (index > vector.size()) {
                System.out.println("Index more than elements in Collection");
            } else {
                if (index < 0) {
                    System.out.println("Index less than elements in Collection");
                } else {
                    vector.remove(index-1);
                    getMin();
                    rewriting();
                    System.out.println("Everything is good! :) (remove)");
                }
            }
        }
    }


    /**
     * This command reads all data from the file into Collection.
     */
    private static void load() {
        vector.removeAllElements();
        InputStreamReader in = null;
        int c;
        String str = "";
        String[] strs;
        try {in = new InputStreamReader(new FileInputStream("../../Proba.csv"));
            while ((c = in.read()) != -1) {
                if (c == '\n' || c == '\r' ) {
                    if(!str.trim().equals("")) {
                        Person person = new Person();
                        strs = str.split(";");
                        m(strs, person);
                        str = "";
                    }
                }
                else {
                    str = str + (char) c;
                }
            }
            if(!str.trim().equals("")) {
                Person person = new Person();
                strs = str.split(";");
                m(strs, person);
            }
        } catch (IOException ioe) {
            System.out.println("Error with path to the file (input)");
            System.exit(1);
        }  finally {
            try {
                in.close();
            } catch (IOException e) {
                System.out.println("Error with close in 1");
            } catch (NullPointerException e) {
                System.out.println("Error with close in 2");
            }
        }
        if (!vector.isEmpty())
            System.out.println("Everything is good! :) (load)");
    }
    private static void m(String [] strs, Person person) {
        if (strs.length != 0) {
            if (!strs[0].trim().equals("")) {
                person.setName(strs[0].trim());
                if (strs.length - 1 != 0) {
                    for (int j = 1; j < strs.length; j++) {
                        person.addPhrase(new Phrase(strs[j].trim()));
                    }
                }
                vector.add(person);
            } else {
                System.out.println("Sorry, but person must have a name");
            }
        }
    }

    /**
     * This command saves all elements in file.
     */
    private static void save(){
        String str = "";
        FileWriter out = null;
        try {
            out = new FileWriter("../../Proba.csv", false);
            for (Person person:vector) {
                for (int n = 0; n < person.getPhrases().size(); n++) {
                    str = str.concat(";" + person.getPhrase(n));
                }
                str = person.getName().concat(str + '\r' + '\n');
                out.write(str);
                str = "";
            }
        } catch (IOException e) {
            System.out.println("Error with access to the file");
        } finally {
            try {
                out.close();
            } catch (IOException ioe) {
                System.out.println("Error with close out");
            } catch (NullPointerException e) {
                System.out.println("Error with path to the file (output)");
            }
        }
    }

    /**
     * This command adds the element into Collection if this element less than min element of this Collection.
     */
    private static void add_if_min(TextField element) {
        try {Gson gson = new Gson();
            Person person = gson.fromJson(element.getText(), Person.class);
            if (!person.getName().equals("")) {
                System.out.println(person.toString());
                if (min.compareTo(person) > 0) {
                    min = person;
                    vector.addElement(person);
                    rewriting();
                    element.setText("");
                    System.out.println("Everything is good! :) (add_if_min)");
                    System.out.println("min = " + min.getName());
                } else {
                    System.out.println("Element is more than min");
                    element.setText("");
                }
            } else {
                System.out.println("Sorry, but person must have a name");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Sorry, it's not JSON");
            if(!"".equals(element.getText())) {
                Person person = new Person(element.getText());
                if (min.compareTo(person) > 0) {
                    min = person;
                    vector.addElement(person);
                    rewriting();
                    pane10.getChildren().remove(element);
                    System.out.println("Everything is good! :) (add_if_min)");
                    System.out.println("min = " + min.getName());
                    phrase = new TextField();
                    phrase.relocate(220,230);
                    phrase.setPrefColumnCount(16);
                    phrase.setPromptText("Enter phrase " + (i+1));
                    pane10.getChildren().add(phrase);
                    phrase.setOnAction(event -> {
                        vector.get(vector.size()).setPhrase(i,new Phrase(phrase.getText()));
                        i++;
                        phrase.setText("");
                        rewriting();
                    });
                } else {
                    System.out.println("Element is more than min");
                    element.setText("");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Sorry, null cannot be here");
            element.setText("");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static void outVector() {
        for (int i = 0; i < vector.size(); i++) {
            System.out.println(i + " " + vector.get(i));
        }
    }

}

//C:\Users\Elizabeth\Desktop\ИТМО\Программирование\Лабораторные\Лаба05\Proba.csv