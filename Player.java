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
    private int maxWeight;
    public Player(Room initialRoom, int maxWeight)
    {
        currentRoom = initialRoom;
        lastRooms = new Stack<>();
        inventory = new ArrayList <Item> ();
        this.maxWeight = maxWeight;
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

            else {
                //se comprueba si al coger el nuevo item se sobre pasa el peso maximo.
                if(checkWeight(selectedItem.getPeso()) && selectedItem.isPickeable()){ 
                    inventory.add(selectedItem);
                    currentRoom.removeItem(idItem);
                }
                else{
                    if(!selectedItem.isPickeable()){
                        System.out.println("This item isn't pickeable!");
                    }
                    if(!checkWeight(selectedItem.getPeso()) && selectedItem.isPickeable()){
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

    public void dropItem(Command command){
        if(!inventory.isEmpty()){
            if(!command.hasSecondWord()) {
                System.out.println("Which SLOT do you want to drop? \n");
                printInventoryInfo();
                return;
            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            if (idItem - 1 >= 0 && (idItem - 1) < (inventory.size())){
                Item selectedItem = inventory.get(idItem - 1);
                currentRoom.addItem(selectedItem.getDescripcion(), selectedItem.getPeso(), selectedItem.isPickeable());
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

    private String getInventoryInfo(){
        String datosInventario = "";

        if (!inventory.isEmpty()){
            for (int i = 1; i <= inventory.size(); i++){
                datosInventario += "Slot #" + i + ": " + inventory.get(i - 1).getDescripcion() +"Peso: "+ inventory.get(i - 1).getPeso() + "kg\n";
            }
        }
        return datosInventario + "\nPeso total: " + getTotalWeight() + " kg"; 
    }

    private boolean checkWeight(int itemWeight){
        boolean validWeight = false;
        if(getTotalWeight() + itemWeight <= maxWeight){
            validWeight = true; 
        }
        return validWeight;
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
        System.out.println("El peso máximo es de "+ maxWeight + "kg.");
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
