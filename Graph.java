/*
 * File: Graph.java
 * Author: Will Fitch
 * Date: 02/23/2020
 *
 */

import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph {

    Vertex[][] grid;

    public Graph(int size) {

        grid = new Vertex[size][size];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid.length; j++) {
                grid[i][j] = new Vertex(i, j);
            }
        }
        connectGrid();

    }

    private void connectGrid() {

        for(Vertex v : toArrayList()) {
            v.reset();
        }

        ArrayList<Vertex> connectedNeighbors;
        ArrayList<Vertex> disconnected = new ArrayList<>();

        Vertex current = grid[ (int)(Math.random()*grid.length) ][ (int)(Math.random()*grid.length) ];
        current.setVisited(true);

        if(current.getX() > 0) {
            disconnected.add( grid[current.getX()-1][current.getY()] );
        }
        if(current.getX()+1 < grid.length) {
            disconnected.add( grid[current.getX()+1][current.getY()] );
        }
        if(current.getY() > 0) {
            disconnected.add( grid[current.getX()][current.getY()-1] );
        }
        if(current.getY()+1 < grid.length) {
            disconnected.add( grid[current.getX()][current.getY()+1] );
        }


        while( !disconnected.isEmpty() ) {

            connectedNeighbors = new ArrayList<>();

            current = disconnected.remove( (int)(Math.random()*disconnected.size()) );
            current.setVisited(true);

            if(current.getX() > 0) {
                if(grid[current.getX()-1][current.getY()].getVisited()) {
                    connectedNeighbors.add(grid[current.getX()-1][current.getY()]);
                } else if (!disconnected.contains(grid[current.getX()-1][current.getY()])) {
                    disconnected.add( grid[current.getX()-1][current.getY()] );
                }
            }
            if(current.getX()+1 < grid.length) {
                if(grid[current.getX()+1][current.getY()].getVisited()) {
                    connectedNeighbors.add(grid[current.getX()+1][current.getY()]);
                } else if (!disconnected.contains(grid[current.getX()+1][current.getY()])) {
                    disconnected.add( grid[current.getX()+1][current.getY()] );
                }
            }
            if(current.getY() > 0) {
                if(grid[current.getX()][current.getY()-1].getVisited()) {
                    connectedNeighbors.add(grid[current.getX()][current.getY()-1]);
                } else if (!disconnected.contains(grid[current.getX()][current.getY()-1])) {
                    disconnected.add( grid[current.getX()][current.getY()-1] );
                }
            }
            if(current.getY()+1 < grid.length) {
                if(grid[current.getX()][current.getY()+1].getVisited()) {
                    connectedNeighbors.add(grid[current.getX()][current.getY()+1]);
                } else if (!disconnected.contains(grid[current.getX()][current.getY()+1])){
                    disconnected.add( grid[current.getX()][current.getY()+1] );
                }
            }

            for(int i = (int)(Math.random()*connectedNeighbors.size()); i >= 0; i--) {

                addEdge( current, connectedNeighbors.remove( (int)(Math.random()*connectedNeighbors.size()) ) );

            }

        }
    }

    private void addEdge(Vertex v1, Vertex v2) {

        v1.connect(v2);
        v2.connect(v1);

    }

    public ArrayList<Vertex> toArrayList() {

        ArrayList<Vertex> returnList = new ArrayList<>();

        for( Vertex[] vertArr : grid) {
            for( Vertex vert : vertArr ) {
                returnList.add( vert );
            }
        }

        return returnList;

    }

    public void draw(Graphics g, int scale) {

        for(Vertex[] vertArr : grid) {
            for(Vertex vert : vertArr) {

                vert.draw(g, scale);

            }
        }

    }

    public String toString() {

        String returnString = "";

        for(Vertex[] vertArr : grid) {
            for(Vertex vert : vertArr) {
                returnString += vert.numNeighbors() + " ";
            }
            returnString += "\n";
        }

        return returnString;

    }

    public void shortestPath(Vertex v) {
        shortestPath(v.getX(), v.getY());
    }

    public void shortestPath(int x, int y) {

        if( x < 0 || x >= grid.length || y < 0 || y >= grid.length) {
            return;
        }

        PriorityQueue<Vertex> pq = new PriorityQueue<>();

        for(Vertex v : toArrayList()) {
            v.reset();
        }

        grid[x][y].setCost(0);
        pq.add(grid[x][y]);

        while( !pq.isEmpty() ) {

            Vertex v = pq.remove();

            if(!v.getVisited()) {
                v.setVisited(true);

                for(Vertex n : v.getNeighbors()) {
                    if( !n.getVisited() && v.getCost() + 1 < n.getCost() ) {
                        n.setCost( v.getCost() + 1 );
                        pq.add(n);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {

        Graph g = new Graph(10);

        System.out.println( g );

    }

}
