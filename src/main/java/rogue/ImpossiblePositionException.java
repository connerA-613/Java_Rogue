package rogue;
public class ImpossiblePositionException extends Exception {
    /**
     * Default constructor.
     */
    public ImpossiblePositionException() {
        super();
    }
    /**
     * Constructor with error message.
     * @param message
     */
    public ImpossiblePositionException(String message) {
        super(message);
    }
}
