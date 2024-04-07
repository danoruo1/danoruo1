import java.util.*;
public class Person {
    String name;
    String description;
    String dialogue;
    int dialouguelines;
    int currentline;
    boolean inText = false;
    public Person(){
        currentline = 1;

    }

    public Person(String n, String descr, String d, int lines) {
        this.name = n;
        this.description = descr;
        this.dialogue = d;
        this.dialouguelines = lines;
    }

    public String Talk() {
        return dialogue;
    }
}