package rogue;
import java.awt.Point;
import java.io.Serializable;
/**
 * A basic Item class; basic functionality for both consumables and equipment.
 */
public class Item implements Serializable {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    private Character displayCharacter;
    private Room currentRoom;

    /**
     * Default constructor for item.
     */
    public Item() {
        int id2;
        String name2;
        String type2;
        Point xyLocation2;
    }

    /**
     * Consturctor for item class.
     * @param idP
     * @param nameP
     * @param typeP
     * @param xyLocationP
     */
    public Item(int idP, String nameP, String typeP, Point xyLocationP) {
        this.id = idP;
        this.name = nameP;
        this.type = typeP;
        this.xyLocation = xyLocationP;
    }
    // Getters and setters
    /**
     * gets the ID of the item.
     * @return id
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID of an item.
     * @param idP
     */
    public void setId(int idP) {
        this.id = idP;
    }

    /**
     * Gets the name of an item.
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the item name.
     * @param nameP
     */
    public void setName(String nameP) {
        this.name = nameP;
    }

    /**
     * Gets the item's type.
     * @return type
     */
    public String getType() {
        return type;

    }
    /**
     * Gets the type of item.
     * @param typeP
     */
    public void setType(String typeP) {
        this.type = typeP;
    }
    /**
     * Gets the display character of the item.
     * @return displayCharacter
     */
    public Character getDisplayCharacter() {
        return displayCharacter;
    }
    /**
     * Sets the display character of the item.
     * @param newDisplayCharacter
     */
    public void setDisplayCharacter(Character newDisplayCharacter) {
        this.displayCharacter = newDisplayCharacter;
    }
    /**
     * Gets the description of the item.
     * @return null
     */
    public String getDescription() {
        return null;
    }
    /**
     * Sets the description.
     * @param newDescription
     */
    public void setDescription(String newDescription) {
    }
    /**
     * Gets the location of an item.
     * @return xyLocation
     */
    public Point getXyLocation() {
        return xyLocation;
    }
    /**
     * Sets the location of the item.
     * @param newXyLocation
     */
    public void setXyLocation(Point newXyLocation) {
        this.xyLocation = newXyLocation;
    }
    /**
     * Gets the current room the item is in.
     * @return null
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }
    /**
     * Sets the item's current room.
     * @param newCurrentRoom
     */
    public void setCurrentRoom(Room newCurrentRoom) {
        currentRoom = newCurrentRoom;
    }
}
