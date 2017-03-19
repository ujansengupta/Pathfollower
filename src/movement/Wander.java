package movement;

import processing.core.PVector;
import steering.*;
import objects.Character;

import java.util.Random;


@SuppressWarnings("WeakerAccess")

public class Wander
{
    static Random rand;
    static float timeToTargetRotation = 10f;

    public static KinematicOutput getKinematic(Character character, float maxVelocity, float maxRotation, int wanderOffset)
    {
        KinematicOutput output = new KinematicOutput();

        output.velocity = PVector.fromAngle(character.orientation).mult(maxVelocity);
        output.rotation = randomBinomial()*maxRotation;

        return output;
    }

    public static SteeringOutput getSteeringAlign(Character character, float targetRot, float maxRotation, float maxAccel, float ROS, float ROD)
    {
        SteeringOutput steering = new SteeringOutput();

        float rotation = targetRot - character.orientation;

        rotation = Character.mapToRange(rotation);
        float rotationSize = Math.abs(rotation);

        if (rotationSize < ROS)
        {
            character.rotation = 0;
            return new SteeringOutput();
        }

        if (rotationSize > ROD)
            targetRot = (rotation/rotationSize) * maxRotation;
        else
            targetRot = (rotation/rotationSize) * rotationSize * maxRotation / ROD;


        steering.angular = targetRot - character.rotation;
        steering.angular /= timeToTargetRotation;

        float angularAcc = Math.abs(steering.angular);

        if (angularAcc > maxAccel)
        {
            steering.angular /= angularAcc;
            steering.angular *= maxAccel;
        }

        return steering;
    }

    public static int randInt(int min, int max)
    {
        rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    public static float randomBinomial()
    {
        return (float)(Math.random() - Math.random());
    }
}
