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
    private Player player;
    private static final int MAX_WEIGHT = 15;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player (createRooms(), MAX_WEIGHT);
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room santo, miCasa, hospital, barrioHumedo, padreIsla, ordono, renfe, armeria, pinilla, eras, depositos, aeropuerto;

        // create the rooms
        santo = new Room("Santo Domingo");
        miCasa = new Room("tu casa");
        miCasa.addItem("cama", 500, false, false);
        hospital = new Room("Hospital. \nAquí podrás volver y pagar 3 monedas por recuperar las vidas que hayas perdido");
        barrioHumedo = new Room("Aquí podrás recuperar los tiros que hayas (mal)gastado con tu mala suerte");
        barrioHumedo.addItem("balas", 1, true, false);
        padreIsla = new Room("Estás en Padre Isla.\n¡Qué ha sido eso!");
        ordono = new Room("Estás en Ordono II. \n Al norte parece verse algo... ZOOOMBIES");
        renfe = new Room("Estación de tren.");
        renfe.addItem("barra metalica", 10, true, false);
        armeria = new Room("Armeria. \nAquí podrás armarte hasta los dientes... siempre y cuando traigas dinero!");
        armeria.addItem("Escopeta", 5, true, true);
        armeria.addItem("Pistola", 3, true, true);
        pinilla = new Room("Barrio Pinilla");
        pinilla.addItem("Bate", 3, true, false);
        eras = new Room ("Eras de Renueva");
        depositos = new Room ("Depósitos de Agua");
        aeropuerto = new Room ("GANASTE!");

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

        return santo;
        
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
        System.out.println("¿Tanto miedo te dan los ZOOOOMBIEEEESS? ¡Hasta otra, gallina!");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Debido a un envenenamiento la población de León se está transformando en zombies..."  );
        System.out.println("...tienes que huir o matar a todos los zombies....");
        System.out.println("...tú escoges...");
        System.out.println();
        look();
        printHelp();
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
            System.out.println("No entiendo qué quieres decir...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            player.goRoom(command);
        }
        else if (commandWord.equals("look")) {  
            look();
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("eat")) {
            System.out.println("Commiste y ya no tienes hambre");
        }
        else if (commandWord.equals("back")){
            player.backRoom();
        }
        else if (commandWord.equals("take")){
            player.takeItem(command);
        }
        else if (commandWord.equals("items")){
            player.printInventoryInfo();
        }
        else if (commandWord.equals("drop")){
            player.dropItem(command);
        }
        else if (commandWord.equals("equip")){
            player.equipItem(command);
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
        System.out.println("Te encuentras cansado, asustado... con ganas de volver a ver a tus seres queridos");
        System.out.println("...y huir de León.");
        System.out.println();
        System.out.println("Tus palabras comando son las siguientes:");
        System.out.println(parser.getCommandList());
    }

    private void look() {   
        System.out.println(player.getRoom().getLongDescription());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("¿Salir, qué? ¿De fiesta?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
