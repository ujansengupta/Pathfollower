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

    private float maxVelocity = 2f;
    private float maxRotation = 2 * (float) Math.PI, maxAngularAccel = 0.08f;  /* Kinematic maxRotation = 0.5f; Steering maxRotation = 2 * (float)Math.PI */
    private float ROS = 0.1f;
    private float ROD = 0.5f;

    private int pathOffset = 3;         //Actually 4. The +1 is to account for the 0 based indexing of the path.

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

    public void followPath(ArrayList<Integer> path) {

        this.path = new ArrayList<>();

        for (int index : path) {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

        this.currentTarget = this.path.get(targetIndex);
    }

    public void changePath(ArrayList<Integer> path)
    {
        this.path.clear();
        targetIndex = pathOffset;

        for (int index : path)
        {
            this.path.add(new PVector((index % numTiles.x) * tileSize.x + tileSize.x / 2, (index / numTiles.x) * tileSize.y + tileSize.y / 8));
        }

        this.currentTarget = this.path.get(targetIndex);

    }

    public void update()
    {
        character.velocity = Seek.getKinematic(character.position, currentTarget, maxVelocity).velocity;
        character.rotation = Align.getSteering(character, currentTarget, maxRotation, maxAngularAccel, ROS, ROD).angular;

        if (character.velocity.mag() == 0  && targetIndex < path.size() - 1)
        {
            targetIndex += pathOffset;

            if (targetIndex >= path.size())
                targetIndex = (path.size() - 1);

            currentTarget = path.get(targetIndex);
        }

        character.move();
    }
}
