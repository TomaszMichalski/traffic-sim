package po.trafficsim.tests;

import org.junit.*;
import po.trafficsim.vehicle.*;
import po.trafficsim.world.*;
import po.trafficsim.exception.*;

public class TestRunner
{
    @Test
    public void correctJunctionAdding() throws JunctionCoordException
    {
        Road road = new Road(0,0 ,100,0); //simple horizontal road with default junctions

        Road customRoad = new Road(0, 0,100,0); //same road but with custom junctions added
        Junction startJunction = new Junction(0,0);
        Junction endJunction = new Junction(100, 0);
        Road customRoad2 = new Road(0, 0,  100, 0);
        customRoad.setJunctions(startJunction, endJunction);
        customRoad2.setJunctions(endJunction, startJunction);

        Assert.assertEquals(road, customRoad);
        Assert.assertEquals(road, customRoad2);
    }

    @Test(expected = JunctionCoordException.class)
    public void invalidCoordJunctionAdding() throws JunctionCoordException
    {
        Road road = new Road(0,0,100,0); //simple horizontal road
        Junction custom = new Junction(0,0); //correct junction
        Junction custom2 = new Junction(200,0); //wrong junction
        road.setJunctions(custom, custom2);
    }

    @Test
    public void correctConnectorAdding() throws RoadCoordException
    {
        Junction junction = new Junction(0,0);
        Road connector = new Road(0,0,100,0);
        junction.addConnector(connector);

        //no exception should be thrown
    }

    @Test(expected = RoadCoordException.class)
    public void invalidCoordConnectorAdding() throws RoadCoordException
    {
        Junction junction = new Junction(0,0);
        Road connector = new Road(10,0, 100 ,0);
        junction.addConnector(connector); //should throw exception
    }

    @Test
    public void gettingStraightRoad() throws RoadCoordException, JunctionCoordException
    {
        //create a three way junction with north, east and south connectors
        Junction main = new Junction(100,100);
        Road north = new Road(100, 100, 100,0);
        Road east = new Road(100,100,200,100);
        Road south = new Road(100,100,100,200);
        main.addConnector(north);
        main.addConnector(east);
        main.addConnector(south);
        north.setJunctions(main, north.getEndJunction());
        east.setJunctions(main,east.getEndJunction());
        south.setJunctions(main, south.getEndJunction());

        Road straightFromNorth = main.getStraight(north); //should be south
        Road straightFromSouth = main.getStraight(south); //should be north
        Road straightFromEast = main.getStraight(east); //should be null

        Assert.assertEquals(south, straightFromNorth);
        Assert.assertEquals(north, straightFromSouth);
        Assert.assertNull(straightFromEast);
    }

    @Test
    public void gettingLeftRoad() throws RoadCoordException, JunctionCoordException
    {
        Junction main = new Junction(100,100); //create two way junction with north and east connectors
        Road north = new Road(100,100, 100,0);
        Road east = new Road(100,100,200,100);
        main.addConnector(north);
        main.addConnector(east);
        north.setJunctions(main, north.getEndJunction());
        east.setJunctions(main,east.getEndJunction());

        Road leftFromNorth = main.getLeft(north); //should be east
        Road leftFromEast = main.getLeft(east); //should be null

        Assert.assertEquals(east, leftFromNorth);
        Assert.assertNull(leftFromEast);
    }

    @Test
    public void gettingRightRoad() throws RoadCoordException, JunctionCoordException
    {
        Junction main = new Junction(100,100); //create two way junction with north and east connectors
        Road north = new Road(100,100, 100,0);
        Road east = new Road(100,100,200,100);
        main.addConnector(north);
        main.addConnector(east);
        north.setJunctions(main, north.getEndJunction());
        east.setJunctions(main,east.getEndJunction());

        Road rightFromEast = main.getRight(east); //should be north
        Road rightFromNorth = main.getRight(north); //should be null

        Assert.assertEquals(north, rightFromEast);
        Assert.assertNull(rightFromNorth);
    }
}
