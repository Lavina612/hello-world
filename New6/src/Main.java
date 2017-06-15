import java.io.*;
import java.time.LocalDate;
import java.util.Vector;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import javafx.application.Application;
import javafx.beans.value.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @author Elizabeth, P3111 :)
 */

public class Main extends Application{
    private static Vector<Person> vector = new Vector();
    private static Person min = null;
    private static int bug = 0;
    private static Label response;
    public void start(Stage stage) {
        stage.setTitle("Laba06");
        GridPane root = new GridPane();
        HBox hbox00 = new HBox(10);
        FlowPane pane01 = new FlowPane(10,10);
        FlowPane pane10 = new FlowPane(10,10);
        FlowPane pane11 = new FlowPane(10,10);
        FlowPane pane20 = new FlowPane(10,10);
        FlowPane pane21 = new FlowPane(10,10);
        pane01.setAlignment(Pos.CENTER);
        pane21.setAlignment(Pos.CENTER);
        pane11.setAlignment(Pos.TOP_CENTER);
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(600);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(250);
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(70);
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(500);
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(70);
        root.getColumnConstraints().addAll(col1, col2);
        root.getRowConstraints().addAll(row1, row2, row3);
        root.setGridLinesVisible(true);
        ProgressBar bar = new ProgressBar();
        DatePicker datePick = new DatePicker();
        datePick.setValue(LocalDate.of(2017,6,14));
        datePick.setShowWeekNumbers(true);
        ProgressBar prBar = new ProgressBar();
        Button exit = new Button("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                exit();
            }
        });
        load();
        rewriting(pane11);
        hbox00.getChildren().addAll();
        pane01.getChildren().addAll(bar);
        pane10.getChildren().addAll(datePick);
        pane11.getChildren().addAll();
        pane20.getChildren().addAll();
        pane21.getChildren().addAll(exit);
        root.add(hbox00, 0,0);
        root.add(pane01, 1, 0);
        root.add(pane10, 0, 1);
        root.add(pane11, 1, 1);
        root.add(pane20, 0, 2);
        root.add(pane21, 1, 2);
        Scene scene = new Scene(root, 850,640);
        stage.setScene(scene);
        stage.show();
    }
    private static void rewriting(FlowPane pane11) {
        response = new Label("No selection");
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
                        tiPhrase.getChildren().add(new TreeItem<String>("I was silent!"));
                    } else
                    tiPhrase.getChildren().add(new TreeItem<String>(vector.get(i).getPhrases().get(j).getPhrase()));
                }
            }
        }
        TreeView<String> tvPerson = new TreeView<>(tiRoot);
 /*       MultipleSelectionModel<TreeItem<String>> tvSelModel = tvPerson.getSelectionModel();
        tvSelModel.selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            public void changed(ObservableValue<? extends TreeItem<String>> changed, TreeItem<String> oldVal, TreeItem<String> newVal) {
                if (newVal != null) {
                    String path = newVal.getValue();
                    TreeItem<String> tmp = newVal.getParent();
                    while (tmp != null) {
                        path = tmp.getValue() + " -> " + path;
                        tmp = tmp.getParent();
                    }
                    response.setText("Selection is " + newVal.getValue() + "\n\nComplete path: \n" + path);
                }
            }
        });  */
        pane11.getChildren().addAll(tvPerson, response);
    }
    private static void exit() {
        System.out.println("Bye!");
        System.exit(1);
    }
    private static void outVector() {
        for (int i = 0; i < vector.size(); i++) {
            System.out.println(i + " " + vector.get(i));
        }
    }
    private static void getMin() {
        if (bug == 1) {
            if (vector.size() != 0) {
                min = vector.get(0);
                for (int k = 1; k < vector.size(); k++) {
                    if (min.compareTo(vector.get(k)) > 0) min = vector.get(k);
                }
                System.out.println("min = " + min.getName());
                outVector();
            } else {
                min = null;
                System.out.println("min = null");
            }
        }
        if (bug == 2) {
            System.out.println("No elements in Collection");
            min = null;
            System.out.println("min = null");
        }
            bug = 0;
    }

    /**
     * This command removes the last element from the Collection.
     */
    private static void remove_last() {
        if (vector.size() != 0) {
            vector.remove(vector.lastElement());
            bug = 1;
            System.out.println("Everything is good! :) (remove_last)");
        } else bug = 2;
    }

    /**
     * This command removes the element from the Collection, which have this index.
     */
    private static void remove(int index) {
        if (vector.size() == 0) bug = 2;
        else {
            if (index >= vector.size()) {
                bug = 0;
                System.out.println("Index more than elements in Collection");
            }
            else {
                if (index < 0) {
                    bug = 0;
                    System.out.println("Index less than elements in Collection");
                } else {
                    vector.remove(index);
                    bug = 1;
                    System.out.println("Everything is good! :) (remove)");
                }
            }
        }
    }

    /**
     * This command reads all data from the file into Collection.
     */
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
                System.out.println("Sorry, but person mush have a name");
            }
        }
    }
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
                System.out.println("Error with close in");
            } catch (NullPointerException e) {
                System.out.println("Error with close in");
            }
        }
        if (vector.size() != 0) {
            bug = 1;
            System.out.println("Everything is good! :) (load)");
        } else bug = 2;
    }

    /**
     * This command adds the element into Collection if this element less than min element of this Collection.
     */
    private static void add_if_min(String element) {
        try {
            Gson gson = new Gson();
            Person person = gson.fromJson(element, Person.class);
            if (!person.getName().equals("")) {
                System.out.println(person.toString());
                if (min.compareTo(person) >= 0) {
                    min = person;
                    vector.addElement(person);
                    bug = 1;
                    System.out.println("Everything is good! :) (add_if_min)");
                    System.out.println("min = " + min.getName());
                    outVector();
                } else System.out.println("Element is more than min");
            } else {
                System.out.println("Sorry, but person must have a name");
            }
        } catch (JsonSyntaxException e) {
            System.out.println("Sorry, it's not JSON");
        } catch (NullPointerException e) {
            System.out.println("Sorry, null cannot be here");
        }
        bug = 0;
    }

    public static void main(String[] args) {
        launch(args);

        String s;
        Scanner scanner = new Scanner(System.in);
        load();
        getMin();
        Runtime.getRuntime().addShutdownHook(new Thread() {
                                                 public void run() {
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
                                             }
        );
        System.out.println("'help' helps you :)");
        while (scanner.hasNextLine() && !(s = scanner.nextLine()).trim().equals("exit")) {
            s = s.trim();
            char[] chars1 = s.toCharArray();
            int counter = 0;
            String s1;
            String s2;
            if (s.equals("help")) {
                System.out.println("remove_last");
                System.out.println("load");
                System.out.println("remove {index}");
                System.out.println("add_if_min {element in json}");
                System.out.println("exit");
            } else {
                if (s.equals("load")) {
                    load();
                    getMin();
                } else {
                    if (s.equals("remove_last")) {
                        remove_last();
                        getMin();
                    } else {
                        for (char ch: chars1) {
                            if (ch == ' ') counter = 1;
                        }
                        if (counter == 1) {
                            s1 = s.substring(0, s.indexOf(' ')).trim();
                            s2 = s.substring(s.indexOf(' ') + 1, s.length()).trim();
                            if (s1.equals("remove")) {
                                try {
                                    Integer integer = new Integer(s2);
                                    remove(integer);
                                    getMin();
                                } catch (NumberFormatException e) {
                                    System.out.println("Sorry, but you wrote a nonsense. Try again!");
                                }
                            } else {
                                if (s1.equals("add_if_min")) add_if_min(s2);
                                 else System.out.println(":( This command wasn't found. Try again!");
                            }
                        } else System.out.println(":( This command not found. Try again!");
                    }
                }
            }
        }
        System.out.println("Come back! :)");
        outVector();
    }
}



//C:\Users\Elizabeth\Desktop\ИТМО\Программирование\Лабораторные\Лаба05\Proba.csv