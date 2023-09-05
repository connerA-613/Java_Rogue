package rogue;
import java.awt.Point;
import java.io.Serializable;

public class Ring extends Magic implements Wearable, Serializable {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    public Ring() {
        super();
    }

    public Ring(int idP, String nameP, String typeP, Point xyLocationP) {
        super(idP, nameP, typeP, xyLocationP);
    }

    public String wear() {
        return this.name;
    }

}