/*
    Generates a simple four-way junction
    Mainly for collision and junction passage purposes
 */
public class SimpleJunctionGenerator implements IMapGenerator
{
    public MapSchema generate()
    {
        //create new map schema
        MapSchema mapSchema = new MapSchema(SIZE);
        //create main junction
        Junction mainJunction = new Junction(150,150);
        //create roads and their end junctions
        Road north = new Road(150, 150,150,50);
        Junction northJunction = new Junction(150,50);

        Road east = new Road(150,150,250,150);
        Junction eastJunction = new Junction(250,150);

        Road south = new Road(150,150, 150,250);
        Junction southJunction = new Junction(150,250);

        Road west = new Road(150,150,100,150);
        Junction westJunction = new Junction(100,150);
        //set connectors to junctions
        try
        {
            northJunction.addConnector(north);
            eastJunction.addConnector(east);
            southJunction.addConnector(south);
            westJunction.addConnector(west);
            mainJunction.addConnector(north);
            mainJunction.addConnector(east);
            mainJunction.addConnector(south);
            mainJunction.addConnector(west);
        }
        catch(RoadCoordException e)
        {
            e.printStackTrace();
        }
        //set junctions for roads
        try
        {
            north.setJunctions(mainJunction, northJunction);
            east.setJunctions(mainJunction, eastJunction);
            south.setJunctions(mainJunction, southJunction);
            west.setJunctions(mainJunction, westJunction);
        }
        catch(JunctionCoordException e)
        {
            e.printStackTrace();
        }
        //add roads to schema
        mapSchema.addRoad(north);
        System.out.println(north);
        mapSchema.addRoad(east);
        System.out.println(east);
        mapSchema.addRoad(south);
        System.out.println(south);
        mapSchema.addRoad(west);
        System.out.println(west);
        //add junctions to schema
        mapSchema.addJunction(northJunction);
        mapSchema.addJunction(eastJunction);
        mapSchema.addJunction(southJunction);
        mapSchema.addJunction(westJunction);
        mapSchema.addJunction(mainJunction);
        //Car[] cars = { new Car(north), new Car(north), new Car(south), new Car(east), new Car(east), new Car(west), new Car(west), new Car(west),
            //new Car(west), new Car(east), new Car(north), new Car(south) };
        Car[] cars = { new Car(north), new Car(south), new Car(north), new Car(south),new Car(north), new Car(south),new Car(north), new Car(south)};
        for(Car car : cars)
        {
            System.out.println(car);
            mapSchema.addVehicle(car);
        }

        return mapSchema;
    }

    private final int SIZE = 300;
}
