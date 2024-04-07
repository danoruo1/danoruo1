import java.util.*;

public class Location {
    // State of the location object
    String name;
    String description;

    // The arrays could be ArrayLists for ease of use - look them up and learn how
    // you can use them.
    Item[] itemsHere = new Item[4]; // to hold all of the items in this location.
    Person[] npcs = new Person[4]; // holds all people in this location
    int totalExits = 1;
    int currentExit = 1;
    boolean nearEnding = true;
    boolean shopPurchase = false;
    boolean roomFound = false;
    boolean alternateExit = false;
    boolean exitFound = false;
    boolean doorFound = false;
    boolean canUsePhone = true;
    boolean quest = false; // If there is something the player must do
    boolean questComplete = false;
    boolean nogoingback = false;
    Item invisItemz = new Item();
    Person nullPerson = new Person();


    public Location() {
        for (int i=0;i<4;i++){ 
            invisItemz.name = "Invisible";
            invisItemz.description = "";
            this.itemsHere[i] = invisItemz;
        }

        for (int i=0;i<4;i++){ 
            nullPerson.name = "null";
            nullPerson.description = "";
            this.npcs[i] = nullPerson;
        }
    };


    // Checks if the item exists in the location
    
    public boolean checkItem(String objectName){
        boolean found = false;
        int num = 0;
        for (int i=0;i<4;i++){ 
            if (itemsHere[i]!= null){
                if (objectName.equalsIgnoreCase(itemsHere[i].name)){
                    found = true;
                    num = i;
                    break;
                }
            }
        }

        return found;
    }
    // Gets the item
    public int getItem(String objectName) {
        int num = 0;
        for (int i=0;i<4;i++){
            if (objectName.equalsIgnoreCase(itemsHere[i].name)){
                num = i;
                break;
            }
        }
        return num; 
    }


    // Checks if npc is in the room
    public boolean checkNPC(String objectName){
        boolean found = false;
        int num = 0;
        for (int i=0;i<4;i++){ 
            if (npcs[i]!= null){
                if (objectName.equalsIgnoreCase(npcs[i].name) && (npcs[i]!=null)){
                    found = true;
                    num = i;
                    break;
                }
            }
        }
        if (found = true){
            return true;
        }
        else
        {
            return false;
        }
    }
    // Gets the location in the array of the NPC
    public int getNPC(String objectName) {
        int num = 0;
        for (int i=0;i<4;i++){
            if (objectName.equalsIgnoreCase(npcs[i].name)){
                num = i;
                break;
            }
        }
        return num; 
    }

    public void clearNPCS(){
        for (int i=0;i<4;i++){ 
            nullPerson.name = "";
            nullPerson.description = "";
            this.npcs[i] = nullPerson;
        }
    }

}
