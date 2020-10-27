import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Hunter extends Character {

    private BufferedImage hunter;

    public Hunter(Vertex location) {

        super(location);

        try {
            hunter = ImageIO.read(new File("src/hunter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void moveNorth() {
        for(Vertex v : location.getNeighbors()) {
            if(v.getX() == location.getX() && v.getY() == location.getY()-1) {
                location = v;
                break;
            }
        }
    }
    public void moveEast() {
        for(Vertex v : location.getNeighbors()) {
            if(v.getX() == location.getX()+1 && v.getY() == location.getY()) {
                location = v;
                break;
            }
        }
    }
    public void moveSouth() {
        for(Vertex v : location.getNeighbors()) {
            if(v.getX() == location.getX() && v.getY() == location.getY()+1) {
                location = v;
                break;
            }
        }
    }
    public void moveWest() {
        for(Vertex v : location.getNeighbors()) {
            if(v.getX() == location.getX()-1 && v.getY() == location.getY()) {
                location = v;
                break;
            }
        }
    }

    public void draw(Graphics g, int scale) {

        g.drawImage(hunter, scale*location.getX(), scale*location.getY(), scale, scale, null);

    }

}
