package po.trafficsim.vehicle;

import javafx.scene.paint.Color;
import po.trafficsim.world.*;

/*
    Implementation of abstract Vehicle class
    Represents a cutting-edge super fast bike which is very fast but it can only ride straight through a junction
    When the road is a dead end it performs a multi-dimensional turnaround using it's super technology
    Basically a joke used to present possibilities of inheritance
 */

public class SuperFastBike extends Vehicle
{
    public SuperFastBike(Road road)
    {
        super(road);
        color = Color.RED;
        speed = 7.5;
    }

    protected void randomizeIntention()
    {
        intention = Intention.STRAIGHT;
    }

    public String toString()
    {
        return "SuperFastBike [x=" + posX + ",y=" + posY + "," + orientation + ",color=" + color + "," + halt + "," + intention + "]";
    }
}
