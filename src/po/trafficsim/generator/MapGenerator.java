package po.trafficsim.generator;

import java.util.Random;
import po.trafficsim.schema.*;
import po.trafficsim.world.*;
import po.trafficsim.exception.*;
import po.trafficsim.vehicle.*;

/*
    Default generation algorithm for project presentation
    Creates a set of junctions, where connectors have random length (except the starter road)
 */
public class MapGenerator implements IMapGenerator
{
    public MapSchema generate()
    {
        //create new map schema
        MapSchema mapSchema = new MapSchema(SIZE);
        Random rand = new Random();
        double minRoadLength = 100.0;
        //start with a single horizontal road
        Road start = new Road(250, 300, 350, 300);
        //add roads to left junction
        Junction left = start.getStartJunction();
        Road leftWest = new Road(left.getPosX(), left.getPosY(), left.getPosX() - rand.nextInt((int)(left.getPosX()-minRoadLength))-minRoadLength, left.getPosY());
        Road leftNorth = new Road(left.getPosX(), left.getPosY(), left.getPosX(), left.getPosY() - rand.nextInt((int)(left.getPosY()-minRoadLength))-minRoadLength);
        Road leftSouth = new Road(left.getPosX(), left.getPosY(), left.getPosX(), left.getPosY() + rand.nextInt((int)(SIZE - left.getPosY()-minRoadLength))+minRoadLength);
        try
        {
            leftWest.setJunctions(left, leftWest.getEndJunction());
            leftNorth.setJunctions(left ,leftNorth.getEndJunction());
            leftSouth.setJunctions(left, leftSouth.getEndJunction());
        }
        catch(JunctionCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try
        {
            left.addConnector(leftWest);
            left.addConnector(leftNorth);
            left.addConnector(leftSouth);
            left.addConnector(start);
            leftWest.getEndJunction().addConnector(leftWest);
            leftNorth.getEndJunction().addConnector(leftNorth);
            leftSouth.getEndJunction().addConnector(leftSouth);
        }
        catch(RoadCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        //add roads to right junction
        Junction right = start.getEndJunction();
        Road rightEast = new Road(right.getPosX(), right.getPosY(), right.getPosX() + rand.nextInt((int)(SIZE - right.getPosX()-minRoadLength))+minRoadLength, right.getPosY());
        Road rightNorth = new Road(right.getPosX(), right.getPosY(), right.getPosX(), right.getPosY() - rand.nextInt((int)(right.getPosY()-minRoadLength))-minRoadLength);
        Road rightSouth = new Road(right.getPosX(), right.getPosY(), right.getPosX(), left.getPosY() + rand.nextInt((int)(SIZE-right.getPosY()-minRoadLength))+minRoadLength);
        try
        {
            rightEast.setJunctions(right, rightEast.getEndJunction());
            rightNorth.setJunctions(right ,rightNorth.getEndJunction());
            rightSouth.setJunctions(right, rightSouth.getEndJunction());
        }
        catch(JunctionCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try
        {
            right.addConnector(rightEast);
            right.addConnector(rightNorth);
            right.addConnector(rightSouth);
            right.addConnector(start);
            rightEast.getEndJunction().addConnector(rightEast);
            rightNorth.getEndJunction().addConnector(rightNorth);
            rightSouth.getEndJunction().addConnector(rightSouth);
        }
        catch(RoadCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //create a single curve
        Road northEastRight = new Road(rightNorth.getEndJunction().getPosX(), rightNorth.getEndJunction().getPosY(),
                rightNorth.getEndJunction().getPosX() + minRoadLength, rightNorth.getEndJunction().getPosY());
        try
        {
            northEastRight.setJunctions(rightNorth.getEndJunction(), northEastRight.getEndJunction());
        }
        catch(JunctionCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try
        {
            rightNorth.getEndJunction().addConnector(northEastRight);
            northEastRight.getEndJunction().addConnector(northEastRight);
        }
        catch(RoadCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //create a three-way junction
        Road threeWayNorth = new Road(leftWest.getEndJunction().getPosX(), leftWest.getEndJunction().getPosY(),
                leftWest.getEndJunction().getPosX(), leftWest.getEndJunction().getPosY()-minRoadLength*2);
        Road threeWaySouth = new Road(leftWest.getEndJunction().getPosX(), leftWest.getEndJunction().getPosY(),
                leftWest.getEndJunction().getPosX(), leftWest.getEndJunction().getPosY()+minRoadLength);
        try
        {
            threeWayNorth.setJunctions(leftWest.getEndJunction(), threeWayNorth.getEndJunction());
            threeWaySouth.setJunctions(leftWest.getEndJunction(), threeWaySouth.getEndJunction());
        }
        catch(JunctionCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        try
        {
            threeWayNorth.getEndJunction().addConnector(threeWayNorth);
            threeWaySouth.getEndJunction().addConnector(threeWaySouth);
            leftWest.getEndJunction().addConnector(threeWayNorth);
            leftWest.getEndJunction().addConnector(threeWaySouth);
        }
        catch(RoadCoordException e)
        {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        //add junctions to the schema
        mapSchema.addJunction(left);
        mapSchema.addJunction(leftNorth.getEndJunction());
        mapSchema.addJunction(leftWest.getEndJunction());
        mapSchema.addJunction(leftSouth.getEndJunction());
        mapSchema.addJunction(right);
        mapSchema.addJunction(rightNorth.getEndJunction());
        mapSchema.addJunction(rightEast.getEndJunction());
        mapSchema.addJunction(rightSouth.getEndJunction());
        mapSchema.addJunction(northEastRight.getEndJunction());
        mapSchema.addJunction(threeWayNorth.getEndJunction());
        mapSchema.addJunction(threeWaySouth.getEndJunction());
        //add roads to the schema
        mapSchema.addRoad(start);
        mapSchema.addRoad(leftNorth);
        mapSchema.addRoad(leftWest);
        mapSchema.addRoad(leftSouth);
        mapSchema.addRoad(rightNorth);
        mapSchema.addRoad(rightEast);
        mapSchema.addRoad(rightSouth);
        mapSchema.addRoad(northEastRight);
        mapSchema.addRoad(threeWayNorth);
        mapSchema.addRoad(threeWaySouth);

        for(int i = 0; i < 40; i++)
        {
            mapSchema.addVehicle(new Car(mapSchema.getRoads().get(rand.nextInt(mapSchema.getRoads().size()))));
        }
        mapSchema.addVehicle(new SuperFastBike(start));
        mapSchema.addVehicle(new SuperFastBike(leftNorth));
        mapSchema.addVehicle(new SuperFastBike(rightNorth));

        return mapSchema;
    }

    private final int SIZE = 600;
}
