package rogue;
import java.awt.Point;

public class Magic extends Item {
    private int id;
    private String name;
    private String type;
    private Point xyLocation;
    public Magic() {
        super();
    }

    public Magic(int idP, String nameP, String typeP, Point xyLocationP) {
        super(idP, nameP, typeP, xyLocationP);
    }


}