package po.trafficsim.ioc;

import po.trafficsim.collision.CollisionEngine;
import po.trafficsim.collision.ICollisionEngine;
import po.trafficsim.generator.IMapGenerator;
import po.trafficsim.generator.MapGenerator;
import po.trafficsim.viewer.IMapViewer;
import po.trafficsim.viewer.WholeMapViewer;

public class Provider
{
    public static void init()
    {
        mapGenerator = new MapGenerator();
        mapViewer = new WholeMapViewer(mapGenerator.generate());
        collisionEngine = new CollisionEngine(mapViewer);
    }

    public static IMapGenerator getMapGeneratorInstance()
    {
        return mapGenerator;
    }

    public static IMapViewer getMapViewerInstance()
    {
        return mapViewer;
    }

    public static ICollisionEngine getCollisionEngineInstance()
    {
        return collisionEngine;
    }

    private static IMapViewer mapViewer;
    private static IMapGenerator mapGenerator;
    private static ICollisionEngine collisionEngine;
}
