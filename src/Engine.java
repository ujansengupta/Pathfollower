import environment.Environment;
import environment.GraphSearch;
import environment.PathFinder;
import environment.PathFollower;
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
    PathFollower pathFollower;
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

        environment = new Environment(this, new PVector(scrWidth, scrHeight), NUM_TILES);
        pathFinder = new PathFinder(this, environment);

        pathFollower = new PathFollower(environment.getPlayer(), NUM_TILES, environment.getTileSize());

        startNode = environment.getPlayer().getGridLocation();
        endNode = new PVector(48, 48);


        pathFollower.followPath(pathFinder.findPath(startNode, endNode, searchMode));

        frameRate(60);
    }

    public void draw()
    {
        background(170);

        pathFinder.renderSearch();
        pathFollower.update();
        environment.update();

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
        /*if ((int)(mouseX/environment.getTileSize().x) == startNode.x && (int) (mouseY/environment.getTileSize().y) == startNode.y)
        {
            isStartMoving = true;
        }

        if ((int)(mouseX/environment.getTileSize().x) == endNode.x && (int) (mouseY/environment.getTileSize().y) == endNode.y)
        {
            isEndMoving = true;
        }*/

        if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
            targetChanged();

    }

    public void mouseDragged()
    {
        /*if (isStartMoving)
        {
            startNode = new PVector((int)(mouseX/environment.getTileSize().x), (int)(mouseY/environment.getTileSize().y));
            pathFinder.findPath(startNode, endNode, searchMode);
        }

        if (isEndMoving)
        {
            endNode = new PVector((int)(mouseX/environment.getTileSize().x), (int)(mouseY/environment.getTileSize().y));
            pathFinder.findPath(startNode, endNode, searchMode);
        }*/

        if (mouseX > 0 && mouseX < width && mouseY > 0 && mouseY < height)
            targetChanged();
    }

    public void mouseReleased()
    {
        isStartMoving = false;
        isEndMoving = false;
    }

    public void targetChanged()
    {
        endNode = new PVector((int)(mouseX/environment.getTileSize().x), (int)(mouseY/environment.getTileSize().y));
        startNode = environment.getPlayer().getGridLocation();

        pathFollower.changePath(pathFinder.findPath(startNode, endNode, searchMode));
    }

    public void pauseAnimation()
    {

    }

    public void restartAnimation()
    {

    }

}
