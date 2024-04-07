/*
GameState.java
For use in the Final project for COSC 236.
Based on starter code first developed by Prof. Dastyni Loksa

This is the class to hold the state of the running game and allows easy
passing of important information to methods that require data from the
state of the game.

This starter code is designed for the verbs to be stored in the commandSystem.

*/

public class GameState {
    Location currentLocation;
    Location livingroom;
    Location bedroom;
    Location cvs;
    Location policestation;
    Location chiefoffice;
    CommandSystem commandSystem;
    Item phone; 
    Item door;
    Item keys;
    Item table;
    Item desk;
    Item GPS;
    Item invisItem;
    Item painkillers;
    Item drawer1;
    Item drawer2;
    Item drawer3;
    Item files;
    Item securityCam;
    Person bobPerson;
    Person susiePerson;
    Person edwardPerson;
    Person chiefNPC; // Same person as the policeChiefText Just not over the phone
    Person policeChiefText;
    Person eddieNPC;
    public static int DISPLAY_WIDTH = 100;

    /*
     * GameState Constructor
     * 
     * Ideally, your game will be fully loaded and ready to play once this
     * constructor has finished running.
     * *
     * How things have been done here are just a rudementry setup to link the other
     * classes and have the
     * bare bones example of the command system. This is not a great way to
     * initilize your project.
     * 
     * You should do better!
     */
    public GameState() {
        commandSystem = new CommandSystem(this);
        
        invisItem = new Item();
        invisItem.name = "Invisible";
        invisItem.description = "";
        commandSystem.addNoun("Invisible");
        // Create first (starting) location
        // (and store it in currentLocation so I can always referece where the player is
        // easily)

        bedroom = new Location();
        bedroom.quest = true;
        bedroom.questComplete = false;
        bedroom.name = "Bedroom";
        bedroom.description = "You are just woke up in your Bedroom. Your feeling ill from your disease but you get a text on your Phone from the PoliceChief";
        commandSystem.addNoun("Bedroom");
        currentLocation = bedroom;

        livingroom = new Location();
        livingroom.canUsePhone = false;
        livingroom.name = "Livingroom";
        livingroom.description = "In the livingroom, the keys are on the couch, and there is a table in the middle of the room. \n You have the option of either going to the station or heading to the Pharmacy to get painkillerz.";
        commandSystem.addNoun("livingroom");


        cvs = new Location();
        cvs.name = "CVS";
        cvs.description = "In the shop there is Eddie the Cashier, in isle1 there is drawer1, in isle2 there is drawer2, and in isle3, there is drawer3";
        commandSystem.addNoun("cvs");
        
        policestation = new Location();
        policestation.name = "PoliceStation";
        policestation.description = "At the police station, there is the police chiefs mainoffice and your coworkers Bob, Edward, and Susie. Aswell as the door to the chiefs office";
        commandSystem.addNoun("PoliceStation");


        chiefoffice = new Location();
        chiefoffice.name = "ChiefOffice";
        chiefoffice.description = "At the detectives office, the PoliceChief is waiting for you at the desk.";
        commandSystem.addNoun("ChiefOffice");


        // Add item to list of nouns so our command system knows it exists.
        commandSystem.addNoun("message");
        // Bedroom Items
        phone = new Item("Phone","3 new notification(s)! from PoliceChief:"); // Create a demo item.
        phone.isFound = true;
        door = new Item("Door", "Leaving Room");
        commandSystem.addNoun(phone.name); 
        commandSystem.addNoun(door.name); 
        bedroom.itemsHere[0] = phone;
        bedroom.itemsHere[1] = door;
        currentLocation.itemsHere[0] = bedroom.itemsHere[0];
        currentLocation.itemsHere[1] = bedroom.itemsHere[1];



        // Bedroom NPC
        policeChiefText = new Person("PoliceChief", "The Police Chief is the person you work for, \n he is involved with assigning you and your coworkers cases to investigate ", "Hello Detective, Yesterday a Victim named Jim Downing was murdedered outside around 2:00 AM (say next  for more with the 2 other nouns)", 3); 
        policeChiefText.inText = true; // Meaning it is just a text message
        policeChiefText.currentline = 1;
        bedroom.npcs[0] = policeChiefText; // The boss isn't actually in the room but we are using the dialogue for text messages
        currentLocation.npcs[0] = bedroom.npcs[0];
        commandSystem.addNoun(policeChiefText.name);




        // Living Room Items
        livingroom.totalExits = 2;
        livingroom.quest = true;
        keys = new Item("Keys", "Need em to start the car. It is very USEful.(Hint:Use a command with this)");
        livingroom.itemsHere[0] = keys;
        table = new Item("Table", "Just a bunch of loose change, and a GPS");
        livingroom.itemsHere[1] = table;
        GPS = new Item("GPS", "USE this to walk to nearby locations like walmart or CVS (😉Hint: Use a command with this)");
        livingroom.itemsHere[2] = GPS;
        commandSystem.addNoun(keys.name); 
        commandSystem.addNoun(table.name); 
        commandSystem.addNoun(GPS.name); 




        // CVS Items and NPCS.
        cvs.totalExits = 1;
        cvs.quest = true;
        drawer1 = new Item("Drawer1", "This drawer contains laxatives, nasal spray, and Cough Syrup");
        drawer1.isFound = true;
        cvs.itemsHere[0] = drawer1;

        drawer2 = new Item("Drawer2", "This drawer contains allegra, viagra, and eardrops");
        drawer2.isFound = true;
        cvs.itemsHere[1] = drawer2;

        drawer3 = new Item("Drawer3", "This drawer contains eyedrops, painkillers, and lotion");
        drawer3.isFound = true;
        cvs.itemsHere[2] = drawer3;

        painkillers = new Item("Painkillers", "This pain is getting worse, were gonna have to do something illegal(hint: Use a command)");
        painkillers.isFound = false;
        cvs.itemsHere[3] = painkillers;

        eddieNPC = new Person("Eddie", "The Cashier At CVS", "Welcome to CVS! Give me your items that you need to purchase so i can scan them", 2);
        cvs.npcs[0] = eddieNPC;


        // Police Station NPCS and etc.
        bobPerson = new Person("Bob", "Your Coworker, worked at the front desk for 5 years(also the most annoying)", "Detective, you feeling okay you look a little bit off", 1);
        susiePerson = new Person("Susie", "Working with the chief for 2 years(Secretly Crushing on)", "Hey Detective, the chief is waiting for you in his office", 1);
        edwardPerson = new Person("Edward", "Rookie, First Year on the job", "Man this job is seriously making me want to reconsider my career choice and IM ONLY A ROOKIE!", 1);

        

        policestation.npcs[0] = bobPerson;
        policestation.npcs[1] = susiePerson;
        policestation.npcs[2] = edwardPerson;
        policestation.exitFound = true;
        policestation.doorFound = true;


        // Chiefs Office Npcs items and etc.
        chiefNPC = new Person(policeChiefText.name, policeChiefText.description, "Hello Detective, As You Know there has been a murder, so far we have found 3 suspects. (next..)", 2);
        chiefNPC.currentline = 1;
        chiefoffice.npcs[0] = chiefNPC;

        desk = new Item("Desk", "Locked There must be a key somewhere");
        chiefoffice.itemsHere[0] = desk;

        // Setting Up Ending
        files = new Item("Files", "This File Contains The Suspects Background Information. John, Rubi and Jake (The Detectives Son).");
        securityCam = new Item("SecurityCam", "Footage from 2 Days Ago Halfway destroyed. Seems like someone is trying to hide somethings (hint: Pretty USEful right?)");
        files.isFound = false;
        securityCam.isFound = false;

        commandSystem.addNoun(drawer1.name); 
        commandSystem.addNoun(drawer2.name); 
        commandSystem.addNoun(drawer3.name); 
        commandSystem.addNoun(painkillers.name); 
        commandSystem.addNoun(eddieNPC.name); 
        commandSystem.addNoun(bobPerson.name); 
        commandSystem.addNoun(susiePerson.name); 
        commandSystem.addNoun(edwardPerson.name); 
        commandSystem.addNoun(chiefNPC.name);
        commandSystem.addNoun(desk.name);
        commandSystem.addNoun(files.name);
        commandSystem.addNoun(securityCam.name);


        /*
         * Once the commandSystem knows about the item, we need to code what happens
         * with each of the commands that can happen with the item.
         * See CommandSystem line 96 for what happens if you currently "look mat"
         */
    }

    public static String getDesc(Item s){
        String desc = s.description;
        return desc;
    }

    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds*1000); // Wait for 1 second (1000 milliseconds)
        } catch (InterruptedException e) {
            // Handle the exception
        }
    }



}
