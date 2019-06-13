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
    private Item equipedItem;
    private int damage;
    public Player(Room initialRoom, int maxWeight)
    {
        currentRoom = initialRoom;
        lastRooms = new Stack<>();
        inventory = new ArrayList <Item> ();
        this.maxWeight = maxWeight;
        damage = 0;
    }

    public void takeItem(Command command) {
        if(currentRoom.hasItems()){
            if(!command.hasSecondWord()) {
                System.out.println("¿Qué objeto? (Escoge un número)");
                System.out.println(currentRoom.getItemString());
                return;

            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            Item selectedItem = currentRoom.getItem(idItem);
            if (selectedItem == null) {
                System.out.println("¡Ese item no existe!");
            }

            else {
                //se comprueba si al coger el nuevo item se sobre pasa el peso maximo.
                if(checkWeight(selectedItem.getPeso()) && selectedItem.isPickeable()){ 
                    inventory.add(selectedItem);
                    currentRoom.removeItem(idItem);
                }
                else{
                    if(!selectedItem.isPickeable()){
                        System.out.println("¡No puedes coger ese item!");
                    }
                    if(!checkWeight(selectedItem.getPeso()) && selectedItem.isPickeable()){
                        System.out.println("Llevas demasiado peso como para coger este objeto!!!");
                    }

                }
                printInventoryInfo();

            }
        }
        else{
            System.out.println("¡En esta habitación no hay objetos!");
        }
    }

    public void dropItem(Command command){
        if(!inventory.isEmpty()){
            if(!command.hasSecondWord()) {
                System.out.println("¿Qué SLOT quieres vaciar? \n");
                printInventoryInfo();
                return;
            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            if (idItem - 1 >= 0 && (idItem - 1) < (inventory.size())){
                Item selectedItem = inventory.get(idItem - 1);
                currentRoom.addItem(selectedItem.getDescripcion(), selectedItem.getPeso(), selectedItem.isPickeable(), selectedItem.isRanged());
                inventory.remove(idItem - 1);
                printInventoryInfo();
            }
            else {
                System.out.println("No existe ese item en tu inventario. \n");
                System.out.println("Lo que tienes es esto... \n");
                printInventoryInfo();
            }
        }
        else {
            System.out.println("No hay ningún item en tu inventario. \n");
        }
    }

    public void equipItem(Command command){
        if(!inventory.isEmpty()){
            if(!command.hasSecondWord()) {
                System.out.println("¿Qué SLOT quieres equiparte? \n");
                printInventoryInfo();
                return;
            }
            Integer idItem = Integer.parseInt(command.getSecondWord());
            if (idItem - 1 >= 0 && (idItem - 1) < (inventory.size())){
                Item selectedItem = inventory.get(idItem - 1);
                if(equipedItem != null){ 
                    if(checkWeight(equipedItem.getPeso() - selectedItem.getPeso())){
                        inventory.add(equipedItem);
                        equipedItem = selectedItem;
                        inventory.remove(idItem - 1);
                    }
                    else{
                        System.out.println("Llevas demasiado peso como para cargar de nuevo con el objeto equipado!!!");
                    }
                }
                else{
                    equipedItem = selectedItem;
                    inventory.remove(idItem - 1);
                }
                printInventoryInfo();
            }
            else {
                System.out.println("No existe ese item en tu inventario. \n");
                System.out.println("Lo que tienes es esto... \n");
                printInventoryInfo();
            }
        }
        else {
            System.out.println("No hay ningún item en tu inventario. \n");
        }
    }

    private String getInventoryInfo(){
        String datosInventario = "";
        if (equipedItem != null){
            datosInventario = "Equipado: " + equipedItem.getDescripcion() + "\n" +
             "Daño: " + equipedItem.getDamage() + "\n" +
             "Objetos inventario: \n";
        }
        if (!inventory.isEmpty()){
            for (int i = 1; i <= inventory.size(); i++){
                datosInventario += "Slot #" + i + ": " + inventory.get(i - 1).getDescripcion() +"  Peso: "+ inventory.get(i - 1).getPeso() + "kg\n";
            }
        }
        return datosInventario + "\nPeso total inventario: " + getTotalWeight() + " kg"; 
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
            System.out.println("¿Ir a dónde?");
            return;
        }
        String direction = command.getSecondWord();
        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("¡Ahí no hay salida!");
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
