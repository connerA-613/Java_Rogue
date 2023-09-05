package rogue;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;



/**
 * A room within the dungeon - contains monsters, treasure,
 * doors out, etc.
 */
public class Room implements Serializable {
    private int width;
    private int height;
    private int id;
    private boolean start;
    private ArrayList<Item> items;
    private Player player;
    private ArrayList<Door> doors;
    private HashMap<String, Integer> doorsHash;
    private static final int DIFF = 3;
    private HashMap<String, Character> roomSymbols;


    /**
     * Default contrustor.
     */
    public Room() {
        items = new ArrayList<Item>();
        doors = new ArrayList<Door>();
        doorsHash = new HashMap<String, Integer>();
        player = new Player();
    }


    // Required getter and setters below

    /**
     * Gets the width of the room.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the room.
     *
     * @param newWidth
     */
    public void setWidth(int newWidth) {
        this.width = newWidth;
    }

    /**
     * Gets the height of the room.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the room.
     *
     * @param newHeight
     */
    public void setHeight(int newHeight) {
        this.height = newHeight;
    }

    /**
     * gets the ID of the room.
     *
     * @return id
     */
    public int getId() {
        return id;

    }

    /**
     * Sets the ID of the room.
     *
     * @param newId
     */
    public void setId(int newId) {
        this.id = newId;
    }

    /**
     * Gets the list of items in the room.
     *
     * @return items
     */
    public ArrayList<Item> getRoomItems() {
        return items;

    }

    /**
     * Sets the list of items for the room.
     *
     * @param newRoomItems
     */
    public void setRoomItems(ArrayList<Item> newRoomItems) {
        this.items = newRoomItems;
    }

    /**
     * Gets the player in the room.
     *
     * @return player
     */
    public Player getPlayer() {
        return player;

    }

    /**
     * Sets the player for the room.
     *
     * @param newPlayer
     */
    public void setPlayer(Player newPlayer) {
        this.player = newPlayer;
    }

    /**
     * Gets the door for the room given the direction.
     *
     * @param direction
     * @return northDoor, southDoor, eastDoor, westDoor, -1
     */
    public Door getDoor(String direction) {
        Door toReturn = new Door();
        for (Door door : doors) {
            if (door.getDirection() == direction) {
                toReturn = door;
            }
        }
        return toReturn;
    }

    /**
     * returns all the doors in the room.
     * @return doors (ArrayList of doors)
     */
    public ArrayList<Door> getDoors() {
        return doors;
    }


/*
direction is one of NSEW
location is a number between 0 and the length of the wall
*/

    /**
     * Sets the door in the room.
     * @param toAdd
     */
    public void setDoor(ArrayList<Door> toAdd) {
        doors = toAdd;
    }

    /**
     * Adds a door to the room.
     * @param door (door to be added)
     */
    public void addDoor(Door door) {
        doors.add(door);
    }

    /**
     * Sets if the room is the starting room.
     *
     * @param startP
     */
    public void setStart(boolean startP) {
        this.start = startP;
    }

    /**
     * Checks if the player is in the room.
     *
     * @return start
     */
    public boolean isPlayerInRoom() {
        return start;
    }

    /**
     * gets the location of the symbol file.
     *
     * @return symbolsLocation
     */
    public String getSymbolFile() {
        JSONParser parser = new JSONParser();
        String configurationFileLocation = "fileLocations.json";
        String symbolsLocation = "";
        try {

            Object obj = parser.parse(new FileReader(configurationFileLocation));
            JSONObject configurationJSON = (JSONObject) obj;
            symbolsLocation = (String) configurationJSON.get("Symbols");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return symbolsLocation;
    }

    /**
     * Sets the room symbols.
     *
     * @param symbols
     */
    public void setRoomSymbols(HashMap<String, Character> symbols) {
        roomSymbols = symbols;
    }

    /**
     * gets the room symbols.
     *
     * @return roomSymbols
     */
    public HashMap<String, Character> getRoomSymbols() {
        return roomSymbols;
    }

    /**
     * Produces a string that can be printed to produce an ascii rendering of the room and all of its contents.
     *
     * @return (String) String representation of how the room looks
     */
    public String displayRoom() {
        StringBuilder result = startOutput();
        if (roomSymbols != null) {
            StringBuilder northWall = new StringBuilder();
            for (int i = 0; i < width; i++) {
                northWall.append(roomSymbols.get("NS_WALL"));
            }
            if (doorsHash.get("N") != -1) {
                northWall.setCharAt(doorsHash.get("N"), roomSymbols.get("DOOR"));
            }
            northWall.append("\n");
            result.append(northWall);
            displayMiddleRoom(result);
            StringBuilder southWall = new StringBuilder();
            for (int i = 0; i < width; i++) {
                southWall.append(roomSymbols.get("NS_WALL"));
            }
            if (doorsHash.get("S") != -1) {
                southWall.setCharAt(doorsHash.get("S"), roomSymbols.get("DOOR"));
            }
            southWall.append("\n");
            result.append(southWall);
        }
            return result.toString();
    }

    /**
     * Creates the middle portion of the room (inside the walls).
     * @param result (String of upper wall)
     * @return result (updated with insides of room)
     */
    private StringBuilder displayMiddleRoom(StringBuilder result) {
        for (int row = 0; row < this.height - 2; row++) {
            if (doorsHash.get("W") == row + 1) {
                result.append(roomSymbols.get("DOOR"));
            } else {
                result.append(roomSymbols.get("EW_WALL"));
            }
            StringBuilder floor = new StringBuilder();
            for (int i = 0; i < width-2; i++) {
                floor.append(roomSymbols.get("FLOOR"));
            }
            if (isPlayerInRoom()) {
                if (player.getXyLocation().y == row + 1) {
                    floor.setCharAt(player.getXyLocation().x - 1, roomSymbols.get("PLAYER"));
                }
            }
            for (Item item : items) {
                Character itemChar = item.getDisplayCharacter();
                if (item.getXyLocation().y == row + 1) {
                    floor.setCharAt(item.getXyLocation().x - 1, itemChar);
                }
            }
            result.append(floor);
            if (doorsHash.get("E") == row + 1) {
                result.append(roomSymbols.get("DOOR"));
            } else {
                result.append(roomSymbols.get(("EW_WALL")));
            }
            result.append("\n");
        }
        return result;
    }

    /**
     * Creates the start of the string for the room diaplay.
     *
     * @return The string with the room header.
     */
    private StringBuilder startOutput() {
        StringBuilder result = new StringBuilder();
        result.append("<-----");
        result.append("[Room " + this.id + "]");
        result.append("----->\n");
        return result;
    }

    /**
     * Adds an item to the room.
     * @param toAdd (Item to be added)
     * @throws ImpossiblePositionException (if item has a position
     *                                      out of bounds or on
     *                                      another item).
     */
    public void addItem(Item toAdd) throws ImpossiblePositionException {
            if (toAdd.getXyLocation().x > 0 && toAdd.getXyLocation().x < width - 1
                    && toAdd.getXyLocation().y > 0 && toAdd.getXyLocation().y < height - 1) {
                for (Item item : items) {
                    if (item.getXyLocation().equals(toAdd.getXyLocation())) {
                        throw new ImpossiblePositionException("Invalid placement");
                    }
                }
                if (toAdd.getXyLocation().equals(player.getXyLocation())) {
                    throw new ImpossiblePositionException("Invalid placement");
                }
                items.add(toAdd);
            } else {
                throw new ImpossiblePositionException("Invalid placement");
            }
    }

    /**
     * Adds a door to the HashMap of doors.
     * @param dir (Door direction)
     * @param pos (Door position)
     */
    public void addDoorHash(String dir, Integer pos) {
        doorsHash.put(dir, pos);
    }
}

