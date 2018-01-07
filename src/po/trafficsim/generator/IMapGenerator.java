package po.trafficsim.generator;

import po.trafficsim.schema.*;

public interface IMapGenerator
{
    /*
        Returns generated map in MapSchema format
     */
    MapSchema generate();
}
