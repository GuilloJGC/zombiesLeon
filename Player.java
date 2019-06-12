import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Stack;
public class Player
{
    private Stack<Room> lastRooms;
    private Room currentRoom;

    public Player(Room initialRoom)
    {
        currentRoom = initialRoom;
        lastRooms = new Stack<>();
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
