import javafx.scene.canvas.Canvas;

public interface IMapViewer extends Runnable
{
    /*
        Tranforms MapSchema to Canvas format, drawable on screen
     */
    public Canvas show();

    public MapSchema getMapSchema();

    /*
        Returns viewer's set vehicle size (length) as used in the show() method
     */
    public double getVehicleSize(IVehicle vehicle);
    /*
        Return viewer's set junction size as used in the show() method
     */
    public double getJunctionSize();
}
