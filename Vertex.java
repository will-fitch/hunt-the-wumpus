/*
 * File: Vertex.java
 * Author: Will Fitch
 * Date: 02/23/2020
 *
 * The Vertex is a single tile in the game. It holds its neighbors, location, information about whether it's visible.
 * It also has data necessary for path finding, specifically the visited and cost variables.
 *
 * Additionally, it has a draw function which allows the room to be drawn with the correct orientation of doors.
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {

    private BufferedImage doorHorizontal, doorVertical, room, stench, shadow; //all the image variables needed for drawing the room

    private ArrayList<Vertex> adjacent; //list of adjacent vertices

    private int x, y; //the x and y position of the Vertex
    private boolean visible, visited; //visible is whether the room is drawn in shadow. Visited is whether the pathing algorithm has visited the vertex.
    private double cost; //the cost for the pathing algorithm.

    public Vertex(int x, int y) {

        //read the images into memory
        try {
            doorHorizontal = ImageIO.read(new File("src/doorHorizontal.png"));
            doorVertical = ImageIO.read(new File("src/doorVertical.png"));
            room = ImageIO.read(new File("src/room.png"));
            stench = ImageIO.read(new File("src/stench.png"));
            shadow = ImageIO.read(new File("src/shadow.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        adjacent = new ArrayList<>(); //instantiate adjacent ArrayList
        this.x = x; //sets x position
        this.y = y; //sets y position
        cost = 1e+7; //sets cost to be very high
        visited = false; //sets visited to false

    }

    //returns the distance between this vertex and the other passed in
    public double distance(Vertex other) {
        return Math.sqrt( (other.getX() - x)*(other.getX() - x) + (other.getY() - y)*(other.getY() - y) ); //calculates distance between two vertices
        //NOTE: using Math.sqrt is a little over excessive for the grid application, but it would be necessary if the rooms weren't in a strict grid
    }

    //adds a given vertex to the adjacent list
    public void connect(Vertex other) {
        adjacent.add(other);
    }

    //returns the adjacent list
    public ArrayList<Vertex> getNeighbors() {

        return adjacent;

    }

    //returns the number of adjacent vertices
    public int numNeighbors() {

        return adjacent.size();

    }

    //resets the information regarding the pathing algorithm
    public void reset() {

        cost = 1e+7;
        visited = false;

    }

    //draws the vertex as a room on the screen
    //int scale: the size of one edge of the square room
    public void draw(Graphics g, int scale) {

        //if the room is not visible, draws it as a shadow:
        if(!visible) {
            g.drawImage(shadow, scale*x, scale*y, scale, scale, null);
            return;
        }

        //draws the room
        g.drawImage(room, scale*x, scale*y, scale, scale,null);

        //draws a door for each adjacent room
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



        }

        //draws the stench effect if the tile is 2 or fewer tiles from the Wumpus
        if(cost <= 2) {
            g.drawImage(stench, scale*x+scale/5, scale*y, 7*scale/10, 7*scale/10,null);
        }

    }

    //returns the difference in cost between this and a given vertex
    public int compareTo(Vertex o) {
        return (int)(cost - o.getCost());
    }

    //returns some information about the vertex as a String
    public String toString() {

        return "# Neighbors: " + numNeighbors() + "   Cost: " + cost + "   Visited: " + visited;

    }

//↓↓↓_______________ Get/Set Methods ______________↓↓↓\\

    //sets the visible variable of the vertex
    public void setVisible(boolean vis) { visible = vis; }

    //sets the visited variable of the vertex
    public void setVisited(boolean visited) { this.visited = visited; }

    //returns the visited varaible of the vertex
    public boolean getVisited() { return visited; }

    //returns the x position
    public int getX() { return x; }

    //returns the y position
    public int getY() { return y; }

    //sets the cost
    public void setCost(double cost) { this.cost = cost; }

    //returns the cost
    public double getCost() { return cost; }

//↑↑↑_______________ End Get/Set _______________↑↑↑\\

    //main function, for testing
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
