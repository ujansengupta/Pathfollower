package implementations;

import helper.AnimationControls;
import movement.Wander;
import objects.Character;
import objects.Kinematic;
import processing.core.PApplet;
import processing.core.PVector;
import steering.KinematicOutput;
import steering.SteeringOutput;

@SuppressWarnings("WeakerAccess")

public class WanderImplementation implements AnimationControls
{
    public enum Mode {KINEMATIC, STEERING}

    PApplet app;
    int scrWidth, scrHeight;
    int wanderOffset = 5;

    float maxVelocity = 1f;
    float maxRotation, maxAngularAccel = 0.0005f;  /* Kinematic maxRotation = 0.5f; Steering maxRotation = 2 * (float)Math.PI */
    float ROS = 1.5f;
    float ROD = 2.5f;

    Character character;
    PVector targetPos, startPos;

    Mode mode;

    KinematicOutput kinematic;
    SteeringOutput steering;

    float targetRotation;

    public WanderImplementation(PApplet app, int scrWidth, int scrHeight, Character character, PVector tileSize)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;

        startPos = new PVector(scrWidth/2, scrHeight/2);
        this.character = (character == null) ? new Character(app, new PVector(startPos.x, startPos.y), tileSize) : character;
        this.character.initCrumbs();
        targetPos = new PVector(startPos.x, startPos.y);
        targetRotation = 0;

        mode = Mode.STEERING;                                   //Change between STEERING and KINEMATIC for different implementations

        start();
    }

    public void update()
    {
        if (character.state == Kinematic.State.MOVING)
        {
            switch (mode)
            {
                case KINEMATIC:
                    maxRotation = 0.5f;
                    kinematic = Wander.getKinematic(character, maxVelocity, maxRotation, wanderOffset);
                    character.checkBounds(scrWidth, scrHeight);
                    character.velocity = kinematic.velocity;
                    character.rotation = kinematic.rotation;
                    break;
                case STEERING:
                    maxRotation = 2 * (float)Math.PI;
                    steering = Wander.getSteeringAlign(character, targetRotation, maxRotation, maxAngularAccel, ROS, ROD);
                    character.checkBounds(scrWidth, scrHeight);
                    character.velocity = PVector.fromAngle(character.orientation).mult(maxVelocity);
                    character.rotation += steering.angular;
                    if (steering.angular == 0)
                        targetRotation = Wander.randomBinomial() * maxRotation;
                    break;
            }

            character.move();
        }

        character.drawShape();
        character.drawCrumbs(true);     //True for trail, False for regular crumbs
    }


    public Character getCharacter()
    {
        return character;
    }

    public void changeVelocity(float velocity)
    {
        maxVelocity = velocity;
    }

    public void changeAngularAcc(float angularAcc)
    {
        maxAngularAccel = angularAcc;
    }

    @Override
    public void start() {
        character.state = Kinematic.State.MOVING;
    }

    @Override
    public void pause() {
        app.noLoop();
        character.state = Kinematic.State.PAUSED;
    }

    @Override
    public void restart() {
        app.loop();
        character.state = Kinematic.State.MOVING;
    }

    @Override
    public Kinematic.State getState() {
        return character.state;
    }
}
