package environment;

import objects.GameObject;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 3/12/17.
 */
public class Obstacle
{
    private static int cornerRadius = 10;
    private PVector centerPosition;
    private PVector color;
    private PVector corner;
    private ArrayList<PVector> tileLocations;


    public Obstacle(PVector upperLeft, PVector bottomRight, PVector tileSize, PVector color)
    {
        this.color = color;
        this.corner = upperLeft;

        tileLocations = new ArrayList<>();

        for (int i = (int)upperLeft.y; i < (int)bottomRight.y; i++)
        {
            for (int j = (int)upperLeft.x; j < (int)bottomRight.x; j++)
            {
                tileLocations.add(new PVector(j,i));
            }
        }

        centerPosition = new PVector((bottomRight.x - upperLeft.x)/2, (upperLeft.y - bottomRight.y)/2);
    }

    ArrayList<PVector> getTileLocations()
    {
        return tileLocations;
    }

    PVector getCenter()
    {
        return centerPosition;
    }
}
