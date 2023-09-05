package rogue;
import java.awt.Point;
import java.io.Serializable;
/**
 * The player character.
 */
public class Player implements Serializable {
    private String name;
    private Point xyLocation;
    private Room currentRoom;
    /**
     * Default constructor.
     */
    public Player() {
        String name2;
        Point xyLocation2;
        Room currentRoom2;
    }
    /**
     * Constructor for the player class.
     * @param nameP
     */
    public Player(String nameP) {
        this.name = nameP;
        this.xyLocation = new Point(1, 1);
    }
    /**
     * Gets the name of the player.
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Sets the name of the player.
     * @param newName
     */
    public void setName(String newName) {
        name = newName;
    }
    /**
     * Gets the location of the player.
     * @return xyLocation
     */
    public Point getXyLocation() {
        return this.xyLocation;

    }
    /**
     * Sets the location of the player.
     * @param newXyLocation
     */
    public void setXyLocation(Point newXyLocation) {
        this.xyLocation = newXyLocation;
    }
    /**
     * Gets the current room the player is in.
     * @return currentRoom
     */
    public Room getCurrentRoom() {
        return this.currentRoom;

    }
    /**
     * Sets the player's current room.
     * @param newRoom
     */
    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }
}
