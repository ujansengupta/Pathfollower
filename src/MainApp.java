import environment.Environment;
import environment.GraphSearch;
import implementations.*;
import implementations.BasicMotion;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class MainApp extends PApplet
{
    int scrWidth = 1200, scrHeight = 800;

    enum Mode {BASIC, ARRIVE, WANDER, FLOCKING}

    BasicMotion motion;
    WanderImplementation wander;
    FlockingImplementation flocking;
    ArriveImplementation arrive;

    Environment environment;
    GraphSearch search;
    Mode mode;

    public static void main(String[] args)
    {
        PApplet.main("MainApp", args);
    }

    public void settings()
    {
        size(scrWidth, scrHeight);

        mode = Mode.FLOCKING;
    }

    public void setup()
    {
        environment = new Environment(this, new PVector(scrWidth, scrHeight));

        frameRate(60);
        noLoop();
    }

    public void draw()
    {
        background(170);
        environment.drawGraph();

        search = new GraphSearch();

        ArrayList<Integer> path = search.dijkstraSearch(10, 321, environment.gameGraph.adjacencyList);

        for (int node : path)
        {
            System.out.println(node);
            environment.colorNode(node);
        }

        println(search.fill);



    }

    public void keyPressed()
    {
        if (keyCode == 32)
        {
            switch (flocking.state)                       // arrive.character.state || wander.character.state
            {
                case PAUSED:
                    restartAnimation();
                    break;
                case MOVING:
                    pauseAnimation();
                    break;
            }

        }
    }

    /* Uncomment for Arrive implementation*/

    public void mousePressed()
    {
        switch (mode)
        {
            case ARRIVE:
                arrive.changeTarget(new PVector(mouseX, mouseY));
                break;
            case FLOCKING:
                flocking.spawnLeader(new PVector(mouseX, mouseY));
                break;
        }
    }

    public void mouseDragged()
    {
        arrive.changeTarget(new PVector(mouseX, mouseY));
    }

    public void pauseAnimation()
    {

    }

    public void restartAnimation()
    {

    }

}
