/*
 * File: Landscape.java
 * Author: Will Fitch
 * Date: 02/23/2020
 */

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Landscape {

    private boolean armed;
    private final int TILES_TO_ROW; //the number of tiles in a given row
    private Graph graph;

    private BufferedImage instructions1, instructions2armed, instructions2disarmed;

    private HuntTheWumpus HTW;

    private Wumpus w;
    private Hunter h;

    public Landscape( HuntTheWumpus HTW, int TILES_TO_ROW ) {

        this.TILES_TO_ROW = TILES_TO_ROW;
        this.HTW = HTW;

        graph = new Graph(TILES_TO_ROW);

        try {
            instructions1 = ImageIO.read(new File("src/instructions1.png"));
            instructions2armed = ImageIO.read(new File("src/instructions2armed.png"));
            instructions2disarmed = ImageIO.read(new File("src/instructions2disarmed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Vertex> temp = (ArrayList<Vertex>) graph.toArrayList().clone();
        w = new Wumpus( temp.remove( (int)(Math.random()*temp.size()) ) );
        h = new Hunter( temp.remove( (int)(Math.random()*temp.size()) ) );
        h.getLocation().setVisible(true);
        graph.shortestPath(w.getLocation());

    }

    public void inputNorth() {
        if(armed) {
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY()-1 == w.getLocation().getY()) {
                HTW.win();
            } else {
                HTW.lose();
            }
        } else {
            h.moveNorth();
            h.getLocation().setVisible(true);
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.lose();
            }
        }
    }

    public void inputEast() {
        if(armed) {
            if(h.getLocation().getX()+1 == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.win();
            } else {
                HTW.lose();
            }
        } else {
            h.moveEast();
            h.getLocation().setVisible(true);
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.lose();
            }
        }
    }

    public void inputSouth() {
        if(armed) {
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY()+1 == w.getLocation().getY()) {
                HTW.win();
            } else {
                HTW.lose();
            }
        } else {
            h.moveSouth();
            h.getLocation().setVisible(true);
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.lose();
            }
        }
    }

    public void inputWest() {
        if(armed) {
            if(h.getLocation().getX()-1 == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.win();
            } else {
                HTW.lose();
            }
        } else {
            h.moveWest();
            h.getLocation().setVisible(true);
            if(h.getLocation().getX() == w.getLocation().getX() && h.getLocation().getY() == w.getLocation().getY()) {
                HTW.lose();
            }
        }
    }

    public void toggleArmed() {
        armed = !armed;
    }

    public void draw(Graphics g, int scale) {

        graph.draw(g, scale);
        h.draw(g, scale);

        g.drawImage(instructions1, 0, scale*TILES_TO_ROW, (int)(scale*TILES_TO_ROW*(21/34.0)), scale,null);
        if(armed)
            //g.drawImage(instructions2armed, (int)(11+11/20.0)*scale, scale*TILES_TO_ROW, (int)(7+3/20.0)*scale, scale,null);
            g.drawImage(instructions2armed, (int)(scale*TILES_TO_ROW*(21/34.0)), scale*TILES_TO_ROW, (int)(scale*TILES_TO_ROW*(13/34.0)), scale,null);
        else
            //g.drawImage(instructions2disarmed, (int)(11+11/20.0)*scale, scale*TILES_TO_ROW, (int)(7+3/20.0)*scale, scale,null);
            g.drawImage(instructions2disarmed, (int)(scale*TILES_TO_ROW*(21/34.0)), scale*TILES_TO_ROW, (int)(scale*TILES_TO_ROW*(13/34.0)), scale,null);

    }

    public int tilesToRow() {
        return TILES_TO_ROW;
    }
}
