package po.trafficsim.vehicle;

import javafx.scene.paint.Color;

import po.trafficsim.world.*;

/*
    Simple implementation of abstract Vehicle, which brings no new elements to Vehicle mechanisms
    other than setting speed to non-zero value and color to random value and randomizing
    entity's spawn location on a road
 */

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
    }

    public String toString()
    {
        return "Car [x=" + posX + ",y=" + posY + "," + orientation + ",color=" + color + "," + halt + "," + intention + "]";
    }
}
