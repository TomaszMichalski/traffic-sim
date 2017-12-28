package po.trafficsim.viewer;

import javafx.geometry.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.scene.shape.*;
import po.trafficsim.schema.*;
import po.trafficsim.vehicle.*;
import po.trafficsim.world.*;
/*
    The whole map is displayed as a single image - no slider bars or displaying only a part of it
    Displayed map schema is scaled based on screen size
    Roads are displayed as black thick lines
    Displays only vehicles which are an instance of Car class
 */

public class WholeMapViewer implements IMapViewer
{
    public WholeMapViewer(MapSchema schema)
    {
        mapSchema = schema;
        screenSize = Screen.getPrimary().getVisualBounds();
        LINE_WIDTH = 10.0;
        SCALE = screenSize.getHeight() / mapSchema.getSize();
        OFFSET = Math.abs(screenSize.getHeight() - screenSize.getWidth())/2;
    }

    public void show(Canvas canvas)
    {
        canvas.setWidth(screenSize.getWidth());
        canvas.setHeight(screenSize.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.setLineWidth(LINE_WIDTH * SCALE);
        for(Road road : mapSchema.getRoads())
        {
            Line roadLine = road.getRoadLine();
            gc.strokeLine(roadLine.getStartX() * SCALE + OFFSET, roadLine.getStartY() * SCALE,
                              roadLine.getEndX() * SCALE + OFFSET, roadLine.getEndY() * SCALE);
        }
        for(Vehicle vehicle : mapSchema.getVehicles())
        {
            if(vehicle instanceof Car)
            {
                Car car = (Car)vehicle;
                gc.setFill(car.getColor());
                if(car.getOrientation() == Vehicle.VehicleOrientation.VO_NORTH)
                {
                    gc.fillRect(car.getCurrentRoad().getRoadLine().getStartX() * SCALE + OFFSET, car.getPosY() * SCALE,
                                LINE_WIDTH / 2 * SCALE, LINE_WIDTH * SCALE);
                }
                else if(car.getOrientation() == Vehicle.VehicleOrientation.VO_EAST)
                {
                    gc.fillRect((car.getPosX() - LINE_WIDTH) * SCALE + OFFSET, car.getCurrentRoad().getRoadLine().getStartY() * SCALE,
                                    LINE_WIDTH * SCALE, LINE_WIDTH / 2 * SCALE);
                }
                else if(car.getOrientation() == Vehicle.VehicleOrientation.VO_SOUTH)
                {
                    gc.fillRect( (car.getCurrentRoad().getRoadLine().getStartX() - LINE_WIDTH / 2) * SCALE + OFFSET, (car.getPosY() - LINE_WIDTH) * SCALE,
                                    LINE_WIDTH / 2 * SCALE, LINE_WIDTH * SCALE);
                }
                else if(car.getOrientation() == Vehicle.VehicleOrientation.VO_WEST)
                {
                    gc.fillRect( car.getPosX() * SCALE + OFFSET, (car.getCurrentRoad().getRoadLine().getStartY() - LINE_WIDTH / 2) * SCALE,
                                        LINE_WIDTH * SCALE, LINE_WIDTH / 2 * SCALE);
                }
            }
            else if(vehicle instanceof SuperFastBike)
            {
                SuperFastBike sfb = (SuperFastBike)vehicle;
                gc.setFill(sfb.getColor());
                if(sfb.getOrientation() == Vehicle.VehicleOrientation.VO_NORTH)
                {
                    gc.fillRect(sfb.getCurrentRoad().getRoadLine().getStartX() * SCALE + OFFSET, sfb.getPosY() * SCALE,
                            LINE_WIDTH / 4 * SCALE, LINE_WIDTH * SCALE);
                }
                else if(sfb.getOrientation() == Vehicle.VehicleOrientation.VO_EAST)
                {
                    gc.fillRect((sfb.getPosX() - LINE_WIDTH) * SCALE + OFFSET, sfb.getCurrentRoad().getRoadLine().getStartY() * SCALE,
                            LINE_WIDTH * SCALE, LINE_WIDTH / 4 * SCALE);
                }
                else if(sfb.getOrientation() == Vehicle.VehicleOrientation.VO_SOUTH)
                {
                    gc.fillRect( (sfb.getCurrentRoad().getRoadLine().getStartX() - LINE_WIDTH / 4) * SCALE + OFFSET, (sfb.getPosY() - LINE_WIDTH) * SCALE,
                            LINE_WIDTH / 4 * SCALE, LINE_WIDTH * SCALE);
                }
                else if(sfb.getOrientation() == Vehicle.VehicleOrientation.VO_WEST)
                {
                    gc.fillRect( sfb.getPosX() * SCALE + OFFSET, (sfb.getCurrentRoad().getRoadLine().getStartY() - LINE_WIDTH / 4) * SCALE,
                            LINE_WIDTH * SCALE, LINE_WIDTH / 4 * SCALE);
                }
            }
        }
    }

    public MapSchema getMapSchema()
    {
        return mapSchema;
    }

    public double getVehicleSize(Vehicle vehicle)
    {
        if(vehicle instanceof Car)
        {
            return LINE_WIDTH*1.2;
        }
        else if(vehicle instanceof SuperFastBike)
        {
            return LINE_WIDTH*2.0;
        }
        else return 0;
    }

    public double getJunctionSize()
    {
        return LINE_WIDTH / 2;
    }

    private MapSchema mapSchema;
    private Rectangle2D screenSize;
    private final double SCALE;
    private final double OFFSET;
    private final double LINE_WIDTH;
}
