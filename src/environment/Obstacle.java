package environment;

import objects.GameObject;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 3/12/17.
 */
public class Obstacle
{
    private static int cornerRadius = 10;
    private PVector centerPosition;
    private ArrayList<PVector> tileLocations;
    private Set<Integer> tileIndex;

    public PVector color;
    public PVector corner;
    public PVector size;

    private PApplet app;

    public Obstacle(PApplet theApplet, PVector upperLeft, PVector size, PVector color, PVector numTiles)
    {
        this.app = theApplet;
        this.color = color;
        this.corner = upperLeft;
        this.size = size;

        tileLocations = new ArrayList<>();
        tileIndex = new HashSet<>();

        for (int i = (int)corner.y; i < (int)corner.y + size.y; i++)
        {
            for (int j = (int)corner.x; j < (int)corner.x + size.x; j++)
            {
                tileLocations.add(new PVector(j,i));
                tileIndex.add(i * (int)numTiles.y + j);
            }
        }

        centerPosition = new PVector(corner.x + size.x/2, corner.y + size.y/2);
    }

    public void draw(PVector tileSize)
    {
        app.fill(color.x, color.y, color.z);
        app.rect(((int)corner.x + 1)* tileSize.x, ((int)corner.y + 1) * tileSize.y, (size.x - 2) * tileSize.x, (size.y - 2) * tileSize.y, cornerRadius);
    }


    ArrayList<PVector> getTileLocations()
    {
        return tileLocations;
    }

    Set<Integer> getTileIndex()
    {
        return tileIndex;
    }

    PVector getCenter()
    {
        return centerPosition;
    }

    PVector getColor()
    {
        return color;
    }
}
