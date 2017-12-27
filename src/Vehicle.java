import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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

    /*
        Performs a turn-action
     */
    protected abstract void makeTurn();

    /*
        Performs a general 'move' action
     */
    protected abstract void move();

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

    protected double posX;
    protected double posY;
    protected Road currentRoad;
    protected VehicleOrientation orientation;
    protected boolean halt;
    protected Paint color;
    protected double speed;
}
