package movement;

import objects.Character;
import processing.core.PVector;
import steering.SteeringOutput;

/**
 * Created by ujansengupta on 3/26/17.
 */
public class Align
{
    private static float timeToTargetRotation = 0.1f;

    public static SteeringOutput getSteering(Character character, PVector target, float maxRotation, float maxAngularAcceleration, float ROS, float ROD)
    {
        float targetRotation;
        SteeringOutput output = new SteeringOutput();

        PVector direction = new PVector(target.x - character.position.x, target.y - character.position.y);

        if (direction.mag() == 0)
            return new SteeringOutput();


        float targetOrientation = direction.heading();
        float rotation = Character.mapToRange(targetOrientation - character.orientation);
        float rotationSize = Math.abs(rotation);

        if (rotationSize < ROS)
            return new SteeringOutput();


        if (rotationSize > ROD)
            targetRotation = (rotation/rotationSize) * maxRotation;
        else
            targetRotation = (rotation/rotationSize) * rotationSize * maxRotation / ROD;


        output.angular = targetRotation - character.rotation;
        output.angular /= timeToTargetRotation;

        float angularAcc = Math.abs(output.angular);

        if (angularAcc > maxAngularAcceleration)
        {
            output.angular /= angularAcc;
            output.angular *= maxAngularAcceleration;
        }

        return output;
    }
}
