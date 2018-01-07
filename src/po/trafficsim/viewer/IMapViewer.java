package po.trafficsim.viewer;

import javafx.scene.canvas.Canvas;
import po.trafficsim.schema.*;
import po.trafficsim.vehicle.*;

public interface IMapViewer
{
    /*
        Transforms MapSchema to Canvas format, drawable on screen
     */
    void show(Canvas canvas);

    /*
        Returns the map schema in MapSchema format
     */
    MapSchema getMapSchema();

    /*
        Returns viewer's set vehicle size (length) as used in the show() method
     */
    double getVehicleSize(Vehicle vehicle);
    /*
        Return viewer's set junction size as used in the show() method
     */
    double getJunctionSize();
}
