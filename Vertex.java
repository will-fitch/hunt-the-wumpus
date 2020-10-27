import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Vertex implements Comparable<Vertex> {

    private BufferedImage doorHorizontal, doorVertical, room, stench, shadow;

    private ArrayList<Vertex> adjacent;

    private int x;
    private int y;
    private boolean visible;

    private double cost;
    private boolean visited;

    public Vertex(int x, int y) {

        try {
            doorHorizontal = ImageIO.read(new File("src/doorHorizontal.png"));
            doorVertical = ImageIO.read(new File("src/doorVertical.png"));
            room = ImageIO.read(new File("src/room.png"));
            stench = ImageIO.read(new File("src/stench.png"));
            shadow = ImageIO.read(new File("src/shadow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adjacent = new ArrayList<>();
        this.x = x;
        this.y = y;
        cost = 1e+7;
        visited = false;

    }

    public double distance(Vertex other) {
        return Math.sqrt( (other.getX() - x)*(other.getX() - x) + (other.getY() - y)*(other.getY() - y) );

    }

    public void connect(Vertex other) {

        adjacent.add(other);

    }

    public ArrayList<Vertex> getNeighbors() {

        return adjacent;

    }

    public int numNeighbors() {

        return adjacent.size();

    }

    public void reset() {

        cost = 1e+7;
        visited = false;

    }

    public void draw(Graphics g, int scale) {

        if(!visible) {
            g.drawImage(shadow, scale*x, scale*y, scale, scale, null);
            return;
        }

        g.drawImage(room, scale*x, scale*y, scale, scale,null);

        for( Vertex v : adjacent ) {

            if(v.getX() == x+1 && v.getY() == y) {
                g.drawImage(doorVertical, scale*x+4*scale/5, scale*y, scale/5, scale,null);
            } else if(v.getX() == x && v.getY() == y+1) {
                g.drawImage(doorHorizontal, scale*x, scale*y+4*scale/5, scale, scale/5,null);
            } else if(v.getX() == x-1 && v.getY() == y) {
                g.drawImage(doorVertical, scale*x, scale*y, scale/5, scale,null);
            } else if(v.getX() == x && v.getY() == y-1) {
                g.drawImage(doorHorizontal, scale*x, scale*y, scale, scale/5,null);
            }

            if(cost <= 2) {
                g.drawImage(stench, scale*x+scale/5, scale*y, 7*scale/10, 7*scale/10,null);
            }

        }

    }

    public int compareTo(Vertex o) {
        return (int)(cost - o.getCost());
    }

    public String toString() {

        return "# Neighbors: " + numNeighbors() + "   Cost: " + cost + "   Visited: " + visited;

    }

    public void setVisible(boolean vis) { visible = vis; }


    public void setVisited(boolean visited) { this.visited = visited; }
    public boolean getVisited() { return visited; }

    public int getX() { return x; }

    public int getY() { return y; }

    public void setCost(double cost) { this.cost = cost; }
    public double getCost() { return cost; }

    public static void main(String[] args) {

        Vertex v1 = new Vertex(20,30);
        Vertex v2 = new Vertex(40,35);

        System.out.println(v1.distance(v2));
        System.out.println(v2.numNeighbors());
        v2.connect(v1);
        System.out.println(v2.numNeighbors());
        System.out.println(v2);

    }

}