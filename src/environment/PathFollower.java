package environment;

import movement.*;
import objects.Character;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by ujansengupta on 3/25/17.
 */
public class PathFollower
{
    private PVector numTiles;
    private PVector tileSize;

    private Character character;
    private ArrayList<PVector> path;
    private PVector currentTarget;
    private int targetIndex = 0;

    private float ROS = 3;
    private float maxVelocity = 3;

    public PathFollower(Character character, PVector numTiles, PVector tileSize)
    {
        this.character = character;
        this.numTiles = numTiles;
        this.tileSize = tileSize;
    }

    public int getTargetIndex()
    {
        return targetIndex;
    }

    public void followPath(ArrayList<Integer> path)
    {

        this.path = new ArrayList<>();

        for (int index : path)
        {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

        this.currentTarget = this.path.get(targetIndex);
    }

    public void changePath(ArrayList<Integer> path)
    {
        this.path.clear();
        targetIndex = 0;

        for (int index : path)
        {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

    }

    public void update()
    {
        character.velocity = Seek.getKinematic(character.position, currentTarget, maxVelocity).velocity ;//Arrive.getKinematic(character.position, currentTarget, maxVelocity, ROS).velocity;

        if (character.velocity.mag() == 0  && targetIndex < path.size() - 1)
        {
            targetIndex++;
            currentTarget = path.get(targetIndex);
        }

        character.orientation = character.getOrientation();
        character.move();
    }
}
