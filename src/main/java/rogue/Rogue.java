package rogue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;
import java.awt.Point;








public class Rogue implements Serializable {
    private Player player;
    private ArrayList<Item> items;
    private ArrayList<Item> inventory;
    private ArrayList<Room> rooms;
    private ArrayList<Food> food;
    private ArrayList<SmallFood> smallFood;
    private ArrayList<Ring> rings;
    private ArrayList<Potion> potions;
    private ArrayList<Magic> magic;
    private ArrayList<Clothing> clothes;
    private HashMap<String, Character> displaySymbols;
    public static final char UP = 's';
    public static final char DOWN = 'w';
    public static final char LEFT = 'a';
    public static final char RIGHT = 'd';
    /**
     * Sets the player for the game.
     * @param thePlayer
     */
    public void setPlayer(Player thePlayer) {
        this.player = thePlayer;
    }
    /**
     * retreives the info from the parser.
     * @param toParse
     */
    public Rogue(RogueParser parser) {
        player = new Player();
        displaySymbols = new HashMap<String, Character>();
        rooms = new ArrayList<Room>();
        items = new ArrayList<Item>();
        food = new ArrayList<Food>();
        smallFood = new ArrayList<SmallFood>();
        magic = new ArrayList<Magic>();
        potions = new ArrayList<Potion>();
        clothes = new ArrayList<Clothing>();
        rings = new ArrayList<Ring>();
        inventory = new ArrayList<Item>();
        //Symbols is used in addRoom and addItem, so it must be first.
        String[] symbolNames = {"NS_WALL", "EW_WALL", "DOOR", "FLOOR", "PASSAGE", "PLAYER", "GOLD", "POTION", "SCROLL",
                "ARMOR", "FOOD", "CLOTHING", "RING", "SMALLFOOD"};
        String[] dir = {"N", "S", "E", "W"};
        int i;
        for (i = 0; i < symbolNames.length; i++) {
            String currentName = symbolNames[i];
            Character currentSymbol;
            currentSymbol = parser.getSymbol(currentName);
                displaySymbols.put(currentName, currentSymbol);
        }
        Map<String, String> current = parser.nextRoom();
        Map<Integer, Map<String, String>> roomDoors = new HashMap<>();

        while (current != null) {
            addRoom(current);
            Map<String, String> doors = new HashMap<String, String>();
            for (String string : dir) {
                doors.put(string, current.get(string));
                doors.put(string + "connected", current.get(string + "connected"));
            }
            int roomNum = Integer.parseInt(current.get("id"));
            roomDoors.put(roomNum, doors);
            current = parser.nextRoom();
        }
            for (Room room : rooms) {
                Map<String, String> currentDoor = roomDoors.get(room.getId());
                addDoors(currentDoor, room);
            }
        current = parser.nextItem();
        while (current != null) {
            addItem(current);
            current = parser.nextItem();
        }

    }

    /**
     * adds a room to the games array list of rooms.
     * @param toAdd
     */
    public void addRoom(Map<String, String> toAdd) {
        Room currentRoom = new Room();
        currentRoom.setHeight(Integer.parseInt(toAdd.get("height")));
        currentRoom.setWidth(Integer.parseInt(toAdd.get("width")));
        currentRoom.setId(Integer.parseInt(toAdd.get("id")));
        currentRoom.setStart(Boolean.parseBoolean(toAdd.get("start")));
        currentRoom.setRoomSymbols(displaySymbols);
        if (currentRoom.isPlayerInRoom()) {
            currentRoom.setPlayer(this.player);
            Point pt = new Point(1, 1);
            currentRoom.getPlayer().setXyLocation(pt);
        }
        this.rooms.add(currentRoom);
    }

    /**
     *
     * @param toAdd
     * @param currentRoom
     */
    public void addDoors(Map<String, String> toAdd, Room currentRoom) {
        String[] dir = {"N", "S", "E", "W"};
        for (String string : dir) {
            int pos = Integer.parseInt(toAdd.get(string));
            currentRoom.addDoorHash(string, pos);
            if (pos != -1) {
                Door door = new Door();
                door.setDirection(string);
                door.setRoom(currentRoom);
                door.setPosition(pos);
                for (Room room : rooms) {
                    int conRoom = Integer.parseInt(toAdd.get(string + "connected"));
                    if (room.getId() == conRoom) {
                        door.connectRoom(room);
                    }
                }
                currentRoom.addDoor(door);
            }
        }
    }
    /**
     * Adds an item to the games array list of items.
     * @param toAdd
     */
    public void addItem(Map<String, String> toAdd) {
        Item currentItem = new Item();
        currentItem.setId(Integer.parseInt(toAdd.get("id")));
        currentItem.setName(toAdd.get("name"));
        currentItem.setType(toAdd.get("type"));
        currentItem.setDescription(toAdd.get("description"));
        String typeUpper = currentItem.getType().toUpperCase();
        currentItem.setDisplayCharacter(displaySymbols.get(typeUpper));
        if (toAdd.containsKey("room")) {
            int roomID = Integer.parseInt(toAdd.get("room"));
            for (Room room : rooms) {
                if (room.getId() == roomID) {
                    currentItem.setCurrentRoom(room);
                    int x = Integer.parseInt(toAdd.get("x"));
                    int y = Integer.parseInt(toAdd.get("y"));
                    Point point = new Point(x, y);
                    currentItem.setXyLocation(point);
                    try {
                        room.addItem(currentItem);
                    } catch (ImpossiblePositionException e) {
                        int newX = (int) Math.random() * (room.getWidth() - 1) + 1;
                        int newY = (int) Math.random() * (room.getHeight() - 1) + 1;
                        currentItem.getXyLocation().move(newX, newY);
                    }
                }
            }
        }
            if (currentItem.getType().equals("Food")) {
                Food food = new Food(currentItem.getId(), currentItem.getName(), "Food", currentItem.getXyLocation());
                this.food.add(food);
            } else if (currentItem.getType().equals("SmallFood")) {
                SmallFood food = new SmallFood(currentItem.getId(), currentItem.getName(), "SmallFood", currentItem.getXyLocation());
                this.smallFood.add(food);
            } else if (currentItem.getType().equals("Magic")) {
                Magic magic = new Magic(currentItem.getId(), currentItem.getName(), "Magic", currentItem.getXyLocation());
                this.magic.add(magic);
            } else if (currentItem.getType().equals("Ring")) {
                Ring ring = new Ring(currentItem.getId(), currentItem.getName(), "Ring", currentItem.getXyLocation());
                this.rings.add(ring);
            } else if (currentItem.getType().equals("Potion")) {
                Potion potion = new Potion(currentItem.getId(), currentItem.getName(), "Potion", currentItem.getXyLocation());
                this.potions.add(potion);
            } else if (currentItem.getType().equals("Clothing")) {
                Clothing clothing = new Clothing(currentItem.getId(), currentItem.getName(), "Clothing", currentItem.getXyLocation());
                this.clothes.add(clothing);
            }
        items.add(currentItem);
    }

    /**
     * Gets the symbols for the output.
     * @return displaySymbols
     */
    public HashMap<String, Character> getSymbols() {
        return displaySymbols;
    }

    /**
     * Gets the rooms in the game.
     * @return rooms
     */
    public ArrayList<Room> getRooms() {
        return this.rooms;

    }

    /**
     * Gets the items in the game.
     * @return items
     */
    public ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Gets the player in the game.
     * @return player
     */
    public Player getPlayer() {
        return player;

    }
    /**
     * Displays all the rooms in the game.
     * @return roomsDisplay
     */
    public String displayAll() {
        String roomsDisplay = new String();
        for (Room room : this.rooms) {
            roomsDisplay += room.displayRoom();
            roomsDisplay += "\n\n";
        }
        return roomsDisplay;
    }

    /**
     * Creates the display for the room adter the new move.
     * @return the next display of the room.
     */
    public String getNextDisplay() {
        String roomsDisplay = new String();
        for (Room room : this.rooms) {
            if (room.isPlayerInRoom()) {
                roomsDisplay += room.displayRoom();
                roomsDisplay += "\n\n";

            }
        }
        return roomsDisplay;
    }

    /**
     * Uses the player's Input to dictate the outcome of the next display.
     * @param input (keyboard input)
     * @return message to the player;
     * @throws InvalidMoveException (moving out of the room other than doors.)
     */
    public String makeMove(Character input) throws InvalidMoveException {
        int removal = 0;
        Item toRemove = new Item();
        Point newPt = player.getXyLocation();
        String message = "";
        int xChange = 0;
        int yChange = 0;
        int x = 0;
        int y = 0;
        if (input == UP) {
            yChange = 1;
        } else if (input == DOWN) {
            yChange = -1;
        } else if (input == RIGHT) {
            xChange = 1;
        } else if (input == LEFT) {
            xChange = -1;
        }
        x = (int) newPt.getX() + xChange;
        y = (int) newPt.getY() + yChange;
        Point toCheck = new Point(x, y);
        for (Room room : rooms) {
            if (room.isPlayerInRoom()) {
                for (Door door : room.getDoors()) {
                    if (toCheck.equals(door.getCoordinate())) {
                        return playerEntersDoor(newPt, room, door);
                    }
                } if (x > 0 && x < room.getWidth() - 1 && y > 0 && y < room.getHeight() - 1) {
                    movePlayer((Point) newPt, x, y, room);
                    ArrayList<Item> currentItems = room.getRoomItems();
                for (Item item: currentItems) {
                    if (item.getXyLocation().equals(player.getXyLocation())) {
                        removal = 1;
                        toRemove = item;
                        inventory.add(item);
                    }
                }
                if (removal == 1) {
                    currentItems.remove(toRemove);
                    room.setRoomItems(currentItems);
                    return "Picked up " + toRemove.getType();
                } else {
                    message = "the adventurer wonders.";
                }
            } else {
                    throw new InvalidMoveException("Cannot move out of bounds");
                }
            }
        }
        return message;
    }

    private void movePlayer(Point newPt, int x, int y, Room room) {
        newPt.move(x, y);
        player.setXyLocation(newPt);
        room.setPlayer(player);
    }


    private java.lang.String playerEntersDoor(Point newPt, Room room, Door door) {
        Room newRoom = door.getOtherRoom(room);
        room.setStart(false);
        newRoom.setStart(true);
        player.setCurrentRoom(newRoom);
        String direction = new String();
        Point doorPt = new Point();
        ArrayList<Door> doorCheck = newRoom.getDoors();
        for (Door loopDoor : doorCheck) {
            if (loopDoor.getOtherRoom(newRoom) == room) {
                System.out.println("door: " + loopDoor.getCoordinate());
                doorPt = loopDoor.getCoordinate();
                direction = loopDoor.getDirection();
            }
        }
        newPt.setLocation(doorPt);
        if (direction == "N") {
            newPt.translate(0, 1);
        } else if (direction == "S") {
            newPt.translate(0, -1);
        } else if (direction == "E") {
            newPt.translate(-1, 0);
        } else if (direction == "W") {
            newPt.translate(1, 0);

        }
        player.setXyLocation(newPt);
        System.out.println("player: " + player.getXyLocation());
        System.out.println("door changed: " + doorPt);
        newRoom.setPlayer(player);
        return "Player found a door";
    }

    public String[] getPlayerItems() {
        String[] itemNames = new String[inventory.size()];
        int i = 0;
        for (Item item: inventory) {
            itemNames[i] = item.getName();
            i++;
        }
        return itemNames;
    }

    public ArrayList<Item> getInventory() {
        return this.inventory;
    }

    public void removeInventoryItem(Item toRemove) {
        this.inventory.remove(toRemove);
    }


}
