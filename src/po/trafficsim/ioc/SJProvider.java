package po.trafficsim.ioc;

import po.trafficsim.collision.CollisionEngine;
import po.trafficsim.collision.ICollisionEngine;
import po.trafficsim.generator.IMapGenerator;
import po.trafficsim.generator.SimpleJunctionGenerator;
import po.trafficsim.viewer.IMapViewer;
import po.trafficsim.viewer.WholeMapViewer;

public class SJProvider implements IProvider
{
    public void init()
    {
        mapGenerator = new SimpleJunctionGenerator();
        mapViewer = new WholeMapViewer(mapGenerator.generate());
        collisionEngine = new CollisionEngine(mapViewer);
    }

    public IMapGenerator getMapGeneratorInstance()
    {
        return mapGenerator;
    }

    public IMapViewer getMapViewerInstance()
    {
        return mapViewer;
    }

    public ICollisionEngine getCollisionEngineInstance()
    {
        return collisionEngine;
    }

    private IMapViewer mapViewer;
    private IMapGenerator mapGenerator;
    private ICollisionEngine collisionEngine;
}
