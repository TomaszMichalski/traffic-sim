package po.trafficsim.schema;

import po.trafficsim.world.*;
import java.util.*;
import po.trafficsim.vehicle.*;

/*
    Contains and manages the schema of map, which is then processed to the implementation of IMapViewer
 */

public class MapSchema
{
    public MapSchema(int size)
    {
        roads = new ArrayList<>();
        junctions = new ArrayList<>();
        vehicles = new ArrayList<>();
        SIZE = size;
    }

    /*
        Adds a new road to the schema
     */
    public void addRoad(Road road)
    {
        roads.add(road);
    }

    /*
        Adds a new junction to the schema
     */
    public void addJunction(Junction junction)
    {
        junctions.add(junction);
    }

    /*
        Adds a new vehicle to the schema
     */
    public void addVehicle(Vehicle vehicle)
    {
        vehicles.add(vehicle);
    }

    public ArrayList<Road> getRoads()
    {
        return roads;
    }

    public ArrayList<Junction> getJunctions()
    {
        return junctions;
    }

    public ArrayList<Vehicle> getVehicles() { return vehicles; }

    public int getSize()
    {
        return SIZE;
    }

    private ArrayList<Road> roads;
    private ArrayList<Junction> junctions;
    private ArrayList<Vehicle> vehicles;
    private final int SIZE;
}
