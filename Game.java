/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
import java.util.ArrayList;
import java.util.Stack;
public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack<Room> lastRooms;
    private ArrayList<Item> inventory;
    private static final int MAX_WEIGHT = 15;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lastRooms = new Stack <Room>();
        inventory = new ArrayList<>();

    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room santo, miCasa, hospital, barrioHumedo, padreIsla, ordono, renfe, armeria, pinilla, eras, depositos, aeropuerto;

        // create the rooms
        santo = new Room("Debido a un envenenamiento la población de León se está transformando en zombies... tienes que huir o matar a todos los zombies... tú escoges...");
        miCasa = new Room("Estas en casa");
        miCasa.addItem("cama", 500, false);
        hospital = new Room("Aquí podrás volver y pagar 3 monedas por recuperar las vidas que hayas perdido");
        barrioHumedo = new Room("Aquí podrás recuperar los tiros que hayas (mal)gastado con tu mala suerte");
        barrioHumedo.addItem("balas", 1, true);
        padreIsla = new Room("Estás en Padre Isla. ¡Qué ha sido eso!");
        ordono = new Room("Estás en Ordono II. AL norte parece verse algo... ZOOOMBIES");
        renfe = new Room("Estación de tren.");
        renfe.addItem("barra metalica", 10, true);
        renfe.addItem("Bate", 3, true);
        armeria = new Room("Aquí podrás armarte hasta los dientes... siempre y cuando traigas dinero!");
        armeria.addItem("Escopeta", 5, true);
        armeria.addItem("Pistola", 3, true);
        pinilla = new Room("Barrio Pinilla");
        pinilla.addItem("Bate", 3, true);
        eras = new Room ("Eras de Renueva");
        depositos = new Room ("Depósitos de Agua");
        aeropuerto = new Room ("GANASTE!");

        currentRoom = santo;  // start game outside

        santo.setExit("north", ordono);
        santo.setExit("east", padreIsla);
        santo.setExit("southEast", miCasa);
        santo.setExit("south", barrioHumedo);
        santo.setExit("west", hospital);

        hospital.setExit("east", santo);

        barrioHumedo.setExit("north", santo);

        padreIsla.setExit("west", santo);
        padreIsla.setExit("north", armeria);
        padreIsla.setExit("east", depositos);
        padreIsla.setExit("HOME", miCasa);

        ordono.setExit("east", renfe);
        ordono.setExit("south", santo);
        ordono.setExit("HOME", miCasa);

        renfe.setExit("east", pinilla);
        renfe.setExit("south", armeria);
        renfe.setExit("west", ordono);
        renfe.setExit("southWest", eras);

        armeria.setExit("north", renfe);
        armeria.setExit("south", padreIsla);

        pinilla.setExit("west", renfe);
        pinilla.setExit("south", eras);
        pinilla.setExit("HOME", miCasa);

        eras.setExit("north", aeropuerto);
        eras.setExit("south", depositos);
        eras.setExit("west", pinilla);
        eras.setExit("northWest", renfe);

        depositos.setExit("north", eras);
        depositos.setExit("west", padreIsla);

        aeropuerto.setExit("south", eras);

        miCasa.setExit("northWest", santo);
        // initialise room exits n-e-se-s-o-no
        // santo.setExits(ordono, padreIsla, miCasa, barrioHumedo, hospital, null);
        // hospital.setExits(null, santo,null,null, null, null);
        // barrioHumedo.setExits(santo, null, null, null, null, null);
        // padreIsla.setExits(armeria, depositos, null, null, santo, null);
        // ordono.setExits(null, renfe, null, santo, null, null);
        // renfe.setExits(null, pinilla, eras, armeria, ordono, null);
        // armeria.setExits(renfe, null, null, padreIsla, null, null);
        // pinilla.setExits(null, null, null, eras, renfe, null);
        // eras.setExits(aeropuerto, null, null, depositos, pinilla, renfe);
        // depositos.setExits(eras, null, null, null, padreIsla, null);
        // aeropuerto.setExits(null, null, null, eras, null, null);
        // miCasa.setExits(null, null, null, null, null, santo);
        // currentRoom = santo;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printLocationInfo();
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("look")) {  
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("eat")) {
            System.out.println("You have eaten now and you are not hungry any more");
        }
        else if (commandWord.equals("back")){
            backRoom();
        }
        else if (commandWord.equals("take")){
            takeItem(command);
        }
        else if (commandWord.equals("items")){
            printInventoryInfo();
        }
        else if (commandWord.equals("drop")){
            dropItem(command);
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommandList());
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRooms.push(currentRoom);
            currentRoom = nextRoom;
            printLocationInfo();
        }
    }

    private void backRoom(){

        if (lastRooms.isEmpty()) {
            System.out.println("Primero tienes que avanzar");
        }
        else {
            currentRoom = lastRooms.pop();
            printLocationInfo();
        }
    }

    private void printLocationInfo()
    {  
        System.out.println(currentRoom.getLongDescription());
        System.out.println();

    }

    private String getInventoryInfo(){
        String datosInventario = "";

        if (!inventory.isEmpty()){
            for (int i = 1; i <= inventory.size(); i++){
                datosInventario += "Slot #" + i + ": " + inventory.get(i - 1).getDescripcion() +"Peso: "+ inventory.get(i - 1).getPeso() + "kg\n";
            }
        }
        return datosInventario + getTotalWeight() + " kg \n";
    }

    private void  printInventoryInfo(){
        System.out.println(getInventoryInfo());
        System.out.println("El peso máximo es de "+ MAX_WEIGHT + "kg.");
    }

    private void look() {   
        System.out.println(currentRoom.getLongDescription());
    }

    private void takeItem(Command command) {
        if(currentRoom.hasItems()){
            if(!command.hasSecondWord()) {
                System.out.println("Which one? (1-6)");
                System.out.println(currentRoom.getItemString());
                return;

            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            Item selectedItem = currentRoom.getItem(idItem);
            if (selectedItem == null) {
                System.out.println("This item doesn't exist!");
            }

            else {
                //se comprueba si al coger el nuevo item se sobre pasa el peso maximo.
                if(checkWeight(selectedItem.getPeso()) && selectedItem.getPickeable()){ 
                    inventory.add(selectedItem);
                    currentRoom.removeItem(idItem);
                }
                else{
                    if(!selectedItem.getPickeable()){
                        System.out.println("This item isn't pickeable!");
                    }
                    if(!checkWeight(selectedItem.getPeso()) && selectedItem.getPickeable()){
                        System.out.println("Llevas demasiado peso como para coger este objeto!!!");
                    }

                }
                printInventoryInfo();

            }
        }
        else{
            System.out.println("There are no items in this room!");
        }
    }

    private void dropItem(Command command){
        if(!inventory.isEmpty()){
            if(!command.hasSecondWord()) {
                System.out.println("Which one? \n");
                printInventoryInfo();
                return;
            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            if (idItem - 1 >= 0 && (idItem - 1) < (inventory.size())){
                Item selectedItem = inventory.get(idItem - 1);
                currentRoom.addItem(selectedItem.getDescripcion(), selectedItem.getPeso(), selectedItem.getPickeable());
                inventory.remove(idItem - 1);
                printInventoryInfo();
            }
            else {
                System.out.println("This item isnt in you invetory. \n");
                System.out.println("This is what you have. \n");
                printInventoryInfo();
            }
        }
        else {
            System.out.println("There no items in your invetory. \n");
        }
    }

    private int getTotalWeight(){
        int totalWeight = 0;
        for(Item item : inventory){
            totalWeight += item.getPeso();
        }
        return totalWeight;
    }

    private boolean checkWeight(int itemWeight){
        boolean validWeight = false;
        if(getTotalWeight() + itemWeight <= MAX_WEIGHT){
            validWeight = true; 
        }
        return validWeight;
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
