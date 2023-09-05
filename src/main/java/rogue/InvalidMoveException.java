package rogue;
public class InvalidMoveException extends Exception {
    /**
     * Default contructor.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * Contructor with error message.
     * @param message
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
