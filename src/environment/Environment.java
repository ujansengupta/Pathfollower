package environment;

import objects.Character;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 3/8/17.
 */

@SuppressWarnings("WeakerAccess")

public class Environment
{

    private PApplet app;
    private int width, height;
    private PVector tileSize;
    private PVector NUM_TILES;
    private PVector startPosition = new PVector(width/2, height/2);

    private List<Obstacle> obstacles;
    private Set<Integer> invalidNodes;
    private Character player;
    public Graph gameGraph;

    public Environment(PApplet app, PVector resolution, PVector numTiles)
    {
        this.app = app;
        this.width = (int)resolution.x;
        this.height = (int)resolution.y;
        this.NUM_TILES = numTiles;

        invalidNodes = new HashSet<>();
        obstacles = new ArrayList<>();
        player = new Character(app, startPosition);

        createTiles();
        createObstacles();

        gameGraph = new Graph();
        gameGraph.buildGraph(invalidNodes, NUM_TILES);

    }

    public void update()
    {
        drawGraph();
        //player.drawShape();
    }



    public PVector getTileSize()
    {
        return tileSize;
    }

    public PVector getNumTiles()
    {
        return NUM_TILES;
    }


    public void drawGraph()
    {
        for (int i = 0; i < NUM_TILES.y; i++)
        {
            for (int j = 0; j < NUM_TILES.x; j++) {
                app.rect(j * tileSize.x, (i) * tileSize.y, tileSize.x, tileSize.y);
            }
        }

        for (Obstacle obstacle : obstacles)
        {
            obstacle.draw(tileSize);
        }
    }

    void createObstacles()
    {
        PVector obstacleColor = new PVector(123, 116, 214);
        obstacles.add(new Obstacle(this.app, new PVector(0.25f * NUM_TILES.x, 0.25f * NUM_TILES.y), new PVector(10, 10), obstacleColor, NUM_TILES));
        obstacles.add(new Obstacle(this.app, new PVector(0.50f * NUM_TILES.x, 0.50f * NUM_TILES.y), new PVector(10, 10), obstacleColor, NUM_TILES));


        for (Obstacle obstacle : obstacles)
            invalidNodes.addAll(obstacle.getTileIndex());
    }

    void createTiles()
    {
        tileSize = new PVector(width/NUM_TILES.x, height/NUM_TILES.y);
    }

    public void colorNode(int index, PVector color)
    {
        app.fill(color.x, color.y, color.z);
        app.rect((index % NUM_TILES.x) * tileSize.x, (float)Math.floor(index / NUM_TILES.y) * tileSize.y, tileSize.x, tileSize.y);
        app.noFill();
    }
}
