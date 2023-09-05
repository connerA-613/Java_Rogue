package rogue;
import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;

public class Door implements Serializable {
    private String direction;
    private int position;
    private Room room;
    private Point doorPt;
    private ArrayList<Room> connectedRooms;

    /**
     * Default Door contructor.
     */
    public Door() {
        direction = new String();
        room = new Room();
        connectedRooms = new ArrayList<Room>();
        connectedRooms.add(room);
        doorPt = new Point();
    }

    /**
     * Adds the room that the door connects to.
     * @param r (Room that door leads to)
     */
    public void connectRoom(Room r) {
        connectedRooms.add(r);
    }

    /**
     * Returns the array of rooms the door is connected to.
     * @return Connected rooms (Array List of rooms)
     */
    public ArrayList<Room> getConnectedRooms() {
        return connectedRooms;
    }

    /**
     * Gets the other room that the door is connected to.
     * @param currentRoom (the source room)
     * @return toReturn (the target room)
     */
    public Room getOtherRoom(Room currentRoom) {
        Room toReturn = new Room();
        for (Room roomIndex : connectedRooms) {
            if (roomIndex != currentRoom) {
                toReturn = roomIndex;
            }
        }
        return toReturn;
    }

    /**
     * Sets the doors direction.
     * @param dir (Door direction)
     */
    public void setDirection(String dir) {
        direction = dir;
    }

    /**
     * Returns the doors direction.
     * @return direction
     */
    public String getDirection() {
        return direction;
    }

    /**
     * Sets the position of the door.
     * @param pos (distance from x or y axis)
     */
    public void setPosition(int pos) {
        position = pos;
        int x = 0;
        int y = 0;
        if (direction == "N") {
            doorPt.move(pos, y);
        } else if (direction == "S") {
            doorPt.move(pos, room.getHeight() - 1);
        } else if (direction == "E") {
            doorPt.move(room.getWidth() - 1, pos);
        } else if (direction == "W") {
            doorPt.move(x, pos);
        }
    }

    /**
     * Returns the doors coordinate.
     * @return doorPt (where the door is on the map)
     */
    public Point getCoordinate() {
        return doorPt;
    }

    /**
     * Return the doors position.
     * @return position
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the room the door is located in.
     * @param toAdd
     */
    public void setRoom(Room toAdd) {
        room = toAdd;
    }

    /**
     * Gets the room the door is located in.
     * @return room
     */
    public Room getRoom() {
        return room;
    }

}
