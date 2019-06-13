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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;
import java.util.Map;
public class Room 
{
    private String description;
    private HashMap <String, Room> salidas;
    private HashMap <Integer, Item> items;
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
        items = new HashMap <> ();
    }

    public void setExit(String direccion, Room habitacion){
        salidas.put(direccion, habitacion);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        return salidas.get(direccion);
    }

    /**
     * Devuelve la información de las salidas existentes
     * Por ejemplo: "Exits: north east west"
     *
     * @return Una descripción de las salidas existentes.
     */
    public String getExitString(){
        Set<String> direcciones = salidas.keySet();
        String descripcion = "Exit: ";

        for(String direccion : direcciones){
            descripcion += direccion + " ";
        }
        return descripcion;
    }

    public void addItem(String nombre, int peso, boolean pickeable){
        int newId = 1;
        while (items.containsKey(newId)){
            newId++;
        }
        Item nuevoItem = new Item (newId, nombre, peso, pickeable);
        items.put(nuevoItem.getId(), nuevoItem);

    }

    public Item getItem(Integer idItem){
        return items.get(idItem);
    }

    public void removeItem(Integer idItem){
        items.remove(idItem);
    }
    
    public boolean hasItems(){
        boolean hasIt = false;
        if(!items.isEmpty()){
            hasIt = true;
        }
        return hasIt;
    }

    public String getItemString(){
        String datosItem = "";
        for(Map.Entry<Integer, Item> item : items.entrySet()){
            datosItem +=item.getKey() + ". " + item.getValue().getDescripcion() + " " + item.getValue().getPeso() + " kg \n";
        }
        return datosItem; 
    }

    /**
     * Devuelve un texto con la descripcion larga de la habitacion del tipo:
     *     You are in the 'name of room'
     *     Exits: north west southwest
     * @return Una descripcion de la habitacion incluyendo sus salidas
     */
    public String getLongDescription(){
        return "Estás en " + description + "\n" + getExitString() +"\n"+ "Items: " + getItemString();
    }
}
