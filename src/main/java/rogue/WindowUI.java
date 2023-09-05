package rogue;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.TerminalPosition;

import javax.swing.JFrame;
import java.awt.Container;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.io.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;

import java.util.ArrayList;

public class WindowUI extends JFrame {


    private SwingTerminal terminal;
    private TerminalScreen screen;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 1000;
    // Screen buffer dimensions are different than terminal dimensions
    public static final int COLS = 80;
    public static final int ROWS = 24;
   private final char startCol = 0;
   private final char msgRow = 1;
   private final char roomRow = 3;
   private Container contentPane;
   private static JList invList = new JList();
   private static JTextField dataEntry = new JTextField("" ,25);
   private static JButton clickMe = new JButton("Submit text");
   private static JLabel textBoxLabel = new JLabel("");
   private static String textBoxString = new String();
   private static JLabel messageLabel = new JLabel();

/**
Constructor.
**/

    public WindowUI() {
        super("my awesome game");
        contentPane = getContentPane();
        setWindowDefaults(getContentPane());
        setUpPanels();
        pack();
        start();
    }

    private void setWindowDefaults(Container contentPane) {
        setTitle("Rogue!");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contentPane.setLayout(new BorderLayout());

    }

    private void setTerminal() {
        JPanel terminalPanel = new JPanel();
        terminal = new SwingTerminal();
        terminalPanel.add(terminal);
        contentPane.add(terminalPanel,BorderLayout.CENTER);
    }

    private void setUpPanels(){
        JPanel labelPanel = new JPanel();
        JPanel invPanel = new JPanel();
        JPanel messagePanel = new JPanel();
        setUpSidePanel(invPanel);
        setUpTopPanel(messagePanel);
        setTerminal();
    }

    private void setUpLabelPanel(JPanel thePanel){
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        thePanel.add(textBoxLabel);
        thePanel.add(dataEntry);
        dataEntry.addActionListener(a->textBoxReturn());
        thePanel.add(clickMe);
        contentPane.add(thePanel, BorderLayout.SOUTH);
    }

    private void setUpSidePanel(JPanel thePanel) {
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        JLabel invLabel = new JLabel("Inventory:");
        thePanel.add(invLabel);
        thePanel.add(invList);
        contentPane.add(thePanel, BorderLayout.EAST);
    }

    private void setUpTopPanel(JPanel thePanel) {
        Border prettyLine = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        thePanel.setBorder(prettyLine);
        thePanel.add(messageLabel);
        contentPane.add(messageLabel, BorderLayout.NORTH);
    }

    private void textBoxReturn() {
        textBoxString = dataEntry.getText();
    }

    private void start() {
        try {
            screen = new TerminalScreen(terminal);
            screen.setCursorPosition(TerminalPosition.TOP_LEFT_CORNER);
            screen.startScreen();
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
Prints a string to the screen starting at the indicated column and row.
@param toDisplay the string to be printed
@param column the column in which to start the display
@param row the row in which to start the display
**/
        public void putString(String toDisplay, int column, int row) {

            Terminal t = screen.getTerminal();
            try {
                t.setCursorPosition(column, row);
            for (char ch: toDisplay.toCharArray()) {
                t.putCharacter(ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        }

/**
Changes the message at the top of the screen for the user.
@param msg the message to be displayed
**/
            public void setMessage(String msg) {
                putString("                                                ", 1, 1);
                putString(msg, startCol, msgRow);
            }

/**
Redraws the whole screen including the room and the message.
@param message the message to be displayed at the top of the room
@param room the room map to be drawn
**/
            public void draw(String message, String room) {

                try {
                    terminal.clearScreen();
                    setMessage(message);
                    putString(room, startCol, roomRow);
                    screen.refresh();
                } catch (IOException e) {

                }

        }

/**
Obtains input from the user and returns it as a char.  Converts arrow
keys to the equivalent movement keys in rogue.
@return the ascii value of the key pressed by the user
**/
        public char getInput() {
            KeyStroke keyStroke = null;
            char returnChar;
            while (keyStroke == null) {
            try {
                keyStroke = screen.pollInput();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
         if (keyStroke.getKeyType() == KeyType.ArrowDown) {
            returnChar = Rogue.DOWN;  //constant defined in rogue
        } else if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            returnChar = Rogue.UP;
        } else if (keyStroke.getKeyType() == KeyType.ArrowLeft) {
            returnChar = Rogue.LEFT;
        } else if (keyStroke.getKeyType() == KeyType.ArrowRight) {
            returnChar = Rogue.RIGHT;
        } else {
            returnChar = keyStroke.getCharacter();
        }
        return returnChar;
    }

    public static void save(Rogue rogue) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("save.ser");
        ObjectOutputStream objectOutputStream  = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(rogue);
        objectOutputStream.flush();
        objectOutputStream.close();
    }

    public static Rogue load() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("save.ser");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Rogue rogue= (Rogue) objectInputStream.readObject();
        objectInputStream.close();

        return rogue;
    }

/**
The controller method for making the game logic work.
@param args command line parameters
**/
    public static void main(String[] args) {

       char userInput = 'h';
    String message;
    String configurationFileLocation = "fileLocations.json";
    //Parse the json files
    RogueParser parser = new RogueParser(configurationFileLocation);
    //allocate memory for the GUI
    WindowUI theGameUI = new WindowUI();
    // allocate memory for the game and set it up
    Rogue theGame = new Rogue(parser);
   //set up the initial game display
    Player thePlayer = new Player("Judi");
    theGame.setPlayer(thePlayer);
    message = "Welcome to my Rogue game";
    theGameUI.draw(message, theGame.getNextDisplay());
    theGameUI.setVisible(true);

    while (userInput != 'q') {
    //get input from the user
    userInput = theGameUI.getInput();
        invList.setListData(theGame.getPlayerItems());
    if (userInput == 'y') {
        try {
            save(theGame);
        } catch (IOException e) {

        }
    } if (userInput == 'l') {
        try {
            theGame = load();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    if (userInput == 'e') {
        textBoxString = JOptionPane.showInputDialog(null, "What would you like to eat? (type an item from invenory)");
        System.out.println(textBoxString);
        String[] edibleTypes = {"Food", "SmallFood", "Potion"};
        boolean checkForItem = false;
        boolean checkEdible = false;
        Item toRemove = new Item();
        for (Item item : theGame.getInventory()) {
            if (item.getName().equals(textBoxString)) {
                checkForItem = true;
                toRemove = item;
            }
        } if (checkForItem) {
            for (int i = 0; i < edibleTypes.length; i++){
                if (edibleTypes[i].equals(toRemove.getType())) {
                    theGame.removeInventoryItem(toRemove);
                    checkEdible = true;
                    invList.setListData(theGame.getPlayerItems());
                    messageLabel.setText(theGame.getPlayer().getName() + " ate " + toRemove.getName());
                }
            } if (!checkEdible) {
                JOptionPane.showMessageDialog(null, "This item is not edible");
            }
        } else {
            JOptionPane.showMessageDialog(null, "This item is not in your inventory");
        }
    } if (userInput == 'w') {
            textBoxString = JOptionPane.showInputDialog(null, "What would you like to wear? (type an item from invenory)");
            System.out.println(textBoxString);
            String[] wearableTypes = {"Clothing", "Ring"};
            boolean checkForItem = false;
            boolean checkWearable = false;
            Item toRemove = new Item();
            for (Item item : theGame.getInventory()) {
                if (item.getName().equals(textBoxString)) {
                    checkForItem = true;
                    toRemove = item;
                }
            } if (checkForItem) {
                for (int i = 0; i < wearableTypes.length; i++){
                    if (wearableTypes[i].equals(toRemove.getType())) {
                        messageLabel.setText(theGame.getPlayer().getName() + " equipped " + toRemove.getName());
                        String newName = toRemove.getName() + " (equipped)";
                        toRemove.setName(newName);
                        checkWearable = true;
                        invList.setListData(theGame.getPlayerItems());
                    }
                } if (!checkWearable) {
                    JOptionPane.showMessageDialog(null, "This item is not Wearable");
                }
            } else {
                JOptionPane.showMessageDialog(null, "This item is not in your inventory");
            }
        } if (userInput == 't') {
            textBoxString = JOptionPane.showInputDialog(null, "What would you like to toss? (type an item from invenory)");
            System.out.println(textBoxString);
            String[] tossableTypes = {"SmallFood", "Potion"};
            boolean checkForItem = false;
            boolean checkTossable = false;
            Item toRemove = new Item();
            for (Item item : theGame.getInventory()) {
                if (item.getName().equals(textBoxString)) {
                    checkForItem = true;
                    toRemove = item;
                }
            } if (checkForItem) {
                for (int i = 0; i < tossableTypes.length; i++){
                    if (tossableTypes[i].equals(toRemove.getType())) {
                        theGame.removeInventoryItem(toRemove);
                        checkTossable = true;
                        invList.setListData(theGame.getPlayerItems());
                        messageLabel.setText(theGame.getPlayer().getName() + " tossed " + toRemove.getName());
                    }
                } if (!checkTossable) {
                    JOptionPane.showMessageDialog(null, "This item is not Tossable");
                }
            } else {
                JOptionPane.showMessageDialog(null, "This item is not in your inventory");
            }
        }
    //ask the game if the user can move there
    try {
        message = theGame.makeMove(userInput);
        theGameUI.draw(message, theGame.getNextDisplay());
    } catch (InvalidMoveException badMove) {
        message = "I didn't understand what you meant, please enter a command";
        theGameUI.setMessage(message);
    }
    }


    }

}
