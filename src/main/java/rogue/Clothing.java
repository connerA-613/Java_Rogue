package rogue;
import java.awt.Point;

public class Clothing extends Item implements Wearable {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    public Clothing() {
        super();
    }

    public Clothing(int idP, String nameP, String typeP, Point xyLocationP) {
        super(idP, nameP, typeP, xyLocationP);
    }

    public String wear() {
        return this.name;
    }


}