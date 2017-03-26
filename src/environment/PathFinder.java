package environment;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.lang.Runtime;
import java.util.Collections;

/**
 * Created by ujansengupta on 3/21/17.
 */
public class PathFinder
{
    private GraphSearch search;
    private Environment environment;
    private PApplet app;
    private PVector numTiles;
    private ArrayList<Integer> path;
    private PVector prevStart, prevEnd;
    private int startIndex, endIndex;
    private GraphSearch.SEARCHMODE mode;
    private Graph graph;



    public PathFinder(PApplet theApplet, Environment environment)
    {
        this.search = new GraphSearch();
        this.app = theApplet;
        this.environment = environment;
        this.numTiles = environment.getNumTiles();
        this.graph = environment.gameGraph;
    }

    public ArrayList<Integer> findPath(PVector startNode, PVector endNode, GraphSearch.SEARCHMODE mode)
    {
        /*System.out.println("\n" + "Mode : " + mode + "\n");
        System.out.println("Start Node : " + startNode.x + ", " + startNode.y);
        System.out.println("End Node : " + endNode.x + ", " + endNode.y + "\n");*/

        startIndex = (int) (startNode.y * numTiles.y + startNode.x);
        endIndex = (int) (endNode.y * numTiles.y + endNode.x);

        if (graph.invalidNodes.contains(startIndex) || graph.invalidNodes.contains(endIndex))
            return path;

        this.prevStart = startNode;
        this.prevEnd = endNode;
        this.mode = mode;

        Runtime run = Runtime.getRuntime();

        long initialMemory = run.totalMemory() - run.freeMemory();
        //System.out.println("Initial Memory : " + initialMemory/Math.pow(10, 6) + " MB");
        int start = app.millis();

        switch (mode)
        {
            case DIJKSTRA:
                path = search.dijkstraSearch(startIndex, endIndex, environment.gameGraph);
                break;
            case ASTAR:
                path = search.aStarSearch(startIndex, endIndex, environment.gameGraph);
                break;

            default:
                path = search.aStarSearch(startIndex, endIndex, environment.gameGraph);
                break;
        }

        Collections.reverse(path);                                   // Since the path is returned in the reverse order

        int end = app.millis();
        long finalMemory = run.totalMemory() - run.freeMemory();

        /*System.out.println("Final Memory : " + finalMemory/Math.pow(10, 6) + " MB");
        System.out.println("Memory consumed : " + (finalMemory - initialMemory)/Math.pow(10, 3) + " kB");
        System.out.println("Time taken : " + (end - start) + " milliseconds");
        System.out.println("Fill : " + search.fill + " nodes");
        System.out.println("Path Length : " + path.size() + " nodes");


        System.out.println("Path : \n");

        for (int x : path)
            System.out.println(x);*/

        return path;
    }


    public void renderSearch()
    {
        if (path != null)
        {
            for (int node : search.getClosedList())
            {
                environment.colorNode(node, new PVector(71, 153 , 131));
            }

            for (int node : path)
            {
                environment.colorNode(node, new PVector(153, 71 , 97));
            }

            environment.colorNode(startIndex, new PVector(255, 0, 0));
            environment.colorNode(endIndex, new PVector(0, 255, 0));
        }
    }


}
