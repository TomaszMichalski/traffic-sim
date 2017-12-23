import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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
            distance = Math.abs(incomingJunction.getPosX() - posX);
        else //for vertical roads
            distance = Math.abs(incomingJunction.getPosY() - posY);
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

    public void halt(boolean halt)
    {
        this.halt = halt;
    }

    public Road getCurrentRoad()
    {
        return currentRoad;
    }

    public VehicleOrientation getOrientation()
    {
        return orientation;
    }

    public void setOrientation(VehicleOrientation newOrientation)
    {
        orientation = newOrientation;
    }

    public Paint getColor()
    {
        return color;
    }

    public double getPosX()
    {
        return posX;
    }

    public double getPosY()
    {
        return posY;
    }

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
