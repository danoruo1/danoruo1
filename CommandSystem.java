/*
CommandSystem.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This class is the primary logic class for the system. It defines what commands are valid, 
and what happens when those commands are executed.  
*/

import java.util.*;

public class CommandSystem {
    private GameState state;

    // ArrayList to store all of the verbs that the game knows about. (This is a
    // parallel array with the verbDescription arraylist)
    private List<String> verbs = new ArrayList<String>();

    // ArrayList of the descriptions for the verbs the game knows. (This is a
    // parallel array with the verbs arraylist)
    private List<String> verbDescription = new ArrayList<String>();

    // ArratList to store all of the nouns the game knows about.
    private List<String> nouns = new ArrayList<String>();

    /*
     * Constructor should defines all verbs that can be used in the commands and all
     * nouns the user can interact with.
     * 
     * Suggestion: These could all be loaded from a file.
     * 
     * Verb descriptions are stored in a parallel Arraylist with the Verbs and are
     * used when printing out the help menu (using the ? command).
     */
    public CommandSystem(GameState state) {
        this.state = state;
        // Assign verbs and descriptions here
        addVerb("?", "Show this help screen.");
        addVerb("look",
                "Use the look command by itself to look in your current area. \nYou can also look at a person or object by ntyping look and the name of what you want to look at.\nExample: look book");
        addVerb("l", "Same as the look command.");
        addVerb("quit", "Quit the game."); 
        addVerb("goto", "moves onto the next location if you find the door (once the door is found say goto door)"); 
        addVerb("use", "Uses an Item"); 
        addVerb("view", "Views The text message as long as you put phone and the name of the person you are recieveing the text from");
        addVerb("next", "Goes onto the next line of text/Dialogue");
        addVerb("talkto", "Like messaging but this time it isnt over the phone");
        addVerb("purchase", "When you have your item use this command");
    }

    // When a command is only one Verb this method controls the result.
    public void executeVerb(String verb) {
        verb.toLowerCase();
        switch (verb) {
            case "l":
            case "look": // will show the description of the current room (stored in the state object)
                gameOutput("You look around.");
                gameOutput("You are in " + state.currentLocation.description);
                break;
            case "":
                break;
            case "?":
                this.printHelp();
                break;
            case "goto":
                gameOutput("Missing the door");
                break;
            case "use":
                gameOutput("Gotta use an item");
                break;  
            case "next":
                gameOutput("Used for text messages.");
                break;
            case "talkto":
                gameOutput("Need to talk to an npc");
                break;
        }
    }

    // When a command is a Verb followed by a noun, this method controls the result.
    public void executeVerbNoun(String verb, String noun) {
        int num = 0;

        /**
         * Initilize the string that we will use as a response text.
         * This method allows us to create a single string and just print it at the end
         * of the method.
         * You can do it any way you wish.
         */
        String resultString = "";

        switch (verb) { // Deciddes what to do based on each verb
            case "l":
            case "look":
                resultString = lookAt(noun);
                break;
            case "goto":
                if (noun.equalsIgnoreCase("door")){
                    if (state.currentLocation.exitFound == true){
                        String currentPlace = state.currentLocation.name;
                        nextLocation(currentPlace);
                    }
                    else{
                        System.out.println("You haven't found the exit");
                    }
                }
                break;
            case "use":
                useItem(noun);
                break;

            case "talkto":
                if (state.currentLocation.checkNPC(noun) == true){
                    num = state.currentLocation.getNPC(noun);
                    if (state.currentLocation.npcs[num].inText == false){
                        gameOutput(state.currentLocation.npcs[num].Talk());
                        if (state.currentLocation.npcs[num].currentline < state.currentLocation.npcs[num].dialouguelines){
                            nextDialouge(noun);
                            state.currentLocation.npcs[num].currentline += 1;
                        }
                    }
                    else{
                        gameOutput("Wrong Command Buddy (Hint: use a verb two nouns the last nound being the entity and the second noun being message)");
                    }   
                }
                break;
            case "purchase":
                if (state.currentLocation.checkNPC(noun) == true){
                    gameOutput(state.currentLocation.npcs[num].Talk());
                }
                break;
        }

        gameOutput(resultString);
    }

    // When a command is a Verb followed by two nouns, this method controls the
    // result.
    public void executeVerbNounNoun(String verb, String noun, String noun2) { // Noun2 is the entity, Noun is the object
        verb = verb.toLowerCase();
        noun = noun.toLowerCase();
        noun2 = noun2.toLowerCase();
        int num = 0;
        String resultString = "";
        switch(verb){ 
            case "view": 
                if (noun.equalsIgnoreCase("message")){ // For viewing text message 
                    if (state.currentLocation.checkNPC(noun2) == true){
                        num = state.currentLocation.getNPC(noun2);
                        resultString += state.currentLocation.npcs[num].Talk();
                        state.currentLocation.npcs[num].currentline += 1;
                    }
                }
                break;
            case "next":
                if (noun.equalsIgnoreCase("message")){ // For viewing text message 
                    if (state.currentLocation.checkNPC(noun2) == true){
                        num = state.currentLocation.getNPC(noun2);
                        nextDialouge(noun2);
                        if (state.currentLocation.npcs[num].currentline <= state.currentLocation.npcs[num].dialouguelines &&  state.currentLocation.npcs[num].currentline > 1)  {
                            resultString += state.currentLocation.npcs[num].Talk();
                            state.currentLocation.npcs[num].currentline += 1;
                        }
                        else{
                            gameOutput("Use view instead");
                        }
                    }
                }
            break;
        }
        gameOutput(resultString);


    }

    // Method to take care of looking at a noun.
    public String lookAt(String noun) {
        int arraynumm = 0;
        String resultString = "";
        switch (noun) { // for the given verb, decide what to do based on what noun was entered
            case "phone":
                if (state.currentLocation.canUsePhone = true){
                    if ((state.currentLocation.checkItem(noun)) == true){ 
                        arraynumm = state.currentLocation.getItem(noun);
                        if (state.currentLocation.itemsHere[arraynumm].isFound){
                            resultString += state.currentLocation.itemsHere[arraynumm].description;  
                        }   
                    }
                    else{
                        gameOutput("You cannot use " + noun + " here try a different place next time");
                    }         
                }
                else{
                    gameOutput("You cannot use " + noun + " here try a different place next time");
                }
                break;
            case "bedroom":
                resultString += "On the far side of the room, there is a door";  
                state.currentLocation.doorFound = true; // Door location for bedroom
                state.currentLocation.exitFound = true;
                break;
            case "livingroom":
                resultString += "On the far side of the room, there is the door to the bedroom and a door to outside (if you wanna go back to the previous room say leave (roomname))";  
                state.currentLocation.doorFound = true; 
                state.currentLocation.exitFound = true;
                state.bedroom.roomFound = true;
                break;
            case "door":
                if (state.currentLocation.doorFound == true){
                    resultString += "This door looks very interesting, I'd be stupid not to LEAVE this room using This Door";  
                    state.currentLocation.exitFound = true;
                }
                else{
                    resultString += "Gotta look harder for the door";
                }
                break;
            case "keys":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        resultString += state.currentLocation.itemsHere[arraynumm].description;  
                    }
                }
                break;
            case "table":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    state.currentLocation.itemsHere[2].isFound = true;
                    resultString += state.currentLocation.itemsHere[arraynumm].description; 
                }
                break;
            case "gps":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        resultString += state.currentLocation.itemsHere[arraynumm].description;  
                    }
                    else{
                        resultString += "Havent found it yet";  

                    }
                }
                break;

            case "drawer1":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    resultString += state.currentLocation.itemsHere[arraynumm].description; 
                }
                break;

            case "drawer2":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    resultString += state.currentLocation.itemsHere[arraynumm].description; 
                }
                break;

            case "drawer3":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    state.currentLocation.itemsHere[3].canLook = true;
                    resultString += state.currentLocation.itemsHere[arraynumm].description; 
                }
                break;
            
            case "painkillers":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    state.currentLocation.itemsHere[arraynumm].isFound = true;
                    if (state.currentLocation.itemsHere[arraynumm].canLook){
                        resultString += state.currentLocation.itemsHere[arraynumm].description; 
                    }
                }
                break;

            case "eddie":
                if (state.currentLocation.checkNPC(noun) == true){ 
                    resultString += state.eddieNPC.description;
                }
                break;


            case "policechief":
                if (state.currentLocation.checkNPC(noun) == true){ 
                    resultString += state.policeChiefText.description;
                }
                break;

            case "desk":
                if (state.currentLocation.checkItem(noun) == true){ 
                    state.currentLocation.itemsHere[2].isFound = true;
                }
                // Purposely did this
            default:
                if (state.currentLocation.checkNPC(noun)){
                    arraynumm = state.currentLocation.getNPC(noun);
                    if (noun.equalsIgnoreCase(state.currentLocation.npcs[arraynumm].name)){
                        resultString += state.currentLocation.npcs[arraynumm].description;
                    }
                }
                if (state.currentLocation.checkItem(noun)){
                    arraynumm = state.currentLocation.getItem(noun);
                    if (noun.equalsIgnoreCase(state.currentLocation.itemsHere[arraynumm].name)){
                        resultString += state.currentLocation.itemsHere[arraynumm].description;
                    }
                }
        }
        return resultString;
    }


    public void useItem(String noun) {
        int arraynumm = 0;
        noun.toLowerCase();
        // This will be what is returned by the method.
        switch (noun) { // for the given verb, decide what to do based on what noun was entered
            case "keys":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        System.out.print("When you leave this place you will enter the car you can now use the look door command");
                        state.currentLocation.alternateExit = false;
                        state.currentLocation.doorFound = true;
                        state.currentLocation.questComplete = true;
                     }
                }
                break;
            case "gps":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        System.out.print("When you leave this place you walk to CVS you can now use the look door command");
                        state.currentLocation.alternateExit = true;
                        state.currentLocation.doorFound = true;
                        state.currentLocation.questComplete = true;
                     }
                }
                break;
            case "painkillers":
                if (state.currentLocation.checkItem(noun) == true){ 
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        gameOutput("You: THATS THAT GOOD STUFF, I FEEL REJUVENATED!");
                    }
                }
                break;

            case "securitycam":
                if (state.currentLocation.checkItem(noun) == true){
                    arraynumm = state.currentLocation.getItem(noun);
                    if (state.currentLocation.itemsHere[arraynumm].isFound){
                        gameOutput("You: Why Would The Detective Try To Destroy Something like this Let Alone Not Mention It to any of us");
                        state.wait(2);
                        gameOutput("Views Footage*");
                        state.wait(2);
                        gameOutput("WAIT WHAT IMPOSSIBLE");
                        state.wait(2);
                        gameOutput("PoliceChief: You saw too much *BANG* you dead x_x");
                        App.ending1();
                    }

                } 
                break;


            
        }
    }

    /*****************************************************************
     * Helper methods to help system work.
     ******************************************************************/

    /*
     * Prints out the help menu. Goes through all verbs and verbDescriptions
     * printing a list of all commands the user can use.
     */
    public void printHelp() {
        int DISPLAY_WIDTH = GameState.DISPLAY_WIDTH;
        String s1 = "";
        while (s1.length() < DISPLAY_WIDTH)
            s1 += "-";

        String s2 = "";
        while (s2.length() < DISPLAY_WIDTH) {
            if (s2.length() == (DISPLAY_WIDTH / 2 - 10)) {
                s2 += " Commands ";
            } else {
                s2 += " ";
            }
        }

        System.out.println("\n\n" + s1 + "\n" + s2 + "\n" + s1 + "\n");
        for (String v : verbs) {
            // System.out.printp(v + " --> " + verbDescription.get(verbs.indexOf(v)));
            System.out.printf("%-8s  %s", v, formatMenuString(verbDescription.get(verbs.indexOf(v))));
        }
    }

    // Used to format the help menu
    public String formatMenuString(String longString) {
        String result = "";
        Scanner chop = new Scanner(longString);
        int charLength = 0;

        while (chop.hasNext()) {
            String next = chop.next();
            charLength += next.length();
            result += next + " ";
            if (charLength >= (GameState.DISPLAY_WIDTH - 30)) {
                result += "\n          ";
                charLength = 0;
            }
        }
        chop.close();
        return result + "\n\n";
    }

    /**
     * Default game output.
     * This is an alias for the other gameOutput method and defaults to
     * doing both the bracketing and the width formatting.
     **/
    public void gameOutput(String longstring) {
        gameOutput(longstring, true, true);
    }

    public void gameOutput(String longstring, boolean addBrackets, boolean formatWidth) {
        if (addBrackets) {
            longstring = addBrackets(longstring);
        }
        if (formatWidth) {
            longstring = formatWidth(longstring);
        }

        System.out.println(longstring);
    }

    // formats a string to DISPLAY_WIDTH character width.
    // Used when getting descriptions from items/locations and printing them to the
    // screen.
    // You can also add [nl] for a newline in a string in a description etc.
    public String formatWidth(String longString) {

        Scanner chop = new Scanner(longString);
        String result = "";
        int charLength = 0;
        boolean addSpace = true;

        while (chop.hasNext()) {

            // Get our next word in the string.
            String next = chop.next();

            // Add the legnth to our charLength.
            charLength += next.length() + 1;

            // Find and replace any special newline characters [nl] with \n.
            if (next.contains("[nl]")) {
                // Find the index after our [nl] characters.
                int secondHalf = next.indexOf("[nl]") + 4;

                // Set charLength to the number of characters after the [nl],
                // because that will be the beginnig of a new line.
                if (secondHalf < next.length()) {
                    charLength = secondHalf;
                } else {
                    charLength = 0;
                    addSpace = false; // Do not add space after if this ended with a newline character.
                }

                // Now actually replace the [nl] with the newline character
                next = next.replace("[nl]", "\n");

            }

            // Add the word to the result.
            result += next;

            // Only add a space if our special case did not happen.
            if (addSpace)
                result += " ";

            // Normally we add a space after a word, prepare for that.
            addSpace = true;

            if (charLength >= GameState.DISPLAY_WIDTH) {
                result += "\n";
                charLength = 0;
            }
        }
        chop.close();
        return result;
    }

    /**
     * Adds brackets around whole words that are included in the `nouns` list,
     * ignoring case, and also deals with any that have punctuation after them.
     *
     * @param longString the string to check for nouns
     * @return the modified string with brackets around the nouns
     */
    public String addBrackets(String longString) {
        String[] words = longString.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            String word = words[i].replaceAll("\\p{Punct}+$", "");
            String punct = words[i].substring(word.length());
            for (String noun : nouns) {
                if (word.equalsIgnoreCase(noun)) {
                    words[i] = "[" + word + "]" + punct;
                    break;
                }
            }
        }
        return String.join(" ", words);
    }

    // Adds a noun to the noun list
    // lets the command system know this is something you an interact with.
    public void addNoun(String string) {
        if (!nouns.contains(string.toLowerCase()))
            nouns.add(string.toLowerCase());
    }

    // Adds a verb to the verb list and its description to the parallel description
    // list.
    // This method should be used to register new commands with the command system.
    public void addVerb(String verb, String description) {
        verbs.add(verb.toLowerCase());
        verbDescription.add(description.toLowerCase());
    }

    // Allows the client code to check to see if a verb is in the game.
    public boolean hasVerb(String string) {
        return verbs.contains(string);
    }

    // Allows the client code to check to see if a noun is in the game.
    public boolean hasNoun(String string) {
        return nouns.contains(string);
    }

    // Changes The Location to the next one
    public void nextLocation(String name){
       int arraynumm = 0;
       name = name.toLowerCase();
       switch (name){
        case "bedroom":
            if (state.currentLocation.quest == true){
                if (state.currentLocation.questComplete){
                    state.currentLocation = state.livingroom;
                    gameOutput(state.currentLocation.description);
                    for (int i=0;i<1;i++){
                        state.currentLocation.itemsHere[i].isFound = true;
                    }
                }
                else{
                    gameOutput("You havent finished viewing the text message from the chief");
                }
            }
            break;
        case "livingroom":
            if (state.currentLocation.questComplete){
                if (state.currentLocation.alternateExit == true) {
                    if (state.currentLocation.checkItem("GPS") == true){ 
                        arraynumm = state.currentLocation.getItem("GPS");
                        if (state.currentLocation.itemsHere[arraynumm].isFound){
                            state.currentLocation = state.cvs;
                            gameOutput(state.currentLocation.description);
                        }
                        else{
                            gameOutput("You cant leave without knowing where you are going. USING an item would help.");
                        }
                    }
                    // Change location to CVS
    
                }
                else{
                    if (state.currentLocation.checkItem("Keys") == true){ 
                        arraynumm = state.currentLocation.getItem("Keys");
                        if (state.currentLocation.itemsHere[arraynumm].isFound){
                            state.currentLocation = state.policestation;
                            gameOutput(state.currentLocation.description);
                        }
                        else{
                            gameOutput("You cant leave without knowing where you are going. USING an item would help.");
                        }
                    }
                    // Change location to policestation
                }
            }
            else{
                gameOutput("You cant leave without knowing where you are going. USING an item would help.");
            }
            break;

        case "cvs":
            // Setting up one of the endings \\
            Item newDeskItem = new Item(state.desk.name, "There was a lock but its broken now, and there is what seems to be a SecurityCam");
            newDeskItem.isFound = true;
            state.chiefoffice.clearNPCS();
            state.chiefoffice.nearEnding = true;
            state.chiefoffice.description = "At the chiefs office, there are files on the desk. The door to the investigation room is in the corner of the room";
            state.chiefoffice.itemsHere[0] = newDeskItem;
            state.chiefoffice.itemsHere[1] = state.files;
            state.chiefoffice.itemsHere[2] = state.securityCam;
            state.currentLocation = state.policestation;
            gameOutput(state.currentLocation.description);
            break;  
        case "policestation":
            state.currentLocation = state.chiefoffice;
            gameOutput(state.currentLocation.description);
            break;
       }
    }
    

        public void nextDialouge(String noun) {
            int numlocation = 0;
            noun.toLowerCase();
            switch(noun){
                case "policechief":
                    if (state.currentLocation.checkNPC(noun))
                    {
                        if (state.currentLocation.name.equalsIgnoreCase("bedroom")){
                            numlocation = state.currentLocation.getNPC(noun);
                            if (state.currentLocation.npcs[numlocation].currentline <= state.currentLocation.npcs[numlocation].dialouguelines ){
                                if (state.currentLocation.npcs[numlocation].currentline == 2){
                                    state.currentLocation.npcs[numlocation].dialogue = "Jim Downings body was found a few streets down from the police station";
                                }
                                else{
                                    if (state.currentLocation.npcs[numlocation].currentline == 3){
                                        state.currentLocation.npcs[numlocation].dialogue = "I need you at the station so we can investigate the 3 suspects. See you soon.";
                                        state.currentLocation.itemsHere[0].description = "No New Messages";
                                        state.phone.description = "No New Messages";
                                        state.currentLocation.questComplete = true;
                                        state.bedroom.questComplete = true;
                                    }
                                }
                            }
                        }  

                        else if (state.currentLocation.name.equalsIgnoreCase("chiefoffice")){
                            numlocation = state.currentLocation.getNPC(noun);
                            if (state.currentLocation.npcs[numlocation].currentline <= state.currentLocation.npcs[numlocation].dialouguelines ){
                                if (state.currentLocation.npcs[numlocation].currentline == 1){
                                    state.currentLocation.npcs[numlocation].dialogue = "Go and Investigate the 3 suspects and conducted further investigation if needed";
                                    state.currentLocation.description = "At the chiefs office, aside the desk. The door to the investigation room is in the corner of the room";
                                    state.currentLocation.exitFound = true;
                                    state.currentLocation.doorFound = true;
                                }
                            }


                        }

                    }
                    break;

                case "eddie":
                    if (state.currentLocation.checkNPC(noun))
                        {
                        numlocation = state.currentLocation.getNPC(noun);
                        if (state.currentLocation.npcs[numlocation].currentline <= state.currentLocation.npcs[numlocation].dialouguelines ){
                            if (state.currentLocation.npcs[numlocation].currentline == 1){
                                state.currentLocation.npcs[numlocation].currentline += 1;
                            }
                            else{
                                state.currentLocation.npcs[numlocation].dialogue = "The total was $13.50 and your Change is $1.75 for the painkillers. Have a good day";
                                state.currentLocation.description = "At CVS you completed the mission the exit door is on the right you will now head to the police station";
                                state.currentLocation.exitFound = true;
                                state.currentLocation.doorFound = true;
                                state.currentLocation.questComplete = true;
                            }
                        }
                    }
                    break;

            }
            
        }


}