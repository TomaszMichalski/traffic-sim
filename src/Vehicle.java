import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;

/*
    Abstract class for the concrete implementations of vehicle entities to inherit from.
    Contains some basic calculation-methods which are common for every vehicle entity
 */

public abstract class Vehicle
{
    public Vehicle(Road road)
    {
        currentRoad = road;
        //default position
        posX = road.getRoadLine().getStartX();
        posY = road.getRoadLine().getStartY();
        //default halt status
        halt = false;
        //default entity color
        color = Color.WHITE;
        //default speed
        speed = 0;
    }

    protected void move()
    {
        if(!halt)
        {
            //if the car is closing to a junction, perform a turn-action
            if(getIncomingJunctionDistance() <= speed)
                makeTurn();
            //drive forward
            if(orientation == VehicleOrientation.VO_NORTH)
                posY -= speed;
            else if(orientation == VehicleOrientation.VO_EAST)
                posX += speed;
            else if(orientation == VehicleOrientation.VO_SOUTH)
                posY += speed;
            else if(orientation == VehicleOrientation.VO_WEST)
                posX -= speed;
        }
    }

    /*
        Performs an action based on the car'c current Intention value
        If intended action cannot be performed, car changes it's intention and tries to perform the turn again
     */
    protected void makeTurn()
    {
        Junction incomingJunction = getIncomingJunction();
        if(intention == Intention.STRAIGHT)
        {
            if(incomingJunction.getStraight(currentRoad) != null) //if there is such road
            {
                Road next = incomingJunction.getStraight(currentRoad);
                currentRoad = next;
                randomizeIntention();
            }
            else
            {
                if(incomingJunction.getSize() == 1) //if the road is a dead end
                {
                    //perform a turnaround
                    if(orientation == VehicleOrientation.VO_NORTH) orientation = VehicleOrientation.VO_SOUTH;
                    else if(orientation == VehicleOrientation.VO_SOUTH) orientation = VehicleOrientation.VO_NORTH;
                    else if(orientation == VehicleOrientation.VO_WEST) orientation = VehicleOrientation.VO_EAST;
                    else if(orientation == VehicleOrientation.VO_EAST) orientation = VehicleOrientation.VO_WEST;
                    randomizeIntention();
                }
                else
                {
                    randomizeIntention();
                    makeTurn();
                }
            }
        }
        else if(intention == Intention.LEFT)
        {
            if(incomingJunction.getLeft(currentRoad) != null) //if there is such road
            {
                Road next = incomingJunction.getLeft(currentRoad);
                currentRoad = next;
                if(orientation == VehicleOrientation.VO_NORTH) orientation = VehicleOrientation.VO_WEST;
                else if(orientation == VehicleOrientation.VO_SOUTH) orientation = VehicleOrientation.VO_EAST;
                else if(orientation == VehicleOrientation.VO_WEST) orientation = VehicleOrientation.VO_SOUTH;
                else if(orientation == VehicleOrientation.VO_EAST) orientation = VehicleOrientation.VO_NORTH;
                randomizeIntention();
            }
            else
            {
                randomizeIntention();
                makeTurn();
            }
        }
        else if(intention == Intention.RIGHT)
        {
            if(incomingJunction.getRight(currentRoad) != null) //if there is such road
            {
                Road next = incomingJunction.getRight(currentRoad);
                currentRoad = next;
                if(orientation == VehicleOrientation.VO_NORTH) orientation = VehicleOrientation.VO_EAST;
                else if(orientation == VehicleOrientation.VO_SOUTH) orientation = VehicleOrientation.VO_WEST;
                else if(orientation == VehicleOrientation.VO_WEST) orientation = VehicleOrientation.VO_NORTH;
                else if(orientation == VehicleOrientation.VO_EAST) orientation = VehicleOrientation.VO_SOUTH;
                randomizeIntention();
            }
            else
            {
                randomizeIntention();
                makeTurn();
            }
        }
    }

    protected void randomizeIntention()
    {
        Random rand = new Random();
        int random = rand.nextInt();
        if(random % 3 == 0) intention = Intention.LEFT;
        else if(random % 3 == 1) intention = Intention.STRAIGHT;
        else intention = Intention.RIGHT;
    }

    /*
    Returns distance to the next junction
    */
    public double getIncomingJunctionDistance()
    {
        Junction incomingJunction = getIncomingJunction();
        double distance;
        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
        {
            if(orientation == VehicleOrientation.VO_EAST) distance = incomingJunction.getPosX() - posX;
            else distance = posX - incomingJunction.getPosX();
        }
        else //for vertical roads
        {
            if(orientation == VehicleOrientation.VO_NORTH) distance = posY - incomingJunction.getPosY();
            else distance = incomingJunction.getPosY() - posY;
        }
        return distance;
    }

    /*
        Return the next junction the car is heading to
     */
    public Junction getIncomingJunction()
    {
        Junction incomingJunction;
        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
        {
            if(orientation == VehicleOrientation.VO_EAST)
            {
                if(currentRoad.getStartJunction().getPosX() > posX)
                    incomingJunction = currentRoad.getStartJunction();
                else
                    incomingJunction = currentRoad.getEndJunction();
            }
            else
            {
                if(currentRoad.getStartJunction().getPosX() < posX)
                    incomingJunction = currentRoad.getStartJunction();
                else
                    incomingJunction = currentRoad.getEndJunction();
            }
        }
        else //for vertical roads
        {
            if(orientation == VehicleOrientation.VO_NORTH)
            {
                if(currentRoad.getStartJunction().getPosY() < posY)
                    incomingJunction = currentRoad.getStartJunction();
                else
                    incomingJunction = currentRoad.getEndJunction();
            }
            else
            {
                if(currentRoad.getStartJunction().getPosY() > posY)
                    incomingJunction = currentRoad.getStartJunction();
                else
                    incomingJunction = currentRoad.getEndJunction();
            }
        }
        return incomingJunction;
    }

    /*
        Sets the halt flag for the vehicle - if it is true, then the car should not move
     */
    public void halt(boolean halt)
    {
        this.halt = halt;
    }

    /*
        Returns the current road the vehicle is on
     */
    public Road getCurrentRoad()
    {
        return currentRoad;
    }

    /*
        Returns current vehicle orientation
     */
    public VehicleOrientation getOrientation()
    {
        return orientation;
    }

    /*
        Sets the vehicle orientation as given in the parameter
     */
    public void setOrientation(VehicleOrientation newOrientation)
    {
        orientation = newOrientation;
    }

    /*
        Returns the color of the vehicle as a Paint object
     */
    public Paint getColor()
    {
        return color;
    }

    /*
        Returns x-coord of the vehicle position
        (0,0) is the upper left corner of the map
        Coord is always greater or equal to 0
     */
    public double getPosX()
    {
        return posX;
    }

    /*
        Returns y-coord of the vehicle position
        (0,0) is the upper left corner of the map
        Coord is always greater or equal to 0
     */
    public double getPosY()
    {
        return posY;
    }

    /*
        Stores vehicle's possible orientations - north, east, south and west
     */
    public enum VehicleOrientation
    {
        VO_NORTH,
        VO_EAST,
        VO_SOUTH,
        VO_WEST
    }

    public Intention getIntention()
    {
        return intention;
    }

    /*
        Stores vehicle preferred turn action possibilities at the next junction
     */
    public enum Intention
    {
        STRAIGHT,
        LEFT,
        RIGHT
    }

    protected Intention intention;
    protected double posX;
    protected double posY;
    protected Road currentRoad;
    protected VehicleOrientation orientation;
    protected boolean halt;
    protected Paint color;
    protected double speed;
}
