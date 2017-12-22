import java.util.*;

/*
    Contains and manages the schema of map, which is then processed to the implementation of IMapViewer
 */

public class MapSchema
{
    public MapSchema(int size)
    {
        roads = new ArrayList<Road>();
        junctions = new ArrayList<Junction>();
        vehicles = new ArrayList<Vehicle>();
        SIZE = size;
    }

    public void addRoad(Road road)
    {
        roads.add(road);
    }

    public void addJunction(Junction junction)
    {
        junctions.add(junction);
    }

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
