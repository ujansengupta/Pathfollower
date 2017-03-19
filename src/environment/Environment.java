package environment;

import objects.Character;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.List;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 3/8/17.
 */

@SuppressWarnings("WeakerAccess")

public class Environment
{
    private static final PVector NUM_TILES = new PVector(50, 50);

    private PApplet app;
    private int width, height;
    private PVector tileSize;
    private PVector startPosition = new PVector(width/2, height/2);

    private List<Obstacle> obstacles;
    private List<Integer> invalidNodes;
    private Character player;
    public Graph gameGraph;

    public Environment(PApplet app, PVector resolution)
    {
        this.app = app;
        this.width = (int)resolution.x;
        this.height = (int)resolution.y;

        obstacles = new ArrayList<>();
        player = new Character(app, startPosition);

        createTiles();

        gameGraph = new Graph();
        gameGraph.buildGraph(new ArrayList<>(), NUM_TILES);

    }

    void update()
    {
        player.drawShape();
    }

    void createTiles()
    {
        tileSize = new PVector(width/NUM_TILES.x, height/NUM_TILES.y);
    }

    void createObstacles()
    {
        //obstacles.add(new Obstacle())
    }

    public void drawGraph()
    {
        for (int i = 0; i < NUM_TILES.y; i++)
        {
            for (int j = 0; j < NUM_TILES.x; j++)
            {
                app.rect(j * tileSize.x, (i) * tileSize.y, tileSize.x, tileSize.y);
            }
        }
    }

    public void colorNode(int index)
    {
        app.fill(0);
        app.rect((index % NUM_TILES.x) * tileSize.x, (float)Math.floor(index / NUM_TILES.y) * tileSize.y, tileSize.x, tileSize.y);
    }
}
