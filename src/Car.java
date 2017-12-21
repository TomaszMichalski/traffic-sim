import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;

public class Car implements IVehicle
{
    public Car(Road road)
    {
        currentRoad = road;
        Random rand = new Random();
        //setting place for the car
        if(road.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL)
        {
            this.posY = road.getRoadLine().getStartY();
            double minBound = Math.min(road.getRoadLine().getStartX(), road.getRoadLine().getEndX()) * 1.1;
            double maxBound = Math.max(road.getRoadLine().getStartX(), road.getRoadLine().getEndX()) * 0.9;
            this.posX = minBound + (maxBound - minBound) * rand.nextDouble();
            int orientationRand = Math.abs(rand.nextInt());
            if(orientationRand % 2 == 0) orientation = VehicleOrientation.VO_WEST;
            else orientation = VehicleOrientation.VO_EAST;
        }
        else if(road.getOrientation() == Road.RoadOrientation.RO_VERTICAL)
        {
            this.posX = road.getRoadLine().getStartX();
            double minBound = Math.min(road.getRoadLine().getStartY(), road.getRoadLine().getEndY()) * 1.1;
            double maxBound = Math.max(road.getRoadLine().getStartY(), road.getRoadLine().getEndY()) * 0.9;
            this.posY = minBound + (maxBound - minBound) * rand.nextDouble();
            int orientationRand = Math.abs(rand.nextInt());
            if(orientationRand % 2 == 0) orientation = VehicleOrientation.VO_NORTH;
            else orientation = VehicleOrientation.VO_SOUTH;
        }
        //setting random color
        int r = rand.nextInt(255);
        int g = rand.nextInt(255);
        int b = rand.nextInt(255);
        color = Color.rgb(r,g,b);
        speed = 0.01;
        halt = true;
        intention = Intention.STRAIGHT;
    }

    public void run()
    {
        while(true)
            move();
    }

    private void move()
    {
        if(!halt)
        {
            if(orientation == VehicleOrientation.VO_NORTH)
                posY -= speed;
            else if(orientation == VehicleOrientation.VO_EAST)
                posX += speed;
            else if(orientation == VehicleOrientation.VO_SOUTH)
                posY += speed;
            else if(orientation == VehicleOrientation.VO_WEST)
                posX -= speed;
        }
        System.out.println(this);
    }

    public Intention getIntention()
    {
        return intention;
    }

    /*
        Return the next junction the car is heading to
     */
    public Junction getIncomingJunction()
    {
        Junction incomingJunction;
        if(currentRoad.getOrientation() == Road.RoadOrientation.RO_HORIZONTAL) //for horizontal roads
        {
            if(orientation == IVehicle.VehicleOrientation.VO_EAST)
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
            if(orientation == IVehicle.VehicleOrientation.VO_NORTH)
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

    public double getPosX()
    {
        return posX;
    }

    public double getPosY()
    {
        return posY;
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

    public Road getCurrentRoad()
    {
        return currentRoad;
    }

    public String toString()
    {
        return "Car [x=" + posX + ",y=" + posY + "," + orientation + ",color=" + color + "," + halt + "]";
    }

    public enum Intention
    {
        STRAIGHT,
        LEFT,
        RIGHT
    }

    private double posX;
    private double posY;
    private VehicleOrientation orientation;
    private Paint color;
    private Road currentRoad;
    private double speed;
    private boolean halt;
    private Intention intention;
}
