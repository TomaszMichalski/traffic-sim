package po.trafficsim.ioc;

import po.trafficsim.collision.ICollisionEngine;
import po.trafficsim.generator.IMapGenerator;
import po.trafficsim.viewer.IMapViewer;

public interface IProvider
{
    void init();
    IMapGenerator getMapGeneratorInstance();
    IMapViewer getMapViewerInstance();
    ICollisionEngine getCollisionEngineInstance();
}
