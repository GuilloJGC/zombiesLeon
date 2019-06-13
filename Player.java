import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Stack;
public class Player
{
    private Stack<Room> lastRooms;
    private Room currentRoom;
    private ArrayList <Item> inventory;

    public Player(Room initialRoom)
    {
        currentRoom = initialRoom;
        lastRooms = new Stack<>();
        inventory = new ArrayList <Item> ();
    }

    public void takeItem(Command command) {
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
            else{
                if(selectedItem.isPickeable()){
                    inventory.add(selectedItem);
                    currentRoom.removeItem(idItem);
                    printInventoryInfo();
                }
                else{
                    System.out.println("This item isn't pickeable!");
                }
            }
        }
        else{
            System.out.println("There are no items in this room!");
        }
    }

    private String getInventoryInfo(){
        String datosInventario = "";

        if (!inventory.isEmpty()){
            for (int i = 1; i <= inventory.size(); i++){
                datosInventario += "Slot #" + i + ": " + inventory.get(i - 1).getDescripcion() +"Peso: "+ inventory.get(i - 1).getPeso() + "kg\n";
            }
        }
        return datosInventario + "\nPeso total: " + getTotalWeight() + " kg"; 
    }
    private int getTotalWeight(){
        int totalWeight = 0;
        for(Item item : inventory){
            totalWeight += item.getPeso();
        }
        return totalWeight;
    }
    public void  printInventoryInfo(){
        System.out.println(getInventoryInfo());

    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    public void goRoom(Command command) 
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
            System.out.println(checkRoom(currentRoom));
        }
    }

    public void backRoom(){
        if (lastRooms.isEmpty()) {
            System.out.println("Primero tienes que avanzar");
        }
        else {
            currentRoom = lastRooms.pop();
            System.out.println(checkRoom(currentRoom));
        }
    }

    public Room getRoom(){
        return currentRoom;
    }

    public String checkRoom(Room room){
        return room.getLongDescription();
    }
}
