package rogue;
import java.awt.Point;
import java.io.Serializable;

public class SmallFood extends Food implements Tossable, Serializable {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    public SmallFood() {
        super();
    }

    public SmallFood(int idP, String nameP, String typeP, Point xyLocationP) {
        super(idP, nameP, typeP, xyLocationP);
    }

    public String eat() {
        return this.name;
    }

    public String toss() {
        return this.name;
    }
}