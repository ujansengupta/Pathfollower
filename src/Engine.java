import environment.Environment;
import environment.GraphSearch;
import environment.PathFinder;
import processing.core.PApplet;
import processing.core.PVector;


/**
 * Created by ujansengupta on 2/14/17.
 */

@SuppressWarnings("WeakerAccess")

public class Engine extends PApplet
{
    public static PVector NUM_TILES;
    public static int scrWidth = 1200;
    public static int scrHeight = 800;

    Environment environment;
    PathFinder pathFinder;
    PVector startNode;
    PVector endNode;
    boolean isStartMoving = false;
    boolean isEndMoving = false;

    GraphSearch.SEARCHMODE searchMode = GraphSearch.SEARCHMODE.ASTAR;


    public static void main(String[] args)
    {
        PApplet.main("Engine", args);
    }

    public void settings()
    {
        size(scrWidth, scrHeight);
    }

    public void setup()
    {
        NUM_TILES = new PVector(50, 50);
        startNode = new PVector(5, 5);
        endNode = new PVector(48, 48);

        environment = new Environment(this, new PVector(scrWidth, scrHeight), NUM_TILES);
        pathFinder = new PathFinder(this, environment);
        pathFinder.findPath(startNode, endNode, searchMode);


        frameRate(60);
        //noLoop();
    }

    public void draw()
    {
        background(170);

        environment.update();
        pathFinder.renderSearch();

    }

    public void keyPressed()
    {
       /* if (keyCode == 32)
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

        }*/
    }

    /* Uncomment for Arrive implementation*/

    public void mousePressed()
    {
   /*     System.out.println("Mouse X = " + (mouseX/environment.getTileSize().x));
        System.out.println("Mouse Y = " + (mouseY/environment.getTileSize().y));*/

        if ((int)(mouseX/environment.getTileSize().x) == startNode.x && (int) (mouseY/environment.getTileSize().y) == startNode.y)
        {
            isStartMoving = true;
        }

        if ((int)(mouseX/environment.getTileSize().x) == endNode.x && (int) (mouseY/environment.getTileSize().y) == endNode.y)
        {
            isEndMoving = true;
        }


    }

    public void mouseDragged()
    {
        if (isStartMoving)
        {
            startNode = new PVector((int)(mouseX/environment.getTileSize().x), (int)(mouseY/environment.getTileSize().y));
            pathFinder.findPath(startNode, endNode, searchMode);
        }

        if (isEndMoving)
        {
            endNode = new PVector((int)(mouseX/environment.getTileSize().x), (int)(mouseY/environment.getTileSize().y));
            pathFinder.findPath(startNode, endNode, searchMode);
        }
    }

    public void mouseReleased()
    {
        isStartMoving = false;
        isEndMoving = false;
    }

    public void pauseAnimation()
    {

    }

    public void restartAnimation()
    {

    }

}
