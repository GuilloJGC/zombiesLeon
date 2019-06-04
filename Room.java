/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */
import java.util.HashMap;
public class Room 
{
    private String description;
    private HashMap <String, Room> salidas;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(Room north, Room east, Room southEast, Room south, Room west, Room northWest) 
    {
        if(north != null)
            salidas.put("north", north);
        if(east != null)
            salidas.put("east", east);
        if (southEast != null)
            salidas.put("southEast", southEast);
        if(south != null)
            salidas.put("south", south);
        if(west != null)
            salidas.put("west", west);
        if(northWest != null)
            salidas.put("northWest", northWest);    
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        Room habitacion = null;
        if(direccion.equals("north")){
            habitacion = salidas.get("north");
        }
        if(direccion.equals("south")){
            habitacion = salidas.get("north");
        }
        if(direccion.equals("east")){
            habitacion = salidas.get("east");
        }
        if(direccion.equals("west")){
            habitacion = salidas.get("west");
        }
        if(direccion.equals("southEast")){
            habitacion = salidas.get("southEast");
        }
        if(direccion.equals("northWest")){
            habitacion = salidas.get("northWest");
        }
        return habitacion;
    }

    /**
     * Devuelve la informaci�n de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripci�n de las salidas existentes.
     */
    public String getExitString(){
        String descripcion = "Exits: ";
        if (salidas.get("north") != null){
            descripcion += "north ";
        }
        if (salidas.get("south") != null){
            descripcion += "south ";
        }
        if (salidas.get("east") != null){
            descripcion += "east ";
        }
        if (salidas.get("west") != null){
            descripcion += "west ";
        }
        if (salidas.get("southEast") != null){
            descripcion += "southEast";
        }
        if (salidas.get("northWest") != null){
            descripcion += "northWest";
        }
        return descripcion;
    }
}
