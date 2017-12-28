package po.trafficsim.generator;

import po.trafficsim.schema.*;
import po.trafficsim.world.*;
import po.trafficsim.exception.*;
import po.trafficsim.vehicle.*;

/*
    Generates a map with a single road
    Very basic case of world generation, mainly for early test purposes
 */

public class SimpleRoadGenerator implements IMapGenerator
{
    public MapSchema generate()
    {
        //create new map schema
        MapSchema mapSchema = new MapSchema(SIZE);
        //create new horizontal road
        Road road = new Road(100, 200, 300, 200);
        //create junctions at the end of the road
        Junction startJunction = new Junction(100,200);
        Junction endJunction = new Junction(300,200);
        //add road to each junction as a connection
        try
        {
            startJunction.addConnector(road);
            endJunction.addConnector(road);
        }
        catch(RoadCoordException e)
        {
            e.printStackTrace();
        }
        //set the junctions for the road
        try
        {
            road.setJunctions(startJunction, endJunction);
        }
        catch(JunctionCoordException e)
        {
            e.printStackTrace();
        }
        //add the road to the schema
        mapSchema.addRoad(road);
        System.out.println(road);
        //add junctions to the schema
        mapSchema.addJunction(startJunction);
        mapSchema.addJunction(endJunction);
        //create some cars
        mapSchema.addVehicle(new SuperFastBike(road));

        return mapSchema;
    }

    private final int SIZE = 400;
}
