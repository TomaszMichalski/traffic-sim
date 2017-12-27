/*
    Advanced randomized world generation algorithm, which creates many roads with all different number of connections in junctions
    Default generator for target simulation purpose
 */
public class MapGenerator implements IMapGenerator
{
    public MapSchema generate()
    {
        //create new map schema
        MapSchema mapSchema = new MapSchema(SIZE);
        //start with a single horizontal road
        return mapSchema;
    }

    private final int SIZE = 600;
}
