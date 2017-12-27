import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Random;

public class Car extends Vehicle
{
    public Car(Road road)
    {
        super(road);
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
        speed = 2.5;
        halt = false;
        randomizeIntention();
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
        //System.out.println(this);
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
                if(orientation == VehicleOrientation.VO_NORTH) posY -= speed;
                else if(orientation == VehicleOrientation.VO_SOUTH) posY += speed;
                else if(orientation == VehicleOrientation.VO_WEST) posX -= speed;
                else if(orientation == VehicleOrientation.VO_EAST) posX += speed;
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

            }
            else
            {
                randomizeIntention();
                makeTurn();
            }
        }
    }

    private void randomizeIntention()
    {
        Random rand = new Random();
        int random = rand.nextInt();
        if(random % 3 == 0) intention = Intention.LEFT;
        else if(random % 3 == 1) intention = Intention.STRAIGHT;
        else intention = Intention.RIGHT;
    }

    public Intention getIntention()
    {
        return intention;
    }

    public String toString()
    {
        return "Car [x=" + posX + ",y=" + posY + "," + orientation + ",color=" + color + "," + halt + "," + intention + "]";
    }

    public enum Intention
    {
        STRAIGHT,
        LEFT,
        RIGHT
    }

    private Intention intention;
}
