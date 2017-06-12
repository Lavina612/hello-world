import java.io.*;
import java.util.Vector;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Elizabeth, P3111 :)
 */

public class Main {
    private static Vector<Person> vector = new Vector();
    private static String file = "";
    private static Person min = null;
    private static int bug = 0;
    Main [] m = new Main [3];
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
        try {
            File f = new File(file);
            System.out.println(f.getAbsolutePath());
            in = new InputStreamReader(new FileInputStream(f));
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
        if (args.length != 1) {
            System.out.println("Incorrect amount of arguments");
            System.exit(404);
        }
        String s;
        Scanner scanner = new Scanner(System.in);
        file = args[0];
        load();
        getMin();
        Runtime.getRuntime().addShutdownHook(new Thread() {
                                                 public void run() {
                                                     String str = "";
                                                     FileWriter out = null;
                                                     try {
                                                         out = new FileWriter("../Proba.csv", false);
                                                         for (Person person:vector) {
                                                             for (int n = 0; n < person.phrases.size(); n++) {
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