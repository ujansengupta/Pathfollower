package implementations;

import helper.AnimationControls;
import movement.Arrive;
import objects.Kinematic;
import objects.Character;
import processing.core.PApplet;
import processing.core.PVector;

@SuppressWarnings("WeakerAccess")

public class ArriveImplementation implements AnimationControls
{
    enum Mode {KINEMATIC, STEERING}

    PApplet app;
    int scrWidth, scrHeight;

    float maxVelocity = 3, maxAcceleration = 0.5f;              // Keep acceleration low and velocity relatively high to see the effect of acceleration
    float ROS = 3;
    float ROD = 30;

    Character character;
    PVector targetPos, startPos;

    Mode mode;


    public ArriveImplementation(PApplet app, int scrWidth, int scrHeight, PVector tileSize)
    {
        this.app = app;
        this.scrWidth = scrWidth;
        this.scrHeight = scrHeight;

        startPos = new PVector(scrWidth/2, scrHeight/2);
        character = new Character(app, new PVector(startPos.x, startPos.y), tileSize);
        character.initCrumbs();
        targetPos = new PVector(startPos.x, startPos.y);

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
                    character.velocity = Arrive.getKinematic(character.position, targetPos, maxVelocity, ROS).velocity;
                    break;
                case STEERING:
                    character.velocity.add(Arrive.getSteering(character, targetPos, maxVelocity, maxAcceleration, ROS, ROD).linear);
                    break;
            }

            character.orientation = character.getOrientation();
            character.move();
        }

        character.drawShape();
        character.drawCrumbs(true);                    //True for trail, False for regular crumbs
    }

    public void changeTarget(PVector target)
    {
        this.targetPos = target;
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
