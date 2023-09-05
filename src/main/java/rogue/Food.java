package rogue;
import java.awt.Point;
import java.io.Serializable;

public class Food extends Item implements Edible, Serializable {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    public Food() {
        super();
    }

    public Food(int idP, String nameP, String typeP, Point xyLocationP) {
        super(idP, nameP, typeP, xyLocationP);
    }

    public String eat() {
        return this.name;
    }


}